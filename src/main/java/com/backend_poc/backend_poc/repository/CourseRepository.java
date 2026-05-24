package com.backend_poc.backend_poc.repository;
 
import com.backend_poc.backend_poc.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByMentorId(Long mentorId);
    List<Course> findByIsActive(Boolean isActive);
}