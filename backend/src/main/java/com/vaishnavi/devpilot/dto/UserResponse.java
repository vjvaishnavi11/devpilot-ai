package com.vaishnavi.devpilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String role;
}
