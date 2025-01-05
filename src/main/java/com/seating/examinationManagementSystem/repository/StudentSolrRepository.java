package com.seating.examinationManagementSystem.repository;

import com.seating.examinationManagementSystem.entity.StudentSolr;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import java.util.List;


public interface StudentSolrRepository extends SolrCrudRepository<StudentSolr, String> {

    @Query("studentName:?0*~2")
    List<StudentSolr> findByStudentNameFuzzy(String studentName);
}