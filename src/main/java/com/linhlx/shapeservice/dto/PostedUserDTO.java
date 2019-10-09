package com.linhlx.shapeservice.dto;

public class PostedUserDTO {

    private String username;
    private String password;
    private String role;

    public PostedUserDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public PostedUserDTO() {
    }
}
