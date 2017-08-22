package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.glorystudent.adapter.CityListAdapter;
import com.glorystudent.adapter.HotCityGridAdapter;
import com.glorystudent.adapter.ProvinceGridAdapter;
import com.glorystudent.entity.TeamCityEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.widget.oywidget.MyGridView;
import com.glorystudent.golflibrary.widget.oywidget.MyListView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 球队管理选择城市页面
 * Created by Gavin.J on 2017/5/16.
 */

public class SelectTeamCityActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "SelectTeamCityActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.hot_city_grid_view)
    MyGridView hotCityGridView;
    @Bind(R.id.province_grid_view)
    MyGridView provinceGridView;
    @Bind(R.id.city_list_view)
    MyListView cityListView;
    private String json;
    private List<TeamCityEntity.HotCityListBean> hotCityList;
    private List<TeamCityEntity.CityListBean> datas;
    private List<TeamCityEntity.CityListBean> provinceList;
    private List<TeamCityEntity.CityListBean> cityList;
    private CityListAdapter cityListAdapter;
    private ProvinceGridAdapter provinceGridAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_select_team_city;
    }

    @Override
    protected void init() {
        //读取本地asset文件下的省市区json
        try {
            InputStream is = getAssets().open("TeamCity.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TeamCityEntity teamCityEntity = new Gson().fromJson(json, TeamCityEntity.class);
        if (teamCityEntity != null) {
            hotCityList = teamCityEntity.getHotCityList();
            datas = teamCityEntity.getCityList();
        }
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        for (TeamCityEntity.CityListBean cityListBean : datas) {
            if (cityListBean.getPid() == 0) {
                //扣掉四个直辖市，只显示省份
                if (cityListBean.getCid() != 110001 && cityListBean.getCid() != 500001
                        && cityListBean.getCid() != 120001 && cityListBean.getCid() != 310001) {
                    provinceList.add(cityListBean);
                }
            }
        }
        HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(this);
        hotCityGridView.setAdapter(hotCityGridAdapter);
        hotCityGridAdapter.setDatas(hotCityList);
        hotCityGridView.setOnItemClickListener(this);

        provinceGridAdapter = new ProvinceGridAdapter(this);
        provinceGridView.setAdapter(provinceGridAdapter);
        provinceGridAdapter.setDatas(provinceList);
        provinceGridView.setOnItemClickListener(this);

        cityListAdapter = new CityListAdapter(this);
        cityListView.setAdapter(cityListAdapter);
        cityListAdapter.setDatas(cityList);
        cityListView.setOnItemClickListener(this);
    }

    @Override
    protected void loadDatas() {
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        if (adapter instanceof HotCityGridAdapter) {//
            // 是热门城市
            TeamCityEntity.HotCityListBean hotCity = ((HotCityGridAdapter) adapter).getDatas(position);
            Intent intent = new Intent();
            intent.putExtra("name", hotCity.getName());
            intent.putExtra("cid", hotCity.getCid());
            this.setResult(RESULT_OK, intent);
            finish();
        } else if (adapter instanceof ProvinceGridAdapter) {
            //是省份
            provinceGridAdapter.changeState(position);
            TeamCityEntity.CityListBean province = ((ProvinceGridAdapter) adapter).getDatas(position);
            cityList.clear();
            for (TeamCityEntity.CityListBean data : datas) {
                if (data.getPid() == province.getCid()) {
                    cityList.add(data);
                }
            }
            cityListAdapter.notifyDataSetChanged();
        } else if (adapter instanceof CityListAdapter) {
            //是市区
            TeamCityEntity.CityListBean city = ((CityListAdapter) adapter).getDatas(position);
            Intent intent = new Intent();
            intent.putExtra("name", city.getName());
            intent.putExtra("cid", city.getCid());
            this.setResult(RESULT_OK, intent);
            finish();
        }
    }
}
