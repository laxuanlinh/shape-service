package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.exception.ShapeException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.*;
public class CustomExceptionHandlerTest {

    private CustomExceptionHandler customExceptionHandler;
    private HttpServletRequest httpRequest;
    private ResponseEntity<CustomExceptionHandler.ErrorResponse> responseEntity;

    @Before
    public void setUp(){
        customExceptionHandler = new CustomExceptionHandler();
        httpRequest = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void shouldReturnError(){
        whenBadRequestHappens();
        shouldReturnErrorResponse();
    }

    private void whenBadRequestHappens() {
        Exception exception = new ShapeException("Shape is invalid");
        responseEntity = customExceptionHandler.handleBadRequest(httpRequest, exception);
    }

    private void shouldReturnErrorResponse(){
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Shape is invalid", responseEntity.getBody().getMessage());
    }

}
