package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.ApplyCheckListAdapter;
import com.glorystudent.entity.ApplyCheckEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.widget.oywidget.MyListView;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 申请入队的审核页面
 * Created by Gavin.J on 2017/5/19.
 */

public class ApplicationCheckActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ApplyCheckActivity";

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.list_view)
    MyListView listView;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @Bind(R.id.ll_no_team_plan)
    LinearLayout noTeamPlan;
    private int teamId;
    private boolean isRefresh = true;
    private int page = 1;
    private ApplyCheckListAdapter applyCheckListAdapter;
    private List<ApplyCheckEntity.ApplyTeamUserListBean> datas;

    @Override
    protected int getContentId() {
        return R.layout.activity_application_check;
    }

    @Override
    protected void init() {
        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                page = 1;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                page++;
                loadDatas();
            }
        });
        Intent intent = getIntent();
        teamId = intent.getIntExtra("id", -1);
        showLoading();
        applyCheckListAdapter = new ApplyCheckListAdapter(this);
        listView.setAdapter(applyCheckListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String json = "\"team\":{" + "\"id\":" + teamId + "}," + "\"page\":" + page;
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/api/APITeam/QueryTeamUserApply";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        ApplyCheckEntity applyCheckEntity = new Gson().fromJson(jo.toString(), ApplyCheckEntity.class);
                        if (applyCheckEntity != null) {
                            List<ApplyCheckEntity.ApplyTeamUserListBean> applyTeamUserList = applyCheckEntity.getApplyTeamUserList();
                            datas = applyTeamUserList;
                            if (datas != null && datas.size() > 0) {
                                noTeamPlan.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                                if (isRefresh) {
                                    applyCheckListAdapter.setDatas(datas);
                                } else {
                                    applyCheckListAdapter.addDatas(datas);
                                }
                            } else {
                                if (listView.getCount() > 0) {
                                    noTeamPlan.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    noTeamPlan.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            }
                        }
                    } else if (statuscode.equals("2")) {//暂无数据
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(ApplicationCheckActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                //访问失败则当前页需要重新加载
                page--;
            }
        }).getEntityData(this, url, requestJson);
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (datas.get(position).getStatus() == 0) {//为待同意才有点击事件
            Map<String, ApplyCheckEntity.ApplyTeamUserListBean> map = new HashMap<>();
            map.put("TeamApplyInfoActivity", datas.get(position));
            EventBus.getDefault().postSticky(map);
            Intent intent = new Intent(this, TeamApplyInfoActivity.class);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(Map<String, ApplyCheckEntity.ApplyTeamUserListBean> map) {
        if (map.containsKey("ApplicationCheckActivity")) {
            if (map.get("ApplicationCheckActivity").equals("refresh")) {
                isRefresh = true;
                page = 1;
                loadDatas();
            }
        }
    }
}
