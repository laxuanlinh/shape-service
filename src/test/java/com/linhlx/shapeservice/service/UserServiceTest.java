package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.exception.UserException;
import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.User;
import com.linhlx.shapeservice.repository.RoleRepository;
import com.linhlx.shapeservice.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private List<UserDTO> userDTOs;
    private UserDTO userDTO;
    private PostedUserDTO postedUserDTO;
    private User user1;
    private User user2;
    private Role roleUser;
    private Role roleAdmin;

    @Before
    public void setUp(){
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);

        roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");
        roleAdmin = new Role();
        roleAdmin.setAuthority("ROLE_ADMIN");
        user1 = new User();
        user1.setUsername("users-1");
        user1.setRole(roleUser);
        user1.setEnabled(true);
        user2 = new User();
        user2.setUsername("users-2");
        user2.setRole(roleUser);
        user2.setEnabled(true);
        when(userRepository.findAll()).thenReturn(newArrayList(user1, user2));

        when(userRepository.save(any())).thenReturn(user1);
        when(roleRepository.save(any())).thenReturn(new Role("ROLE_USER", user1));
        when(passwordEncoder.encode("password")).thenReturn("encrypted");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user1));
        when(roleRepository.findByUsername(anyString())).thenReturn(Optional.of(roleUser));
    }

    @Test
    public void shouldGetAllUsers() throws Exception{
        whenGetAllUsers();
        shouldReturnUserDTOs();
    }

    @Test
    public void shouldCreateUser(){
        givenPostedUserDTO();
        whenCreateUser();
        shouldReturnUserDTO();
    }

    @Test
    public void shouldEditUser(){
        givenPostedUserDTOWithRoleADMIN();
        whenUpdateUser();
        shouldReturnUserDTOWithRleADMIN();
    }

    @Test
    public void shouldDisableUser(){
        whenDeleteUser();
        userShouldBeDisabled();
    }

    @Test(expected = UserException.class)
    public void shouldThrowExceptionWhenUserNotFoundWhenUpdate(){
        givenPostedUserDTO();
        givenUserCannotBeFound();
        whenUpdateUser();
    }

    @Test(expected = UserException.class)
    public void shouldThrowExceptionWhenRoleNotFoundWhenUpdate(){
        givenPostedUserDTO();
        givenUserCannotBeFound();
        whenUpdateUser();
    }

    @Test(expected = UserException.class)
    public void shouldThrowExceptionWhenUserNotFoundWhenDelete(){
        givenUserCannotBeFound();
        whenDeleteUser();
    }

    private void givenUserCannotBeFound() {
        when(userRepository.findById(anyString())).thenReturn(empty());
    }

    private void givenRoleCannotBeFound() {
        when(roleRepository.findByUsername(anyString())).thenReturn(empty());
    }

    private void userShouldBeDisabled() {
        assertFalse(userDTO.getEnabled());
    }

    private void whenDeleteUser() {
        userDTO = userService.deleteUser("users-1");
    }

    private void shouldReturnUserDTOWithRleADMIN() {
        assertEquals("users-1", userDTO.getUsername());
        assertEquals("ROLE_ADMIN", userDTO.getRole());
        assertTrue(userDTO.getEnabled());
    }

    private void givenPostedUserDTOWithRoleADMIN() {
        postedUserDTO = new PostedUserDTO("users-1", "password", "ROLE_ADMIN");
        when(roleRepository.save(any())).thenReturn(roleAdmin);
    }

    private void whenUpdateUser() {
        userDTO = userService.updateUser(postedUserDTO);
    }

    private void givenPostedUserDTO() {
        postedUserDTO = new PostedUserDTO("users-1", "password", "ROLE_USER");
    }

    private void whenCreateUser() {
        userDTO = userService.createUser(postedUserDTO);
    }

    private void shouldReturnUserDTO() {
        assertEquals("users-1", userDTO.getUsername());
        assertEquals("ROLE_USER", userDTO.getRole());
        assertTrue(userDTO.getEnabled());
    }

    private void shouldReturnUserDTOs() {
        assertEquals(2, userDTOs.size());
    }

    private void whenGetAllUsers() {
        userDTOs = userService.getAllUsers();
    }



}





















