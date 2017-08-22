package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.glorystudent.fragment.CoachViewPagerFragment;
import com.glorystudent.util.Constants;
import com.glorystudent.util.TimeUtil;

/**
 * Created by hyj on 2017/2/22.
 */
public class CoachHomeViewPagerAdapter extends FragmentStatePagerAdapter {
    private String date;
    private int subDay;
    public CoachHomeViewPagerAdapter(FragmentManager fm, String date) {
        super(fm);
        this.date = date;
    }

    @Override
    public Fragment getItem(int position) {
        subDay = (Constants.MAX / 2) - position;
        String currentDay = TimeUtil.subDate(subDay);
        return CoachViewPagerFragment.getInstance(currentDay);
    }

    @Override
    public int getCount() {
        return 10000;
    }
}
