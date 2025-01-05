package com.seating.examinationManagementSystem.serviceImpl;

import com.seating.examinationManagementSystem.entity.StudentSolr;
import com.seating.examinationManagementSystem.exception.NotAcceptableException;
import com.seating.examinationManagementSystem.repository.StudentSolrRepository;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
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
import org.springframework.data.solr.core.query.SimpleQuery;
import java.util.List;
import java.util.ArrayList;


@Component
public class StudentServiceImpl implements StudentService {


    private UserDetailsRepository userDetailsRepository;

    private StudentRepository studentRepository;

    private StudentMapper studentMapper;

    private PasswordEncoder passwordEncoder;

    private StudentSolrRepository studentSolrRepository;


    @Autowired
    private SolrTemplate solrTemplate;

    public List<StudentSolr> searchStudentByFuzzyName(String name) {

        String queryString = "studentName:" + name + "* OR studentName:" + name + "~2";
        Query query = new SimpleQuery(queryString);

        return solrTemplate.query("students", query, StudentSolr.class).getContent();
    }

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,StudentMapper studentMapper,
                              UserDetailsRepository userDetailsRepository,PasswordEncoder passwordEncoder,
                              StudentSolrRepository studentSolrRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper=studentMapper;
        this.userDetailsRepository=userDetailsRepository;
        this.passwordEncoder=passwordEncoder;
        this.studentSolrRepository=studentSolrRepository;
    }

    @Override
    public List<StudentResponseDto> getStudentsByName(String name) {
        if(name!=null){
            name=name.toLowerCase().replaceAll("\\s+", "");
            List<StudentSolr> studentSolrs=searchStudentByFuzzyName(name.toLowerCase());
            List<Student> students=new ArrayList<>();
            studentSolrs.forEach(studentSolr -> {
                Student student=studentRepository.findById(Long.valueOf(studentSolr.getStudentRollNo())).orElse(null);
                if(student!=null){
                    students.add(student);
                }
            });
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
        StudentSolr studentSolr = studentMapper.mapToSolr(student);
        studentSolrRepository.save(studentSolr);

        CreateResponseDto createResponseDto =new CreateResponseDto();
        createResponseDto.setMessage("student with rollNo "+student.getStudentRollNo()+" created successfully");
        createResponseDto.setStatus(HttpStatus.OK.toString());
        return createResponseDto;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}