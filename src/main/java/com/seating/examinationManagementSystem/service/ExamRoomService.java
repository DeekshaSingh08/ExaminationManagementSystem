package com.seating.examinationManagementSystem.service;

import com.seating.examinationManagementSystem.dto.CreateResponseDto;
import com.seating.examinationManagementSystem.dto.ExamRoomResponseDto;
import com.seating.examinationManagementSystem.dto.StudentResponseDto;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface ExamRoomService {

    void initRoleAndUser();

    public CreateResponseDto assignSeat(String roomName, Long studentId, int seatRow, int seatColumn) throws Exception;

    public List<StudentResponseDto> getPossibleStudents(String roomName, int seatRow, int seatColumn,String searchName) throws NotFoundException;

    public Map<String, StudentResponseDto[][]> getSeatingArrangement(String examRoomName) throws NotFoundException;

    public List<ExamRoomResponseDto> getAllExamRooms();
}
