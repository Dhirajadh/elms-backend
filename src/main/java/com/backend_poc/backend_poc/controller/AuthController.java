package com.backend_poc.backend_poc.controller;
 
import com.backend_poc.backend_poc.dto.LoginRequest;
import com.backend_poc.backend_poc.dto.LoginResponse;
import com.backend_poc.backend_poc.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/auth")
public class AuthController {
 
    @Autowired
    private AuthService authService;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
 
    @GetMapping("/hash/{raw}")
    public ResponseEntity<?> getHash(@PathVariable String raw) {
        return ResponseEntity.ok(passwordEncoder.encode(raw));
    }
}