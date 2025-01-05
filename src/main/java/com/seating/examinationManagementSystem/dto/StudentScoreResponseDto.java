package com.seating.examinationManagementSystem.dto;

import java.util.List;

public class StudentScoreResponseDto {

    private Long studentRollNo;

    private String studentName;

    private String className;

    private String house;

    private String address;

    private SeatDto seatDto;


    private List<StudentSubjectEnrollmentResponseDto> subjects;

    private String examRoomName;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SeatDto getSeat() {
        return seatDto;
    }

    public void setSeat(SeatDto seatDto) {
        this.seatDto = seatDto;
    }

    public List<StudentSubjectEnrollmentResponseDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<StudentSubjectEnrollmentResponseDto> subjects) {
        this.subjects = subjects;
    }

    public String getExamRoomName() {
        return examRoomName;
    }

    public void setExamRoomName(String examRoomName) {
        this.examRoomName = examRoomName;
    }
}