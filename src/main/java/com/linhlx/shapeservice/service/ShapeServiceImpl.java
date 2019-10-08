package com.linhlx.shapeservice.service;

import com.google.common.collect.Sets;
import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.dto.ShapeDTO;
import com.linhlx.shapeservice.exception.ShapeException;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.repository.ShapeCategoryRepository;
import com.linhlx.shapeservice.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ShapeServiceImpl implements ShapeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShapeServiceImpl.class);

    private final ShapeCategoryRepository shapeCategoryRepository;
    private final ShapeRepository shapeRepository;

    @Autowired
    public ShapeServiceImpl(ShapeCategoryRepository shapeCategoryRepository, ShapeRepository shapeRepository) {
        this.shapeCategoryRepository = shapeCategoryRepository;
        this.shapeRepository = shapeRepository;
    }

    @Override
    public List<ShapeDTO> getAllShapes() {
        List<Shape> shapes = shapeRepository.findAll();
        return this.convertToShapeDTO(shapes);
    }

    @Override
    public Shape createShape(Shape shape) {
        if (shape == null)
            throw new ShapeException("Shape cannot be null");

        ShapeCategory shapeCategory = shapeCategoryRepository.findById(shape.getShapeCategory().getShapeCategoryName())
                                                            .orElseThrow(()->new ShapeException("Shape category cannot be found"));
        shape.setShapeCategory(shapeCategory);

        this.validateShapeSizes(shape);

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

    private List<ShapeDTO> convertToShapeDTO(List<Shape> shapes) {
        return shapes.stream()
                .map(ShapeDTO::new)
                .collect(Collectors.toList());
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
        ShapeCategory shapeCategory = this.getShapeCategory(shape);
        Double area = this.calculateArea(shape.getSizes(), shapeCategory.getFormula());

        return new AreaDTO(area, new ShapeCategoryDTO(shapeCategory));
    }

    private ShapeCategory getShapeCategory(Shape shape){
        Set<String> dimensions = Sets.newHashSet();
        for (Map.Entry<String, Double> entry : shape.getSizes().entrySet()){
            dimensions.add(entry.getKey());
        }
        List<ShapeCategory> shapeCategories = shapeCategoryRepository.getShapeCategoryByDimensions(dimensions);
        if (shapeCategories.isEmpty())
            throw new ShapeException("Shape category not found");

        return shapeCategories.get(0);
    }

    private Double calculateArea(Map<String, Double> sizes, String formula){
        String[] formulaItems = formula.split(" ");
        String mathOperations = this.convertFormulaToOperations(sizes, formulaItems);
        return calculateFromString(mathOperations);
    }

    private String convertFormulaToOperations(Map<String, Double> sizes, String[] formulaItems){
        StringBuilder mathOperations = new StringBuilder();
        for (String formulaItem : formulaItems){
            if (isOperand(formulaItem)){
                mathOperations.append(sizes.get(formulaItem));
            } else {
                mathOperations.append(formulaItem);
            }
        }

        return mathOperations.toString();
    }
    private Boolean isOperand(String formulaItem){
        return !Arrays.asList("*", "-", "+", "/", "(", ")").contains(formulaItem) && !this.isNumber(formulaItem);
    }

    private boolean isNumber(String formulaItem) {
        try {
            Double.parseDouble(formulaItem);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    private Double calculateFromString(String operations){
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            return (Double) engine.eval(operations);
        } catch (ScriptException e){
            throw new ShapeException("Unable to read and calculate operations: "+operations);
        }
    }

}

























