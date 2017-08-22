package com.glorystudent.golflife;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 所获成就
 * Created by hyj on 2016/12/26.
 */
public class GetAchievementActivity extends BaseActivity implements TextWatcher {
    @Bind(R.id.et_achievement)
    public EditText et_achievement;
    @Bind(R.id.tv_size)
    public TextView tv_size;

    private int textLength = 100;
    @Override
    protected int getContentId() {
        return R.layout.activity_get_achievement;
    }



    @Override
    protected void init() {
        Intent intent = getIntent();
        String pp = intent.getStringExtra("achievement");
        et_achievement.setText(pp);
        et_achievement.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        int length = str.length();
        tv_size.setText("剩余" + (textLength - length) + "字");
    }

    @OnClick({R.id.back, R.id.tv_save})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_save:
                //确定
                String textStr = et_achievement.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("achievement", textStr);
                setResult(0x044, intent);
                finish();
                break;
        }
    }
}
