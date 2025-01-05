package com.seating.examinationManagementSystem.entity;

import javax.persistence.*;

@Entity
public class SubjectEnrollement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer subjectEnrollementId;

    @OneToOne
    private Subject subject;

    private int score;

    public Integer getSubjectEnrollementId() {
        return subjectEnrollementId;
    }

    public void setSubjectEnrollementId(Integer subjectEnrollementId) {
        this.subjectEnrollementId = subjectEnrollementId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
