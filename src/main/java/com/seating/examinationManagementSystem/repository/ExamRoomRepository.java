package com.seating.examinationManagementSystem.repository;

import com.seating.examinationManagementSystem.entity.ExamRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRoomRepository extends JpaRepository<ExamRoom,String> {

    Optional<ExamRoom> findByExamRoomName(String roomName);


}
