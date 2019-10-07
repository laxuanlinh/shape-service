package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.ShapeCategory;

public class ShapeCategoryDTO {

    private String shapeCategoryName;

    public ShapeCategoryDTO(ShapeCategory shapeCategory){
        this.shapeCategoryName = shapeCategory.getShapeCategoryName();
    }

    public ShapeCategoryDTO(String shapeCategoryName) {
        this.shapeCategoryName = shapeCategoryName;
    }

    public ShapeCategoryDTO() {
    }

    public String getShapeCategoryName() {
        return shapeCategoryName;
    }

    public void setShapeCategoryName(String shapeCategoryName) {
        this.shapeCategoryName = shapeCategoryName;
    }
}
