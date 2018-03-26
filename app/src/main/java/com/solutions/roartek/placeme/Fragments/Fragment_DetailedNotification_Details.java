package com.solutions.roartek.placeme.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutions.roartek.placeme.Activity.Activity_Notifications;
import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 29-12-2016.
 */
public class Fragment_DetailedNotification_Details extends Fragment {
    private TextView txt_eligible,txt_company,txt_venue,txt_date,txt_time;
    private LinearLayout layout_container;
    private OnFragmentCreated fragmentCreatedCallBack;

    public void setFragmentCreated(final OnFragmentCreated fragmentCreatedCallBack)
    {
        this.fragmentCreatedCallBack=fragmentCreatedCallBack;
    }

    public Fragment_DetailedNotification_Details() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detailed_notification_details, container, false);
        initialiseViews(view);
        fragmentCreatedCallBack.onViewCreated(1);
        return view;
    }

    private void initialiseViews(View view)
    {
        txt_eligible = (TextView) view.findViewById(R.id.detailedNotification_txt_eligible);
        txt_company = (TextView) view.findViewById(R.id.detailedNotification_txt_company);
        txt_venue = (TextView) view.findViewById(R.id.detailedNotification_txt_venue);
        txt_date = (TextView) view.findViewById(R.id.detailedNotification_txt_date);
        txt_time = (TextView) view.findViewById(R.id.detailedNotification_txt_time);

        layout_container= (LinearLayout) view.findViewById(R.id.frag_details_container);
        layout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCreatedCallBack.onViewCreated(5);
            }
        });
    }

    public TextView getTxt_eligible() {
        return txt_eligible;
    }

    public void setTxt_eligible(TextView txt_eligible) {
        this.txt_eligible = txt_eligible;
    }

    public TextView getTxt_company() {
        return txt_company;
    }

    public void setTxt_company(TextView txt_company) {
        this.txt_company = txt_company;
    }

    public TextView getTxt_venue() {
        return txt_venue;
    }

    public void setTxt_venue(TextView txt_venue) {
        this.txt_venue = txt_venue;
    }

    public TextView getTxt_date() {
        return txt_date;
    }

    public void setTxt_date(TextView txt_date) {
        this.txt_date = txt_date;
    }

    public TextView getTxt_time() {
        return txt_time;
    }

    public void setTxt_time(TextView txt_time) {
        this.txt_time = txt_time;
    }
}
