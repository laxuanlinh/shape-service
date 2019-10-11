package com.linhlx.shapeservice.service;

import com.google.common.base.Strings;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ShapeServiceImpl implements ShapeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShapeServiceImpl.class);

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

    private List<ShapeDTO> convertToShapeDTO(List<Shape> shapes) {
        return shapes.stream()
                .map(ShapeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Shape createShapeForCurrentUser(Shape shape, String currentUsername) {
        User user = this.validateUser(currentUsername);
        return this.createShape(shape, user);
    }

    private User validateUser(String username){
        return userRepository.findById(username)
                .orElseThrow(()->new UserException("User not found for shape"));
    }

    private Shape createShape(Shape shape, User user){
        if (shape == null)
            throw new ShapeException("Shape cannot be null");

        ShapeCategory shapeCategory = this.validateShapeCategory(shape);
        shape.setShapeCategory(shapeCategory);
        shape.setUser(user);
        this.validateShapeSizes(shape);
        this.validateShapeRules(shape);

        return shapeRepository.save(shape);
    }

    private void validateShapeSizes(Shape shape) {
        for (String dimension : shape.getShapeCategory().getDimensions()) {
            if (!shape.getSizes().containsKey(dimension))
                throw new ShapeException("Shape " + shape.getShapeName() + " does not have " + dimension);
        }
        if (shape.getSizes().size() > shape.getShapeCategory().getDimensions().size())
            throw new ShapeException("Shape " + shape.getShapeName() + " cannot have more dimensions than category");
    }


    private void validateShapeRules(Shape shape) {
        if (!Strings.isNullOrEmpty(shape.getShapeCategory().getRules())) {
            String[] rules = shape.getShapeCategory().getRules().split(",");
            for (String rule : rules){
                String[] ruleExpressionItems = rule.split(" ");
                String ruleExpression = this.convertFormulaToOperations(shape.getSizes(), ruleExpressionItems);
                this.evaluateRuleExpression(ruleExpression);
            }
        }
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
    public Shape createShapeForOtherUser(Shape shape) {
        if (shape == null)
            throw new ShapeException("Shape cannot be null");

        User user = this.validateUser(shape.getUser().getUsername());
        return this.createShape(shape, user);
    }

    private ShapeCategory validateShapeCategory(Shape shape){
        return shapeCategoryRepository.findById(shape.getShapeCategory().getShapeCategoryName())
                .orElseThrow(()->new ShapeException("Shape category cannot be found"));
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
        String[] formulaItems = formula.split(" ");
        String mathOperations = this.convertFormulaToOperations(sizes, formulaItems);
        return (Double) evaluate(mathOperations);
    }

    private String convertFormulaToOperations(Map<String, Double> sizes, String[] formulaItems){
        StringBuilder mathOperations = new StringBuilder();
        for (String formulaItem : formulaItems){
            mathOperations.append(sizes.get(formulaItem));
        }

        return mathOperations.toString();
    }
}

























