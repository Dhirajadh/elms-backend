package com.backend_poc.backend_poc.dto;
 
import lombok.Data;
 
@Data
public class LoginRequest {
    private String email;
    private String password;
}