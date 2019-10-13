package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("")
public class ShapeController {

    private final ShapeService shapeService;

    @Autowired
    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String shapes(Model model, Principal principal){
        model.addAttribute("shapes", shapeService.getAllShapesForUser(principal.getName()));
        return "shapes";
    }
}
