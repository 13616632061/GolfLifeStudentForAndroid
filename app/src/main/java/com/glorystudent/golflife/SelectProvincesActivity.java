package com.glorystudent.golflife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.CitysListAdapter;
import com.glorystudent.entity.CitysEntity;
import com.glorystudent.entity.LocationEntity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 选择省
 * Created by hyj on 2016/12/23.
 */
public class SelectProvincesActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = "SelectProvincesActivity";
    private String provider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;

    private TextView tv_positioning;
    @Bind(R.id.citys_lv)
    public PullableNoUpListView citys_lv;
    private CitysListAdapter citysListAdapter;
    private List<CitysEntity.ListchinacityBean> datas;
    private List<CitysEntity.ListchinacityBean> listchinacity;
    private LocationEntity locationEntity;
    private String city;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Override
    protected int getContentId() {
        return R.layout.activity_select_provinces;
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

        View lvHead = LayoutInflater.from(this).inflate(R.layout.item_city_head, null);
        tv_positioning = (TextView) lvHead.findViewById(R.id.tv_positioning);
        citys_lv.addHeaderView(lvHead);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
        provider = judgeProvider(locationManager);

        if (provider != null) {//有位置提供器的情况
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                getLocation(location);//得到当前经纬度并开启线程去反向地理编码
            } else {
                tv_positioning.setText("暂时无法获得当前位置");
            }
        }else{//不存在位置提供器的情况

        }
        citysListAdapter = new CitysListAdapter(this);
        citys_lv.setAdapter(citysListAdapter);
        citys_lv.setOnItemClickListener(this);
    }

    /**
     * 得到当前经纬度并开启线程去反向地理编码
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude()+"";
        String longitude = location.getLongitude()+"";
        String url = "http://maps.google.cn/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true,language=zh-CN";
        OkGo.get(url)
                .tag(this)//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, okhttp3.Call call, okhttp3.Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            locationEntity = new Gson().fromJson(jsonObject.toString(), LocationEntity.class);
                            city = locationEntity.getResults().get(0).getAddress_components().get(4).getLong_name() + " "
                                         + locationEntity.getResults().get(0).getAddress_components().get(3).getLong_name() + " "
                                         + locationEntity.getResults().get(0).getAddress_components().get(2).getLong_name();

                            tv_positioning.setText(city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 判断是否有可用的内容提供器
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
            return LocationManager.NETWORK_PROVIDER;
        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        }else{
            Toast.makeText(SelectProvincesActivity.this,"请手动打开安全中心定位权限", Toast.LENGTH_SHORT).show();
        }
        return null;
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
    protected void loadDatas() {
        showLoading();
        String citysJson = RequestUtil.getCitys(this);
        OkGo.post(Constants.BASE_URL+"/api/APIChinaCity/QueryChinaCity")
                .tag(this)//
                .params("request", citysJson)
                .execute(new StringCallback() {


                    @Override
                    public void onSuccess(String s, okhttp3.Call call, okhttp3.Response response) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if(statuscode.equals("1")){
                                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                                CitysEntity citysEntity = new Gson().fromJson(jo.toString(), CitysEntity.class);
                                listchinacity = citysEntity.getListchinacity();
                                datas = new ArrayList<CitysEntity.ListchinacityBean>();
                                for (int i = 0; i < listchinacity.size(); i++) {
                                    if(listchinacity.get(i).getPid() == 0){
                                        datas.add(listchinacity.get(i));
                                    }
                                }
                                if(listchinacity != null){
                                    citysListAdapter.setDatas(datas);
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
                    public void onError(Call call, Response response, Exception e) {
                        dismissLoading();
                        Toast.makeText(SelectProvincesActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            if (locationEntity != null) {
                String provinceName = locationEntity.getResults().get(0).getAddress_components().get(4).getLong_name();
                String cityName = locationEntity.getResults().get(0).getAddress_components().get(3).getLong_name();
                String countyName = locationEntity.getResults().get(0).getAddress_components().get(2).getLong_name();
                SharedUtil.putString(Constants.PROVINCES_NAME,provinceName);
                SharedUtil.putString(Constants.CITY_NAME, cityName);
                SharedUtil.putString(Constants.COUNTY_NAME, countyName);
                String addressid = locationEntity.getResults().get(0).getAddress_components().get(6).getLong_name();
                SharedUtil.putString(Constants.ADDRESS, city);
                String addressName = provinceName + " " + cityName + " " + countyName;
                String nicknameJson = "\"chinacity\":" + "\"" + addressid + "\"," + "\"chinacity_name\":" + "\"" + addressName +"\"";
                String editUserInfo = RequestUtil.getEditUserInfo(this, nicknameJson);
                OkGo.post(Constants.BASE_URL+"/api/APIUser/EditUser")
                        .tag(this)//
                        .params("request", editUserInfo)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jo = new JSONObject(s);
                                    String statuscode = jo.getString("statuscode");
                                    String statusmessage = jo.getString("statusmessage");
                                    if(statuscode.equals("1")){
                                        Toast.makeText(SelectProvincesActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post(city);
                                        finish();
                                    }else{
                                        Toast.makeText(SelectProvincesActivity.this, "保存失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
            finish();
        }else{
            CitysEntity.ListchinacityBean adapterDatas = citysListAdapter.getDatas(position - 1);
            Intent intent = new Intent(this, SelectCitysActivity.class);
            intent.putExtra("provincesname", adapterDatas.getName());
            intent.putExtra("provincesid", adapterDatas.getCid());
            startActivity(intent);
            EventBus.getDefault().postSticky(listchinacity);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 80)
    public void closeActivity(String str){
        if(str.equals(Constants.CLOSE)){
            finish();
        }
    }
}
