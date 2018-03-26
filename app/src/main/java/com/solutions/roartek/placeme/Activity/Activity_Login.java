package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

/**
 * Created by Swathi.K on 08-12-2016.
 */
public class Activity_Login extends AppCompatActivity implements ResponseDelegate,GenericActivityDelegate {

    private EditText inp_userID,inp_password;
    private TextView txt_blank_userID,txt_blank_pwd;
    private PreferenceManager preferenceManager;
    private Entity_Staff entityStaff;
    private AppManager appManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseViews();
        setPreferences();
        getObjectFromPreferences();

    }

  @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.save:
                onProceed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onProceed() {

        if(isActivityValidated())
        {
            String password= Utility.getMD5Value(inp_password.getText().toString().trim());
            long staffID= Long.parseLong(inp_userID.getText().toString().trim());
            if(entityStaff==null)
            {
                entityStaff=new Entity_Staff(staffID,password);
            }
            else {
                entityStaff.setStaffID(staffID);
                entityStaff.setPassword(password);
            }

            appManager=new AppManagerImpl();
            appManager.executeDBCalls(Activity_Login.this, entityStaff, this);
        }
    }

    @Override
    public void initialiseViews() {
        inp_userID = (EditText) findViewById(R.id.user_id);
        inp_password = (EditText) findViewById(R.id.password);
        txt_blank_userID = (TextView) findViewById(R.id.login_txt_blank_userID);
        txt_blank_pwd = (TextView) findViewById(R.id.login_txt_blank_pwd);
    }

    @Override
    public void setPreferences() {
        preferenceManager=new PreferenceManagerImpl(Activity_Login.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_DB_MODE_KEY, Constants.OPERATION_DB_MODE_SECONDARY, false);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, Constants.OPERATION_LOGIN, false);
    }

    @Override
    public void getObjectFromPreferences() {
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
        if (entityStaff != null)
        {     inp_userID.setText(String.valueOf(entityStaff.getStaffID()));
            inp_password.requestFocus();
        }
    }

    private boolean isActivityValidated()
    {
        txt_blank_userID.setVisibility(View.GONE);
        txt_blank_pwd.setVisibility(View.GONE);

        if(TextUtils.isEmpty(inp_userID.getText()))
        {
            txt_blank_userID.setVisibility(View.VISIBLE);
            inp_userID.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(inp_password.getText()))
        {
            txt_blank_pwd.setVisibility(View.VISIBLE);
            inp_password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onPostExecute(Object responseObject) {
        if(responseObject instanceof Entity_Staff)
        {
            entityStaff= (Entity_Staff) responseObject;
            if(entityStaff.getIsAccountActive()) {
                preferenceManager.setPreferences(Constants.PREFERANCE_STAFF_KEY, entityStaff, true);
                startActivity(new Intent(Activity_Login.this, Activity_Home.class));
                overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.pull_from_main_to_bottom);
            }
            else
                Utility.showToast(Activity_Login.this,Constants.ERROR_MSG_USER_DEACTIVATED);
        }
        else if(responseObject instanceof String)
            Utility.showToast(Activity_Login.this,(String)responseObject);
        else if(responseObject==null)
            Utility.showToast(Activity_Login.this, Constants.ERROR_MSG_ANONYMOUS);
    }
}
