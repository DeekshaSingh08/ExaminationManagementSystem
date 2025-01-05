package com.seating.examinationManagementSystem.service;
import com.seating.examinationManagementSystem.dto.*;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {

    void initSubjects();

    List<SubjectResponseDto> getAllSubjects();

    StudentScoreResponseDto addSubjectsToStudent(SubjectStudentRequestDto subjectStudentRequestDto) throws NotFoundException;

    ScoreAllocationResponseDto scoreAllocation(PrincipalScoreAllomentDto principalScoreAllomentDto) throws NotFoundException;
}