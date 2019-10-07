package com.linhlx.shapeservice.repository;

import com.linhlx.shapeservice.model.ShapeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeCategoryRepository extends JpaRepository<ShapeCategory, Long> {



}
