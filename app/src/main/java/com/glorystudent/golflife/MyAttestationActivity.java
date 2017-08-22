package com.glorystudent.golflife;

import android.view.View;

import com.glorystudent.fragment.CertificationCenterFragment;
import com.glorystudent.fragment.CertificationCoachFragment;
import com.glorystudent.fragment.CertificationFinishFragment;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.OnClick;

/**
 * 认证信息
 * Created by hyj on 2016/12/21.
 */
public class MyAttestationActivity extends BaseActivity {
    private final static String TAG = "MyAttestationActivity";

    @Override
    protected int getContentId() {
        return R.layout.activity_my_attestation;
    }

    @Override
    protected void init() {
        String state = SharedUtil.getString(Constants.ATTESTATION_STATE);
        switch (state) {
            case "0":
                //未申请
                showFragment(R.id.cer_fragment, new CertificationCoachFragment());
                break;
            case "1":
                //已审核
                SharedUtil.putString(Constants.USER_TYPE, "1");
                showFragment(R.id.cer_fragment, new CertificationFinishFragment());
                break;
            case "2":
                //待审核
                showFragment(R.id.cer_fragment, new CertificationCenterFragment());
                break;
        }
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



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<String, String> map) {
        if (map.containsKey("MyAttestationActivity")) {
            if (map.get("MyAttestationActivity").equals("close")) {
                finish();
            }
        }
    }
}
