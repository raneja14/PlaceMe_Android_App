package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.solutions.roartek.placeme.Adapter.Adapter_ShortlistStudents;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Component.Shortlist_Criteria;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.Fragments.Fragment_Branches;
import com.solutions.roartek.placeme.Fragments.Fragment_Criteria;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by Swathi.K on 09-12-2016.
 */
public class Activity_ShortlistStudents extends AppCompatActivity implements GenericActivityDelegate,ResponseDelegate {
    private TabLayout tabLayout_shortlist;
    private ViewPager viewPager_shortlist;
    private Adapter_ShortlistStudents adapter_shortlistStudents;
    private Fragment_Branches fragment_branches;
    private Fragment_Criteria fragment_criteria;
    private PreferenceManager preferenceManager;
    private AppManager appManager;
    private Entity_Staff entityStaff;
    private String CURRENT_OPERATION, companyMapJSON;
    private List checkedBranchList;
    private Shortlist_Criteria shortlistCriteria;
    private MenuItem menuItem_checkAll,menuItem_unCheckAll;
    private boolean areAllSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortlist_students);

        initialiseViews();
        getObjectFromPreferences();
        onProceed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        menuItem_checkAll=  menu.findItem(R.id.checkALL);
        menuItem_unCheckAll=  menu.findItem(R.id.unCheckALL);
        onTabChange();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                onProceed();
                return true;
            case R.id.checkALL:
                fragment_branches.checkAll();
                displayMenuItem(false, true);
                areAllSelected = true;
                return true;
            case R.id.unCheckALL:
                fragment_branches.unCheckAll();
                displayMenuItem(true, false);
                areAllSelected = false;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayMenuItem(boolean checkAll,boolean unCheckAll) {
        menuItem_checkAll.setVisible(checkAll);
        menuItem_unCheckAll.setVisible(unCheckAll);
    }

    @Override
    public void initialiseViews() {
        viewPager_shortlist = (ViewPager) findViewById(R.id.ss_ViewPager);
        tabLayout_shortlist = (TabLayout) findViewById(R.id.ss_tab_layout);
        CURRENT_OPERATION=Constants.OPERATION_CRITERIA_FORM;
        appManager=new AppManagerImpl();

    }

    private void onTabChange() {
        viewPager_shortlist.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1)
                    displayMenuItem(!areAllSelected, areAllSelected);
                else
                    displayMenuItem(false, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void setPreferences() {
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, CURRENT_OPERATION, false);
    }

    @Override
    public void getObjectFromPreferences() {
        preferenceManager=new PreferenceManagerImpl(Activity_ShortlistStudents.this);
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
    }

    @Override
    public void onProceed() {
        setPreferences();
        switch (CURRENT_OPERATION) {
            case Constants.OPERATION_CRITERIA_FORM:
                appManager.executeDBCalls(Activity_ShortlistStudents.this, entityStaff.getAppConfigure(), this);
                break;
            case Constants.OPERATION_SHORTLIST_STUDENTS:
                if (isActivityValidated()) {
                    setCriteriaObj();
                    appManager.executeDBCalls(Activity_ShortlistStudents.this, shortlistCriteria, this);
                }
                break;
        }
    }

    private void setCriteriaObj()
    {
        shortlistCriteria=new Shortlist_Criteria();
        shortlistCriteria.setBatch(entityStaff.getAppConfigure().getCurrentBatch());
        shortlistCriteria.setDegree(entityStaff.getAppConfigure().getCurrentDegree());
        shortlistCriteria.setX(Double.parseDouble(fragment_criteria.getInp_x().getText().toString()));
        shortlistCriteria.setXII(Double.parseDouble(fragment_criteria.getInp_xii().getText().toString()));
        shortlistCriteria.setCgpa(Double.parseDouble(fragment_criteria.getInp_cgpa().getText().toString()));
        shortlistCriteria.setArrears(Integer.parseInt(fragment_criteria.getInp_arrears().getText().toString()));
        shortlistCriteria.setIsPlaced(fragment_criteria.getSwitch_status().isChecked());
        shortlistCriteria.setIsDiplomaAllowed(fragment_criteria.getSwitch_diploma().isChecked());
        shortlistCriteria.setBranch(checkedBranchList);
    }

    @Override
    public void onPostExecute(Object responseObject) {

        if (responseObject instanceof Map) {
            Map responseMap = (Map) responseObject;
            if (CURRENT_OPERATION == Constants.OPERATION_CRITERIA_FORM) {
                if (responseMap.containsKey("BranchList"))
                    fragment_branches = new Fragment_Branches((List<String>) responseMap.get("BranchList"));
                fragment_criteria = new Fragment_Criteria();
                if (responseMap.containsKey("CompanyMapJSON"))
                    companyMapJSON = responseMap.get("CompanyMapJSON").toString();

                adapter_shortlistStudents = new Adapter_ShortlistStudents(getSupportFragmentManager(), Activity_ShortlistStudents.this, fragment_criteria, fragment_branches);
                viewPager_shortlist.setAdapter(adapter_shortlistStudents);
                tabLayout_shortlist.setupWithViewPager(viewPager_shortlist);

                CURRENT_OPERATION = Constants.OPERATION_SHORTLIST_STUDENTS;
            } else if (CURRENT_OPERATION == Constants.OPERATION_SHORTLIST_STUDENTS) {
                Entity_Notification entityNotification = new Entity_Notification();
                entityNotification.setStaffId(entityStaff.getStaffID());
                entityNotification.setAppConfigureObj(entityStaff.getAppConfigure());

                entityNotification.setCriteriaObj(shortlistCriteria);
                entityNotification.setEligibleStudentsMap((Map<String, Integer>) responseMap.get("DROPDOWN"));

                Intent intent = new Intent(Activity_ShortlistStudents.this, Activity_PlacedDetails.class);
                String notificationJson = Utility.convertObjectToJSON(entityNotification);
                intent.putExtra("NOTIFICATION_OBJ_JSON", notificationJson);
                intent.putExtra("COMPANY_MAP_JSON", companyMapJSON);
                intent.putExtra("SMS", responseMap.get("SMS").toString());
                intent.putExtra("EMAIL", responseMap.get("EMAIL").toString());
                startActivity(intent);

            }
        } else if (responseObject instanceof String)
            Utility.showToast(Activity_ShortlistStudents.this, (String) responseObject);
        else if (responseObject == null)
            Utility.showToast(Activity_ShortlistStudents.this, Constants.ERROR_MSG_ANONYMOUS);
    }

    private boolean isActivityValidated() {

        fragment_criteria.getWrapper_cgpa().setErrorEnabled(false);
        fragment_criteria.getWrapper_arrears().setErrorEnabled(false);
        fragment_criteria.getWrapper_x().setErrorEnabled(false);
        fragment_criteria.getWrapper_xii().setErrorEnabled(false);
        viewPager_shortlist.setCurrentItem(0);

        if (TextUtils.isEmpty(fragment_criteria.getInp_cgpa().getText())) {
            fragment_criteria.getWrapper_cgpa().setError("");
            fragment_criteria.getWrapper_cgpa().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_criteria.getInp_cgpa().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_criteria.getInp_arrears().getText())) {
            fragment_criteria.getWrapper_arrears().setError("");
            fragment_criteria.getWrapper_arrears().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_criteria.getInp_arrears().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_criteria.getInp_x().getText())) {
            fragment_criteria.getWrapper_x().setError("");
            fragment_criteria.getWrapper_x().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_criteria.getInp_x().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_criteria.getInp_xii().getText())) {
            fragment_criteria.getWrapper_xii().setError("");
            fragment_criteria.getWrapper_xii().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_criteria.getInp_xii().requestFocus();
            return false;
        } else {
            checkedBranchList = fragment_branches.getCheckedItems();
            if (checkedBranchList.size() < 1) {
                viewPager_shortlist.setCurrentItem(1);
                Utility.showToast(Activity_ShortlistStudents.this, Constants.VALIDATION_MSG_SELECT_BRANCH);
                return false;
            }
        }
        return true;
    }

}
