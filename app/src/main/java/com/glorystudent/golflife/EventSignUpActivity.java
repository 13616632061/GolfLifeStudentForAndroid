package com.glorystudent.golflife;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.QRCodeUtil;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.UpLoadImageUtil;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/26.
 */

public class EventSignUpActivity extends BaseActivity {
    private static final String TAG = "EventSignUpActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_voucher)
    TextView tvVoucher;
    @Bind(R.id.tv_efficient_code)
    TextView tvEfficientCode;
    @Bind(R.id.tv_event_name)
    TextView tvEventName;
    @Bind(R.id.iv_sign_qrcode)
    ImageView ivSignQrcode;
    @Bind(R.id.tv_save_qrcode)
    TextView tvSaveQrcode;
    private int eventactivity_id;
    private String rootUrl;
    private String codeText;
    private static final int REQUEST_QRCODE = 0x100;
    private static final int REQUEST_CAMERA_ACCESS_PERMISSION = 1;

    @Override
    protected int getContentId() {
        return R.layout.activity_event_sign_up;
    }

    @Override
    protected void init() {
        rootUrl = Environment.getExternalStorageDirectory().getPath();
        Intent intent = getIntent();
        eventactivity_id = intent.getIntExtra("id", -1);
        String eventactivity_name = intent.getStringExtra("name");
        tvEventName.setText(eventactivity_name);
        //生成二维码
        String format = "%d";//赛事id
        codeText = String.format(format, eventactivity_id);
        QRCodeUtil.createCode(this, ivSignQrcode, codeText);
    }

    @OnClick({R.id.back, R.id.tv_voucher, R.id.tv_efficient_code, R.id.tv_save_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_voucher:
                //调用相机扫码
                checkCameraPermission();
                break;
            case R.id.tv_efficient_code:
                //手动输入
                startEfficientCode();
                break;
            case R.id.tv_save_qrcode:
                //保存二维码图片
                String filePath = rootUrl + "/golf/qrcode/" + codeText + ".png";
                File file = new File(filePath);
                if (file.exists()) {
                    Toast.makeText(this, "二维码已保存", Toast.LENGTH_SHORT).show();
                } else {
                    saveQrcodeImage(filePath);
                }
                break;
        }
    }

    /**
     * 开启相机扫码
     */
    private void startScanQrcode() {
        Intent intent = new Intent(Intents.Scan.ACTION);
        //设置模式:条形码或者二维码
        intent.putExtra(Intents.Scan.MODE, Intents.Scan.QR_CODE_MODE);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;
        int size = h < w ? h : w;
        size = size >> 1;
        intent.putExtra(Intents.Scan.WIDTH, size);
        intent.putExtra(Intents.Scan.HEIGHT, size);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, REQUEST_QRCODE);
    }

    /**
     * 保存二维码图片
     *
     * @param filePath
     */
    private void saveQrcodeImage(String filePath) {
        Bitmap bitmap = QRCodeUtil.createCodeBitmap(this, codeText);
        UpLoadImageUtil.saveBitmapFile(bitmap, filePath);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 手动输入有效验证码
     */
    private void startEfficientCode() {
        Intent intent = new Intent(this, EfficientCodeActivity.class);
        intent.putExtra("id", eventactivity_id);
        startActivity(intent);
    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_ACCESS_PERMISSION);
        } else {
            startScanQrcode();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_QRCODE && resultCode == RESULT_OK && data != null) {
            String text = data.getStringExtra(Intents.Scan.RESULT);
            String[] strings = text.split(",");
            String eventIdStr = strings[0];//活动id
            String json = null;
            switch (strings.length) {
                case 1://赛事id
                    json = "\"signup\":{" + "\"sign_activitiesid\":" + eventIdStr + "}";
                    Log.i(TAG, "onActivityResult: json2--->" + json);
                    handleResult(json);
                    break;
                case 2://赛事id，凭证号
                    String voucherStr = strings[1];
                    json = "\"signup\":{" + "\"sign_activitiesid\":" + eventIdStr + "," + "\"sign_voucher\":"
                            + "\"" + voucherStr + "\"}";
                    Log.i(TAG, "onActivityResult: json1-->" + json);
                    handleResult(json);
                    break;
            }
        }
    }

    /**
     * 处理扫码后的数据，访问网络验证信息
     *
     * @param json
     */
    private void handleResult(String json) {
        showLoading();
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "handleResult: " + requestJson);
        String url = "/api/APISignUp/SweepCodeSignUp";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    Log.i(TAG, "parseDatas: " + statusmessage);
                    if (statuscode.equals("1")) {
                        Toast.makeText(EventSignUpActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EventSignUpActivity.this, statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {

            }
        }).getEntityData(this, url, requestJson);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_ACCESS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanQrcode();
            } else {
                Toast.makeText(this, "相机权限开启失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
