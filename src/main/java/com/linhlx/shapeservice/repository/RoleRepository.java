package com.linhlx.shapeservice.repository;

import com.linhlx.shapeservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.user.username = ?1")
    Role findByUsername(String username);
}
