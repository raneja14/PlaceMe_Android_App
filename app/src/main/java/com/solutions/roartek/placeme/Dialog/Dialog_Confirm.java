package com.solutions.roartek.placeme.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.solutions.roartek.placeme.Activity.Activity_ViewCompany;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;

/**
 * Created by Raghav.Aneja on 06-01-2017.
 */
public class Dialog_Confirm extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder homeBuilder = new AlertDialog.Builder(getActivity());
        homeBuilder.setTitle("Confirm")
                .setMessage("Are you sure ??")
                .setNegativeButton("No",dialogClickListener)
                .setPositiveButton("Yes",dialogClickListener);

        AlertDialog dialog = homeBuilder.create();
        return dialog;

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clickedg
                    ((Activity_ViewCompany)getActivity()).onViewCreated(1);
                    dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dismiss();
                    break;
            }
        }
    };
}
