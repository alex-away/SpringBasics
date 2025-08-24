package com.super30.springbasics;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v2/students")
public class BetterStudentController {

    List<Student> students = new ArrayList<>();

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return students;
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam String name, @RequestParam int age) {
        Student student = new Student(name, age);
        students.add(student);
        return "Student " + name + " with age " + age + " has been added";
    }

    @PutMapping("/update/name/{index}")
    public String updateStudent(@PathVariable int index, @RequestParam String name) {
        if (index >= 0 && index < students.size()) {
            Student student = students.get(index);
            student.setName(name);
            return "Student " + index + " with name " + name + " has been updated";
        } else {
            return "Invalid index";
        }
    }

    @PutMapping("/update/age/{index}")
    public String updateStudent(@PathVariable int index, @RequestParam int age) {
        if (index >= 0 && index < students.size()) {
            Student student = students.get(index);
            student.setAge(age);
            return "Student " + index + " with age " + age + " has been updated";
        } else {
            return "Invalid index";
        }
    }

    @DeleteMapping("/delete/{index}")
    public String deleteStudent(@PathVariable int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            return "Student " + index + " has been deleted";
        } else {
            return "Invalid index";
        }
    }
}
