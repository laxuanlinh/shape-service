package com.linhlx.shapeservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "shape_category")
public class ShapeCategory {

    @Id
    @Column(name = "shape_category_name")
    private String shapeCategoryName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dimension", joinColumns = @JoinColumn(name = "shape_category_name"))
    @Column(name = "dimension")
    @NotNull(message = "Dimensions cannot be null")
    private Set<String> dimensions;

    @OneToMany(mappedBy = "shapeCategory")
    private List<Shape> shapes;
    @NotEmpty(message = "Formula cannot be empty")
    private String formula;

    private String rules;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="other_category_name")
    @Column(name = "conditions")
    @CollectionTable(name = "conditions_other_categories", joinColumns = @JoinColumn(name = "shape_category_name"))
    private Map<String, String> conditionsOtherCategories;

    public ShapeCategory() {
    }

    public ShapeCategory(String shapeCategoryName, Set<String> dimensions, List<Shape> shapes, String formula, String rules, Map<String, String> conditionsOtherCategories) {
        this.shapeCategoryName = shapeCategoryName;
        this.dimensions = dimensions;
        this.shapes = shapes;
        this.formula = formula;
        this.rules = rules;
        this.conditionsOtherCategories = conditionsOtherCategories;
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

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
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
