package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.UserDetailsDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private String viewName;
    private Model model;
    private ResponseEntity responseEntity;

    @Before
    public void setUp(){
        userService = mock(UserService.class);
        userController = new UserController(userService);
        model = mock(Model.class);
        when(userService.getAllUsers()).thenReturn(newArrayList());
        when(userService.createUser(any())).thenReturn(new UserDTO());
        when(userService.updateUser(any())).thenReturn(new UserDTO());
        when(userService.deleteUser(anyString())).thenReturn(new UserDTO());
    }

    @Test
    public void shouldReturnView(){
        whenRequestViewUsers();
        shouldReturnViewUser();
    }

    @Test
    public void shouldCreateUser(){
        whenCreateUser();
        shouldReturnResponseEntity(HttpStatus.CREATED);
    }

    @Test
    public void shouldUpdateUser(){
        whenUpdateUser();
        shouldReturnResponseEntity(HttpStatus.OK);
    }

    @Test
    public void shouldDeleteUser(){
        whenDeleteUser();
        shouldReturnResponseEntity(HttpStatus.OK);
    }

    private void whenDeleteUser() {
        responseEntity = userController.deleteUser("username");
    }

    private void shouldReturnResponseEntity(HttpStatus status) {
        assertEquals(status, responseEntity.getStatusCode());
    }

    private void whenUpdateUser() {
        responseEntity = userController.updateUser(new UserDetailsDTO());
    }

    private void whenCreateUser() {
        responseEntity = userController.createUser(new UserDetailsDTO());
    }

    private void whenRequestViewUsers() {
        viewName = userController.users(model);
    }

    private void shouldReturnViewUser(){
        assertEquals("users", viewName);
    }

}
