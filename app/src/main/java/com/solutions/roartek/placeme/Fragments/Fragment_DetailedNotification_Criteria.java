package com.solutions.roartek.placeme.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.ListItemClickCallBack;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 29-12-2016.
 */
public class Fragment_DetailedNotification_Criteria extends Fragment {
    private TextView txt_cgpa,txt_arrears,txt_xPer,txt_xiiPer,txt_placed,txt_diploma,txt_branch;

    private LinearLayout layout_container;
    private OnFragmentCreated fragmentCreatedCallBack;

    public Fragment_DetailedNotification_Criteria() {
    }

    public void setFragmentCreated(final OnFragmentCreated fragmentCreatedCallBack)
    {
        this.fragmentCreatedCallBack=fragmentCreatedCallBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_detailed_notification_criteria, container, false);
        initialiseViews(view);
        fragmentCreatedCallBack.onViewCreated(0);


        return view;
    }

    private void initialiseViews(View view)
    {
        txt_cgpa = (TextView) view.findViewById(R.id.detailedNotification_txt_cgpa);
        txt_arrears = (TextView) view.findViewById(R.id.detailedNotification_txt_arrears);
        txt_xPer = (TextView) view.findViewById(R.id.detailedNotification_txt_xPer);
        txt_xiiPer = (TextView) view.findViewById(R.id.detailedNotification_txt_xiiPer);
        txt_placed = (TextView) view.findViewById(R.id.detailedNotification_txt_placed);
        txt_diploma = (TextView) view.findViewById(R.id.detailedNotification_txt_diploma);
        txt_branch = (TextView) view.findViewById(R.id.detailedNotification_txt_branch);

        layout_container= (LinearLayout) view.findViewById(R.id.frag_criteria_container);
        layout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCreatedCallBack.onViewCreated(5);
            }
        });
    }

    public TextView getTxt_cgpa() {
        return txt_cgpa;
    }

    public void setTxt_cgpa(TextView txt_cgpa) {
        this.txt_cgpa = txt_cgpa;
    }

    public TextView getTxt_arrears() {
        return txt_arrears;
    }

    public void setTxt_arrears(TextView txt_arrears) {
        this.txt_arrears = txt_arrears;
    }

    public TextView getTxt_xPer() {
        return txt_xPer;
    }

    public void setTxt_xPer(TextView txt_xPer) {
        this.txt_xPer = txt_xPer;
    }

    public TextView getTxt_xiiPer() {
        return txt_xiiPer;
    }

    public void setTxt_xiiPer(TextView txt_xiiPer) {
        this.txt_xiiPer = txt_xiiPer;
    }

    public TextView getTxt_placed() {
        return txt_placed;
    }

    public void setTxt_placed(TextView txt_placed) {
        this.txt_placed = txt_placed;
    }

    public TextView getTxt_diploma() {
        return txt_diploma;
    }

    public void setTxt_diploma(TextView txt_diploma) {
        this.txt_diploma = txt_diploma;
    }

    public TextView getTxt_branch() {
        return txt_branch;
    }

    public void setTxt_branch(TextView txt_branch) {
        this.txt_branch = txt_branch;
    }
}
