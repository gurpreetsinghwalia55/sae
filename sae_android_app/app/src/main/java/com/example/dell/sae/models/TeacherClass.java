package com.example.dell.sae.models;

import org.parceler.Parcel;

@Parcel
public class TeacherClass {
    int id, year, sectionFrom, sectionTo;
    String branch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSectionFrom() {
        return sectionFrom;
    }

    public void setSectionFrom(int sectionFrom) {
        this.sectionFrom = sectionFrom;
    }

    public int getSectionTo() {
        return sectionTo;
    }

    public void setSectionTo(int sectionTo) {
        this.sectionTo = sectionTo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
