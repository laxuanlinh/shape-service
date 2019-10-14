package com.linhlx.shapeservice.model;

import org.junit.Test;

import static java.lang.Long.valueOf;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RoleTest {

    private Role role;

    @Test
    public void shouldCreateRole(){
        whenCreateRole();
        roleShouldBeCreatedWithProperties();
    }

    public void whenCreateRole(){
        role = new Role();
        role.setId(1l);
        role.setAuthority("ROLE_USER");
        User user = new User();
        user.setUsername("username");
        role.setUser(user);
    }

    public void roleShouldBeCreatedWithProperties(){
        assertNotNull(role);
        assertEquals("ROLE_USER", role.getAuthority());
        assertEquals("username", role.getUser().getUsername());
        assertTrue(valueOf(role.getId()).equals(1l));
    }

}
