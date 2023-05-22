package com.springsecurity.controller;

import com.springsecurity.model.Student;
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
    public List<Student> getAllStuedents() {
        return STUDENTS;
    }

    @PostMapping("/")
    public boolean registerNewStudent(@RequestBody Student student) {
        return STUDENTS.add(student);
    }

    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable Integer studentId,@RequestBody Student student){
        System.out.println(String.format("%s %s", studentId,student));
    }
    @DeleteMapping("/{studentId}")
    public boolean deleteStudent(Integer studentId) {
        return STUDENTS.remove(studentId);
    }
}
