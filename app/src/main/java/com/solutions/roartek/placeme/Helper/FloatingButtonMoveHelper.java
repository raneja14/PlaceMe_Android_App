package com.solutions.roartek.placeme.Helper;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Dialog.Dialog_Home;
import com.solutions.roartek.placeme.R;

/**
 * Created by Raghav.Aneja on 23-12-2016.
 */
public class FloatingButtonMoveHelper {

    private float dX,dY,startPositionX,startPositionY,endPositoinX,endPosiionY,diffX,diffY;
    private FloatingActionButton floatingHomeButton;
     private OnFragmentCreated onFragmentCreated;

    public FloatingButtonMoveHelper(FloatingActionButton floatingHomeButton,OnFragmentCreated onFragmentCreated) {
        this.floatingHomeButton = floatingHomeButton;
        this.onFragmentCreated=onFragmentCreated;
    }

    public boolean onTouchListener(View v, MotionEvent event)
    {
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                dX = floatingHomeButton.getX() - event.getRawX();
                dY = floatingHomeButton.getY() - event.getRawY();
                startPositionX=event.getRawX();
                startPositionY=event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                floatingHomeButton.setY(event.getRawY() + dY);
                floatingHomeButton.setX(event.getRawX() + dX);
                break;

            case MotionEvent.ACTION_UP:
                endPositoinX=event.getRawX();
                endPosiionY=event.getRawY();
                diffX=Math.abs(endPositoinX - startPositionX);
                diffY=Math.abs(endPosiionY-startPositionY);

                if(diffX<10 && diffY<10) {

                    onFragmentCreated.onViewCreated(1);
           /*       new Dialog_Home(floatingHomeButton).show(activity.getFragmentManager(), "dialog");
                  floatingHomeButton.setVisibility(View.INVISIBLE);

       */         }
                break;
            default:
                return false;
        }
        return true;
    }
}
