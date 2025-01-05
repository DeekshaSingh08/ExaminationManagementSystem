package com.seating.examinationManagementSystem.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.annotation.Id;

@SolrDocument(collection = "students")
public class StudentSolr {

    @Id
    private String studentRollNo; // Solr requires a String ID

    @Field("studentName")
    private String studentName;

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}