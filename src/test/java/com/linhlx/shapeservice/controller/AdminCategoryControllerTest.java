package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.service.ShapeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AdminCategoryControllerTest {

    private AdminCategoryController adminCategoryController;
    private ShapeService shapeService;
    private String viewName;
    private Model model;
    private ResponseEntity responseEntity;

    @Before
    public void setUp(){
        shapeService = mock(ShapeService.class);
        model = mock(Model.class);
        adminCategoryController = new AdminCategoryController(shapeService);
        when(shapeService.createCategory(any())).thenReturn(new ShapeCategoryDTO());
    }

    @Test
    public void shouldReturnViewName(){
        whenRequestAdminShapes();
        shouldReturnAdminShapesViewName();
    }

    @Test
    public void shouldCreateCategory(){
        whenCreateCategory();
        shouldReturnResponseEntity();
    }

    private void shouldReturnResponseEntity() {
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    private void whenCreateCategory() {
        responseEntity = adminCategoryController.createCategory(new ShapeCategory());
    }

    private void shouldReturnAdminShapesViewName() {
        assertEquals("categories", viewName);
    }

    private void whenRequestAdminShapes() {
        viewName = adminCategoryController.categories(model);
    }

}
