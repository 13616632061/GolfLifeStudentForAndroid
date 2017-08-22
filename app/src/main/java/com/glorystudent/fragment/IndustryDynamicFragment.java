package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.IndustryDynamicListAdapter;
import com.glorystudent.entity.NewsRequestEntity;
import com.glorystudent.entity.StuNewsEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.NewsDetailsActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableListView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 行业动态
 * Created by hyj on 2017/1/9.
 */
public class IndustryDynamicFragment extends BaseFragment implements OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener {
    private final static String TAG = "IndustryDynamicFragment";
    @Bind(R.id.industry_dynamic_lv)
    public PullableListView industry_dynamic_lv;
    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;
    private IndustryDynamicListAdapter homeListAdapter;//ListView设配器
    private List<StuNewsEntity.ListnewsBean> news;
    private List<StuNewsEntity.ListnewsBean> newsDatas;
    private StuNewsEntity newsEntity;
    private int newsPage = 1; //默认加载第1页

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Override
    protected int getContentId() {
        return R.layout.fragment_industry_dynamic;
    }

    @Override
    protected void init(View view) {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                newsPage = 1;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                newsPage ++;
                loadDatas();
            }
        });
        showLoading();
        ll_empty.setVisibility(View.GONE);
        homeListAdapter = new IndustryDynamicListAdapter(getActivity());
        industry_dynamic_lv.setAdapter(homeListAdapter);
        industry_dynamic_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        NewsRequestEntity requestEntity = new NewsRequestEntity();
        NewsRequestEntity.NewsBean newsBean = new NewsRequestEntity.NewsBean();
        requestEntity.setNews(newsBean);
        requestEntity.setPage(newsPage);
        String newsJson = new Gson().toJson(requestEntity);
        String requestNewsJson = RequestUtil.getRequestJson(getActivity(), newsJson);
        String url = "/api/APINews/QueryNews";
        Log.d(TAG, "loadDatas: --->" + requestNewsJson);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, requestNewsJson);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        newsDatas = new ArrayList<>();
        news = newsEntity.getListnews();
        for (int i = 0; i < news.size(); i++) {
            if(news.get(i).getNews_top().equals("0")){
                newsDatas.add(news.get(i));
            }
        }
        if (isRefresh) {
            homeListAdapter.setDatas(newsDatas);
        }else {
            homeListAdapter.addDatas(newsDatas);
        }
    }

    @Override
    public void parseDatas(String json) {
        try {
            Log.d(TAG, "parseDatas: --->" + json);
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if(statuscode.equals("1")){
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                newsEntity = new Gson().fromJson(jo.toString(), StuNewsEntity.class);
                if (newsEntity.getListnews() != null) {
                    initDatas();
                }
            }else if(statuscode.equals("2")){
                ll_empty.setVisibility(View.VISIBLE);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }else{
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
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
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StuNewsEntity.ListnewsBean data = homeListAdapter.getData(position);
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("newsid", data.getNews_id());
        Bundle bundle = new Bundle();
        bundle.putSerializable("newsBean", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
