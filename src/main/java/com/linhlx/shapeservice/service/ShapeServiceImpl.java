package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.model.ShapeCategory;
import com.linhlx.shapeservice.repository.ShapeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeServiceImpl implements ShapeService {

    private final ShapeCategoryRepository shapeCategoryRepository;

    @Autowired
    public ShapeServiceImpl(ShapeCategoryRepository shapeCategoryRepository) {
        this.shapeCategoryRepository = shapeCategoryRepository;
    }

    @Override
    public List<ShapeCategory> getAllShapeCategory() {
        return shapeCategoryRepository.findAll();
    }
}
