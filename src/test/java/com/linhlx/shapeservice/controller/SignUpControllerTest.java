package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.dto.UserDetailsDTO;
import com.linhlx.shapeservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignUpControllerTest {

    private SignUpController signUpController;
    private UserService userService;
    private String viewName;
    private ResponseEntity responseEntity;

    @Before
    public void setUp(){
        userService = mock(UserService.class);
        signUpController = new SignUpController(userService);
        when(userService.signUp(any())).thenReturn(new UserDTO());
    }

    @Test
    public void shouldReturnViewName(){
        whenRequestViewSignUp();
        shouldReturnView();
    }

    @Test
    public void shouldReturnResponseEntityWhenPostSignUp(){
        whenPostSignUp();
        shouldReturnResponseEntity();
    }

    private void whenPostSignUp() {
        responseEntity = signUpController.signUp(new UserDetailsDTO());
    }

    private void shouldReturnResponseEntity(){
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    private void shouldReturnView() {
        assertEquals("signup", viewName);
    }

    private void whenRequestViewSignUp() {
        viewName = signUpController.signUp();
    }



}
