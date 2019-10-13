package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.UserDetailsDTO;
import com.linhlx.shapeservice.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDetailsDTO userDetailsDTO);
    UserDTO updateUser(UserDetailsDTO userDetailsDTO);
    UserDTO deleteUser(String username);
    UserDTO signUp(UserDetailsDTO userDetailsDTO);
}
