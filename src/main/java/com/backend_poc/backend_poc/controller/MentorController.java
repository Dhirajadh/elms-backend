package com.backend_poc.backend_poc.controller;
 
import com.backend_poc.backend_poc.config.JwtUtil;
import com.backend_poc.backend_poc.dto.CourseRequest;
import com.backend_poc.backend_poc.dto.EnrollRequest;
import com.backend_poc.backend_poc.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/mentor")
public class MentorController {
 
    @Autowired private MentorService mentorService;
    @Autowired private JwtUtil jwtUtil;
 
    private Long getUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractUserId(token);
    }
 
    @GetMapping("/students/progress")
    public ResponseEntity<?> getStudentsProgress(
            @RequestHeader("Authorization") String authHeader) {
        Long mentorId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(mentorService.getMyStudentsProgress(mentorId));
    }
 
    @GetMapping("/courses")
    public ResponseEntity<?> getMyCourses(
            @RequestHeader("Authorization") String authHeader) {
        Long mentorId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(mentorService.getMyCourses(mentorId));
    }
 
    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CourseRequest request) {
        Long mentorId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(mentorService.createCourse(mentorId, request));
    }
 
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        mentorService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted");
    }
 
    @PostMapping("/assign")
    public ResponseEntity<?> assignCourse(@RequestBody EnrollRequest request) {
        try {
            return ResponseEntity.ok(mentorService.assignCourseToStudent(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}