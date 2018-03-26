package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Dialog.Dialog_Home;
import com.solutions.roartek.placeme.Helper.FloatingButtonMoveHelper;
import com.solutions.roartek.placeme.R;
/**
 * Created by Swathi.K on 09-12-2016.
 */
public class Activity_Home extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener,OnFragmentCreated {

    private TextView icon_RegisterCompany, icon_ViewCompany, icon_ShortlistStudents, icon_Statistics;
    private FloatingActionButton floatingHomeButton;
    private Bitmap blurredBitmap;
    private FloatingButtonMoveHelper floatingButtonMoveHelper;
    private ViewGroup layout_content, layout_contentAll, layout_blur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeIds();
        startAnimations();
        setClickListeners();
    }

    public void initializeIds() {
        icon_RegisterCompany = (TextView) findViewById(R.id.icon_register);
        icon_ViewCompany = (TextView) findViewById(R.id.icon_view_company);
        icon_ShortlistStudents = (TextView) findViewById(R.id.icon_shortlist_student);
        icon_Statistics = (TextView) findViewById(R.id.icon_statistics);
        floatingHomeButton = (FloatingActionButton) findViewById(R.id.floatingHomeBtn);

        layout_blur = (ViewGroup) findViewById(R.id.home_layout_blur);
        layout_content = (ViewGroup) findViewById(R.id.home_layout_content);
        layout_contentAll = (ViewGroup) findViewById(R.id.home_layout_content_all);

    }

    private void startAnimations() {
        Utility.startAnimation(Activity_Home.this, icon_RegisterCompany, R.anim.fade_in);
        Utility.startAnimation(Activity_Home.this, icon_ViewCompany, R.anim.fade_in);
        Utility.startAnimation(Activity_Home.this, icon_ShortlistStudents, R.anim.fade_in);
        Utility.startAnimation(Activity_Home.this, icon_Statistics, R.anim.fade_in);
    }

    private void setClickListeners() {
        icon_RegisterCompany.setOnClickListener(this);
        icon_ViewCompany.setOnClickListener(this);
        icon_ShortlistStudents.setOnClickListener(this);
        icon_Statistics.setOnClickListener(this);
        floatingHomeButton.setOnTouchListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_register:
                startActivity(new Intent(Activity_Home.this, Activity_RegisterCompany.class));
                break;
            case R.id.icon_view_company:
                startActivity(new Intent(Activity_Home.this, Activity_ViewCompany.class));
                break;
            case R.id.icon_shortlist_student:
                startActivity(new Intent(Activity_Home.this, Activity_ShortlistStudents.class));
                break;
            case R.id.icon_statistics:
                startActivity(new Intent(Activity_Home.this, Activity_Statistics.class));
                break;
            default:
                break;
        }
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (floatingButtonMoveHelper == null)
            floatingButtonMoveHelper = new FloatingButtonMoveHelper(floatingHomeButton, this);

        return floatingButtonMoveHelper.onTouchListener(v, event);
    }


    @Override
    public void onViewCreated(int viewCode) {
        floatingHomeButton.setVisibility(View.GONE);
        startActivity(new Intent(Activity_Home.this, Activity_Menu.class));
        overridePendingTransition(R.anim.fade_in, R.anim.none);
        blurHomeView();
    }

    private void blurHomeView() {
        layout_content.setDrawingCacheEnabled(true);
        layout_content.buildDrawingCache();
        Bitmap bitmap = layout_content.getDrawingCache();
        blurredBitmap = Utility.blurimage(bitmap, Activity_Home.this);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), blurredBitmap);
        Utility.setBackgorund(layout_blur, bitmapDrawable);
        layout_contentAll.setVisibility(View.GONE);
        layout_blur.setVisibility(View.VISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            floatingHomeButton.setVisibility(View.VISIBLE);
            Utility.setBackgorund(layout_blur, null);
            layout_blur.setVisibility(View.GONE);
            layout_contentAll.setVisibility(View.VISIBLE);
        }
    }
}

