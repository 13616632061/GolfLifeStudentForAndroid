package com.glorystudent.golflife;

import android.view.View;

import com.glorystudent.fragment.EmptyCourseFragment;
import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by hyj on 2016/12/28.
 */
public class CourseBookingActivity extends BaseActivity {
    @Override
    protected int getContentId() {
        return R.layout.activity_course_booking;
    }

    @Override
    protected void init() {
        showFragment(R.id.course_fragment, new EmptyCourseFragment());
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
