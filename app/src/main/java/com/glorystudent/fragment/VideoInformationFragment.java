package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.VideoInformationListAdapter;
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

import java.util.List;

import butterknife.Bind;

/**
 * 视频资讯
 * Created by hyj on 2017/1/9.
 */
public class VideoInformationFragment extends BaseFragment implements OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener {
    private final static String TAG = "VideoInforFragment";
    @Bind(R.id.video_information_lv)
    public PullableListView video_information_lv;
    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    private VideoInformationListAdapter videoInformationListAdapter;

    private int page = 1;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Override
    protected int getContentId() {
        return R.layout.fragment_video_information;
    }

    @Override
    protected void init(View view) {
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
        showLoading();
        ll_empty.setVisibility(View.GONE);
        videoInformationListAdapter = new VideoInformationListAdapter(getActivity());
        video_information_lv.setAdapter(videoInformationListAdapter);
        video_information_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        NewsRequestEntity newsRequestEntity = new NewsRequestEntity();
        NewsRequestEntity.NewsBean newsBean = new NewsRequestEntity.NewsBean();
        newsBean.setNews_reviewed("1");
        newsBean.setNews_type("3");
        newsRequestEntity.setPage(page);
        newsRequestEntity.setNews(newsBean);
        String request = new Gson().toJson(newsRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APINews/QueryNews";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, requestJson);
    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if(statuscode.equals("1")){
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                StuNewsEntity stuNewsEntity = new Gson().fromJson(jo.toString(), StuNewsEntity.class);
                List<StuNewsEntity.ListnewsBean> listnews = stuNewsEntity.getListnews();
                if (listnews != null) {
                    videoInformationListAdapter.setDatas(listnews);
                }
            }else if(statuscode.equals("2")){
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                ll_empty.setVisibility(View.VISIBLE);
            }else {
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
        Toast.makeText(getActivity(), "请求网络失败", Toast.LENGTH_SHORT).show();
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StuNewsEntity.ListnewsBean data = videoInformationListAdapter.getDatas(position);
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("newsid", data.getNews_id());
        Bundle bundle = new Bundle();
        bundle.putSerializable("newsBean", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
