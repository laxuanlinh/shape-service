package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.service.ShapeService;
import com.linhlx.shapeservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static com.google.common.collect.Lists.newArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;

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
        when(shapeService.createShapeForOtherUser(any())).thenReturn(createdShape);
    }

    @Test
    public void shouldReturnView(){
        whenRequestShapesView();
        shouldReturnShapesView();
    }

    @Test
    public void shouldReturnCreatedShapeId(){
        whenCreateShape();
        shouldReturnId();
    }

    private void shouldReturnId() {
        assertEquals(CREATED, responseEntity.getStatusCode());
        assertEquals(1l, responseEntity.getBody());
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
