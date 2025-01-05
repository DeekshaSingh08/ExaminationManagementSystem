package com.seating.examinationManagementSystem.repository;

import com.seating.examinationManagementSystem.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    List<UserDetails> findByUserName(String userName);
}
