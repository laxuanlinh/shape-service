package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserDTO createUser(@RequestBody PostedUserDTO postedUserDTO){
        return userService.createUser(postedUserDTO);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserDTO updateUser(@RequestBody PostedUserDTO postedUserDTO){
        return userService.updateUser(postedUserDTO);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserDTO deleteUser(@RequestBody UserDTO userDTO){
        return userService.deleteUser(userDTO);
    }
}
