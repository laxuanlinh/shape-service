package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.model.User;
import com.linhlx.shapeservice.service.ShapeService;
import com.linhlx.shapeservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static com.google.common.collect.Lists.newArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class AdminShapeControllerTest {

    private AdminShapeController adminShapeController;
    private UserService userService;
    private ShapeService shapeService;
    private Model model;
    private String viewName;
    private ResponseEntity responseEntity;

    @Before
    public void setUp(){
        userService = mock(UserService.class);
        shapeService = mock(ShapeService.class);
        model = mock(Model.class);
        adminShapeController = new AdminShapeController(shapeService, userService);
        
        when(userService.getAllUsers()).thenReturn(newArrayList());
        when(shapeService.getAllShapeCategories()).thenReturn(newArrayList());
        when(shapeService.getAllShapes()).thenReturn(newArrayList());
        Shape createdShape = new Shape();
        createdShape.setId(1l);
        createdShape.setShapeName("created shape");
        createdShape.setShapeCategory(new ShapeCategory());
        User author = new User();
        author.setUsername("author");
        createdShape.setUser(author);
        when(shapeService.saveShape(any())).thenReturn(createdShape);
        when(shapeService.deleteShape(any())).thenReturn(1l);
    }

    @Test
    public void shouldReturnView(){
        whenRequestShapesView();
        shouldReturnShapesView();
    }

    @Test
    public void shouldReturnCreatedShapeId(){
        whenCreateShape();
        shouldReturnResponseEntity(CREATED);
    }
    
    @Test
    public void shouldUpdateShape(){
        whenUpdateShape();
        shouldReturnResponseEntity(OK);
    }

    @Test
    public void shouldDeleteShape(){
        whenDeleteShape();
        shouldReturnResponseEntity(OK);
    }

    private void whenDeleteShape() {
        responseEntity = adminShapeController.deleteShape(1l);
    }

    private void whenUpdateShape() {
        responseEntity = adminShapeController.updateShape(new Shape());
    }

    private void shouldReturnResponseEntity(HttpStatus status) {
        assertEquals(status, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    private void whenCreateShape() {
        responseEntity = adminShapeController.createShape(new Shape());
    }

    private void whenRequestShapesView() {
        viewName = adminShapeController.shapes(model);
    }

    private void shouldReturnShapesView(){
        assertEquals("adminShapes", viewName);
    }


}
