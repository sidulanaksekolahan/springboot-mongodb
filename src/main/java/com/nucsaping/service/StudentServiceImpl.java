package com.nucsaping.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nucsaping.dto.StudentRequestDTO;
import com.nucsaping.dto.StudentResponseDTO;
import com.nucsaping.entity.Student;
import com.nucsaping.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              ObjectMapper objectMapper) {
        this.studentRepository = studentRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public void createStudent(StudentRequestDTO studentRequestDTO) {

        // Create student object and convert DTO to entity
        Student student = new Student();
        student.setFirstName(studentRequestDTO.getFirstName());
        student.setLastName(studentRequestDTO.getLastName());
        student.setEmail(studentRequestDTO.getEmail());

        // Save the student to database
        studentRepository.save(student);
    }

    @Override
    public StudentResponseDTO getStudentById(String id) {

        // Find student by id, if not found throw exception
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Convert entity to DTO
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setFirstName(student.getFirstName());
        studentResponseDTO.setLastName(student.getLastName());
        studentResponseDTO.setEmail(student.getEmail());

        // Return the response DTO
        return studentResponseDTO;
    }

    @Transactional
    @Override
    public void updateStudent(String id, StudentRequestDTO studentRequestDTO) {

        // Find student by id, if not found throw exception
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Update the found student with the new values from the request DTO
        student.setFirstName(studentRequestDTO.getFirstName());
        student.setLastName(studentRequestDTO.getLastName());
        student.setEmail(studentRequestDTO.getEmail());

        // Save the updated student to database
        studentRepository.save(student);
    }

    @Transactional
    @Override
    public void deleteStudent(String id) {

        // Find student by id, if not found throw exception
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Delete the found student
        studentRepository.delete(student);
    }

    @Transactional
    @Override
    public void patchStudent(String id, Map<String, Object> patchPayload) throws JsonMappingException {

        // Find student by id, if not found throw exception
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Security check - prevent ID modifications
        if (patchPayload.containsKey("id")) {
            throw new RuntimeException(
                    "Student id cannot be modified. Remove 'id' from request body."
            );
        }

        // Apply the partial update
        Student patchStudent = objectMapper.updateValue(student, patchPayload);

        // save the updated student to database
        studentRepository.save(patchStudent);
    }
}
