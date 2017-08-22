package com.glorystudent.golflife;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.PhotoWallRecyclerAdapter;
import com.glorystudent.entity.LookCertificateEntity;
import com.glorystudent.entity.PhotoWallRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.AliyunUtil;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.QRCodeUtil;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.util.UpLoadImageUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/20.
 */

public class LookCertificateActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LookCertificateActivity";

    private static final int REQUEST_CAMERA_ACCESS_PERMISSION = 1;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 2;
    private static final int LOCAL_IMAGE_CODE = 1;
    private static final int CAMERA_IMAGE_CODE = 2;
    private static final String IMAGE_TYPE = "image/*";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_certificate_cancel)
    TextView cancel;
    @Bind(R.id.tv_certificate_name)
    TextView name;
    @Bind(R.id.tv_certificate_cost)
    TextView cost;
    @Bind(R.id.tv_certificate_apply_number)
    TextView applyNumber;
    @Bind(R.id.tv_certificate_date)
    TextView date;
    @Bind(R.id.tv_certificate_address)
    TextView address;
    @Bind(R.id.tv_certificate_look_info)
    TextView lookInfo;
    @Bind(R.id.iv_pic_canceled)
    ImageView ivPicCanceled;
    @Bind(R.id.iv_pic_completed)
    ImageView ivPicCompleted;
    @Bind(R.id.tv_certificate_number)
    TextView certificateNumber;
    @Bind(R.id.tv_certificate_apply_name)
    TextView applyName;
    @Bind(R.id.iv_certificate_qrcode)
    ImageView qrCode;
    @Bind(R.id.ll_certificate_info)
    LinearLayout llCertificateInfo;
    @Bind(R.id.tv_certificate_photo_wall_number)
    TextView photoWallNumber;
    @Bind(R.id.rl_photo_wall)
    RelativeLayout rlPhotoWall;
    @Bind(R.id.iv_add_photo)
    ImageView addPhoto;
    @Bind(R.id.rv_photo_wall)
    RecyclerView photoWall;
    @Bind(R.id.ll_photo_wall)
    LinearLayout llPhotoWall;
    @Bind(R.id.tv_certificate_cancel_info)
    TextView tvCertificateCancelInfo;
    @Bind(R.id.ll_certificate_cancel_event)
    LinearLayout llCertificateCancelEvent;
    private String rootUrl = null;
    private String curFormatDateStr = null;
    private String filePath;

    private PhotoWallRecyclerAdapter photoWallRecyclerAdapter = new PhotoWallRecyclerAdapter(this);
    private int signUpId;
    private int eventActivity_id;

    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String testBucket;
    private final static int SUCCESS = 0x123;
    private final static int FAILURE = 0x321;
    private PhotoWallRequestEntity entity;//新增照片墙请求实体
    private AliyunUtil aliyunUtil;
    private PopupWindow popupWindow;
    private int photoWallPage = 1;

    private LookCertificateEntity.ListeventactivityBean datas;
    private LookCertificateEntity.ListeventactivityBean.ListSignUpBean listSignUpBean;
    private Button weather;//天气原因
    private Button matters;//事宜安排有冲突
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    String url = (String) msg.obj;
                    entity = new PhotoWallRequestEntity();
                    PhotoWallRequestEntity.EventpicBean eventpicBean = new PhotoWallRequestEntity.EventpicBean();
                    eventpicBean.setEventactivity_id(eventActivity_id);
                    eventpicBean.setEventactivity_picpath(url);
                    entity.setEventpic(eventpicBean);
                    upLoad();
                    break;
                case FAILURE:
                    Log.i(TAG, "handleMessage: 上传阿里云失败");
                    break;
            }
            return false;
        }
    });

    private void upLoad() {
        String json = new Gson().toJson(entity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        Log.i(TAG, "upLoad: " + requestJson);
        String url = "/Public/APIPublicEventActivityPic/AddEventActivityPic";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    Log.i(TAG, "parseDatas: " + statuscode + "消息：" + statusmessage);
                    if (statuscode.equals("1")) {
                        Toast.makeText(LookCertificateActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        loadDatas();
                    } else {
                        Toast.makeText(LookCertificateActivity.this, statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                Toast.makeText(LookCertificateActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_look_certificate;
    }

    @Override
    protected void init() {
        rootUrl = Environment.getExternalStorageDirectory().getPath();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photoWall.setLayoutManager(manager);
        photoWall.setAdapter(photoWallRecyclerAdapter);
        //recyclerView的item自定义点击事件
        photoWallRecyclerAdapter.setOnItemClickListener(new PhotoWallRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(LookCertificateActivity.this, "点击了图片：" + photoWallList.get(position), Toast.LENGTH_SHORT).show();
                if (listSignUpBean != null && !"3".equals(listSignUpBean.getSign_state())) {
                    Intent intent = new Intent(LookCertificateActivity.this, PhotoWallDetailActivity.class);
                    intent.putExtra("url", datas.getListeventpicwall().get(position).getEventactivity_picpath());
                    LookCertificateActivity.this.startActivity(intent);
                }
            }
        });
        Intent intent = getIntent();
        signUpId = intent.getIntExtra("signUpId", -1);
        eventActivity_id = intent.getIntExtra("eventActivityId", -1);
    }

    @Override
    protected void loadDatas() {
        showLoading();
        //根据报名id获取页面信息
        String json = "\"signUpID\":" + signUpId;
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/Public/APIPublicEventActivity/QueryEventActivityVoucher";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        LookCertificateEntity lookCertificateEntity = new Gson().fromJson(jo.toString(), LookCertificateEntity.class);
                        List<LookCertificateEntity.ListeventactivityBean> listeventactivity = lookCertificateEntity.getListeventactivity();
                        if (listeventactivity != null && listeventactivity.size() > 0) {
                            datas = listeventactivity.get(0);
                            initValue();
                        }
                    } else if (statuscode.equals("2")) {//无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {//失败

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(LookCertificateActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 给控件赋初始值
     */
    private void initValue() {
        //判空后赋初始值
        if (datas != null) {
            name.setText(datas.getEventactivity_name());
            String costtype = datas.getEventactivity_costtype();
            if (costtype.equals("1")) {
                cost.setText("免费活动");
                cost.setTextColor(getResources().getColor(R.color.primaryColor));
            } else {
                DecimalFormat df = new DecimalFormat("0.00");
                cost.setText("￥" + df.format(datas.getEventactivity_cost()));
                cost.setTextColor(getResources().getColor(R.color.colorOrange));
            }
            applyNumber.setText(datas.getSignUpNumber() + "");
            String startTime = TimeUtil.getCertificateTime(TimeUtil.getStandardDate(datas.getEventactivity_begintime()));
            String endTime = TimeUtil.getCertificateTime(TimeUtil.getStandardDate(datas.getEventactivity_endtime()));
            date.setText(startTime + " - " + endTime);
            address.setText(datas.getEventactivity_address());
            eventActivity_id = datas.getEventActivity_id();
            if (datas.getListSignUp() != null && datas.getListSignUp().size() > 0) {
                listSignUpBean = datas.getListSignUp().get(0);
            }
            if (listSignUpBean != null) {
                certificateNumber.setText(listSignUpBean.getSign_voucher());
                applyName.setText(listSignUpBean.getSign_name());
                //生成二维码
                String format = "%d,%s";//赛事id,凭证号
                QRCodeUtil.createCode(this, qrCode, String.format(format, eventActivity_id, listSignUpBean.getSign_voucher()));
            }
        }
        Log.i(TAG, "initValue: " + eventActivity_id);
        //获取照片墙信息
        List<LookCertificateEntity.ListeventactivityBean.ListeventpicwallBean> listeventpicwall = datas.getListeventpicwall();
        if (listeventpicwall != null) {
            photoWallNumber.setText(listeventpicwall.size() + "");
            photoWallRecyclerAdapter.setDatas(listeventpicwall);
        } else {
            photoWallNumber.setText(0 + "");
        }
        //判断用户状态，1.未付款，2.报名成功，3.已取消，4.已拒绝，5.已付款
        if (listSignUpBean.getSign_state().equals("2")) {//已报名
            lookInfo.setVisibility(View.VISIBLE);
            ivPicCanceled.setVisibility(View.GONE);
            ivPicCompleted.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        } else if (listSignUpBean.getSign_state().equals("3")) {//已取消
            lookInfo.setVisibility(View.GONE);
            ivPicCanceled.setVisibility(View.VISIBLE);
            ivPicCompleted.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            qrCode.setAlpha(0.32f);
            applyName.setTextColor(Color.argb(255, 169, 169, 169));
            certificateNumber.setTextColor(Color.argb(255, 169, 169, 169));
        }
        //判断发布活动状态  -0 未开赛 1 进行中 2已结束 3 已删除 4 取消活动
        if (datas.getEventactivity_state().equals("4")) {
            llCertificateCancelEvent.setVisibility(View.VISIBLE);
//            llCertificateInfo.setVisibility(View.GONE);
//            llPhotoWall.setVisibility(View.GONE);
            String text1 = "\u3000\u3000由于";
            String refuse = (String) datas.getCancelrefuse();
            if (refuse == null) {
                refuse = "其他";
            }
            String text2 = "，发布者";
            String organizer = (String) datas.getEventactivity_organizer();
            if (organizer == null) {
                organizer = "";
            }
            String text3 = "取消了本次活动，很抱歉给您带来的不便。";
            SpannableString spannableString = new SpannableString(text1 + refuse + text2 + organizer + text3);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorOrange)),
                    text1.length(), text1.length() + refuse.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorOrange))
                    , refuse.length() + text1.length() + text2.length(),
                    text1.length() + text2.length() + refuse.length() + organizer.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tvCertificateCancelInfo.setText(spannableString);
        } else {
            llCertificateCancelEvent.setVisibility(View.GONE);
            llCertificateInfo.setVisibility(View.VISIBLE);
            llPhotoWall.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.back, R.id.tv_certificate_look_info, R.id.rl_photo_wall, R.id.iv_add_photo, R.id.tv_certificate_cancel})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_certificate_look_info:
                //查看详情
                startEventDetail();
                break;
            case R.id.rl_photo_wall:
                //跳转照片墙页面
                if (listSignUpBean != null && !"3".equals(listSignUpBean.getSign_state())) {
                    Intent intent = new Intent(this, PhotoWallActivity.class);
                    intent.putExtra("id", eventActivity_id);
                    startActivity(intent);
                }
                break;
            case R.id.iv_add_photo:
                //添加本地图片到照片墙
                if (listSignUpBean != null && !"3".equals(listSignUpBean.getSign_state())) {
                    showPopupWindow();
                }
                break;
            case R.id.tv_certificate_cancel:
                //取消活动
                if (listSignUpBean != null && !"3".equals(listSignUpBean.getSign_state())) {
                    showPopupWindow2();
                }
                break;
        }
    }

    /**
     * 弹出选择相册和拍照
     */
    private void showPopupWindow() {
        Log.i(TAG, "onViewClicked: 点击了添加图片");
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_photo_layout, null);
        Button photograph = (Button) view.findViewById(R.id.photograph);
        Button photo_album = (Button) view.findViewById(R.id.photo_album);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        photograph.setOnClickListener(this);
        photo_album.setOnClickListener(this);
        cancel.setOnClickListener(this);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow.showAtLocation(this.findViewById(R.id.back), Gravity.BOTTOM, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 弹出选择取消原因
     */
    private void showPopupWindow2() {
        Log.i(TAG, "onViewClicked: 点击了添加图片");
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_select_cancel_layout, null);
        weather = (Button) view.findViewById(R.id.weather);
        matters = (Button) view.findViewById(R.id.matters);
        Button other = (Button) view.findViewById(R.id.other);
        weather.setOnClickListener(this);
        matters.setOnClickListener(this);
        other.setOnClickListener(this);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow.showAtLocation(this.findViewById(R.id.back), Gravity.BOTTOM, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        popupWindow.dismiss();
        switch (v.getId()) {
            case R.id.photograph://拍照
                checkCameraPermission();
                break;
            case R.id.photo_album://相册选择
                pickPhoto();
                break;
            case R.id.cancel://取消
                break;
            case R.id.weather://天气原因
                requestCancelRefuse(weather.getText().toString());
                break;
            case R.id.matters://事宜安排有冲突
                requestCancelRefuse(matters.getText().toString());
                break;
            case R.id.other://其他
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.popu_cancel_layout, null);
                final TextView cancelInfo = (TextView) view.findViewById(R.id.et_cancel_info);
                TextView cancel = (TextView) view.findViewById(R.id.cancel);
                TextView sure = (TextView) view.findViewById(R.id.sure);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String text = cancelInfo.getText().toString().trim();
                        if (text.isEmpty()) {
                            Toast.makeText(LookCertificateActivity.this, "取消原因不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        popupWindow.dismiss();
                        requestCancelRefuse(text);
                    }
                });
                //显示popupwindow
                popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
                popupWindow.setFocusable(true);
                // 实例化一个ColorDrawable颜色为半透明
                ColorDrawable dw = new ColorDrawable(0x00000000);
                popupWindow.setBackgroundDrawable(dw);
                // 在中间显示
                popupWindow.showAtLocation(this.findViewById(R.id.back), Gravity.CENTER, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
        }
    }

    /**
     * 请求网络取消原因
     */
    private void requestCancelRefuse(String text) {
        showLoading();
        String json = "\"wxpay\":{" + "\"sign_id\":" + listSignUpBean.getSignup_id() + "}," + "\"signrefse\":" + "\"" + text + "\"" + "," + "\"type\":" + "\"1\"";
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "requestCancelRefuse: " + requestJson);
        String url = "/api/APIWXPay/WXPayReFundAPP";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(LookCertificateActivity.this, "取消报名成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("MyJoinEventFragment", "refresh"));
                        finish();
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LookCertificateActivity.this.dismissLoading();
            }

            @Override
            public void requestFailed() {
                LookCertificateActivity.this.dismissLoading();
                Toast.makeText(LookCertificateActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 查看详情页面
     */
    private void startEventDetail() {
        Log.i(TAG, "startEventDetail: " + eventActivity_id);
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("id", eventActivity_id);
        startActivity(intent);
    }

    /**
     * 相册选取
     */
    private void pickPhoto() {
        Log.i(TAG, "pickPhoto: 从相册选取");
        //从相册中取
        Intent intent = new Intent();
                 /* 开启Pictures画面Type设定为image */
        intent.setType(IMAGE_TYPE);
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, LOCAL_IMAGE_CODE);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Log.i(TAG, "takePhoto: 拍照选取");
        curFormatDateStr = TimeUtil.getImageNameTime(Calendar.getInstance().getTime());
        filePath = rootUrl + "/golf/camera/" + "IMG_" + curFormatDateStr + ".png";
        File file = new File(filePath);
        if (!file.exists()) {
            File dirs = new File(file.getParent());
            if (!dirs.exists()) {
                dirs.mkdirs();
                Log.i(TAG, "startCamera: " + "是否执行了?");
            }
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
        startActivityForResult(intent, CAMERA_IMAGE_CODE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent2(Map<String, String> map) {
        if (map.containsKey("LookCertificateActivity")) {
            if (map.get("LookCertificateActivity").equals("refresh")) {
                Log.i(TAG, "getEventBus: 接收到数据");
                loadDatas();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String url = "";
            if (requestCode == LOCAL_IMAGE_CODE) {
                Uri uri = data.getData();
                url = UpLoadImageUtil.getRealFilePath(this, uri);
                Log.i(TAG, "本地相册url是：" + url);
                upLoadImage(url);
            } else if (requestCode == CAMERA_IMAGE_CODE) {
                //拍摄的照片存储位置
                Log.i(TAG, "相机拍照的url是：" + filePath);
                upLoadImage(filePath);
            }
        }
    }

    private void upLoadImage(String url) {
        showLoading();
        AliyunUtil.loadOss(this, url, handler);
    }

//    private void loadOss(final String url) {
//        AliyunRequestEntity aliyunOSS = RequestUtil.getAliyunOSS();
//        if (aliyunOSS != null) {
//            getKeyId(aliyunOSS, url);
//        } else {
//            OkGoRequest.getOkGoRequest().setOnGetOssListener(new OkGoRequest.OnGetOssListener() {
//                @Override
//                public void getOssSucceed(AliyunRequestEntity aliyunRequestEntity) {
//                    getKeyId(aliyunRequestEntity, url);
//                }
//            }).getAliyunOSS(this);
//        }
//    }
//
//    private void getKeyId(AliyunRequestEntity aliyunRequestEntity, String url) {
//        List<AliyunRequestEntity.ListsettingvalueBean> listsettingvalue = aliyunRequestEntity.getListsettingvalue();
//        accessKeyId = listsettingvalue.get(0).getSettingvalue();
//        accessKeySecret = listsettingvalue.get(1).getSettingvalue();
//        endpoint = "https://" + listsettingvalue.get(2).getSettingvalue() + ".aliyuncs.com";
//        testBucket = listsettingvalue.get(3).getSettingvalue();
//        Log.i(TAG, "upLoadImage: " + accessKeyId + "--->" + accessKeySecret + "===>" + endpoint);
//        OSS oss = aliyunUtil.getOss(accessKeyId, accessKeySecret, endpoint);
//        aliyunUtil.uploadImage(url, oss, testBucket, handler);
//    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_ACCESS_PERMISSION);
        } else {
            checkWriteStoragePermission();
        }
    }

    private void checkWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE_PERMISSION);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_ACCESS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkWriteStoragePermission();
            } else {
                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_WRITE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(this, "WRITE_EXTERNAL_STORAGE PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
