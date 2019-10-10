package com.linhlx.shapeservice.controller;

import com.google.common.collect.Lists;
import com.linhlx.shapeservice.dto.ShapeDTO;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.service.ShapeService;
import com.linhlx.shapeservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static com.google.common.collect.Lists.newArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;

public class ShapeControllerTest {

    private ShapeController shapeController;
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
        shapeController = new ShapeController(shapeService, userService);

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
        responseEntity = shapeController.createShape(new Shape());
    }

    private void whenRequestShapesView() {
        viewName = shapeController.shapes(model);
    }

    private void shouldReturnShapesView(){
        assertEquals("shapes", viewName);
    }


}
