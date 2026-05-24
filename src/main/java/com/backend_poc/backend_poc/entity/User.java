package com.backend_poc.backend_poc.entity;
 
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
 
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
 
    @Column(unique = true)
    private String email;
 
    private String password;
 
    private String role; // STUDENT, MENTOR, ADMIN
 
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}