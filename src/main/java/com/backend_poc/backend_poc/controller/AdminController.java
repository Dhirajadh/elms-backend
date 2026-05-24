package com.backend_poc.backend_poc.controller;
 
import com.backend_poc.backend_poc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/admin")
public class AdminController {
 
    @Autowired private AdminService adminService;
 
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
 
    @GetMapping("/users/role/{role}")
    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(adminService.getUsersByRole(role));
    }
 
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentDetail(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(adminService.getStudentDetail(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
 
    @PutMapping("/courses/{courseId}/toggle")
    public ResponseEntity<?> toggleCourse(@PathVariable Long courseId) {
        try {
            return ResponseEntity.ok(adminService.toggleCourseStatus(courseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}