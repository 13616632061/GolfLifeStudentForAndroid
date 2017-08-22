package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.glorystudent.adapter.LabelGridAdapter;
import com.glorystudent.aliyun.ResuambleUploadSamples;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.entity.CompetityRequestEntity;
import com.glorystudent.entity.CourtLocationEntity;
import com.glorystudent.entity.EventWithSignInfoEntity;
import com.glorystudent.entity.ReleasedRequestEntity;
import com.glorystudent.entity.SignDefinitionEntity;
import com.glorystudent.entity.SingleLocationEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.PhoneFormatCheckUtils;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflibrary.widget.labelgridview.LabelGridView;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.FileToMD5Util;
import com.glorystudent.util.FileUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.PickerViewUtil;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.util.UpLoadImageUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 活动发布页面
 * Created by Administrator on 2017/4/1.
 */

public class EventReleasedActivity extends BaseActivity {
    private static final String TAG = "ReleasedActivity";
    private static final int REQUEST_LOCATION_ACCESS_PERMISSION = 0x100;
    private static final int REQUEST_CALL_ACCESS_PERMISSIONS = 0X101;
    @Bind(R.id.cb_released_pic)
    ConvenientBanner convenientBanner;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_released_image)
    ImageView image;
    @Bind(R.id.iv_released_add_image)
    ImageView addImage;
    @Bind(R.id.et_released_event_name)
    EditText eventName;
    @Bind(R.id.tv_released_begin_time)
    TextView beginTime;
    @Bind(R.id.tv_released_end_time)
    TextView endTime;
    @Bind(R.id.tv_released_kickoff_time)
    TextView kickoffTime;
    @Bind(R.id.ll_released_event_address)
    LinearLayout eventAddressLayout;
    @Bind(R.id.tv_released_event_address)
    TextView eventAddress;
    @Bind(R.id.tv_released_cost_type)
    TextView costType;
    @Bind(R.id.ll_released_event_money)
    LinearLayout eventMoneyLayout;
    @Bind(R.id.et_released_event_money)
    EditText eventMoney;
    @Bind(R.id.ll_released_lineup_money)
    LinearLayout lineupMoneyLayout;
    @Bind(R.id.et_released_lineup_money)
    EditText lineupMoney;
    @Bind(R.id.et_released_people_number)
    EditText peopleNumber;
    @Bind(R.id.tv_released_guest_type)
    TextView guestType;
    @Bind(R.id.ll_released_guest)
    LinearLayout guestLayout;
    @Bind(R.id.et_released_guest_number)
    EditText guestNumber;
    @Bind(R.id.tv_released_guest_cost_type)
    TextView guestCostType;
    @Bind(R.id.ll_released_guest_money)
    LinearLayout guestMoneyLayout;
    @Bind(R.id.et_released_guest_money)
    EditText guestMoney;
    @Bind(R.id.ll_released_guest_lineup_money)
    LinearLayout guestLineupMoneyLayout;
    @Bind(R.id.et_released_guest_lineup_money)
    EditText guestLineupMoney;
    @Bind(R.id.tv_released_password_type)
    TextView passwordType;
    @Bind(R.id.ll_released_password)
    LinearLayout passwordLayout;
    @Bind(R.id.et_released_password)
    EditText password;
    @Bind(R.id.et_released_phone_number)
    EditText phoneNumber;
    @Bind(R.id.gv_released_label)
    LabelGridView labelGridView;
    @Bind(R.id.et_released_event_info)
    EditText eventInfo;
    @Bind(R.id.cb_released_agree)
    CheckBox agree;
    @Bind(R.id.tv_released_service_terms)
    TextView serviceTerms;
    @Bind(R.id.tv_released_service_phone)
    TextView servicePhone;
    @Bind(R.id.tv_released_setting)
    TextView setting;
    @Bind(R.id.tv_released_preview)
    TextView preview;
    @Bind(R.id.tv_released_release)
    TextView release;
    private PickerViewUtil pickerViewUtil;
    private List<String> costTypeOptions;
    private List<String> guestTypeOptions;
    private List<String> passwordTypeOptions;
    private static final int EVENTINFO_CODE = 10;//活动描述详情的请求码
    private static final int ADDRESS_CODE = 11;//球场地址的请求码
    private static final int ADD_IMAGE_CODE = 12;//添加图片的请求码
    private static final int IMAGE_DETAIL_CODE = 13;//图片详情的请求码
    private List<SignDefinitionEntity.ListsigndefinitionBean> labelList;
    private LabelGridAdapter labelGridAdapter;
    private ArrayList<String> imageList;//图片轮播的数据源
    private String rootUrl;//根路径
    public static ReleasedRequestEntity releasedRequestEntity;//发布请求实体
    private boolean editFlag = false;
    private int teamId;
    private List<EventWithSignInfoEntity.ListsigndefinitionBean> listsigndefinition;//编辑时的报名定义信息
    private EventWithSignInfoEntity.EventactivityBean datas;//编辑时的活动详情

    private String provider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;
    private int page = 1;//赛事地址页码
    private OSS oss;
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String testBucket;
    private final static int SUCCESS = 0x123;
    private final static int FAILURE = 0x321;
    private int courtid;//活动地址球场id
    private String servicePhoneStr;//服务电话

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    String url = (String) msg.obj;
                    ReleasedRequestEntity.EventactivityBean.ListeventpicBean bean = new ReleasedRequestEntity.EventactivityBean.ListeventpicBean();
                    bean.setEventactivity_picpath(url);
                    bean.setEventactivity_type("1");//赛事图片
                    bean.setUpload_time(TimeUtil.getUploadingTime(new Date()));
                    releasedRequestEntity.getEventactivity().getListeventpic().add(bean);
                    releasedEvent();
                    break;
                case FAILURE:
                    break;
            }
            return false;
        }
    });
    private int eventActivity_id;

    /**
     * 发布活动
     */
    private void releasedEvent() {
        Log.i(TAG, "releasedEvent: " + releasedRequestEntity.getEventactivity().getListeventpic().size());
        //判断图片是否全部上传
        if (releasedRequestEntity.getEventactivity().getListeventpic().size() == imageList.size()) {
            if (editFlag) {
                //编辑活动
                editReleasedEvent();
            } else {
                //新增活动
                addReleasedEvent();
            }
        }
    }

    /**
     * 修改活动
     */
    private void editReleasedEvent() {
        String json = new Gson().toJson(releasedRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "editReleasedEvent: " + requestJson);
        String url = "/Public/APIPublicEventActivity/EditEventActivity";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(EventReleasedActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        deleteSaveImage();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("MyReleasedEventFragment", "refresh"));
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("LookDetailActivity", "refresh"));
                        finish();
                    } else if (statuscode.equals("2")) {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EventReleasedActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 新增活动
     */
    private void addReleasedEvent() {
        String json = new Gson().toJson(releasedRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "addReleasedEvent: " + requestJson);
        String url = "/Public/APIPublicEventActivity/AddEventActivity";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(EventReleasedActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        deleteSaveImage();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventActivity", "refresh"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
                releasedRequestEntity = null;
                finish();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EventReleasedActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 删除以保存的图片
     */
    private void deleteSaveImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = FileUtil.deleteDir(new File(rootUrl + "/golf/upload/"));
                Log.i(TAG, "run: 删除是否成功" + flag);
            }
        }).start();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_event_released;
    }

    @Override
    protected void init() {
        showLoading();
        initRequestEntity();//初始化请求实体类
        Intent intent = getIntent();
        //判断是否是新增活动
//        boolean addFlag = intent.getBooleanExtra("add", false);
        eventActivity_id = intent.getIntExtra("id", -1);
        if (eventActivity_id == -1) {//新增活动
            datas = null;
            editFlag = false;
        } else {//编辑活动
            editFlag = true;
            requestEventData();
        }
        rootUrl = Environment.getExternalStorageDirectory().getPath();
        pickerViewUtil = new PickerViewUtil(this);
        initTypeOptions();
        initLabel();
        initImageBanner();
//        editEventValue();//编辑活动时调用
        teamId = intent.getIntExtra("teamId", -1);
        if (teamId != -1) {
            releasedRequestEntity.getEventactivity().setEventactivity_teamid(teamId);
        }
    }

    private void requestEventData() {
        showLoading();
        CompetityRequestEntity competityRequestEntity = new CompetityRequestEntity();
        final CompetityRequestEntity.EventactivityBean eventactivityBean = new CompetityRequestEntity.EventactivityBean();
        eventactivityBean.setEventActivity_id(eventActivity_id);
        competityRequestEntity.setEventactivity(eventactivityBean);
        String json = new Gson().toJson(competityRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/Public/APIPublicEventActivity/QueryEventActivityByID";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        EventWithSignInfoEntity eventWithSignInfoEntity = new Gson().fromJson(jo.toString(), EventWithSignInfoEntity.class);
                        datas = eventWithSignInfoEntity.getEventactivity();
                        listsigndefinition = eventWithSignInfoEntity.getListsigndefinition();
                        if (datas != null) {
                            editEventValue();
                        }
                        //获取报名定义信息的方法
                        getLabelData();
                    } else if (statuscode.equals("2")) {//暂无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EventReleasedActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    @Override
    protected void loadDatas() {
        //判断android6.0的情况
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.READ_PHONE_STATE},
                        REQUEST_LOCATION_ACCESS_PERMISSION);
            } else {
                initLocation();//初始化定位
            }
        } else {
            initLocation();//初始化定位
        }
        //获取报名定义信息的方法
        getLabelData();
    }

    /**
     * 获取报名定义信息的方法
     */
    private void getLabelData() {
        if (editFlag && listsigndefinition != null) {//编辑状态时直接呈现
            List<SignDefinitionEntity.ListsigndefinitionBean> list = new ArrayList<>();
            for (EventWithSignInfoEntity.ListsigndefinitionBean bean : listsigndefinition) {
                SignDefinitionEntity.ListsigndefinitionBean listsigndefinitionBean = new SignDefinitionEntity.ListsigndefinitionBean();
                listsigndefinitionBean.setSign_state(bean.getSign_state());
                listsigndefinitionBean.setSdid(bean.getSdid());
                listsigndefinitionBean.setSign_binarynumber(bean.getSign_binarynumber());
                listsigndefinitionBean.setSign_describe(bean.getSign_describe());
                list.add(listsigndefinitionBean);
            }
            labelList.addAll(list);
            //编辑时获取已有的报名列表
            for (int i = 2; i < labelList.size(); i++) {
                if ((datas.getEventactivity_binarynumber() & labelList.get(i).getSign_binarynumber()) == labelList.get(i).getSign_binarynumber()) {
                    labelList.get(i).setSign_state(0);
                }
            }
            labelGridAdapter.setDatas(labelList);
            dismissLoading();
        } else if (!editFlag) { //新增时请求网络获取定义信息
            String requestJson = RequestUtil.getSignDefinition(this);
            Log.i(TAG, "loadDatas: " + requestJson);
            String url = "/api/APISignDefinition/QuerySignDefinition";
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                @Override
                public void parseDatas(String json) {
                    try {
                        JSONObject jo = new JSONObject(json);
                        String statuscode = jo.getString("statuscode");
                        String statusmessage = jo.getString("statusmessage");
                        if ("1".equals(statuscode)) {
                            SignDefinitionEntity signDefinitionEntity = new Gson().fromJson(jo.toString(), SignDefinitionEntity.class);
                            List<SignDefinitionEntity.ListsigndefinitionBean> data = signDefinitionEntity.getListsigndefinition();
                            if (data != null) {
                                labelList.addAll(data);
                                Log.i(TAG, "parseDatas: 长度：" + labelList.size());
                                labelGridAdapter.setDatas(labelList);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dismissLoading();
                }

                @Override
                public void requestFailed() {
                    dismissLoading();
                    Toast.makeText(EventReleasedActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            }).getEntityData(this, url, requestJson);
        }
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
        provider = judgeProvider(locationManager);
        if (provider != null) {//有位置提供器的情况
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                if (editFlag) {
                    dismissLoading();
                    return;
                }
                //获取附近的球场地址信息，并显示第一条数据
                getNearbyCourt();
            } else {
                Log.d(TAG, "initLocation: 无法获得当前位置");
            }
        } else {
            //不存在位置提供器的情况
        }
    }

    /**
     * 获取附近的球场地址信息，并显示第一条数据
     */
    private void getNearbyCourt() {
        Log.d(TAG, "getNearbyCourt" + location.getLatitude() + " " + location.getLongitude());
        SingleLocationEntity singleLocationEntity = new SingleLocationEntity();
        SingleLocationEntity.CourtBean courtBean = new SingleLocationEntity.CourtBean();
        courtBean.setCourt_latitude(location.getLatitude() + "");
        courtBean.setCourt_longitude(location.getLongitude() + "");
        singleLocationEntity.setCourt(courtBean);
        singleLocationEntity.setPage(page);
        String request = new Gson().toJson(singleLocationEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/public/APIPublicCourt/QueryCourt";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    CourtLocationEntity courtLocationEntity = new Gson().fromJson(jo.toString(), CourtLocationEntity.class);
                    List<CourtLocationEntity.ListCourtBean> listCourt = courtLocationEntity.getListCourt();
                    if (listCourt != null && listCourt.size() > 0) {
                        String court_name = listCourt.get(0).getCourt_name();
                        courtid = listCourt.get(0).getCourt_id();
                        eventAddress.setText(court_name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EventReleasedActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 编辑活动的初始化
     */
    private void editEventValue() {
        //如果是编辑活动就取出datas赋值
        if (editFlag && datas != null) {
            Log.i(TAG, "editEventValue: 开始赋值");
            releasedRequestEntity.getEventactivity().setEventActivity_id(datas.getEventActivity_id());
            releasedRequestEntity.getEventactivity().setEventActivity_AddressID(datas.getEventActivity_AddressID());
            releasedRequestEntity.getEventactivity().setEventactivity_publisherid(datas.getEventactivity_publisherid());

            eventName.setText(datas.getEventactivity_name());

            Date beginDate = TimeUtil.getStandardDate(datas.getEventactivity_begintime());
            beginTime.setText(TimeUtil.getReleasedTime2(beginDate, "开始"));
            releasedRequestEntity.getEventactivity().setEventactivity_begintime(TimeUtil.getUploadingTime(beginDate));
            Date endDate = TimeUtil.getStandardDate(datas.getEventactivity_endtime());
            endTime.setText(TimeUtil.getReleasedTime2(endDate, "结束"));
            releasedRequestEntity.getEventactivity().setEventactivity_endtime(TimeUtil.getUploadingTime(endDate));
            Date kickoffDate = TimeUtil.getStandardDate(datas.getEventactivity_kickofftime());
            kickoffTime.setText(TimeUtil.getReleasedTime(kickoffDate, "开球"));
            releasedRequestEntity.getEventactivity().setEventactivity_kickofftime(TimeUtil.getUploadingTime(kickoffDate));

            eventAddress.setText(datas.getCoachgroupname());
            if (datas.getEventactivity_costtype().equals("2")) {
                //线下支付
                eventMoneyLayout.setVisibility(View.VISIBLE);
                lineupMoneyLayout.setVisibility(View.GONE);
                costType.setText("线下支付");
                eventMoney.setText(datas.getEventactivity_cost() + "");
            } else if (datas.getEventactivity_costtype().equals("3")) {
                //线上支付
                eventMoneyLayout.setVisibility(View.VISIBLE);
                lineupMoneyLayout.setVisibility(View.VISIBLE);
                costType.setText("线上支付");
                eventMoney.setText(datas.getEventactivity_cost() + "");
                lineupMoney.setText(datas.getEventactivity_prepayment() + "");
            } else if (datas.getEventactivity_costtype().equals("1")) {
                //免费
                eventMoneyLayout.setVisibility(View.GONE);
                lineupMoneyLayout.setVisibility(View.GONE);
                costType.setText("免费");
            }
            if (datas.getEventactivity_number() > 0) {
                peopleNumber.setText(datas.getEventactivity_number() + "");
            }
            if (datas.isEventactivity_ifbringguests()) {
                //允许携带嘉宾
                guestType.setText("允许");
                guestLayout.setVisibility(View.VISIBLE);
                if (datas.getEventactivity_bringguestsnum() > 0) {
                    guestNumber.setText(datas.getEventactivity_bringguestsnum() + "");
                }
                if (datas.getEventactivity_guestcosttype().equals("1")) {
                    //嘉宾费用免费
                    guestMoneyLayout.setVisibility(View.GONE);
                    guestLineupMoneyLayout.setVisibility(View.GONE);
                    guestCostType.setText("免费");
                } else if (datas.getEventactivity_guestcosttype().equals("2")) {
                    //嘉宾费用线下支付
                    guestMoneyLayout.setVisibility(View.VISIBLE);
                    guestLineupMoneyLayout.setVisibility(View.GONE);
                    guestCostType.setText("线下支付");
                    guestMoney.setText(datas.getEventactivity_guestscost() + "");
                } else if (datas.getEventactivity_guestcosttype().equals("3")) {
                    //嘉宾费用线上支付
                    guestMoneyLayout.setVisibility(View.VISIBLE);
                    guestLineupMoneyLayout.setVisibility(View.VISIBLE);
                    guestCostType.setText("线上支付");
                    guestMoney.setText(datas.getEventactivity_guestscost() + "");
                    guestLineupMoney.setText(datas.getEventactivity_guestprepayment() + "");
                }
            } else {
                //不允许
                guestLayout.setVisibility(View.GONE);
                guestType.setText("不允许");
            }
            if (datas.isEventactivity_ifpublicly()) {
                passwordLayout.setVisibility(View.GONE);
                passwordType.setText("无");
            } else {
                passwordLayout.setVisibility(View.VISIBLE);
                passwordType.setText("有");
                password.setText(datas.getEventactivity_pwd());
            }
            phoneNumber.setText(datas.getEventactivity_publishertel());
            eventInfo.setText(datas.getEventactivity_detail());
            //赛事图片
            imageList.clear();
            for (EventWithSignInfoEntity.EventactivityBean.ListeventpicBean bean : datas.getListeventpic()) {
                imageList.add(bean.getEventactivity_picpath());
            }
            UpdateBanner();
            //以下是高级设置
            releasedRequestEntity.getEventactivity().setEventactivity_ifshowphotowall(datas.isEventactivity_ifshowphotowall());
            releasedRequestEntity.getEventactivity().setEventactivity_ifshowsignname(datas.isEventactivity_ifshowsignname());
            if (datas.getEventactivity_organizer() != null) {
                releasedRequestEntity.getEventactivity().setEventactivity_organizer(datas.getEventactivity_organizer());
            }
            if (datas.getEventactivity_signupbegintime() != null) {
                Date signUpBeginDate = TimeUtil.getStandardDate(datas.getEventactivity_signupbegintime());
                releasedRequestEntity.getEventactivity().setEventactivity_signupbegintime(TimeUtil.getUploadingTime(signUpBeginDate));
            }
            if (datas.getEventactivity_signupendtime() != null) {
                Date signUpEndDate = TimeUtil.getStandardDate(datas.getEventactivity_signupendtime());
                releasedRequestEntity.getEventactivity().setEventactivity_signupendtime(TimeUtil.getUploadingTime(signUpEndDate));
            }
        }
    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "请手动打开安全中心定位权限", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 初始化顶部轮播
     */
    private void initImageBanner() {
        imageList = new ArrayList<>();
        String url = rootUrl + "/golf/upload/" + "banner_default" + ".png";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.competition_banner_1);
        UpLoadImageUtil.saveBitmapFile(bitmap, url);
        imageList.add(url);
        convenientBanner.setPages(new CBViewHolderCreator<LoaclViewHolder>() {
            @Override
            public LoaclViewHolder createHolder() {
                return new LoaclViewHolder();
            }
        }, imageList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.shape_min_whitecircle, R.drawable.shape_min_orangecircle})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).startTurning(3000)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(EventReleasedActivity.this, ImageDetailActivity.class);
                        intent.putExtra("url", imageList.get(position));
                        intent.putExtra("position", position);
                        startActivityForResult(intent, IMAGE_DETAIL_CODE);
                    }
                });
        UpdateBanner();
    }

    /**
     * TODO 自动轮播类
     * 自动轮播需要的类
     */
    public class LoaclViewHolder implements Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            GlideUtil.loadImageView(context, data, imageView);
        }

    }

    /**
     * 初始化请求的实体
     */
    private void initRequestEntity() {
        releasedRequestEntity = new ReleasedRequestEntity();
        ReleasedRequestEntity.EventactivityBean eventactivity = new ReleasedRequestEntity.EventactivityBean();
        releasedRequestEntity.setEventactivity(eventactivity);
        List<ReleasedRequestEntity.EventactivityBean.ListeventpicBean> listeventpic = new ArrayList<>();
        releasedRequestEntity.getEventactivity().setListeventpic(listeventpic);
        //赋初始值
        eventactivity.setEventactivity_ifshowphotowall(true);
        eventactivity.setEventactivity_ifshowsignname(false);
        eventactivity.setEventactivity_type("2");

        Date tomorrowDate = TimeUtil.getTomorrowDate();
        UpdateTime(tomorrowDate);

        eventactivity.setEventactivity_organizertel(SharedUtil.getString(Constants.PHONE_NUMBER));
        phoneNumber.setText(SharedUtil.getString(Constants.PHONE_NUMBER));
    }

    /**
     * 更新结束和开球时间
     *
     * @param date
     */
    private void UpdateTime(Date date) {
        long newTime = date.getTime() + 12 * 60 * 60 * 1000;
        Date kickoffDate = new Date(newTime);
        releasedRequestEntity.getEventactivity().setEventactivity_begintime(TimeUtil.getUploadingTime(date));
        releasedRequestEntity.getEventactivity().setEventactivity_endtime(TimeUtil.getUploadingTime(date));
        releasedRequestEntity.getEventactivity().setEventactivity_kickofftime(TimeUtil.getUploadingTime(kickoffDate));
        beginTime.setText(TimeUtil.getReleasedTime2(date, "开始"));
        endTime.setText(TimeUtil.getReleasedTime2(date, "结束"));
        kickoffTime.setText(TimeUtil.getReleasedTime(kickoffDate, "开球"));
    }

    /**
     * 初始化报名标签
     */
    private void initLabel() {
        labelList = new ArrayList<>();
        SignDefinitionEntity.ListsigndefinitionBean listsigndefinitionBean0 = new SignDefinitionEntity.ListsigndefinitionBean();
        listsigndefinitionBean0.setSdid(0);
//        listsigndefinitionBean0.setSign_binarynumber(1);
        listsigndefinitionBean0.setSign_describe("姓名");
        listsigndefinitionBean0.setSign_state(0);
        labelList.add(listsigndefinitionBean0);
        SignDefinitionEntity.ListsigndefinitionBean listsigndefinitionBean1 = new SignDefinitionEntity.ListsigndefinitionBean();
        listsigndefinitionBean1.setSdid(1);
//        listsigndefinitionBean1.setSign_binarynumber(2);
        listsigndefinitionBean1.setSign_describe("手机");
        listsigndefinitionBean1.setSign_state(0);
        labelList.add(listsigndefinitionBean1);
        labelGridAdapter = new LabelGridAdapter(this);
        labelGridView.setAdapter(labelGridAdapter);
    }

    /**
     * 初始化下拉选择标签
     */
    private void initTypeOptions() {
        costTypeOptions = new ArrayList<String>();
        costTypeOptions.add("免费");
        costTypeOptions.add("线下支付");
        costTypeOptions.add("线上支付");
        guestTypeOptions = new ArrayList<String>();
        guestTypeOptions.add("不允许");
        guestTypeOptions.add("允许");
        passwordTypeOptions = new ArrayList<String>();
        passwordTypeOptions.add("无");
        passwordTypeOptions.add("有");
    }

    @OnClick({R.id.back, R.id.iv_released_image, R.id.iv_released_add_image, R.id.tv_released_begin_time, R.id.tv_released_end_time,
            R.id.tv_released_kickoff_time, R.id.ll_released_event_address, R.id.tv_released_cost_type,
            R.id.tv_released_guest_type, R.id.tv_released_guest_cost_type, R.id.tv_released_password_type,
            R.id.et_released_event_info, R.id.tv_released_service_terms, R.id.tv_released_service_phone,
            R.id.tv_released_setting, R.id.tv_released_preview, R.id.tv_released_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                releasedRequestEntity = null;
                finish();
                break;
            case R.id.iv_released_image:
                //点击打开全屏图片
                Toast.makeText(this, "打开了页面", Toast.LENGTH_SHORT).show();
                startImageDetail();
                break;
            case R.id.iv_released_add_image:
                //添加顶部轮播图片
                addImage2Banner();
                break;
            case R.id.tv_released_begin_time:
                //开始时间
                setBeginTime();
                break;
            case R.id.tv_released_end_time:
                //结束时间
                setEndTime();
                break;
            case R.id.tv_released_kickoff_time:
                //开球时间
                setKickoffTime();
                break;
            case R.id.ll_released_event_address:
                //地址
                startEventAddress();
                break;
            case R.id.tv_released_cost_type:
                //费用类型
                setCostType();
                break;
            case R.id.tv_released_guest_type:
                //嘉宾类型
                setGuestType();
                break;
            case R.id.tv_released_guest_cost_type:
                //嘉宾价格类型
                setGuestCostType();
                break;
            case R.id.tv_released_password_type:
                //密码类型
                setPasswordType();
                break;
            case R.id.et_released_event_info:
                //活动描述详情
                startEventInfo();
                break;
            case R.id.tv_released_service_terms:
                //服务条款
                startServiceTerms();
                break;
            case R.id.tv_released_service_phone:
                //客服电话
                callServicePhone();
                break;
            case R.id.tv_released_setting:
                //高级设置
                startSetting();
                break;
            case R.id.tv_released_preview:
                //预览
                startPreview();
                break;
            case R.id.tv_released_release:
                //发布
                acquireReleasedData();
                break;
        }
    }

    /**
     * 打开服务条款页面
     */
    private void startServiceTerms() {
        Intent intent = new Intent(this, ServiceTermsActivity.class);
        startActivity(intent);
    }

    /**
     * 打开预览页面
     */
    private void startPreview() {
        acquirePreviewData();//获取已填的数据
//        Map<String, ReleasedRequestEntity> map = new HashMap<>();
//        map.put("ReleasedPreviewActivity", releasedRequestEntity);
//        EventBus.getDefault().postSticky(map);
        Intent intent = new Intent(this, ReleasedPreviewActivity.class);
        intent.putStringArrayListExtra("imageList", imageList);
        startActivity(intent);
    }

    /**
     * 打开高级设置页面
     */
    private void startSetting() {
//        Map<String, ReleasedRequestEntity> map = new HashMap<>();
//        map.put("ReleasedSettingActivity", releasedRequestEntity);
//        EventBus.getDefault().postSticky(map);
        Intent intent = new Intent(this, ReleasedSettingActivity.class);
        startActivity(intent);
    }

    /**
     * 点击拨打客服电话
     */
    private void callServicePhone() {
        servicePhoneStr = servicePhone.getText().toString();
        //拨打电话
        Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
            @Override
            public void onSure() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(EventReleasedActivity.this, android.Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EventReleasedActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_ACCESS_PERMISSIONS);
                    }
                } else {
                    callPhone(servicePhoneStr);
                }
            }

            @Override
            public void onCancel() {

            }
        }).showDialog(this, "拨打电话", "你确定拨打该成员电话吗?", "拨打");
    }

    /**
     * 拨打电话的方法
     *
     * @param phoneStr
     */
    private void callPhone(String phoneStr) {
        //意图：想干什么事
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + phoneStr));
        try {
            //开启系统拨号器
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(EventReleasedActivity.this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 开启活动详情描述页面
     */
    private void startEventInfo() {
        Intent intent = new Intent(this, ReleasedEventInfoActivity.class);
        intent.putExtra("text", eventInfo.getText().toString());
        startActivityForResult(intent, EVENTINFO_CODE);
    }

    /**
     * 设置密码类型
     */
    private void setPasswordType() {
        pickerViewUtil.showOptionsPickerView("公开可见", passwordTypeOptions, new PickerViewUtil.OptionsLisener() {
            @Override
            public void onSubmit(int options1, int options2, int options3, View v) {
                passwordType.setText(passwordTypeOptions.get(options1));
                switch (passwordType.getText().toString()) {
                    case "无":
                        passwordLayout.setVisibility(View.GONE);
                        password.setText("");
                        break;
                    case "有":
                        passwordLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置嘉宾费用类型
     */
    private void setGuestCostType() {
        pickerViewUtil.showOptionsPickerView("陪同嘉宾是否收费", costTypeOptions, new PickerViewUtil.OptionsLisener() {
            @Override
            public void onSubmit(int options1, int options2, int options3, View v) {
                guestCostType.setText(costTypeOptions.get(options1));
                switch (guestCostType.getText().toString()) {
                    case "免费":
                        guestMoneyLayout.setVisibility(View.GONE);
                        guestLineupMoneyLayout.setVisibility(View.GONE);
                        guestMoney.setText("");
                        guestLineupMoney.setText("");
                        break;
                    case "线下支付":
                        guestMoneyLayout.setVisibility(View.VISIBLE);
                        guestLineupMoneyLayout.setVisibility(View.GONE);
                        guestLineupMoney.setText("");
                        break;
                    case "线上支付":
                        guestMoneyLayout.setVisibility(View.VISIBLE);
                        guestLineupMoneyLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置嘉宾类型
     */
    private void setGuestType() {
        pickerViewUtil.showOptionsPickerView("允许陪同嘉宾", guestTypeOptions, new PickerViewUtil.OptionsLisener() {
            @Override
            public void onSubmit(int options1, int options2, int options3, View v) {
                guestType.setText(guestTypeOptions.get(options1));
                switch (guestType.getText().toString()) {
                    case "不允许":
                        guestLayout.setVisibility(View.GONE);
                        guestNumber.setText("");
                        break;
                    case "允许":
                        guestLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置费用类型
     */
    private void setCostType() {
//        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//
//            }
//        })
//                .setSubmitText("完成")
//                .setLineSpacingMultiplier(9f)
//                .setTitleSize(14)
//                .setContentTextSize(14)
//                .setSubCalSize(16)
//                .setLineSpacingMultiplier(2.0f)
//                .setTitleText("收费类型")
//                .setCancelColor(Color.BLACK)
//                .setSubmitColor(Color.BLACK)
//                .setTitleColor(Color.BLACK)
//                .isCenterLabel(false)
//                .setSelectOptions(1, 0, 0)  //设置默认选中项
//                .setOutSideCancelable(false)//点击外部dismiss default true
//                .build();
//        pvOptions.setPicker(costTypeOptions);
//        pvOptions.show();
        pickerViewUtil.showOptionsPickerView("收费类型", costTypeOptions, new PickerViewUtil.OptionsLisener() {
            @Override
            public void onSubmit(int options1, int options2, int options3, View v) {
                costType.setText(costTypeOptions.get(options1));
                switch (costType.getText().toString()) {
                    case "免费":
                        eventMoneyLayout.setVisibility(View.GONE);
                        lineupMoneyLayout.setVisibility(View.GONE);
                        eventMoney.setText("");
                        lineupMoney.setText("");
                        break;
                    case "线下支付":
                        eventMoneyLayout.setVisibility(View.VISIBLE);
                        lineupMoneyLayout.setVisibility(View.GONE);
                        lineupMoney.setText("");
                        break;
                    case "线上支付":
                        eventMoneyLayout.setVisibility(View.VISIBLE);
                        lineupMoneyLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置赛事地址
     */
    private void startEventAddress() {
        Intent intent = new Intent(this, ChoosePitchActivity.class);
        if (location != null) {
            String longitude = location.getLongitude() + "";
            String latitude = location.getLatitude() + "";
            intent.putExtra("longitude", longitude);
            intent.putExtra("latitude", latitude);
        }
        startActivityForResult(intent, ADDRESS_CODE);
    }

    /**
     * 设置开球时间
     */
    private void setKickoffTime() {
        Calendar currentCalendar = getCalendarDate(releasedRequestEntity.getEventactivity().getEventactivity_kickofftime());
        pickerViewUtil.showTimePickerView("开球时间", currentCalendar, new PickerViewUtil.TimeLisener() {
            @Override
            public void onSubmit(Date date, View v) {
                kickoffTime.setText(TimeUtil.getReleasedTime(date, "开球"));
                releasedRequestEntity.getEventactivity().setEventactivity_kickofftime(TimeUtil.getUploadingTime(date));
            }
        });
    }

    /**
     * 设置赛事结束时间
     */
    private void setEndTime() {
        Calendar currentCalendar = getCalendarDate(releasedRequestEntity.getEventactivity().getEventactivity_endtime());
        pickerViewUtil.showTimePickerView2("活动结束时间", currentCalendar, new PickerViewUtil.TimeLisener() {
            @Override
            public void onSubmit(Date date, View v) {
                endTime.setText(TimeUtil.getReleasedTime2(date, "结束"));
                releasedRequestEntity.getEventactivity().setEventactivity_endtime(TimeUtil.getUploadingTime(date));
            }
        });
    }

    /**
     * 设置赛事开始时间
     */
    private void setBeginTime() {
//        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
////                        tvTime.setText(getTime(date));
//                Log.i("tag", "活动发布时间选择：" + date.toString());
//            }
//        })
//                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
//                .setLineSpacingMultiplier(2.0f)
//                .setSubmitText("完成")
//                .setTitleSize(14)
//                .setContentSize(14)
//                .setSubCalSize(16)
//                .setTitleText("活动开始时间")
//                .setOutSideCancelable(false)
//                .isCyclic(false)
//                .setCancelColor(Color.BLACK)
//                .setSubmitColor(Color.BLACK)
//                .setTitleColor(Color.BLACK)
//                .setRange(startDate.get(Calendar.YEAR), startDate.get(Calendar.YEAR) + 100)
//                .setRangDate(startDate, endDate)
//                .isCenterLabel(false)
//                .build();
//        pvTime.show();
        Calendar currentCalendar = getCalendarDate(releasedRequestEntity.getEventactivity().getEventactivity_begintime());
        pickerViewUtil.showTimePickerView2("活动开始时间", currentCalendar, new PickerViewUtil.TimeLisener() {
            @Override
            public void onSubmit(Date date, View v) {
//                beginTime.setText(TimeUtil.getReleasedTime(date, "开始"));
//                releasedRequestEntity.getEventactivity().setEventactivity_begintime(TimeUtil.getUploadingTime(date));
                UpdateTime(date);
            }
        });
    }

    @NonNull
    private Calendar getCalendarDate(String currentTime) {
        Date currentDate = TimeUtil.getDateFromUploading(currentTime);
        Calendar currentCalendar = new GregorianCalendar();
        currentCalendar.setTime(currentDate);
        return currentCalendar;
    }

    /**
     * 点击添加轮播图片
     */
    private void addImage2Banner() {
        Intent intent = new Intent(this, ReleasedAddImageActivity.class);
        startActivityForResult(intent, ADD_IMAGE_CODE);
    }

    /**
     * 开启图片详情页面
     */
    private void startImageDetail() {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("url", imageList.get(0));
        startActivityForResult(intent, IMAGE_DETAIL_CODE);
    }

    /**
     * 发布执行的方法
     */
    private void acquireReleasedData() {
        //检查赛事名称是否为空
        if (checkEventName()) return;
        //检查赛事时间是否合法
        if (checkCompetitonTime()) return;
        //检查赛事地址是否为空
        String eventAddressStr = checkEventAddress();
        if (eventAddressStr == null) return;
        //检查赛事费用
        if (checkEventMoney()) return;
        //检查参与人数
        if (checkNumber()) return;
        //检查嘉宾
        if (checkGuest()) return;
        //检查访问密码
        if (checkPassword()) return;
        //检查联系电话
        if (checkPhoneNumber()) return;
        //获取报名定义列
        getSignDefinitionNumber();
        //获取活动详情描述
        getEventInfoDetail();
        //检查是否同意服务条款
        if (checkAgree()) return;
        //上传图片
        //清空已有图片
        releasedRequestEntity.getEventactivity().getListeventpic().clear();
        loadOss();
    }

    private void loadOss() {
        showLoading();
        AliyunRequestEntity aliyunOSS = RequestUtil.getAliyunOSS();
        if (aliyunOSS != null) {
            getKeyId(aliyunOSS);
        } else {
            OkGoRequest.getOkGoRequest().setOnGetOssListener(new OkGoRequest.OnGetOssListener() {
                @Override
                public void getOssSucceed(AliyunRequestEntity aliyunRequestEntity) {
                    getKeyId(aliyunRequestEntity);
                }
            }).getAliyunOSS(this);
        }
    }

    public void getKeyId(AliyunRequestEntity aliyunRequestEntity) {
        List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
        accessKeyId = listsettingvalue.get(0).getSettingvalue();
        accessKeySecret = listsettingvalue.get(1).getSettingvalue();
        endpoint = "https://" + listsettingvalue.get(2).getSettingvalue() + ".aliyuncs.com";
        testBucket = listsettingvalue.get(3).getSettingvalue();
        setOss();
    }

    public void setOss() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        for (int i = 0; i < imageList.size(); i++) {
            Log.d(TAG, "setOss: --->路径" + imageList.get(i));
            if (imageList.get(i).contains("http")) {
                //已经上传过了，不用上传了
                ReleasedRequestEntity.EventactivityBean.ListeventpicBean bean = new ReleasedRequestEntity.EventactivityBean.ListeventpicBean();
                bean.setEventactivity_picpath(imageList.get(i));
                bean.setEventactivity_type("1");//赛事图片
                bean.setUpload_time(TimeUtil.getUploadingTime(new Date()));
                releasedRequestEntity.getEventactivity().getListeventpic().add(bean);
                releasedEvent();
            } else {
                //上传本地图片
                upLoadImages(imageList.get(i));
            }
        }
    }

    private void upLoadImages(String path) {
        String fileMD5 = FileToMD5Util.getFileMD5(new File(path));
        String uid = SharedUtil.getString(Constants.USER_ID);
        String userId = uid.replace("/", "");
        final String uploadFilePath = path;
        final String uploadObject = userId + "/images/" + fileMD5 + ".png";
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResuambleUploadSamples up = new ResuambleUploadSamples(oss, testBucket, uploadObject, uploadFilePath, new ProgressBar(EventReleasedActivity.this))
                        .setOnUpLoadVideoListener(new ResuambleUploadSamples.OnUpLoadVideoListener() {

                            @Override
                            public void onUpSuccess(String url) {
                                Log.d(TAG, "onUpSuccess: --->" + "上传成功：" + url);
                                Message.obtain(handler, SUCCESS, url).sendToTarget();
                            }

                            @Override
                            public void onUpFailure() {
                                Log.d(TAG, "onUpFailure: --->" + "上传失败");
                                Message.obtain(handler, FAILURE, "上传失败").sendToTarget();
                            }
                        });


                up.setOnProgressListener(new ResuambleUploadSamples.OnProgressListener() {
                    @Override
                    public void onProgress(long sum, long current) {
                        final long ss = sum;
                        final long cu = current;
                        Log.d(TAG, "onProgress: " + ss + " " + cu);
                    }
                });

                up.resumableUploadWithRecordPathSetting();

            }
        }).start();
    }

    /**
     * 检查是否同意服务条款
     *
     * @return
     */
    private boolean checkAgree() {
        if (!agree.isChecked()) {
            Toast.makeText(this, "请阅读并同意服务条款", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 获取活动详情描述
     */
    private void getEventInfoDetail() {
        String eventInfoStr = eventInfo.getText().toString().trim();
        if (!eventInfoStr.isEmpty()) {
            releasedRequestEntity.getEventactivity().setEventactivity_detail(eventInfoStr);
        }
    }

    /**
     * 获取报名定义列
     */
    private void getSignDefinitionNumber() {
        int sum = 0;
        for (SignDefinitionEntity.ListsigndefinitionBean listsigndefinitionBean : labelList) {
            if (listsigndefinitionBean.getSign_state() == 0) {
                sum += listsigndefinitionBean.getSign_binarynumber();
            }
        }
        Log.i("tag", "sum=" + sum);
        releasedRequestEntity.getEventactivity().setEventactivity_binarynumber(sum);
    }

    /**
     * 检查手机号是否匹配
     *
     * @return
     */
    private boolean checkPhoneNumber() {
        String phoneStr = phoneNumber.getText().toString().trim();
        if ("".equals(phoneStr)) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        //手机号码的正则表达式
        if (!PhoneFormatCheckUtils.isPhoneLegal(phoneStr)) {
            Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return true;
        }
        releasedRequestEntity.getEventactivity().setEventactivity_organizertel(phoneStr);
        return false;
    }

    /**
     * 检查密码是否合法
     *
     * @return
     */
    private boolean checkPassword() {
        String passwordTypeStr = passwordType.getText().toString();
        if ("无".equals(passwordTypeStr)) {
            releasedRequestEntity.getEventactivity().setEventactivity_ifpublicly(true);
        } else if ("有".equals(passwordTypeStr)) {
            releasedRequestEntity.getEventactivity().setEventactivity_ifpublicly(false);
            String passwordStr = password.getText().toString().trim();
            if ("".equals(passwordStr)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return true;
            } else if (passwordStr.length() < 4) {
                Toast.makeText(this, "密码必须是4位", Toast.LENGTH_SHORT).show();
                return true;
            }
            releasedRequestEntity.getEventactivity().setEventactivity_pwd(passwordStr);
        }
        return false;
    }

    /**
     * 检查嘉宾是否合法
     *
     * @return
     */
    private boolean checkGuest() {
        String guestTypeStr = guestType.getText().toString();
        if ("不允许".equals(guestTypeStr)) {
            releasedRequestEntity.getEventactivity().setEventactivity_ifbringguests(false);
        } else if ("允许".equals(guestTypeStr)) {
            releasedRequestEntity.getEventactivity().setEventactivity_ifbringguests(true);
            String guestNumberStr = guestNumber.getText().toString().trim();
            //检查嘉宾人数
            if (!"".equals(guestNumberStr)) {
                releasedRequestEntity.getEventactivity().setEventactivity_bringguestsnum(Integer.parseInt(guestNumberStr));
            }
            //检查陪同嘉宾是否收费
            String guestCostTypeStr = guestCostType.getText().toString();
            if ("免费".equals(guestCostTypeStr)) {
                releasedRequestEntity.getEventactivity().setEventactivity_guestcosttype("1");
            } else {
                String guestMoneyStr = guestMoney.getText().toString().trim();
                if ("".equals(guestMoneyStr)) {
                    Toast.makeText(this, "嘉宾费用必须大于0", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    releasedRequestEntity.getEventactivity().setEventactivity_guestscost(new BigDecimal(guestMoneyStr).setScale(4));
                }
                if ("线下支付".equals(guestCostTypeStr)) {
                    releasedRequestEntity.getEventactivity().setEventactivity_guestcosttype("2");
                } else if ("线上支付".equals(guestCostTypeStr)) {
                    releasedRequestEntity.getEventactivity().setEventactivity_guestcosttype("3");
                    String guestLineupMoneyStr = guestLineupMoney.getText().toString().trim();
                    if ("".equals(guestLineupMoneyStr)) {
                        Toast.makeText(this, "嘉宾线上费用必须大于0", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        releasedRequestEntity.getEventactivity().setEventactivity_guestprepayment(new BigDecimal(guestLineupMoneyStr).setScale(4));
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查参与人数
     *
     * @return
     */
    private boolean checkNumber() {
        String peopleNumberStr = peopleNumber.getText().toString().trim();
        if (!"".equals(peopleNumberStr) && Integer.parseInt(peopleNumberStr) > 0) {
            releasedRequestEntity.getEventactivity().setEventactivity_number(Integer.parseInt(peopleNumberStr));
        }
        return false;
    }

    /**
     * 检查赛事活动经费是否合法
     *
     * @return
     */
    private boolean checkEventMoney() {
        String costTypeStr = costType.getText().toString();
        if ("免费".equals(costTypeStr)) {
            releasedRequestEntity.getEventactivity().setEventactivity_costtype("1");
        } else {
            String eventMoneyStr = eventMoney.getText().toString().trim();
            if ("".equals(eventMoneyStr)) {
                Toast.makeText(this, "活动费用必须大于0", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                releasedRequestEntity.getEventactivity().setEventactivity_cost(new BigDecimal(eventMoneyStr).setScale(4));
            }
            if ("线下支付".equals(costTypeStr)) {
                releasedRequestEntity.getEventactivity().setEventactivity_costtype("2");
            } else if ("线上支付".equals(costTypeStr)) {
                releasedRequestEntity.getEventactivity().setEventactivity_costtype("3");
                String lineupMoneyStr = lineupMoney.getText().toString().trim();
                if ("".equals(lineupMoneyStr)) {
                    Toast.makeText(this, "线上支付金额不能为空", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    releasedRequestEntity.getEventactivity().setEventactivity_prepayment(new BigDecimal(lineupMoneyStr).setScale(4));
                }
            }
        }
        return false;
    }

    /**
     * 检查赛事地址是否为空
     *
     * @return
     */
    @Nullable
    private String checkEventAddress() {
        String eventAddressStr = eventAddress.getText().toString();
        if ("".equals(eventAddressStr)) {
            Toast.makeText(this, "赛事地址不能为空", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            releasedRequestEntity.getEventactivity().setEventActivity_AddressID(courtid);
//            releasedRequestEntity.getEventactivity().setEventactivity_address(eventAddressStr);
        }
        return eventAddressStr;
    }

    /**
     * 检查赛事时间是否合法
     *
     * @return
     */
    private boolean checkCompetitonTime() {
        String b = beginTime.getText().toString();
        if ("".equals(b)) {
            Toast.makeText(this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        String beginTimeStr = b.substring(0, b.length() - 3);//String对汉字的长度计算为：一个汉字长度为1，但是占两个字符
        Long bs = TimeUtil.getMilliseconds2(beginTimeStr);
        String e = endTime.getText().toString();
        if ("".equals(e)) {
            Toast.makeText(this, "结束时间不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        String endTimeStr = e.substring(0, e.length() - 3);
        Long es = TimeUtil.getMilliseconds2(endTimeStr) + 24 * 60 * 60 * 1000 - 1000;//结束时间为yyyy-MM-dd 23:59:59
        if (es < bs) {
            Toast.makeText(this, "结束时间必须在开始时间之后", Toast.LENGTH_SHORT).show();
            return true;
        }
        String k = kickoffTime.getText().toString();
        if ("".equals(k)) {
            Toast.makeText(this, "开球时间不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        String kickoffTimeStr = k.substring(0, k.length() - 3);
        Long ks = TimeUtil.getMilliseconds(kickoffTimeStr);
        if (ks < bs) {
            Toast.makeText(this, "开球时间不能在开始时间之前", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (ks > es) {
            Toast.makeText(this, "开球时间不能在结束时间之后", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 检查活动名称是否为空
     *
     * @return
     */
    private boolean checkEventName() {
        String eventNameStr = eventName.getText().toString().trim();
        if ("".equals(eventNameStr)) {
            Toast.makeText(this, "赛事名称不能为空", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            releasedRequestEntity.getEventactivity().setEventactivity_name(eventNameStr);
        }
        return false;
    }

    /**
     * 点击预览执行的方法
     */
    private void acquirePreviewData() {
        releasedRequestEntity.getEventactivity().setEventactivity_name(eventName.getText().toString().trim());
        releasedRequestEntity.getEventactivity().setEventactivity_address(eventAddress.getText().toString());
        releasedRequestEntity.getEventactivity().setEventactivity_detail(eventInfo.getText().toString());
    }

    /**
     * 带返回值的跳转页面返后执行的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == EVENTINFO_CODE) {
                //活动详情
                eventInfo.setText(data.getStringExtra("text"));
                eventInfo.requestFocus();
//                releasedRequestEntity.getEventactivity().setEventactivity_detail(data.getStringExtra("text"));
            } else if (requestCode == ADDRESS_CODE) {
                //赛事地址
                eventAddress.setText(data.getStringExtra("courtname"));
                courtid = data.getIntExtra("courtid", -1);
//                releasedRequestEntity.getEventactivity().setEventactivity_address(data.getStringExtra("courtname"));
            } else if (requestCode == ADD_IMAGE_CODE) {
                //添加图片
                String url = data.getStringExtra("url");
                if (url != null) {
                    imageList.add(url);
                }
                UpdateBanner();
            } else if (requestCode == IMAGE_DETAIL_CODE) {
                //图片详情
                if (data.getBooleanExtra("isDelete", false)) {
                    int position = data.getIntExtra("position", -1);
                    Log.i(TAG, "onActivityResult: position:" + position);
                    if (position != -1) {
                        imageList.remove(position);
                        Log.i(TAG, "onActivityResult: 图片集合长度:" + imageList.size());
                        UpdateBanner();
                    }
                }
            }
        }
    }

    /**
     * Android6.0申请权限的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case REQUEST_LOCATION_ACCESS_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initLocation();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(this, "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CALL_ACCESS_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(servicePhoneStr);
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(this, "请手动打开拨打电话权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更新轮播图的显示
     */
    private void UpdateBanner() {
        if (imageList.size() > 1) {
            convenientBanner.startTurning(3000);
            convenientBanner.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            convenientBanner.notifyDataSetChanged();
        } else {
            convenientBanner.stopTurning();
            convenientBanner.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            GlideUtil.loadImageView(this, imageList.get(0), image);
        }
    }

    @Override
    public void onBackPressed() {
        releasedRequestEntity = null;
        finish();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void getEvent(Map<String, EventWithSignInfoEntity> map) {
//        if (map.containsKey("EventReleasedActivity")) {
//            Log.i(TAG, "getEvent: 执行了");
//            EventWithSignInfoEntity eventWithSignInfoEntity = map.get("EventReleasedActivity");
//            this.datas = eventWithSignInfoEntity.getEventactivity();
//            listsigndefinition = eventWithSignInfoEntity.getListsigndefinition();
//            this.editFlag = true;
//            eventWithSignInfoEntity = null;
//        }
//    }
}
