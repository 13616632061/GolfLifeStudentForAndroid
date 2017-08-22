package com.glorystudent.golflife;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.HistoryPlayerListAdapter;
import com.glorystudent.entity.HistoryPlayerEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 填写球员
 * Created by hyj on 2017/1/4.
 */
public class FillPlayerActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private final static String TAG = "FillPlayerActivity";

    private HistoryPlayerListAdapter historyPlayerListAdapter;
    @Bind(R.id.lv_player)
    public PullableNoUpListView lv_player;
    private EditText et_player_name;//填写球员名字

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private int playersUserID;

    @Override
    protected int getContentId() {
        return R.layout.activity_fill_player;
    }

    @Override
    protected void init() {
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
                loadDatas();
            }
        });

        View head = LayoutInflater.from(this).inflate(R.layout.item_history_player_head, null);
        LinearLayout ll_address_friend = (LinearLayout) head.findViewById(R.id.ll_address_friend);
        LinearLayout ll_golf_friend = (LinearLayout) head.findViewById(R.id.ll_golf_friend);
        ll_address_friend.setOnClickListener(this);
        ll_golf_friend.setOnClickListener(this);
        et_player_name = (EditText) head.findViewById(R.id.et_player_name);
        lv_player.addHeaderView(head);
        historyPlayerListAdapter = new HistoryPlayerListAdapter(this);
        lv_player.setAdapter(historyPlayerListAdapter);
        lv_player.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String requestJson = RequestUtil.getEmptyParameter(this);
        String url = "/api/APITest/QueryTestUserList";
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, requestJson);
    }

    @OnClick({R.id.back, R.id.complete})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.complete:
                //完成
                if (et_player_name.getText().toString().isEmpty()) {
                    Toast.makeText(FillPlayerActivity.this, "亲，球员名称不能为空～", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    String s = et_player_name.getText().toString();
                    intent.putExtra("playername", s);
                    intent.putExtra("playersUserID", playersUserID);
                    setResult(0x044, intent);
                    finish();
                }
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
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                HistoryPlayerEntity historyPlayerEntity = new Gson().fromJson(jo.toString(), HistoryPlayerEntity.class);
                List<HistoryPlayerEntity.ListtestuserlogBean> listtestuserlog = historyPlayerEntity.getListtestuserlog();
                if (listtestuserlog != null) {
                    historyPlayerListAdapter.setDatas(listtestuserlog);
                }
            }else {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.NOMORE);
                Toast.makeText(FillPlayerActivity.this,"错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HistoryPlayerEntity.ListtestuserlogBean datas = historyPlayerListAdapter.getDatas(position - 1);//因添加了头部，所以下标需-1
        String username = datas.getUsername();
        et_player_name.setText(username);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_address_friend:
                //通讯录好友添加
                startActivityForResult(new Intent(this, AddressFriendsActivity.class), 0x341);
                break;
            case R.id.ll_golf_friend:
                //GolfLife好友添加
                startActivityForResult(new Intent(this, FriendsListActivity.class), 0x341);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x341 && resultCode == 0x342 && data != null) {
            String username = data.getStringExtra("username");
            String phonenumber = data.getStringExtra("phonenumber");
            playersUserID = data.getIntExtra("userid",0);

            et_player_name.setText(username);
        }
    }
}
