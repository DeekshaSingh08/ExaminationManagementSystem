package com.seating.examinationManagementSystem.dto;

public class ExamRoomResponseDto {
    private String examRoomName;


    private  StudentResponseDto[][] seats;
    private int RemainingCapacity;
    private int overallCapacity;

    public String getExamRoomName() {
        return examRoomName;
    }

    public void setExamRoomName(String examRoomName) {
        this.examRoomName = examRoomName;
    }

    public int getRemainingCapacity() {
        return RemainingCapacity;
    }

    public StudentResponseDto[][] getSeats() {
        return seats;
    }

    public void setSeats(StudentResponseDto[][] seats) {
        this.seats = seats;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        RemainingCapacity = remainingCapacity;
    }

    public int getOverallCapacity() {
        return overallCapacity;
    }

    public void setOverallCapacity(int overallCapacity) {
        this.overallCapacity = overallCapacity;
    }
}
