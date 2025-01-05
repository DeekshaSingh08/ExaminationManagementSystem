package com.seating.examinationManagementSystem.mapper;

import com.seating.examinationManagementSystem.dto.*;
import com.seating.examinationManagementSystem.entity.Student;
import com.seating.examinationManagementSystem.entity.StudentSolr;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentMapper {

    public List<StudentResponseDto> convertStudentToDto(List<Student> students){
        List<StudentResponseDto> studentResponseDtos=new ArrayList<>();
        students.stream().forEach(student->{
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setStudentName(student.getStudentName().toLowerCase());
            studentResponseDto.setStudentRollNo(student.getStudentRollNo());
            studentResponseDto.setClassName(student.getClassName());
            studentResponseDto.setHouse(student.getHouse());
            List<StudentSubjectEnrollmentResponseDto> studentSubjectEnrollmentResponseDtos=new ArrayList<>();
            student.getSubjectEnrollements().stream().forEach(subjectEnrollement -> {
                StudentSubjectEnrollmentResponseDto studentSubjectEnrollmentResponseDto=new StudentSubjectEnrollmentResponseDto();
                studentSubjectEnrollmentResponseDto.setSubjectId(subjectEnrollement.getSubject().getSubjectId());
                studentSubjectEnrollmentResponseDto.setSubjectName(subjectEnrollement.getSubject().getSubjectName());
                studentSubjectEnrollmentResponseDto.setScore(subjectEnrollement.getScore());
                studentSubjectEnrollmentResponseDtos.add(studentSubjectEnrollmentResponseDto);
            });
            studentResponseDto.setSubjects(studentSubjectEnrollmentResponseDtos);
            studentResponseDtos.add(studentResponseDto);
        });
        return studentResponseDtos;
    }

    public StudentResponseDto convertStudentToDto(Student student){
        StudentResponseDto studentResponseDto=new StudentResponseDto();
        studentResponseDto.setStudentName(student.getStudentName().toLowerCase());
        studentResponseDto.setStudentRollNo(student.getStudentRollNo());
        studentResponseDto.setClassName(student.getClassName());
        studentResponseDto.setHouse(student.getHouse());
        return studentResponseDto;

    }

    public StudentScoreResponseDto convertStudentToScoreResponseDto(Student student){
        StudentScoreResponseDto studentScoreResponseDto=new StudentScoreResponseDto();
        studentScoreResponseDto.setStudentName(student.getStudentName());
        studentScoreResponseDto.setStudentRollNo(student.getStudentRollNo());
        studentScoreResponseDto.setHouse(student.getHouse());
        studentScoreResponseDto.setClassName(student.getClassName());
        SeatDto seatDto =new SeatDto();
        seatDto.setSeatColumn(student.getSeatColumn());
        seatDto.setSeatRow(student.getSeatRow());
        studentScoreResponseDto.setSeat(seatDto);
        studentScoreResponseDto.setExamRoomName(student.getExamRoomName());
        studentScoreResponseDto.setAddress(student.getAddress());

        List<StudentSubjectEnrollmentResponseDto> studentSubjectEnrollmentResponseDtos=new ArrayList<>();
        student.getSubjectEnrollements().stream().forEach(subjectEnrollement -> {
            StudentSubjectEnrollmentResponseDto studentSubjectEnrollmentResponseDto=new StudentSubjectEnrollmentResponseDto();
            studentSubjectEnrollmentResponseDto.setSubjectId(subjectEnrollement.getSubject().getSubjectId());
            studentSubjectEnrollmentResponseDto.setSubjectName(subjectEnrollement.getSubject().getSubjectName());
            studentSubjectEnrollmentResponseDto.setScore(subjectEnrollement.getScore());
            studentSubjectEnrollmentResponseDtos.add(studentSubjectEnrollmentResponseDto);
        });
        studentScoreResponseDto.setSubjects(studentSubjectEnrollmentResponseDtos);
        return studentScoreResponseDto;

    }

    public Student convertDtoToStudent(StudentRequestDto studentRequestDto) {
        Student student=new Student();
        student.setStudentRollNo(studentRequestDto.getStudentRollNo());
        student.setStudentName(studentRequestDto.getStudentName().toLowerCase());
        student.setHouse(studentRequestDto.getHouse());
        student.setClassName(studentRequestDto.getClassName());
        student.setAddress(studentRequestDto.getAddress());
        return student;
    }

    public StudentSolr mapToSolr(Student student) {
        StudentSolr studentSolr = new StudentSolr();
        studentSolr.setStudentRollNo(String.valueOf(student.getStudentRollNo())); // Convert int to String
        studentSolr.setStudentName(student.getStudentName().trim().toLowerCase().replaceAll("\\s+", ""));
        return studentSolr;
    }
}


