package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.glorystudent.fragment.CalendarFragment;

/**
 * Created by hyj on 2017/1/10.
 */
public class CalendarViewPagerAdapter extends FragmentPagerAdapter {
    private int year, month;
    private int savePosition = Integer.MAX_VALUE / 2;
    private CalendarFragment.OnCaleadarDayClick onCaleadarDayClick;

    public CalendarViewPagerAdapter(FragmentManager fm, int year, int month, CalendarFragment.OnCaleadarDayClick onCaleadarDayClick) {
        super(fm);
        this.year = year;
        this.month = month;
        this.onCaleadarDayClick = onCaleadarDayClick;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public Fragment getItem(int position) {
        //核心算法(使日历左右滑动,显示正确的日历)
        month = month + position - savePosition;
        if (month <= 0) {
            year--;
            month = 12 + month;
        } else if (month > 12) {
            year++;
            month = month % 12;
        }
        savePosition = position;
        return CalendarFragment.getInstance(year, month, onCaleadarDayClick);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

}
