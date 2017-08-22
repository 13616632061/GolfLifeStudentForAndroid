package com.glorystudent.golflife;

import android.view.View;

import com.glorystudent.fragment.EmptyVideoFragment;
import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.OnClick;

/**
 * 我的视频
 * Created by hyj on 2016/12/21.
 */
public class MyVideoActivity extends BaseActivity {
    @Override
    protected int getContentId() {
        return R.layout.activity_my_video;
    }
    /**
     * 初始化
     */
    @Override
    protected void init() {
    }

    @Override
    protected void loadDatas() {
        showFragment(R.id.my_video_fragment, new EmptyVideoFragment());
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
