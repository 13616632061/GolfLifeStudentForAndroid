package com.glorystudent.golflife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.UriToPathUtil;
import com.glorystudent.widget.residentview.ACache;
import com.glorystudent.widget.residentview.BitmapUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 常住分析
 */
public class AnalysisActivity extends BaseActivity implements BaseActivity.PermissionsResultListener,RadioGroup.OnCheckedChangeListener {


    private static final int REQUEST_CODE_GALLERY = 99;//request code
    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.analysis_make_befor)
    TextView analysisMakeBefor;
    @Bind(R.id.analysis_image1)
    ImageView analysisImage1;
    @Bind(R.id.analysis_make_after)
    TextView analysisMakeAfter;
    @Bind(R.id.analysis_image2)
    ImageView analysisImage2;
    @Bind(R.id.analysis_btngroup_side)
    RadioButton analysisBtngroupSide;
    @Bind(R.id.analysis_btngroup_front)
    RadioButton analysisBtngroupFront;
    @Bind(R.id.analysis_btngroup)
    RadioGroup analysisBtngroup;

    @Bind(R.id.analysis_make_lay)
    LinearLayout layout;

    private ACache cache;
    private  String path;
    private int type=0;//前后   0前  1后
    @Override
    protected int getContentId() {
        return R.layout.activity_analysis;
    }


    @Override
    protected void init()  {
        super.init();
        cache=ACache.get(this);
        analysisBtngroup.setOnCheckedChangeListener(this);

        path= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test"+File.separator;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.requestPermission(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, true, this);
        }else{
            path= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test"+File.separator;
        }

        Log.d(TAG, path);

        type=getIntent().getIntExtra("mode",0);

        analysisBtngroup.check(type==0?R.id.analysis_btngroup_side:R.id.analysis_btngroup_front);

        analysisImage1.setImageBitmap(type==0?getImage("01.png"):getImage("01.png"));
        analysisImage2.setImageBitmap(type==0?getImage("10.png"):getImage("11.png"));

    }


    @OnClick({R.id.back, R.id.tv_historical,R.id.analysis_make_befor,R.id.analysis_make_after})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.back://返回
                finish();
                break;
            case R.id.tv_historical://保存
                layout.setDrawingCacheEnabled(true);
                Bitmap bitmap=layout.getDrawingCache();

                if(BitmapUtils.saveImageToGallery(this,bitmap,System.currentTimeMillis()+"")){
                    Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.analysis_make_befor://以前
                type=0;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0x100);
                break;
            case R.id.analysis_make_after://以后
                type=1;
                Intent intents = new Intent();
                intents.setType("image/*");
                intents.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intents, 0x100);
                break;
        }

    }


    @Override
    public void onPermissionGranted() {


    }

    @Override
    public void onPermissionDenied() {
        finish();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.analysis_btngroup_side://侧面
                analysisImage1.setImageBitmap(type==0?getImage("01.png"):getImage("01.png"));
                analysisImage2.setImageBitmap(type==0?getImage("10.png"):getImage("11.png"));
//                GlideUtil.loadImageView(this,path+"00.png",analysisImage1);
//                GlideUtil.loadImageView(this,path+"01.png",analysisImage2);
                break;
            case R.id.analysis_btngroup_front://前面
//                GlideUtil.loadImageView(this,path+"10.png",analysisImage1);
//                GlideUtil.loadImageView(this,path+"11.png",analysisImage2);

                analysisImage1.setImageBitmap(type==0?getImage("01.png"):getImage("01.png"));
                analysisImage2.setImageBitmap(type==0?getImage("10.png"):getImage("11.png"));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 选取图片的返回值
            if (requestCode == 0x100) {
                try {
                    Uri uri = data.getData();
                    Log.i(TAG, "onActivityResult: " + uri);
                    String path = UriToPathUtil.getPath(this, uri);
                    if(!TextUtils.isEmpty(path)){
                       if(type==0){
                           GlideUtil.loadImageView(this,path,analysisImage1);
                       }else {
                           GlideUtil.loadImageView(this,path,analysisImage2);
                       }
                    }

//
                } catch (Exception e) {
                    Log.i(TAG, "异常: " + e.getMessage());
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * 获取本地图片
     * @param fileName
     * @return
     */
    private Bitmap getImage(String fileName){
        if(TextUtils.isEmpty(fileName)){
            return null;
        }
        Bitmap bitmap= BitmapFactory.decodeFile(path+fileName);
        return bitmap;
    }
}
