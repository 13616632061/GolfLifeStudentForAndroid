package com.glorystudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glorystudent.entity.EventInformationEntity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.RequestUtil;

import java.util.List;
import java.util.Map;


/**
 * 赛事日历
 * Created by hyj on 2017/1/10.
 */
public class EventCalendarGridAdapter extends AbsCalendarBaseAdapter<Integer> {
    private static final String TAG = "EventCalendarAdapter";
    private String currentTime = RequestUtil.getCurrentTime();
    private int year;
    private int month;
    private boolean isFirst = true;//是否是第一次加载
    private TextView tv;
    private boolean isCurrentClick;//是否点击的是今天
    private TextView saveTv;
    private OnCalendarClickListener onCalendarClickListener;
    private LocalBroadcastManager localBroadcastManager;

    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        this.onCalendarClickListener = onCalendarClickListener;
    }

    public EventCalendarGridAdapter(Context context) {
        super(context, R.layout.item_calendar_grid);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @Override
    public void bindView(ViewHolder viewHolder, Integer data, Map<String, List<EventInformationEntity.ListEventBean>> mapDatas) {
        RelativeLayout rv = (RelativeLayout) viewHolder.getView(R.id.rv_calendar);
        TextView tv_point = (TextView) viewHolder.getView(R.id.tv_point);
        final TextView tv_calendar = (TextView) viewHolder.getView(R.id.tv_calendar);
        tv_calendar.setTag(data);
        if (data != 0) {
            viewHolder.setTextView(R.id.tv_calendar, data + "");
            tv_calendar.setTextColor(context.getResources().getColor(R.color.primaryColor));
            tv_calendar.setBackgroundColor(Color.TRANSPARENT);
            String monthStr = month < 9 ? "0" + month : month + "";
            String dayStr = data < 9 ? "0" + data : data + "";
            String time = year + "-" + monthStr + "-" + dayStr;

            if (isFirst && time.equals(currentTime)) {
                Log.i(TAG, "bindView: 执行设置了没？");
                isCurrentClick = true;
                saveTv = tv_calendar;
                tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
            }

            if (mapDatas.containsKey(String.valueOf(data))) {
                tv_point.setVisibility(View.VISIBLE);
            } else {
                tv_point.setVisibility(View.GONE);
            }
        } else {
            tv_point.setVisibility(View.GONE);
        }

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] split = currentTime.split("-");
                Integer currentDay = Integer.valueOf(split[2]);
                int tag = (int) tv_calendar.getTag();
                Log.i(TAG, "onClick: currentDay:" + currentDay);
                Log.i(TAG, "onClick: tag:" + tag);
                if (tag != 0) {
                    if (tv != null) {
                        tv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        tv.setBackgroundColor(Color.TRANSPARENT);
                    }

                    if (isCurrentClick && saveTv != null) {
                        Log.i(TAG, "onClick: 设置空圈");
                        saveTv.setTextColor(context.getResources().getColor(R.color.primaryColor));
                        saveTv.setBackgroundResource(R.drawable.shape_circle);
                        isCurrentClick = false;
                    }

                    tv_calendar.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    tv_calendar.setBackgroundResource(R.drawable.shape_circle_message);
                    if (currentDay == tag) {
                        isCurrentClick = true;
                    }
                    tv = tv_calendar;
                }
                if (onCalendarClickListener != null) {
                    String t = year + "-" + month + "-" + tag;
                    onCalendarClickListener.onCalendarClick(t);
                }

            }
        });
    }

    public void setCurrentTime(int year, int month) {
        this.year = year;
//        this.month = month < 9 ? "0" + month : month + "";//转成月份为01、02、03...格式
        this.month = month;
    }

    public interface OnCalendarClickListener {
        void onCalendarClick(String time);
    }
}
