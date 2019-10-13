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

    @NotNull(message = "Sizes cannot be null")
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="name")
    @Column(name = "size")
    @CollectionTable(name = "size", joinColumns = @JoinColumn(name = "shape_id"))
    private Map<String, Double> sizes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shape_category_name")
    @NotNull(message = "Shape category cannot be null")
    private ShapeCategory shapeCategory;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    @NotNull(message = "Creator cannot be null")
    private User user;

    private Boolean enabled;

    public Shape() {
    }

    public Shape(Long id, String shapeName, Map<String, Double> sizes, ShapeCategory shapeCategory, User user, Boolean enabled) {
        this.id = id;
        this.shapeName = shapeName;
        this.sizes = sizes;
        this.shapeCategory = shapeCategory;
        this.user = user;
        this.enabled = enabled;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
