package com.glorystudent.fragment;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.glorystudent.entity.TestScoreEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.MeasurementActivity;
import com.glorystudent.golflife.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 开始测评
 * Created by hyj on 2017/1/4.
 */
public class MeasurementFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private final static String TAG = "MeasurementFragment";
    @Bind(R.id.tv_par)
    public TextView tv_par;
    @Bind(R.id.rg_bunkers)
    public RadioGroup rg_bunkers;//沙坑救球
    @Bind(R.id.rg_fairway)
    public RadioGroup rg_fairway;//开球上球道
    @Bind(R.id.rg_followball)
    public RadioGroup rg_followball;//切杆救球
    @Bind(R.id.rg_upgreen)
    public RadioGroup rg_upgreen;//标准杆上果岭
    private int par = 0;
    private int bunkers = -1;// -1 未设置，0否  1是  2未出现
    private int fairway = -1;
    private int followball = -1;
    private int upgreen = -1;
    @Override
    protected int getContentId() {
        return R.layout.fragment_measurement;
    }

    @Override
    protected void init(View view) {
        rg_fairway.setOnCheckedChangeListener(this);
        rg_upgreen.setOnCheckedChangeListener(this);
        rg_followball.setOnCheckedChangeListener(this);
        rg_bunkers.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.iv_subtract, R.id.iv_add})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_subtract:
                //减
                par--;
                break;
            case R.id.iv_add:
                //加
                par++;
                break;
        }
        if(par > 0){
            tv_par.setText("+" + par);
        }else if(par < 0){
            tv_par.setText("" + par);
        }else {
            tv_par.setText("PAR");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_fairway:
                //开球上球道
                switch (checkedId) {
                    case R.id.rb_fairway_yes:
                        Log.d(TAG, "onCheckedChanged: --->1");
                        fairway = 1;
                        break;
                    case R.id.rb_fairway_no:
                        Log.d(TAG, "onCheckedChanged: --->2");
                        fairway = 0;
                        break;
                }
                break;
            case R.id.rg_upgreen:
                //标准杆上果岭
                switch (checkedId) {
                    case R.id.rb_upgreen_yes:
                        upgreen = 1;
                        break;
                    case R.id.rb_upgreen_no:
                        upgreen = 0;
                        break;
                }
                break;
            case R.id.rg_followball:
                //切杆救球
                switch (checkedId) {
                    case R.id.rb_followball_noappear:
                        followball = 2;
                        break;
                    case R.id.rb_followball_yes:
                        followball = 1;
                        break;
                    case R.id.rb_followball_no:
                        followball = 0;
                        break;
                }
                break;
            case R.id.rg_bunkers:
                //沙坑救球
                switch (checkedId) {
                    case R.id.rb_bunkers_noappear:
                        bunkers = 2;
                        break;
                    case R.id.rb_bunkers_yes:
                        bunkers = 1;
                        break;
                    case R.id.rb_bunkers_no:
                        bunkers = 0;
                        break;
                }
                break;
        }
        if (bunkers != -1 && followball != -1 && upgreen != -1 && fairway != -1) {
            //全都选择完，则自动跳转到下一页
            MeasurementActivity activity = (MeasurementActivity) getActivity();
            activity.setAutomaticJump();
        }
    }

    public TestScoreEntity getTest() {
        TestScoreEntity testScoreEntity = new TestScoreEntity();
        testScoreEntity.setBunkers(bunkers);
        testScoreEntity.setFollowball(followball);
        testScoreEntity.setUpgreen(upgreen);
        testScoreEntity.setFairway(fairway);
        testScoreEntity.setPar(par);
        return testScoreEntity;
    }
}
