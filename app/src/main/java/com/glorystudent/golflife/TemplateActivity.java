package com.glorystudent.golflife;

import android.view.View;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.OnClick;

/**
 * 模板
 * Created by hyj on 2017/1/6.
 */
public class TemplateActivity extends BaseActivity {
    @Override
    protected int getContentId() {
        return R.layout.activity_template;
    }

    @OnClick({R.id.iv_template})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_template:
                //返回
                finish();
                break;
        }
    }
}
