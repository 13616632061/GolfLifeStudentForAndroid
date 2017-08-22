package com.glorystudent.golflife;

import android.content.Intent;
import android.widget.ImageView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;

/**
 * 欢迎页
 * Created by hyj on 2017/3/6.
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.iv_welcome)
    ImageView ivWelcome;

    @Override
    protected int getContentId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        ivWelcome.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
