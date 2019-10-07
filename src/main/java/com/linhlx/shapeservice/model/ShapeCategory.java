package com.linhlx.shapeservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shape_category")
public class ShapeCategory {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "shape_category_name")
    private String shapeCategoryName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dimension", joinColumns = @JoinColumn(name = "shape_category_id"))
    @Column(name = "dimension")
    private List<String> dimensions;

    @OneToMany(mappedBy = "shapeCategory")
    private List<Shape> shapes;

    public ShapeCategory() {
    }

    public ShapeCategory(Long id, String shapeCategoryName, List<String> dimensions, List<Shape> shapes) {
        this.id = id;
        this.shapeCategoryName = shapeCategoryName;
        this.dimensions = dimensions;
        this.shapes = shapes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShapeCategoryName() {
        return shapeCategoryName;
    }

    public void setShapeCategoryName(String shapeCategoryName) {
        this.shapeCategoryName = shapeCategoryName;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
}
