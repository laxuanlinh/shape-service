package com.linhlx.shapeservice.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UserTest {

    private User user;

    @Test
    public void shouldCreateUser(){
        whenCreateUser();
        userShouldBeCreatedWithProperties();
    }

    private void userShouldBeCreatedWithProperties() {
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.getEnabled());
        assertNotNull(user.getRole());
    }

    private void whenCreateUser() {
        user = new User("username", "password", true, new Role());
    }


}
