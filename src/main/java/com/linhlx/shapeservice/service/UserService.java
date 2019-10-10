package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO createUser(PostedUserDTO postedUserDTO);
    UserDTO updateUser(PostedUserDTO postedUserDTO);
    UserDTO deleteUser(String username);

}
