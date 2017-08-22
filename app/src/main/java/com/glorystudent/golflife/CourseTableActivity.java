package com.glorystudent.golflife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.CalendarWeekGridAdapter;
import com.glorystudent.adapter.CourseTableGridAdapter;
import com.glorystudent.adapter.DayViewPagerAdapter;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.AppointmentListEntity;
import com.glorystudent.entity.CoachCalendarRequestEntity;
import com.glorystudent.entity.SubscribeRequestEntity;
import com.glorystudent.entity.TimeDefineEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 课表
 * Created by hyj on 2017/1/11.
 */
public class CourseTableActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener, ViewPager.OnPageChangeListener, CourseTableGridAdapter.OnTimeClickListener {
    private final static String TAG = "CourseTableActivity";
    @Bind(R.id.gv_week)
    public GridView gv_week;
    private String[] week = new String[]{"一", "二", "三", "四", "五", "六", "日"};
    private CalendarWeekGridAdapter calendarWeekGridAdapter;

    @Bind(R.id.day_vp)
    public ViewPager day_vp;
    private DayViewPagerAdapter dayViewPagerAdapter;

    @Bind(R.id.gv_course_table)
    public GridView gv_course_table;

    @Bind(R.id.rl_info)
    public RelativeLayout rl_info;

    @Bind(R.id.iv_img)
    public ImageView iv_img;

    @Bind(R.id.tv_veteran)
    public TextView tv_veteran;

    @Bind(R.id.tv_age)
    public TextView tv_age;

    @Bind(R.id.tv_name)
    public TextView tv_name;

    @Bind(R.id.tv_play_information)
    public TextView tv_play_information;

    @Bind(R.id.tv_class_type)
    public TextView tv_class_type;

    @Bind(R.id.tv_class_time)
    public TextView tv_class_time;

    @Bind(R.id.tv_course_content)
    public TextView tv_course_content;

    @Bind(R.id.tv_circle)
    public TextView tv_circle;

    @Bind(R.id.tv_remind)
    public TextView tv_remind;




    @Bind(R.id.tv_date)
    public TextView tv_date;
    private CourseTableGridAdapter courseTableGridAdapter;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.ChooseDate":
                    //接收选择的日期
                    String date = intent.getStringExtra("date");
                    if (date.equals(RequestUtil.getCurrentTime())) {
                        tv_return.setVisibility(View.GONE);
                    }else{
                        tv_return.setVisibility(View.VISIBLE);
                    }

                    currentTime = date;
                    courseTableGridAdapter.setDatas(savedinition);
                    hide();
                    load();
                    break;
            }
        }
    };
    private String currentTime;
    private List<TimeDefineEntity.ListtimedefinitionBean> listtimedefinition;
    private String phoneNumber;
    private int appointmentid;
    private int ccoursedetailid;
    private List<TimeDefineEntity.ListtimedefinitionBean> savedinition;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    @Bind(R.id.tv_return)
    public TextView tv_return;
    private int clickBinaryNumber;
    private int sumBinaryNumber;
    private int coachgroupid;
    private int userid;
    private int positionsid;
    private int coachid;
    private String appointmenttel;
    private String appointmentname;
    private String appointmentdate;
    private PopupWindow deletewindow;
    private String timeDefinition;
    private RelativeLayout saveRl;
    private List<TimeDefineEntity.ListtimedefinitionBean> emptyDatas;

    @Override
    protected int getContentId() {
        return R.layout.activity_course_table;
    }

    @Override
    protected void init() {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                if (listtimedefinition != null) {
                    load();
                }else{
                    loadDatas();
                }
                EventBus.getDefault().post(EventBusMapUtil.getIntMap(8,1));
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
            }
        });
        showLoading();
        emptyDatas = new ArrayList<>();
        currentTime = RequestUtil.getCurrentTime();
        String[] split = currentTime.split("-");
        tv_date.setText(split[0] + "年" + split[1] + "月");
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.ChooseDate");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        savedinition = new ArrayList<>();

        SharedUtil.putString(Constants.COURSE_CALENDAR_TODAY, RequestUtil.getCurrentTime());
        SharedUtil.putString(Constants.COURSE_CALENDAR_CLICK, "");
        calendarWeekGridAdapter = new CalendarWeekGridAdapter(this);
        gv_week.setAdapter(calendarWeekGridAdapter);
        List<String> weekdatas = Arrays.asList(week);
        calendarWeekGridAdapter.setDatas(weekdatas);

        dayViewPagerAdapter = new DayViewPagerAdapter(getSupportFragmentManager());
        day_vp.setAdapter(dayViewPagerAdapter);
        day_vp.setCurrentItem(10000 / 2);
        day_vp.addOnPageChangeListener(this);
        day_vp.setOffscreenPageLimit(1);
        courseTableGridAdapter = new CourseTableGridAdapter(this);
        gv_course_table.setAdapter(courseTableGridAdapter);
        courseTableGridAdapter.setOnTimeClickListener(this);
    }

    @Override
    protected void loadDatas() {
        load();
    }

    private void load() {

            String start = currentTime;
            String end = currentTime;
            CoachCalendarRequestEntity coachCalendarRequestEntity = new CoachCalendarRequestEntity();
            coachCalendarRequestEntity.setBeginDate(start);
            coachCalendarRequestEntity.setEndDate(end);
            String request = new Gson().toJson(coachCalendarRequestEntity);
            String requestJson = RequestUtil.getRequestJson(this, request);
            String url = "/api/APICalendar/QueryCalendar";
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, requestJson);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        SharedUtil.putString(Constants.COURSE_CALENDAR_CLICK, "");
    }

    @Override
    public void parseDatas(String json) {

        try {
            JSONObject jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("1")) {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                AppointmentListEntity appointmentListEntity = new Gson().fromJson(jo.toString(), AppointmentListEntity.class);
                List<AppointmentListEntity.ListAppointmentBean> listAppointment = appointmentListEntity.getListAppointment();
                List<TimeDefineEntity.ListtimedefinitionBean> appointmentlist = new ArrayList<>();
                List<AppointmentListEntity.ListTimeDefinitionBean> listTimeDefinition = appointmentListEntity.getListTimeDefinition();
                if (listAppointment != null) {
                    for (AppointmentListEntity.ListAppointmentBean listAppointmentBean : listAppointment) {
                        int binarynumber = listAppointmentBean.getBinarynumber();
                        for (int i = 0; i < listTimeDefinition.size(); i++) {
                            int timeBinaryNumber = listTimeDefinition.get(i).getBinarynumber();
                            if ((binarynumber & timeBinaryNumber) == timeBinaryNumber) {
                                TimeDefineEntity.ListtimedefinitionBean listtimedefinitionBean = new TimeDefineEntity.ListtimedefinitionBean();
                                listtimedefinitionBean.setBinarynumber(timeBinaryNumber);
                                TimeDefineEntity.ListtimedefinitionBean.TimedefinitionBean timedefinitionBean = new TimeDefineEntity.ListtimedefinitionBean.TimedefinitionBean();
                                timedefinitionBean.setBinarynumber(timeBinaryNumber);
                                timedefinitionBean.setTimedefinitionid(listTimeDefinition.get(i).getTimedefinitionid());
                                timedefinitionBean.setTimesection(listTimeDefinition.get(i).getTimesection());
                                timedefinitionBean.setListAppointmentBean(listAppointmentBean);
                                listtimedefinitionBean.setTimedefinitionBean(timedefinitionBean);
                                appointmentlist.add(listtimedefinitionBean);
                            }
                        }
                    }
                }
                List<TimeDefineEntity.ListtimedefinitionBean> sort = sort(appointmentlist);
                courseTableGridAdapter.setDatas(sort);
            } else if (statuscode.equals("2")) {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                courseTableGridAdapter.setDatas(emptyDatas);
            } else {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                courseTableGridAdapter.setDatas(emptyDatas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismissLoading();
    }

    private List<TimeDefineEntity.ListtimedefinitionBean> sort(List<TimeDefineEntity.ListtimedefinitionBean> appointmentlist) {
        TimeDefineEntity.ListtimedefinitionBean.TimedefinitionBean t;
        int tb;
        for (int i = 0; i < appointmentlist.size() - 1 ; i++) {
            for (int j = 0; j < appointmentlist.size() - 1 - i; j++) {
                if (appointmentlist.get(j).getBinarynumber() > appointmentlist.get(j + 1).getBinarynumber()) {
                    Log.d(TAG, "sort: 交换" + appointmentlist.get(j).getBinarynumber() + "  " + appointmentlist.get(j + 1).getBinarynumber());
                    t = appointmentlist.get(j).getTimedefinitionBean();
                    appointmentlist.get(j).setTimedefinitionBean(appointmentlist.get(j+1).getTimedefinitionBean());
                    appointmentlist.get(j+1).setTimedefinitionBean(t);

                    tb = appointmentlist.get(j).getBinarynumber();
                    appointmentlist.get(j).setBinarynumber(appointmentlist.get(j+1).getBinarynumber());
                    appointmentlist.get(j+1).setBinarynumber(tb);
                }
            }
        }
        return appointmentlist;
    }

    @Override
    public void requestFailed() {
        dismissLoading();
        Toast.makeText(CourseTableActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }

    @OnClick({R.id.back, R.id.tv_appointment,R.id.tv_remind, R.id.cancel, R.id.tv_contact_him, R.id.tv_return})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_return:
                //回到今天
                currentTime = RequestUtil.getCurrentTime();
                SharedUtil.putString(Constants.COURSE_CALENDAR_TODAY, RequestUtil.getCurrentTime());
                SharedUtil.putString(Constants.COURSE_CALENDAR_CLICK, "");
                dayViewPagerAdapter = new DayViewPagerAdapter(getSupportFragmentManager());
                day_vp.setAdapter(dayViewPagerAdapter);
                day_vp.setCurrentItem(10000 / 2);
                hide();
                load();
                break;
            case R.id.tv_appointment:
                //预约学员上课
                startActivityForResult(new Intent(this, AppointmentCourseActivity.class), 0x511);
                break;
            case R.id.tv_remind:
                //提醒签课
                SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
                SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
                appointmentBean.setAppointmentid(appointmentid);
                subscribeRequestEntity.setAppointment(appointmentBean);
                String request = new Gson().toJson(subscribeRequestEntity);
                String requestJson = RequestUtil.getRequestJson(this, request);
                String url = "/api/APISignClass/QuerySignClass";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        Log.d(TAG, "parseDatas: --->" + json);
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                Toast.makeText(CourseTableActivity.this, "提醒成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CourseTableActivity.this, "提醒失败，错误码："  + statusmessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void requestFailed() {
                        Toast.makeText(CourseTableActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, url, requestJson);
                break;
            case R.id.cancel:
                //取消预约
                showDeleteAppointment();

                break;
            case R.id.tv_contact_him:
                //联系他
                Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                    @Override
                    public void onSure() {
                        //意图：想干什么事
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url:统一资源定位符
                        //uri:统一资源标示符（更广）
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        try {
                            //开启系统拨号器
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(CourseTableActivity.this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }).showDialog(this, "拨打电话", "你确定拨打该学员电话吗?", "拨打");
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Intent intent = new Intent();
        intent.setAction("com.glory.broadcast.CourseTableCalendar");
        localBroadcastManager.sendBroadcast(intent);
        int sub = (5000 - position) * 7 + TimeUtil.getWeek(RequestUtil.getCurrentTime()) - 1;
        String time = TimeUtil.subDate(sub);
        String[] split = time.split("-");
        tv_date.setText(split[0] + "年" + split[1] + "月");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(int position, String time) {
        TimeDefineEntity.ListtimedefinitionBean datas = courseTableGridAdapter.getDatas(position);
        AppointmentListEntity.ListAppointmentBean listAppointmentBean = datas.getTimedefinitionBean().getListAppointmentBean();
        if (listAppointmentBean == null) {
            hide();
        } else {
            show();
            timeDefinition = listAppointmentBean.getTimeDefinition();
            appointmentid = listAppointmentBean.getAppointmentid();
            sumBinaryNumber = listAppointmentBean.getBinarynumber();
            coachgroupid = listAppointmentBean.getCoachgroupid();
            clickBinaryNumber = datas.getBinarynumber();
            phoneNumber = listAppointmentBean.getAppointmenttel();
            userid = listAppointmentBean.getUserid();
            positionsid = listAppointmentBean.getPositionsid();
            coachid = listAppointmentBean.getCoachid();
            ccoursedetailid = listAppointmentBean.getCcoursedetailid();
            appointmenttel = listAppointmentBean.getAppointmenttel();
            appointmentname = listAppointmentBean.getAppointmentname();
            appointmentdate = listAppointmentBean.getAppointmentdate();
            GlideUtil.loadCircleImageView(this, listAppointmentBean.getCustomerPhoto(), iv_img, R.drawable.icon_chat_golffriend);
            tv_veteran.setText("球龄" + listAppointmentBean.getAppontimengolfage() + "年");
            tv_age.setText(listAppointmentBean.getAppontimentage() + "岁");
            tv_name.setText(listAppointmentBean.getRemarks());
            tv_play_information.setText(listAppointmentBean.getPositionsname());
            tv_class_type.setText(listAppointmentBean.getPackagename());
            String timeDefinition = listAppointmentBean.getTimeDefinition();
            if (timeDefinition.contains(",")) {
                tv_class_time.setText(time);
            }else {
                tv_class_time.setText(time);
            }
            tv_course_content.setText(listAppointmentBean.getCcoursedetailname());
        }
    }

    /**
     * 显示控件
     */
    private void show() {
        rl_info.setVisibility(View.VISIBLE);
        tv_circle.setVisibility(View.VISIBLE);
        iv_img.setVisibility(View.VISIBLE);
        tv_name.setVisibility(View.VISIBLE);
        tv_veteran.setVisibility(View.VISIBLE);
        tv_age.setVisibility(View.VISIBLE);
        tv_remind.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏控件
     */
    private void hide() {
        rl_info.setVisibility(View.GONE);
        tv_circle.setVisibility(View.GONE);
        iv_img.setVisibility(View.GONE);
        tv_name.setVisibility(View.GONE);
        tv_veteran.setVisibility(View.GONE);
        tv_age.setVisibility(View.GONE);
        tv_remind.setVisibility(View.GONE);
    }

    private void editAppointment() {
        SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
        SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
        appointmentBean.setAppointmentid(appointmentid);
        appointmentBean.setCoachgroupid(coachgroupid);
        appointmentBean.setUserid(userid);
        appointmentBean.setPositionsid(positionsid);
        appointmentBean.setCoachid(coachid);
        appointmentBean.setCcoursedetailid(ccoursedetailid);
        appointmentBean.setAppointmenttel(appointmenttel);
        appointmentBean.setAppointmentname(appointmentname);
        appointmentBean.setBinarynumber(sumBinaryNumber - clickBinaryNumber);
        appointmentBean.setAppointmentdate(appointmentdate);
        subscribeRequestEntity.setAppointment(appointmentBean);
        String request = new Gson().toJson(subscribeRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIAppointment/EditAppointment";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(CourseTableActivity.this, "取消预约成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction("com.glory.broadcast.CourseTableCalendar");
                        localBroadcastManager.sendBroadcast(intent);
                        hide();
                        load();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(this, url, requestJson);

    }

    private void deleteAppointment() {
        SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
        SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
        appointmentBean.setAppointmentid(appointmentid);
        appointmentBean.setCcoursedetailid(ccoursedetailid);
        subscribeRequestEntity.setAppointment(appointmentBean);
        String request = new Gson().toJson(subscribeRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIAppointment/DeleteAppointment";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        deletewindow.dismiss();
                        Toast.makeText(CourseTableActivity.this, "取消预约成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction("com.glory.broadcast.CourseTableCalendar");
                        localBroadcastManager.sendBroadcast(intent);
                        hide();
                        load();
                    }else {
                        Toast.makeText(CourseTableActivity.this, "取消预约失败，错误码："  + statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(CourseTableActivity.this, url, requestJson);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x511) {
            Intent intent = new Intent();
            intent.setAction("com.glory.broadcast.CourseTableCalendar");
            localBroadcastManager.sendBroadcast(intent);
            EventBus.getDefault().post(EventBusMapUtil.getIntMap(8,1));
            hide();
            load();
        }
    }

    /**
     * 显示取消预约的popupWindow
     */
    private void showDeleteAppointment() {
        String times = timeDefinition;
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_delete_appointment, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        String[] split;
        if (times.contains(",")) {
            split = times.split(",");
        } else {
            split = new String[]{times};
        }
        LinearLayout ll_add_view = (LinearLayout) view.findViewById(R.id.ll_add_view);
        for (int i = 0; i < split.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(split[i]);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.colorBlack));
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            textView.setLayoutParams(layoutParams);
            ll_add_view.addView(textView);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletewindow.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAppointment();
            }
        });


        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        deletewindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        deletewindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        deletewindow.setBackgroundDrawable(dw);
        // 在底部显示
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        deletewindow.showAtLocation(findViewById(R.id.back), Gravity.CENTER, 0, 0);
    }
}
