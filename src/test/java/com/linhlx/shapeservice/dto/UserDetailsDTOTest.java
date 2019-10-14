package com.linhlx.shapeservice.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDetailsDTOTest {

    private UserDetailsDTO userDetailsDTO;

    @Test
    public void shouldCreateUserDetailsDTO(){
        whenCreateUserDetailsDTO();
        userDetailsDTOShouldBeCreatedWithProperties();
    }

    private void userDetailsDTOShouldBeCreatedWithProperties() {
        assertEquals("username", userDetailsDTO.getUsername());
        assertEquals("password", userDetailsDTO.getPassword());
        assertEquals("ROLE_USER", userDetailsDTO.getRole());
        assertTrue(userDetailsDTO.getEnabled());
    }

    private void whenCreateUserDetailsDTO() {
        userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUsername("username");
        userDetailsDTO.setPassword("password");
        userDetailsDTO.setRole("ROLE_USER");
        userDetailsDTO.setEnabled(true);
    }
}
