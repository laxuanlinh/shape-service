package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO createUser(PostedUserDTO postedUserDTO);
    UserDTO updateUser(PostedUserDTO postedUserDTO);
    UserDTO deleteUser(UserDTO user);

}
