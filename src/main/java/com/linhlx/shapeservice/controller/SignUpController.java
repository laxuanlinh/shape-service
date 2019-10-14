package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.UserDetailsDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/signup")
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String signUp(){
        return "signup";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDetailsDTO userDetailsDTO){
        return new ResponseEntity<>(userService.signUp(userDetailsDTO), HttpStatus.CREATED);
    }

}
