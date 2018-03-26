package com.solutions.roartek.placeme.Service;

import android.content.Context;

import com.solutions.roartek.placeme.Delegate.ResponseDelegate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public interface AppManager {

        void executeDBCalls(Context context,Object object,ResponseDelegate responseDelegate);

        int getUnreadNotificationCount(List allNotificationsList, List userNotificationList);

        List getReadNotificationList(List userNotificationList,Context context);

        void storeAppReadNotification(long notificationID,long staffID,PreferenceManager preferenceManager);

        Map<String,Set> getUserAppReadNotificationMap(PreferenceManager preferenceManager);
      }
