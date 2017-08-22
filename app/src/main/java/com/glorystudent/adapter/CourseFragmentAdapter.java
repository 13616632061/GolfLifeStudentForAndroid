package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.glorystudent.fragment.AppointmentCourseFragment;

import java.util.List;

/**
 * 课程Fragment设配器
 * Created by hyj on 2016/12/20.
 */
public class CourseFragmentAdapter extends FragmentPagerAdapter{
    private List<String> datas;
    private int state;
    public CourseFragmentAdapter(FragmentManager fm, List<String> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        state = position + 1;
        return AppointmentCourseFragment.getInstance(state);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position);//页卡标题
    }

}
