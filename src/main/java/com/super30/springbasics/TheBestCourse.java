package com.super30.springbasics;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "course")
public class TheBestCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name is mandatory")
    @Size(min = 2, max = 50, message = "Course name must be between 2 and 50 characters")
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private TheBestStudent student;

    public TheBestCourse(String name, TheBestStudent student) {
        this.name = name;
        this.student = student;
    }

    public TheBestCourse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TheBestStudent getStudent() {
        return student;
    }

    public void setStudent(TheBestStudent student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "TheBestCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", student=" + student +
                '}';
    }
}
