package com.linhlx.shapeservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Shape {

    @Id
    @GeneratedValue
    @Column(name = "shape_id")
    private Long id;
    private String shapeName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ShapeCategory shapeCategory;

    public Shape() {
    }

    public Shape(Long id, String shapeName, ShapeCategory shapeCategory) {
        this.id = id;
        this.shapeName = shapeName;
        this.shapeCategory = shapeCategory;
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

    public ShapeCategory getShapeCategory() {
        return shapeCategory;
    }

    public void setShapeCategory(ShapeCategory shapeCategory) {
        this.shapeCategory = shapeCategory;
    }
}
