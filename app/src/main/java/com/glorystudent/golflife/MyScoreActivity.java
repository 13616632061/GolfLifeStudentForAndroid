package com.glorystudent.golflife;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.ScoreCardListAdapter;
import com.glorystudent.entity.ScoreCardEntity;
import com.glorystudent.entity.ScoreCardRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的计分卡
 * Created by hyj on 2016/12/21.
 */
public class MyScoreActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener, TextWatcher, AdapterView.OnItemClickListener {
    private final static String TAG = "MyScoreActivity";

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.score_lv)
    public ListView score_lv;

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;
    private ScoreCardListAdapter scoreCardListAdapter;
    private List<ScoreCardEntity.ListScorecardBean> listScorecard;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_score;
    }

    /**
     * 初始化
     */
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
            }
        });

        showLoading();
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_search_list, null);
        EditText search = (EditText) inflate.findViewById(R.id.search_address);
        search.addTextChangedListener(this);
        score_lv.addHeaderView(inflate);
        scoreCardListAdapter = new ScoreCardListAdapter(this);
        score_lv.setAdapter(scoreCardListAdapter);
        score_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        ScoreCardRequestEntity scoreCardRequestEntity = new ScoreCardRequestEntity();
        ScoreCardRequestEntity.ScoreCardBean scoreCardBean = new ScoreCardRequestEntity.ScoreCardBean();
        scoreCardRequestEntity.setScorecard(scoreCardBean);
        String request = new Gson().toJson(scoreCardRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIScorecard/QueryScorecard";
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
            Log.d(TAG, "parseDatas: ---->" + json);
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                ll_empty.setVisibility(View.GONE);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                ScoreCardEntity scoreCardEntity = new Gson().fromJson(jo.toString(), ScoreCardEntity.class);
                listScorecard = scoreCardEntity.getListScorecard();
                if (listScorecard != null) {
                    scoreCardListAdapter.setDatas(listScorecard);
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
    public void requestFailed() {
        dismissLoading();
        Toast.makeText(MyScoreActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (text != null && !text.isEmpty()) {
            if (listScorecard != null) {
                List<ScoreCardEntity.ListScorecardBean> datas = new ArrayList<>();
                for (int i = 0; i < listScorecard.size(); i++) {
                    if(listScorecard.get(i).getGolfcoursename().contains(text)){
                        datas.add(listScorecard.get(i));
                    }
                }
                scoreCardListAdapter.setDatas(datas);
            }
        }else {
            scoreCardListAdapter.setDatas(listScorecard);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ScoreCardEntity.ListScorecardBean data = scoreCardListAdapter.getDatas(position - 1);
        Intent intent = new Intent(this, ScoreCardActivity.class);
        intent.putExtra("scorecardid", data.getScorecard_id());
        intent.putExtra("secoredatetime", data.getSecoredatetime());
        intent.putExtra("golfcoursename", data.getGolfcoursename());
        intent.putExtra("scorecardimage", data.getScorecard_image());
        startActivity(intent);
    }
}
