package com.glorystudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.glorystudent.fragment.ImageBrowsingFragment;

import java.util.List;

/**
 * Created by hyj on 2017/3/22.
 */
public class ImageBrowsingViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> datas;
    public ImageBrowsingViewPagerAdapter(FragmentManager fm, List<String> picList) {
        super(fm);
        this.datas = picList;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageBrowsingFragment.getInstance(datas.get(position));
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
