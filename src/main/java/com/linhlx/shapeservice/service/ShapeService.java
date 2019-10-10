package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.AreaDTO;
import com.linhlx.shapeservice.dto.ShapeCategoryDTO;
import com.linhlx.shapeservice.dto.ShapeDTO;
import com.linhlx.shapeservice.model.Shape;
import com.linhlx.shapeservice.model.ShapeCategory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

public interface ShapeService {

    List<ShapeDTO> getAllShapes();
    Shape createShape(Shape shape, String username);
    List<ShapeCategoryDTO> getAllShapeCategories();
    AreaDTO getArea(Shape shape);

}
