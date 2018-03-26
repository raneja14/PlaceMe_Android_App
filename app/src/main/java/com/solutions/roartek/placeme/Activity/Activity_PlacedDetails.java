package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Component.PlacementInfo;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Domain.Entity_College;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.Fragments.Fragment_DatePicker;
import com.solutions.roartek.placeme.Fragments.Fragment_TimePicker;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by swathi.k on 10-12-2016.
 */
public class Activity_PlacedDetails extends AppCompatActivity implements GenericActivityDelegate,View.OnClickListener{

    private Spinner spinner_branch, spinner_company;
    private TextView txt_count,txt_spinnerError;
    private EditText inp_venue,inp_time,inp_dor;
    private TextInputLayout wrapper_venue,wrapper_time;
    private ImageView img_date,img_time;
    private Entity_Notification entityNotification;
    private ArrayAdapter<String> branchAdapter,companyAdapter;
    private TreeMap<String, LinkedTreeMap<String,String>> companyMap;
    private Map<String, Integer> shortListMap;
    private Entity_College entityCollege;
    private String emails,contacts;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_placed_details);
       initialiseViews();
       getObjectsFromIntent();
   }

    private void getObjectsFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            entityNotification = (Entity_Notification) Utility.convertJSONToObject(Entity_Notification.class, bundle.getString("NOTIFICATION_OBJ_JSON"));
            shortListMap = entityNotification.getEligibleStudentsMap();
            companyMap = (TreeMap) Utility.convertJSONToObject(TreeMap.class, bundle.getString("COMPANY_MAP_JSON"));

            emails = bundle.getString("EMAIL");
            contacts = bundle.getString("SMS");

            entityNotification.getCriteriaObj().setEligibleCount(shortListMap.get("ALL"));
            populateSpinners();
        }
    }

    private void populateSpinners() {
        branchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(shortListMap.keySet()));
        spinner_branch.setAdapter(branchAdapter);
        onBranchSelected();

        if (companyMap != null) {
            companyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(companyMap.keySet()));
            spinner_company.setAdapter(companyAdapter);
            onCompanySelected();
        }
        else {
            spinner_company.setEnabled(false);
            txt_spinnerError.setVisibility(View.VISIBLE);
        }
    }
    private void onBranchSelected() {
        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedBranch = parent.getSelectedItem().toString();
                txt_count.setText(shortListMap.get(selectedBranch).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onCompanySelected() {
        spinner_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCompany = parent.getSelectedItem().toString();
                inp_dor.setText(Utility.getUIDate(companyMap.get(selectedCompany).get("DOR")));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (isActivityValidated())
                    onProceed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isActivityValidated() {

        wrapper_venue.setErrorEnabled(false);
        wrapper_time.setErrorEnabled(false);
        if (TextUtils.isEmpty(inp_venue.getText()))
        {
            wrapper_venue.setError("");
            wrapper_venue.setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            inp_venue.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(inp_time.getText()))
        {
            wrapper_time.setError("");
            wrapper_time.setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            img_time.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onProceed() {

        if (isActivityValidated()) {
            PlacementInfo placementInfo = new PlacementInfo();
            placementInfo.setCompanyName(spinner_company.getSelectedItem().toString());
            placementInfo.setDOR(inp_dor.getText().toString());
            placementInfo.setVenue(inp_venue.getText().toString());
            placementInfo.setTime(inp_time.getText().toString());
            placementInfo.setMessage(generateMessage());

            entityNotification.setPlacementInfoObj(placementInfo);
            startNextActivity();
        }
    }

    private void startNextActivity() {
        Intent intent=new Intent(Activity_PlacedDetails.this,Activity_NotifyMsg.class);
        String notificationJson=Utility.convertObjectToJSON(entityNotification);
        intent.putExtra("NOTIFICATION_OBJ_JSON",notificationJson);
        intent.putExtra("SMS",contacts);
        intent.putExtra("EMAIL",emails);
        startActivity(intent);}

    @Override
    public void initialiseViews() {
        spinner_branch = (Spinner) findViewById(R.id.placedDetails_spinner_select_branch);
        spinner_company = (Spinner) findViewById(R.id.placedDetails_spinner_select_company);
        txt_count = (TextView) findViewById(R.id.placedDetails_txt_count);
        txt_spinnerError= (TextView) findViewById(R.id.placedDetails_txt_spinnerError);
        inp_venue = (EditText) findViewById(R.id.placedDetails_inp_venue);
        inp_time = (EditText) findViewById(R.id.placedDetails_time);
        inp_dor = (EditText) findViewById(R.id.placedDetails_date);

        img_date = (ImageView) findViewById(R.id.placedDetails_img_date);
        img_time = (ImageView) findViewById(R.id.placedDetails_img_time);

        wrapper_venue= (TextInputLayout) findViewById(R.id.placedDetails_wrapper_venue);
        wrapper_time= (TextInputLayout) findViewById(R.id.placedDetails_wrapper_time);

        img_date.setOnClickListener(this);
        img_time.setOnClickListener(this);
    }

    @Override
    public void setPreferences() {

    }

    @Override
    public void getObjectFromPreferences() {
        PreferenceManager preferenceManager=new PreferenceManagerImpl(Activity_PlacedDetails.this);
        entityCollege= (Entity_College) preferenceManager.getPrferences(Entity_College.class,Constants.PREFERANCE_COLLEGE_OBJECT_KEY,true);
    }

    private String generateMessage() {
        getObjectFromPreferences();

        String msg = MyConfig.NOTIFY_MESSAGE;
        msg = msg.replace(Constants.REPLACE_KEY_COMPANY, spinner_company.getSelectedItem().toString());
        msg = msg.replace(Constants.REPLACE_KEY_DATE, inp_dor.getText().toString());
        msg = msg.replace(Constants.REPLACE_KEY_HOME, entityCollege.getCollegeShortName());
        msg = msg.replace(Constants.REPLACE_KEY_TIME, inp_time.getText().toString());
        msg = msg.replace(Constants.REPLACE_KEY_VENUE, inp_venue.getText().toString());

        return msg;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.placedDetails_img_date:
                Fragment_DatePicker fragment_datePicker = new Fragment_DatePicker();
                fragment_datePicker.show(getFragmentManager(), "datepicker");
                break;
            case R.id.placedDetails_img_time:
                Fragment_TimePicker fragment_timePicker = new Fragment_TimePicker();
                fragment_timePicker.show(getFragmentManager(), "timepicker");
                break;
        }
    }
}
