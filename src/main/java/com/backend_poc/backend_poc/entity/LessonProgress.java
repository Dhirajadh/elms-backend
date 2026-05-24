package com.backend_poc.backend_poc.entity;
 
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
 
@Data
@Entity
@Table(name = "lesson_progress")
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "student_id")
    private Long studentId;
 
    @Column(name = "lesson_id")
    private Long lessonId;
 
    private Boolean completed;
 
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}