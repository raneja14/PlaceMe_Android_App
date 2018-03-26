package com.solutions.roartek.placeme.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class PlacementInfo implements Serializable {
   
    private String companyName ;
    private String venue ;
    private String DOR ;
    private String time ;
    private String message ;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDOR() {
        return DOR;
    }

    public void setDOR(String DOR) {
        this.DOR = DOR;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
