package com.linhlx.shapeservice.model;

import com.google.common.collect.Maps;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ShapeTest {
    
    private Shape shape;
    
    @Test
    public void shouldCreateShape(){
        whenCreateShape();
        shapeShouldBeCreatedWithProperties();
    }

    private void shapeShouldBeCreatedWithProperties() {
        assertEquals("test shape", shape.getShapeName());
        assertTrue(shape.getEnabled());
        assertNotNull(shape.getSizes());
        assertNotNull(shape.getShapeCategory());
        assertNotNull(shape.getUser());
    }

    private void whenCreateShape() {
        shape = new Shape(1l, "test shape", Maps.newHashMap(), new ShapeCategory(), new User(), true);
    }


    
}
