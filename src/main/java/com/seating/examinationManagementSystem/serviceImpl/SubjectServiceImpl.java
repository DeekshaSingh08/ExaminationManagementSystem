package com.seating.examinationManagementSystem.serviceImpl;

import com.seating.examinationManagementSystem.dto.*;
import com.seating.examinationManagementSystem.entity.Student;
import com.seating.examinationManagementSystem.entity.Subject;
import com.seating.examinationManagementSystem.entity.SubjectEnrollement;
import com.seating.examinationManagementSystem.exception.NotAcceptableException;
import com.seating.examinationManagementSystem.exception.NotFoundException;
import com.seating.examinationManagementSystem.mapper.StudentMapper;
import com.seating.examinationManagementSystem.repository.StudentRepository;
import com.seating.examinationManagementSystem.repository.SubjectEnrollmentRepository;
import com.seating.examinationManagementSystem.repository.SubjectRepository;
import com.seating.examinationManagementSystem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectEnrollmentRepository subjectEnrollmentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void initSubjects() {
        checkAndAddSubjects();
    }

    @Override
    public List<SubjectResponseDto> getAllSubjects() {
        return  subjectRepository.findAll().stream().map(subject -> {
            SubjectResponseDto subjectResponseDto=new SubjectResponseDto();
            subjectResponseDto.setSubjectName(subject.getSubjectName());
            subjectResponseDto.setSubjectId(subject.getSubjectId());
            return subjectResponseDto;
        }).collect(Collectors.toList());

    }

    @Override
    public StudentScoreResponseDto addSubjectsToStudent(SubjectStudentRequestDto subjectStudentRequestDto) throws NotFoundException {
        Student student = studentRepository.findById(subjectStudentRequestDto.getStudentRollNo())
                .orElseThrow(() -> new NotFoundException("Student not found: " + subjectStudentRequestDto.getStudentRollNo()));
        List<SubjectEnrollement> subjectEnrollements;
        if(student.getSubjectEnrollements()==null ||student.getSubjectEnrollements().isEmpty()) {
            subjectEnrollements=new ArrayList<>();
            subjectStudentRequestDto.getSubjectIds().forEach(subjectId->{
                try {
                    Subject subject = subjectRepository.findById(subjectId)
                            .orElseThrow(() -> new NotFoundException("suject not found: " + subjectId));
                    SubjectEnrollement subjectEnrollement=new SubjectEnrollement();
                    subjectEnrollement.setSubject(subject);
                    subjectEnrollement.setScore(0);
                    subjectEnrollements.add(subjectEnrollmentRepository.save(subjectEnrollement));
                } catch (NotFoundException e) {
                    throw new NotAcceptableException("suject not found: " + subjectId);
                }
            });
            student.setSubjectEnrollements(subjectEnrollements);
            studentRepository.save(student);
        }
        else {
            subjectEnrollements=student.getSubjectEnrollements();
            List<Integer> subjectIds= student.getSubjectEnrollements().stream().map(subjectEnrollement -> {
                return subjectEnrollement.getSubject().getSubjectId();
            }).toList();

            subjectStudentRequestDto.getSubjectIds().forEach(subjectId->{
                try {
                    Subject subject = subjectRepository.findById(subjectId)
                            .orElseThrow(() -> new NotFoundException("suject not found: " + subjectId));
                    if(!subjectIds.contains(subjectId)) {
                        SubjectEnrollement subjectEnrollement = new SubjectEnrollement();
                        subjectEnrollement.setSubject(subject);
                        subjectEnrollement.setScore(0);
                        subjectEnrollements.add(subjectEnrollmentRepository.save(subjectEnrollement));
                    }
                } catch (NotFoundException e) {
                    throw new NotAcceptableException("suject not found: " + subjectId);
                }
            });
            student.setSubjectEnrollements(subjectEnrollements);
            studentRepository.save(student);

        }
        return studentMapper.convertStudentToScoreResponseDto(student);

    }

    @Override
    public ScoreAllocationResponseDto scoreAllocation(PrincipalScoreAllomentDto principalScoreAllomentDto) throws NotFoundException {
        Student student = studentRepository.findById(principalScoreAllomentDto.getStudentRollNo())
                .orElseThrow(() -> new NotFoundException("Student not found: " + principalScoreAllomentDto.getStudentRollNo()));

        List<Integer> subjectIds= student.getSubjectEnrollements().stream().map(subjectEnrollement -> {
            return subjectEnrollement.getSubject().getSubjectId();
        }).toList();
        principalScoreAllomentDto.getSubjects().forEach(studentSubjectEnrollmentResponseDto -> {
            Integer subjectId=studentSubjectEnrollmentResponseDto.getSubjectId();
            try {
                Subject subject = subjectRepository.findById(subjectId)
                        .orElseThrow(() -> new NotFoundException("suject not found: " + subjectId));
                student.getSubjectEnrollements().forEach(subjectEnrollement -> {
                    if(subjectEnrollement.getSubject().getSubjectId().equals(subjectId)){
                        subjectEnrollement.setScore(studentSubjectEnrollmentResponseDto.getScore());
                        subjectEnrollmentRepository.save(subjectEnrollement);
                    }
                });

            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        ScoreAllocationResponseDto scoreAllocationResponseDto =new ScoreAllocationResponseDto();
        scoreAllocationResponseDto.setMessage("student with rollNo "+student.getStudentRollNo()+" scores update successfully");
        scoreAllocationResponseDto.setStatus(HttpStatus.OK.toString());
        return scoreAllocationResponseDto;
    }



    public void checkAndAddSubjects() {
        List<Subject> subjectList = new ArrayList<>();
        List<String> subjectNames= Arrays.asList("Mathematics","Science","History","Physical Education","Computer Science",
                "Hindi","Telugu","spanish","german");
        for (String subjects : subjectNames) {
            Optional<Subject> subjectObject= subjectRepository.findBySubjectName(subjects);
            if (subjectObject.isEmpty()) {
                Subject subject=new Subject();
                subject.setSubjectName(subjects);
                //subjectEnrollement.setScore(0);
                subjectList.add(subject);
            }
        }

        // Save all new rooms at once
        if (!subjectList.isEmpty()) {
            subjectRepository.saveAll(subjectList);
        }
    }

}