package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.glorystudent.golflibrary.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/5/19.
 */

public class TeamIntroductionActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et_introduction_input)
    EditText introductionInput;
    private InputMethodManager imm;

    @Override
    protected int getContentId() {
        return R.layout.activity_team_introduction;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        introductionInput.setText(text);
        introductionInput.setFocusable(true);
        introductionInput.setFocusableInTouchMode(true);
        introductionInput.requestFocus();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(introductionInput, 0);
                }
            }
        }, 200);
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                //先关闭输入法，这样返回后才能让上一个页面的活动详情获取焦点
                if (imm != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                }
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        close();
                    }
                }, 100);
                break;
        }
    }

    /**
     * 关闭页面保存数据的方法
     */
    private void close() {
        Intent intent = new Intent();
        intent.putExtra("text", introductionInput.getText().toString().trim());
        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
