package com.glorystudent.golflife;

import android.view.View;
import android.widget.RadioGroup;

import com.glorystudent.fragment.EventCalendarFragment;
import com.glorystudent.fragment.IndustryEventsFragment;
import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 行业日历
 * Created by hyj on 2017/1/10.
 */
public class IndustryCalendarActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.industry_calendar_rg)
    public RadioGroup industry_calendar_rg;
    private IndustryEventsFragment industryEventsFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_industry_calendar;
    }

    @Override
    protected void init() {
        industryEventsFragment = new IndustryEventsFragment();
        showFragment(R.id.industry_calendar_fragment, industryEventsFragment);
        industry_calendar_rg.getChildAt(0).performClick();
        industry_calendar_rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.industry_calendar_rb1:
                //行业大事件
                showFragment(R.id.industry_calendar_fragment, industryEventsFragment);
                break;
            case R.id.industry_calendar_rb2:
                //赛事日历
                showFragment(R.id.industry_calendar_fragment, new EventCalendarFragment());
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
