package com.solutions.roartek.placeme.Domain;

import com.solutions.roartek.placeme.Component.AppConfigure;

import java.util.Date;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class Entity_Company {

    private long compID;
    private long compDetailID;
    private long staffID;
    private String compName;
    private Integer status;
    private Integer type;
    private String host;
    private String DOR;
    private Integer affectedTable;
    private AppConfigure appConfigure;
    private boolean isChecked;

    public Entity_Company() {
    }

    public Entity_Company(long compDetailID, String compName, String DOR) {
        this.compDetailID = compDetailID;
        this.compName = compName;
        this.DOR = DOR;
    }


    public long getCompID() {
        return compID;
    }

    public void setCompID(long compID) {
        this.compID = compID;
    }

    public long getCompDetailID() {
        return compDetailID;
    }

    public void setCompDetailID(long compDetailID) {
        this.compDetailID = compDetailID;
    }

    public long getStaffID() {
        return staffID;
    }

    public void setStaffID(long staffID) {
        this.staffID = staffID;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDOR() {
        return DOR;
    }

    public void setDOR(String DOR) {
        this.DOR = DOR;
    }

    public Integer getAffectedTable() {
        return affectedTable;
    }

    public void setAffectedTable(Integer affectedTable) {
        this.affectedTable = affectedTable;
    }

    public AppConfigure getAppConfigure() {
        return appConfigure;
    }

    public void setAppConfigure(AppConfigure appConfigure) {
        this.appConfigure = appConfigure;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity_Company company = (Entity_Company) o;

        return compDetailID == company.compDetailID;

    }

    @Override
    public int hashCode() {
        return (int) (compDetailID ^ (compDetailID >>> 32));
    }

}
