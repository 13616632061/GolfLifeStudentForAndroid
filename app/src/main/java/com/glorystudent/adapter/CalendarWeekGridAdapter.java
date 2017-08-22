package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/1/10.
 */
public class CalendarWeekGridAdapter extends AbsBaseAdapter<String> {
    public CalendarWeekGridAdapter(Context context) {
        super(context, R.layout.item_week_calendar_grid);
    }

    @Override
    public void bindView(ViewHolder viewHolder, String data) {
        viewHolder.setTextView(R.id.tv_week, data);
    }
}
