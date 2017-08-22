package com.glorystudent.golflife;

import android.view.View;
import android.widget.RadioGroup;

import com.glorystudent.fragment.IndustryDynamicFragment;
import com.glorystudent.fragment.VideoInformationFragment;
import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 今日推荐
 * Created by hyj on 2017/1/9.
 */
public class TodayRecommendationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind({R.id.recommendation_rg})
    public RadioGroup recommendation_rg;
    private IndustryDynamicFragment industryDynamicFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_today_recommendation;
    }

    @Override
    protected void init() {
        industryDynamicFragment = new IndustryDynamicFragment();
        showFragment(R.id.recommendation_fragment, industryDynamicFragment);
        recommendation_rg.getChildAt(0).performClick();
        recommendation_rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.recommendation_rb1:
                //行业动态
                showFragment(R.id.recommendation_fragment, industryDynamicFragment);
                break;
            case R.id.recommendation_rb2:
                //视频资讯
                showFragment(R.id.recommendation_fragment, new VideoInformationFragment());
                break;
        }
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
