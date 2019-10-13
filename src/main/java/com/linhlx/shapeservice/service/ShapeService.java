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
    List<ShapeDTO> getAllShapesForUser(String username);
    Shape saveShape(Shape shape);
    AreaDTO getArea(Shape shape);
    Long deleteShape(Long id);

    List<ShapeCategoryDTO> getAllShapeCategories();
    List<String> getOtherCategories(Shape editedShape);
    ShapeCategoryDTO createCategory(ShapeCategory shapeCategory);

}
