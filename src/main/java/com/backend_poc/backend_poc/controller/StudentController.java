package com.backend_poc.backend_poc.controller;
 
import com.backend_poc.backend_poc.config.JwtUtil;
import com.backend_poc.backend_poc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/student")
public class StudentController {
 
    @Autowired private StudentService studentService;
    @Autowired private JwtUtil jwtUtil;
 
    private Long getUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractUserId(token);
    }
 
    @GetMapping("/courses")
    public ResponseEntity<?> getEnrolledCourses(
            @RequestHeader("Authorization") String authHeader) {
        Long studentId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(studentService.getEnrolledCourses(studentId));
    }
 
    @PostMapping("/lesson/{lessonId}/complete")
    public ResponseEntity<?> markComplete(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long lessonId) {
        Long studentId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(studentService.markLessonComplete(studentId, lessonId));
    }
 
    @GetMapping("/courses/available")
    public ResponseEntity<?> getAvailableCourses(
            @RequestHeader("Authorization") String authHeader) {
        Long studentId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(studentService.getAvailableCourses(studentId));
    }
}