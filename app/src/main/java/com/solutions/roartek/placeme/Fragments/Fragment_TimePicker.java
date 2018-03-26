package com.solutions.roartek.placeme.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import com.solutions.roartek.placeme.R;

import java.util.Calendar;

/**
 * Created by Raghav.Aneja on 25-12-2016.
 */
public class Fragment_TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setRetainInstance(true);
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String am_pm="am";
        String min=String.valueOf(minute);
        String hour=String.valueOf(hourOfDay);
        if(hourOfDay>=12)
        {
            if(hourOfDay>12)
                 hourOfDay-=12;
            am_pm="pm";
            hour=String.valueOf(hourOfDay);
        }
        else if(hourOfDay==0)
            hour="12";

        if(minute<10)
          min="0"+min;
        ((EditText) getActivity().findViewById(R.id.placedDetails_time)).setText(hour+":"+min+" " +am_pm);
    }
}
