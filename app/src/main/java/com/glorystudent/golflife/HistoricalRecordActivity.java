package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.HistoricalRecordListAdapter;
import com.glorystudent.entity.HistoricalRecordEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 历史纪录
 * Created by hyj on 2017/1/6.
 */
public class HistoricalRecordActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "HistoricalActivity";
    @Bind(R.id.lv_historical)
    public PullableNoUpListView lv_historical;
    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;
    private HistoricalRecordListAdapter historicalRecordListAdapter;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    public static ClickCallBack clickCallBack;
    //delete 删除  query列表信息
    public String STATE;

    @Override
    protected int getContentId() {
        return R.layout.activity_historical_record;
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
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.NOMORE);
            }
        });
        ll_empty.setVisibility(View.GONE);
        historicalRecordListAdapter = new HistoricalRecordListAdapter(this);
        lv_historical.setAdapter(historicalRecordListAdapter);

        clickCallBack = new ClickCallBack() {
            @Override
            public void itemClickCallBack(int position) {
                itemClick(position);
            }

            @Override
            public void deleteClickCallBack(int position) {
                deleteItem(position);

            }


        };
    }



    @Override
    protected void loadDatas() {
        historicalRecordListAdapter.notifyDataSetChanged();
        String url = "/api/APITests/QueryTests";
        String request = RequestUtil.getEmptyParameter(this);
        Log.i("request",request);
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, request);
        STATE="query";
    }

    @Override
    public void parseDatas(String json) {
        switch (STATE){
            case "query":
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if(statuscode.equals("1")){
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        HistoricalRecordEntity historicalRecordEntity = new Gson().fromJson(jo.toString(), HistoricalRecordEntity.class);
                        List<HistoricalRecordEntity.TestsListBean> listtest = historicalRecordEntity.getTestsList();
                        if (listtest != null) {
                            historicalRecordListAdapter.setDatas(listtest);
                        }else{
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                    }else{
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    if(statuscode.equals("1")){
                        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                        loadDatas();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void requestFailed() {
        Toast.makeText(HistoricalRecordActivity.this, "请求网络失败", Toast.LENGTH_SHORT).show();
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    //条目点击跳转下一页
    public void itemClick(int position){
        HistoricalRecordEntity.TestsListBean datas = historicalRecordListAdapter.getDatas(position);
        int test_id =  datas.getTestID();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("testid",test_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String request = RequestUtil.getJson(this, "\"Tests\":"  + jsonObject);
        if (datas.getTestType()==1||datas.getTestType()==2){
            Intent intent = new Intent(this, TestResultActivity.class);
            intent.putExtra("request", request);
            intent.putExtra("type", "query");
            startActivity(intent);
        }else if (datas.getTestType()==3){
            Intent intent = new Intent(this, EvaluationResultActivity.class);
            intent.putExtra("test_id", test_id);
            intent.putExtra("type", "test");
            startActivity(intent);
        }
    }
    //删除条目
    private void deleteItem(int position) {
        HistoricalRecordEntity.TestsListBean datas = historicalRecordListAdapter.getDatas(position);
        int test_id =  datas.getTestID();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("testid",test_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String request = RequestUtil.getJson(this, "\"Tests\":"  + jsonObject);
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, "/api/APITests/DelTests", request);
        STATE="delete";
    }

    public interface ClickCallBack{
        void itemClickCallBack(int position);
        void deleteClickCallBack(int position);
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
