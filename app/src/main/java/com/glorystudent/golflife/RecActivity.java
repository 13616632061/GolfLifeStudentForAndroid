package com.glorystudent.golflife;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glorystudent.adapter.RecPreviewRecyclerAdapter;
import com.glorystudent.entity.LocalVideoEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.FileToMD5Util;
import com.glorystudent.util.LogsUtil;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 录制
 * Created by hyj on 2016/12/29.
 */
public class RecActivity extends BaseActivity implements SurfaceHolder.Callback {
    private static final String TAG = "RecActivity";

    @Bind(R.id.cb_guide)
    public CheckBox cbGuide;
    @Bind(R.id.iv_change)
    public ImageView iv_change;
    @Bind(R.id.iv_delay)
    public ImageView iv_delay;
    @Bind(R.id.iv_light)
    public ImageView iv_light;
    @Bind(R.id.iv_filming)
    public ImageView ivFilming;
    @Bind(R.id.rl_guide)
    public RelativeLayout rlGuideLayout;
    @Bind(R.id.rl_toolbar)
    public RelativeLayout rlToolbarLayout;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.btnStartStop)
    public CheckBox mBtnStartStop;
    @Bind(R.id.surfaceview)
    public SurfaceView mSurfaceview;
    @Bind(R.id.rv_rec_preview)
    public RecyclerView recyclerView;

    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera camera;
    private String path;//视频文件的路径
    private int timeCount = 0;//计数秒
    private int cameraType = 0;//自拍切换 0默认对外录制 后置， 1自拍 前置
    private int delayCount = 0; //延时切换 0不延时 1延时5秒 2延时10秒
    private int lightCount = 0;//闪关灯切换 0不开闪光灯 1开闪光灯
    private long delayTime = 0;//延迟时长
    private int playAngle = 90;//设置录制之后的播放角度, 外摄像头设置为90度，内摄像头270度
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeCount++;
            String minuteAndSecond = TimeUtil.getMinuteAndSecond(timeCount);
            tvTime.setText(minuteAndSecond);
            handler.postDelayed(this, 1000);
        }
    };
    private Animation flickerAnimation;//图片闪烁动画
    private String roolPath;//项目根路径
    private MediaMetadataRetriever mediaMetadataRetriever;
    private SQLiteDatabase sqLiteDatabase;
    private LinkedList<LocalVideoEntity> datas;
    private RecPreviewRecyclerAdapter adapter;
    private boolean bIfPreview; //是否正在预览
    private String pathDir;//输出文件的目录
    private int screenWidth, screenHeight;

    @Override
    protected int getContentId() {
        return R.layout.activity_rec;
    }

    @Override
    protected void init() {
        //初始化闪烁动画
        initFlickerAnimation();
        //获取sd卡根路径
        roolPath = getSDPath();
        sqLiteDatabase = sqLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        //获取屏幕宽和高
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        Log.i(TAG, "init: 屏幕宽" + screenWidth + "-->高" + screenHeight);

        datas = new LinkedList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecPreviewRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        cbGuide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rlGuideLayout.setVisibility(View.VISIBLE);
                } else {
                    rlGuideLayout.setVisibility(View.GONE);
                }
            }
        });
        mBtnStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivFilming.setVisibility(View.VISIBLE);
                    ivFilming.setAnimation(flickerAnimation);
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText("00:00");
                    timeCount = 0;
                    rlToolbarLayout.setVisibility(View.GONE);
                    //开始录像
                    initRecord();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startRecording();
                        }
                    }, delayTime);
                } else {
                    //停止摄像
                    ivFilming.setAnimation(null);
                    ivFilming.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    rlToolbarLayout.setVisibility(View.VISIBLE);
                    stopRecording();
                }
            }
        });

        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initRecord() {
        mRecorder = new MediaRecorder();
        if (camera != null) {
            camera.unlock();
        }
        mRecorder.setCamera(camera);
        // 这两项需要放在setOutputFormat之前
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

//        // Set output file format
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//
//        // 这两项需要放在setOutputFormat之后
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
////            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
//        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
//
//        mRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
//        mRecorder.setVideoSize(720, 1280);
//        mRecorder.setVideoFrameRate(30);
        mRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));

        mRecorder.setOrientationHint(playAngle);
        //设置记录会话的最大持续时间（毫秒）
        mRecorder.setMaxDuration(59 * 60 * 1000);
        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
    }

    /**
     * 停止摄像
     */
    private void stopRecording() {
        try {
            handler.removeCallbacks(runnable);
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.reset();
                mRecorder.release();
                mRecorder = null;
            }
            if (camera != null) {
                camera.lock();
            }
            //保存到数据库
            saveDatabase();
            initCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始摄像
     */
    private void startRecording() {
        try {
            if (roolPath != null) {
                if (pathDir == null) {
                    pathDir = roolPath + "/golf/record/rec";
                }
                File fileDir = new File(pathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                path = pathDir + TimeUtil.getImageNameTime(Calendar.getInstance().getTime()) + ".mp4";
                mRecorder.setOutputFile(path);
                mRecorder.prepare();
                mRecorder.start();
                handler.postDelayed(runnable, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存视频到数据库
     */
    private void saveDatabase() {
        if (mediaMetadataRetriever == null) {
            mediaMetadataRetriever = new MediaMetadataRetriever();
        }
        if (path != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", "1");//1拍摄的视频，2本地导入的视频，3正在合成的视频
            contentValues.put("path", path);
            contentValues.put("date", RequestUtil.getCurrentTime());

            String fileMD5 = FileToMD5Util.getFileMD5(new File(path));
            contentValues.put("fileMd5", fileMD5);

            mediaMetadataRetriever.setDataSource(path);
            String durationMs = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            String durationTime = TimeUtil.getToDay(Integer.valueOf(durationMs));
            contentValues.put("duration", durationTime);

            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);//获取第一帧图片
//            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            contentValues.put("picBytes", out.toByteArray());
//            }
            sqLiteDatabase.insert("videoModel", null, contentValues);
            //添加一条数据
            String recPath = new String(path);
            addDatas(recPath, durationTime, bitmap);
        }
    }

    /**
     * 添加一条数据显示
     */
    private void addDatas(String path, String time, Bitmap bitmap) {
        LocalVideoEntity localVideoEntity = new LocalVideoEntity();
        localVideoEntity.setBitmap(bitmap);
        localVideoEntity.setPath(path);
        localVideoEntity.setTime(time);
        datas.addFirst(localVideoEntity);
        adapter.setDatas(datas);
    }

    /**
     * 图片闪动动画
     */

    private void initFlickerAnimation() {
        flickerAnimation = new AlphaAnimation(1, 0);
        flickerAnimation.setDuration(500);//闪烁时间间隔
        flickerAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        flickerAnimation.setRepeatCount(Animation.INFINITE);
        flickerAnimation.setRepeatMode(Animation.REVERSE);
    }

    /**
     * 获取根路径
     *
     * @return
     */
    public String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getPath();// 获取跟目录
        }
        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
        camera = Camera.open(cameraType);
        try {
            Log.i(TAG, "SurfaceHolder.Callback：surface Created");
            camera.setPreviewDisplay(surfaceHolder);//set the surface to be used for live preview
        } catch (Exception ex) {
            //报错则释放资源
            if (null != camera) {
                camera.release();
                camera = null;
            }
            Log.i(TAG + "initCamera", ex.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = surfaceHolder;
        initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceview = null;
        mSurfaceHolder = null;
        handler.removeCallbacks(runnable);
        releaseResource();
    }

    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    @OnClick({R.id.back, R.id.iv_change, R.id.iv_delay, R.id.iv_light})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.iv_change:
                //切换自拍
                cameraType++;
                if (cameraType > 1) {
                    cameraType = 0;
                }
                switch (cameraType) {
                    case 0:
                        //对外录制
                        playAngle = 90;
                        iv_light.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onclick: 外置");
                        break;
                    case 1:
                        //自拍录制
                        playAngle = 270;
                        lightCount = 0;
                        iv_light.setImageResource(R.drawable.nav_vcr_closelight);
                        iv_light.setVisibility(View.INVISIBLE);
                        LogsUtil.d(TAG, "内置");
                        break;
                }
                if (camera != null) {
                    camera.release();
                    camera = null;
                }
                initCamera();
                break;
            case R.id.iv_delay:
                //定时
                delayCount++;
                switch (delayCount % 3) {
                    case 0:
                        //不定时
                        iv_delay.setImageResource(R.drawable.nav_vcr_delay);
                        delayTime = 0;
                        break;
                    case 1:
                        //定时5秒
                        iv_delay.setImageResource(R.drawable.nav_vcr_delay5);
                        delayTime = 5 * 1000;//毫秒
                        break;
                    case 2:
                        //定时10秒
                        iv_delay.setImageResource(R.drawable.nav_vcr_delay10);
                        delayTime = 10 * 1000;//毫秒
                        break;
                }
                break;
            case R.id.iv_light:
                //闪光灯
                lightCount++;
                switch (lightCount % 2) {
                    case 0:
                        //不开闪光灯
                        iv_light.setImageResource(R.drawable.nav_vcr_closelight);
                        closeLightOff();
                        break;
                    case 1:
                        //开闪光灯
                        iv_light.setImageResource(R.drawable.nav_vcr_light);
                        openLightOn();
                        break;
                }
                break;
        }
    }

    private void initCamera() {
        try {
            if (camera == null) {
                Log.i(TAG, "initCamera: 执行");
                camera = Camera.open(cameraType);
                camera.setPreviewDisplay(mSurfaceHolder);
            }
            if (bIfPreview) {
//            camera.setPreviewCallback(null);
                camera.stopPreview();
                bIfPreview = false;
            }
//            camera.setDisplayOrientation(90);
            camera.setDisplayOrientation(getPreviewDegree(RecActivity.this));
            camera.startPreview();
            bIfPreview = true;
        } catch (IOException e) {
            //报错则释放资源
            if (null != camera) {
                camera.release();
                camera = null;
            }
            Log.i(TAG + "initCamera", e.getMessage());
        }
    }

    private void openLightOn() {
        try {
            if (camera != null) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
            }
        } catch (Exception e) {
            if (camera != null) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
        }
    }

    private void closeLightOff() {
        try {
            if (camera != null) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
            }
        } catch (Exception e) {
            if (camera != null) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseResource();
    }

    /**
     * 关闭页面
     */
    private void close() {
        EventBus.getDefault().post(EventBusMapUtil.getStringMap("LocalVideoFragment", "refresh"));
        finish();
        overridePendingTransition(R.anim.activity_no_anim, R.anim.activity_top_bottom);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 释放资源
     */
    private void releaseResource() {
        if (null != camera) {
            camera.setPreviewCallback(null); //！！这个必须在前，不然退出出错
            camera.stopPreview();
            bIfPreview = false;
            camera.release();
            camera = null;
        }
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }
}