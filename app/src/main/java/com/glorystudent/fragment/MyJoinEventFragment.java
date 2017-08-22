package com.glorystudent.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.glorystudent.adapter.MyJoinEventListAdapter;
import com.glorystudent.entity.MyJoinEventEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.widget.oywidget.MyListView;
import com.glorystudent.golflife.EventDetail3Activity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MyJoinEventFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "MyJoinEventFragment";
    @Bind(R.id.join_event_lv)
    MyListView joinEventLv;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @Bind(R.id.no_event)
    RelativeLayout noEvent;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private MyJoinEventListAdapter myJoinEventListAdapter;
    private int page = 1;
    private List<MyJoinEventEntity.ListsignupBean> datas;

    @Override
    protected int getContentId() {
        return R.layout.fragment_my_join_event;
    }

    @Override
    protected void init(View view) {
        EventBus.getDefault().register(this);
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
        showLoading();
        myJoinEventListAdapter = new MyJoinEventListAdapter(getActivity());
        joinEventLv.setAdapter(myJoinEventListAdapter);
        joinEventLv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String json = "\"page\":" + page;
        String requestJson = RequestUtil.getJson(getActivity(), json);
        String url = "/api/APISignUp/QuerySignUpByMy";
        Log.i(TAG, "loadDatas: " + requestJson);
        Log.i(TAG, "loadDatas: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {

            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        //有数据返回则正常显示
                        joinEventLv.setVisibility(View.VISIBLE);
                        noEvent.setVisibility(View.GONE);
                        MyJoinEventEntity myJoinEventEntity = new Gson().fromJson(jo.toString(), MyJoinEventEntity.class);
                        datas = myJoinEventEntity.getListsignup();
                        if (datas != null) {
                            if (isRefresh) {
                                myJoinEventListAdapter.setDatas(datas);
                            } else {
                                myJoinEventListAdapter.addDatas(datas);
                            }
                        }
                    } else if (statuscode.equals("2")) {//无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        //没有数据显示noEvent
                        if (datas == null) {
                            joinEventLv.setVisibility(View.GONE);
                            noEvent.setVisibility(View.VISIBLE);
                        }
                    } else {//失败
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                //访问失败则当前页需要重新加载
                page--;
            }
        }).getEntityData(getActivity(), url, requestJson);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyJoinEventEntity.ListsignupBean datas = myJoinEventListAdapter.getDatas(position);
        Log.i(TAG, "onItemClick: " + datas.getSign_activitiesid());
        Intent intent = new Intent(getActivity(), EventDetail3Activity.class);
        intent.putExtra("id", datas.getSign_activitiesid());
        intent.putExtra("state", datas.getSign_state());
        if (datas.getSign_state().equals("1")) {//状态为待付款时需要传orderid
            intent.putExtra("orderId", datas.getOrderid());
        }
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("MyJoinEventFragment")) {
            if (map.get("MyJoinEventFragment").equals("refresh")) {
                Log.i(TAG, "getEvent: 获取到数据刷新");
                isRefresh = true;//刷新
                page = 1;
                loadDatas();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
