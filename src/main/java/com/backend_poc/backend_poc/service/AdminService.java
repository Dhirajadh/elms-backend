package com.backend_poc.backend_poc.service;
 
import com.backend_poc.backend_poc.entity.*;
import com.backend_poc.backend_poc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
 
@Service
public class AdminService {
 
    @Autowired private UserRepository userRepository;
    @Autowired private MentorStudentRepository mentorStudentRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private LessonProgressRepository lessonProgressRepository;
 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
 
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
 
    public Map<String, Object> getStudentDetail(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
 
        List<MentorStudent> mentorMappings = mentorStudentRepository.findByStudentId(studentId);
        List<Map<String, Object>> mentors = new ArrayList<>();
 
        for (MentorStudent ms : mentorMappings) {
            userRepository.findById(ms.getMentorId()).ifPresent(mentor -> {
                Map<String, Object> m = new HashMap<>();
                m.put("mentorId", mentor.getId());
                m.put("mentorName", mentor.getName());
                mentors.add(m);
            });
        }
 
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<Map<String, Object>> courseProgress = new ArrayList<>();
 
        for (Enrollment enrollment : enrollments) {
            Course course = courseRepository.findById(enrollment.getCourseId()).orElse(null);
            if (course == null) continue;
 
            List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndex(course.getId());
            List<LessonProgress> progress = lessonProgressRepository.findByStudentId(studentId);
 
            long completed = progress.stream()
                    .filter(p -> p.getCompleted() && lessons.stream()
                            .anyMatch(l -> l.getId().equals(p.getLessonId())))
                    .count();
 
            Map<String, Object> cp = new HashMap<>();
            cp.put("courseTitle", course.getTitle());
            cp.put("totalLessons", lessons.size());
            cp.put("completedLessons", completed);
            courseProgress.add(cp);
        }
 
        Map<String, Object> result = new HashMap<>();
        result.put("studentId", student.getId());
        result.put("name", student.getName());
        result.put("email", student.getEmail());
        result.put("mentors", mentors);
        result.put("courseProgress", courseProgress);
        return result;
    }
 
    public Course toggleCourseStatus(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setIsActive(!course.getIsActive());
        return courseRepository.save(course);
    }
}