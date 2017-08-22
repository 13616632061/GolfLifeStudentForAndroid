package com.glorystudent.golflife;

import android.view.View;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.OnClick;

/**
 * 认证信息
 * Created by hyj on 2016/12/28.
 */
public class AuthenticationInfoActivity extends BaseActivity {
    @Override
    protected int getContentId() {
        return R.layout.activity_authentication_info;
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
