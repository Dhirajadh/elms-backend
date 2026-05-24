package com.backend_poc.backend_poc.repository;
 
import com.backend_poc.backend_poc.entity.MentorStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface MentorStudentRepository extends JpaRepository<MentorStudent, Long> {
    List<MentorStudent> findByMentorId(Long mentorId);
    List<MentorStudent> findByStudentId(Long studentId);
}