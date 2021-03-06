package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.service.ShapeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShapeRestControllerTest {

    private ShapeRestController shapeRestController;
    private ShapeService shapeService;
    private ResponseEntity entity;
    private Principal principal;

    @Before
    public void setUp(){
        shapeService = mock(ShapeService.class);
        shapeRestController = new ShapeRestController(shapeService);
        principal = mock(Principal.class);

        when(shapeService.getAllShapes()).thenReturn(newArrayList());
        when(shapeService.getAllShapeCategories()).thenReturn(newArrayList());
        when(shapeService.getArea(any())).thenReturn(new AreaDTO());
        when(shapeService.saveShape(any())).thenReturn(new Shape());
        when(shapeService.getOtherCategories(any())).thenReturn(newArrayList());
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

    @Test
    public void shouldReturnOKWhenGetOtherCategories(){
        whenGetOtherCategories();
        shouldReturnResponseEntity();
    }

    private void whenGetOtherCategories() {
        entity = shapeRestController.getOtherCategories(new Shape());
    }

    private void whenCreateShape() {
        entity = shapeRestController.createShape(new Shape(), principal);
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
