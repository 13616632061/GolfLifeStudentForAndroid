package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.glorystudent.adapter.StudentFileListAdapter;
import com.glorystudent.entity.ContractTraineeEntity;
import com.glorystudent.entity.QueryContractRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableListView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的学员
 * Created by hyj on 2017/1/6.
 */
public class StudentsFileActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener {
    private final static String TAG = "StudentsFileActivity";
    private int page = 1;

    @Bind(R.id.lv_student)
    public PullableListView lv_student;

    private StudentFileListAdapter studentFileListAdapter;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    @Override
    protected int getContentId() {
        return R.layout.activity_student_file;
    }
    @Override
    protected void init() {
        ll_empty.setVisibility(View.GONE);
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
        showLoading();
        studentFileListAdapter = new StudentFileListAdapter(this);
        lv_student.setAdapter(studentFileListAdapter);
        lv_student.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        QueryContractRequestEntity queryContractRequestEntity = new QueryContractRequestEntity();
        QueryContractRequestEntity.ContractTraineeBean contractTraineeBean = new QueryContractRequestEntity.ContractTraineeBean();
        queryContractRequestEntity.setPage(page);
        queryContractRequestEntity.setContractTrainee(contractTraineeBean);
        String request = new Gson().toJson(queryContractRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIContractTrainees/QueryContractTrainees";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, requestJson);
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


    @OnClick({R.id.back, R.id.iv_add})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.iv_add:
                //添加学员
                startActivityForResult(new Intent(this, AddStudentActivity.class), 0x513);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x513) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1,1);
            EventBus.getDefault().post(map);
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
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                ContractTraineeEntity contractTraineeEntity = new Gson().fromJson(jo.toString(), ContractTraineeEntity.class);
                List<ContractTraineeEntity.ListContractUserExtBean> listContractUserExt = contractTraineeEntity.getListContractUserExt();
                studentFileListAdapter.setDatas(listContractUserExt);
            }else if (statuscode.equals("2")) {
                ll_empty.setVisibility(View.VISIBLE);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }else {
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
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBus(Map<Integer, Integer> map){
        if (map.containsKey(1)) {
            if (map.get(1).equals(1)) {
                loadDatas();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContractTraineeEntity.ListContractUserExtBean datas = studentFileListAdapter.getDatas(position);
        Intent intent = new Intent(this, StudentProfileActivity.class);
        int userid = datas.getContracttraineeid();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ContractuserExt", datas);
        intent.putExtras(bundle);
        intent.putExtra("userid", userid);
        if (datas.getFriends() != null) {
            intent.putExtra("type", 1);
        }
        startActivity(intent);
    }


}
