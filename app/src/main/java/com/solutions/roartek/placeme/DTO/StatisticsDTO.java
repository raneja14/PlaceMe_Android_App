package com.solutions.roartek.placeme.DTO;

import java.util.Map;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class StatisticsDTO {

    private int placedCount;
    private int nonPlacedCount;
    private int confirmedCompCount;
    private int upcomingCompCount;
    private Map<String,Integer> studentBranchMap;
    private Map<String,Integer> studentCompanyMap;
    private  Map<String,Integer> monthCompanyMap;
    private Map<String,Integer> monthStudentMap;
    private Map<String,Integer> yearCompanyMap;
    private Map<String,Integer> yearStudentMap;

    public int getPlacedCount() {
        return placedCount;
    }

    public void setPlacedCount(int placedCount) {
        this.placedCount = placedCount;
    }

    public int getNonPlacedCount() {
        return nonPlacedCount;
    }

    public void setNonPlacedCount(int nonPlacedCount) {
        this.nonPlacedCount = nonPlacedCount;
    }

    public int getConfirmedCompCount() {
        return confirmedCompCount;
    }

    public void setConfirmedCompCount(int confirmedCompCount) {
        this.confirmedCompCount = confirmedCompCount;
    }

    public int getUpcomingCompCount() {
        return upcomingCompCount;
    }

    public void setUpcomingCompCount(int upcomingCompCount) {
        this.upcomingCompCount = upcomingCompCount;
    }

    public Map<String, Integer> getStudentBranchMap() {
        return studentBranchMap;
    }

    public void setStudentBranchMap(Map<String, Integer> studentBranchMap) {
        this.studentBranchMap = studentBranchMap;
    }

    public Map<String, Integer> getStudentCompanyMap() {
        return studentCompanyMap;
    }

    public void setStudentCompanyMap(Map<String, Integer> studentCompanyMap) {
        this.studentCompanyMap = studentCompanyMap;
    }

    public Map<String, Integer> getMonthCompanyMap() {
        return monthCompanyMap;
    }

    public void setMonthCompanyMap(Map<String, Integer> monthCompanyMap) {
        this.monthCompanyMap = monthCompanyMap;
    }

    public Map<String, Integer> getMonthStudentMap() {
        return monthStudentMap;
    }

    public void setMonthStudentMap(Map<String, Integer> monthStudentMap) {
        this.monthStudentMap = monthStudentMap;
    }

    public Map<String, Integer> getYearCompanyMap() {
        return yearCompanyMap;
    }

    public void setYearCompanyMap(Map<String, Integer> yearCompanyMap) {
        this.yearCompanyMap = yearCompanyMap;
    }

    public Map<String, Integer> getYearStudentMap() {
        return yearStudentMap;
    }

    public void setYearStudentMap(Map<String, Integer> yearStudentMap) {
        this.yearStudentMap = yearStudentMap;
    }
}
