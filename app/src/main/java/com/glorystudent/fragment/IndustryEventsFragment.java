package com.glorystudent.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.IndustryEventListAdapter;
import com.glorystudent.entity.IndustryEventEntity;
import com.glorystudent.entity.IndustryEventRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.BigEventDetailActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 行业大事件
 * Created by hyj on 2017/1/10.
 */
public class IndustryEventsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final static String TAG = "IndustryEventsFragment";
    @Bind(R.id.industry_events_lv)
    public ListView industry_events_lv;
    private int year;
    private int month;
    private IndustryEventListAdapter industryEventListAdapter;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh = true;//true 是下拉刷新， false 是上拉加载
    private List<IndustryEventEntity.ListIndustryeventsBean> nullDatas;
    private TextView tv_calendar;
    private int page = 1;
    private List<IndustryEventEntity.ListIndustryeventsBean> list_industryevents;

    @Override
    protected int getContentId() {
        return R.layout.fragment_industry_events;
    }

    @Override
    protected void init(View view) {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
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
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_industry_events_head, null);
        ImageView left = (ImageView) inflate.findViewById(R.id.left);
        ImageView right = (ImageView) inflate.findViewById(R.id.right);
        tv_calendar = (TextView) inflate.findViewById(R.id.tv_calendar);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        industry_events_lv.addHeaderView(inflate);
        String currentTime = RequestUtil.getCurrentTime();
        String[] split = currentTime.split("-");
        year = Integer.valueOf(split[0]);
        month = Integer.valueOf(split[1]);
        String date = year + "年" + month + "月";
        tv_calendar.setText(date);
        industryEventListAdapter = new IndustryEventListAdapter(getActivity());
        industry_events_lv.setAdapter(industryEventListAdapter);
        industry_events_lv.setOnItemClickListener(this);
        nullDatas = new ArrayList<>();
    }

    @Override
    protected void loadDatas() {
        showLoading();
        IndustryEventRequestEntity industryEventsEntity = new IndustryEventRequestEntity();
        IndustryEventRequestEntity.IndustryEventsBean industryEventsBean = new IndustryEventRequestEntity.IndustryEventsBean();
        String beginDate = year + "-" + month + "-" + "1";
//        String endDate = year + "-" + month + "-" + "1";
        industryEventsBean.setIndustryevents_Begindate(beginDate);
//        industryEventsBean.setIndustryevents_Enddate(endDate);
        industryEventsEntity.setIndustryevents(industryEventsBean);
        industryEventsEntity.setPage(page);
        String json = new Gson().toJson(industryEventsEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), json);
        String url = "/api/APIIndustryEvents/QueryIndustryEvents";
        Log.i(TAG, "loadDatas: " + requestJson);
        Log.i(TAG, "loadDatas: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                dismissLoading();
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        IndustryEventEntity industryEventEntity = new Gson().fromJson(jo.toString(), IndustryEventEntity.class);
                        list_industryevents = industryEventEntity.getList_industryevents();
                        if (list_industryevents != null) {
                            if (isRefresh) {
                                industryEventListAdapter.setDatas(list_industryevents);
                            } else {
                                industryEventListAdapter.addDatas(list_industryevents);
                            }
                        }
                    } else if (statuscode.equals("2")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                    } else {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                page--;
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(getActivity(), url, requestJson);
    }

    private void setDate() {
        String date = year + "年" + month + "月";
        tv_calendar.setText(date);
        page = 1;
        isRefresh = true;
        loadDatas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                //往上一个月
                month--;
                if (month <= 0) {
                    month = 12;
                    year--;
                }
                break;
            case R.id.right:
                //往下一个月
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
                break;
        }
        industryEventListAdapter.setDatas(nullDatas);
        setDate();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " + position);
        if (position > 0) {
            String url = list_industryevents.get(position - 1).getUrl();
            Intent intent = new Intent(getActivity(), BigEventDetailActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }
}
