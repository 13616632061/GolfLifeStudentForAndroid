package com.glorystudent.golflife;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.glorystudent.entity.DrawShapeEntity;
import com.glorystudent.entity.DrawType;
import com.glorystudent.entity.ProgressAndShapeEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.util.FileUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.widget.DrawDesignView;
import com.glorystudent.widget.NumberSeekBar;
import com.glorystudent.widget.ScaleLineView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/6/26.
 */

public class VideoAnalyzeActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,
        RadioGroup.OnCheckedChangeListener,CompoundButton.OnCheckedChangeListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, DrawDesignView.OnShowWipeLineListener, ScaleLineView.OnValueChangeListener,
        TextureView.SurfaceTextureListener {

    private static final String TAG = "VideoAnalyzeActivity";

    @Bind(R.id.scale_layout)
    FrameLayout scaleLayout;
    //    @Bind(R.id.video_surface)
//    SurfaceView surfaceView;
    @Bind(R.id.video_surface)
    TextureView textureView;
    @Bind(R.id.dp)
    DrawDesignView drawDesignView;
    @Bind(R.id.rl_rec)
    RelativeLayout rlRec;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.iv_pen)
    ImageView ivPen;
    @Bind(R.id.rg_shape)
    RadioGroup rgShape;
    @Bind(R.id.iv_pen_color)
    ImageView ivPenColor;
    @Bind(R.id.ll_more)
    LinearLayout llMore;
    @Bind(R.id.rg_color)
    RadioGroup rgColor;
    @Bind(R.id.cb_rec)
    CheckBox cbRec;
    @Bind(R.id.oval)
    ImageView oval;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.contrast)
    ImageView contrast;
    @Bind(R.id.timer)
    Chronometer timer;
    @Bind(R.id.tv_play_speed)
    TextView tvPlaySpeed;
    @Bind(R.id.cb_play)
    CheckBox cbPlay;
    @Bind(R.id.seek_bar)
    NumberSeekBar numberSeekBar;
    @Bind(R.id.scale_line)
    ScaleLineView scaleLineView;
    private SQLiteDatabase sqLiteDatabase;
    private String path;
    private String title;
    private String content;
    private MediaPlayer mediaPlayer;
    private boolean colorFlag;//控制颜色选择的显示
    private static final int START = 0x001;
    private static final int PAUSE = 0x002;
    private int downValue;//刻度线按下时的值;

    private int currentProgress;//当前进度
    private int duration;//播放总时长
    private int downProgress;//按下刻度时的进度
    private double percent;//移动刻度时的移动百分比
    private List<ProgressAndShapeEntity> progressAndShapeEntities = new ArrayList<>();
    private float mDensity;//屏幕分辨率

    private Handler playHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        Log.i(TAG, "handleMessage: 进度" + mediaPlayer.getCurrentPosition());
                        currentProgress = mediaPlayer.getCurrentPosition();
                        numberSeekBar.setProgress(currentProgress);
                        numberSeekBar.setProgressText(getProgressTime(currentProgress));
                        playHandler.sendEmptyMessageDelayed(START, 50);
                    }
                    break;
                case PAUSE:
                    break;
            }
            return true;
        }
    });
    private Handler recordHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ProgressAndShapeEntity entity = new ProgressAndShapeEntity();
            entity.setProgress(mediaPlayer.getCurrentPosition());
            entity.setTimeStamp(timeStamp);
            List<DrawShapeEntity> shapeEntities = new ArrayList<>();
            shapeEntities.addAll(drawDesignView.getShapeList());
            entity.setShapeEntities(shapeEntities);
            progressAndShapeEntities.add(entity);
            Log.i("gavin", "run: " + System.currentTimeMillis());
            recordHandler.postDelayed(this, 30);
        }
    };
    private String sdPath;
    private String timeStamp;
    private MediaRecorder mediaRecorder;
    private Surface surface;
    //    private SurfaceHolder surfaceHolder;

    @Override
    protected int getContentId() {
        return R.layout.activity_analyze_video;
    }

    @Override
    protected void init() {
        //初始化库。若少了会报错！！
//        Vitamio.isInitialized(getApplicationContext());
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        sdPath = getSDPath();
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        Log.i(TAG, "init: " + path);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        mDensity = getResources().getDisplayMetrics().density;
        Log.i(TAG, "init: " + mDensity);

        textureView.setSurfaceTextureListener(this);
//        surfaceHolder = surfaceView.getHolder();
////        surfaceHolder.setFixedSize(720, 1080);
//        surfaceHolder.setKeepScreenOn(true);
//        surfaceHolder.addCallback(this);
//        // setType必须设置，要不出错.
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        surfaceHolder.setFormat(PixelFormat.RGBA_8888);

        rgColor.setOnCheckedChangeListener(this);
        rgShape.setOnCheckedChangeListener(this);
        cbPlay.setOnCheckedChangeListener(this);
        cbRec.setOnCheckedChangeListener(this);
        scaleLineView.setValueChangeListener(this);
        drawDesignView.setOnShowWipeLineListener(this);
    }

    /**
     * 初始化播放器
     */
    private void initMediaPlayer() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.reset();
            //设置资源文件，绑定视图，准备播放
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置视频流类型
//            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setSurface(surface);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_delete, R.id.iv_return, R.id.iv_pen, R.id.iv_packup, R.id.iv_pen_color, R.id.close,
            R.id.tv_play_speed, R.id.contrast, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                //删除所有画线
                drawDesignView.removeAll();
                break;
            case R.id.iv_return:
                //撤销上一次画线
                drawDesignView.revocation();
                break;
            case R.id.iv_pen:
                //打开笔形状选择
                ivPen.setVisibility(View.GONE);
                llMore.setVisibility(View.VISIBLE);
                drawDesignView.setDrawFlag(true);
                break;
            case R.id.iv_packup:
                //关闭笔形状选择
                ivPen.setVisibility(View.VISIBLE);
                llMore.setVisibility(View.GONE);
                colorFlag = false;
                rgColor.setVisibility(View.GONE);
                drawDesignView.setDrawFlag(false);
                break;
            case R.id.iv_pen_color:
                //打开颜色板
                if (colorFlag = !colorFlag) {
                    rgColor.setVisibility(View.VISIBLE);
                } else {
                    rgColor.setVisibility(View.GONE);
                }
                break;
            case R.id.close:
                //关闭页面
                close();
                break;
            case R.id.tv_play_speed:
                //播放速度选择

                break;
            case R.id.contrast:
                //视频对比
                Intent intent = new Intent(this, VideoContrastActivity.class);
                startActivity(intent);
                break;
            case R.id.share:
                //分享
                break;
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

    /**
     * 关闭页面的方法
     */
    private void close() {
        if (cbRec.isChecked()) {
            new AlertDialog(this).builder()
                    .setTitle("确认放弃录制")
                    .setNegativeButton("继续录制", null)
                    .setPositiveButton("放弃", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
        } else {
            finish();
        }
    }

    /**
     * radioGroup的单选回调
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_color:
                //画笔颜色选择
                switch (checkedId) {
                    case R.id.rb_yellow:
                        drawDesignView.setColor(Color.YELLOW);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_yellow);
                        break;
                    case R.id.rb_red:
                        drawDesignView.setColor(Color.RED);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_red);
                        break;
                    case R.id.rb_green:
                        drawDesignView.setColor(Color.GREEN);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_green);
                        break;
                    case R.id.rb_blue:
                        drawDesignView.setColor(Color.BLUE);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_blue);
                        break;
                }
                colorFlag = false;
                rgColor.setVisibility(View.GONE);
                break;
            case R.id.rg_shape:
                //画笔形状选择
                switch (checkedId) {
                    case R.id.rb_line:
                        //画直线
                        drawDesignView.setDrawType(DrawType.line);
                        break;
                    case R.id.rb_circle:
                        //画圆
                        drawDesignView.setDrawType(DrawType.circle);
                        break;
                    case R.id.rb_angle:
                        //画角度
                        drawDesignView.setDrawType(DrawType.angle);
                        break;
                    case R.id.rb_arrow:
                        //画箭头
                        drawDesignView.setDrawType(DrawType.arrow);
                        break;
                    case R.id.rb_curve:
                        //画曲线
                        drawDesignView.setDrawType(DrawType.curve);
                        break;
                    case R.id.rb_rect:
                        //画矩形
                        drawDesignView.setDrawType(DrawType.rect);
                        break;
                }
                break;
        }
    }

    /**
     * checkBox的多选回调
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_play:
                //播放与暂停
                if (isChecked) {
                    //播放
                    mediaPlayer.start();
                    //进度条更新
                    playHandler.sendEmptyMessage(START);
                } else {
                    //暂停
                    mediaPlayer.pause();
                    playHandler.sendEmptyMessage(PAUSE);
                }
                break;
            case R.id.cb_rec:
                //录制与结束
                if (isChecked) {
                    //开始录制
                    startRecord();
                } else {
                    //结束录制
                    stopRecord();
                }
                break;
        }
    }

    /**
     * 结束录制视频
     */
    private void stopRecord() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        timer.stop();
        recordHandler.removeCallbacks(runnable);
        rlRec.setBackgroundColor(getResources().getColor(R.color.colorAlphaBlack2));
        timer.setVisibility(View.GONE);
        share.setVisibility(View.VISIBLE);
        contrast.setVisibility(View.VISIBLE);
        Log.i("gavin", "onCheckedChanged: " + progressAndShapeEntities.size());
        showWaiting();
        //提取保存的数据集合到本地
        saveDataToLocal();
        //开始提取分析视频
        Map<String, List<ProgressAndShapeEntity>> map = new HashMap<>();
        map.put("Analyze", progressAndShapeEntities);
        EventBus.getDefault().post(map);
        dismissWaiting();
        finish();
    }

    /**
     * 开始录制视频
     */
    private void startRecord() {
        if (sdPath == null) {
            return;
        }
        //时间戳
        timeStamp = TimeUtil.getImageNameTime(Calendar.getInstance().getTime());
        final String srcPath = path;
        final String descPath = sdPath + "/golf/compound/" + timeStamp + "/" + "copy_video.mp4";
        String voicePath = sdPath + "/golf/compound/" + timeStamp + "/" + "audio.mp4";
        File voiceFile = new File(voicePath);
        if (!voiceFile.exists()) {
            if (!voiceFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                voiceFile.getParentFile().mkdirs();
            }
        }
        try {
            if (mediaRecorder == null) {
                mediaRecorder = new MediaRecorder();
            }
            mediaRecorder.reset();
            initMediaRecorder();
            mediaRecorder.setOutputFile(voicePath);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rlRec.setBackgroundColor(getResources().getColor(R.color.colorAlphaRed));
        timer.setVisibility(View.VISIBLE);
        contrast.setVisibility(View.GONE);
        share.setVisibility(View.GONE);
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        timer.start();
        recordHandler.postDelayed(runnable, 30);
        //copy视频源
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtil.copyFile(srcPath, descPath);
            }
        }).start();
    }

    /**
     * 初始化mediaRecorder
     */
    private void initMediaRecorder() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
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

    /**
     * 保存数据到本地
     */
    private void saveDataToLocal() {
    }

    /**
     * 播放器准备完成的方法
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.seekTo(currentProgress);
        //获取播放时长
        duration = mediaPlayer.getDuration();
        numberSeekBar.setTextColor(getResources().getColor(R.color.colorBlack6));
        numberSeekBar.setTextSize((int) (16 * mDensity));
        numberSeekBar.setProgressText(getProgressTime(currentProgress));
        numberSeekBar.setMax(duration);
        numberSeekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * 播放完成的回调
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        numberSeekBar.setProgress(0);
        numberSeekBar.setProgressText(getProgressTime(0));
        cbPlay.setChecked(false);
    }

    /**
     * 根据 毫秒数 返回 秒:毫秒
     *
     * @param time
     * @return
     */
    private String getProgressTime(int time) {
        int s = time / 1000;
        int ms = time % 1000 / 10;
        String msStr = ms + "";
        if (ms < 10) {
            msStr = "0" + ms;
        }
        return s + ":" + msStr;
    }

    /**
     * seekBar 变化回调
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            Log.i(TAG, "onProgressChanged: 进度条" + progress);
            numberSeekBar.setProgressText(getProgressTime(progress));
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(progress);
            }
            Log.i(TAG, "onProgressChanged: 视频进度" + mediaPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            cbPlay.setChecked(false);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        currentProgress = numberSeekBar.getProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 是否显示左侧清除撤销画线的控件
     *
     * @param size
     */
    @Override
    public void showWipeLine(int size) {
        if (size > 0) {
            llLeft.setVisibility(View.VISIBLE);
        } else {
            llLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 按下刻度线时的监听
     *
     * @param value
     */
    @Override
    public void onValueDown(int value) {
        this.downValue = value;
        downProgress = currentProgress;
    }

    /**
     * 移动刻度线时的监听
     *
     * @param value
     */
    @Override
    public void onValueMove(int value) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            cbPlay.setChecked(false);
        }
        percent = (value - downValue) * 10;
        int progress = (int) (downProgress + percent);
        if (progress < 0) {
            progress = duration + progress;
        } else if (progress > duration) {
            progress = progress - duration;
        }
        Log.i(TAG, "onValueMove: " + progress);
        numberSeekBar.setProgress(progress);
        numberSeekBar.setProgressText(getProgressTime(progress));
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }
        currentProgress = (int) (downProgress + percent);
    }

    @Override
    public void onValueUp(int value) {
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        surface = new Surface(surfaceTexture);
        initMediaPlayer();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        textureView = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
