package com.seating.examinationManagementSystem.repository;

import com.seating.examinationManagementSystem.entity.SubjectEnrollement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectEnrollmentRepository  extends JpaRepository<SubjectEnrollement,Integer> {

}
