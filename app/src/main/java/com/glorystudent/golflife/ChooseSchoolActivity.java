package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.ChooseSchoolAdapter;
import com.glorystudent.entity.SchoolEntity;
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
 * 所属院校
 * Created by hyj on 2016/12/26.
 */
public class ChooseSchoolActivity extends BaseActivity implements TextWatcher, AdapterView.OnItemClickListener, OkGoRequest.OnOkGoUtilListener {
    @Bind(R.id.address_lv)
    public ListView address_lv;
    private final static String TAG = "ChooseSchoolActivity";
    private ChooseSchoolAdapter chooseSchoolAdapter;//选择院校适配器
    private List<SchoolEntity.ListcoachgroupBean> listcoachgroup;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private EditText et_search_address;

    @Override
    protected int getContentId() {
        return R.layout.activity_choose_school;
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
            }
        });
        showLoading();
        View head = LayoutInflater.from(this).inflate(R.layout.item_search_list, null);
        et_search_address = (EditText) head.findViewById(R.id.search_address);
        et_search_address.addTextChangedListener(this);
        address_lv.addHeaderView(head);
        chooseSchoolAdapter = new ChooseSchoolAdapter(this);
        address_lv.setAdapter(chooseSchoolAdapter);
        address_lv.setOnItemClickListener(this);
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


    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
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


    /**
     * 加载数据
     */
    @Override
    protected void loadDatas() {
        String request = RequestUtil.getEmptyParameter(this);
        String url = "/api/APICoachGroup/SelectAllCoachGroup";
        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, request);
    }


    /**
     * 解析数据
     *
     * @param json
     */
    @Override
    public void parseDatas(String json) {
        dismissLoading();
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("-114")) {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                Toast.makeText(ChooseSchoolActivity.this, statusmessage + "，请重新登陆", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChooseSchoolActivity.this, LoginActivity.class));
                finish();
            } else if (statuscode.equals("1")) {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                SchoolEntity schoolEntity = new Gson().fromJson(jo.toString(), SchoolEntity.class);
                listcoachgroup = schoolEntity.getListcoachgroup();
                chooseSchoolAdapter.setDatas(listcoachgroup);
            }else if(statuscode.equals("2")){
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }else{
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 请求失败
     */
    @Override
    public void requestFailed() {
        dismissLoading();
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
        Toast.makeText(ChooseSchoolActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String textStr = s.toString();
        if (textStr != null) {
            if (listcoachgroup != null) {
                List<SchoolEntity.ListcoachgroupBean> list = new ArrayList<>();
                for (SchoolEntity.ListcoachgroupBean listcoachgroupBean : listcoachgroup) {
                    if (listcoachgroupBean.getGroupname().contains(textStr)) {
                        list.add(listcoachgroupBean);
                    }
                }
                chooseSchoolAdapter.setDatas(list);
            }
        } else {
            chooseSchoolAdapter.setDatas(listcoachgroup);
        }
    }

    @OnClick({R.id.back, R.id.tv_add_school})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_add_school:
                //添加学员
                String text = et_search_address.getText().toString();
                if (text != null && !text.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("address", text);
                    setResult(0x046, intent);
                    finish();
                }else {
                    Toast.makeText(ChooseSchoolActivity.this, "请输入要添加的学院名称", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SchoolEntity.ListcoachgroupBean datas = chooseSchoolAdapter.getDatas(position - 1);
        Intent intent = new Intent();
        intent.putExtra("address", datas.getGroupname());
        intent.putExtra("groupid", datas.getCoachgroupid());
        setResult(0x046, intent);
        finish();
    }
}
