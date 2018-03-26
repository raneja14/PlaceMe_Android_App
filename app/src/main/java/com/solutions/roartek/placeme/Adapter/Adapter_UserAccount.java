package com.solutions.roartek.placeme.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.solutions.roartek.placeme.Fragments.Fragment_MyProfile;
import com.solutions.roartek.placeme.Fragments.Fragment_Password;
import com.solutions.roartek.placeme.R;

/**
 * Created by Swathi.K on 21-12-2016.
 */
public class Adapter_UserAccount extends FragmentPagerAdapter {

    private String userAccountTab[];
    private Fragment_MyProfile fragment_myProfile;
    private Fragment_Password fragment_password;

    public Adapter_UserAccount(FragmentManager fm,Context context,Fragment_MyProfile fragment_myProfile,Fragment_Password fragment_password) {
        super(fm);
         this.fragment_myProfile=fragment_myProfile;
         this.fragment_password=fragment_password;
         this.userAccountTab= context.getResources().getStringArray(R.array.userAccount_tab_heading);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragment_myProfile;
            case 1:
                return fragment_password;
            default:
                return null;

        }

    }
    @Override
    public int getCount() {
        return userAccountTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return userAccountTab[position];
    }

}
