package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_College;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.Date;

/**
 * Created by Swathi.K on 14-12-2016.
 */
public class Activity_CollegeCode extends AppCompatActivity implements ResponseDelegate,GenericActivityDelegate {

    private EditText inp_CollegeCode;
    private TextView txt_blank;
    private Entity_College entityCollege;
    private PreferenceManager preferenceManager;
    private AppManager appManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_code);
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
    public void onProceed()
    {
        if(isActivityValidated())
        {
            entityCollege=(entityCollege==null)?new Entity_College(inp_CollegeCode.getText().toString().trim()):entityCollege;
            appManager.executeDBCalls(Activity_CollegeCode.this, entityCollege, this);
        }
    }

    @Override
    public void initialiseViews() {
        inp_CollegeCode = (EditText) findViewById(R.id.college_code);
        txt_blank = (TextView) findViewById(R.id.collegeCode_txt_blank);

        inp_CollegeCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MyConfig.COLLEGE_CODE_MAX_LENGTH)});
        appManager=new AppManagerImpl();
    }

    @Override
    public void setPreferences() {
        preferenceManager=new PreferenceManagerImpl(Activity_CollegeCode.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_DB_MODE_KEY, Constants.OPERATION_DB_MODE_PRIMARY, false);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, Constants.OPERATION_COLLEGE_INFO, false);
    }

    @Override
    public void getObjectFromPreferences() {
        entityCollege= (Entity_College) preferenceManager.getPrferences(Entity_College.class,Constants.PREFERANCE_COLLEGE_OBJECT_KEY,true);
        if(entityCollege!=null)
        {
            inp_CollegeCode.setText(entityCollege.getCollegeCode());
            onProceed();
        }
    }

    private boolean isActivityValidated()
    {
         if(TextUtils.isEmpty(inp_CollegeCode.getText()))
         {
             txt_blank.setVisibility(View.VISIBLE);
             inp_CollegeCode.requestFocus();
             return false;
         }
         txt_blank.setVisibility(View.GONE);
         return true;
    }

    @Override
    public void onPostExecute(Object responseObject) {

        if(responseObject instanceof Entity_College)
        {
            entityCollege= (Entity_College) responseObject;
            if(Utility.compareDates(entityCollege.getProductExpiryDate(),new Date())==1) {
                preferenceManager.setPreferences(Constants.PREFERANCE_COLLEGE_OBJECT_KEY, responseObject, true);
                startActivity(new Intent(Activity_CollegeCode.this, Activity_Login.class));
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
            }
            else
                Utility.showToast(Activity_CollegeCode.this,Constants.ERROR_MSG_PRODUCT_EXPIRED);
        }
        else
        {
            entityCollege=null;
            if (responseObject instanceof String)
                Utility.showToast(Activity_CollegeCode.this, (String) responseObject);
            else if (responseObject == null)
                Utility.showToast(Activity_CollegeCode.this, Constants.ERROR_MSG_ANONYMOUS);
        }
    }


}


