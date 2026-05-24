package com.backend_poc.backend_poc.dto;
 
import lombok.Data;
 
@Data
public class EnrollRequest {
    private Long studentId;
    private Long courseId;
}