package com.solutions.roartek.placeme.Service;

import android.content.Context;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Helper.JsonDecodeHelper;
import com.solutions.roartek.placeme.Common.Utility;

import org.json.JSONObject;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class JsonManagerImpl implements JsonManager{

    private String operationMode;
    private JSONObject jsonObject;

    public JsonManagerImpl(Context context) {
        PreferenceManager preferenceManager =new PreferenceManagerImpl(context);
        operationMode= (String) preferenceManager.getPrferences(null, Constants.PREFERANCE_OPERATION_MODE_KRY, false);
    }

    @Override
    public String encodeToJSON(Object object) {
        return Utility.convertObjectToJSON(object);
    }

    @Override
    public Object decodeFromJSON(String response) {

        JsonDecodeHelper jsonDecodeHelper=new JsonDecodeHelper(response);
        switch (operationMode)
        {
            case Constants.OPERATION_COLLEGE_INFO:
                return jsonDecodeHelper.decodeCollegeInfo();
            case Constants.OPERATION_LOGIN:
               return jsonDecodeHelper.decodeLoginResponse();
            case Constants.OPERATION_REGISTER_COMPANY:
                return jsonDecodeHelper.decodeCUDResponse();
            case Constants.OPERATION_VIEW_COMPANY:
               return jsonDecodeHelper.decodeViewCompanyResponse();
            case Constants.OPERATION_UPDATE_COMPANY:
                return jsonDecodeHelper.decodeCUDResponse();
            case Constants.OPERATION_SHORTLIST_STUDENTS:
                return jsonDecodeHelper.decodeShortlistResponse();
            case Constants.OPERATION_STATISTICS:
                return jsonDecodeHelper.decodeStatsResponse();
            case Constants.OPERATION_UPDATE_USER:
                return jsonDecodeHelper.decodeCUDResponse();
            case Constants.OPERATION_UPDATE_PASSWORD:
                return jsonDecodeHelper.decodeCUDResponse();
            case Constants.OPERATION_DELETE_COMPANY:
                return jsonDecodeHelper.decodeCUDResponse();
            case Constants.OPERATION_APP_CONFIGURE:
                return jsonDecodeHelper.decodeCUDResponse();
            case Constants.OPERATION_LOAD_NOTIFICATIONS:
               return jsonDecodeHelper.decodeNotificationLoad();
            case Constants.OPERATION_CRITERIA_FORM:
                return jsonDecodeHelper.decodeCriteriaFormResponse();
            case Constants.OPERATION_BATCH_DEGREES:
                return jsonDecodeHelper.decodeBatchDegreeResponse();
            case Constants.OPERATION_SAVE_NOTIFICATION:
                return jsonDecodeHelper.decodeCUDResponse();
        }
        return null;
    }

}
