package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.GridView;

import com.glorystudent.adapter.CourseCalendarGridAdapter;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 课程日日历
 * Created by hyj on 2017/1/11.
 */
public class CourseCalendarFragment extends BaseFragment {
    private final static String TAG = "DayCalendarFragment";
    private ArrayList<String> list;
    private LocalBroadcastManager localBroadcastManager;

    public static CourseCalendarFragment getInstance(List<String> datas){
        CourseCalendarFragment courseCalendarFragment = new CourseCalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", (ArrayList<String>) datas);
        courseCalendarFragment.setArguments(bundle);
        return courseCalendarFragment;
    }

    @Bind(R.id.gv_day)
    public GridView gv_day;
    private CourseCalendarGridAdapter courseCalendarGridAdapter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.AppointmentCalendar":
                    initView();
                    break;
            }
        }
    };
    @Override
    protected int getContentId() {
        return R.layout.fragment_day_calendar;
    }

    @Override
    protected void init(View view) {
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.AppointmentCalendar");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void getBundle(Bundle bundle) {
        list = bundle.getStringArrayList("list");
        if (list != null) {
            initView();
        }
    }

    private void initView() {
        courseCalendarGridAdapter = new CourseCalendarGridAdapter(getActivity());
        gv_day.setAdapter(courseCalendarGridAdapter);
        courseCalendarGridAdapter.setDatas(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
