package com.springsecurity.controller;

import com.springsecurity.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Student1"),
            new Student(2, "Student2"),
            new Student(3, "Student3"));

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("POST");
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId, student));
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasRole( 'ROLE_ADMIN')")
    public void deleteStudent(Integer studentId) {
        System.out.println("DELETE");
    }
}
