package com.glorystudent.golflife;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glorystudent.adapter.LoginViewPagerAdapter;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;


import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 * Created by hyj on 2016/12/15.
 */
public class LoginActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    @Bind(R.id.tv_index)
    public TextView tv_index;

    @Bind(R.id.vp)
    public ViewPager vp;

    @Bind(R.id.ll_add)
    public LinearLayout ll_add;

    @Bind(R.id.tv_student)
    public TextView tv_student;

    @Bind(R.id.tv_coach)
    public TextView tv_coach;

    @Bind(R.id.ll_root)
    public LinearLayout ll_root;

    private LoginViewPagerAdapter loginViewPagerAdapter;

    /**
     * TODO 绑定布局
     * 绑定布局
     *
     * @return
     */
    @Override
    protected int getContentId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        //已经登录，则跳转主页
        if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            SharedUtil.putString(Constants.SWITCH_VERSION, "客户端");
        }
        loginViewPagerAdapter = new LoginViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(loginViewPagerAdapter);
        vp.addOnPageChangeListener(this);
        vp.setOffscreenPageLimit(3);
        vp.setPageMargin(DensityUtil.dip2px(this, 20));
        tv_student.setOnClickListener(this);
        tv_coach.setOnClickListener(this);
        ll_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return vp.dispatchTouchEvent(motionEvent);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv_index.getLayoutParams();
        int marginLeft = (int) (positionOffset * DensityUtil.dip2px(this, 175) + DensityUtil.dip2px(this, 175) * position);
        layoutParams.setMargins(marginLeft, 0, 0, 0);
        tv_index.setLayoutParams(layoutParams);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            SharedUtil.putString(Constants.SWITCH_VERSION, "客户端");
            tv_student.setTextColor(getResources().getColor(R.color.colorBlack));
            tv_coach.setTextColor(getResources().getColor(R.color.colorGray11));
        } else {
            SharedUtil.putString(Constants.SWITCH_VERSION, "教练端");
            tv_student.setTextColor(getResources().getColor(R.color.colorGray11));
            tv_coach.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_student:
                //学员端登陆
                vp.setCurrentItem(0);
                break;
            case R.id.tv_coach:
                //教练端登陆
                vp.setCurrentItem(1);
                break;
        }
    }


    @OnClick({R.id.tv_chat_login})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tv_chat_login:
                //微信登陆
                EventBus.getDefault().post(EventBusMapUtil.getStringMap("login", "wechat"));
                break;
        }
    }
}
