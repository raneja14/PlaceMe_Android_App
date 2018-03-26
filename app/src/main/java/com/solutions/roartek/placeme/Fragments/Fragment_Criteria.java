package com.solutions.roartek.placeme.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.solutions.roartek.placeme.Common.MyConfig;
import com.solutions.roartek.placeme.Helper.InputNumberRangeHelper;
import com.solutions.roartek.placeme.R;


/**
 * Created by swathi.k on 10-12-2016.
 */
public class Fragment_Criteria extends Fragment {

    private EditText inp_cgpa,inp_x,inp_xii,inp_arrears;
    private TextInputLayout wrapper_cgpa,wrapper_x,wrapper_xii,wrapper_arrears;
    private Switch switch_status,switch_diploma;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criteria, container, false);
        setRetainInstance(true);

        inp_cgpa = (EditText) view.findViewById(R.id.criteria_cgpa);
        inp_cgpa.setFilters(new InputFilter[]{new InputNumberRangeHelper(MyConfig.NUMBER_MIN_VALUE, MyConfig.CGPA_MAX_VALUE, MyConfig.CGPA_MAX_LENGTH,MyConfig.IS_CGPA_DECIMAL)});

        inp_x = (EditText) view.findViewById(R.id.criteria_x_per);
        inp_x.setFilters(new InputFilter[]{new InputNumberRangeHelper(MyConfig.NUMBER_MIN_VALUE, MyConfig.X_MAX_VALUE, MyConfig.X_MAX_LENGTH,MyConfig.IS_X_DECIMAL)});

        inp_xii = (EditText) view.findViewById(R.id.criteria_xii_per);
        inp_xii.setFilters(new InputFilter[]{new InputNumberRangeHelper(MyConfig.NUMBER_MIN_VALUE, MyConfig.X_MAX_VALUE, MyConfig.X_MAX_LENGTH,MyConfig.IS_X_DECIMAL)});

        inp_arrears = (EditText) view.findViewById(R.id.criteria_arrears);
        inp_arrears.setFilters(new InputFilter[]{new InputNumberRangeHelper(MyConfig.NUMBER_MIN_VALUE, MyConfig.ARREAR_MAX_VALUE, MyConfig.ARREAR_MAX_LENGTH,MyConfig.IS_ARREARS_DECIMAL)});

        switch_status = (Switch) view.findViewById(R.id.criteria_status);
        switch_diploma = (Switch) view.findViewById(R.id.criteria_diploma);

        wrapper_cgpa= (TextInputLayout) view.findViewById(R.id.criteria_wrapper_Cgpa);
        wrapper_x= (TextInputLayout) view.findViewById(R.id.criteria_wrapper_Xpercentage);
        wrapper_xii= (TextInputLayout) view.findViewById(R.id.criteria_wrapper_Xiipercentage);
        wrapper_arrears= (TextInputLayout) view.findViewById(R.id.criteria_wrapper_Arrears);

        return view;
    }

    public EditText getInp_cgpa() {
        return inp_cgpa;
    }

    public void setInp_cgpa(EditText inp_cgpa) {
        this.inp_cgpa = inp_cgpa;
    }

    public EditText getInp_x() {
        return inp_x;
    }

    public void setInp_x(EditText inp_x) {
        this.inp_x = inp_x;
    }

    public EditText getInp_xii() {
        return inp_xii;
    }

    public void setInp_xii(EditText inp_xii) {
        this.inp_xii = inp_xii;
    }

    public EditText getInp_arrears() {
        return inp_arrears;
    }

    public void setInp_arrears(EditText inp_arrears) {
        this.inp_arrears = inp_arrears;
    }

    public Switch getSwitch_status() {
        return switch_status;
    }

    public void setSwitch_status(Switch switch_status) {
        this.switch_status = switch_status;
    }

    public Switch getSwitch_diploma() {
        return switch_diploma;
    }

    public void setSwitch_diploma(Switch switch_diploma) {
        this.switch_diploma = switch_diploma;
    }

    public TextInputLayout getWrapper_cgpa() {
        return wrapper_cgpa;
    }

    public void setWrapper_cgpa(TextInputLayout wrapper_cgpa) {
        this.wrapper_cgpa = wrapper_cgpa;
    }

    public TextInputLayout getWrapper_x() {
        return wrapper_x;
    }

    public void setWrapper_x(TextInputLayout wrapper_x) {
        this.wrapper_x = wrapper_x;
    }

    public TextInputLayout getWrapper_xii() {
        return wrapper_xii;
    }

    public void setWrapper_xii(TextInputLayout wrapper_xii) {
        this.wrapper_xii = wrapper_xii;
    }

    public TextInputLayout getWrapper_arrears() {
        return wrapper_arrears;
    }

    public void setWrapper_arrears(TextInputLayout wrapper_arrears) {
        this.wrapper_arrears = wrapper_arrears;
    }
}
