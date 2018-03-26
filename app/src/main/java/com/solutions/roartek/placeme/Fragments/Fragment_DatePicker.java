package com.solutions.roartek.placeme.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Raghav.Aneja on 25-12-2016.
 */
public class Fragment_DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public Fragment_DatePicker() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        setRetainInstance(true);
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis()-1000);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
         String date=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        ((EditText) getActivity().findViewById(R.id.placedDetails_date)).setText(Utility.getUIDate(date));
    }
}
