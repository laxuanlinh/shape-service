package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.User;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UserDTOTest {

    private UserDTO userDTO;

    @Test
    public void shouldConvertUserToUserDTO(){
        whenConvertToUserDTO();
        userDTOShouldBeCreatedWithProperties();
    }

    @Test
    public void shouldCreateUserDTO(){
        whenCreateUserDTO();
        userDTOShouldBeCreated();
    }

    private void userDTOShouldBeCreated() {
        assertNotNull(userDTO);
    }

    private void whenCreateUserDTO() {
        userDTO = new UserDTO();
    }

    private void whenConvertToUserDTO() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        User user = new User("username", "password", true, role);
        userDTO = new UserDTO(user);
    }

    private void userDTOShouldBeCreatedWithProperties(){
        assertEquals("username", userDTO.getUsername());
        assertEquals("ROLE_USER", userDTO.getRole());
        assertTrue(userDTO.getEnabled());
    }

}
