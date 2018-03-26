package com.solutions.roartek.placeme.Domain;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Component.AppConfigure;
import com.solutions.roartek.placeme.Component.PlacementInfo;
import com.solutions.roartek.placeme.Component.Shortlist_Criteria;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raghav.aneja on 11-12-2016.
 */
public class Entity_Notification {

    private long notificationId;
    private long staffId;
    private String staffName;
    private String criteriaJSON ;
    private String eligibleStudentMapJSON ;
    private String placementInfoJSON;
    private String notificationDate;
    private AppConfigure appConfigureObj;
    private Shortlist_Criteria criteriaObj;
    private PlacementInfo placementInfoObj;
    private Map<String,Integer> EligibleStudentsMap;
    private int isRead;

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
        this.setIsRead(0);
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCriteriaJSON() {
        return criteriaJSON;
    }

    public void setCriteriaJSON(String criteriaJSON) {
        this.criteriaJSON = criteriaJSON;
        setCriteriaObj((Shortlist_Criteria) Utility.convertJSONToObject(Shortlist_Criteria.class, criteriaJSON));

    }

    public String getEligibleStudentMapJSON() {
        return eligibleStudentMapJSON;
    }

    public void setEligibleStudentMapJSON(String eligibleStudentMapJSON) {
        this.eligibleStudentMapJSON = eligibleStudentMapJSON;
        setEligibleStudentsMap((Map<String, Integer>) Utility.convertJSONToObject(HashMap.class, eligibleStudentMapJSON));
    }

    public String getPlacementInfoJSON() {
        return placementInfoJSON;
    }

    public void setPlacementInfoJSON(String placementInfoJSON) {
        this.placementInfoJSON = placementInfoJSON;
        setPlacementInfoObj((PlacementInfo) Utility.convertJSONToObject(PlacementInfo.class,placementInfoJSON));
    }

    public Shortlist_Criteria getCriteriaObj() {
        return criteriaObj;
    }

    public void setCriteriaObj(Shortlist_Criteria criteriaObj) {
        this.criteriaObj = criteriaObj;
    }

    public PlacementInfo getPlacementInfoObj() {
        return placementInfoObj;
    }

    public void setPlacementInfoObj(PlacementInfo placementInfoObj) {
        this.placementInfoObj = placementInfoObj;
    }

    public Map<String, Integer> getEligibleStudentsMap() {
        return EligibleStudentsMap;
    }

    public void setEligibleStudentsMap(Map<String, Integer> eligibleStudentsMap) {
        EligibleStudentsMap = eligibleStudentsMap;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public AppConfigure getAppConfigureObj() {
        return appConfigureObj;
    }

    public void setAppConfigureObj(AppConfigure appConfigureObj) {
        this.appConfigureObj = appConfigureObj;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
