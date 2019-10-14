package com.linhlx.shapeservice.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.dto.ShapeDTO;
import com.linhlx.shapeservice.exception.ShapeException;
import com.linhlx.shapeservice.exception.UserException;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.model.User;
import com.linhlx.shapeservice.repository.ShapeCategoryRepository;
import com.linhlx.shapeservice.repository.ShapeRepository;
import com.linhlx.shapeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ShapeServiceImpl implements ShapeService {

    private final ShapeCategoryRepository shapeCategoryRepository;
    private final ShapeRepository shapeRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShapeServiceImpl(ShapeCategoryRepository shapeCategoryRepository, ShapeRepository shapeRepository, UserRepository userRepository) {
        this.shapeCategoryRepository = shapeCategoryRepository;
        this.shapeRepository = shapeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ShapeDTO> getAllShapes() {
        List<Shape> shapes = shapeRepository.findAll();
        return this.convertToShapeDTO(shapes);
    }

    @Override
    public List<ShapeDTO> getAllShapesForUser(String username) {
        List<Shape> shapes = shapeRepository.findAllByUsername(username);
        return this.convertToShapeDTO(shapes);
    }

    private List<ShapeDTO> convertToShapeDTO(List<Shape> shapes) {
        return shapes.stream()
                .map(ShapeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Shape saveShape(Shape shape) {
        if (shape == null)
            throw new ShapeException("Shape cannot be null");
        shape.setEnabled(true);
        ShapeCategory shapeCategory = this.getShapeCategory(shape.getShapeCategory().getShapeCategoryName());
        shape.setShapeCategory(shapeCategory);
        User user = this.getUser(shape.getUser().getUsername());
        shape.setUser(user);
        this.validateShapeSizes(shape);
        this.validateShapeRules(shape);

        return shapeRepository.save(shape);
    }

    private ShapeCategory getShapeCategory(String shapeCategoryName){
        return shapeCategoryRepository.findById(shapeCategoryName)
                .orElseThrow(()->new ShapeException("Shape category cannot be found"));
    }

    private User getUser(String username){
        return userRepository.findById(username)
                .orElseThrow(()->new UserException("User not found for shape"));
    }

    private Shape getShape(Long id){
        return shapeRepository.findById(id)
                .orElseThrow(()->new ShapeException("Shape not found"));
    }

    @Override
    public Long deleteShape(Long id) {
        Shape shape = this.getShape(id);
        shape.setEnabled(false);
        shapeRepository.save(shape);
        return id;
    }

    private void validateShapeSizes(Shape shape) {
        for (String dimension : shape.getShapeCategory().getDimensions()) {
            this.checkIfShapeHasDimension(shape, dimension);
        }
        this.checkIfShapeHasMoreDimensionsThanCategory(shape);
    }

    private void checkIfShapeHasMoreDimensionsThanCategory(Shape shape){
        if (shape.getSizes().size() > shape.getShapeCategory().getDimensions().size())
            throw new ShapeException("Shape " + shape.getShapeName() + " cannot have more dimensions than category");
    }

    private void checkIfShapeHasDimension(Shape shape, String dimensionName){
        if (!shape.getSizes().containsKey(dimensionName))
            throw new ShapeException("Shape " + shape.getShapeName() + " does not have " + dimensionName);
    }

    private void validateShapeRules(Shape shape) {
        if (this.hasRules(shape)) {
            String[] ruleTermArr = shape.getShapeCategory().getRules().split(" ");
            String ruleExpressionValues = this.replaceTermsWithValues(shape.getSizes(), ruleTermArr);
            this.evaluateRuleExpression(ruleExpressionValues);
        }
    }

    private Boolean hasRules(Shape shape){
        return !Strings.isNullOrEmpty(shape.getShapeCategory().getRules());
    }

    private void evaluateRuleExpression(String expression){
        if (!(Boolean) this.evaluate(expression)){
            throw new ShapeException("The shape does not satisfy shape category's requirements. This is wrong "+expression);
        }
    }

    private Object evaluate(String expression){
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            return engine.eval(expression);
        } catch (ScriptException e) {
            throw new ShapeException("Unable to read and evaluate expression: "+expression);
        }
    }

    @Override
    public List<ShapeCategoryDTO> getAllShapeCategories() {
        List<ShapeCategory> shapeCategories = shapeCategoryRepository.findAll();
        return this.convertToShapeCategoryDTO(shapeCategories);
    }

    private List<ShapeCategoryDTO> convertToShapeCategoryDTO(List<ShapeCategory> shapeCategories){
        return shapeCategories.stream()
                .map(ShapeCategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AreaDTO getArea(Shape shape) {
        ShapeCategory shapeCategory = this.getShapeCategoryBySizes(shape.getSizes().entrySet());
        shape.setShapeCategory(shapeCategory);
        this.validateShapeRules(shape);
        Double area = this.calculateArea(shape.getSizes(), shapeCategory.getFormula());
        return new AreaDTO(area, new ShapeCategoryDTO(shapeCategory));
    }

    private ShapeCategory getShapeCategoryBySizes(Set<Map.Entry<String, Double>> sizeEntrySet){
        Set<String> dimensions = this.getDimensionsFromSizes(sizeEntrySet);
        List<ShapeCategory> shapeCategories = this.getCategoriesFromDimensions(dimensions);

        return shapeCategories.get(0);
    }

    private Set<String> getDimensionsFromSizes(Set<Map.Entry<String, Double>> sizeEntrySet){
        Set<String> dimensions = Sets.newHashSet();
        for (Map.Entry<String, Double> entry : sizeEntrySet){
            dimensions.add(entry.getKey());
        }

        return dimensions;
    }

    private List<ShapeCategory> getCategoriesFromDimensions(Set<String> dimensions){
        List<ShapeCategory> shapeCategories = shapeCategoryRepository.getShapeCategoryByDimensions(dimensions);
        if (shapeCategories.isEmpty())
            throw new ShapeException("Shape category not found");
        return shapeCategories;
    }

    private Double calculateArea(Map<String, Double> sizes, String formula){
        String[] formulaTerms = formula.split(" ");
        String mathOperations = this.replaceTermsWithValues(sizes, formulaTerms);
        return (Double) evaluate(mathOperations);
    }

    private String replaceTermsWithValues(Map<String, Double> sizes, String[] formulaTerms){
        StringBuilder mathExpression = new StringBuilder();
        for (String formulaTerm : formulaTerms){
            mathExpression.append(this.getValueWithTerm(sizes, formulaTerm));
        }
        return mathExpression.toString();
    }

    private String getValueWithTerm(Map<String, Double> sizes, String formulaTerm){
        if (sizes.containsKey(formulaTerm))
            return String.valueOf(sizes.get(formulaTerm));
        else
            return formulaTerm;
    }

    @Override
    public ShapeCategoryDTO createCategory(ShapeCategory shapeCategory) {
        return new ShapeCategoryDTO(shapeCategoryRepository.save(shapeCategory));
    }

    @Override
    public List<String> getOtherCategories(Shape shape) {
        ShapeCategory shapeCategory = this.getShapeCategory(shape.getShapeCategory().getShapeCategoryName());
        shape.setShapeCategory(shapeCategory);
        this.validateShapeRules(shape);
        return this.validateShapeConditions(shape);
    }

    private List<String> validateShapeConditions(Shape shape) {
        List<String> otherCategories = Lists.newArrayList();
        if (shape.getShapeCategory() != null) {
            this.addSatisfiedCategories(shape, otherCategories);
        }

        return otherCategories;
    }

    private void addSatisfiedCategories(Shape shape, List<String> otherCategories){
        Set<Map.Entry<String, String>> conditionSet = shape.getShapeCategory().getConditionsOtherCategories().entrySet();

        for (Map.Entry<String, String> condition : conditionSet){
            String conditionWithValues = this.replaceTermsWithValues(shape.getSizes(), condition.getValue().split(" "));
            if ((Boolean) this.evaluate(conditionWithValues)){
                otherCategories.add(condition.getKey());
            }
        }
    }
}

























