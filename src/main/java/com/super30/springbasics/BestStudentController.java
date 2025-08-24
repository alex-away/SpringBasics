package com.super30.springbasics;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v3/students")
public class BestStudentController {

    private List<BestStudent> students = new ArrayList<>();
    private Long nextId = 1L;

    // GET - Retrieve all students (200 OK)
    @GetMapping("/all")
    public ResponseEntity<List<BestStudent>> getAllStudents() {
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(students); // 200
    }

    // POST - Create new student (201 Created)
    @PostMapping("/add")
    public ResponseEntity<?> createStudent(@Valid @RequestBody BestStudent student, BindingResult result) {

        if (result.hasErrors()) {
            return buildValidationErrorResponse(result);
        }

        student.setId(nextId.intValue());
        nextId++;
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully"); // 201
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@Valid @RequestBody BestStudent student, @PathVariable int id) {

        for (BestStudent bestStudent : students) {
            if (bestStudent.getId() == id) {
                bestStudent.setName(student.getName());
                bestStudent.setAge(student.getAge());
                bestStudent.setEmail(student.getEmail());
                return ResponseEntity.ok("Student updated successfully"); // 200
            }
        }

        return ResponseEntity.notFound().build(); // 404
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        boolean removed = students.removeIf(student -> student.getId() == id);

        if (removed) {
            return ResponseEntity.ok("Student deleted successfully"); // 200
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    private ResponseEntity<Map<String, String>> buildValidationErrorResponse(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
