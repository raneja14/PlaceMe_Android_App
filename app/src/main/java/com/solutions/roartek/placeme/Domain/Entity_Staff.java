package com.solutions.roartek.placeme.Domain;

import com.solutions.roartek.placeme.Component.AppConfigure;

import java.util.Date;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class Entity_Staff {

    private long staffID;
    private String staffName;
    private String password;
    private String email;
    private String contact;
    private Date createdOn;
    private String lastModifiedOn;
    private Boolean isAccountActive;
    private String GCMToken;
    private AppConfigure appConfigure;

    public Entity_Staff(long staffID, String password) {
        this.staffID = staffID;
        this.password = password;
    }

    public Entity_Staff() {
    }

    public long getStaffID() {
        return staffID;
    }

    public void setStaffID(long staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public Boolean getIsAccountActive() {
        return isAccountActive;
    }

    public void setIsAccountActive(Boolean isAccountActive) {
        this.isAccountActive = isAccountActive;
    }

    public String getGCMToken() {
        return GCMToken;
    }

    public void setGCMToken(String GCMToken) {
        this.GCMToken = GCMToken;
    }

    public AppConfigure getAppConfigure() {
        return appConfigure;
    }

    public void setAppConfigure(AppConfigure appConfigure) {
        this.appConfigure = appConfigure;
    }
}
