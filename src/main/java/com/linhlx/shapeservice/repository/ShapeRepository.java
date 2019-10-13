package com.linhlx.shapeservice.repository;

import com.linhlx.shapeservice.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {
    @Query("select s from Shape s where s.user.username=:username and s.enabled=true")
    List<Shape> findAllByUsername(@Param("username") String username);

    @Override
    @Query("select s from Shape s where s.id=:id and s.enabled=true")
    Optional<Shape> findById(@Param("id") Long id);

    @Override
    @Query("select s from Shape s where s.enabled=true")
    List<Shape> findAll();
}
