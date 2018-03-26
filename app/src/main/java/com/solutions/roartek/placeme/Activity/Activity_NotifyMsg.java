package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.Date;
import java.util.Map;

/**
 * Created by Swathi.K on 24-12-2016.
 */
public class Activity_NotifyMsg extends AppCompatActivity implements GenericActivityDelegate,ResponseDelegate{

    private EditText inp_message,inp_otp;
    private TextView txt_blankMsg,txt_blankOTP;
    private View container_otp;
    private AppManager appManager;
    private Entity_Notification entityNotification;
    private String emails,contacts;
    private MenuItem menuItem_tick, menuItem_edit, menuItem_notify, menuItem_otp;
    private CountDownTimer countDownTimer;
    private int otpDisplayInterval=60000;
    private boolean isOTPSent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_msg);
        
        initialiseViews();
        setPreferences();
        getObjectsFromIntent();
    }

    private void getObjectsFromIntent() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            entityNotification=(Entity_Notification) Utility.convertJSONToObject(Entity_Notification.class, bundle.getString("NOTIFICATION_OBJ_JSON"));
            emails=bundle.getString("EMAIL");
            contacts=bundle.getString("SMS");

            inp_message.setText(entityNotification.getPlacementInfoObj().getMessage());
        }
    }

    private void initialiseTImer()
    {
        countDownTimer=new CountDownTimer(otpDisplayInterval,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish()
            {
                menuItem_otp.setVisible(true);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        menuItem_tick = menu.findItem(R.id.tick);
        menuItem_edit = menu.findItem(R.id.edit_icon);
        menuItem_notify = menu.findItem(R.id.notify_icon);
        menuItem_otp = menu.findItem(R.id.otp_icon);

        displayMenuItems(true, false, true, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_icon:
                displayMenuItems(false, true, false, false);
                inp_message.setEnabled(true);
                Selection.setSelection(inp_message.getText(), inp_message.length());
                return true;
            case R.id.tick:
                if (isOTPSent) {
                    if(inp_otp.getText().toString().length()==0)
                        txt_blankOTP.setVisibility(View.VISIBLE);
                    else
                    {
                        txt_blankOTP.setVisibility(View.GONE);
                        displayMenuItems(false, false, false, true);
                        countDownTimer.cancel();
                        Utility.startAnimation(Activity_NotifyMsg.this, container_otp, R.anim.slide_down);
                        container_otp.setVisibility(View.GONE);
                    }
                } else {
                    if (inp_message.getText().toString().length() == 0)
                        txt_blankMsg.setVisibility(View.VISIBLE);
                    else {
                        inp_message.setEnabled(false);
                        displayMenuItems(true, false, true, false);
                        txt_blankMsg.setVisibility(View.GONE);
                    }
                }
                return true;
            case R.id.otp_icon:
                isOTPSent = true;
                displayMenuItems(false, true, false, false);
                container_otp.setVisibility(View.VISIBLE);
                Utility.startAnimation(Activity_NotifyMsg.this, container_otp, R.anim.slide_up);
                txt_blankOTP.setVisibility(View.GONE);
                inp_otp.requestFocus();
                countDownTimer.start();
                return true;
            case R.id.notify_icon:
                container_otp.setVisibility(View.GONE);
                displayMenuItems(false, false, false, false);
                onProceed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onProceed() {
        prepareObjectForDB();
        appManager=new AppManagerImpl();
        appManager.executeDBCalls(Activity_NotifyMsg.this, entityNotification, this);
    }

    @Override
    public void initialiseViews() {
        inp_message= (EditText) findViewById(R.id.notifyMsg_inp_message);
        inp_otp= (EditText) findViewById(R.id.notifyMsg_inp_otp);
        container_otp=findViewById(R.id.notifyMsg_layout_otp);
        txt_blankMsg= (TextView) findViewById(R.id.notifyMsg_txt_blankMsg);
        txt_blankOTP= (TextView) findViewById(R.id.notifyMsg_txt_blankOTP);

        inp_message.setEnabled(false);
        container_otp.setVisibility(View.GONE);
        initialiseTImer();

    }

    public void displayMenuItems(boolean edit,boolean tick,boolean otp, boolean notify) {
        menuItem_tick.setVisible(tick);
        menuItem_notify.setVisible(notify);
        menuItem_otp.setVisible(otp);
        menuItem_edit.setVisible(edit);
    }

    @Override
    public void setPreferences() {
        PreferenceManager preferenceManager=new PreferenceManagerImpl(Activity_NotifyMsg.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY,Constants.OPERATION_SAVE_NOTIFICATION , false);
    }

    @Override
    public void getObjectFromPreferences() {

    }

    private void prepareObjectForDB() {
        entityNotification.setCriteriaJSON(Utility.convertObjectToJSON(entityNotification.getCriteriaObj()));

        entityNotification.getPlacementInfoObj().setMessage(null);
        entityNotification.setPlacementInfoJSON(Utility.convertObjectToJSON(entityNotification.getPlacementInfoObj()));

        entityNotification.setNotificationDate(Utility.getCurrentDate());

        entityNotification.getEligibleStudentsMap().remove("ALL");
        entityNotification.setEligibleStudentMapJSON(Utility.convertObjectToJSON(entityNotification.getEligibleStudentsMap()));

        entityNotification.setPlacementInfoObj(null);
        entityNotification.setCriteriaObj(null);
        entityNotification.setEligibleStudentsMap(null);
    }

    @Override
    public void onPostExecute(Object responseObject) {
        if(responseObject instanceof Integer)
        {
            Utility.showToast(Activity_NotifyMsg.this, Constants.SUCCESS_MSG_REQUEST_PROCESSED);
            Intent intent=new Intent(this,Activity_ShortlistStudents.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
        else if(responseObject instanceof String)
            Utility.showToast(Activity_NotifyMsg.this,(String)responseObject);
        else if(responseObject==null)
            Utility.showToast(Activity_NotifyMsg.this, Constants.ERROR_MSG_ANONYMOUS);
    }
}
