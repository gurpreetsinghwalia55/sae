package com.example.dell.sae.models;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Examination {
    int id;
    Course course;
    String examinationType;
    Date dateTime;
    int totalMarks;
    String referenceAnswerSheet;
    boolean evaluationStatus;

    public boolean getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(boolean evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(String examinationType) {
        this.examinationType = examinationType;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getReferenceAnswerSheet() {
        return referenceAnswerSheet;
    }

    public void setReferenceAnswerSheet(String referenceAnswerSheet) {
        this.referenceAnswerSheet = referenceAnswerSheet;
    }
}

