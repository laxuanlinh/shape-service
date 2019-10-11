package com.linhlx.shapeservice.repository;

import com.linhlx.shapeservice.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {
    @Query("select s from Shape s where s.user.username=:username")
    List<Shape> findAllByUsername(@Param("username") String username);
}
