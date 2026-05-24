package com.backend_poc.backend_poc.entity;
 
import jakarta.persistence.*;
import lombok.Data;
 
@Data
@Entity
@Table(name = "mentor_students")
public class MentorStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "mentor_id")
    private Long mentorId;
 
    @Column(name = "student_id")
    private Long studentId;
}