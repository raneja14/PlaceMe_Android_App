package com.solutions.roartek.placeme.Common;

import android.graphics.Color;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class MyConfig {

    public static final String phpURL = "http://www.v-sehgaltextiles.com/RoarTek/PlaceMe/Controller.php";
    public static final int READ_OUT_TIME = 15000;
    public static final int CONNECTION_OUT_TIME = 5000;
    public static final long adminID = 1400;
    public static final String USER_PREFS = "PLACEME_USER_PREF";
    public static final String DATE_DB_FORMAT = "yyyy-MM-dd";
    public static final String DATE_UI_FORMAT = "dd MMM,yyyy";

    public static final String[] COMPANY_TYPE = {"ON-CAMPUS", "OFF-CAMPUS", "POOL"};
    public static final String[] COMPANY_STATUS = {"UPCOMING", "CONFIRMED"};

    public static final int NUMBER_MIN_VALUE = 0;

    public static final int CGPA_MAX_VALUE = 10;
    public static final int CGPA_MAX_LENGTH = 3;
    public static final boolean IS_CGPA_DECIMAL = true;

    public static final int ARREAR_MAX_VALUE = 99;
    public static final int ARREAR_MAX_LENGTH = 2;
    public static final boolean IS_ARREARS_DECIMAL = false;

    public static final int X_MAX_VALUE = 100;
    public static final int X_MAX_LENGTH = 4;
    public static final boolean IS_X_DECIMAL = true;

    public static final int COLLEGE_CODE_MAX_LENGTH = 15;
    public static final int REQUEST_CODE_UPDATE_COMPANY = 101;

    public static final String NOTIFICATION_UNREAD_MSG = "You have [UNREAD_COUNT] unread notifications !!";
    public static final String NOTIFY_MESSAGE = "Dear student,\n\n" +
            "You are shortlisted in the recruitment drive for [ITEM_COMPANY]" +
            ".\nVENUE: [ITEM_VENUE]," +
            "\nDATE: [ITEM_DATE] at [ITEM_TIME]" +
            ".\nAll the Best!!\n\nRegards\n[ITEM_HOME]";

    public static final int[] PIE_CHART_COLORS = {
            Color.rgb(244, 143, 177), Color.rgb(77, 208, 225), Color.rgb(255, 87, 34), Color.rgb(206, 147, 216), Color.rgb(128, 222, 234), Color.rgb(255, 202, 40),
            Color.rgb(156, 204, 101), Color.rgb(255, 23, 68), Color.rgb(124, 77, 255), Color.rgb(128, 216, 255),
            Color.rgb(255, 255, 141), Color.rgb(29, 233, 182), Color.rgb(174, 234, 0), Color.rgb(255, 110, 64),
            Color.rgb(248, 187, 208), Color.rgb(98, 0, 234), Color.rgb(192, 202, 51), Color.rgb(255, 160, 0),
            Color.rgb(178, 235, 242), Color.rgb(212, 225, 87), Color.rgb(105, 240, 174)};

}