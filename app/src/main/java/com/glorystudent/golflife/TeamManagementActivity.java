package com.glorystudent.golflife;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.MyTeamListAdapter;
import com.glorystudent.adapter.NearTeamListAdapter;
import com.glorystudent.entity.LocationEntity;
import com.glorystudent.entity.TeamManagementEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.widget.oywidget.MyListView;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/5/16.
 */

public class TeamManagementActivity extends BaseActivity implements AdapterView.OnItemClickListener, TextWatcher {
    private static final String TAG = "TeamManagementActivity";
    private static final int REQUEST_CITY_CODE = 0x123;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_create)
    TextView create;
    @Bind(R.id.tv_team_city)
    TextView tvCity;
    @Bind(R.id.et_team_search)
    EditText search;
    @Bind(R.id.tv_team_sure)
    TextView sure;
    @Bind(R.id.lv_my_team_list)
    MyListView myTeamListView;
    @Bind(R.id.ll_my_team)
    LinearLayout myTeamLayout;
    @Bind(R.id.lv_near_team_list)
    MyListView nearTeamListView;
    @Bind(R.id.ll_near_team)
    LinearLayout nearTeamLayout;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    private boolean isRefresh = true;//true 是下拉刷新， false 是上拉加载
    private int page = 1;//分页加载
    private MyTeamListAdapter myTeamListAdapter;
    private NearTeamListAdapter nearTeamListAdapter;
    private String requestJson;

    @Override
    protected int getContentId() {
        return R.layout.activity_team_management;
    }

    @Override
    protected void init() {
        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                page = 1;
                requestData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                page++;
                requestData();
            }
        });
        myTeamListAdapter = new MyTeamListAdapter(this);
        myTeamListView.setAdapter(myTeamListAdapter);
        nearTeamListAdapter = new NearTeamListAdapter(this);
        nearTeamListView.setAdapter(nearTeamListAdapter);
        myTeamListView.setOnItemClickListener(this);
        nearTeamListView.setOnItemClickListener(this);

        requestPermission(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_PHONE_STATE},
                true, new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        initLocation();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(TeamManagementActivity.this, "请赋予定位权限", Toast.LENGTH_SHORT).show();
                    }
                });
        search.addTextChangedListener(this);
        showLoading();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
        String provider = judgeProvider(locationManager);
        if (provider != null) {//有位置提供器的情况
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                getLocation(location);
            } else {
                Log.d(TAG, "initLocation: 无法获得当前位置");
            }
        } else {
            //不存在位置提供器的情况
        }
    }

    /**
     * 得到当前经纬度并开启线程去反向地理编码
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";
        String url = "http://maps.google.cn/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true,language=zh-CN";
        OkGo.get(url)
                .tag(this)//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, okhttp3.Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            LocationEntity locationEntity = new Gson().fromJson(jsonObject.toString(), LocationEntity.class);
                            String city = locationEntity.getResults().get(0).getAddress_components().get(2).getLong_name();
                            tvCity.setText(city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(TeamManagementActivity.this, "请手动打开安全中心定位权限", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void loadDatas() {
        String json = "\"team\":{}," + "\"type\":" + 0 + "," + "\"page\":" + page;
        requestJson = RequestUtil.getJson(this, json);
        requestData();
    }

    public void requestData() {
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/api/APITeam/QueryTeam";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        TeamManagementEntity teamManagementEntity = new Gson().fromJson(jo.toString(), TeamManagementEntity.class);
                        if (teamManagementEntity != null) {
                            List<TeamManagementEntity.TeamListBean> teamList = teamManagementEntity.getTeamList();
                            List<TeamManagementEntity.MyTeamListBean> myTeamList = teamManagementEntity.getMyTeamList();
                            if (isRefresh) {
                                myTeamListAdapter.setDatas(myTeamList);
                                nearTeamListAdapter.setDatas(teamList);
                            } else {
                                nearTeamListAdapter.addDatas(teamList);
                            }
                        }
                    } else if (statuscode.equals("2")) {//暂无数据
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(TeamManagementActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                refreshView.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                //访问失败则当前页需要重新加载
                page--;
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * @param view
     */
    @OnClick({R.id.back, R.id.tv_create, R.id.tv_team_city, R.id.tv_team_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_create:
                //创建球队
                startCreateTeam();
                break;
            case R.id.tv_team_city:
                //选择城市
                startSelectCity();
                break;
            case R.id.tv_team_sure:
                //搜索
                String text = search.getText().toString().trim();
                if (!text.isEmpty()) {
                    requestDataByTitle(text);
                }
                break;
        }
    }

    /**
     * 通过搜索查询数据
     */
    private void requestDataByTitle(String text) {
        isRefresh = true;
        page = 1;
        String json = "\"team\":{" + "\"title\":" + "\"" + text + "\"}," + "\"type\":" + 0 + "," + "\"page\":" + page;
        requestJson = RequestUtil.getJson(this, json);
        requestData();
    }

    /**
     * 通过区域id查询数据
     *
     * @param cid
     */
    private void requestDataByRegion(int cid) {
        isRefresh = true;
        page = 1;
        String json = "\"team\":{" + "\"regionid\":" + cid + "}," + "\"type\":" + 0 + "," + "\"page\":" + page;
        requestJson = RequestUtil.getJson(this, json);
        requestData();
    }

    /**
     * 打开选择城市的页面
     */

    private void startSelectCity() {
        Intent intent = new Intent(this, SelectTeamCityActivity.class);
        startActivityForResult(intent, REQUEST_CITY_CODE);
    }

    /**
     * 打开申请创建球队页面
     */
    private void startCreateTeam() {
        Intent intent = new Intent(this, CreateTeamActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        int teamId = 0;
        if (adapter instanceof MyTeamListAdapter) {
            TeamManagementEntity.MyTeamListBean myTeamListBean = ((MyTeamListAdapter) adapter).getDatas(position);
            teamId = myTeamListBean.getId();
        } else if (adapter instanceof NearTeamListAdapter) {
            TeamManagementEntity.TeamListBean teamListBean = ((NearTeamListAdapter) adapter).getDatas(position);
            teamId = teamListBean.getId();
        }
        Intent intent = new Intent(this, TeamDetailActivity.class);
        intent.putExtra("id", teamId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CITY_CODE) {
                if (data != null) {
                    String name = data.getStringExtra("name");
                    int cid = data.getIntExtra("cid", -1);
                    tvCity.setText(name);
                    requestDataByRegion(cid);
                }
            }
        }
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
        requestDataByTitle(text);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("TeamManagementActivity")) {
            if (map.get("TeamManagementActivity").equals("refresh")) {
                loadDatas();
            }
        }
    }
}
