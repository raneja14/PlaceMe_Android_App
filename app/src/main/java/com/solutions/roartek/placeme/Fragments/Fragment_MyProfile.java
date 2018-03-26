package com.solutions.roartek.placeme.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 21-12-2016.
 */
public class Fragment_MyProfile extends Fragment{
    private View view;
    private EditText inp_staffName,inp_email,inp_contact;
    private TextView txt_createdOn,txt_lastModified,txt_staffId,txt_collegeName;
    private TextInputLayout wrapper_name,wrapper_email,wrapper_contact;
    private Entity_Staff entityStaff;
    private String collegeName;
    private OnFragmentCreated fragmentCreatedCallBack;

    public Fragment_MyProfile() {
    }

   /* public Fragment_MyProfile(Entity_Staff entityStaff, String collegeName) {
        this.entityStaff = entityStaff;
        this.collegeName = collegeName;
    }*/

    public void setFragmentCreated(final OnFragmentCreated fragmentCreatedCallBack)
    {
        this.fragmentCreatedCallBack=fragmentCreatedCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view  =  inflater.inflate(R.layout.fragment_myprofile,container,false);
        initialiseIds();
        fragmentCreatedCallBack.onViewCreated(0);
      //  populateViews();
        return view;
    }

    /*private void populateViews() {
        txt_staffId.setText(String.valueOf(entityStaff.getStaffID()));
        inp_staffName.setText(entityStaff.getStaffName());
        inp_contact.setText(entityStaff.getContact());
        inp_email.setText(entityStaff.getEmail());
        txt_createdOn.setText(Utility.convertDateToString(entityStaff.getCreatedOn()));
        txt_lastModified.setText(Utility.getUIDate(entityStaff.getLastModifiedOn()));
        txt_collegeName.setText(collegeName);
    }*/

    public void initialiseIds()
    {
        txt_staffId = (TextView) view.findViewById(R.id.myProfile_txt_staffId);
        txt_createdOn = (TextView) view.findViewById(R.id.myProfile_txt_created_on);
        txt_lastModified = (TextView) view.findViewById(R.id.myProfile_txt_last_modified);
        txt_collegeName = (TextView) view.findViewById(R.id.myProfile_txt_collegeName);

        inp_staffName = (EditText) view.findViewById(R.id.myProfile_inp_staff_name);
        inp_email = (EditText) view.findViewById(R.id.myProfile_inp_email);
        inp_contact = (EditText) view.findViewById(R.id.myProfile_inp_contact);

        wrapper_name= (TextInputLayout) view.findViewById(R.id.myProfile_wrapper_staffName);
        wrapper_contact= (TextInputLayout) view.findViewById(R.id.myProfile_wrapper_contact);
        wrapper_email= (TextInputLayout) view.findViewById(R.id.myProfile_wrapper_email);
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public TextView getTxt_staffId() {
        return txt_staffId;
    }

    public void setTxt_staffId(TextView txt_staffId) {
        this.txt_staffId = txt_staffId;
    }

    public EditText getInp_staffName() {
        return inp_staffName;
    }

    public void setInp_staffName(EditText inp_staffName) {
        this.inp_staffName = inp_staffName;
    }

    public EditText getInp_email() {
        return inp_email;
    }

    public void setInp_email(EditText inp_email) {
        this.inp_email = inp_email;
    }

    public EditText getInp_contact() {
        return inp_contact;
    }

    public void setInp_contact(EditText inp_contact) {
        this.inp_contact = inp_contact;
    }

    public TextView getTxt_createdOn() {
        return txt_createdOn;
    }

    public void setTxt_createdOn(TextView txt_createdOn) {
        this.txt_createdOn = txt_createdOn;
    }

    public TextView getTxt_lastModified() {
        return txt_lastModified;
    }

    public void setTxt_lastModified(TextView txt_lastModified) {
        this.txt_lastModified = txt_lastModified;
    }

    public TextView getTxt_collegeName() {
        return txt_collegeName;
    }

    public void setTxt_collegeName(TextView txt_collegeName) {
        this.txt_collegeName = txt_collegeName;
    }

    public TextInputLayout getWrapper_name() {
        return wrapper_name;
    }

    public void setWrapper_name(TextInputLayout wrapper_name) {
        this.wrapper_name = wrapper_name;
    }

    public TextInputLayout getWrapper_email() {
        return wrapper_email;
    }

    public void setWrapper_email(TextInputLayout wrapper_email) {
        this.wrapper_email = wrapper_email;
    }

    public TextInputLayout getWrapper_contact() {
        return wrapper_contact;
    }

    public void setWrapper_contact(TextInputLayout wrapper_contact) {
        this.wrapper_contact = wrapper_contact;
    }

    public Entity_Staff getEntityStaff() {
        return entityStaff;
    }

    public void setEntityStaff(Entity_Staff entityStaff) {
        this.entityStaff = entityStaff;
    }
}
