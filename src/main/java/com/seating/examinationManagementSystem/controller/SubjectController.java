package com.seating.examinationManagementSystem.controller;

import com.seating.examinationManagementSystem.dto.*;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import com.seating.examinationManagementSystem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/examinationmanagementsystem/students/subject")
public class SubjectController {


    @Autowired
    private SubjectService subjectService;

    @PostConstruct
    public void initRoleAndUser() {
        subjectService.initSubjects();
    }

    @GetMapping("/getSubjects")
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjects() {
        List<SubjectResponseDto> response=subjectService.getAllSubjects();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/addSubjects")
    public ResponseEntity<StudentScoreResponseDto> addStudent(@RequestBody SubjectStudentRequestDto subjectStudentRequestDto) throws NotFoundException {
        StudentScoreResponseDto students = subjectService.addSubjectsToStudent(subjectStudentRequestDto);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/scoreAllocation")
    public ResponseEntity<ScoreAllocationResponseDto> addStudent(@RequestBody PrincipalScoreAllomentDto principalScoreAllomentDto) throws NotFoundException {
        ScoreAllocationResponseDto scoreAllocationResponseDto = subjectService.scoreAllocation(principalScoreAllomentDto);
        return ResponseEntity.ok(scoreAllocationResponseDto);
    }

}
