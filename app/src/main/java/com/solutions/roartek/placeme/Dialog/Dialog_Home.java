package com.solutions.roartek.placeme.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.solutions.roartek.placeme.Activity.Activity_AppConfigure;
import com.solutions.roartek.placeme.Activity.Activity_Login;
import com.solutions.roartek.placeme.Activity.Activity_Notifications;
import com.solutions.roartek.placeme.Activity.Activity_UserAccount;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 20-12-2016.
 */
public class Dialog_Home extends DialogFragment implements View.OnClickListener {
    private LayoutInflater inflater;
    private View homeView;
    private ImageView homeImage;
    private ImageView appConfigureImage;
    private ImageView userProfileImage;
    private ImageView passwordImage;
    private ImageView logoutImage;
    private ImageView notificationImage;
    private FloatingActionButton floatingHomeButton;

    public Dialog_Home(FloatingActionButton floatingHomeButton) {
        this.floatingHomeButton = floatingHomeButton;
    }

    public Dialog_Home() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
       homeView = inflater.inflate(R.layout.dialog_home, null);

        initialiseIds();
        setClickListeners();

        AlertDialog.Builder homeBuilder = new AlertDialog.Builder(getActivity());
        homeBuilder.setView(homeView);
        AlertDialog dialog = homeBuilder.create();
        return dialog;
    }

    private void initialiseIds() {
        homeImage = (ImageView) homeView.findViewById(R.id.home_dialogImage);
        userProfileImage = (ImageView) homeView.findViewById(R.id.userProfile_dialogImage);
        appConfigureImage = (ImageView) homeView.findViewById(R.id.appConfigure_dialogImage);
        passwordImage = (ImageView) homeView.findViewById(R.id.changePassword_dialogImage);
        notificationImage = (ImageView) homeView.findViewById(R.id.notification_dialogImage);
        logoutImage = (ImageView) homeView.findViewById(R.id.logout_dialogImage);
    }

    private void setClickListeners()
    {
        homeImage.setOnClickListener(this);
        userProfileImage.setOnClickListener(this);
        appConfigureImage.setOnClickListener(this);
        passwordImage.setOnClickListener(this);
        notificationImage.setOnClickListener(this);
        logoutImage.setOnClickListener(this);
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
       floatingHomeButton.setVisibility(View.VISIBLE);
        Utility.startAnimation(getActivity(),floatingHomeButton,R.anim.fade_in);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.home_dialogImage:
                dismiss();
                break;
            case R.id.appConfigure_dialogImage:
                startActivity(new Intent(getActivity(), Activity_AppConfigure.class));
                dismiss();
                break;
            case R.id.userProfile_dialogImage:
                Intent myProfileIntent = new Intent(getActivity(), Activity_UserAccount.class);
                myProfileIntent.putExtra("currentTab", 0);
                startActivity(myProfileIntent);
                dismiss();
                break;
            case R.id.changePassword_dialogImage:
                Intent changePasswordIntent = new Intent(getActivity(), Activity_UserAccount.class);
                changePasswordIntent.putExtra("currentTab", 1);
                startActivity(changePasswordIntent);
                dismiss();break;
            case R.id.notification_dialogImage:
                startActivity(new Intent(getActivity(),Activity_Notifications.class));
                dismiss();
                break;
            case R.id.logout_dialogImage:
                Intent intent = new Intent(getActivity(), Activity_Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                dismiss();
                break;
            default:
                dismiss();
                break;

        }
        getActivity().overridePendingTransition(R.anim.pull_from_top_to_main, R.anim.pull_from_main_to_bottom);
    }
}





