package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.ChoosePitchListAdapter;
import com.glorystudent.entity.CourtLocationEntity;
import com.glorystudent.entity.SingleLocationEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择球场
 * Created by hyj on 2017/4/18.
 */
public class ChoosePitchActivity extends BaseActivity implements AdapterView.OnItemClickListener, TextWatcher {
    private static final String TAG = "ChoosePitchActivity";
    @Bind(R.id.lv_pitch)
    public ListView lv_pitch;

    private ChoosePitchListAdapter choosePitchListAdapter;
    private String longitude;
    private String latitude;
    private int page = 1;
    private int searchPage = 1;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh = true;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.et_court)
    public EditText et_court;

    @Bind(R.id.tv_search)
    public TextView tv_search;

    private boolean isSearch = false;//是否搜索
    private boolean searchFlag = false; //true 为搜索  false 为取消
    private List<CourtLocationEntity.ListCourtBean> firstlistCourt;
    private TextView tv_near;

    @Override
    protected int getContentId() {
        return R.layout.activity_choose_pitch;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        longitude = intent.getStringExtra("longitude");
        latitude = intent.getStringExtra("latitude");

        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                if (isSearch) {
                    searchPage = 1;
                    String text = et_court.getText().toString();
                    if (text != null && !text.isEmpty()) {
                        searchCourt(text);
                    }
                } else {
                    page = 1;
                    loadDatas();
                }
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                if (isSearch) {
                    searchPage++;
                    String text = et_court.getText().toString();
                    if (text != null && !text.isEmpty()) {
                        searchCourt(text);
                    }
                } else {
                    page++;
                    loadDatas();
                }
            }
        });
        et_court.addTextChangedListener(this);
        firstlistCourt = new ArrayList<>();
        showLoading();
        Log.d(TAG, "init: ----》" + longitude + " " + latitude);
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_choose_pitch_head, null);
        tv_near = (TextView) inflate.findViewById(R.id.tv_near);
        lv_pitch.addHeaderView(inflate);
        choosePitchListAdapter = new ChoosePitchListAdapter(this);
        lv_pitch.setAdapter(choosePitchListAdapter);
        lv_pitch.setOnItemClickListener(this);
    }


    @Override
    protected void loadDatas() {
        if (longitude != null && latitude != null) {
            SingleLocationEntity singleLocationEntity = new SingleLocationEntity();
            SingleLocationEntity.CourtBean courtBean = new SingleLocationEntity.CourtBean();
            courtBean.setCourt_longitude(longitude);
            courtBean.setCourt_latitude(latitude);
            singleLocationEntity.setCourt(courtBean);
            singleLocationEntity.setPage(page);
            String request = new Gson().toJson(singleLocationEntity);
            String requestJson = RequestUtil.getRequestJson(this, request);
            Log.d("print", "searchCourt: --->" + requestJson);
            String url = "/public/APIPublicCourt/QueryCourt";
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                @Override
                public void parseDatas(String json) {
                    try {
                        JSONObject jo = new JSONObject(json);
                        String statuscode = jo.getString("statuscode");
                        String statusmessage = jo.getString("statusmessage");
                        if (statuscode.equals("1")) {
                            refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                            CourtLocationEntity courtLocationEntity = new Gson().fromJson(jo.toString(), CourtLocationEntity.class);
                            firstlistCourt = courtLocationEntity.getListCourt();
                            if (firstlistCourt != null && firstlistCourt.size() > 0) {
                                if (isRefresh) {
                                    choosePitchListAdapter.setDatas(firstlistCourt);
                                } else {
                                    choosePitchListAdapter.addDatas(firstlistCourt);
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
                    dismissLoading();
                }

                @Override
                public void requestFailed() {
                    dismissLoading();
                    refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    Toast.makeText(ChoosePitchActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }).getEntityData(this, url, requestJson);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        choosePitchListAdapter.setChooseCourt(position - 1);
    }


    @OnClick({R.id.back, R.id.tv_search, R.id.sure})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_search:
                //搜索
                if (searchFlag = !searchFlag) {
                    tv_near.setVisibility(View.GONE);
                    tv_search.setText("取消");
                    isSearch = true;
                    searchPage = 1;
//                lv_pitch.removeViewAt(0);
                    String text = et_court.getText().toString();
                    if (text != null && !text.isEmpty()) {
                        searchCourt(text);
                    } else {
                        Toast.makeText(ChoosePitchActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tv_near.setVisibility(View.VISIBLE);
                    tv_search.setText("搜索");
                    et_court.setText("");
                    choosePitchListAdapter.setChooseCourt(0);
                    choosePitchListAdapter.setDatas(firstlistCourt);
                }

                break;
            case R.id.sure:
                //确定
                CourtLocationEntity.ListCourtBean datas = choosePitchListAdapter.getDatas(choosePitchListAdapter.getChooseCourtPosition());
                Intent intent = new Intent();
                intent.putExtra("courtname", datas.getCourt_name());
                intent.putExtra("courtid", datas.getCourt_id());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void searchCourt(String courtname) {
        SingleLocationEntity singleLocationEntity = new SingleLocationEntity();
        SingleLocationEntity.CourtBean courtBean = new SingleLocationEntity.CourtBean();
        courtBean.setCourt_name(courtname);
        singleLocationEntity.setCourt(courtBean);
        singleLocationEntity.setPage(searchPage);
        String request = new Gson().toJson(singleLocationEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        Log.d("print", "searchCourt: --->" + requestJson);
        String url = "/public/APIPublicCourt/QueryCourt";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    Log.d("print", "parseDatas: " + json);
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        CourtLocationEntity courtLocationEntity = new Gson().fromJson(jo.toString(), CourtLocationEntity.class);
                        List<CourtLocationEntity.ListCourtBean> listCourt = courtLocationEntity.getListCourt();
                        if (listCourt != null && listCourt.size() > 0) {
                            if (isRefresh) {
                                choosePitchListAdapter.setChooseCourt(0);
                                choosePitchListAdapter.setDatas(listCourt);
                            } else {
                                choosePitchListAdapter.addDatas(listCourt);
                            }
                        }
                    } else if (statuscode.equals("2")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
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
        }).getEntityData(this, url, requestJson);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
