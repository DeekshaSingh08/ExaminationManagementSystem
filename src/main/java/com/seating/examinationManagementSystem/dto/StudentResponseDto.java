package com.seating.examinationManagementSystem.dto;

import java.util.List;

public class StudentResponseDto {

    private Long studentRollNo;

    private String studentName;

    private String className;

    private String house;

    private List<StudentSubjectEnrollmentResponseDto> subjects;

    public Long getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(Long studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public StudentResponseDto(Long studentRollNo, String studentName, String className, String house) {
        this.studentRollNo = studentRollNo;
        this.studentName = studentName;
        this.className = className;
        this.house = house;
    }

    public StudentResponseDto() {
    }

    public List<StudentSubjectEnrollmentResponseDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<StudentSubjectEnrollmentResponseDto> subjects) {
        this.subjects = subjects;
    }
}
