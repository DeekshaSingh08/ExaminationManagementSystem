package com.seating.examinationManagementSystem.repository;

import com.seating.examinationManagementSystem.entity.Student;
import com.seating.examinationManagementSystem.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByStudentNameContaining(String keyword);

    List<Student> findAllByExamRoomNameIsNull();

    List<Student> findByStudentNameContainingAndExamRoomNameIsNull(String keyword);

    List<Student> findByExamRoomName(String examRoomName);

}
