package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserDTO createUser(@RequestBody PostedUserDTO postedUserDTO){
        return userService.createUser(postedUserDTO);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public UserDTO updateUser(@RequestBody PostedUserDTO postedUserDTO){
        return userService.updateUser(postedUserDTO);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public UserDTO deleteUser(@RequestBody UserDTO userDTO){
        return userService.deleteUser(userDTO);
    }

}
