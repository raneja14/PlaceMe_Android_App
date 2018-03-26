package com.solutions.roartek.placeme.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.solutions.roartek.placeme.Domain.Entity_Staff;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 21-12-2016.
 */
public class Fragment_Password extends Fragment {
    private EditText inp_oldPasswordTxt,inp_newPasswordTxt,inp_confirmPasswordTxt;
    private TextInputLayout wrapper_oldPassword,wrapper_newPassword,wrapper_confirmPassword;
    private View view;
    private Entity_Staff entityStaff;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view =  inflater.inflate(R.layout.fragment_password, container, false);
        initialiseIds();
        return view;
    }

    public void initialiseIds() {
        inp_oldPasswordTxt = (EditText) view.findViewById(R.id.password_inp_old_password);
        inp_newPasswordTxt = (EditText) view.findViewById(R.id.password_inp_newPassword);
        inp_newPasswordTxt.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        inp_confirmPasswordTxt = (EditText) view.findViewById(R.id.password_inp_confirm_password);
        inp_confirmPasswordTxt.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        wrapper_oldPassword= (TextInputLayout) view.findViewById(R.id.password_wrapper_oldPassword);
        wrapper_newPassword= (TextInputLayout) view.findViewById(R.id.password_wrapper_newPassword);
        wrapper_confirmPassword= (TextInputLayout) view.findViewById(R.id.password_wrapper_confirmNewPassword);
    }

    public EditText getOldPasswordTxt() {
        return inp_oldPasswordTxt;
    }

    public void setOldPasswordTxt(EditText oldPasswordTxt) {
        this.inp_oldPasswordTxt = oldPasswordTxt;
    }

    public EditText getInp_newPasswordTxt() {
        return inp_newPasswordTxt;
    }

    public void setInp_newPasswordTxt(EditText inp_newPasswordTxt) {
        this.inp_newPasswordTxt = inp_newPasswordTxt;
    }

    public EditText getInp_oldPasswordTxt() {
        return inp_oldPasswordTxt;
    }

    public void setInp_oldPasswordTxt(EditText inp_oldPasswordTxt) {
        this.inp_oldPasswordTxt = inp_oldPasswordTxt;
    }

    public TextInputLayout getWrapper_oldPassword() {
        return wrapper_oldPassword;
    }

    public void setWrapper_oldPassword(TextInputLayout wrapper_oldPassword) {
        this.wrapper_oldPassword = wrapper_oldPassword;
    }

    public TextInputLayout getWrapper_newPassword() {
        return wrapper_newPassword;
    }

    public void setWrapper_newPassword(TextInputLayout wrapper_newPassword) {
        this.wrapper_newPassword = wrapper_newPassword;
    }

    public TextInputLayout getWrapper_confirmPassword() {
        return wrapper_confirmPassword;
    }

    public void setWrapper_confirmPassword(TextInputLayout wrapper_confirmPassword) {
        this.wrapper_confirmPassword = wrapper_confirmPassword;
    }

    public EditText getInp_confirmPasswordTxt() {
        return inp_confirmPasswordTxt;
    }

    public void setInp_confirmPasswordTxt(EditText inp_confirmPasswordTxt) {
        this.inp_confirmPasswordTxt = inp_confirmPasswordTxt;
    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}
