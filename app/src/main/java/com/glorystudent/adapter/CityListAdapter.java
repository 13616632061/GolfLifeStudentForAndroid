package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.TeamCityEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 球队选择城市页面的市区适配器
 * Created by Gavin.J on 2017/5/22.
 */

public class CityListAdapter extends AbsBaseAdapter<TeamCityEntity.CityListBean> {

    public CityListAdapter(Context context) {
        super(context, R.layout.item_city_name_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, TeamCityEntity.CityListBean data) {
        viewHolder.setTextView(R.id.tv_city_name, data.getName());
    }
}
