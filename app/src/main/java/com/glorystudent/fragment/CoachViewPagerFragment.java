package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.AppointmentListEntity;
import com.glorystudent.entity.CoachCalendarRequestEntity;
import com.glorystudent.entity.CoachHomeEntity;
import com.glorystudent.entity.EventInformationEntity;
import com.glorystudent.entity.EventRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.AppointmentCourseActivity;
import com.glorystudent.golflife.MyAttestationActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教练首页的计划
 * Created by hyj on 2017/2/22.
 */
public class CoachViewPagerFragment extends BaseFragment implements OkGoRequest.OnOkGoUtilListener, View.OnClickListener {
    private final static String TAG = "CoachViewPagerFragment";
    private String date;
    private List<CoachHomeEntity> datas;
    private int count = 0;
    private String groupid;

    public static CoachViewPagerFragment getInstance(String date) {
        CoachViewPagerFragment coachViewPagerFragment = new CoachViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        coachViewPagerFragment.setArguments(bundle);
        return coachViewPagerFragment;
    }

    private List<EventInformationEntity.ListEventBean> list_event;
    @Bind(R.id.ll_add)
    public LinearLayout ll_add;

    @Bind(R.id.tv_appointment)
    public TextView tv_appointment;

    @Bind(R.id.iv_expand)
    public ImageView iv_expand;

    private boolean isExpand = true;

    @Override
    protected int getContentId() {
        return R.layout.fragment_coach_viewpager;
    }

    @Override
    protected void init(View view) {
        groupid = SharedUtil.getString(Constants.GROUP_ID);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            date = bundle.getString("date");
            if (!TimeUtil.dateCompareToday(date)) {
                tv_appointment.setClickable(false);
                tv_appointment.setVisibility(View.GONE);
            }else{
                tv_appointment.setTag(date);
                tv_appointment.setOnClickListener(this);
            }
            loadEvents();
        }
    }

    private void loadEvents(){
        count = 0;
        datas = new ArrayList<>();
        EventRequestEntity eventRequestEntity = new EventRequestEntity();
        EventRequestEntity.EventBean eventBean = new EventRequestEntity.EventBean();
        eventBean.setEvents_begintime(TimeUtil.subDate(7, date));
        eventBean.setEvents_endtime(TimeUtil.subDate(-7, date));
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
                        if (list_event != null) {
                            for (int i = 0; i < list_event.size(); i++) {
                                String events_begintime = list_event.get(i).getEvents_begintime();
                                String events_endtime = list_event.get(i).getEvents_endtime();
                                String begin = TimeUtil.getEventTime(events_begintime);
                                String end = TimeUtil.getEventTime(events_endtime);
                                if (TimeUtil.isInDate(date, begin, end) && count < 3) {
                                    CoachHomeEntity coachHomeEntity = new CoachHomeEntity();
                                    coachHomeEntity.setState(1);
                                    coachHomeEntity.setListEventBean(list_event.get(i));
                                    datas.add(coachHomeEntity);
                                    count++;
                                }
                            }
                        }
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
        CoachCalendarRequestEntity coachCalendarRequestEntity = new CoachCalendarRequestEntity();
        coachCalendarRequestEntity.setBeginDate(date);
        coachCalendarRequestEntity.setEndDate(date);
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
                        if (count < 3) {
                            CoachHomeEntity coachHomeEntity = new CoachHomeEntity();
                            coachHomeEntity.setState(2);
                            coachHomeEntity.setAppointmentBean(listDatas.get(i));
                            datas.add(coachHomeEntity);
                            count++;
                        }else {
                            break;
                        }
                    }
                }
                initView();
            } else if (statuscode.equals("2")) {
                initView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        if (datas != null && datas.size() > 0) {
            ll_add.removeAllViews();
            for (int i = 0; i < datas.size(); i++) {
                int state = datas.get(i).getState();
                switch (state) {
                    case 1:
                        View plan1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_plan1, null);
                        TextView tv_title = (TextView) plan1.findViewById(R.id.tv_title);
                        tv_title.setText(datas.get(i).getListEventBean().getEvents_name());
                        ll_add.addView(plan1);
                        break;
                    case 2:
                        AppointmentListEntity.ListAppointmentBean appointmentBean = datas.get(i).getAppointmentBean();
                        View plan2 = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_plan2, null);
                        TextView tv_time = (TextView) plan2.findViewById(R.id.tv_time);
                        TextView tv_name = (TextView) plan2.findViewById(R.id.tv_name);
                        TextView tv_play = (TextView) plan2.findViewById(R.id.tv_play);
                        TextView tv_course = (TextView) plan2.findViewById(R.id.tv_course);
                        tv_time.setText(appointmentBean.getTimeDefinition());
                        tv_name.setText(appointmentBean.getRemarks());
                        tv_play.setText(appointmentBean.getPositionsname());
                        tv_course.setText(appointmentBean.getCcoursedetailname());
                        ll_add.addView(plan2);
                        break;
                }
            }
        }


    }

    @Override
    public void requestFailed() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<Integer, Integer> map){
        if (map.containsKey(4)) {
            if (map.get(4) == 1) {
                loadEvents();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick({R.id.iv_expand})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_expand:
                //展开/关闭
                if (isExpand = !isExpand) {
                    iv_expand.setImageResource(R.drawable.btn_video_expand);
                    ll_add.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(EventBusMapUtil.getStringMap("CoachHome", "expand"));
                }else {
                    iv_expand.setImageResource(R.drawable.btn_video_packup);
                    ll_add.setVisibility(View.GONE);
                    EventBus.getDefault().post(EventBusMapUtil.getStringMap("CoachHome", "packup"));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (groupid != null) {
            String tag = (String) v.getTag();
            Intent intent = new Intent(getActivity(), AppointmentCourseActivity.class);
            intent.putExtra("date", tag);
            startActivity(intent);
        }else {
            Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                @Override
                public void onSure() {
                    startActivity(new Intent(getActivity(), MyAttestationActivity.class));
                }

                @Override
                public void onCancel() {

                }
            }).showDialog(getActivity(), "此功能只有教练才能使用", "是否申请教练", "前往");
        }
    }
}
