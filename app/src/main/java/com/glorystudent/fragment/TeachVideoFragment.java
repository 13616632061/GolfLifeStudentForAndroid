package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.VideoListAdapter;
import com.glorystudent.entity.VideoEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.VideoDetailsActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 教学视频Fragment
 * Created by hyj on 2016/12/20.
 */
public class TeachVideoFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    public static TeachVideoFragment getInstance(int level){
        TeachVideoFragment teachVideoFragment = new TeachVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        teachVideoFragment.setArguments(bundle);
        return teachVideoFragment;
    }

    private final static String TAG = "TeachVideoFragment";
    @Bind(R.id.teach_video_lv)
    public PullableNoUpListView teach_video_lv;

    private int page = 1;//默认加载第一页
    private String teachingvideo_level;//视频等级
    private VideoEntity videoEntity;
    private VideoListAdapter videoListAdapter;
    private List<VideoEntity.ListvideocollectBean> listvideocollect;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;
    @Override
    protected int getContentId() {
        return R.layout.fragment_teach_video;
    }

    @Override
    protected void init(View view) {
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
            }
        });
        showLoading();
        videoListAdapter = new VideoListAdapter(getActivity());
        teach_video_lv.setAdapter(videoListAdapter);
        teach_video_lv.setOnItemClickListener(this);
        ll_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatas();
            }
        });
    }

    @Override
    protected void getBundle(Bundle bundle) {
        int level = bundle.getInt("level");
        switch (level) {
            case 0:
                teachingvideo_level = "入门视频";
                break;
            case 1:
                teachingvideo_level = "进阶视频";
                break;
            case 2:
                teachingvideo_level = "高级视频";
                break;
        }
        loadDatas();
    }

    public void loadDatas() {
        String videoJson = RequestUtil.getTeachVideo(getActivity(), teachingvideo_level, page);
        OkGo.post(Constants.BASE_URL+"/api/APITeachingVideo/QueryTeachingVideo")
                .tag(this)//
                .params("request", videoJson)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if(statuscode.equals("1")){
                                ll_empty.setVisibility(View.GONE);
                                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                                videoEntity = new Gson().fromJson(jo.toString(), VideoEntity.class);
                                List<VideoEntity.ListTeachingVideoBean> listTeachingVideo = videoEntity.getListTeachingVideo();
                                listvideocollect = videoEntity.getListvideocollect();
                                if(listTeachingVideo != null){
                                    videoListAdapter.setDatas(listTeachingVideo);
                                }
                            } else if (statuscode.equals("2")) {
                                ll_empty.setVisibility(View.VISIBLE);
                                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                            } else {
                                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dismissLoading();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissLoading();
                        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VideoEntity.ListTeachingVideoBean datas = videoListAdapter.getDatas(position);
        Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("video", datas);
        if (listvideocollect != null) {
            for (VideoEntity.ListvideocollectBean listvideocollectBean : listvideocollect) {
                if(listvideocollectBean.get_CollectObjectID() == datas.getTeachingvideo_id()){
                    bundle.putInt("collectid", listvideocollectBean.getCollectid());
                }
            }
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
