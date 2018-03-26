package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.solutions.roartek.placeme.Adapter.Adapter_Notifications;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ListItemClickCallBack;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Swathi.K on 18-12-2016.
 */
public class Activity_Notifications extends AppCompatActivity implements ListItemClickCallBack,GenericActivityDelegate,ResponseDelegate {

    private RecyclerView recyclerView;
    private TextView txt_unreadCount;
    private Adapter_Notifications adapter_notifications;
    private List<Entity_Notification> notificationList;
    private PreferenceManager preferenceManager;
    private AppManager appManager;
    private Entity_Staff entityStaff;
    private int currentUnreadCount = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        initialiseViews();
        setPreferences();
        getObjectFromPreferences();
        onProceed();
    }

    @Override
    public void onItemClick(int position) {
        Entity_Notification notification = notificationList.get(position);
        if (notification.getIsRead() == 0) {
            currentUnreadCount--;
            notification.setIsRead(1);
            setUnreadText(currentUnreadCount);
        }
        showDetailedNotification(notification);
        appManager.storeAppReadNotification(notification.getNotificationId(), entityStaff.getStaffID(), preferenceManager);
    }

    private void showDetailedNotification(Entity_Notification notification) {
        Intent intent = new Intent(Activity_Notifications.this, Activity_Detailed_Notifications.class);
        String notificationJSON = Utility.convertObjectToJSON(notification);
        intent.putExtra("NOTIFICATION_JSON", notificationJSON);
        startActivity(intent);
    }

    @Override
    public void onProceed() {
        appManager = new AppManagerImpl();
        appManager.executeDBCalls(Activity_Notifications.this, entityStaff, this);
    }

    @Override
    public void initialiseViews() {
        txt_unreadCount = (TextView) findViewById(R.id.notifications_txt_notificationCount);
        recyclerView = (RecyclerView) findViewById(R.id.notification_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void setPreferences() {
        preferenceManager = new PreferenceManagerImpl(Activity_Notifications.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, Constants.OPERATION_LOAD_NOTIFICATIONS, false);
    }

    @Override
    public void getObjectFromPreferences() {
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
    }

    private void setAdapter() {
        adapter_notifications = new Adapter_Notifications(notificationList, this);
        recyclerView.setAdapter(adapter_notifications);
        adapter_notifications.setItemClickCallBack(this);
    }

    @Override
    public void onPostExecute(Object responseObject) {
        if (responseObject instanceof Map) {
            Map<Long, Entity_Notification> notificationMap = (Map<Long, Entity_Notification>) responseObject;
            List templist = getUserNotificationList(entityStaff.getStaffID());
            Set<Double> userNotificationsIdSet;
            notificationList = new ArrayList<>(notificationMap.values());
            currentUnreadCount = notificationList.size();
            if (templist != null) {
                userNotificationsIdSet = new HashSet<>(templist);
                currentUnreadCount -= userNotificationsIdSet.size();
                for (Double notificationID : userNotificationsIdSet) {
                    if (notificationMap.containsKey(notificationID.longValue()))
                        notificationMap.get(notificationID.longValue()).setIsRead(1);
                }
            }
            setUnreadText(currentUnreadCount);
            setAdapter();
        } else if (responseObject instanceof String)
            Utility.showToast(Activity_Notifications.this, (String) responseObject);
        else if (responseObject == null)
            Utility.showToast(Activity_Notifications.this, Constants.ERROR_MSG_ANONYMOUS);
    }

    private List getUserNotificationList(long staffId) {
        Map usersAppReadNotificationsMap = appManager.getUserAppReadNotificationMap(preferenceManager);
        if (usersAppReadNotificationsMap != null) {
            if (usersAppReadNotificationsMap.containsKey(String.valueOf(staffId)))
                return (List) usersAppReadNotificationsMap.get(String.valueOf(staffId));
        }
        return null;
    }

    private void setUnreadText(int count) {
        String label = MyConfig.NOTIFICATION_UNREAD_MSG;
        label = label.replace(Constants.REPLACE_KEY_NOTIFICATION_COUNT, count + "");
        txt_unreadCount.setText(label);
        txt_unreadCount.setVisibility(View.VISIBLE);
        Utility.startAnimation(Activity_Notifications.this, txt_unreadCount, R.anim.slide_down_within_parent_from_top);
    }
}
