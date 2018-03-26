package com.solutions.roartek.placeme.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.solutions.roartek.placeme.Fragments.Fragment_Branches;
import com.solutions.roartek.placeme.Fragments.Fragment_Criteria;
import com.solutions.roartek.placeme.R;

/**
 * Created by swathi.k on 10-12-2016.
 */
public class Adapter_ShortlistStudents extends FragmentPagerAdapter {

    private String shortlistStudentsTab[];
    private Fragment_Branches fragment_branches;
    private Fragment_Criteria fragment_criteria;

    public Adapter_ShortlistStudents(FragmentManager supportFragmentManager,Context context,Fragment_Criteria fragment_criteria,Fragment_Branches fragment_branches) {
        super(supportFragmentManager);
        this.shortlistStudentsTab = context.getResources().getStringArray(R.array.shortList_tab_heading);
        this.fragment_branches = fragment_branches;
        this.fragment_criteria = fragment_criteria;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return fragment_criteria;
            case 1:
                return fragment_branches;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return shortlistStudentsTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return shortlistStudentsTab[position];
    }

}
