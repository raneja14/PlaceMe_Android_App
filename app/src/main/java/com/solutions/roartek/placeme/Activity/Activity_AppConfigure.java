package com.solutions.roartek.placeme.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Swathi.K on 22-12-2016.
 */
public class Activity_AppConfigure extends AppCompatActivity implements GenericActivityDelegate,ResponseDelegate{
    private Spinner spinner_batch;
    private Spinner spinner_degree;
    private List<String> batchList;
    private ArrayAdapter<String> batchAdapter;
    private ArrayAdapter<String> degreeAdapter;
    private PreferenceManager preferenceManager;
    private Entity_Staff entityStaff;
    private AppManager appManager;
    private String CURRENT_OPERATION;
    private Map<String,List<String>> batchdegreeMap;
    private boolean isFirstLoad;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_configure);

        initialiseViews();
        setPreferences();
        getObjectFromPreferences();
        onProceed();
    }

    private void onBatchSelected()
    {
        spinner_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedBatch = parent.getSelectedItem().toString();
                setDegreeAdapter(batchdegreeMap.get(selectedBatch));

                if(isFirstLoad)
                {
                    int spinnerDegreePosition = degreeAdapter.getPosition(entityStaff.getAppConfigure().getCurrentDegree());
                    spinner_degree.setSelection(spinnerDegreePosition);
                    isFirstLoad=false;
                }
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
                CURRENT_OPERATION=Constants.OPERATION_APP_CONFIGURE;
                setPreferences();
                onProceed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void initialiseViews() {
        spinner_batch = (Spinner) findViewById(R.id.batch);
        spinner_degree = (Spinner) findViewById(R.id.degree);

        CURRENT_OPERATION=Constants.OPERATION_BATCH_DEGREES;
        appManager=new AppManagerImpl();
        isFirstLoad=true;
    }

    @Override
    public void setPreferences() {
        preferenceManager=new PreferenceManagerImpl(Activity_AppConfigure.this);
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, CURRENT_OPERATION, false);
    }

    @Override
    public void getObjectFromPreferences() {
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
    }

    @Override
    public void onProceed() {
        switch (CURRENT_OPERATION)
        {
            case Constants.OPERATION_BATCH_DEGREES:
                appManager.executeDBCalls(Activity_AppConfigure.this,entityStaff.getAppConfigure(),this);
                break;
            case Constants.OPERATION_APP_CONFIGURE:
                entityStaff.getAppConfigure().setCurrentDegree(spinner_degree.getSelectedItem().toString());
                entityStaff.getAppConfigure().setCurrentBatch(spinner_batch.getSelectedItem().toString());
                appManager.executeDBCalls(Activity_AppConfigure.this,entityStaff,this);
                break;
        }
    }


    @Override
    public void onPostExecute(Object responseObject) {
        if(responseObject instanceof Map) {
            batchdegreeMap = (Map<String, List<String>>) responseObject;
            if (batchdegreeMap.size() > 0) {
                batchList = new ArrayList<>(batchdegreeMap.keySet());
                batchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, batchList);
                spinner_batch.setAdapter(batchAdapter);

                setDegreeAdapter(batchdegreeMap.get(batchAdapter.getItem(0)));
                onBatchSelected();

                if(entityStaff.getAppConfigure()!=null) {
                    int spinnerBacthPosition = batchAdapter.getPosition(entityStaff.getAppConfigure().getCurrentBatch());
                    spinner_batch.setSelection(spinnerBacthPosition);
                }
            }
        }
         else if(responseObject instanceof Integer)
         {
             Utility.showToast(Activity_AppConfigure.this, Constants.SUCCESS_MSG_REQUEST_PROCESSED);
             preferenceManager.setPreferences(Constants.PREFERANCE_STAFF_KEY,entityStaff,true);
         }
         else if(responseObject instanceof String)
             Utility.showToast(Activity_AppConfigure.this,(String)responseObject);
         else if(responseObject==null)
             Utility.showToast(Activity_AppConfigure.this, Constants.ERROR_MSG_ANONYMOUS);
        }



    private void setDegreeAdapter(List<String> degreeList)
    {
        degreeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, degreeList);
        spinner_degree.setAdapter(degreeAdapter);
    }
}
