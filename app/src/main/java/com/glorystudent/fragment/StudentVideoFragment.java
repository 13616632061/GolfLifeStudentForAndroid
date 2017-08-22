package com.glorystudent.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.glorystudent.adapter.VideoFragmentrAdapter;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 学员端视频模块
 * Created by hyj on 2016/12/20.
 */
public class StudentVideoFragment extends BaseFragment {
//    @Bind(R.id.video_tab)
    public TabLayout tabLayout;
    @Bind(R.id.video_vp)
    public ViewPager video_vp;
    private VideoFragmentrAdapter videoViewPagerAdapter;
    private List<String> datas;
    @Override
    protected int getContentId() {
        return R.layout.fragment_stu_video;
    }

    @Override
    protected void init(View view) {
        datas = new ArrayList<>();
        datas.add("入门视频");
        datas.add("进阶视频");
        datas.add("高级视频");
        tabLayout = (TabLayout) view.findViewById(R.id.video_tab);
        tabLayout.addTab(tabLayout.newTab().setText(datas.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(datas.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(datas.get(2)));
        videoViewPagerAdapter = new VideoFragmentrAdapter(getChildFragmentManager(), datas);
        video_vp.setAdapter(videoViewPagerAdapter);
        video_vp.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(video_vp);
        tabLayout.setTabsFromPagerAdapter(videoViewPagerAdapter);
    }
}
