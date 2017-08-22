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
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.EventListAdapter;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.CompetityRequestEntity;
import com.glorystudent.entity.EventCompetityEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableListView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 赛事活动
 * Created by hyj on 2016/12/21.
 */
public class EventActivity extends BaseActivity implements AdapterView.OnItemClickListener, TextWatcher {
    private final static String TAG = "EventActivity";
    @Bind(R.id.competition_lv)
    public PullableListView competition_lv;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    @Bind(R.id.tv_activity_name)
    public TextView activityName;
    @Bind(R.id.competition_tv_release)
    public TextView competition_tv_release;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private List<EventCompetityEntity.ListeventactivityBean> datas;
    private EventListAdapter eventListAdapter;
    private int page = 1;
    private EditText search;
    private CompetityRequestEntity competityRequestEntity;
    private int teamId;//球队id

    @Override
    protected int getContentId() {
        return R.layout.activity_event;
    }

    @Override
    protected void init() {
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
        if (competityRequestEntity == null) {
            competityRequestEntity = new CompetityRequestEntity();
            competityRequestEntity.setEventactivity(new CompetityRequestEntity.EventactivityBean());
        }
        Intent intent = getIntent();
        teamId = intent.getIntExtra("teamId", -1);
        if (teamId != -1) {
            activityName.setText("球队活动");
            competition_tv_release.setText("发布队内活动");
            competityRequestEntity.getEventactivity().setEventactivity_teamid(teamId);
        }
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_search_list, null);
        search = (EditText) inflate.findViewById(R.id.search_address);
        search.addTextChangedListener(this);
        competition_lv.addHeaderView(inflate);
        eventListAdapter = new EventListAdapter(this);
        competition_lv.setAdapter(eventListAdapter);
        competition_lv.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
        String text = search.getText().toString().trim();
        requestData(text);
    }

    /**
     * 根据搜索内容请求数据
     *
     * @param text
     */
    private void requestData(String text) {
        competityRequestEntity.getEventactivity().setEventactivity_name(text);
        competityRequestEntity.setPage(page);
        String json = new Gson().toJson(competityRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/Public/APIPublicEventActivity/QueryEventActivity";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        EventCompetityEntity eventCompetityEntity = new Gson().fromJson(jo.toString(), EventCompetityEntity.class);
                        datas = eventCompetityEntity.getListeventactivity();
                        if (datas != null) {
                            if (isRefresh) {
                                eventListAdapter.setDatas(datas);
                            } else {
                                eventListAdapter.addDatas(datas);
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
                Toast.makeText(EventActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                //访问失败则当前页需要重新加载
                page--;
            }
        }).getEntityData(this, url, requestJson);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {//需要登陆
            EventCompetityEntity.ListeventactivityBean datas = eventListAdapter.getData(position - 1);
            int eventActivity_id = datas.getEventActivity_id();
            Intent intent = new Intent(this, EventDetailActivity.class);
            intent.putExtra("id", eventActivity_id);
            intent.putExtra("name", datas.getEventactivity_name());
            intent.putExtra("detail", datas.getEventactivity_detail());
            String imgUrl;
            if (datas.getListeventpic() != null && datas.getListeventpic().size() != 0) {
                imgUrl = datas.getListeventpic().get(0).getEventactivity_picpath();
            } else {
                imgUrl = "http://glorygolflife.oss-cn-shenzhen.aliyuncs.com/XXQ2Lsd0yq3b72wWdapS4a5Rg24MhxF6cxEOYyODBFQ%3D%2Fcompetitions%2Fbanners%2Faf2026710e2859c421fc19f67eca1fc8.png";
            }
            intent.putExtra("imageUrl", imgUrl);
            startActivity(intent);
        } else {
            new AlertDialog(this).builder()
                    .setTitle("此功能需要登录")
                    .setMsg("是否去登陆")
                    .setCancelable(true)
                    .setPositiveButton("去登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EventActivity.this, LoginActivity.class));
                        }
                    })
                    .setNegativeButton("取消", null).show();
        }
    }

    @OnClick({R.id.back, R.id.competition_tv_release})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.competition_tv_release:
                if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {//需要登陆
                    if (SharedUtil.getString(Constants.GROUP_ID) != null && !SharedUtil.getString(Constants.GROUP_ID).isEmpty()) {//必须为教练才能使用
                        Intent intent = new Intent(this, EventReleasedActivity.class);
//                        intent.putExtra("add", true);
                        if (teamId != -1) {
                            intent.putExtra("teamId", teamId);
                        }
                        startActivity(intent);
                    } else {
                        Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                            @Override
                            public void onSure() {
                                startActivity(new Intent(EventActivity.this, MyAttestationActivity.class));
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).showDialog(this, "此功能只有教练才能使用", "是否申请教练", "前往");
                    }
                } else {
                    Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                        @Override
                        public void onSure() {
                            startActivity(new Intent(EventActivity.this, LoginActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).showDialog(this, "此功能需要登陆", "是否去登陆", "去登陆");
                }
                break;
        }
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
        String text = s.toString().trim();
        isRefresh = true;
        page = 1;
        requestData(text);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("EventActivity")) {
            if (map.get("EventActivity").equals("refresh")) {
                Log.i(TAG, "getEvent: 是否接收到值");
                isRefresh = true;//刷新
                page = 1;
                loadDatas();
            }
        }
    }
}
