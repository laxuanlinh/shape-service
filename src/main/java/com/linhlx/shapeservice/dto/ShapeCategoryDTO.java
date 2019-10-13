package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.ShapeCategory;

import java.util.Map;
import java.util.Set;

public class ShapeCategoryDTO {

    private String shapeCategoryName;
    private Set<String> dimensions;
    private String formula;
    private String rules;
    private Map<String, String> conditionsOtherCategories;

    public ShapeCategoryDTO(ShapeCategory shapeCategory){
        if (shapeCategory != null){
            this.shapeCategoryName = shapeCategory.getShapeCategoryName();
            this.dimensions = shapeCategory.getDimensions();
            this.formula = shapeCategory.getFormula();
            this.rules = shapeCategory.getRules();
            this.conditionsOtherCategories = shapeCategory.getConditionsOtherCategories();
        }
    }

    public ShapeCategoryDTO(String shapeCategoryName, Set<String> dimensions, String formula, String rules, Map<String, String> conditionsOtherCategories) {
        this.shapeCategoryName = shapeCategoryName;
        this.dimensions = dimensions;
        this.formula = formula;
        this.rules = rules;
        this.conditionsOtherCategories = conditionsOtherCategories;
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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public Map<String, String> getConditionsOtherCategories() {
        return conditionsOtherCategories;
    }

    public void setConditionsOtherCategories(Map<String, String> conditionsOtherCategories) {
        this.conditionsOtherCategories = conditionsOtherCategories;
    }
}
