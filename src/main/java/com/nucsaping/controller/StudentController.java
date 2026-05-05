package com.nucsaping.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.nucsaping.dto.StudentRequestDTO;
import com.nucsaping.dto.StudentResponseDTO;
import com.nucsaping.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody StudentRequestDTO studentRequestDTO) {

        studentService.createStudent(studentRequestDTO);

        return ResponseEntity.ok("Successfully create student");
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable("id") String id) {

        StudentResponseDTO responseDTO = studentService.getStudentById(id);

        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
    }

    // update whole object
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") String id, @RequestBody StudentRequestDTO studentRequestDTO) {

        studentService.updateStudent(id, studentRequestDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully update student");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") String id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok("Successfully delete student");
    }

    // patch only specific field/partial update
    @PatchMapping("{id}")
    public ResponseEntity<String> patchStudent(@PathVariable("id") String id,
                                               @RequestBody Map<String, Object> patchPayload) {

        try {
            studentService.patchStudent(id, patchPayload);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Successfully patch student");
    }
}
