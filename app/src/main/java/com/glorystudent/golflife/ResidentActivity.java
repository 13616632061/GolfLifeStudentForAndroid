package com.glorystudent.golflife;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.UriToPathUtil;
import com.glorystudent.widget.residentview.ACache;
import com.glorystudent.widget.residentview.BaseSpirit;
import com.glorystudent.widget.residentview.BitmapUtils;
import com.glorystudent.widget.residentview.CameraPreview;
import com.glorystudent.widget.residentview.TestView;

import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 常住测评
 */
public class ResidentActivity extends BaseActivity implements BaseActivity.PermissionsResultListener, Camera.PictureCallback, RadioGroup.OnCheckedChangeListener,View.OnClickListener {


    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.resident_camera_template)
    FrameLayout residentCameraTemplate;
    @Bind(R.id.resident_element)
    TestView residentElement;

    @Bind(R.id.resident_btn_group)
    RadioGroup rgGroupBtn;

    @Bind(R.id.resident_btn_take)
    ImageView takeButton;

    @Bind(R.id.resident_template_image)
    ImageView imageViewBG;
    @Bind(R.id.tv_historical)
    TextView tvHistorical;
    @Bind(R.id.resident_bg_image)
    ImageView residentBgImage;
    @Bind(R.id.resident_front_image)
    ImageView residentFrontImage;


    private Camera camera;
    private CameraPreview cameraView;
    private Bitmap bitmapBG;//背景
    private int height, width;
    private boolean isTakePicture = false;//是否拍照

    private LinkedHashMap<String,Bitmap> bitmaps;


    private int direction = 0;//方向（0，前 1，后）
    private int mode = 0;//模式（侧面 0，正面 1）
    private ACache cache;

    @Override
    protected int getContentId() {
        return R.layout.activity_resident;
    }


    @Override
    protected void init() {
        super.init();
        cache=ACache.get(this);
        cache.clear();
        bitmaps=new LinkedHashMap<>();


        tvHistorical.setText("导入");
        rgGroupBtn.setOnCheckedChangeListener(this);
        height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        width = getWindow().getWindowManager().getDefaultDisplay().getWidth();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, true, this);
        } else {
            open();
        }
    }


    /**
     * 准备拍照
     */
    private void open() {
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
//        parameters.setPictureFormat(PixelFormat.RGBA_8888);
        parameters.set("jpeg-quality", 100);// 设置照片质量
        List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();

        Log.i(TAG, "open: size x " + width + "\t" + "size y " + height);

        for (Camera.Size size : sizeList
                ) {
            if (size.width == height && size.height == width) {
                parameters.setPictureSize(size.width, size.height);
                break;
            }
            Log.i(TAG, "open: size x " + size.width + "\t" + "size y " + size.height);
        }
//        parameters.setPictureSize(height,width);

        parameters.set("orientation", "portrait");
        parameters.set("rotation", 90);
        camera.setParameters(parameters);
        cameraView = new CameraPreview(this, camera);
        residentCameraTemplate.addView(cameraView);
    }


    @OnClick({R.id.back, R.id.tv_historical, R.id.resident_btn_take, R.id.resident_btn_bottom, R.id.resident_btn_top})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.back://返回
                finish();
                break;
            case R.id.tv_historical://保存/导入

                if(checkIsImport()){//两张都有
                    startAnalysis();
                }else {
                    if(!checkedSaveImage(""+mode+direction)) {//本图片不存在
                        importImage();
                        return;
                    }else {//已存在
                        saveImage();//成功则保存
                        direction=1-direction;//如果本图片存在则检测另一张图片是否存在
                    }
                }





                break;
            case R.id.resident_btn_take://拍照

                imageViewBG.setVisibility(View.INVISIBLE);
                cameraView.setVisibility(View.VISIBLE);

                if (camera != null)

                    if(checkIsImport()){//图片已存在就分析
                        startAnalysis();
                    }else {
                        if(checkedSaveImage(""+mode+direction) ) {//本图片已经存在
                            direction=1-direction;
                            if(checkedSaveImage(""+mode+direction)){//如果其他图片存在
                            }else {
                                isTakePicture();//检测是否已经拍照
                            }
                        }else {//本张图片不存在

                            isTakePicture();//检测是否已经拍照
                        }
                    }









                break;

            case R.id.resident_btn_bottom://后
                direction = 0;
                break;
            case R.id.resident_btn_top://前
                direction = 1;
                break;
        }

    }


    @Override
    public void onPermissionGranted() {
        open();

    }

    @Override
    public void onPermissionDenied() {
        finish();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmapBG = bitmap;
        isTakePicture = true;//拍照成功
        showGird();
        setFrontAndBgImage(bitmap);//更新右下角缩略图


    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.resident_btn_side://侧面
                mode = 0;
                cache.clear();
                isTakePicture=false;
                if(cameraView.getVisibility()==View.VISIBLE)
                    camera.startPreview();

                residentElement.selectFrontOrSide(BaseSpirit.SpiritType.SIDE);
                if(!checkedSaveImage(""+mode+direction)) {//本地不存在本张图片
                    residentElement.selectFrontOrSide(BaseSpirit.SpiritType.SIDE);
                    tvHistorical.setText("导入");
                }else {
                    showGird();
                }

                break;
            case R.id.resident_btn_front://正面
                mode = 1;
                cache.clear();
                isTakePicture=false;

                if(cameraView.getVisibility()==View.VISIBLE)
                    camera.startPreview();

                residentElement.selectFrontOrSide(BaseSpirit.SpiritType.FRONT);
                if(!checkedSaveImage(""+mode+direction)) {//本地不存在本张图片
                    residentElement.selectFrontOrSide(BaseSpirit.SpiritType.FRONT);
                    tvHistorical.setText("导入");
                }else {
                    showGird();
                }

                break;
            case R.id.resident_btn_analysis://分析
                startAnalysis();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean saveImage() {

        residentElement.disBigCircle(View.INVISIBLE);
        residentElement.setVisibility(View.INVISIBLE);
        residentElement.setDrawingCacheEnabled(true);
        residentElement.getRootView().getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(residentElement.getDrawingCache());
        bitmap.setConfig(Bitmap.Config.ARGB_8888);
        tvHistorical.setText("导入");
        return saveImageToDisk(bitmap);//保存图片
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
                    if (!TextUtils.isEmpty(path)) {
                        imageViewBG.setVisibility(View.VISIBLE);
                        setFrontAndBgImage(path);//更新右下角缩略图
                        showGird();//更新标注信息

                        if(checkIsImport()){//两张都有
                            tvHistorical.setText("分析");
                        }else {
                            tvHistorical.setText("保存");
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



    @Override
    public void onClick(View v) {
        camera.startPreview();
    }

    /**
     * 检测是否已经拍照
     */
    private void isTakePicture(){
        if(!isTakePicture){
            camera.startPreview();
            camera.takePicture(null, null, this);
        }else {
            AlertDialog alertDialog=new AlertDialog(this)
                    .builder()
                    .setTitle("是否保存图片")
                    .setCancelable(true)
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isTakePicture=false;
                            camera.startPreview();
                        }
                    })
                    .setPositiveButton("保存", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveImage();
                            isTakePicture=false;
                            camera.startPreview();
                        }
                    });

            alertDialog.show();
        }
    }

    /**
     *
     * @return false 导入  true 保存
     */

    private boolean checkIsImport(){
        if(mode==0){
            return checkedSaveImage("00") && checkedSaveImage("01");
        }else {
            return checkedSaveImage("10") && checkedSaveImage("11");
        }
    }

    /**
     * 保存图片
     * @param bitmap  涂鸦图片
     * @return
     */
    private boolean saveImageToDisk(Bitmap bitmap){
        Bitmap bitmapTemp=BitmapUtils.combineBitmap(bitmapBG, bitmap);
        bitmaps.put(""+mode+direction,bitmapTemp);
        isTakePicture=false;
        if(cameraView.getVisibility()==View.VISIBLE)
            camera.startPreview();
        return BitmapUtils.saveImageToGallery(this, bitmapTemp, "" + mode + direction);
    }

    /**
     * 导入图片
     */
    private void importImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0x100);
    }


    /**
     * 更新显示标注信息
     */
    private void showGird(){
        if (mode == 0) {//侧面
            residentElement.setDrawType(BaseSpirit.SpiritType.SIDE);
            residentElement.takePicture();
            residentElement.requestLayout();
        } else {//正面
            residentElement.setDrawType(BaseSpirit.SpiritType.FRONT);
            residentElement.takePicture();
            residentElement.requestLayout();

        }
    }

    /**
     * 设置右下角缩略图
     */
    private void setFrontAndBgImage(String path){
        if(imageViewBG.getVisibility()==View.VISIBLE){
            GlideUtil.loadImageView(this, path, imageViewBG);
            cameraView.setVisibility(View.INVISIBLE);
        }else {
            cameraView.setVisibility(View.VISIBLE);
        }

        Bitmap bitmap=BitmapFactory.decodeFile(path);
        cache.put(""+mode+direction,bitmap);
        bitmapBG=bitmap;
        if(direction==0){
            residentFrontImage.setImageBitmap(bitmap);
        }else {
            residentBgImage.setImageBitmap(bitmap);
        }
        showGird();
    }

    /**
     * 设置右下角缩略图
     */
    private void setFrontAndBgImage(Bitmap bitmap){
        bitmapBG=bitmap;
        cache.put(""+mode+direction,bitmap);
        if(imageViewBG.getVisibility()==View.VISIBLE){
            imageViewBG.setImageBitmap(bitmap);
            cameraView.setVisibility(View.INVISIBLE);
        }else {
            cameraView.setVisibility(View.VISIBLE);
        }

        if(direction==0){
            residentFrontImage.setImageBitmap(bitmap);
        }else {
            residentBgImage.setImageBitmap(bitmap);
        }

        showGird();



    }

    /**
     * 检测程序是否缓冲图片
     * @param key
     * @return
     */
    private boolean checkedSaveImage(@NonNull String key){
        boolean status=false;
        Bitmap bitmap=cache.getAsBitmap(key);
        if(bitmap==null){
            status= false;
        }else {
            status=true;

        }
        Log.d(TAG, "checkedSaveImage: "+key+"-----"+status);
        return status;
    }

    /**
     * 跳转分析页面
     */
    private void startAnalysis(){
        Intent intent=new Intent(ResidentActivity.this, AnalysisActivity.class);
        intent.putExtra("mode",mode);
        startActivity(intent );
    }
}
