package com.glorystudent.golflife;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.TeachingCenterListAdapter;
import com.glorystudent.entity.TeachingCenterEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教学中心
 * Created by hyj on 2016/12/28.
 */
public class TeachingCenterActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "TeachingCenterActivity";
    @Bind(R.id.teaching_center_lv)
    public ListView teaching_center_lv;
    private TeachingCenterListAdapter teachingCenterListAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_teaching_center;
    }

    @Override
    protected void init() {
        showLoading();
        teachingCenterListAdapter = new TeachingCenterListAdapter(this);
        teaching_center_lv.setAdapter(teachingCenterListAdapter);
    }

    @Override
    protected void loadDatas() {
        String url = "/api/APIGroupAddress/QueryAddressList";
        String requestJson = RequestUtil.getEmptyParameter(this);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, requestJson);
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

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if(statuscode.equals("1")){
                TeachingCenterEntity teachingCenterEntity = new Gson().fromJson(jo.toString(), TeachingCenterEntity.class);
                List<TeachingCenterEntity.ListgroupaddressBean> listgroupaddress = teachingCenterEntity.getListgroupaddress();
                if (listgroupaddress != null) {
                    teachingCenterListAdapter.setDatas(listgroupaddress);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dismissLoading();
    }

    @Override
    public void requestFailed() {
        dismissLoading();
        Toast.makeText(TeachingCenterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
    }
}
