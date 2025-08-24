package com.super30.springbasics;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v4/students")
public class TheBestStudentController {

    @Autowired
    private TheBestStudentRepository theBestStudentRepository;

    // GET - Retrieve all students (200 OK)
    @GetMapping("/all")
    public ResponseEntity<List<TheBestStudent>> getAllStudents() {

        return ResponseEntity.ok(theBestStudentRepository.findAll()); // 200
    }

    // POST - Create new student (201 Created)
    @PostMapping("/add")
    public ResponseEntity<?> createStudent(@Valid @RequestBody TheBestStudent student, BindingResult result) {

        if (result.hasErrors()) {
            return buildValidationErrorResponse(result);
        }

        theBestStudentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully"); // 201
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@Valid @RequestBody TheBestStudent student, @PathVariable int id) {

        TheBestStudent existingStudent = theBestStudentRepository.findById(id).orElse(null);

        if (existingStudent != null) {
            existingStudent.setName(student.getName());
            existingStudent.setAge(student.getAge());
            existingStudent.setEmail(student.getEmail());
            theBestStudentRepository.save(existingStudent);
            return ResponseEntity.ok("Student updated successfully"); // 200
        }

        return ResponseEntity.notFound().build(); // 404
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {

        TheBestStudent existingStudent = theBestStudentRepository.findById(id).orElse(null);

        if (existingStudent != null) {
            theBestStudentRepository.delete(existingStudent);
            return ResponseEntity.ok("Student deleted successfully"); // 200
        }

        return ResponseEntity.notFound().build(); // 404
    }

    private ResponseEntity<Map<String, String>> buildValidationErrorResponse(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
