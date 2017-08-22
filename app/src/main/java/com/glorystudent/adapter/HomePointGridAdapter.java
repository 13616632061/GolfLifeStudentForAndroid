package com.glorystudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.glorystudent.entity.CalendarStateEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 教练首页日历适配器
 * Created by hyj on 2017/1/12.
 */
public class HomePointGridAdapter extends AbsBaseAdapter<CalendarStateEntity> {

    public HomePointGridAdapter(Context context) {
        super(context, R.layout.item_home_point_grid);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final CalendarStateEntity data) {
        TextView tv_point = (TextView) viewHolder.getView(R.id.tv_point);
        switch (data.getState()) {
            case 0:
                tv_point.setBackgroundColor(Color.TRANSPARENT);
                break;
            case 1:
                tv_point.setBackgroundResource(R.drawable.shape_min_blackcircle);
                break;
            case 2:
                tv_point.setBackgroundResource(R.drawable.shape_min_orangecircle);
                break;
        }

    }

}
