package com.springsecurity.controller;

import com.springsecurity.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private static final List<Student> STUDENTS = Arrays.asList(new Student(1, "Student1"),
            new Student(2, "Student2"),
            new Student(3, "Student3"));

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Integer studentId) {
        return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No student present with this studentId : " + studentId));
    }
}
