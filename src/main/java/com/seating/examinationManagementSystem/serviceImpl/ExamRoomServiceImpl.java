package com.seating.examinationManagementSystem.serviceImpl;

import com.seating.examinationManagementSystem.dto.CreateResponseDto;
import com.seating.examinationManagementSystem.dto.ExamRoomResponseDto;
import com.seating.examinationManagementSystem.dto.StudentResponseDto;
import com.seating.examinationManagementSystem.entity.ExamRoom;
import com.seating.examinationManagementSystem.entity.Student;
import com.seating.examinationManagementSystem.exception.BadRequestException;
import com.seating.examinationManagementSystem.exception.NotAcceptableException;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import com.seating.examinationManagementSystem.mapper.StudentMapper;
import com.seating.examinationManagementSystem.repository.ExamRoomRepository;
import com.seating.examinationManagementSystem.repository.StudentRepository;
import com.seating.examinationManagementSystem.service.ExamRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExamRoomServiceImpl implements ExamRoomService {

    @Autowired
    private ExamRoomRepository examRoomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    public void checkAndAddExamRooms() {
        List<ExamRoom> newExamRooms = new ArrayList<>();

        List<String> examRoomNames= Arrays.asList("examRoom1","examRoom2","examRoom3","examRoom4","examRoom5","examRoomExtra");

        for (String roomName : examRoomNames) {
            Optional<ExamRoom> existingRoom = examRoomRepository.findByExamRoomName(roomName);

            if (existingRoom.isEmpty()) {
                ExamRoom newRoom = new ExamRoom();
                newRoom.setExamRoomName(roomName);
                newRoom.setSeatRows(5);
                newRoom.setSeatColumns(5);
                newExamRooms.add(newRoom);
            }
        }

        // Save all new rooms at once
        if (!newExamRooms.isEmpty()) {
            examRoomRepository.saveAll(newExamRooms);
        }
    }

    @Override
    public void initRoleAndUser() {
        checkAndAddExamRooms();
    }

    public CreateResponseDto assignSeat(String roomName, Long studentId, int seatRow, int seatColumn) throws Exception {
        // Fetch the exam room
        ExamRoom examRoom = examRoomRepository.findById(roomName)
                .orElseThrow(() -> new NotFoundException("Exam room not found: " + roomName));

        // Fetch the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found: " + studentId));

        if(student.getSeatRow()!=0){
            throw new NotAcceptableException("student was already alocated the seat");
        }

        // Validate seat boundaries
        if (seatRow <0 || !(seatRow < examRoom.getSeatRows()) || seatColumn <0 || !(seatColumn < examRoom.getSeatColumns())) {
            throw new BadRequestException("Invalid seat position. row and column must be between 0 and 4");
        }

        // Validate constraints
        validateConstraints(examRoom, seatRow+1, seatColumn+1, student);

        // Assign seat
        student.setSeatRow(seatRow+1);
        student.setSeatColumn(seatColumn+1);
        student.setExamRoomName(examRoom.getExamRoomName().toLowerCase());

        // Update the exam room
        List<Student> students = examRoom.getStudents();
        students.add(student);
        examRoom.setStudents(students);

        // Save the updates
        studentRepository.save(student);
        examRoomRepository.save(examRoom);

        CreateResponseDto createResponseDto =new CreateResponseDto();
        createResponseDto.setMessage("student with rollNo "+student.getStudentRollNo()+" allocated the seat");
        createResponseDto.setStatus(HttpStatus.OK.toString());
        return createResponseDto;
    }

    private void validateConstraints(ExamRoom examRoom, int seatRow, int seatColumn, Student student) throws Exception {
        // Check row and column for the same class
        for (Student s : examRoom.getStudents()) {
            if ((s.getSeatRow() == seatRow || s.getSeatColumn() == seatColumn)
                    && s.getClassName().equals(student.getClassName())) {
                throw new NotAcceptableException("A student from the same class is already seated in this row or column.");
            }
        }

        // Check adjacent seats for the same house
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            int adjacentRow = seatRow + dir[0];
            int adjacentCol = seatColumn + dir[1];

            if (adjacentRow >= 0 && adjacentRow < examRoom.getSeatRows() && adjacentCol >= 0 && adjacentCol < examRoom.getSeatColumns()) {
                for (Student s : examRoom.getStudents()) {
                    if (s.getSeatRow() == adjacentRow && s.getSeatColumn() == adjacentCol && s.getHouse().equalsIgnoreCase(student.getHouse())) {
                        throw new NotAcceptableException("A student from the same house is already adjacent to this seat.");
                    }
                }
            }
        }
    }

    public List<StudentResponseDto> getPossibleStudents(String roomName, int seatRow, int seatColumn,String searchName) throws NotFoundException {
        // Get the exam room by name
        ExamRoom examRoom = examRoomRepository.findById(roomName)
                .orElseThrow(() -> new NotFoundException("Exam room not found"));

        // Get the list of students already allocated in this room
        List<Student> allocatedStudents = examRoom.getStudents();

        if (seatRow <0 || !(seatRow < examRoom.getSeatRows()) || seatColumn <0 || !(seatColumn < examRoom.getSeatColumns())) {
            throw new BadRequestException("Invalid seat position. row and column must be between 0 and 4");
        }
        List<Student> unallocatedStudents=new ArrayList<>();
        if(searchName!=null){
            unallocatedStudents=studentRepository.findByStudentNameContainingAndExamRoomNameIsNull(searchName);
        }
        else{
            unallocatedStudents=studentRepository.findAllByExamRoomNameIsNull();
        }
        //List<Student> unallocatedStudents = studentRepository.findAllByExamRoomNameIsNull();

        // Filter unallocated students based on the rule

        List<Student> students= unallocatedStudents.stream()
                .filter(student -> isValidForSeat(student, allocatedStudents, seatRow+1, seatColumn+1, examRoom))
                .collect(Collectors.toList());

        return studentMapper.convertStudentToDto(students);
    }

    private boolean isValidForSeat(Student student, List<Student> allocatedStudents, int seatRow, int seatColumn, ExamRoom examRoom) {
        // Check Rule 1: No two students in the same class in the same row or column
        for (Student allocated : allocatedStudents) {
            if (allocated.getClassName().equalsIgnoreCase(student.getClassName()) &&
                    (allocated.getSeatRow() == seatRow || allocated.getSeatColumn() == seatColumn)) {
                return false;
            }
        }

        // Check Rule 2: No two students in the same house adjacent (row, column, diagonal)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            int adjacentRow = seatRow + dir[0];
            int adjacentCol = seatColumn + dir[1];

            if (adjacentRow >= 0 && adjacentRow < examRoom.getSeatRows() &&
                    adjacentCol >= 0 && adjacentCol < examRoom.getSeatColumns()) {
                for (Student allocated : allocatedStudents) {
                    if (allocated.getSeatRow() == adjacentRow && allocated.getSeatColumn() == adjacentCol &&
                            allocated.getHouse().equalsIgnoreCase(student.getHouse())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    public Map<String, StudentResponseDto[][]> getSeatingArrangement(String examRoomName) throws NotFoundException {
        // Fetch the exam room by name
        ExamRoom examRoom = examRoomRepository.findById(examRoomName)
                .orElseThrow(() -> new NotFoundException("Exam Room not found: " + examRoomName));

        // Create a seating arrangement matrix
        int rows = examRoom.getSeatRows();
        int columns = examRoom.getSeatColumns();
        StudentResponseDto[][] seatingArrangement = new StudentResponseDto[rows][columns];

        // Fetch all students allocated to this exam room
        List<Student> allocatedStudents = studentRepository.findByExamRoomName(examRoomName.toLowerCase());

        // Populate the seating arrangement matrix
        for (Student student : allocatedStudents) {
            seatingArrangement[student.getSeatRow()-1][student.getSeatColumn()-1] = studentMapper.convertStudentToDto(student);
        }

        // Return the result as a map (can include additional details if needed)
        Map<String, StudentResponseDto[][]> result = new HashMap<>();
        result.put(examRoomName, seatingArrangement);

        return result;
    }

    @Override
    public List<ExamRoomResponseDto> getAllExamRooms() {
        return examRoomRepository.findAll().stream().map(examRoom -> {
            ExamRoomResponseDto examRoomResponseDto=new ExamRoomResponseDto();
            String examRoomName=examRoom.getExamRoomName();
            StudentResponseDto[][] response;
            try {
                response=getSeatingArrangement(examRoomName).get(examRoomName);
            } catch (NotFoundException e) {
                throw new NotFoundException("Exam Room not found: " + examRoomName);
            }
            examRoomResponseDto.setSeats(response);
            examRoomResponseDto.setExamRoomName(examRoomName);
            int capacity=examRoom.getSeatColumns()*examRoom.getSeatRows();
            examRoomResponseDto.setOverallCapacity(capacity);
            List<Student> students=examRoom.getStudents();
            if(students==null||students.isEmpty()){
                examRoomResponseDto.setRemainingCapacity(capacity);
            }
            else {
                examRoomResponseDto.setRemainingCapacity(capacity-students.size());
            }
            return examRoomResponseDto;
        }).collect(Collectors.toList());
    }


}