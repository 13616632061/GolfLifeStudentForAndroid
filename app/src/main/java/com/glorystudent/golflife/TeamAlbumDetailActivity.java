package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.TeamAlbumDetailGridAdapter;
import com.glorystudent.entity.TeamAlbumDetailEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.widget.oywidget.MyGridView;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/5/18.
 */

public class TeamAlbumDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "TeamAlbumDetailActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_activity_name)
    TextView activityName;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @Bind(R.id.gv_album_grid_view)
    MyGridView gridView;
    private int eventactivity_id;
    private boolean isRefresh = true;
    private List<TeamAlbumDetailEntity.PhotolistBean> datas;
    private TeamAlbumDetailGridAdapter teamAlbumDetailGridAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_team_album_detail;
    }

    @Override
    protected void init() {
        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            }
        });
        Intent intent = getIntent();
        eventactivity_id = intent.getIntExtra("eventactivity_id", -1);
        String eventactivity_name = intent.getStringExtra("eventactivity_name");
        activityName.setText(eventactivity_name);
        teamAlbumDetailGridAdapter = new TeamAlbumDetailGridAdapter(this);
        gridView.setAdapter(teamAlbumDetailGridAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String json = "\"Photoid\":" + eventactivity_id;
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/api/APITeam/QueryTeamEventActivityPhotoDeatil";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        TeamAlbumDetailEntity teamAlbumDetailEntity = new Gson().fromJson(jo.toString(), TeamAlbumDetailEntity.class);
                        datas = teamAlbumDetailEntity.getPhotolist();
                        if (datas != null) {
                            teamAlbumDetailGridAdapter.setDatas(datas);
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
                Toast.makeText(TeamAlbumDetailActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
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
        Map<String, List<TeamAlbumDetailEntity.PhotolistBean>> map = new HashMap<>();
        map.put("TeamAlbumPhotoActivity", datas);
        EventBus.getDefault().postSticky(map);
        Intent intent = new Intent(this, TeamAlbumPhotoActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
