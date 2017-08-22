package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.glorystudent.fragment.HomeDayCalendarFragment;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * day适配器
 * Created by hyj on 2017/1/11.
 */
public class HomeDayViewPagerAdapter extends FragmentStatePagerAdapter {
    private int page = 10000;
    private int savePosition = page/2;
    private int subDay = 0;
    private int year;
    private int month;
    private int day;
    private int monthCountDays;
    private int saveMonthCountDays;

    public HomeDayViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        subDay = (position - savePosition) * 7;
        return HomeDayCalendarFragment.getInstance(getCurrentDay());
    }

    @Override
    public int getCount() {
        return page;
    }

    public List<String> getCurrentDay(){
        List<String> date  = new ArrayList<>();
        String currentTime = RequestUtil.getCurrentTime();
        String[] split = currentTime.split("-");
        year = Integer.valueOf(split[0]);
        month = Integer.valueOf(split[1]);
        day = Integer.valueOf(split[2]);
        monthCountDays = TimeUtil.getMonthDays(year, month);
        saveMonthCountDays = monthCountDays;
        int week = TimeUtil.getWeek(currentTime);
        int left = week - 1;
        int right = 7 - week;

        if (subDay > 0) {
            for(int i = subDay - 6 ; i <= subDay; i++){
                int cyear, cmonth, cday;
                cyear = year;
                cmonth = month;
                cday = day;
                monthCountDays = saveMonthCountDays;
                for (int j = 0; j < i + right; j++) {
                    cday++;
                    if(cday > monthCountDays){
                        cmonth++;
                        cday = 1;
                        if (cmonth > 12) {
                            cyear++;
                            cmonth = 1;
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        }else{
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        }
                    }
                }
                String smonth = cmonth < 10 ? "0" + cmonth : cmonth + "";
                String sday = cday < 10 ? "0" + cday : cday + "";
                date.add(cyear + "-" + smonth  + "-" + sday);
            }
        } else if (subDay < 0) {
            for(int i = subDay; i <= subDay + 6; i++) {
                int cyear, cmonth, cday;
                cyear = year;
                cmonth = month;
                cday = day;
                for(int j = 0; j > i - left; j--) {
                    cday--;
                    if (cday < 1) {
                        cmonth --;
                        if (cmonth < 1) {
                            cyear--;
                            cmonth = 12;
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        }else{
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        }
                        cday = monthCountDays;
                    }
                }
                String smonth = cmonth < 10 ? "0" + cmonth : cmonth + "";
                String sday = cday < 10 ? "0" + cday : cday + "";
                date.add( cyear + "-" + smonth + "-" + sday);
            }
        } else if (subDay == 0) {
            for(int i = left; i > 0; i--) {
                int cyear, cmonth, cday;
                cyear = year;
                cmonth = month;
                cday = day;
                for (int j = 0; j < i; j++) {
                    cday--;
                    if (cday < 1) {
                        cmonth--;
                        if (cmonth < 1) {
                            cyear--;
                            cmonth = 12;
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        } else {
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        }
                        cday = monthCountDays;
                    }
                }
                String smonth = cmonth < 10 ? "0" + cmonth : cmonth + "";
                String sday = cday < 10 ? "0" + cday : cday + "";
                date.add( cyear + "-" + smonth + "-" + sday);
            }
            date.add(currentTime);
            for(int i = 1 ; i <= right; i++) {
                int cyear, cmonth, cday;
                cyear = year;
                cmonth = month;
                cday = day;
                monthCountDays = saveMonthCountDays;
                for (int j = 0; j < i; j++) {
                    cday++;
                    if (cday > monthCountDays) {
                        cmonth++;
                        cday = 1;
                        if (cmonth > 12) {
                            cyear++;
                            cmonth = 1;
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        } else {
                            monthCountDays = TimeUtil.getMonthDays(cyear, cmonth);
                        }
                    }
                }
                String smonth = cmonth < 10 ? "0" + cmonth : cmonth + "";
                String sday = cday < 10 ? "0" + cday : cday + "";
                date.add(cyear + "-" + smonth + "-" + sday);
            }
        }
        return date;
    }
}
