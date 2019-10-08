package com.linhlx.shapeservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
public class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shape_id")
    private Long id;

    @NotEmpty(message = "Shape name cannot be empty")
    private String shapeName;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="name")
    @Column(name = "size")
    @CollectionTable(name = "size", joinColumns = @JoinColumn(name = "shape_id"))
    @NotNull(message = "Sizes cannot be null")
    private Map<String, Double> sizes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shape_category_name")
    @NotNull(message = "Shape category cannot be null")
    private ShapeCategory shapeCategory;

    public Shape() {
    }

    public Shape(Long id, String shapeName, Map<String, Double> sizes, ShapeCategory shapeCategory) {
        this.id = id;
        this.shapeName = shapeName;
        this.sizes = sizes;
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

    public Map<String, Double> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, Double> sizes) {
        this.sizes = sizes;
    }
}
