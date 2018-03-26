package com.solutions.roartek.placeme.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.solutions.roartek.placeme.Common.Utility;
import com.solutions.roartek.placeme.Delegate.OnFragmentCreated;
import com.solutions.roartek.placeme.Domain.Entity_Notification;
import com.solutions.roartek.placeme.Fragments.Fragment_DetailedNotification_Criteria;
import com.solutions.roartek.placeme.Fragments.Fragment_DetailedNotification_Details;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 29-12-2016.
 */
public class Activity_Detailed_Notifications extends Activity
        implements  FragmentManager.OnBackStackChangedListener,OnFragmentCreated {

    private boolean mShowingBack;
    private Fragment_DetailedNotification_Criteria fragmentDetailedNotification_criteria;
    private Fragment_DetailedNotification_Details fragmentDetailedNotification_details;
    private Entity_Notification entityNotification;
    private FrameLayout layout_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notification);
        initialiseFragments();
        getObjectFromBundle();
    }

    private void initialiseFragments() {

        layout_container = (FrameLayout) findViewById(R.id.detailedNotification_container);

        layout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fragmentDetailedNotification_criteria = new Fragment_DetailedNotification_Criteria();
        fragmentDetailedNotification_details = new Fragment_DetailedNotification_Details();

        fragmentDetailedNotification_criteria.setFragmentCreated(this);
        fragmentDetailedNotification_details.setFragmentCreated(this);

        getFragmentManager().beginTransaction()
                .add(R.id.detailedNotification_container, fragmentDetailedNotification_criteria)
                .commit();
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    private void getObjectFromBundle()
    {
        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            String notificationJSON = extras.getString("NOTIFICATION_JSON");
            entityNotification = (Entity_Notification) Utility.convertJSONToObject(Entity_Notification.class, notificationJSON);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flip:
                flipCard();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                        R.anim.card_flip_left_in, R.anim.card_flip_left_out)
                .replace(R.id.detailedNotification_container, fragmentDetailedNotification_details)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    private void populateCriteriaCard(Entity_Notification entityNotification) {
        fragmentDetailedNotification_criteria.getTxt_cgpa().setText(String.valueOf(entityNotification.getCriteriaObj().getCgpa()));
        fragmentDetailedNotification_criteria.getTxt_xPer().setText(String.valueOf(entityNotification.getCriteriaObj().getX()));
        fragmentDetailedNotification_criteria.getTxt_xiiPer().setText(String.valueOf(entityNotification.getCriteriaObj().getXII()));
        fragmentDetailedNotification_criteria.getTxt_arrears().setText(String.valueOf(entityNotification.getCriteriaObj().getArrears()));
        String placed = (entityNotification.getCriteriaObj().isPlaced()) ? "Consider" : "Ignore";
        String diploma = (entityNotification.getCriteriaObj().isDiplomaAllowed()) ? "Consider" : "Ignore";
        fragmentDetailedNotification_criteria.getTxt_placed().setText(placed);
        fragmentDetailedNotification_criteria.getTxt_diploma().setText(diploma);

        String branch = entityNotification.getCriteriaObj().getBranch().toString();
        branch = branch.replaceAll("'", "");
        branch = branch.replaceAll("]", "");
        branch = branch.replaceAll("\\[", "");
        fragmentDetailedNotification_criteria.getTxt_branch().setText(branch);
    }

    private void populateDetailsCard(Entity_Notification entityNotification)
    {
        fragmentDetailedNotification_details.getTxt_eligible().setText(entityNotification.getCriteriaObj().getEligibleCount() + " students");
        fragmentDetailedNotification_details.getTxt_company().setText(String.valueOf(entityNotification.getPlacementInfoObj().getCompanyName()));
        fragmentDetailedNotification_details.getTxt_venue().setText(String.valueOf(entityNotification.getPlacementInfoObj().getVenue()));
        fragmentDetailedNotification_details.getTxt_time().setText(String.valueOf(entityNotification.getPlacementInfoObj().getTime()));
        fragmentDetailedNotification_details.getTxt_date().setText(String.valueOf(entityNotification.getPlacementInfoObj().getDOR()));
    }

    @Override
    public void onViewCreated(int viewCode) {

        if(viewCode==0)
            populateCriteriaCard(entityNotification);
        else if(viewCode==1)
            populateDetailsCard(entityNotification);
        else if(viewCode==5)
            flipCard();
    }
}
