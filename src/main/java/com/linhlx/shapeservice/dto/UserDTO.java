package com.linhlx.shapeservice.dto;

import com.linhlx.shapeservice.model.User;

public class UserDTO {

    private String username;
    private String role;

    private Boolean enabled;

    public UserDTO(User user){
        this.username = user.getUsername();
        this.role = user.getRole().getAuthority();
        this.enabled = user.getEnabled();
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
