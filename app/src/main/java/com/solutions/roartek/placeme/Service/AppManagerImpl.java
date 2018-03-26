package com.solutions.roartek.placeme.Service;

import android.content.Context;
import android.util.Log;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.DAO.Async_ExecuteOperation;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_Staff;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Raghav.Aneja on 10-12-2016.
 */
public class AppManagerImpl implements AppManager {

    @Override
    public void executeDBCalls(Context context, Object object, ResponseDelegate responseDelegate) {
        JSONObject jsonObject = getPrerequisiteJsonParameters(context);
        if (jsonObject != null) {
            String requestJSON;
            JsonManager jsonManager = new JsonManagerImpl(context);
            String jsonParameter = jsonManager.encodeToJSON(object);
            try {
                if (jsonParameter != null) {
                    jsonObject.put(Constants.PHP_PARAMETER_KEY, jsonParameter);
                    requestJSON = jsonObject.toString();
                    new Async_ExecuteOperation(context, responseDelegate).execute(requestJSON);
                }
            } catch (JSONException e) {
                Log.e("executeDBCalls", e.getMessage());
            }
        } else
            responseDelegate.onPostExecute(Constants.ERROR_MSG_ANONYMOUS);
    }

    @Override
    public int getUnreadNotificationCount(List allNotificationsList, List userNotificationList) {

        return allNotificationsList.size()-userNotificationList.size();
    }

    @Override
    public List getReadNotificationList(List userNotificationList,Context context) {
        PreferenceManager preferenceManager =new PreferenceManagerImpl(context);

        Entity_Staff staff= (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class,Constants.PREFERANCE_STAFF_KEY,true);
        if(staff!=null)
        {
            Map<Long,Set> usersAppReadNotificationsMap= (Map<Long, Set>) preferenceManager.getPrferences(Map.class,Constants.PREFERANCE_USER_READ_NOTIFICATIONS_KEY,true);
            Set<Long> userAppNoticationSet;
            if(usersAppReadNotificationsMap!=null && usersAppReadNotificationsMap.size()>0) {
                if(usersAppReadNotificationsMap.containsKey(staff.getStaffID()))
                {
                    userAppNoticationSet = usersAppReadNotificationsMap.get(staff.getStaffID());
                    if (userAppNoticationSet.size() > 0) {
                        userAppNoticationSet.removeAll(userNotificationList);
                        userNotificationList.addAll(userAppNoticationSet);
                        usersAppReadNotificationsMap.put(staff.getStaffID(), userAppNoticationSet);
                        preferenceManager.setPreferences(Constants.PREFERANCE_USER_READ_NOTIFICATIONS_KEY,usersAppReadNotificationsMap,true);
                        return userNotificationList;
                    }
                }
            }
        }
        return userNotificationList;
    }

    @Override
    public void storeAppReadNotification(long notificationID,long staffID,PreferenceManager preferenceManager) {

        Map<String,Set> usersAppReadNotificationsMap;
        Set<Double> userAppNotificationSet=null;

        usersAppReadNotificationsMap=getUserAppReadNotificationMap(preferenceManager);

        if(usersAppReadNotificationsMap==null)
            usersAppReadNotificationsMap=new HashMap<>();

        if(usersAppReadNotificationsMap.containsKey(String.valueOf(staffID)))
            userAppNotificationSet=new HashSet<>(usersAppReadNotificationsMap.get(String.valueOf(staffID)));
        else
            userAppNotificationSet=new HashSet<>();
try {
    userAppNotificationSet.add(((double) notificationID));
    usersAppReadNotificationsMap.put(String.valueOf(staffID), userAppNotificationSet);

    preferenceManager.setPreferences(Constants.PREFERANCE_USER_READ_NOTIFICATIONS_KEY,usersAppReadNotificationsMap,true);

}
catch (Exception e)
{
    Log.d("d",e.getMessage());
}
    }

    @Override
    public Map<String, Set> getUserAppReadNotificationMap(PreferenceManager preferenceManager) {

        return (Map<String, Set>) preferenceManager.getPrferences(Map.class,Constants.PREFERANCE_USER_READ_NOTIFICATIONS_KEY,true);
    }

    private JSONObject getPrerequisiteJsonParameters(Context context)
    {
        JSONObject jsonObject=null;
        try {

            PreferenceManager preferenceManager =new PreferenceManagerImpl(context);
            String dbCode= (String) preferenceManager.getPrferences(null,Constants.PREFERANCE_DB_MODE_KEY,false);
            String operationCode= (String) preferenceManager.getPrferences(null,Constants.PREFERANCE_OPERATION_MODE_KRY,false);
            String collegeJsonString= (String) preferenceManager.getPrferences(null,Constants.PREFERANCE_COLLEGE_OBJECT_KEY,false);

            jsonObject=new JSONObject();
            jsonObject.put(Constants.PREFERANCE_DB_MODE_KEY,dbCode);
            jsonObject.put(Constants.PREFERANCE_OPERATION_MODE_KRY,operationCode);
            jsonObject.put(Constants.PREFERANCE_COLLEGE_OBJECT_KEY,collegeJsonString);

        } catch (JSONException e) {
            Log.e("PrerequisiteParameters",e.getMessage());
        }

        return jsonObject;
    }

}
