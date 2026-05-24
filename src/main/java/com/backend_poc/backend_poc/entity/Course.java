package com.backend_poc.backend_poc.entity;
 
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
 
@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String title;
    private String description;
 
    @Column(name = "mentor_id")
    private Long mentorId;
 
    @Column(name = "is_active")
    private Boolean isActive;
 
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}