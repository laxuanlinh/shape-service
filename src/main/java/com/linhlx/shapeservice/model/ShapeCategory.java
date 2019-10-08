package com.linhlx.shapeservice.model;

import javax.persistence.*;
import java.util.List;
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
    private Set<String> dimensions;

    @OneToMany(mappedBy = "shapeCategory")
    private List<Shape> shapes;

    private String formula;

    public ShapeCategory() {
    }

    public ShapeCategory(String shapeCategoryName, Set<String> dimensions, List<Shape> shapes, String formula) {
        this.shapeCategoryName = shapeCategoryName;
        this.dimensions = dimensions;
        this.shapes = shapes;
        this.formula = formula;
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
}
