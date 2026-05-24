package com.backend_poc.backend_poc.entity;
 
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
 
@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "student_id")
    private Long studentId;
 
    @Column(name = "course_id")
    private Long courseId;
 
    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;
}