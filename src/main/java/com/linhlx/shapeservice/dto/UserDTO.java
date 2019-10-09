package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.User;

public class UserDTO {

    private String username;
    private String role;

    public UserDTO(User user, Role role){
        this.username = user.getUsername();
        this.role = role.getName();
    }

    public UserDTO() {
    }

    public UserDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
