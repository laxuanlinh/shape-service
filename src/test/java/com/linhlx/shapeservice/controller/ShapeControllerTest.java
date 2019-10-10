package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.service.ShapeService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShapeControllerTest {

    private ShapeController shapeController;
    private ShapeService shapeService;
    private String viewName;
    private Model model;
    private Principal principal;

    @Before
    public void setUp(){
        shapeService = mock(ShapeService.class);
        model = mock(Model.class);
        principal = mock(Principal.class);
        shapeController = new ShapeController(shapeService);

        when(shapeService.getAllShapesForUser(anyString())).thenReturn(Lists.newArrayList());
    }

    @Test
    public void shouldReturnView(){
        whenRequestShapesView();
        shouldReturnShapeView();
    }

    private void whenRequestShapesView() {
        viewName = shapeController.shapes(model, principal);
    }

    private void shouldReturnShapeView(){
        assertEquals("shapes", viewName);
    }

}
