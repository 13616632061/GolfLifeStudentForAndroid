package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.glorystudent.adapter.CalendarWeekGridAdapter;
import com.glorystudent.adapter.EventCalendarGridAdapter;
import com.glorystudent.entity.EventInformationEntity;
import com.glorystudent.entity.EventRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.widget.oywidget.MyGridView;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 赛事日历
 * Created by hyj on 2017/1/10.
 */
public class CalendarFragment extends BaseFragment implements OkGoRequest.OnOkGoUtilListener, EventCalendarGridAdapter.OnCalendarClickListener {
    private final static String TAG = "CalendarFragment";
    private int year;
    private int month;
    private int monthDays;
    private Map<String, List<EventInformationEntity.ListEventBean>> map;
    private List<Integer> datas;
    private OnCaleadarDayClick onCalendarDayClick;

    public interface OnCaleadarDayClick {
        void onCaleadarDayClick(String time, Map<String, List<EventInformationEntity.ListEventBean>> map);
    }

    public static CalendarFragment getInstance(int year, int month, OnCaleadarDayClick onCalendarDayClick) {
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.onCalendarDayClick = onCalendarDayClick;
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        calendarFragment.setArguments(bundle);
        return calendarFragment;
    }

    @Bind(R.id.gv_week)
    public MyGridView gv_week;
    private String[] week = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private CalendarWeekGridAdapter calendarWeekGridAdapter;

    @Bind(R.id.gv_calendar)
    public MyGridView gv_calendar;
    private EventCalendarGridAdapter eventCalendarGridAdapter;

//    @Bind(R.id.event_information_lv)
//    public MyListView event_information_lv;

    //    private EventCalendarListAdapter eventCalendarListAdapter;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.IndustryCalendar":
                    initViews();
                    break;
            }
        }
    };

    @Override
    protected int getContentId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        year = bundle.getInt("year", -1);
        month = bundle.getInt("month", -1);
        if (year != -1 && month != -1) {
            init();
            initViews();
            reuestDatas();
        }
    }

    @Override
    protected void init(View view) {
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.IndustryCalendar");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void init() {
        calendarWeekGridAdapter = new CalendarWeekGridAdapter(getActivity());
        gv_week.setAdapter(calendarWeekGridAdapter);
        List<String> weekdatas = Arrays.asList(week);
        calendarWeekGridAdapter.setDatas(weekdatas);
    }

    private void initViews() {
        map = new HashMap<>();
        datas = new ArrayList<>();
        eventCalendarGridAdapter = new EventCalendarGridAdapter(getActivity());
        eventCalendarGridAdapter.setCurrentTime(year, month);
        gv_calendar.setAdapter(eventCalendarGridAdapter);
        int week = TimeUtil.getWeek(year + "-" + month + "-" + "1");
        monthDays = TimeUtil.getMonthDays(year, month);
        if (week != 7) {
            for (int i = 0; i < week; i++) {
                datas.add(0);
            }
        }
        for (int i = 1; i <= monthDays; i++) {
            datas.add(i);
        }
        eventCalendarGridAdapter.setDatas(datas);
        eventCalendarGridAdapter.setOnCalendarClickListener(this);
    }

    private void reuestDatas() {
        showLoading();
        EventRequestEntity eventRequestEntity = new EventRequestEntity();
        EventRequestEntity.EventBean eventBean = new EventRequestEntity.EventBean();
        eventBean.setEvents_begintime(year + "-" + month + "-" + "1");
        eventBean.setEvents_endtime(year + "-" + month + "-" + monthDays);
        eventBean.setEvents_type("1");
        eventRequestEntity.setEvent(eventBean);
        eventRequestEntity.setPage(0);
        String json = new Gson().toJson(eventRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), json);
        String url = "/api/APIEvent/QueryEvent";
        Log.i(TAG, "loadDatas: " + requestJson);
        Log.i(TAG, "loadDatas: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, requestJson);
    }

    @Override
    public void parseDatas(String json) {
        dismissLoading();
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                EventInformationEntity eventInformationEntity = new Gson().fromJson(jo.toString(), EventInformationEntity.class);
                List<EventInformationEntity.ListEventBean> list_event = eventInformationEntity.getList_Event();
                //得到本月某天的赛事Map
                for (EventInformationEntity.ListEventBean listEventBean : list_event) {
                    String events_begintime = listEventBean.getEvents_begintime();
                    String events_endtime = listEventBean.getEvents_endtime();
                    String[] ts_begin = events_begintime.split("T");
                    String[] ts_end = events_endtime.split("T");
                    String[] split_begin = ts_begin[0].split("-");
                    String[] split1_end = ts_end[0].split("-");
                    Integer year_begin = Integer.valueOf(split_begin[0]);
                    Integer month_begin = Integer.valueOf(split_begin[1]);
                    Integer day_begin = Integer.valueOf(split_begin[2]);
                    Integer year_end = Integer.valueOf(split1_end[0]);
                    Integer month_end = Integer.valueOf(split1_end[1]);
                    Integer day_end = Integer.valueOf(split1_end[2]);
                    String time;
                    for (int i = year_begin; i <= year_end; i++) {
                        for (int j = month_begin; j <= month_end; j++) {
                            for (int k = day_begin; k <= day_end; k++) {
//                                String m = j + "";
//                                String d = k + "";
//                                if (j < 10) {
//                                    m = "0" + j;
//                                }
//                                if (k < 10) {
//                                    d = "0" + k;
//                                }
                                time = i + "-" + j + "-" + k;
                                if (map.containsKey(time)) {
                                    List<EventInformationEntity.ListEventBean> listEventMap = map.get(time);
                                    listEventMap.add(listEventBean);
                                    map.put(time, listEventMap);
                                } else {
                                    List<EventInformationEntity.ListEventBean> datas = new ArrayList<>();
                                    datas.add(listEventBean);
                                    map.put(time, datas);
                                }
                            }
                        }
                    }
                }
                //得到适配器所需要的Map
                Map<String, List<EventInformationEntity.ListEventBean>> mapDatas = new HashMap<>();
                Iterator<Map.Entry<String, List<EventInformationEntity.ListEventBean>>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, List<EventInformationEntity.ListEventBean>> next = iterator.next();
                    String key = next.getKey();
                    String[] split = key.split("-");
                    mapDatas.put(split[2], next.getValue());
                }
                Log.i(TAG, "parseDatas: " + mapDatas.size());
                eventCalendarGridAdapter.setMapDatas(mapDatas);
                eventCalendarGridAdapter.setDatas(datas);
            } else {
                eventCalendarGridAdapter.setDatas(datas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        loadEvents();
    }

//    private void loadEvents() {
//        Iterator<Map.Entry<String, List<EventInformationEntity.ListEventBean>>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, List<EventInformationEntity.ListEventBean>> next = iterator.next();
//        }
//        eventCalendarListAdapter = new EventCalendarListAdapter(getActivity());
//        event_information_lv.setAdapter(eventCalendarListAdapter);
//        if (map != null) {
//            if (map.containsKey(RequestUtil.getCurrentTime())) {
//                eventCalendarListAdapter.setDatas(map.get(RequestUtil.getCurrentTime()));
//            }
//        }
//    }

    @Override
    public void requestFailed() {
        dismissLoading();
        eventCalendarGridAdapter.setDatas(datas);
    }


    @Override
    public void onCalendarClick(String time) {
        if (map != null) {
//            if (map.containsKey(time)) {
//                eventCalendarListAdapter.setDatas(map.get(time));
//                event_information_lv.setVisibility(View.VISIBLE);
//            } else {
//                event_information_lv.setVisibility(View.GONE);
//            }
            if (onCalendarDayClick != null) {
                Log.i(TAG, "onCalendarClick: 执行了");
                onCalendarDayClick.onCaleadarDayClick(time, map);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
