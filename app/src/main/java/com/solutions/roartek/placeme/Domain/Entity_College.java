package com.solutions.roartek.placeme.Domain;

import java.util.Date;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class Entity_College {

    private String collegeCode;
    private String collegeName;
    private String collegeShortName;
    private String DBUser;
    private String DBPassword;
    private String DBServer;
    private String DBName;
    private Date productExpiryDate;

    public Entity_College(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public Entity_College() {
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeShortName() {
        return collegeShortName;
    }

    public void setCollegeShortName(String collegeShortName) {
        this.collegeShortName = collegeShortName;
    }

    public String getDBUser() {
        return DBUser;
    }

    public void setDBUser(String DBUser) {
        this.DBUser = DBUser;
    }

    public String getDBPassword() {
        return DBPassword;
    }

    public void setDBPassword(String DBPassword) {
        this.DBPassword = DBPassword;
    }

    public String getDBServer() {
        return DBServer;
    }

    public void setDBServer(String DBServer) {
        this.DBServer = DBServer;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    public Date getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(Date productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }
}
