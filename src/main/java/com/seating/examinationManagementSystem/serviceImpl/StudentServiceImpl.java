package com.seating.examinationManagementSystem.serviceImpl;

import com.seating.examinationManagementSystem.exception.NotAcceptableException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.seating.examinationManagementSystem.dto.CreateResponseDto;
import com.seating.examinationManagementSystem.dto.StudentRequestDto;
import com.seating.examinationManagementSystem.dto.StudentResponseDto;
import com.seating.examinationManagementSystem.dto.StudentScoreResponseDto;
import com.seating.examinationManagementSystem.entity.Student;
import com.seating.examinationManagementSystem.entity.UserDetails;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import com.seating.examinationManagementSystem.mapper.StudentMapper;
import com.seating.examinationManagementSystem.repository.StudentRepository;
import com.seating.examinationManagementSystem.repository.UserDetailsRepository;
import com.seating.examinationManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {


    private UserDetailsRepository userDetailsRepository;

    private StudentRepository studentRepository;

    private StudentMapper studentMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,StudentMapper studentMapper, UserDetailsRepository userDetailsRepository,PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.studentMapper=studentMapper;
        this.userDetailsRepository=userDetailsRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public List<StudentResponseDto> getStudentsByName(String name) {
        if(name!=null){
            List<Student> students=studentRepository.findByStudentNameContaining(name);
            return studentMapper.convertStudentToDto(students);
        }else{
            List<Student> students=studentRepository.findAll();
            return studentMapper.convertStudentToDto(students);
        }
    }

    @Override
    public StudentScoreResponseDto getStudentByRollNo(Long rollNo) throws NotFoundException {
        Student student = studentRepository.findById(rollNo)
                .orElseThrow(() -> new NotFoundException("Student not found: " + rollNo));

        return studentMapper.convertStudentToScoreResponseDto(student);
    }

    @Override
    public CreateResponseDto addStudent(StudentRequestDto studentRequestDto) {

        Student student = studentRepository.findById(studentRequestDto.getStudentRollNo()).orElse(null);
        if(student!=null){
            throw new NotAcceptableException("student rollNo already taken");
        }

        student=studentMapper.convertDtoToStudent(studentRequestDto);
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(String.valueOf(student.getStudentRollNo()));
        userDetails.setRole("Student");
        userDetails.setPassword(getEncodedPassword(String.valueOf(student.getStudentRollNo())));

        student.setUser(userDetailsRepository.save(userDetails));
        student=studentRepository.save(student);

        CreateResponseDto createResponseDto =new CreateResponseDto();
        createResponseDto.setMessage("student with rollNo "+student.getStudentRollNo()+" created successfully");
        createResponseDto.setStatus(HttpStatus.OK.toString());
        return createResponseDto;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
