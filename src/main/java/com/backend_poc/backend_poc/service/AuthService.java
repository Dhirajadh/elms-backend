package com.backend_poc.backend_poc.service;
 
import com.backend_poc.backend_poc.config.JwtUtil;
import com.backend_poc.backend_poc.dto.LoginRequest;
import com.backend_poc.backend_poc.dto.LoginResponse;
import com.backend_poc.backend_poc.entity.User;
import com.backend_poc.backend_poc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
@Service
public class AuthService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
 
        System.out.println("Input password: " + request.getPassword());
        System.out.println("Stored hash: " + user.getPassword());
        System.out.println("Match result: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));
 
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
 
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        return new LoginResponse(token, user.getRole(), user.getName(), user.getId());
    }
}