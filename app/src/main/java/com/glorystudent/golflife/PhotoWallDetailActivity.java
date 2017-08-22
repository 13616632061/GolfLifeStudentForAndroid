package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/21.
 */

public class PhotoWallDetailActivity extends BaseActivity {
    @Bind(R.id.iv_photo_wall_detail)
    ImageView photoWallDetail;
    private String url;

    @Override
    protected int getContentId() {
        return R.layout.activity_photo_wall_detail;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        GlideUtil.loadImageView(this, url, photoWallDetail);
    }

    @OnClick(R.id.iv_photo_wall_detail)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo_wall_detail:
                finish();
                break;
        }
    }
}
