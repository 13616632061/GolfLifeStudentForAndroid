package com.glorystudent.golflife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.StudentCourseListAdapter;
import com.glorystudent.entity.MyCourseEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的课程
 * Created by hyj on 2016/12/21.
 */
public class MyCourseActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener {
    private final static String TAG = "MyCourseActivity";
    @Bind(R.id.lv_course)
    public ListView lv_course;
    public StudentCourseListAdapter studentCourseListAdapter;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_course;
    }

    @Override
    protected void init() {
        ll_empty.setVisibility(View.GONE);
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.NOMORE);
            }
        });

        showLoading();
        studentCourseListAdapter = new StudentCourseListAdapter(this);
        lv_course.setAdapter(studentCourseListAdapter);
        lv_course.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String request = "{" + "\"contracttype\":" + "2" + "}";
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIContract/QueryContractList";
        Log.d(TAG, "loadDatas: --->" + requestJson);
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
            Log.d(TAG, "parseDatas: ----》" + json);
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                MyCourseEntity myCourseEntity = new Gson().fromJson(jo.toString(), MyCourseEntity.class);
                List<MyCourseEntity.CcourselistBean> ccourselist = myCourseEntity.getCcourselist();
                studentCourseListAdapter.setDatas(ccourselist);
            } else if (statuscode.equals("2")) {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                ll_empty.setVisibility(View.VISIBLE);
            } else {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissLoading();
    }

    @Override
    public void requestFailed() {
        dismissLoading();
        Toast.makeText(MyCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyCourseEntity.CcourselistBean datas = studentCourseListAdapter.getDatas(position);
        Intent intent = new Intent(this, StudentAppointmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", datas);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
