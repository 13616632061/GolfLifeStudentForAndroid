package com.glorystudent.golflife;

import android.view.View;
import android.widget.ListView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 系统消息
 * Created by hyj on 2017/2/14.
 */
public class SystemMessageActivity extends BaseActivity {
    @Bind(R.id.lv_system_message)
    public ListView lv_system_message;

    @Override
    protected int getContentId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void init() {

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
