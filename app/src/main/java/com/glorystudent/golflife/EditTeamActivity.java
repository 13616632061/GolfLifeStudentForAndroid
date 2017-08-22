package com.glorystudent.golflife;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.EditTeamRequestEntity;
import com.glorystudent.entity.TeamDetailEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.ActionSheetDialog;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.AliyunUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.PickerViewUtil;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/6/7.
 */

public class EditTeamActivity extends BaseActivity {
    private static final String TAG = "EditTeamActivity";
    private static final int REQUEST_INTRODUCTION_CODE = 0x123;
    private static final int REQUEST_CITY_CODE = 0X122;
    private static final int LOCAL_IMAGE_CODE = 0x121;
    private static final int CAMERA_IMAGE_CODE = 0x120;
    private static final String IMAGE_TYPE = "image/*";
    private String rootUrl = null;
    private String curFormatDateStr = null;
    private String filePath;

    @Bind(R.id.iv_team_image)
    ImageView ivTeamImage;
    @Bind(R.id.iv_team_logo)
    ImageView ivTeamLogo;
    @Bind(R.id.tv_team_change)
    TextView tvTeamChange;
    @Bind(R.id.et_team_name)
    EditText etTeamName;
    @Bind(R.id.tv_team_date)
    TextView tvTeamDate;
    @Bind(R.id.rl_team_date)
    RelativeLayout rlTeamDate;
    @Bind(R.id.tv_team_city)
    TextView tvTeamCity;
    @Bind(R.id.rl_team_adress)
    RelativeLayout rlTeamAdress;

    private TeamDetailEntity.TeamDetailBean teamDetail;
    private EditTeamRequestEntity editTeamRequestEntity;//请求实体

    private boolean photoState; //false->代表点击的是球队大图，true->代表是点击的球队logo

    private int logoUpLoadState = 0;//上传图片的状态，0->默认，没有添加。
    private int picUpLoadState = 0; //上传图片的状态，0->默认，没有添加。
    private int upLoadSuccess = 1;//上传成功
    private int upLoading = 2;//正在上传
    private int upLoadFailure = 3;//上传失败

    private final static int SUCCESS = 0x123;
    private final static int FAILURE = 0x321;
    private int cid;//城市id
    //上传大图的回调handler
    private Handler picHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    String url = (String) msg.obj;
                    editTeamRequestEntity.getTeam().setPic(url);
                    picUpLoadState = upLoadSuccess;
                    break;
                case FAILURE:
                    picUpLoadState = upLoadFailure;
                    break;
            }
            return false;
        }
    });
    //上传logo的handler回调
    private Handler logoHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    String url = (String) msg.obj;
                    editTeamRequestEntity.getTeam().setLogo(url);
                    logoUpLoadState = upLoadSuccess;
                    break;
                case FAILURE:
                    logoUpLoadState = upLoadFailure;
                    break;
            }
            return false;
        }
    });

    @Override
    protected int getContentId() {
        return R.layout.activity_edit_team;
    }

    @Override
    protected void init() {
        initValue();
        initRequestEntity();
    }

    /**
     * 初始化请求实体
     */
    private void initRequestEntity() {
        editTeamRequestEntity = new EditTeamRequestEntity();
        EditTeamRequestEntity.TeamBean teamBean = new EditTeamRequestEntity.TeamBean();
        editTeamRequestEntity.setTeam(teamBean);
        teamBean.setSummary(teamDetail.getSummary());
        teamBean.setId(teamDetail.getId());
    }

    /**
     * 赋值
     */
    private void initValue() {
        if (teamDetail != null) {
            GlideUtil.loadImageView(this, teamDetail.getPic(), ivTeamImage);
            GlideUtil.loadImageView(this, teamDetail.getLogo(), ivTeamLogo);
            etTeamName.setText(teamDetail.getTitle());
            tvTeamCity.setText(teamDetail.getRegionText());
            tvTeamDate.setText(TimeUtil.getEventTime(TimeUtil.getStandardDate(teamDetail.getCreatedate())));
            cid = teamDetail.getRegionid();
        }
    }

    @OnClick({R.id.back, R.id.tv_submit, R.id.iv_team_logo, R.id.tv_team_change, R.id.rl_team_date,
            R.id.rl_team_adress, R.id.rl_team_introduction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                close();
                break;
            case R.id.tv_submit:
                //提交
                acquireData();
                break;
            case R.id.iv_team_logo:
                //球队logo
                photoState = true;
                showPhotographDialog();
                break;
            case R.id.tv_team_change:
                //更换球队图片
                photoState = false;
                showPhotographDialog();
                break;
            case R.id.rl_team_date:
                //成立时间
                selectDate();
                break;
            case R.id.rl_team_adress:
                //所在地区
                startSelectCity();
                break;
            case R.id.rl_team_introduction:
                //球队简介
                startTeamIntroduction();
                break;
        }
    }

    /**
     * 打开拍照相册的对话框
     */
    private void showPhotographDialog() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //拍照
                                requestPermission(new String[]{android.Manifest.permission.CAMERA,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, false, new PermissionsResultListener() {
                                    @Override
                                    public void onPermissionGranted() {
                                        takePhoto();
                                    }

                                    @Override
                                    public void onPermissionDenied() {

                                    }
                                });
                            }
                        })
                .addSheetItem("相册",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //相册选择
                                pickPhoto();
                            }
                        })
                .show();
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
            }
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
        startActivityForResult(intent, CAMERA_IMAGE_CODE);
    }

    /**
     * 收集已输入的数据
     */
    private void acquireData() {
        //检查球队名称
        String nameStr = etTeamName.getText().toString().trim();
        if (nameStr.isEmpty()) {
            Toast.makeText(this, "球队名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //检查地区
        String cityStr = tvTeamCity.getText().toString().trim();
        if (cityStr.isEmpty()) {
            Toast.makeText(this, "地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //检查日期
        String dateStr = tvTeamDate.getText().toString().trim();
        if (dateStr.isEmpty()) {
            Toast.makeText(this, "成立时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        editTeamRequestEntity.getTeam().setTitle(nameStr);
        editTeamRequestEntity.getTeam().setRegionText(cityStr);
        editTeamRequestEntity.getTeam().setRegionid(cid);
        editTeamRequestEntity.getTeam().setCreatedate(dateStr);
        showLoading();
        //检查图片上传的状态
        if (logoUpLoadState == upLoadFailure || picUpLoadState == upLoadFailure) {//图片上传失败
            dismissLoading();
            Toast.makeText(this, "图片上传失败,请重新上传", Toast.LENGTH_SHORT).show();
            return;
        } else if (logoUpLoadState == upLoading || picUpLoadState == upLoading) {//正在上传
            while (logoUpLoadState == upLoading || picUpLoadState == upLoading) {
                try {
                    Thread.sleep(100);//每隔0.1秒判断一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (logoUpLoadState == upLoadFailure || picUpLoadState == upLoadFailure) {//上传失败
                dismissLoading();
                Toast.makeText(this, "图片上传失败,请重新上传", Toast.LENGTH_SHORT).show();
                return;
            } else {//上传成功
                editTeam();
            }
        } else {//没有添加或者成功
            editTeam();
        }

    }

    /**
     * 编辑球队
     */
    private void editTeam() {
        String json = new Gson().toJson(editTeamRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        String url = "/api/APITeam/EditTeamInfo";
        Log.i(TAG, "editTeam: " + requestJson);
        Log.i(TAG, "editTeam: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        Toast.makeText(EditTeamActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("TeamDetailActivity", "refresh"));
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("TeamManagementActivity", "refresh"));
                        finish();
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
                Toast.makeText(EditTeamActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 打开球队简介页面
     */
    private void startTeamIntroduction() {
        Intent intent = new Intent(this, TeamIntroductionActivity.class);
        String text = editTeamRequestEntity.getTeam().getSummary();
        intent.putExtra("text", text);
        startActivityForResult(intent, REQUEST_INTRODUCTION_CODE);
    }

    /**
     * 选择城市页面
     */
    private void startSelectCity() {
        Intent intent = new Intent(this, SelectTeamCityActivity.class);
        startActivityForResult(intent, REQUEST_CITY_CODE);
    }

    /**
     * 选择成立时间
     */
    private void selectDate() {
        Calendar currentCalendar = new GregorianCalendar();
        if (tvTeamDate.getText().toString().isEmpty()) {
            currentCalendar.setTime(new Date());
        } else {
            currentCalendar.setTime(TimeUtil.getDateFromEvent(tvTeamDate.getText().toString()));
        }
        new PickerViewUtil(this).showTimePickerView3("选择日期", currentCalendar, new PickerViewUtil.TimeLisener() {
            @Override
            public void onSubmit(Date date, View v) {
                tvTeamDate.setText(TimeUtil.getEventTime(date));
            }
        });
    }

    /**
     * 关闭页面的弹窗提示
     */
    private void close() {
        new AlertDialog(this)
                .builder()
                .setTitle("取消编辑球队")
                .setCancelable(true)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setNegativeButton("继续编辑", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == resultCode) {
            if (requestCode == REQUEST_INTRODUCTION_CODE) {//简介
                if (data != null) {
                    String text = data.getStringExtra("text");
                    editTeamRequestEntity.getTeam().setSummary(text);
                }
            }
            if (requestCode == REQUEST_CITY_CODE) {//地区
                if (data != null) {
                    String name = data.getStringExtra("name");
                    cid = data.getIntExtra("cid", -1);
                    tvTeamCity.setText(name);
                }
            }
            if (requestCode == LOCAL_IMAGE_CODE) {//相册
                Uri uri = data.getData();
                String url = UpLoadImageUtil.getRealFilePath(this, uri);
                Log.i(TAG, "本地相册url是：" + url);
                upLoadImage(url);
            } else if (requestCode == CAMERA_IMAGE_CODE) {//拍照
                //拍摄的照片存储位置
                Log.i(TAG, "相机拍照的url是：" + filePath);
                upLoadImage(filePath);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用阿里云上传图片
     *
     * @param url
     */
    private void upLoadImage(String url) {
        if (photoState) {//logo
            GlideUtil.loadImageView(this, url, ivTeamLogo);
            logoUpLoadState = upLoading;//正在上传
            AliyunUtil.loadOss(this, url, logoHandler);
        } else {//大图
            GlideUtil.loadImageView(this, url, ivTeamImage);
            picUpLoadState = upLoading;
            AliyunUtil.loadOss(this, url, picHandler);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(Map<String, TeamDetailEntity.TeamDetailBean> map) {
        if (map.containsKey("EditTeamActivity")) {
            if (map.get("EditTeamActivity") instanceof TeamDetailEntity.TeamDetailBean) {
                TeamDetailEntity.TeamDetailBean datas = map.get("EditTeamActivity");
                this.teamDetail = datas;
                datas = null;
            }
        }
    }
}
