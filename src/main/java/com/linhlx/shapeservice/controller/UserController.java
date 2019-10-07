package com.linhlx.shapeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @GetMapping("/public")
    public String getPublic(){
        return new String("private");
    }

    @GetMapping("/private")
    public String getPrivate(){
        return "Private";
    }

}
