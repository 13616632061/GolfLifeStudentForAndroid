package com.glorystudent.golflife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.CalendarWeekGridAdapter;
import com.glorystudent.adapter.CourseViewPagerAdapter;
import com.glorystudent.entity.AllAppointmentEntity;
import com.glorystudent.entity.AppointmentRequestEntity;
import com.glorystudent.entity.AreaEntity;
import com.glorystudent.entity.ContractCourseEntity;
import com.glorystudent.entity.ContractInfoEntity;
import com.glorystudent.entity.ContractTraineeEntity;
import com.glorystudent.entity.CourseDetailsEntity;
import com.glorystudent.entity.GroupAddressEntity;
import com.glorystudent.entity.MyCourseEntity;
import com.glorystudent.entity.PositionEntity;
import com.glorystudent.entity.QueryContractRequestEntity;
import com.glorystudent.entity.SubscribeRequestEntity;
import com.glorystudent.entity.UserInformationEntity;
import com.glorystudent.entity.UserRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflibrary.widget.pickerscrollview.PickerScrollView;
import com.glorystudent.golflibrary.widget.pickerscrollview.Pickers;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 课程预约
 * Created by hyj on 2017/2/13.
 */
public class AppointmentCourseActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private final static String TAG = "AppointmentCActivity";
    @Bind(R.id.gv_week)
    public GridView gv_week;
    private String[] week = new String[]{"一", "二","三","四","五","六","日"};
    private CalendarWeekGridAdapter calendarWeekGridAdapter;

    @Bind(R.id.day_vp)
    public ViewPager day_vp;
    @Bind(R.id.tv_date)
    public TextView tv_date;

    @Bind(R.id.hsv_ll_add)
    public LinearLayout hsv_ll_add;

    @Bind(R.id.choose_student)
    public TextView choose_student;
    @Bind(R.id.choose_course)
    public TextView choose_course;
    @Bind(R.id.choose_area)
    public TextView choose_area;
    @Bind(R.id.choose_play)
    public TextView choose_play;
    @Bind(R.id.choose_time)
    public TextView choose_time;
    @Bind(R.id.tv_appointment)
    public TextView tv_appointment;

    private CourseViewPagerAdapter dayViewPagerAdapter;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private String chooseDateTimes;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.ChooseCourseDate":
                    String date = intent.getStringExtra("date");
                    chooseDateTimes = date;
                    if(TimeUtil.dateCompareToday(chooseDateTimes)){
                        //今天之后的日期(包括今天)
                        tv_appointment.setBackgroundResource(R.drawable.selector_btn_login);
                        tv_appointment.setClickable(true);
                    }else{
                        //今天之前的日期
                        tv_appointment.setBackgroundResource(R.drawable.shape_btn_login_not);
                        tv_appointment.setClickable(false);
                    }
                    choose_play.setText("");
                    choose_play.setHint("选择打位");
                    count = 0;
                    choose_time.setText("已选择" + count + "个时段");
                    hsv_ll_add.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private Map<Integer, Boolean> saveStateMap;
    private PopupWindow window;
    private String studentName;
    private List<Pickers> studentNameList;
    private String contractName1;
    private String contractName2;
    private String groupName;
    private ContractCourseEntity contractCourseEntity;
    private String playName;
    private String areaName;
    private int areaId;
    private int playId;
    private int coachid;
    private String studentid;
    private int contractid;
    private int sum;
    private int binarynumber;
    private int count;
    private int appointmentType = 2;//预约类型 1预约教练，2预约学员
    @Bind(R.id.tv_type)
    public TextView tv_type;
    @Bind(R.id.tv_count)
    public TextView tv_count;

    @Bind(R.id.ll_choose_student)
    public LinearLayout ll_choose_student;

    @Bind(R.id.ll_choose_course)
    public LinearLayout ll_choose_course;
    @Bind(R.id.line)
    public View line;
    @Override
    protected int getContentId() {
        return R.layout.activity_appointment_course;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("studentid", -1);
        String name = intent.getStringExtra("username");
        int cid = intent.getIntExtra("coachid", -1);
        int coursedetailid = intent.getIntExtra("coursedetailid", -1);
        String coursedetailname = intent.getStringExtra("coursedetailname");

        if (cid != -1 && coursedetailid != -1 && coursedetailname != null) {
            coachid = cid;
            contractid = coursedetailid;
            contractName2 = coursedetailname;
            choose_course.setText(contractName2);
            choose_course.setClickable(false);
        }

        if (id != -1 && name != null) {
            studentName = name;
            studentid = String.valueOf(id);
            choose_student.setText(studentName);
            choose_student.setClickable(false);
        }
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            MyCourseEntity.CcourselistBean listcontractBean = (MyCourseEntity.CcourselistBean) bundle.getSerializable("ListcontractBean");
            CourseDetailsEntity.ContractcourseBean contractcourseBean = (CourseDetailsEntity.ContractcourseBean) bundle.getSerializable("ContractcourseBean");

            if (listcontractBean != null && contractcourseBean != null) {
                tv_type.setVisibility(View.VISIBLE);
                tv_count.setVisibility(View.VISIBLE);
                line.setVisibility(View.GONE);
                tv_type.setText(contractcourseBean.getCcoursedetailname());
                tv_count.setText("第" + contractcourseBean.getCoursecode() + "节/共" + listcontractBean.getCoursetotalcount() + "节");
                studentid = String.valueOf(listcontractBean.getUserid());
                studentName = String.valueOf(listcontractBean.getCoachname());
                contractid = contractcourseBean.getCcoursedetailid();
                this.coachid = contractcourseBean.getCoachid();
                ll_choose_student.setVisibility(View.GONE);
                ll_choose_course.setVisibility(View.GONE);
                appointmentType = 1;
            }
        }
        String currentTime = RequestUtil.getCurrentTime();
        String[] split = currentTime.split("-");
        tv_date.setText(split[0] + "年"  + split[1] + "月");
        SharedUtil.putString(Constants.DAY_CALENDAR_TODAY, RequestUtil.getCurrentTime());
        SharedUtil.putString(Constants.DAY_CALENDAR_CLICK,"");
        hsv_ll_add.setVisibility(View.GONE);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.ChooseCourseDate");
        intentFilter.addAction("com.glory.broadcast.DATE");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        chooseDateTimes = RequestUtil.getCurrentTime();
        calendarWeekGridAdapter = new CalendarWeekGridAdapter(this);
        gv_week.setAdapter(calendarWeekGridAdapter);
        List<String> weekdatas = Arrays.asList(week);
        calendarWeekGridAdapter.setDatas(weekdatas);
        dayViewPagerAdapter = new CourseViewPagerAdapter(getSupportFragmentManager());
        day_vp.setAdapter(dayViewPagerAdapter);
        day_vp.setCurrentItem(10000/2);
        day_vp.setOffscreenPageLimit(1);
        day_vp.addOnPageChangeListener(this);
    }

    @OnClick({R.id.back, R.id.tv_appointment, R.id.choose_student, R.id.choose_course, R.id.choose_area, R.id.choose_play})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.choose_student:
                //选择学员
                showWaiting();
                QueryContractRequestEntity queryContractRequestEntity = new QueryContractRequestEntity();
                QueryContractRequestEntity.ContractTraineeBean contractTraineeBean = new QueryContractRequestEntity.ContractTraineeBean();
                queryContractRequestEntity.setPage(-1);
                queryContractRequestEntity.setContractTrainee(contractTraineeBean);
                String request = new Gson().toJson(queryContractRequestEntity);
                String queryrequestJson = RequestUtil.getRequestJson(this, request);
                String url = "/api/APIContractTrainees/QueryContractTrainees";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                ContractTraineeEntity contractTraineeEntity = new Gson().fromJson(jo.toString(), ContractTraineeEntity.class);
                                List<ContractTraineeEntity.ListContractUserExtBean> listContractUserExt = contractTraineeEntity.getListContractUserExt();
                                if (listContractUserExt != null) {
                                    showPopwindow(listContractUserExt);
                                }
                            }else{
                                Toast.makeText(AppointmentCourseActivity.this, "暂无学员", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dismissWaiting();
                    }

                    @Override
                    public void requestFailed() {
                        dismissWaiting();
                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, url, queryrequestJson);



//                String request = RequestUtil.getEmptyParameter(this);
//                String url = "/api/APIUser/QueryContractUser";
//                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
//                    @Override
//                    public void parseDatas(String json) {
//                        try {
//                            JSONObject jo = new JSONObject(json);
//                            JSONArray ja = jo.getJSONArray("listContractuser");
//                            List<ContractStudentEntity> datas = new ArrayList<>();
//                            TypeToken<List<ContractStudentEntity>> tt = new TypeToken<List<ContractStudentEntity>>(){};
//                            datas = new Gson().fromJson(ja.toString(), tt.getType());
//                            if(datas != null){
//                                showPopwindow(datas);
//                            }else{
//                                Toast.makeText(AppointmentCourseActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        dismissWaiting();
//                    }
//
//                    @Override
//                    public void requestFailed() {
//                        dismissWaiting();
//                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
//                    }
//                }).getEntityData(this, url, request);
                break;
            case R.id.choose_course:
                //选择课程
                String student = choose_student.getText().toString();
                if (student == null || student.isEmpty()) {
                    Toast.makeText(AppointmentCourseActivity.this, "请先选择学员", Toast.LENGTH_SHORT).show();
                    break;
                }
                showWaiting();
                /**
                 * 获取合约
                 */
                String request2 = "{\"usersid\":" + studentid + ",\"finish\":" + "0" + ",\"status\":" + "1" + "}";
                final String requestJson2 = RequestUtil.getRequestJson(this, request2);
                Log.d(TAG, "load: --->" + requestJson2);
                String url2 = "/api/APIContract/QueryContractCourseListBy";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            dismissLoading();
                            Log.d(TAG, "parseDatas: --->查询课程明细" + json);
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                ContractInfoEntity contractCourseEntity = new Gson().fromJson(jo.toString(), ContractInfoEntity.class);
                                coachid = contractCourseEntity.getListcontracts().get(0).getCoachid();
                                List<ContractInfoEntity.ListcontractsBean> listcontract = contractCourseEntity.getListcontracts();
                                if (listcontract != null && listcontract.size() > 0) {
                                    for (int i = 0; i < listcontract.size(); i++) {
                                        int contractid = listcontract.get(i).getContractid();
                                        int state = listcontract.get(i).getStatus();
                                        List<ContractInfoEntity.ListContractCourseDetailBean> listContractCourseDetail = contractCourseEntity.getListContractCourseDetail();
                                        if (listContractCourseDetail != null && listContractCourseDetail.size() > 0) {
                                            List<ContractInfoEntity.ListContractCourseDetailBean> listContractCourseDetailBeen = sortContractcourseList(listContractCourseDetail, contractid, state);
                                            listcontract.get(i).setListContractDetail(listContractCourseDetailBeen);
                                        }
                                    }
                                    showCoursePopwindow(listcontract);
                                }
                            }else {
                                Toast.makeText(AppointmentCourseActivity.this, "获取套餐失败，错误码：" + statusmessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissWaiting();
                    }

                    @Override
                    public void requestFailed() {
                        dismissWaiting();
                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, url2, requestJson2);





//                String request2 = "{\"usersid\":" + studentid + ",\"finish\":" + "0}";
//                final String requestJson2 = RequestUtil.getRequestJson(this, request2);
//                String url2 = "/api/APIContract/QueryContractCourseListBy";
//                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
//                    @Override
//                    public void parseDatas(String json) {
//                        try {
//                            JSONObject jo = new JSONObject(json);
//                            String statuscode = jo.getString("statuscode");
//                            String statusmessage = jo.getString("statusmessage");
//                            if(statuscode.equals("1")){
//                                contractCourseEntity = new Gson().fromJson(jo.toString(), ContractCourseEntity.class);
//                                if (contractCourseEntity != null) {
//                                    coachid = contractCourseEntity.getListcontract().get(0).getCoachid();
//                                    showPopwindow(contractCourseEntity);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        dismissWaiting();
//                    }
//
//                    @Override
//                    public void requestFailed() {
//                        dismissWaiting();
//                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
//                    }
//                }).getEntityData(this, url2, requestJson2);

                break;
            case R.id.choose_area:
                //选择区域
                if (appointmentType == 2) {
                    //预约学员才
                    String student2 = choose_student.getText().toString();
                    if (student2 == null || student2.isEmpty()) {
                        Toast.makeText(AppointmentCourseActivity.this, "请先选择学员", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String course = choose_course.getText().toString();
                    if (course == null || course.isEmpty()) {
                        Toast.makeText(AppointmentCourseActivity.this, "请先选择课程", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                showWaiting();
                String requestEmpty = RequestUtil.getEmptyParameter(this);
                String addressurl = "/api/APIGroupAddress/QueryAddressList";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if(statuscode.equals("1")){
                                GroupAddressEntity groupAddressEntity = new Gson().fromJson(jo.toString(), GroupAddressEntity.class);
                                int addressid = groupAddressEntity.getListgroupaddress().get(0).getAddressid();
                                String addressRequest = "{\"addressid\":" + addressid + "}";
                                String requestJson = RequestUtil.getRequestJson(AppointmentCourseActivity.this, addressRequest);
                                String url = "/api/APIArea/QueryArea";
                                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                                    @Override
                                    public void parseDatas(String json) {
                                        try {
                                            JSONObject jo = new JSONObject(json);
                                            String statuscode = jo.getString("statuscode");
                                            String statusmessage = jo.getString("statusmessage");
                                            if (statuscode.equals("1")) {
                                                AreaEntity area = new Gson().fromJson(jo.toString(), AreaEntity.class);
                                                List<AreaEntity.ListareaBean> listarea = area.getListarea();
                                                if(listarea != null){
                                                    getArea(listarea);
                                                }
                                            }
                                        }catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void requestFailed() {

                                    }
                                }).getEntityData(AppointmentCourseActivity.this, url, requestJson);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissWaiting();
                    }

                    @Override
                    public void requestFailed() {
                        dismissWaiting();
                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, addressurl, requestEmpty);

                break;
            case R.id.choose_play:

                //选择打位
                if (appointmentType == 2) {
                    String student3 = choose_student.getText().toString();
                    if (student3== null || student3.isEmpty()) {
                        Toast.makeText(AppointmentCourseActivity.this, "请先选择学员", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String course2 = choose_course.getText().toString();
                    if (course2 == null || course2.isEmpty()) {
                        Toast.makeText(AppointmentCourseActivity.this, "请先选择课程", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                String area = choose_area.getText().toString();
                if (area == null || area.isEmpty()) {
                    Toast.makeText(AppointmentCourseActivity.this, "请先选择区域", Toast.LENGTH_SHORT).show();
                    break;
                }


                showWaiting();
                String groupRequest = "{\"areaid\":" + areaId + "}";
                String requestJson = RequestUtil.getRequestJson(this, groupRequest);
                String playurl = "/api/APIPositions/QueryPositions";
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                PositionEntity positionEntity = new Gson().fromJson(jo.toString(), PositionEntity.class);
                                List<PositionEntity.ListpositionsBean> listpositions = positionEntity.getListpositions();
                                getPosition(listpositions);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissWaiting();
                    }

                    @Override
                    public void requestFailed() {
                        dismissWaiting();
                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, playurl, requestJson);
                break;
            case R.id.tv_appointment:
                //预约
                if (appointmentType == 2) {
                    String student3 = choose_student.getText().toString();
                    if (student3== null || student3.isEmpty()) {
                        Toast.makeText(AppointmentCourseActivity.this, "请先选择学员", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String course2 = choose_course.getText().toString();
                    if (course2 == null || course2.isEmpty()) {
                        Toast.makeText(AppointmentCourseActivity.this, "请先选择课程", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                String area2 = choose_area.getText().toString();
                if (area2 == null || area2.isEmpty()) {
                    Toast.makeText(AppointmentCourseActivity.this, "请先选择区域", Toast.LENGTH_SHORT).show();
                    break;
                }
                String play = choose_play.getText().toString();
                if (play == null || play.isEmpty()) {
                    Toast.makeText(AppointmentCourseActivity.this, "请选择打位", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (count <= 0) {
                    Toast.makeText(AppointmentCourseActivity.this, "请选择至少1个时段", Toast.LENGTH_SHORT).show();
                    break;
                }
                showWaiting();
                UserRequestEntity userRequestEntity = new UserRequestEntity();
                UserRequestEntity.UserBean userBean = new UserRequestEntity.UserBean();
                userBean.setUserid(Integer.valueOf(studentid));
                userRequestEntity.setUser(userBean);
                String userRequest = new Gson().toJson(userRequestEntity);
                String requestUser = RequestUtil.getRequestJson(this, userRequest);
                String userurl = "/api/APIUser/QueryUsers";
                Log.d(TAG, "onclick: --JSON-->" + requestUser);
                OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                    @Override
                    public void parseDatas(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                UserInformationEntity userInformationEntity = new Gson().fromJson(jo.toString(), UserInformationEntity.class);
                                List<UserInformationEntity.ListUsersBean> listUsers = userInformationEntity.getListUsers();
                                appointmentUser(listUsers);
                            } else {
                                Toast.makeText(AppointmentCourseActivity.this, "预约失败,错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismissWaiting();
                    }

                    @Override
                    public void requestFailed() {
                        dismissWaiting();
                        Toast.makeText(AppointmentCourseActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }).getEntityData(this, userurl, requestUser);
                break;
        }
    }

    private void appointmentUser(List<UserInformationEntity.ListUsersBean> listUsers){
        String phonenumber = listUsers.get(0).getPhonenumber();
        SubscribeRequestEntity subscribeRequestEntity = new SubscribeRequestEntity();
        SubscribeRequestEntity.AppointmentBean appointmentBean = new SubscribeRequestEntity.AppointmentBean();
        appointmentBean.setUserid(Integer.valueOf(studentid));
        appointmentBean.setPositionsid(playId);
        appointmentBean.setCoachid(coachid);
        appointmentBean.setCcoursedetailid(contractid);
        appointmentBean.setAppointmenttel(phonenumber);
        appointmentBean.setAppointmentname(studentName);
        appointmentBean.setAppointmentdate(chooseDateTimes);

        appointmentBean.setBinarynumber(sum);
        subscribeRequestEntity.setAppointment(appointmentBean);
        subscribeRequestEntity.setAppointmenttype(appointmentType);
        subscribeRequestEntity.setCoachid(coachid);
        subscribeRequestEntity.setUsersid(Integer.valueOf(studentid));
        String request = new Gson().toJson(subscribeRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        Log.d(TAG, "appointmentUser: --->" + requestJson);
        String url = "/api/APIAppointment/AddAppointment";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        choose_play.setText("");
                        choose_play.setHint("选择打位");
                        count = 0;
                        choose_time.setText("已选择" + count + "个时段");
                        hsv_ll_add.setVisibility(View.GONE);
                        if (appointmentType == 1) {
                            EventBus.getDefault().post(EventBusMapUtil.getIntMap(3,1));
                        }
                        Toast.makeText(AppointmentCourseActivity.this, "已预约成功", Toast.LENGTH_SHORT).show();
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


    private void getPosition(List<PositionEntity.ListpositionsBean> positions){
        List<PositionEntity.ListpositionsBean> positionDatas = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getPositionsstatus().equals("1")) {
                positionDatas.add(positions.get(i));
            }
        }
        showPlayPopwindow(positionDatas);
    }

    private void getArea(List<AreaEntity.ListareaBean> areas){
        List<AreaEntity.ListareaBean> areaDatas = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).isAreaenable()) {
                areaDatas.add(areas.get(i));
            }
        }
        if (areaDatas.size() == 0) {
            Toast.makeText(AppointmentCourseActivity.this, "暂无可打区域", Toast.LENGTH_SHORT).show();
        }else {
            showAreaPopwindow(areaDatas);
        }
    }

    /**
     * TODO 显示学员popupWindow
     */
    private void showPopwindow(List<ContractTraineeEntity.ListContractUserExtBean> datas) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_student.setText(studentName);
                window.dismiss();
            }
        });
        PickerScrollView pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(AppointmentCourseActivity.this.findViewById(R.id.choose_student),
                Gravity.BOTTOM, 0, 0);

        studentNameList = new ArrayList<Pickers>();

        int size = datas.size();
        String[] id = new String[size];
        String[] name = new String[size];
        for (int i = 0; i < datas.size(); i++) {
            id[i] = datas.get(i).getContracttraineeid() + "";
            name[i] = datas.get(i).getContracttraineename();
        }
        for (int i = 0; i < name.length; i++) {
            studentNameList.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrlllview.setData(studentNameList);
        pickerscrlllview.setSelected(0);
        if (name.length > 0) {
            studentid = id[0];
            studentName = name[0];
        }
        pickerscrlllview.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                studentid = pickers.getShowId();
                studentName = pickers.getShowConetnt();
            }
        });
    }

    /**
     * TODO 显示课程popupWindow
     */
    private void showCoursePopwindow(List<ContractInfoEntity.ListcontractsBean> datas) {
        if (datas != null && datas.size() > 0) {
            studentid = String.valueOf(datas.get(0).getUserid());
        }
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_double_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_course.setText(contractName2);
                window.dismiss();
            }
        });
        PickerScrollView pickerscrlllview1 = (PickerScrollView) view.findViewById(R.id.pickerscrlllview1);
        PickerScrollView pickerscrlllview2 = (PickerScrollView) view.findViewById(R.id.pickerscrlllview2);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(AppointmentCourseActivity.this.findViewById(R.id.choose_student),
                Gravity.BOTTOM, 0, 0);

        /**
         * 第一个
         */

         List<Pickers> contractNameList1 = new ArrayList<Pickers>();


        int size1 = datas.size();
        String[] id1 = new String[size1];
        String[] name1 = new String[size1];
        for (int i = 0; i < datas.size(); i++) {
            id1[i] = datas.get(i).getContractid() + "";
            name1[i] = datas.get(i).getContractname();
        }
        for (int i = 0; i < name1.length; i++) {
            contractNameList1.add(new Pickers(name1[i], id1[i]));
        }



        if (name1.length > 0) {
            contractName1 = name1[0];
        }
        /**
         * 第二个
         */
        List<Pickers> contractNameList2 = new ArrayList<Pickers>();
        List<ContractInfoEntity.ListContractCourseDetailBean> listContractDetail = datas.get(0).getListContractDetail();
        int size2 = listContractDetail.size();
        String[] id2 = new String[size2];
        String[] name2 = new String[size2];
        for (int i = 0; i < listContractDetail.size(); i++) {
            id2[i] = listContractDetail.get(i).getCcoursedetailid() + "";
            name2[i] = listContractDetail.get(i).getCcoursedetailname();
        }
        for (int i = 0; i < name2.length; i++) {
            contractNameList2.add(new Pickers(name2[i], id2[i]));
        }

        // 设置数据，默认选择第一条
        pickerscrlllview1.setData(contractNameList1);
        pickerscrlllview1.setSelected(0);
        if (name1.length > 0) {
            contractName1 = name1[0];
        }

        // 设置数据，默认选择第一条
        pickerscrlllview2.setData(contractNameList2);
        pickerscrlllview2.setSelected(0);
        if (name2.length > 0) {
            contractid = Integer.valueOf(id2[0]);
            contractName2 = name2[0];
        }

        pickerscrlllview2.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                contractid = Integer.valueOf(pickers.getShowId());
                contractName2 = pickers.getShowConetnt();
            }
        });

        pickerscrlllview1.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                contractName1 = pickers.getShowConetnt();
            }
        });
    }

    /**
     * TODO 显示区域popupWindow
     */
    private void showAreaPopwindow(List<AreaEntity.ListareaBean> areas) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_area.setText(areaName);
                window.dismiss();
            }
        });
        PickerScrollView pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(AppointmentCourseActivity.this.findViewById(R.id.choose_student),
                Gravity.BOTTOM, 0, 0);

        List<Pickers> groupNameList = new ArrayList<Pickers>();

        int size = areas.size();
        String[] id = new String[size];
        String[] name = new String[size];

        for (int i = 0; i < areas.size(); i++) {
            id[i] = areas.get(i).getAreaid() + "";
            name[i] = areas.get(i).getAreaname();
        }
        for (int i = 0; i < name.length; i++) {
            groupNameList.add(new Pickers(name[i], id[i]));
        }

        // 设置数据，默认选择第一条
        pickerscrlllview.setData(groupNameList);
        pickerscrlllview.setSelected(0);
        if (name.length > 0) {
            areaId = Integer.valueOf(id[0]);
            areaName = name[0];
        }
        pickerscrlllview.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                areaId = Integer.valueOf(pickers.getShowId());
                areaName = pickers.getShowConetnt();
            }
        });
    }

    /**
     * TODO 显示打位popupWindow
     */
    private void showPlayPopwindow(List<PositionEntity.ListpositionsBean> positionDatas) {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_golfage_layout, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_play.setText(playName);
                getTimeQuantum();
                window.dismiss();
            }
        });
        PickerScrollView pickerscrlllview = (PickerScrollView) view.findViewById(R.id.pickerscrlllview);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(AppointmentCourseActivity.this.findViewById(R.id.choose_student),
                Gravity.BOTTOM, 0, 0);

        List<Pickers> playNameList = new ArrayList<Pickers>();


        int size = positionDatas.size();
        String[] id = new String[size];
        String[] name = new String[size];
        for (int i = 0; i < positionDatas.size(); i++) {
            id[i] = positionDatas.get(i).getPositionsid() + "";
            name[i] = positionDatas.get(i).getPositionsname();
        }
        for (int i = 0; i < name.length; i++) {
            playNameList.add(new Pickers(name[i], id[i]));
        }
        // 设置数据，默认选择第一条
        pickerscrlllview.setData(playNameList);
        pickerscrlllview.setSelected(0);
        if (name.length > 0) {
            playId = Integer.valueOf(id[0]);
            playName = name[0];
        }
        pickerscrlllview.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                playId = Integer.valueOf(pickers.getShowId());
                playName = pickers.getShowConetnt();
            }
        });
    }

    /**
     * TODO 获取时间段
     */
    private void getTimeQuantum(){
        AppointmentRequestEntity appointmentRequestEntity = new AppointmentRequestEntity();
        appointmentRequestEntity.setDatetimes(chooseDateTimes);
        appointmentRequestEntity.setCoachid(String.valueOf(coachid));
        appointmentRequestEntity.setPositionsid(playId);
        appointmentRequestEntity.setUsersid(studentid);
        String request = new Gson().toJson(appointmentRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIAllAppointment/QueryAllAppointmentBy";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        AllAppointmentEntity allAppointmentEntity = new Gson().fromJson(jo.toString(), AllAppointmentEntity.class);
                        addHorizontalScrollView(allAppointmentEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(this, url, requestJson);
    }


    private void addHorizontalScrollView(AllAppointmentEntity datas){
        hsv_ll_add.setVisibility(View.VISIBLE);
        hsv_ll_add.removeAllViews();
        List<AllAppointmentEntity.ListtimeplanBean> listtimeplan = datas.getListtimeplan();
        final List<AllAppointmentEntity.ListtimeplanBean> timeplanDatas = new ArrayList<>();

        saveStateMap = new HashMap<>();
        for (int i = 0; i < listtimeplan.size(); i++) {
            if (listtimeplan.get(i).getPlanstatus().equals("1")) {
                timeplanDatas.add(listtimeplan.get(i));
            }
        }
        List<Integer> hiddenList = getBinaryInteger(datas, timeplanDatas);

        for (int i = 0; i < timeplanDatas.size(); i++) {
            saveStateMap.put(i, false);
            Log.d(TAG, "addHorizontalScrollView: 时间定义--->" + timeplanDatas.get(i).getBinarynumber() + " " + timeplanDatas.get(i).getTimesection());
        }

        sum = 0;
        count = 0;
        final List<AllAppointmentEntity.ListtimeplanBean> finalDatas = timeplanDatas;
        for (int i = 0; i < timeplanDatas.size(); i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_time_quantum_layout, null);
            final TextView tv_start = (TextView) inflate.findViewById(R.id.tv_start);
            final TextView tv_end = (TextView) inflate.findViewById(R.id.tv_end);
            final TextView tv_split = (TextView) inflate.findViewById(R.id.tv_split);
            String[] split = timeplanDatas.get(i).getTimesection().split("-");
            tv_start.setText(split[0]);
            tv_end.setText(split[1]);
            LinearLayout ll_layout = (LinearLayout) inflate.findViewById(R.id.ll_layout);
            ll_layout.setTag(i);

            if (hiddenList.get(i) == 0) {
                ll_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        Log.d(TAG, "onClick: 选择的时间定义---->" + tag);
                        TextView tv_start = (TextView) v.findViewById(R.id.tv_start);
                        TextView tv_end = (TextView) v.findViewById(R.id.tv_end);
                        TextView tv_split = (TextView) v.findViewById(R.id.tv_split);
                        int binarynumber = finalDatas.get(tag).getBinarynumber();
                        Boolean state = saveStateMap.get(tag);
                        if (state = !state) {
                            sum = sum + binarynumber;
                            count ++;
                            tv_start.setTextColor(getResources().getColor(R.color.colorWhite));
                            tv_end.setTextColor(getResources().getColor(R.color.colorWhite));
                            tv_split.setTextColor(getResources().getColor(R.color.colorWhite));
                            v.setBackgroundResource(R.drawable.shape_time_quantum_yes);
                        }else{
                            sum = sum - binarynumber;
                            count --;
                            tv_start.setTextColor(getResources().getColor(R.color.colorGray9));
                            tv_end.setTextColor(getResources().getColor(R.color.colorGray9));
                            tv_split.setTextColor(getResources().getColor(R.color.colorGray9));
                            v.setBackgroundResource(R.drawable.shape_time_quantum_no);
                        }
                        saveStateMap.put(tag, state);
                        choose_time.setText("已选择" + count + "个时段");
                    }
                });
            }else{
                tv_start.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_end.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_split.setTextColor(getResources().getColor(R.color.colorWhite));
                ll_layout.setBackgroundResource(R.drawable.shape_time_quantum_not);
                ll_layout.setClickable(false);
            }

            hsv_ll_add.addView(inflate);
        }
    }

    private List<Integer> getBinaryInteger(AllAppointmentEntity datas,
                                           List<AllAppointmentEntity.ListtimeplanBean> timeplanDatas){
        List<Integer> hiddenList = new ArrayList<>();
        int coachperiod = datas.getCoachperiod();
        int userperiod = datas.getUserperiod();
        int planpauseperiod = datas.getPlanpauseperiod();
        int appointmentperiod = datas.getAppointmentperiod();
        for (int i = 0; i < timeplanDatas.size(); i++) {
            int binarynumber = timeplanDatas.get(i).getBinarynumber();
            if((binarynumber & coachperiod) == binarynumber){
                hiddenList.add(1);
                continue;
            }
            if ((binarynumber & userperiod) == binarynumber) {
                hiddenList.add(1);
                continue;
            }
            if((binarynumber & planpauseperiod) == binarynumber){
                hiddenList.add(1);
                continue;
            }
            if ((binarynumber & appointmentperiod) == binarynumber) {
                hiddenList.add(1);
                continue;
            }
            hiddenList.add(0);
        }
        return hiddenList;
    }


    private List<ContractInfoEntity.ListContractCourseDetailBean> sortContractcourseList(List<ContractInfoEntity.ListContractCourseDetailBean> listcontractcourse, int contractid, int state) {
        List<ContractInfoEntity.ListContractCourseDetailBean> newlistcontractcourse = new ArrayList<>();
        for (int i = 0; i < listcontractcourse.size(); i++) {
            if (listcontractcourse.get(i).get_ContractID() == contractid) {
                listcontractcourse.get(i).setState(state);
                newlistcontractcourse.add(listcontractcourse.get(i));
            }
        }
        return newlistcontractcourse;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        SharedUtil.putString(Constants.DAY_CALENDAR_CLICK,"");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Intent intent = new Intent();
        intent.setAction("com.glory.broadcast.AppointmentCalendar");
        localBroadcastManager.sendBroadcast(intent);
        int sub = (5000 - position) * 7 + TimeUtil.getWeek(RequestUtil.getCurrentTime()) - 1;
        String time = TimeUtil.subDate(sub);
        String[] split = time.split("-");
        tv_date.setText(split[0] + "年"  + split[1] + "月");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
