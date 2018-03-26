package com.solutions.roartek.placeme.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 21-01-2017.
 */
public class Activity_Menu extends Activity implements View.OnClickListener{
    private ImageView homeImage,appConfigureImage,userProfileImage,passwordImage,logoutImage,notificationImage;
    private ViewGroup layout_main;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeViews();
        setClickListeners();
    }

    private void initializeViews()
    {
        homeImage = (ImageView)findViewById(R.id.menu_img_home);
        userProfileImage = (ImageView)findViewById(R.id.userProfile_dialogImage);
        appConfigureImage = (ImageView)findViewById(R.id.appConfigure_dialogImage);
        passwordImage = (ImageView)findViewById(R.id.changePassword_dialogImage);
        notificationImage = (ImageView)findViewById(R.id.notification_dialogImage);
        logoutImage = (ImageView)findViewById(R.id.logout_dialogImage);

        layout_main= (ViewGroup) findViewById(R.id.menu_layout_main);
    }

    private void setClickListeners() {
        homeImage.setOnClickListener(this);
        userProfileImage.setOnClickListener(this);
        appConfigureImage.setOnClickListener(this);
        passwordImage.setOnClickListener(this);
        notificationImage.setOnClickListener(this);
        logoutImage.setOnClickListener(this);
        layout_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appConfigure_dialogImage:
                startActivity(new Intent(Activity_Menu.this, Activity_AppConfigure.class));
                finish();
                break;
            case R.id.userProfile_dialogImage:
                Intent myProfileIntent = new Intent(Activity_Menu.this, Activity_UserAccount.class);
                myProfileIntent.putExtra("currentTab", 0);
                startActivity(myProfileIntent);
                finish();
                overridePendingTransition(R.anim.pull_from_top_to_main, R.anim.pull_from_main_to_bottom);
                break;
            case R.id.changePassword_dialogImage:
                Intent changePasswordIntent = new Intent(Activity_Menu.this, Activity_UserAccount.class);
                changePasswordIntent.putExtra("currentTab", 1);
                startActivity(changePasswordIntent);
                finish();
                overridePendingTransition(R.anim.pull_from_top_to_main, R.anim.pull_from_main_to_bottom);
                break;
            case R.id.notification_dialogImage:
                startActivity(new Intent(Activity_Menu.this, Activity_Notifications.class));
                finish();
                overridePendingTransition(R.anim.pull_from_top_to_main, R.anim.pull_from_main_to_bottom);
                break;
            case R.id.logout_dialogImage:
                Intent intent = new Intent(Activity_Menu.this, Activity_Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.pull_from_top_to_main, R.anim.pull_from_main_to_bottom);
                break;
            default:
                finish();
                break;
        }

     }
}
