package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.service.ShapeService;
import com.linhlx.shapeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/shapes")
public class AdminShapeController {

    private final ShapeService shapeService;
    private final UserService userService;

    @Autowired
    public AdminShapeController(ShapeService shapeService, UserService userService) {
        this.shapeService = shapeService;
        this.userService = userService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String shapes(Model model){
        model.addAttribute("shapes", shapeService.getAllShapes());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("categories", shapeService.getAllShapeCategories());
        return "adminShapes";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Long> createShape(@RequestBody Shape shape){
        Shape createdShape = shapeService.createShapeForOtherUser(shape);
        return new ResponseEntity<>(createdShape.getId(), HttpStatus.CREATED);
    }

}
