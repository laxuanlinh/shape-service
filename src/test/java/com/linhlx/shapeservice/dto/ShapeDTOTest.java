package com.linhlx.shapeservice.dto;

import com.google.common.collect.Maps;
import com.linhlx.shapeservice.model.Shape;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ShapeDTOTest {

    private ShapeDTO shapeDTO;

    @Test
    public void shouldCreateShapeDTOUsingConstructor(){
        whenCreateShapeDTOUsingConstructor();
        shapeDTOShouldBeCreatedWithProperties();
    }

    @Test
    public void shouldCreateShapeDTOUsingSetters(){
        whenCreateShapeDTOUsingSetters();
        shapeDTOShouldBeCreatedWithProperties();
    }

    private void whenCreateShapeDTOUsingSetters() {
        shapeDTO = new ShapeDTO();
        shapeDTO.setId(1l);
        shapeDTO.setShapeName("shape name");
        shapeDTO.setShapeCategory(new ShapeCategoryDTO());
        shapeDTO.setSizes(Maps.newHashMap());
        shapeDTO.setCreatedBy("author");
    }

    private void shapeDTOShouldBeCreatedWithProperties() {
        assertTrue(Long.valueOf(1l).equals(shapeDTO.getId()));
        assertEquals("shape name", shapeDTO.getShapeName());
        assertEquals("author", shapeDTO.getCreatedBy());
        assertNotNull(shapeDTO.getShapeCategory());
        assertNotNull(shapeDTO.getSizes());
    }

    private void whenCreateShapeDTOUsingConstructor() {
        shapeDTO = new ShapeDTO(1l, "shape name", new ShapeCategoryDTO(), Maps.newHashMap(), "author");
    }

}
