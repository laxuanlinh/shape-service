package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.User;

public class UserDetailsDTO {

    private String username;
    private String password;
    private String role;
    private Boolean enabled;

    public UserDetailsDTO(String username, String password, String role, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public UserDetailsDTO(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole().getAuthority();
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

    public UserDetailsDTO() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
