package com.linhlx.shapeservice.repository;

import com.linhlx.shapeservice.model.ShapeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Set;

@Repository
public interface ShapeCategoryRepository extends JpaRepository<ShapeCategory, String> {

    @Query("SELECT distinct sc from ShapeCategory sc " +
            "join sc.dimensions d " +
            "where d in (:dimensions) " +
            "and size(d) = :#{#dimensions.size()}")
    List<ShapeCategory> getShapeCategoryByDimensions(@Param("dimensions") Set<String> dimensions);

}
