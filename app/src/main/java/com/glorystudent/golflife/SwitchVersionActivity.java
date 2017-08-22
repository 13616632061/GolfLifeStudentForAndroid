package com.glorystudent.golflife;

import android.view.View;
import android.widget.Button;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 切换版本
 * Created by hyj on 2016/12/22.
 */
public class SwitchVersionActivity extends BaseActivity {
    @Bind(R.id.btn_switch)
    public Button btn_switch;
    @Override
    protected int getContentId() {
        return R.layout.activity_switch_version;
    }

    @Override
    protected void init() {
        String v = SharedUtil.getString(Constants.SWITCH_VERSION);
        if(v.equals("客户端")){
            btn_switch.setText("切换成教练端");
        }else {
            btn_switch.setText("切换成客户端");
        }
    }

    @OnClick({R.id.btn_switch, R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.btn_switch:
                //切换
                String v = SharedUtil.getString(Constants.SWITCH_VERSION);
                if(v.equals("客户端")){
                    SharedUtil.putString(Constants.SWITCH_VERSION, "教练端");
                }else {
                    SharedUtil.putString(Constants.SWITCH_VERSION, "客户端");
                }
                setResult(0x889);
                finish();
                break;
        }
    }
}
