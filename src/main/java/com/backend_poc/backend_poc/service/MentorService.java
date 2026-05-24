package com.backend_poc.backend_poc.service;
 
import com.backend_poc.backend_poc.dto.CourseRequest;
import com.backend_poc.backend_poc.dto.EnrollRequest;
import com.backend_poc.backend_poc.entity.*;
import com.backend_poc.backend_poc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
 
@Service
public class MentorService {
 
    @Autowired private CourseRepository courseRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private MentorStudentRepository mentorStudentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private LessonProgressRepository lessonProgressRepository;
 
    public Course createCourse(Long mentorId, CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setMentorId(mentorId);
        course.setIsActive(true);
        course.setCreatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }
 
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
 
    public List<Course> getMyCourses(Long mentorId) {
        return courseRepository.findByMentorId(mentorId);
    }
 
    public List<Map<String, Object>> getMyStudentsProgress(Long mentorId) {
        List<MentorStudent> mappings = mentorStudentRepository.findByMentorId(mentorId);
        List<Map<String, Object>> result = new ArrayList<>();
 
        for (MentorStudent ms : mappings) {
            User student = userRepository.findById(ms.getStudentId()).orElse(null);
            if (student == null) continue;
 
            List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
            List<Map<String, Object>> courseProgress = new ArrayList<>();
 
            for (Enrollment enrollment : enrollments) {
                Course course = courseRepository.findById(enrollment.getCourseId()).orElse(null);
                if (course == null) continue;
 
                List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndex(course.getId());
                List<LessonProgress> progress = lessonProgressRepository.findByStudentId(student.getId());
 
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
 
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("studentId", student.getId());
            studentData.put("studentName", student.getName());
            studentData.put("email", student.getEmail());
            studentData.put("courses", courseProgress);
            result.add(studentData);
        }
        return result;
    }
 
    public Enrollment assignCourseToStudent(EnrollRequest request) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(
                request.getStudentId(), request.getCourseId())) {
            throw new RuntimeException("Student already enrolled");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(request.getStudentId());
        enrollment.setCourseId(request.getCourseId());
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }
}