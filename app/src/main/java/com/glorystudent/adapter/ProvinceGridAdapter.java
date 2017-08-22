package com.glorystudent.adapter;

import android.content.Context;
import android.widget.TextView;

import com.glorystudent.entity.TeamCityEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by Gavin.J on 2017/5/22.
 */

public class ProvinceGridAdapter extends AbsBaseAdapter<TeamCityEntity.CityListBean> {
    private int currentPos= -1;
    private Context context;

    public ProvinceGridAdapter(Context context) {
        super(context, R.layout.item_province_grid);
        this.context = context;
    }

    @Override
    public void bindView(ViewHolder viewHolder, TeamCityEntity.CityListBean data) {
        viewHolder.setTextView(R.id.tv_city_name, data.getName());
        TextView tv = (TextView) viewHolder.getView(R.id.tv_city_name);
        if (currentPos == getCurrentPosition()) {//选中状态
            tv.setBackground(context.getResources().getDrawable(R.drawable.shape_tv_city_selected));
            tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {//未选中状态
            tv.setBackground(context.getResources().getDrawable(R.drawable.shape_tv_city_unselected));
            tv.setTextColor(context.getResources().getColor(R.color.colorGray15));
        }
    }

    public void changeState(int position) {
        currentPos = position;
        notifyDataSetChanged();
    }
}
