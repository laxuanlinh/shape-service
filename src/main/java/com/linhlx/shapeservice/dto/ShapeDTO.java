package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.Shape;

import java.util.Map;

public class ShapeDTO {

    private Long id;
    private String shapeName;
    private ShapeCategoryDTO shapeCategory;
    private Map<String, Double> sizes;

    public ShapeDTO(Shape shape){
        this.id = shape.getId();
        this.shapeName = shape.getShapeName();
        this.shapeCategory = new ShapeCategoryDTO(shape.getShapeCategory());
        this.sizes = shape.getSizes();
    }

    public ShapeDTO() {
    }

    public ShapeDTO(Long id, String shapeName, ShapeCategoryDTO shapeCategory, Map<String, Double> sizes) {
        this.id = id;
        this.shapeName = shapeName;
        this.shapeCategory = shapeCategory;
        this.sizes = sizes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public ShapeCategoryDTO getShapeCategory() {
        return shapeCategory;
    }

    public void setShapeCategory(ShapeCategoryDTO shapeCategory) {
        this.shapeCategory = shapeCategory;
    }

    public Map<String, Double> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, Double> sizes) {
        this.sizes = sizes;
    }
}
