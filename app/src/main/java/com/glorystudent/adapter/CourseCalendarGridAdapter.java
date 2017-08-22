package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;

/**
 * 预约日历适配器
 * Created by hyj on 2017/1/12.
 */
public class CourseCalendarGridAdapter extends AbsBaseAdapter<String> {
    private final static String TAG = "DayCalendarGridAdapter";
    private String currentTime = RequestUtil.getCurrentTime();
    private String day = SharedUtil.getString(Constants.DAY_CALENDAR_TODAY);
    private String clickday = SharedUtil.getString(Constants.DAY_CALENDAR_CLICK);
    private TextView tv, point;
    private TextView saveClickTv, savePoint;
    private LocalBroadcastManager localBroadcastManager;
    public CourseCalendarGridAdapter(Context context) {
        super(context, R.layout.item_course_calendar_grid);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }



    @Override
    public void bindView(ViewHolder viewHolder, final String data) {
        String[] split = data.split("-");
        final TextView tv_point = (TextView) viewHolder.getView(R.id.tv_point);
        RelativeLayout rv_calendar = (RelativeLayout) viewHolder.getView(R.id.rv_calendar);
        final TextView tv_calendar = (TextView) viewHolder.getView(R.id.tv_calendar);
        tv_calendar.setTag(data);
        viewHolder.setTextView(R.id.tv_calendar, Integer.valueOf(split[2]) + "");

        if (currentTime.equals(data) &&  clickday.isEmpty()) {
            tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
            tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
            tv_point.setVisibility(View.VISIBLE);
            tv = tv_calendar;
            point = tv_point;
        } else if (currentTime.equals(data)) {
            tv_calendar.setTextColor(context.getResources().getColor(R.color.primaryColor));
            tv_calendar.setBackgroundResource(R.drawable.shape_circle);
            tv_point.setVisibility(View.GONE);
            tv = tv_calendar;
            point = tv_point;
        }

        if (clickday != null && !clickday.isEmpty() && clickday.equals(data)) {
            if (tv != null) {
                tv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                tv.setBackgroundResource(R.drawable.shape_circle);
                point.setVisibility(View.GONE);
            }
            if(data.equals(clickday)){
                tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
                tv_point.setVisibility(View.VISIBLE);
                saveClickTv  = tv_calendar;
                savePoint = tv_point;
            }
        }


        rv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) tv_calendar.getTag();
                Intent intent = new Intent();
                intent.putExtra("date", tag);
                intent.setAction("com.glory.broadcast.ChooseCourseDate");
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
                    tv_point.setVisibility(View.VISIBLE);
                    if (saveClickTv != null && saveTag != null && !day.equals(saveTag)) {
                        saveClickTv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        saveClickTv.setBackgroundColor(Color.TRANSPARENT);
                        savePoint.setVisibility(View.GONE);
                    }
                    SharedUtil.putString(Constants.DAY_CALENDAR_CLICK, tag);
                    tv = tv_calendar;
                    point = tv_point;
                } else {
                    Log.d(TAG, "onClick: --->执行了");
                    if(tv != null){
                        Log.d(TAG, "onClick: 执行1--->");
                        tv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        tv.setBackgroundResource(R.drawable.shape_circle);
                        point.setVisibility(View.GONE);
                    }
                    if (saveClickTv != null && saveTag != null && !day.equals(saveTag)) {
                        Log.d(TAG, "onClick: 执行2--->");
                        saveClickTv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        saveClickTv.setBackgroundColor(Color.TRANSPARENT);
                        savePoint.setVisibility(View.GONE);
                    }
                    tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
                    tv_point.setVisibility(View.VISIBLE);
                    SharedUtil.putString(Constants.DAY_CALENDAR_CLICK, tag);
                    saveClickTv = tv_calendar;
                    savePoint = tv_point;
                    saveClickTv.setTag(tag);
                }
                Intent intent1 = new Intent();
                intent1.setAction("com.glory.broadcast.AppointmentCalendar");
                localBroadcastManager.sendBroadcast(intent1);

            }
        });
    }
}
