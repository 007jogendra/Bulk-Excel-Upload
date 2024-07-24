package com.techrev.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techrev.entity.Student;
import com.techrev.service.StudentService;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public ResponseEntity<List<Student>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Student> students = studentService.saveStudentsFromExcel(file);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qualification/{qualification}")
    public ResponseEntity<List<Student>> getStudentsByQualification(@PathVariable String qualification)
    {
        List<Student> student = studentService.getStudentsByQualification(qualification);
        if (student != null && !student.isEmpty()) 
        {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<Student>> getStudentAge(@PathVariable int age) {
        List<Student> student = studentService.getStudentByAge(age);
        if (student != null && !student.isEmpty()) {
            return new ResponseEntity<>(student, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/report")
    public ResponseEntity<List<Student>> getStudentsReport(
        @RequestParam String collegeName,
        @RequestParam String qualification,
        @RequestParam String branch,
        @RequestParam String order) {
        
        List<Student> students = studentService.getStudentsReport(collegeName, qualification, branch, order);
        if (students != null && !students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
