package com.linhlx.shapeservice.dto;

public class AreaDTO {

    private Double area;
    private ShapeCategoryDTO possibleCategory;

    public AreaDTO() {
    }

    public AreaDTO(Double area, ShapeCategoryDTO possibleCategory) {
        this.area = area;
        this.possibleCategory = possibleCategory;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public ShapeCategoryDTO getPossibleCategory() {
        return possibleCategory;
    }

    public void setPossibleCategory(ShapeCategoryDTO possibleCategory) {
        this.possibleCategory = possibleCategory;
    }
}
