package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;

/**
 * 教练首页日历适配器
 * Created by hyj on 2017/1/12.
 */
public class HomeDayCalendarGridAdapter extends AbsBaseAdapter<String> {
    private String currentTime = RequestUtil.getCurrentTime();
    private String day = SharedUtil.getString(Constants.HOME_CALENDAR_TODAY);
    private String clickday = SharedUtil.getString(Constants.HOME_CALENDAR_CLICK);
    private TextView tv;
    private TextView saveClickTv;
    private LocalBroadcastManager localBroadcastManager;
    public HomeDayCalendarGridAdapter(Context context) {
        super(context, R.layout.item_home_calendar_grid);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final String data) {
        String[] split = data.split("-");
//        TextView tv_point = (TextView) viewHolder.getView(R.id.tv_point);
//        switch (data.getState()) {
//            case 0:
//                tv_point.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case 1:
//                tv_point.setBackgroundResource(R.drawable.shape_min_blackcircle);
//                break;
//            case 2:
//                tv_point.setBackgroundResource(R.drawable.shape_min_orangecircle);
//                break;
//        }

        RelativeLayout rv_calendar = (RelativeLayout) viewHolder.getView(R.id.rv_calendar);
        final TextView tv_calendar = (TextView) viewHolder.getView(R.id.tv_calendar);
        tv_calendar.setTag(data);
        viewHolder.setTextView(R.id.tv_calendar, Integer.valueOf(split[2]) + "");

        if (currentTime.equals(data) &&  clickday.isEmpty()) {
            tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
            tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
            tv = tv_calendar;
        } else if (currentTime.equals(data)) {
            tv_calendar.setTextColor(context.getResources().getColor(R.color.primaryColor));
            tv_calendar.setBackgroundResource(R.drawable.shape_circle);
            tv = tv_calendar;
        }

        if (clickday != null && !clickday.isEmpty() && clickday.equals(data)) {
            if (tv != null) {
                tv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                tv.setBackgroundResource(R.drawable.shape_circle);
            }
            if(data.equals(clickday)){
                tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
                saveClickTv  = tv_calendar;
            }
        }


        rv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) tv_calendar.getTag();
                Intent intent = new Intent();
                intent.setAction("com.glory.broadcast.HomeChooseDate");
                intent.putExtra("date", tag);
                localBroadcastManager.sendBroadcast(intent);
                String saveTag;
                if (saveClickTv != null) {
                    saveTag = (String) saveClickTv.getTag();
                }else{
                    saveTag = null;
                }

                if (day.equals(data)) { //是否点击的是今天
                    tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
                    if (saveClickTv != null && saveTag != null && !day.equals(saveTag)) {
                        saveClickTv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        saveClickTv.setBackgroundColor(Color.TRANSPARENT);
                    }
                    SharedUtil.putString(Constants.HOME_CALENDAR_CLICK, tag);
                    tv = tv_calendar;
                } else {
                    if(tv != null){
                        tv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        tv.setBackgroundResource(R.drawable.shape_circle);
                    }
                    if (saveClickTv != null && saveTag != null && !day.equals(saveTag)) {
                        saveClickTv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        saveClickTv.setBackgroundColor(Color.TRANSPARENT);
                    }
                    tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
                    SharedUtil.putString(Constants.HOME_CALENDAR_CLICK, tag);
                    saveClickTv = tv_calendar;
                    saveClickTv.setTag(tag);
                }
            }
        });
    }
}
