package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.TeamCityEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by Gavin.J on 2017/5/22.
 */

public class HotCityGridAdapter extends AbsBaseAdapter<TeamCityEntity.HotCityListBean> {
    public HotCityGridAdapter(Context context) {
        super(context, R.layout.item_province_grid);
    }

    @Override
    public void bindView(ViewHolder viewHolder, TeamCityEntity.HotCityListBean data) {
        viewHolder.setTextView(R.id.tv_city_name, data.getName());
    }
}
