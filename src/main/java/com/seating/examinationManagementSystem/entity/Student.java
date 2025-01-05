package com.seating.examinationManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentRollNo;

    private String studentName;

    private String className;

    private String house;

    private String address;

    private int seatRow;

    private int seatColumn;

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

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }

    @OneToOne
    @JoinColumn(name="userId")
    private UserDetails user;

    @OneToMany
    private List<SubjectEnrollement> subjectEnrollements;

    private String examRoomName;

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public List<SubjectEnrollement> getSubjectEnrollements() {
        return subjectEnrollements;
    }

    public void setSubjectEnrollements(List<SubjectEnrollement> subjectEnrollements) {
        this.subjectEnrollements = subjectEnrollements;
    }

    public String getExamRoomName() {
        return examRoomName;
    }

    public void setExamRoomName(String examRoomName) {
        this.examRoomName = examRoomName;
    }
}