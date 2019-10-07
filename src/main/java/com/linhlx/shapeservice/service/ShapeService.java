package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.model.ShapeCategory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShapeService {

    List<ShapeCategory> getAllShapeCategory();

}
