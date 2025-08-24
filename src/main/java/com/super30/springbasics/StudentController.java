package com.super30.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    List<String> students = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public String addStudent(@RequestParam String name) {
        students.add(name);
        return "Student added: " + name;
    }

    @GetMapping("/all")
    public List<String> getAllStudents() {
        return students;
    }

    @PutMapping("/update/{index}")
    public String updateStudent(@PathVariable int index, @RequestParam String name) {
        if (index >= 0 && index < students.size()) {
            students.set(index, name);
            return "Student updated at " + index + ": " + name;
        } else {
            return "Invalid index";
        }
    }

    @DeleteMapping("/delete/{index}")
    public String deleteStudent(@PathVariable int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            return "Student deleted at " + index;
        } else {
            return "Invalid index";
        }
    }

    @GetMapping("/{index}/courses")
    public List<String> getCourses(@PathVariable int index) {
        String url = "http://SPRINGMICROSERVICE/api/v1/courses/student/" + index;
        return restTemplate.getForObject(url, List.class);
    }

    @PostMapping("/{index}/assign-course")
    public String assignCourseToStudent(@PathVariable int index, @RequestParam String course) {
        String url = "http://SPRINGMICROSERVICE/api/v1/courses/assign?studentId=" + index + "&course=" + course;
        return restTemplate.postForObject(url, null, String.class);
    }

    @DeleteMapping("/{index}/delete-course")
    public String deleteCourseFromStudent(@PathVariable int index, @RequestParam String course) {
        String url = "http://SPRINGMICROSERVICE/api/v1/courses/student/" + index + "?course=" + course;
        return  restTemplate.exchange(url, HttpMethod.DELETE, null, String.class).getBody();
    }
}
