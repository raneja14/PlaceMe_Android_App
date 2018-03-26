package com.solutions.roartek.placeme.Service;

import android.content.Context;
import android.content.SharedPreferences;

import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Utility;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class PreferenceManagerImpl implements PreferenceManager {

    private SharedPreferences appPreferance;

    public PreferenceManagerImpl(Context context) {
        appPreferance=context.getSharedPreferences(MyConfig.USER_PREFS,0);
    }

    @Override
    public void setPreferences(String key, Object obj,boolean convertToJSON) {

        SharedPreferences.Editor sp_edit = appPreferance.edit();
        String value;
        if (convertToJSON)
            value = Utility.convertObjectToJSON(obj);
        else
            value = obj.toString();

        sp_edit.putString(key, value);
        sp_edit.commit();
    }

    @Override
    public Object getPrferences(Class clazz, String key,boolean convertFromJSON) {

        String preferenceValue = appPreferance.getString(key, "");

        if (convertFromJSON)
          return Utility.convertJSONToObject(clazz, preferenceValue);

        return preferenceValue;
    }
}
