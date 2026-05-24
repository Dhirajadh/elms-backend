package com.backend_poc.backend_poc.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
 
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String role;
    private String name;
    private Long userId;
}
