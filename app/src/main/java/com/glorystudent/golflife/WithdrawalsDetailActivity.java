package com.glorystudent.golflife;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/5/16.
 */

public class WithdrawalsDetailActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_complete)
    TextView tvComplete;

    @Override
    protected int getContentId() {
        return R.layout.activity_withdrawals_detail;
    }

    @Override
    protected void init() {


    }

    @OnClick({R.id.back, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_complete:
                finish();
                break;
        }
    }
}
