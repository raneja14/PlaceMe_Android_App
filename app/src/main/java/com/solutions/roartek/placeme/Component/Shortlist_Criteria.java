package com.solutions.roartek.placeme.Component;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class Shortlist_Criteria implements Serializable {

    private double X ;
    private double XII ;
    private double cgpa ;
    private int arrears;
    private boolean isPlaced ;
    private boolean isDiplomaAllowed ;
    private List<String> branch ;
    private int eligibleCount ;
    private String batch;
    private String degree;

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getXII() {
        return XII;
    }

    public void setXII(double XII) {
        this.XII = XII;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getArrears() {
        return arrears;
    }

    public void setArrears(int arrears) {
        this.arrears = arrears;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setIsPlaced(boolean isPlaced) {
        this.isPlaced = isPlaced;
    }

    public boolean isDiplomaAllowed() {
        return isDiplomaAllowed;
    }

    public void setIsDiplomaAllowed(boolean isDiplomaAllowed) {
        this.isDiplomaAllowed = isDiplomaAllowed;
    }

    public List<String> getBranch() {
        return branch;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public int getEligibleCount() {
        return eligibleCount;
    }

    public void setEligibleCount(int eligibleCount) {
        this.eligibleCount = eligibleCount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
