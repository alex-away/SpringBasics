package com.super30.springbasics;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v4/courses")
public class TheBestCourseController {

    @Autowired
    private TheBestCourseRepository theBestCourseRepository;

    // GET - Retrieve all courses (200 OK)
    @GetMapping("/all")
    public ResponseEntity<List<TheBestCourse>> getAllCourses() {
        List<TheBestCourse> courses = theBestCourseRepository.findAll();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(courses); // 200
    }

    // POST - Create new course (201 Created)
    @PostMapping("/add")
    public ResponseEntity<TheBestCourse> createCourse(@Valid @RequestBody TheBestCourse course) {
        TheBestCourse savedCourse = theBestCourseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TheBestCourse> updateCourse(@PathVariable Long id, @Valid @RequestBody TheBestCourse courseDetails) {
        Optional<TheBestCourse> courseOptional = theBestCourseRepository.findById(id);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        TheBestCourse existingCourse = courseOptional.get();
        existingCourse.setName(courseDetails.getName());
        existingCourse.setStudent(courseDetails.getStudent());

        TheBestCourse updatedCourse = theBestCourseRepository.save(existingCourse);
        return ResponseEntity.ok(updatedCourse); // 200
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        if (!theBestCourseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        theBestCourseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
