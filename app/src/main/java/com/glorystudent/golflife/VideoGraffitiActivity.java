package com.glorystudent.golflife;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.DrawViewEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.golflibrary.util.FileZipUtils;
import com.glorystudent.golflibrary.widget.pickerscrollview.ScrollViewCustom;
import com.glorystudent.util.AudioRecoderUtils;
import com.glorystudent.util.ColorUtil;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.FileCopyUtil;
import com.glorystudent.util.FileToMD5Util;
import com.glorystudent.util.FileUtil;
import com.glorystudent.util.GetAmrDuration;
import com.glorystudent.util.ImageUtil;
import com.glorystudent.util.LogsUtil;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 视频涂鸦
 * Created by hyj on 2017/2/24.
 */
public class VideoGraffitiActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, SurfaceHolder.Callback, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener, AudioRecoderUtils.OnAudioStatusUpdateListener {
    private final static String TAG = "VideoGraffitiActivity";
    @Bind(R.id.video_surface)
    public SurfaceView video_surface;

    @Bind(R.id.iv_first_frame)
    public ImageView iv_first_frame;

    @Bind(R.id.cb_play)
    public CheckBox cb_play;

    @Bind(R.id.cb_record)
    public CheckBox cb_record;

    @Bind(R.id.seek)
    public SeekBar seek;

    @Bind(R.id.tv_playing)
    public TextView tv_playing;

    @Bind(R.id.tv_sum)
    public TextView tv_sum;

    @Bind(R.id.ll_more)
    public LinearLayout ll_more;

    @Bind(R.id.rg_color)
    public RadioGroup rg_color;

    @Bind(R.id.iv_pen)
    public ImageView iv_pen;

    @Bind(R.id.rg_shape)
    public RadioGroup rg_shape;

    @Bind(R.id.iv_pen_color)
    public ImageView iv_pen_color;

    @Bind(R.id.ll_add_frame)
    public LinearLayout ll_add_frame;

    @Bind(R.id.hsv)
    public ScrollViewCustom hsv;

    @Bind(R.id.tv_slow_down)
    public TextView tv_slow_down;

    @Bind(R.id.ll_left)
    public LinearLayout ll_left;

    @Bind(R.id.rl_rec)
    public RelativeLayout rl_rec;


    private MediaPlayer mp;
    private String path;
    private boolean penFlag = false;
    private boolean colorFlag = false;
    private String duration;
    private final static int START = 0x001;
    private final static int STOP = 0x002;
    private final static int START_SCREENSHOT = 0x003;
    private final static int STOP_SCREENSHOT = 0x004;
    private boolean stopFlag = false;//是否暂停计时 false为不暂停，true为暂停
    private boolean playingFlag = false;//是否正在播放 false为未播放，true为正在播放
    private int savePaintColor = Color.YELLOW;
    private float voicePlayTime = 0;
    private int drawLineCount = 0;
    private int drawCircleCount = 0;
    private int drawAngleCount = 0;
    private int drawArrowCount = 0;
    private int drawCurCount = 0;
    private int drawRectCount = 0;
    private long stime, etime;
    private boolean screenshotFlag = true;
    private int screenshotCount = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    if (!stopFlag) {
                        if (isPlayVoice) {
                            stime = System.currentTimeMillis();
                            int currentPosition = voiceMp.getCurrentPosition();
                            seek.setProgress(currentPosition);
                            tv_playing.setText(TimeUtil.getMinuteAndSecond((voiceMp.getCurrentPosition()) / 1000));
                            voicePlayTime = (float) (currentPosition / 1000.0);
                            for (int i = 0; i < drawViewDatas.getVoiceTimeArr().size(); i++) {
                                Float voiceTime = drawViewDatas.getVoiceTimeArr().get(i).getBeginTime();
                                if (voiceTime >= voicePlayTime && voiceTime <= (voicePlayTime + 0.1)) {
                                    Integer type = drawViewDatas.getDrawTypeArr().get(i);
                                    String color = drawViewDatas.getDrawColorArr().get(i);
                                    int intColor = ColorUtil.stringToColor(color);
                                    drawView(type, intColor);
                                }
                            }
                            float voiceProgressTime;
                            for (int i = 0; i < drawViewDatas.getVoiceArr().size(); i++) {
                                voiceProgressTime = drawViewDatas.getVoiceArr().get(i).getVoiceTime();
                                if (voiceProgressTime >= voicePlayTime && voiceProgressTime <= (voicePlayTime + 0.1)) {
                                    mp.seekTo((int) (drawViewDatas.getVoiceArr().get(i).getVideoProgress() * mpDuration));
                                }
                            }
                            etime = System.currentTimeMillis();
                            handler.sendEmptyMessageDelayed(START, 100);
                        } else {
                            try {
                                seek.setProgress(mp.getCurrentPosition());
                                tv_playing.setText(TimeUtil.getMinuteAndSecond((mp.getCurrentPosition()) / 1000));
                                handler.sendEmptyMessageDelayed(START, 100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    break;
                case STOP:
                    stopFlag = true;
                    break;
                case START_SCREENSHOT:
                    if (screenshotFlag) {
                        long start = System.currentTimeMillis();
                        int i = screenshotCount * 1;
                        MediaMetadataRetriever media = new MediaMetadataRetriever();
                        media.setDataSource(path);
                        //获取第一帧
                        bitmap = media.getFrameAtTime(i * 1000 * 1000 / 30, MediaMetadataRetriever.OPTION_CLOSEST);
                        ImageUtil.saveBitmapToJPG(bitmap, screenshotCount + "");
                        long end = System.currentTimeMillis();
                        Log.d(TAG, "测试时间handleMessage: ---->" + mp.getDuration() + " " + (end - start) + i);
                        screenshotCount++;
                        handler.sendEmptyMessageDelayed(START_SCREENSHOT, 33);
                    }
                    break;
                case STOP_SCREENSHOT:
                    screenshotFlag = false;
                    break;
            }
        }
    };
    private SurfaceHolder holder;
    private Paint paint;
//    private DrawPanelView dp;
    private PopupWindow window;
    private int mPosX;
    private int mPosY;
    private int mCurrentPosX;
    private int mCurrentPosY;
    private String sdCardPath;
    private AudioRecoderUtils audioRecoderUtils;
    private SQLiteDatabase sqLiteDatabase;
    private Bitmap bitmap;
    private String title;
    private MediaPlayer voiceMp;
    private boolean isPlayVoice = false;
    private String zippath;
    private String mp3Path;
    private DrawViewEntity drawViewDatas;
    private int mpDuration;
    private int timeCount;
    private int c;
    private MediaMetadataRetriever media;


    @Override
    protected int getContentId() {
        return R.layout.activity_video_graffiti;
    }

    @Override
    protected void init() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        sdCardPath = ImageUtil.getSDCardPath();
        audioRecoderUtils = new AudioRecoderUtils(sdCardPath);
        audioRecoderUtils.setOnAudioStatusUpdateListener(this);
        hsv.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        dp = (DrawPanelView) findViewById(R.id.dp);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        String videopath = intent.getStringExtra("videopath");
        title = intent.getStringExtra("title");
        String type = intent.getStringExtra("type");
        zippath = intent.getStringExtra("zippath");
        if (type != null && type.equals("2")) {
            //已经分析过的视频
            isPlayVoice = true;
            cb_record.setVisibility(View.GONE);
            ll_left.setVisibility(View.GONE);
            iv_pen.setVisibility(View.GONE);
            try {
                String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera";
                FileZipUtils.upZipFile(new File(zippath), filePath);
                ArrayList<String> entriesNames = FileZipUtils.getEntriesNames(new File(zippath));
                String file1 = entriesNames.get(0);
                String file2 = entriesNames.get(1);
                String textFile = null;
                String mp3File = null;
                if (file1.contains(".txt")) {
                    String name = new File(file1).getName();
                    textFile = filePath + "/" + name;
                    String mp3name = new File(file2).getName();
                    mp3File = filePath + "/" + mp3name;
                } else if (file2.contains(".txt")) {
                    String name = new File(file2).getName();
                    textFile = filePath + "/" + name;
                    String mp3name = new File(file1).getName();
                    mp3File = filePath + "/" + mp3name;
                }
                mp3Path = mp3File;


                String s = FileUtil.readFile(textFile);
                JSONObject jo = new JSONObject(s);
                drawViewDatas = new Gson().fromJson(jo.toString(), DrawViewEntity.class);
                if (drawViewDatas != null) {
//                    dp.setCalculatedParameters((int) drawViewDatas.getCurrentDrawViewWidth(), (int) drawViewDatas.getCurrentDrawViewHeight(), drawViewDatas.getTransformToPTRate());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (videopath != null) {
            path = videopath;
        }
        if (path != null) {

            media = new MediaMetadataRetriever();
            media.setDataSource(path);
            //获取第一帧
            bitmap = media.getFrameAtTime(0);

            if (isPlayVoice) {
                MediaMetadataRetriever media2 = new MediaMetadataRetriever();
                media2.setDataSource(mp3Path);
                duration = media2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            } else {
                duration = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                LogsUtil.d(TAG, duration);
            }
            iv_first_frame.setImageBitmap(bitmap);
        }
        voiceMp = new MediaPlayer();
        voiceMp.setOnCompletionListener(this);
        try {
            if (isPlayVoice) {
                voiceMp.setDataSource(mp3Path);
                voiceMp.prepare();
                int duration = voiceMp.getDuration();
                seek.setMax(duration);
                tv_sum.setText(TimeUtil.getMinuteAndSecond(duration / 1000));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp = new MediaPlayer();

        mp.setOnCompletionListener(this);
        holder = video_surface.getHolder();

        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cb_play.setOnCheckedChangeListener(this);
        cb_record.setOnCheckedChangeListener(this);
        seek.setOnSeekBarChangeListener(this);
        setPaint();
        rg_shape.setOnCheckedChangeListener(this);
        rg_color.setOnCheckedChangeListener(this);


        for (int i = 0; i < 100; i++) {
            if (i % 5 == 0) {
                View inflate1 = LayoutInflater.from(this).inflate(R.layout.item_long_frame_layout, null);
                ll_add_frame.addView(inflate1);
            } else {
                View inflate2 = LayoutInflater.from(this).inflate(R.layout.item_short_frame_layout, null);
                ll_add_frame.addView(inflate2);
            }
        }
        timeCount = 0;

        hsv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    iv_first_frame.setVisibility(View.GONE);
                    mPosX = (int) event.getX();
                    mPosY = (int) event.getY();
                }
                if (MotionEvent.ACTION_MOVE == event.getAction()) {
                    int width = ll_add_frame.getChildAt(0).getWidth();

                    mCurrentPosX = (int) event.getX();
                    mCurrentPosY = (int) event.getY();
                    int dis = mCurrentPosX - mPosX;
                    int duration = mp.getCurrentPosition();
                    if (c > mp.getDuration()) {
                        c = 0;
                    }
                    if (c < 0) {
                        c = mp.getDuration();
                    }

                    c += (dis);
                    mp.seekTo(c);
                    seek.setProgress(c);
                    mPosX = mCurrentPosX;
                    mPosY = mCurrentPosY;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hsv.startScrollerTask();
                }

                return false;
            }
        });

        hsv.setOnScrollStopListner(new ScrollViewCustom.OnScrollStopListner() {
            @Override
            public void onScrollStoped() {

            }

            @Override
            public void onScrollToLeftEdge() {
                hsv.scrollTo(1000, 0);
            }

            @Override
            public void onScrollToRightEdge() {
                Log.d(TAG, "onScrollToRightEdge: 到最右了");
                hsv.scrollTo(0, 0);
            }

            @Override
            public void onScrollToMiddle() {

            }
        });

//        dp.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (playingFlag) {
//                            cb_play.setChecked(false);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void setPaint() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10.0f);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @OnClick({R.id.back, R.id.iv_return, R.id.iv_delete, R.id.iv_pen, R.id.iv_pen_color, R.id.iv_packup, R.id.tv_slow_down})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.iv_return:
                //返回上一次的绘画
//                dp.undo();
//                dp.setColor(savePaintColor);
                break;
            case R.id.iv_delete:
                //清空所有的绘画
//                dp.clearAll();
//                handler.sendEmptyMessage(STOP_SCREENSHOT);
                break;
            case R.id.iv_pen:
                //打开与关闭使用笔
                iv_pen.setVisibility(View.GONE);
                ll_more.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_pen_color:
                //打开颜色板
                if (colorFlag = !colorFlag) {
                    rg_color.setVisibility(View.VISIBLE);
                } else {
                    rg_color.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_packup:
                //关闭使用笔
                ll_more.setVisibility(View.GONE);
                rg_color.setVisibility(View.GONE);
                iv_pen.setVisibility(View.VISIBLE);
                colorFlag = false;
                break;
            case R.id.tv_slow_down:
                //缩放速度
                //暂停
                if (playingFlag) {
                    cb_play.setChecked(false);
                }
                showPopwindow();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.cb_play:
                playingFlag = isChecked;
                if (isChecked) {
                    iv_first_frame.setVisibility(View.GONE);
                    stopFlag = false;
                    if (isPlayVoice) {
                        mpDuration = mp.getDuration();
                        voiceMp.start();
                        mp.start();
                        mp.seekTo(1);
                        if (voiceMp.getCurrentPosition() <= 0) {
//                            dp.clearAll();
                            clearDrawCount();
                        }
                    } else {
                        mp.start();
                    }
                    handler.sendEmptyMessage(START);
                } else {
                    //暂停
                    if (isPlayVoice) {
                        voiceMp.pause();
                    } else {
                        mp.pause();
                    }
                    handler.sendEmptyMessage(STOP);
                }
                break;
            case R.id.cb_record:
                if (isChecked) {
                    //开始录制语音
                    audioRecoderUtils.startRecord();
                    audioRecoderUtils.setContext(this);
//                    handler.sendEmptyMessage(START_SCREENSHOT);
                } else {
                    //结束录制语音
                    long recordTime = audioRecoderUtils.stopRecord();
                }
                break;
        }

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //播放
        try {
            Log.d(TAG, "surfaceCreated: ");
//            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            mp.setDataSource(path);//设置要播放的视频
            mp.setDisplay(holder);//设置将视频画面输出到SurfaceView
            mp.prepare();    //预加载视频
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isPlayVoice) {
            int duration = mp.getDuration();
            seek.setMax(duration);
            tv_sum.setText(TimeUtil.getMinuteAndSecond(duration / 1000));
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        seek.setProgress(0);
        if (isPlayVoice) {
            voiceMp.seekTo(0);
        } else {
            mp.seekTo(0);    //重置MediaPlayer对象
        }

        cb_play.setChecked(false);
        playingFlag = false;
        handler.sendEmptyMessage(STOP);
        tv_playing.setText("00:00");
    }

    boolean isTouch = false;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (isTouch) {
            c = progress;
            if (isPlayVoice) {
                clearDrawCount();
//                dp.clearAll();
                float voiceTime = (float) (progress / 1000.0);
                for (int i = 0; i < drawViewDatas.getVoiceTimeArr().size(); i++) {
                    if (drawViewDatas.getVoiceTimeArr().get(i).getBeginTime() <= voiceTime) {
                        Integer type = drawViewDatas.getDrawTypeArr().get(i);
                        String s = drawViewDatas.getDrawColorArr().get(i);
                        int color = ColorUtil.stringToColor(s);
                        drawView(type, color);
                    }
                }
                voiceMp.seekTo(progress);
            } else {
                mp.seekTo(progress);
            }
            tv_playing.setText(TimeUtil.getMinuteAndSecond(progress / 1000));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        iv_first_frame.setVisibility(View.GONE);
        isTouch = true;
        if (playingFlag) {
            if (isPlayVoice) {
                voiceMp.pause();
            } else {
                mp.pause();
            }
            handler.sendEmptyMessage(STOP);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouch = false;
        stopFlag = false;
        if (playingFlag) {
            if (isPlayVoice) {
                voiceMp.start();
            } else {
                mp.start();
            }
            handler.sendEmptyMessage(START);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id = group.getId();
        switch (id) {
            case R.id.rg_shape:
                switch (checkedId) {
                    case R.id.rb_line:
                        //画直线
//                        dp.dt = DrawType.straight;
                        break;
                    case R.id.rb_cricle:
                        //画圆
//                        dp.dt = DrawType.circle;
                        break;
                    case R.id.rb_angle:
                        //画角度
//                        dp.dt = DrawType.angle;
                        break;
                    case R.id.rb_arrow:
                        //画箭头
//                        dp.dt = DrawType.arrows;
                        break;
                    case R.id.rb_sline:
                        //画曲线
//                        dp.dt = DrawType.curve;
                        break;
                    case R.id.rb_rectangle:
                        //画矩形
//                        dp.dt = DrawType.rectangle;
                        break;
                }
                break;
            case R.id.rg_color:
                switch (checkedId) {
                    case R.id.rb_yellow:
//                        dp.setColor(Color.YELLOW);
                        savePaintColor = Color.YELLOW;
                        iv_pen_color.setImageResource(R.drawable.btn_vcr_yellow);
                        break;
                    case R.id.rb_red:
//                        dp.setColor(Color.RED);
                        savePaintColor = Color.RED;
                        iv_pen_color.setImageResource(R.drawable.btn_vcr_red);
                        break;
                    case R.id.rb_green:
//                        dp.setColor(Color.GREEN);
                        savePaintColor = Color.GREEN;
                        iv_pen_color.setImageResource(R.drawable.btn_vcr_green);
                        break;
                    case R.id.rb_blue:
//                        dp.setColor(Color.BLUE);
                        savePaintColor = Color.BLUE;
                        iv_pen_color.setImageResource(R.drawable.btn_vcr_blue);
                        break;
                }
                colorFlag = false;
                rg_color.setVisibility(View.GONE);
                break;
        }

    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View

        View view = LayoutInflater.from(this).inflate(R.layout.popu_slow_down_layout, null);
        TextView tv_11 = (TextView) view.findViewById(R.id.tv_11);
        TextView tv_21 = (TextView) view.findViewById(R.id.tv_21);
        TextView tv_41 = (TextView) view.findViewById(R.id.tv_41);
        TextView tv_81 = (TextView) view.findViewById(R.id.tv_81);
        tv_11.setOnClickListener(this);
        tv_21.setOnClickListener(this);
        tv_41.setOnClickListener(this);
        tv_81.setOnClickListener(this);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 在底部显示
        TextView tv_slow_down = (TextView) findViewById(R.id.tv_slow_down);
        int[] location = new int[2];
        tv_slow_down.getLocationInWindow(location);
        window.showAsDropDown(VideoGraffitiActivity.this.findViewById(R.id.tv_slow_down), 0, -DensityUtil.dip2px(this, 100));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_11:
                window.dismiss();
                tv_slow_down.setText("1/1");
                break;
            case R.id.tv_21:
                window.dismiss();
                tv_slow_down.setText("1/2");
                break;
            case R.id.tv_41:
                window.dismiss();
                tv_slow_down.setText("1/4");
                break;
            case R.id.tv_81:
                window.dismiss();
                tv_slow_down.setText("1/8");
                break;
        }
        cb_play.setChecked(true);
    }

    @Override
    public void onUpdate(double db, long time) {
        float progress = (float) (mp.getCurrentPosition() / (mp.getDuration() * 1.0));
        float voiceTime = (float) (time / 1000.0);
//        dp.setCurrentTime(progress, voiceTime);
    }

    @Override
    public void onStop(String filePath) {
        try {
            long amrDuration = GetAmrDuration.getAmrDuration(new File(filePath));
            if (amrDuration > 1) {
                long t = audioRecoderUtils.getSaveTime() / 1000;
                if (t >= 1) {
//                    DrawViewEntity drawViewEntity = dp.getDrawViewEntity();
//                    int width = dp.getWidth();
//                    int height = dp.getHeight();
//                    drawViewEntity.setCurrentDrawViewWidth(width);
//                    drawViewEntity.setCurrentDrawViewHeight(height);
//                    drawViewEntity.setTransformToPTRate(0.5f);
//                    String str = new Gson().toJson(drawViewEntity);
                    String[] split = filePath.split("\\.");
                    String textPath = split[0] + ".txt";
//                    FileUtil.saveFile(str, textPath);
                    List<File> fileList = new ArrayList<>();
                    fileList.add(new File(filePath));
                    fileList.add(new File(textPath));
                    File zipPath = new File(split[0] + ".zip");
                    if (!zipPath.exists()) {
                        zipPath.createNewFile();
                    }
                    String newPath = getFilesDir() + "/" + System.currentTimeMillis() + ".mp4";
                    FileCopyUtil.copyFile(path, newPath);

                    FileZipUtils.zipFiles(fileList, zipPath);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("type", "2");// 1 是未分析的视频  2 是分析过的视频
                    contentValues.put("picname", new File(newPath).getName());
                    contentValues.put("path", newPath);
                    contentValues.put("time", TimeUtil.getToDay(Integer.valueOf(duration)));
                    contentValues.put("date", RequestUtil.getCurrentTime());
                    contentValues.put("zippath", zipPath.getPath());
                    contentValues.put("filemd5", FileToMD5Util.getFileMD5(new File(newPath)) + "_" + FileToMD5Util.getFileMD5(zipPath));
                    if (title == null) {
                        title = "";
                    }
                    contentValues.put("title", "分析视频 " + title);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    contentValues.put("pic", out.toByteArray());
                    sqLiteDatabase.insert("img", null, contentValues);
                    EventBus.getDefault().post(EventBusMapUtil.getIntMap(6, 1));
                    finish();
                } else {
                    Toast.makeText(VideoGraffitiActivity.this, "发送语音不能少于1秒", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawView(int type, int intColor) {
        switch (type) {
            case 1:
//                dp.drawCurve(drawViewDatas.getCurveArr().get(drawCurCount), intColor);
                drawCurCount++;
                break;
            case 2:
//                dp.drawLine(drawViewDatas.getLineArr().get(drawLineCount), intColor);
                drawLineCount++;
                break;
            case 3:
//                dp.drawArrow(drawViewDatas.getArrowArr().get(drawArrowCount), intColor);
                drawArrowCount++;
                break;
            case 4:
//                dp.drawAngle(drawViewDatas.getAngelArr().get(drawAngleCount), intColor);
                drawAngleCount++;
                break;
            case 5:
//                dp.drawRct(drawViewDatas.getRectArr().get(drawRectCount), intColor);
                drawRectCount++;
                break;
            case 6:
//                dp.drawCircle(drawViewDatas.getCircleArr().get(drawCircleCount), intColor);
                drawCircleCount++;
                break;
        }
    }

    private void clearDrawCount() {
        drawLineCount = 0;
        drawCircleCount = 0;
        drawAngleCount = 0;
        drawArrowCount = 0;
        drawCurCount = 0;
        drawRectCount = 0;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            handler.sendEmptyMessage(STOP);
            mp.release();
        }

        if (voiceMp != null) {
            handler.sendEmptyMessage(STOP);
            mp.release();
        }
    }
}
