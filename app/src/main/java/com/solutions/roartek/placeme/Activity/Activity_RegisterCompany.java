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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_College;
import com.solutions.roartek.placeme.Domain.Entity_Company;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.Fragments.Fragment_DatePicker;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.Date;

/**
 * Created by Swathi.K on 09-12-2016.
 */
public class Activity_RegisterCompany extends AppCompatActivity implements ResponseDelegate,GenericActivityDelegate, View.OnClickListener {

    private EditText inp_companyName,inp_hostCollege,inp_estDOR;
    private TextInputLayout wrapper_name,wrapper_host,wrapper_dor;
    private Spinner inp_companyTypeSpinner,inp_companyStatusSpinner;
    private ImageView img_dor;
    private Bundle extras;
    private PreferenceManager preferenceManager;
    private AppManager appManager;
    private Entity_Staff entityStaff;
    private Entity_Company entityCompany;
    private Entity_College entityCollege;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        initialiseViews();
        getObjectFromPreferences();
        getCompanyFromBundle();

    }

    private void getCompanyFromBundle() {
        extras = getIntent().getBundleExtra(Constants.BUNDLE_KEY);
        if (extras != null) {
            entityCompany = (Entity_Company) Utility.convertJSONToObject(Entity_Company.class, (String) extras.get(Constants.BUNDLE_COMPANY_KEY));
            popluateActivity();
        } else {
            inp_hostCollege.setText(entityCollege.getCollegeShortName());
            setPreferences();
        }
    }

    private void popluateActivity() {
        inp_companyName.setText(entityCompany.getCompName());
        inp_hostCollege.setText(entityCompany.getHost());
        inp_estDOR.setText(Utility.getUIDate(entityCompany.getDOR()));
        inp_companyTypeSpinner.setSelection(entityCompany.getType(), true);
        inp_companyStatusSpinner.setSelection(entityCompany.getStatus(), true);

        if(!isCompanyEditable(entityCompany.getDOR()))
            disableViews();
    }

    private boolean isCompanyEditable(String compDOR) {
        return Utility.compareDates(Utility.convertStringToDate(compDOR), new Date()) >= 0 ? true : false;
    }

    private void disableViews() {
        inp_companyName.setEnabled(false);
        inp_hostCollege.setEnabled(false);
        img_dor.setEnabled(false);
        inp_companyStatusSpinner.setEnabled(false);
        inp_companyTypeSpinner.setEnabled(false);
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
            boolean executeDbCAll=true;
            if(entityCompany==null)
            {
                entityCompany=new Entity_Company();
                entityCompany.setCompName(inp_companyName.getText().toString().trim());
                entityCompany.setHost(inp_hostCollege.getText().toString().trim());
                entityCompany.setDOR(Utility.getDbDate(inp_estDOR.getText().toString()));
                entityCompany.setType(inp_companyTypeSpinner.getSelectedItemPosition());
                entityCompany.setStatus(inp_companyStatusSpinner.getSelectedItemPosition());
                entityCompany.setStaffID(entityStaff.getStaffID());
                entityCompany.setAppConfigure(entityStaff.getAppConfigure());

            }
            else
            {
                if(isCompanyChanged() && isCompanyDetailsChanged())
                {
                    entityCompany.setCompName(inp_companyName.getText().toString().trim());
                    entityCompany.setHost(inp_hostCollege.getText().toString().trim());
                    entityCompany.setDOR(Utility.getDbDate(inp_estDOR.getText().toString()));
                    entityCompany.setType(inp_companyTypeSpinner.getSelectedItemPosition());
                    entityCompany.setStatus(inp_companyStatusSpinner.getSelectedItemPosition());
                    entityCompany.setAffectedTable(Constants.AFFECTED_TABLE_BOTH);
                }
                else if(isCompanyChanged())
                {
                    entityCompany.setCompName(inp_companyName.getText().toString().trim());
                    entityCompany.setAffectedTable(Constants.AFFECTED_TABLE_COMPANY);
                }
                else if(isCompanyDetailsChanged())
                {
                    entityCompany.setHost(inp_hostCollege.getText().toString().trim());
                    entityCompany.setDOR(Utility.getDbDate(inp_estDOR.getText().toString()));
                    entityCompany.setType(inp_companyTypeSpinner.getSelectedItemPosition());
                    entityCompany.setStatus(inp_companyStatusSpinner.getSelectedItemPosition());
                    entityCompany.setAffectedTable(Constants.AFFECTED_TABLE_COMPANY_DETAILS);
                }
                else
                {
                    Utility.showToast(Activity_RegisterCompany.this,Constants.VALIDATION_MSG_NO_CHANGE);
                    executeDbCAll=false;
                }
            }

            if(executeDbCAll)
            {
                appManager=new AppManagerImpl();
                appManager.executeDBCalls(Activity_RegisterCompany.this, entityCompany, this);
            }
        }
    }

    private boolean isCompanyChanged()
    {
        return !entityCompany.getCompName().equals(inp_companyName.getText().toString().trim());
    }

    private boolean isCompanyDetailsChanged()
    {
        if(!entityCompany.getHost().equals(inp_hostCollege.getText().toString().trim()))
            return true;
        else if(entityCompany.getType()!=inp_companyTypeSpinner.getSelectedItemPosition())
            return true;
        else if(entityCompany.getStatus()!=inp_companyStatusSpinner.getSelectedItemPosition())
            return true;
        else if(!Utility.getUIDate(entityCompany.getDOR()).equals(inp_estDOR.getText().toString()))
            return true;
        return false;
    }

    @Override
    public void initialiseViews() {
        inp_companyName = (EditText) findViewById(R.id.company_name);
        inp_hostCollege = (EditText) findViewById(R.id.host_college);
        inp_estDOR = (EditText) findViewById(R.id.placedDetails_date);
        inp_companyTypeSpinner = (Spinner) findViewById(R.id.type_spinner);
        inp_companyStatusSpinner = (Spinner) findViewById(R.id.studentStatus_spinner);
        img_dor = (ImageView) findViewById(R.id.img_dor);

        wrapper_name = (TextInputLayout) findViewById(R.id.register_wrapper_companyName);
        wrapper_host = (TextInputLayout) findViewById(R.id.register_wrapper_hostCollege);
        wrapper_dor = (TextInputLayout) findViewById(R.id.register_wrapper_DOR);

        img_dor.setOnClickListener(this);
    }

    @Override
    public void setPreferences() {

        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, Constants.OPERATION_REGISTER_COMPANY, false);
    }

    @Override
    public void getObjectFromPreferences() {
        preferenceManager=new PreferenceManagerImpl(Activity_RegisterCompany.this);
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
        entityCollege = (Entity_College) preferenceManager.getPrferences(Entity_College.class, Constants.PREFERANCE_COLLEGE_OBJECT_KEY, true);
    }

    private boolean isActivityValidated()
    {
        wrapper_name.setErrorEnabled(false);
        wrapper_host.setErrorEnabled(false);
        wrapper_dor.setErrorEnabled(false);
        if(TextUtils.isEmpty(inp_companyName.getText()))
        {
            wrapper_name.setError("");
            wrapper_name.setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            inp_companyName.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(inp_hostCollege.getText()))
        {
            wrapper_host.setError("");
            wrapper_host.setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            inp_hostCollege.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(inp_estDOR.getText()))
        {
            wrapper_dor.setError("");
            wrapper_dor.setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            img_dor.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onPostExecute(Object responseObject) {

        if(responseObject instanceof Integer)
        {
            switch ((int)responseObject)
            {
                case 1:
                    Utility.showToast(Activity_RegisterCompany.this, Constants.SUCCESS_MSG_REQUEST_PROCESSED);
                    if(extras==null)
                        resetActivity();
                    else {
                        String companyJson= Utility.convertObjectToJSON(entityCompany);
                        Intent intent=new Intent();
                        intent.putExtra(Constants.BUNDLE_COMPANY_KEY, companyJson);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
                case 2:
                    Utility.showToast(Activity_RegisterCompany.this,Constants.ERROR_MSG_DUPLICATE_COMPANY);
                    break;
            }
        }
        else if(responseObject instanceof String)
            Utility.showToast(Activity_RegisterCompany.this,(String)responseObject);
        else if(responseObject==null)
            Utility.showToast(Activity_RegisterCompany.this, Constants.ERROR_MSG_ANONYMOUS);
    }

    private void resetActivity()
    {
        inp_companyStatusSpinner.setSelection(0,true);
        inp_companyTypeSpinner.setSelection(0,true);
        inp_estDOR.setText("");
        inp_companyName.setText("");
        inp_hostCollege.setText(entityCollege.getCollegeShortName());
    }

    @Override
    public void onClick(View v) {
        new Fragment_DatePicker().show(getFragmentManager(), "datepicker");
    }
}


