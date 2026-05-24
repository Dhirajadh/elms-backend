package com.backend_poc.backend_poc.service;
 
import com.backend_poc.backend_poc.entity.*;
import com.backend_poc.backend_poc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
 
@Service
public class StudentService {
 
    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private LessonProgressRepository lessonProgressRepository;
 
    public List<Map<String, Object>> getEnrolledCourses(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<Map<String, Object>> result = new ArrayList<>();
 
        for (Enrollment enrollment : enrollments) {
            Course course = courseRepository.findById(enrollment.getCourseId()).orElse(null);
            if (course == null) continue;
 
            List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndex(course.getId());
            List<LessonProgress> progress = lessonProgressRepository.findByStudentId(studentId);
 
            long completed = progress.stream()
                    .filter(p -> p.getCompleted() && lessons.stream()
                            .anyMatch(l -> l.getId().equals(p.getLessonId())))
                    .count();
 
            Map<String, Object> courseData = new HashMap<>();
            courseData.put("courseId", course.getId());
            courseData.put("title", course.getTitle());
            courseData.put("description", course.getDescription());
            courseData.put("totalLessons", lessons.size());
            courseData.put("completedLessons", completed);
            courseData.put("lessons", lessons);
            result.add(courseData);
        }
        return result;
    }
 
    public Map<String, Object> markLessonComplete(Long studentId, Long lessonId) {
        Optional<LessonProgress> existing =
                lessonProgressRepository.findByStudentIdAndLessonId(studentId, lessonId);
 
        LessonProgress progress = existing.orElse(new LessonProgress());
        progress.setStudentId(studentId);
        progress.setLessonId(lessonId);
        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());
        lessonProgressRepository.save(progress);
 
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Lesson marked as completed");
        return response;
    }
 
    public List<Course> getAvailableCourses(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<Long> enrolledIds = enrollments.stream()
                .map(Enrollment::getCourseId).toList();
 
        return courseRepository.findByIsActive(true).stream()
                .filter(c -> !enrolledIds.contains(c.getId()))
                .toList();
    }
}