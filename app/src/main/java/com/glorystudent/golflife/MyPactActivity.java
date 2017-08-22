package com.glorystudent.golflife;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.glorystudent.adapter.CourseFragmentAdapter;
import com.glorystudent.golflibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的预约
 * Created by hyj on 2016/12/21.
 */
public class MyPactActivity extends BaseActivity {
    public TabLayout tabLayout;
    @Bind(R.id.video_vp)
    public ViewPager video_vp;
    private CourseFragmentAdapter courseFragmentAdapter;
    private List<String> datas;
    @Override
    protected int getContentId() {
        return R.layout.activity_my_pact;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        datas = new ArrayList<>();
        datas.add("未开始");
        datas.add("已结束");
        tabLayout = (TabLayout) findViewById(R.id.video_tab);
        tabLayout.addTab(tabLayout.newTab().setText(datas.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(datas.get(1)));
        courseFragmentAdapter = new CourseFragmentAdapter(getSupportFragmentManager(), datas);
        video_vp.setAdapter(courseFragmentAdapter);
        tabLayout.setupWithViewPager(video_vp);
        tabLayout.setTabsFromPagerAdapter(courseFragmentAdapter);
    }


    @OnClick({R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
        }
    }
}
