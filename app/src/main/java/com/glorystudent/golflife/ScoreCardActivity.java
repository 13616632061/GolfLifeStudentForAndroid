package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glorystudent.adapter.ScoreDetailListAdapter;
import com.glorystudent.entity.ScorecardDetailEntity;
import com.glorystudent.entity.ScorecardDetailRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.widget.oywidget.MyListView;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 计分卡
 * Created by hyj on 2017/3/21.
 */
public class ScoreCardActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.tv_golf_score)
    public TextView tv_score;

    @Bind(R.id.tv_golf_putts)
    public TextView tv_putts;

    @Bind(R.id.tv_golf_name)
    public TextView tv_golf_name;

    @Bind(R.id.tv_play_date)
    public TextView tv_play_date;

    @Bind(R.id.score_lv)
    public MyListView score_lv;

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    private int scorecardid;
    private int sumPar = 0;
    private int sumScore = 0;
    private int sumPutts = 0;
    private ScoreDetailListAdapter scoreDetailListAdapter;
    private String scorecardimage;

    @Override
    protected int getContentId() {
        return R.layout.activity_score_card;
    }


    @Override
    protected void init() {
        ll_empty.setVisibility(View.GONE);
        refresh_view.setVisibility(View.GONE);
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
        Intent intent = getIntent();
        scorecardid = intent.getIntExtra("scorecardid", -1);
        String secoredatetime = intent.getStringExtra("secoredatetime");
        tv_play_date.setText(TimeUtil.getEventTime(secoredatetime));
        String golfcoursename = intent.getStringExtra("golfcoursename");
        tv_golf_name.setText(golfcoursename);
        scorecardimage = intent.getStringExtra("scorecardimage");
        scoreDetailListAdapter = new ScoreDetailListAdapter(this);
        score_lv.setAdapter(scoreDetailListAdapter);
    }

    @Override
    protected void loadDatas() {
        if (scorecardid != -1) {
            ScorecardDetailRequestEntity scorecardDetailEntity = new ScorecardDetailRequestEntity();
            ScorecardDetailRequestEntity.ScorecardDetailBean scorecardDetailBean = new ScorecardDetailRequestEntity.ScorecardDetailBean();
            scorecardDetailBean.setScorecard_id(scorecardid);
            scorecardDetailEntity.setScorecardDetail(scorecardDetailBean);
            String request = new Gson().toJson(scorecardDetailEntity);
            String requestJson = RequestUtil.getRequestJson(this, request);
            String url = "/api/APIScorecard/QueryScorecardDetail";
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, requestJson);
        }
    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                ll_empty.setVisibility(View.GONE);
                refresh_view.setVisibility(View.VISIBLE);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                ScorecardDetailEntity scorecardDetailEntity = new Gson().fromJson(jo.toString(), ScorecardDetailEntity.class);
                List<ScorecardDetailEntity.ListScorecardDetailBean> listScorecardDetail = scorecardDetailEntity.getListScorecardDetail();
                List<List<ScorecardDetailEntity.ListScorecardDetailBean>> datas = new ArrayList<>();
                for (int i = 0; i < (listScorecardDetail.size()/9); i++) {
                    List<ScorecardDetailEntity.ListScorecardDetailBean> scoreDatas = new ArrayList<>();
                    int start = i * 9;
                    int end = (i + 1) * 9;
                    int itemSumPar = 0;
                    int itemSumScore = 0;
                    int itemSumPutts = 0;
                    for (int j = start; j < end; j++) {
                        itemSumPar = itemSumPar + listScorecardDetail.get(j).getPar();
                        itemSumScore = itemSumScore + listScorecardDetail.get(j).getScore();
                        itemSumPutts = itemSumPutts + listScorecardDetail.get(j).getPutts();
                        scoreDatas.add(listScorecardDetail.get(j));
                    }
                    ScorecardDetailEntity.ListScorecardDetailBean sumBean = new ScorecardDetailEntity.ListScorecardDetailBean();
                    sumBean.setPar(itemSumPar);
                    sumBean.setScore(itemSumScore);
                    sumBean.setPutts(itemSumPutts);
                    sumScore = sumScore + itemSumScore;
                    sumPutts = sumPutts + itemSumPutts;
                    scoreDatas.add(sumBean);
                    datas.add(scoreDatas);
                }
                tv_score.setText(sumScore + "");
                tv_putts.setText(sumPutts + "");
                scoreDetailListAdapter.setDatas(datas);
            } else if (statuscode.equals("2")) {
                ll_empty.setVisibility(View.VISIBLE);
            } else {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @OnClick({R.id.back, R.id.tv_look_pic})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_look_pic:
                //查看原始图片
                if (scorecardimage != null) {
                    List<String> picList = new ArrayList<>();
                    picList.add(scorecardimage);
                    Intent intent = new Intent(this, ImageBrowsingActivity.class);
                    intent.putStringArrayListExtra("picList", (ArrayList<String>) picList);
                    startActivity(intent);
                }
                break;
        }
    }


}
