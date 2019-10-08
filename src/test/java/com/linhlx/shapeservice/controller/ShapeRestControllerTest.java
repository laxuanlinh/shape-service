package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.service.ShapeService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShapeRestControllerTest {

    private ShapeRestController shapeRestController;
    private ShapeService shapeService;
    private ResponseEntity entity;

    @Before
    public void setUp(){
        shapeService = mock(ShapeService.class);
        shapeRestController = new ShapeRestController(shapeService);

        when(shapeService.getAllShapes()).thenReturn(Lists.newArrayList());
        when(shapeService.getAllShapeCategories()).thenReturn(Lists.newArrayList());
        when(shapeService.getArea(any())).thenReturn(new AreaDTO());
        when(shapeService.createShape(any())).thenReturn(new Shape());
    }

    @Test
    public void shouldReturnOKWhenRequestShapes(){
        whenGetAllShapes();
        shouldReturnResponseEntity();
    }

    @Test
    public void shouldReturnOKWhenRequestCategories(){
        whenGetAllCategories();
        shouldReturnResponseEntity();
    }

    @Test
    public void shouldReturnCreatedWhenCreate(){
        whenCreateShape();
        shouldReturnResponseEntityWithCreated();
    }

    @Test
    public void shouldReturnOKWhenCalculateArea(){
        whenCalculateArea();
        shouldReturnResponseEntity();
    }

    private void whenCreateShape() {
        entity = shapeRestController.createShape(new Shape());
    }

    private void shouldReturnResponseEntityWithCreated() {
        assertNotNull(entity);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    private void whenCalculateArea() {
        entity = shapeRestController.calculateArea(new Shape());
    }

    private void whenGetAllCategories() {
        entity = shapeRestController.getAllShapeCategories();
    }

    private void whenGetAllShapes(){
        entity = shapeRestController.getAllShapes();
    }

    private void shouldReturnResponseEntity(){
        assertNotNull(entity);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
