package com.solutions.roartek.placeme.Activity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.R;

public class Activity_Logo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setLogo(R.drawable.lion);

        setContentView(R.layout.activity_logo);
        ImageView appLogo= (ImageView) findViewById(R.id.logo);

        Utility.startAnimation(Activity_Logo.this,appLogo,R.anim.logo_entry);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Activity_Logo.this, Activity_CollegeCode.class));
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
            }
        }, SPLASH_TIME_OUT);
    }
}
