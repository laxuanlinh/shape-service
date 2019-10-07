package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/shapes")
public class ShapeRestController {

    private final ShapeService shapeService;

    @Autowired
    public ShapeRestController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }


    @RequestMapping(name = "/category")
    public ResponseEntity<List<ShapeCategory>> getAllShapeCategories(){
        return new ResponseEntity(shapeService.getAllShapeCategory(), HttpStatus.OK);
    }
}
