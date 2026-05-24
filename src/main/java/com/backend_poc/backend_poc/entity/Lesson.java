package com.backend_poc.backend_poc.entity;
 
import jakarta.persistence.*;
import lombok.Data;
 
@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "course_id")
    private Long courseId;
 
    private String title;
    private String content;
 
    @Column(name = "order_index")
    private Integer orderIndex;
}