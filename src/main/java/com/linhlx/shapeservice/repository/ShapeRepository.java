package com.linhlx.shapeservice.repository;

import com.linhlx.shapeservice.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {

}
