package com.seating.examinationManagementSystem.controller;

import com.seating.examinationManagementSystem.dto.CreateResponseDto;
import com.seating.examinationManagementSystem.dto.ExamRoomResponseDto;
import com.seating.examinationManagementSystem.dto.StudentResponseDto;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import com.seating.examinationManagementSystem.service.ExamRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/examinationmanagementsystem/exam-room")
public class ExamRoomController {

    @Autowired
    private ExamRoomService examRoomService;

    @PostConstruct
    public void initRoleAndUser() {
        examRoomService.initRoleAndUser();
    }

    @PostMapping("/{roomName}/assign-seat")
    public ResponseEntity<CreateResponseDto> assignSeat(
            @PathVariable String roomName,
            @RequestParam Long studentId,
            @RequestParam int seatRow,
            @RequestParam int seatColumn) throws Exception {
        CreateResponseDto response=examRoomService.assignSeat(roomName, studentId, seatRow, seatColumn);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomName}/possible-students")
    public ResponseEntity<List<StudentResponseDto>> getPossibleStudents(
            @PathVariable String roomName,
            @RequestParam int seatRow,
            @RequestParam int seatColumn,@RequestParam(value = "name",required = false) String searchName) throws NotFoundException {
        List<StudentResponseDto> possibleStudents = examRoomService.getPossibleStudents(roomName, seatRow, seatColumn,searchName);
        return ResponseEntity.ok(possibleStudents);
    }

    @GetMapping("/{examRoomName}/seating-arrangement")
    public ResponseEntity<Map<String, StudentResponseDto[][]>> getSeatingArrangement(@PathVariable String examRoomName) throws NotFoundException {
        Map<String, StudentResponseDto[][]> seatingArrangement = examRoomService.getSeatingArrangement(examRoomName);
        return ResponseEntity.ok(seatingArrangement);
    }

    @GetMapping("/getAllExamRooms")
    public ResponseEntity<List<ExamRoomResponseDto>> getAllExamRooms() {
        List<ExamRoomResponseDto> examRoomResponseDtos = examRoomService.getAllExamRooms();
        return ResponseEntity.ok(examRoomResponseDtos);
    }

}
