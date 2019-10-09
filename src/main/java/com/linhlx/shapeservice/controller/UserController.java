package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDTO> createUser(@RequestBody PostedUserDTO postedUserDTO){
        return new ResponseEntity<>(userService.createUser(postedUserDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> updateUser(@RequestBody PostedUserDTO postedUserDTO){
        return new ResponseEntity<>(userService.updateUser(postedUserDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        UserDTO userDTO = new UserDTO(username, null);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
