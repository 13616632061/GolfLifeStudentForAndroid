package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.OnClick;

/**
 * 测评分类
 * Created by hyj on 2017/1/4.
 */
public class AssessmentClassifyActivity extends BaseActivity {
    @Override
    protected int getContentId() {
        return R.layout.activity_assessment_classify;
    }

    @OnClick({R.id.back, R.id.rv_test1, R.id.ll_template, R.id.tv_historical, R.id.rv_test2, R.id.rv_test_resident})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.rv_test1:
                //测试
                startActivity(new Intent(this, AssessmentActivity.class));
                break;
            case R.id.ll_template:
                //模板预览
                startActivity(new Intent(this, TemplateActivity.class));
                break;
            case R.id.tv_historical:
                //历史纪录
                startActivity(new Intent(this, HistoricalRecordActivity.class));
                break;
            case R.id.rv_test2:
                //tpi测试
                startActivity(new Intent(this, TPITestActivity.class));
                break;
            case R.id.rv_test_resident:
                //常住测试
                startActivity(new Intent(this, ResidentActivity.class));
                break;
        }
    }
}
