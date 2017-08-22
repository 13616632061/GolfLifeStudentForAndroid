package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.glorystudent.fragment.TeachVideoFragment;

import java.util.List;

/**
 * 视频模块Fragment设配器
 * Created by hyj on 2016/12/20.
 */
public class VideoFragmentrAdapter extends FragmentPagerAdapter{
    private List<String> datas;
    public VideoFragmentrAdapter(FragmentManager fm, List<String> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return TeachVideoFragment.getInstance(position);
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
