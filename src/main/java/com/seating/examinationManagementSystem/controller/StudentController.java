package com.seating.examinationManagementSystem.controller;


import com.seating.examinationManagementSystem.dto.CreateResponseDto;
import com.seating.examinationManagementSystem.dto.StudentRequestDto;
import com.seating.examinationManagementSystem.dto.StudentResponseDto;
import com.seating.examinationManagementSystem.dto.StudentScoreResponseDto;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import com.seating.examinationManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/examinationmanagementsystem/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<CreateResponseDto> addStudent(@RequestBody StudentRequestDto studentDTO) {
        CreateResponseDto response=studentService.addStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/getStudentByName")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents( @RequestParam(name = "studentName",required = false) String studentName) {

        List<StudentResponseDto> students = studentService.getStudentsByName(studentName);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getStudentByRollNo")
    public ResponseEntity<StudentScoreResponseDto> getstudentByRollNo(@RequestParam(name = "studentRollNo") Long studentrollNo) throws NotFoundException {

        StudentScoreResponseDto students = studentService.getStudentByRollNo(studentrollNo);
        return ResponseEntity.ok(students);
    }

}
