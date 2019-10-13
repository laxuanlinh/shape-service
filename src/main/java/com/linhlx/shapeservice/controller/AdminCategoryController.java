package com.linhlx.shapeservice.controller;

import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/categories")
public class AdminCategoryController {

    private final ShapeService shapeService;

    @Autowired
    public AdminCategoryController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String categories(Model model){
        model.addAttribute("categories", shapeService.getAllShapeCategories());
        return "categories";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ShapeCategoryDTO> createCategory(@RequestBody ShapeCategory shapeCategory){
        return new ResponseEntity<>(shapeService.createCategory(shapeCategory), HttpStatus.CREATED);
    }

}
