package com.glorystudent.golflife;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.glorystudent.adapter.CitysListAdapter;
import com.glorystudent.entity.CitysEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 选择县（区）
 * Created by hyj on 2016/12/23.
 */
public class SelectCountysActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = "SelectCountysActivity";
    @Bind(R.id.lv_citys)
    public ListView lv_citys;
    private CitysListAdapter citysListAdapter;
    private List<CitysEntity.ListchinacityBean> citysData;
    private String provincesname;
    private int cityid;
    private String cityname;
    private CitysEntity.ListchinacityBean adapterDatas;
    private String address;

    @Override
    protected int getContentId() {
        return R.layout.activity_select_countys;
    }
    @Override
    protected void init() {
        Intent intent = getIntent();
        provincesname = intent.getStringExtra("provincesname");
        cityid = intent.getIntExtra("cityid", -1);
        cityname = intent.getStringExtra("cityname");
        View head = LayoutInflater.from(this).inflate(R.layout.item_city_head2, null);
        lv_citys.addHeaderView(head);
        citysListAdapter = new CitysListAdapter(this);
        lv_citys.setAdapter(citysListAdapter);
        lv_citys.setOnItemClickListener(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getList(List<CitysEntity.ListchinacityBean> datas){
        citysData = datas;
    }

    @Override
    protected void loadDatas() {
        List<CitysEntity.ListchinacityBean> datas = new ArrayList<>();
        for (int i = 0; i < citysData.size(); i++) {
            if (citysData.get(i).getPid() == cityid) {
                datas.add(citysData.get(i));
            }
        }
        citysListAdapter.setDatas(datas);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapterDatas = citysListAdapter.getDatas(position - 1);
        address = provincesname + " " + cityname + " " + adapterDatas.getName();
        showWaiting();
        String nicknameJson = "\"chinacity\":" + "\"" + adapterDatas.getCid() + "\"," + "\"chinacity_name\":" + "\"" + address +"\"";
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
                                SharedUtil.putString(Constants.PROVINCES_NAME, provincesname);
                                SharedUtil.putString(Constants.CITY_NAME, cityname);
                                SharedUtil.putString(Constants.COUNTY_NAME, adapterDatas.getName());
                                SharedUtil.putString(Constants.ADDRESS, address);
                                Toast.makeText(SelectCountysActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(address);
                                EventBus.getDefault().post(Constants.CLOSE);
                                finish();
                            }else{
                                Toast.makeText(SelectCountysActivity.this, "保存失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissWaiting();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        dismissWaiting();
                        Toast.makeText(SelectCountysActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
