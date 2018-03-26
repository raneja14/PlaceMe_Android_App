package com.solutions.roartek.placeme.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;

import com.solutions.roartek.placeme.Adapter.Adapter_UserAccount;
import com.solutions.roartek.placeme.Common.Constants;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.GenericActivityDelegate;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Delegate.ResponseDelegate;
import com.solutions.roartek.placeme.Domain.Entity_College;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.Fragments.Fragment_MyProfile;
import com.solutions.roartek.placeme.Fragments.Fragment_Password;
import com.solutions.roartek.placeme.R;
import com.solutions.roartek.placeme.Service.AppManager;
import com.solutions.roartek.placeme.Service.AppManagerImpl;
import com.solutions.roartek.placeme.Service.PreferenceManager;
import com.solutions.roartek.placeme.Service.PreferenceManagerImpl;

import java.util.Date;

/**
 * Created by Swathi.K on 21-12-2016.
 */
public class Activity_UserAccount extends AppCompatActivity implements GenericActivityDelegate, ResponseDelegate, OnFragmentCreated {

    private TabLayout userAccountTab;
    private ViewPager userAccountViewPager;
    private Adapter_UserAccount userAccountAdapter;
    private Fragment_MyProfile fragment_myProfile;
    private Fragment_Password fragment_password;
    private PreferenceManager preferenceManager;
    private Entity_Staff entityStaff;
    private Entity_College entityCollege;
    private AppManager appManager;
    private int CURRENT_TAB_POSITION;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        initialiseViews();
        getObjectFromPreferences();
        setUserAccountAdapter();
        setPreferences();
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
    public void initialiseViews() {
        userAccountTab = (TabLayout) findViewById(R.id.userAccount_tabLayout);
        userAccountViewPager = (ViewPager) findViewById(R.id.userAccount_ViewPager);

        Bundle bundle = getIntent().getExtras();
        CURRENT_TAB_POSITION = bundle.getInt("currentTab");
    }

    private void setUserAccountAdapter() {
        fragment_myProfile = new Fragment_MyProfile();
        fragment_password = new Fragment_Password();

        fragment_myProfile.setFragmentCreated(this);

        userAccountAdapter = new Adapter_UserAccount(getSupportFragmentManager(), Activity_UserAccount.this, fragment_myProfile, fragment_password);
        userAccountViewPager.setAdapter(userAccountAdapter);
        userAccountViewPager.setCurrentItem(CURRENT_TAB_POSITION);
        userAccountTab.setupWithViewPager(userAccountViewPager);
        onAccountTabClick();
    }

    private void onAccountTabClick() {
        userAccountViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CURRENT_TAB_POSITION = position;
                setPreferences();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setPreferences() {
        String operation[] = new String[]{Constants.OPERATION_UPDATE_USER, Constants.OPERATION_UPDATE_PASSWORD};
        preferenceManager.setPreferences(Constants.PREFERANCE_OPERATION_MODE_KRY, operation[CURRENT_TAB_POSITION], false);
    }

    @Override
    public void getObjectFromPreferences() {
        preferenceManager = new PreferenceManagerImpl(Activity_UserAccount.this);
        entityStaff = (Entity_Staff) preferenceManager.getPrferences(Entity_Staff.class, Constants.PREFERANCE_STAFF_KEY, true);
        entityCollege = (Entity_College) preferenceManager.getPrferences(Entity_College.class, Constants.PREFERANCE_COLLEGE_OBJECT_KEY, true);
    }

    @Override
    public void onProceed() {
        if (isActivityValidated()) {
            if (appManager == null)
                appManager = new AppManagerImpl();

            switch (CURRENT_TAB_POSITION) {
                case 0:
                    entityStaff.setStaffName(fragment_myProfile.getInp_staffName().getText().toString().trim());
                    entityStaff.setContact(fragment_myProfile.getInp_contact().getText().toString().trim());
                    entityStaff.setEmail(fragment_myProfile.getInp_email().getText().toString().trim());
                    entityStaff.setLastModifiedOn(Utility.getCurrentDate());
                    break;
                case 1:
                    String pwd = fragment_password.getInp_newPasswordTxt().getText().toString().trim();
                    entityStaff.setPassword(Utility.getMD5Value(pwd));
                    entityStaff.setLastModifiedOn(Utility.getCurrentDate());
                    break;
            }
            appManager.executeDBCalls(Activity_UserAccount.this, entityStaff, this);
        }
    }

    @Override
    public void onPostExecute(Object responseObject) {
        if (responseObject instanceof Integer) {
            Utility.showToast(Activity_UserAccount.this, Constants.SUCCESS_MSG_REQUEST_PROCESSED);
            preferenceManager.setPreferences(Constants.PREFERANCE_STAFF_KEY, entityStaff, true);
            resetActivity();
        } else if (responseObject instanceof String)
            Utility.showToast(Activity_UserAccount.this, (String) responseObject);
        else if (responseObject == null)
            Utility.showToast(Activity_UserAccount.this, Constants.ERROR_MSG_ANONYMOUS);
    }

    private void resetActivity() {
        fragment_myProfile.getTxt_lastModified().setText(Utility.convertDateToString(new Date()));
        fragment_password.getInp_newPasswordTxt().setText("");
        fragment_password.getInp_confirmPasswordTxt().setText("");
        fragment_password.getOldPasswordTxt().setText("");
    }

    private boolean isActivityValidated() {
        switch (CURRENT_TAB_POSITION) {
            case 0:
                return validateProfile();
            case 1:
                return validatePassword();
        }

        return true;
    }

    private boolean validateProfile() {
        fragment_myProfile.getWrapper_name().setErrorEnabled(false);
        fragment_myProfile.getWrapper_email().setErrorEnabled(false);
        fragment_myProfile.getWrapper_contact().setErrorEnabled(false);

        if (TextUtils.isEmpty(fragment_myProfile.getInp_staffName().getText())) {
            fragment_myProfile.getWrapper_name().setError("");
            fragment_myProfile.getWrapper_name().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_myProfile.getInp_staffName().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_myProfile.getInp_email().getText())) {
            fragment_myProfile.getWrapper_email().setError("");
            fragment_myProfile.getWrapper_email().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_myProfile.getInp_email().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_myProfile.getInp_contact().getText())) {
            fragment_myProfile.getWrapper_contact().setError("");
            fragment_myProfile.getWrapper_contact().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_myProfile.getInp_contact().requestFocus();
            return false;
        } else if (fragment_myProfile.getInp_staffName().getText().toString().trim().length() < 5) {
            fragment_myProfile.getWrapper_name().setError("");
            fragment_myProfile.getWrapper_name().setError(Constants.VALIDATION_MSG_MINIMUM_PWD_LENGTH);
            fragment_myProfile.getInp_staffName().requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(fragment_myProfile.getInp_email().getText().toString().trim()).matches()) {
            fragment_myProfile.getWrapper_email().setError("");
            fragment_myProfile.getWrapper_email().setError(Constants.VALIDATION_MSG_INVALID_EMAIL_PATTERN);
            fragment_myProfile.getInp_email().requestFocus();
            return false;
        } else if (fragment_myProfile.getInp_contact().getText().toString().trim().length() != 10) {
            fragment_myProfile.getWrapper_contact().setError("");
            fragment_myProfile.getWrapper_contact().setError(Constants.VALIDATION_MSG_MINIMUM_PHONE_LENGTH);
            fragment_myProfile.getInp_contact().requestFocus();
            return false;
        }
        return isUserInfoChanged();
    }

    private boolean isUserInfoChanged() {
        if (!entityStaff.getStaffName().equals(fragment_myProfile.getInp_staffName().getText().toString().trim()))
            return true;
        else if (!entityStaff.getEmail().equals(fragment_myProfile.getInp_email().getText().toString().trim()))
            return true;
        else if (!entityStaff.getContact().equals(fragment_myProfile.getInp_contact().getText().toString().trim()))
            return true;

        Utility.showToast(Activity_UserAccount.this, Constants.VALIDATION_MSG_NO_CHANGE);
        return false;
    }

    private boolean validatePassword() {
        fragment_password.getWrapper_oldPassword().setErrorEnabled(false);
        fragment_password.getWrapper_newPassword().setErrorEnabled(false);
        fragment_password.getWrapper_confirmPassword().setErrorEnabled(false);

        if (TextUtils.isEmpty(fragment_password.getOldPasswordTxt().getText())) {
            fragment_password.getWrapper_oldPassword().setError("");
            fragment_password.getWrapper_oldPassword().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_password.getOldPasswordTxt().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_password.getInp_newPasswordTxt().getText())) {
            fragment_password.getWrapper_newPassword().setError("");
            fragment_password.getWrapper_newPassword().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_password.getInp_newPasswordTxt().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fragment_password.getInp_confirmPasswordTxt().getText())) {
            fragment_password.getWrapper_confirmPassword().setError("");
            fragment_password.getWrapper_confirmPassword().setError(Constants.VALIDATION_MSG_BLANK_FIELD);
            fragment_password.getInp_confirmPasswordTxt().requestFocus();
            return false;
        } else if (fragment_password.getInp_newPasswordTxt().getText().toString().trim().length() < 5) {
            fragment_password.getWrapper_newPassword().setError("");
            fragment_password.getWrapper_newPassword().setError(Constants.VALIDATION_MSG_MINIMUM_PWD_LENGTH);
            fragment_password.getInp_newPasswordTxt().requestFocus();
            return false;
        } else if (!fragment_password.getInp_newPasswordTxt().getText().toString().trim().equals(fragment_password.getInp_confirmPasswordTxt().getText().toString().trim())) {
            fragment_password.getWrapper_newPassword().setError("");
            fragment_password.getWrapper_newPassword().setError(Constants.VALIDATION_MSG_PASSWORD_NO_MATCH);
            fragment_password.getWrapper_confirmPassword().setError(Constants.VALIDATION_MSG_PASSWORD_NO_MATCH);
            fragment_password.getInp_newPasswordTxt().requestFocus();
            return false;
        } else {
            String encryptPwd = Utility.getMD5Value(fragment_password.getInp_oldPasswordTxt().getText().toString().trim());
            if (!entityStaff.getPassword().equals(encryptPwd)) {
                fragment_password.getWrapper_oldPassword().setError("");
                fragment_password.getWrapper_oldPassword().setError(Constants.VALIDATION_MSG_WRONG_OLD_PASSWORD);
                fragment_password.getInp_oldPasswordTxt().requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onViewCreated(int viewCode) {
        if (viewCode == 0)
            populateProfileViews();
    }

    private void populateProfileViews() {
        fragment_myProfile.getTxt_staffId().setText(String.valueOf(entityStaff.getStaffID()));
        fragment_myProfile.getInp_staffName().setText(entityStaff.getStaffName());
        fragment_myProfile.getInp_contact().setText(entityStaff.getContact());
        fragment_myProfile.getInp_email().setText(entityStaff.getEmail());
        fragment_myProfile.getTxt_createdOn().setText(Utility.convertDateToString(entityStaff.getCreatedOn()));
        fragment_myProfile.getTxt_lastModified().setText(Utility.getUIDate(entityStaff.getLastModifiedOn()));
        fragment_myProfile.getTxt_collegeName().setText(entityCollege.getCollegeShortName());
    }
}