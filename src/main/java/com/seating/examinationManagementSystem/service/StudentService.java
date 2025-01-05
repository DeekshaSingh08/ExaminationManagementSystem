package com.seating.examinationManagementSystem.service;

import com.seating.examinationManagementSystem.dto.CreateResponseDto;
import com.seating.examinationManagementSystem.dto.StudentRequestDto;
import com.seating.examinationManagementSystem.dto.StudentResponseDto;
import com.seating.examinationManagementSystem.dto.StudentScoreResponseDto;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

        List<StudentResponseDto> getStudentsByName(String name);

        StudentScoreResponseDto getStudentByRollNo(Long rollNo) throws NotFoundException;

        CreateResponseDto addStudent(StudentRequestDto studentRequestDto);
}
