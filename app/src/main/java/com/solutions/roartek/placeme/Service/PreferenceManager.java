package com.solutions.roartek.placeme.Service;

import com.solutions.roartek.placeme.Domain.Entity_College;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public interface PreferenceManager {

    void setPreferences(String key, Object obj,boolean convertToJSON);

    Object getPrferences(Class clazz, String key,boolean convertFromJSON);


}
