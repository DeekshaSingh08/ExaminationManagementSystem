package com.seating.examinationManagementSystem.dto;

import java.util.List;

public class SubjectStudentRequestDto {
    private List<Integer> subjectIds;

    private Long studentRollNo;

    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public Long getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(Long studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public SubjectStudentRequestDto(List<Integer> subjectIds, Long studentRollNo) {
        this.subjectIds = subjectIds;
        this.studentRollNo = studentRollNo;
    }

    public SubjectStudentRequestDto() {
    }
}
