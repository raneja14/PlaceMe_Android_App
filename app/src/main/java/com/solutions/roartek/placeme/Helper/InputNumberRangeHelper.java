package com.solutions.roartek.placeme.Helper;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Raghav.Aneja on 01-01-2017.
 */
public class InputNumberRangeHelper implements InputFilter{

    private int min,max,textLength;
    private boolean isDecimalAllowed;

    public InputNumberRangeHelper(int min, int max, int textLength, boolean isDecimalAllowed) {
        this.min = min;
        this.max = max;
        this.textLength = textLength;
        this.isDecimalAllowed = isDecimalAllowed;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String inputStr = dest.toString() + source.toString();
            if (inputStr.length() <= textLength) {
                if (isDecimalAllowed(source)) {
                    double inputVal = Double.parseDouble(inputStr);
                    if (isInRange(min, max, inputVal))
                        return null;
                }
            }
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(int minRange, int maxRange, double inputVal) {
        return maxRange > minRange ? inputVal >= minRange && inputVal <= maxRange : inputVal >= maxRange && inputVal <= minRange;
    }

    private boolean isDecimalAllowed(CharSequence source) {
        return source.toString().equals(".") ? isDecimalAllowed : true;
    }
}
