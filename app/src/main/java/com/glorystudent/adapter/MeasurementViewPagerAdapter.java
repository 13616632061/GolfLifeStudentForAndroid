package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.glorystudent.fragment.MeasurementFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyj on 2017/1/4.
 */
public class MeasurementViewPagerAdapter extends FragmentPagerAdapter{
    private int page;
    private List<MeasurementFragment> fragment;
    public MeasurementViewPagerAdapter(FragmentManager fm, int page) {
        super(fm);
        this.page = page;
        fragment = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            MeasurementFragment measurementFragment = new MeasurementFragment();
            fragment.add(measurementFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return page;
    }
}
