package com.seating.examinationManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExamRoom {

    @Id
    private String examRoomName;

    private int seatRows;

    private int seatColumns;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Student> students;


    public String getExamRoomName() {
        return examRoomName;
    }

    public void setExamRoomName(String examRoomName) {
        this.examRoomName = examRoomName;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
    }

    public int getSeatColumns() {
        return seatColumns;
    }

    public void setSeatColumns(int seatColumns) {
        this.seatColumns = seatColumns;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
