package com.nucsaping.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.nucsaping.dto.StudentRequestDTO;
import com.nucsaping.dto.StudentResponseDTO;

import java.util.Map;

public interface StudentService {

    void createStudent(StudentRequestDTO studentRequestDTO);

    StudentResponseDTO getStudentById(String id);

    void updateStudent(String id, StudentRequestDTO studentRequestDTO);

    void deleteStudent(String id);

    void patchStudent(String id, Map<String, Object> patchPayload) throws JsonMappingException;
}
