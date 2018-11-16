package com.example.dell.sae.models;

public class EvaluationClass {
    TeacherClass teacherClass;
    boolean evaluationStatus;

    public TeacherClass getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(TeacherClass teacherClass) {
        this.teacherClass = teacherClass;
    }

    public boolean getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(boolean evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    @Override
    public String toString() {
        return teacherClass.getYear() + teacherClass.getBranch() + " " + teacherClass.getSectionFrom() + " - " + teacherClass.getSectionTo();
    }
}
