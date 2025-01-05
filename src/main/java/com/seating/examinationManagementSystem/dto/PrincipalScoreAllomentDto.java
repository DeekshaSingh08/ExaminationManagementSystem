package com.seating.examinationManagementSystem.dto;

import java.util.List;

public class PrincipalScoreAllomentDto {

    private List<StudentSubjectEnrollmentResponseDto> subjects;

    private Long studentRollNo;

    public List<StudentSubjectEnrollmentResponseDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<StudentSubjectEnrollmentResponseDto> subjects) {
        this.subjects = subjects;
    }

    public Long getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(Long studentRollNo) {
        this.studentRollNo = studentRollNo;
    }
}
