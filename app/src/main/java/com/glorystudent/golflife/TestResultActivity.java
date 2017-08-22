package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.glorystudent.adapter.TestResultListAdapter;
import com.glorystudent.entity.TestResultEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 评测结果
 * Created by hyj on 2017/1/5.
 */
public class TestResultActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "TestResultActivity";
    @Bind(R.id.lv_test)
    public ListView lv_test;
    private String request;
    private TestResultListAdapter testResultListAdapter;
    private TextView tv_parcount;
    private TextView tv_test_name;
    private TextView tv_date;
    private String type;
    private String url;

    @Override
    protected int getContentId() {
        return R.layout.activity_test_result;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        request = intent.getStringExtra("request");
        type = intent.getStringExtra("type");
        View head = LayoutInflater.from(this).inflate(R.layout.item_test_result_head, null);
        tv_parcount = (TextView) head.findViewById(R.id.tv_parcount);
        tv_test_name = (TextView) head.findViewById(R.id.tv_test_name);
        tv_date = (TextView) head.findViewById(R.id.tv_date);
        lv_test.addHeaderView(head);
        testResultListAdapter = new TestResultListAdapter(this);
        lv_test.setAdapter(testResultListAdapter);
    }

    @Override
    protected void loadDatas() {
        switch (type) {
            case "add":
                url = "/api/APITests/AddTests";
                break;
            case "query":
                url = "/api/APITests/GetTests";
                break;
        }
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, request);
        Log.i("request",request);
    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            TestResultEntity testResultEntity = new Gson().fromJson(jo.toString(), TestResultEntity.class);
            List<TestResultEntity.TestresultBean.TestholeBean> testhole = testResultEntity.getTestresult().getTesthole();
            if (testhole != null) {
                tv_parcount.setText(testResultEntity.getTestresult().getParcount() + "");
                tv_test_name.setText(testResultEntity.getTestresult().getPlayers_username());
                String createtime = testResultEntity.getTestresult().getCreatetime();
                String[] ts = createtime.split("T");
                tv_date.setText(ts[0]);
                testResultListAdapter.setDatas(testhole);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {

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
