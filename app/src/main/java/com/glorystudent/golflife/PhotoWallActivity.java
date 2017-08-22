package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.glorystudent.adapter.PhotoWallGridAdapter;
import com.glorystudent.entity.PhotoWallEntity;
import com.glorystudent.entity.PhotoWallRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.AliyunUtil;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.util.UpLoadImageUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableGridView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/21.
 */

public class PhotoWallActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG = "PhotoWallActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.gv_photo_wall)
    PullableGridView gridView;
    @Bind(R.id.iv_photo_wall_camera)
    ImageView camera;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refresh_view;
    private PopupWindow popupWindow;

    private static final int REQUEST_CAMERA_ACCESS_PERMISSION = 1;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 2;
    private static final int LOCAL_IMAGE_CODE = 1;
    private static final int CAMERA_IMAGE_CODE = 2;
    private static final String IMAGE_TYPE = "image/*";
    private String rootUrl = null;
    private String curFormatDateStr = null;

    private String filePath;
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String testBucket;
    private final static int SUCCESS = 0x123;
    private final static int FAILURE = 0x321;
    private PhotoWallRequestEntity entity;
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
    private int eventActivity_id;
    private PhotoWallGridAdapter gridAdapter;
    private List<PhotoWallEntity.EventPicWallListBean> datas;
    private int photoWallPage = 1;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

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
                        Toast.makeText(PhotoWallActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        loadDatas();
                    } else {
                        Toast.makeText(PhotoWallActivity.this, statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                Toast.makeText(PhotoWallActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
        //发送消息通知前上个页面刷新数据
        EventBus.getDefault().post(EventBusMapUtil.getStringMap("LookCertificateActivity", "refresh"));
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_photo_wall;
    }

    @Override
    protected void init() {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                photoWallPage = 1;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                photoWallPage++;
                loadDatas();
            }
        });
        rootUrl = Environment.getExternalStorageDirectory().getPath();
        showLoading();
        gridAdapter = new PhotoWallGridAdapter(this);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);

        Intent intent = getIntent();
        eventActivity_id = intent.getIntExtra("id", -1);
    }

    @Override
    protected void loadDatas() {
        //根据活动id获取照片墙
        String json = "\"eventpic\":{" + "\"eventactivity_id\":" + eventActivity_id + "}," + "\"page\":" + photoWallPage;
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "loadDatas: " + requestJson);
        String url = "/Public/APIPublicEventActivityPic/QueryEventActivityPicWall";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        PhotoWallEntity photoWallEntity = new Gson().fromJson(jo.toString(), PhotoWallEntity.class);
                        datas = photoWallEntity.getEventPicWallList();
                        if (datas != null) {
                            if (isRefresh) {
                                gridAdapter.setDatas(datas);
                            } else {
                                gridAdapter.addDatas(datas);
                            }
                        }
                        gridAdapter.setDatas(datas);
                    } else if (statuscode.equals("2")) {//无数据
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {//失败
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(PhotoWallActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                photoWallPage--;
            }
        }).getEntityData(this, url, requestJson);

    }

    @OnClick({R.id.back, R.id.iv_photo_wall_camera})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_photo_wall_camera:
                //添加本地图片到照片墙
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
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PhotoWallDetailActivity.class);
        intent.putExtra("url", datas.get(position).getEventactivity_picpath());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        popupWindow.dismiss();
        switch (v.getId()) {
            case R.id.photograph:
                checkCameraPermission();
                break;
            case R.id.photo_album:
                pickPhoto();
                break;
            case R.id.cancel:
                break;
        }
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
        } else {
//            Toast.makeText(this, "没有添加图片", Toast.LENGTH_SHORT).show();
        }
    }

    private void upLoadImage(String url) {
        showLoading();
        AliyunUtil.loadOss(this, url, handler);
    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_ACCESS_PERMISSION);
        } else {
            checkWriteStoragePermission();
        }
    }

    private void checkWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE_PERMISSION);
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
