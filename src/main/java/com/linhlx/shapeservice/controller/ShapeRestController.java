package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/shapes")
public class ShapeRestController {

    private final ShapeService shapeService;

    @Autowired
    public ShapeRestController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Shape>> getAllShapes(){
        return new ResponseEntity(shapeService.getAllShapes(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<List<Shape>> createShape(@RequestBody Shape shape, Principal currentUser){
        Shape createdShape = shapeService.createShape(shape, currentUser.getName());
        return new ResponseEntity(createdShape.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<ShapeCategoryDTO>> getAllShapeCategories(){
        return new ResponseEntity(shapeService.getAllShapeCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/area", method = RequestMethod.POST)
    public ResponseEntity<AreaDTO> calculateArea(@RequestBody Shape shape){
        return new ResponseEntity(shapeService.getArea(shape), HttpStatus.OK);
    }
}
