package com.vaishnavi.devpilot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Full name is reqiured")
    private String fullName;

    @NotBlank(message = "Username is required")
    private String username;

    public String getFullName(String fullName){
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername(String username){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
