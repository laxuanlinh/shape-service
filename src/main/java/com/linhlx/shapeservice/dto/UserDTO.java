package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.User;

public class UserDTO {

    private String username;
    private String role;

    public UserDTO(User user){
        this.username = user.getUsername();
        this.role = user.getRole().getAuthority();
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
