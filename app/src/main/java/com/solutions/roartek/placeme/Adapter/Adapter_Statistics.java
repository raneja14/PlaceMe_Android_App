package com.solutions.roartek.placeme.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.solutions.roartek.placeme.Fragments.Fragment_DetailStatistics;
import com.solutions.roartek.placeme.Fragments.Fragment_OverallStatistics;
import com.solutions.roartek.placeme.R;


/**
 * Created by Swathi.K on 23-12-2016.
 */
public class Adapter_Statistics extends FragmentPagerAdapter {
    private String statisticsTab[];
    private Fragment_DetailStatistics fragment_detailStatistics;
    private Fragment_OverallStatistics fragment_overallStatistics;
    public Adapter_Statistics(FragmentManager fm,Context context,Fragment_OverallStatistics fragment_overallStatistics,Fragment_DetailStatistics fragment_detailStatistics) {
        super(fm);
        this.statisticsTab=context.getResources().getStringArray(R.array.statistics_tab_heading);
        this.fragment_overallStatistics= fragment_overallStatistics;
        this.fragment_detailStatistics = fragment_detailStatistics;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragment_overallStatistics;
            case 1:
                return fragment_detailStatistics;
            default:
                return null;

        }
    }


    @Override
    public int getCount() {
        return statisticsTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return statisticsTab[position];
    }
}
