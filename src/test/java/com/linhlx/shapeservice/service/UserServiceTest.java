package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.dto.UserDetailsDTO;
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
    private List<UserDTO> userDTOS;
    private UserDTO userDTO;
    private List<UserDetailsDTO> userDetailsDTOS;
    private UserDetailsDTO userDetailsDTO;
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

        setUpUser();
        setUpWhen();
    }

    private void setUpUser(){
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
    }

    private void setUpWhen(){
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
        givenUserDetailsDTO();
        givenUserNotExists();
        whenCreateUser();
        shouldReturnUserDTO();
    }

    @Test(expected = UserException.class)
    public void shouldThrowExceptionWhenUserExistsWhenCreate(){
        givenUserDetailsDTO();
        givenUserExists();
        givenUserExists();
        whenCreateUser();
    }

    @Test
    public void shouldEditUser(){
        givenUserDetailsDTOWithRoleADMIN();
        givenReturnRoleAdminWhenSaveAdmin();
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
        givenUserDetailsDTO();
        givenUserCannotBeFound();
        whenUpdateUser();
    }

    @Test(expected = UserException.class)
    public void shouldThrowExceptionWhenUserNotFoundWhenDelete(){
        givenUserCannotBeFound();
        whenDeleteUser();
    }

    @Test
    public void shouldSignUp(){
        givenSignUpUser();
        givenUserNotExists();
        whenSignUp();
        shouldReturnUserDTO();
    }

    private void givenUserExists() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user1));
    }

    private void whenSignUp() {
        userDTO = userService.signUp(userDetailsDTO);
    }

    private void givenSignUpUser() {
        userDetailsDTO = new UserDetailsDTO("users-3", "password", null, null);
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

    private void givenUserDetailsDTOWithRoleADMIN() {
        userDetailsDTO = new UserDetailsDTO("users-1", "password", "ROLE_ADMIN", true);
    }

    private void givenReturnRoleAdminWhenSaveAdmin(){
        when(roleRepository.save(any())).thenReturn(roleAdmin);
    }

    private void whenUpdateUser() {
        userDTO = userService.updateUser(userDetailsDTO);
    }

    private void givenUserDetailsDTO() {
        userDetailsDTO = new UserDetailsDTO("users-1", "password", "ROLE_USER", true);
    }

    private void whenCreateUser() {
        userDTO = userService.createUser(userDetailsDTO);
    }

    private void givenUserNotExists(){
        when(userRepository.findById(any())).thenReturn(empty());
    }

    private void shouldReturnUserDTO() {
        assertEquals("users-1", userDTO.getUsername());
        assertEquals("ROLE_USER", userDTO.getRole());
        assertTrue(userDTO.getEnabled());
    }

    private void shouldReturnUserDTOs() {
        assertEquals(2, userDTOS.size());
    }

    private void whenGetAllUsers() {
        userDTOS = userService.getAllUsers();
    }



}





















