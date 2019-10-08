package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.ShapeCategory;

import java.util.List;
import java.util.Set;

public class ShapeCategoryDTO {

    private String shapeCategoryName;
    private Set<String> dimensions;
    private String formula;

    public ShapeCategoryDTO(ShapeCategory shapeCategory){
        if (shapeCategory != null){
            this.shapeCategoryName = shapeCategory.getShapeCategoryName();
            this.dimensions = shapeCategory.getDimensions();
            this.formula = shapeCategory.getFormula();
        }
    }

    public ShapeCategoryDTO(String shapeCategoryName, Set<String> dimensions, String formula) {
        this.shapeCategoryName = shapeCategoryName;
        this.dimensions = dimensions;
        this.formula = formula;
    }

    public ShapeCategoryDTO() {
    }

    public String getShapeCategoryName() {
        return shapeCategoryName;
    }

    public void setShapeCategoryName(String shapeCategoryName) {
        this.shapeCategoryName = shapeCategoryName;
    }

    public Set<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Set<String> dimensions) {
        this.dimensions = dimensions;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
