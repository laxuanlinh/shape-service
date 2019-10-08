package com.linhlx.shapeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "formula")
public class Formula {

    @Id
    @GeneratedValue
    private Long id;
    private String formula;

    @OneToOne
    @JoinColumn(name = "shape_category_name")
    private ShapeCategory shapeCategory;

    public Formula() {
    }

    public Formula(Long id, String formula, ShapeCategory shapeCategory) {
        this.id = id;
        this.formula = formula;
        this.shapeCategory = shapeCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public ShapeCategory getShapeCategory() {
        return shapeCategory;
    }

    public void setShapeCategory(ShapeCategory shapeCategory) {
        this.shapeCategory = shapeCategory;
    }
}
