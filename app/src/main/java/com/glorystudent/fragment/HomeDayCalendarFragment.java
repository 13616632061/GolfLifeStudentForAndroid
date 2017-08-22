package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.GridView;

import com.glorystudent.adapter.HomeDayCalendarGridAdapter;
import com.glorystudent.adapter.HomePointGridAdapter;
import com.glorystudent.entity.AppointmentListEntity;
import com.glorystudent.entity.CalendarStateEntity;
import com.glorystudent.entity.CoachCalendarRequestEntity;
import com.glorystudent.entity.EventInformationEntity;
import com.glorystudent.entity.EventRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 教练端首页日历
 * Created by hyj on 2017/1/11.
 */
public class HomeDayCalendarFragment extends BaseFragment implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "DayCalendarFragment";
    private ArrayList<String> list;
    private LocalBroadcastManager localBroadcastManager;
    private List<CalendarStateEntity> datas;
    private List<EventInformationEntity.ListEventBean> list_event;
    private HomePointGridAdapter homePointGridAdapter;

    public static HomeDayCalendarFragment getInstance(List<String> datas){
        HomeDayCalendarFragment dayCalendarFragment = new HomeDayCalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", (ArrayList<String>) datas);
        dayCalendarFragment.setArguments(bundle);
        return dayCalendarFragment;
    }

    @Bind(R.id.gv_day)
    public GridView gv_day;

    @Bind(R.id.gv_point)
    public GridView gv_point;

    private HomeDayCalendarGridAdapter homeDayCalendarGridAdapter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.Calendar":
                    initView();
                    break;
            }
        }
    };
    @Override
    protected int getContentId() {
        return R.layout.fragment_home_calendar;
    }

    @Override
    protected void init(View view) {
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.Calendar");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void getBundle(Bundle bundle) {
        list = bundle.getStringArrayList("list");
        if (list != null) {
            initView();
            initPoint();
        }
    }
    private void initView(){
        homeDayCalendarGridAdapter = new HomeDayCalendarGridAdapter(getActivity());
        gv_day.setAdapter(homeDayCalendarGridAdapter);
        homeDayCalendarGridAdapter.setDatas(list);
    }

    private void initPoint() {
        datas = new ArrayList<>();
        homePointGridAdapter = new HomePointGridAdapter(getActivity());
        gv_point.setAdapter(homePointGridAdapter);
        for (int i = 0; i < list.size(); i++) {
            CalendarStateEntity stateEntity = new CalendarStateEntity();
            stateEntity.setDate(list.get(i));
            stateEntity.setState(0);
            datas.add(stateEntity);
        }
        loadEvents();
    }

    private void loadEvents(){
        EventRequestEntity eventRequestEntity = new EventRequestEntity();
        EventRequestEntity.EventBean eventBean = new EventRequestEntity.EventBean();
        eventBean.setEvents_begintime(list.get(0));
        eventBean.setEvents_endtime(list.get(list.size() - 1));
        eventBean.setEvents_type("1");
        eventRequestEntity.setEvent(eventBean);
        String request = new Gson().toJson(eventRequestEntity);
        String reqeustJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APIEvent/QueryEvent";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        EventInformationEntity eventInformationEntity = new Gson().fromJson(jo.toString(), EventInformationEntity.class);
                        list_event = eventInformationEntity.getList_Event();
                        load();
                    } else if (statuscode.equals("2")) {
                        list_event = new ArrayList<>();
                        load();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(getActivity(), url, reqeustJson);
    }



    private void load() {
        String start = list.get(0);
        String end = list.get(list.size() - 1);
        CoachCalendarRequestEntity coachCalendarRequestEntity = new CoachCalendarRequestEntity();
        coachCalendarRequestEntity.setBeginDate(start);
        coachCalendarRequestEntity.setEndDate(end);
        String request = new Gson().toJson(coachCalendarRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APICalendar/QueryCalendar";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(getActivity(), url, requestJson);
    }


    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                JSONArray ja = jo.getJSONArray("listAppointment");
                TypeToken<List<AppointmentListEntity.ListAppointmentBean>> tt = new TypeToken<List<AppointmentListEntity.ListAppointmentBean>>(){};
                List<AppointmentListEntity.ListAppointmentBean> listDatas = new Gson().fromJson(ja.toString(), tt.getType());
                if (listDatas != null) {
                    for (int i = 0; i < listDatas.size(); i++) {
                        String appointmentdate = listDatas.get(i).getAppointmentdate();
                        String[] ts = appointmentdate.split("T");
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).equals(ts[0])) {
                                datas.get(j).setState(1);
                                break;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < list_event.size(); i++) {
                String events_begintime = list_event.get(i).getEvents_begintime();
                String[] startTime = events_begintime.split("T");
                String events_endtime = list_event.get(i).getEvents_endtime();
                String[] endTime = events_endtime.split("T");
                for (int j = 0; j < list.size(); j++) {
                    if(TimeUtil.isInDate(list.get(j), startTime[0], endTime[0])){
                        datas.get(j).setState(2);
                    }
                }
            }
            homePointGridAdapter.setDatas(datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<Integer, Integer> map) {
        if (map.containsKey(5)) {
            if (map.get(5) == 1) {
                initPoint();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        EventBus.getDefault().unregister(this);
    }
}
