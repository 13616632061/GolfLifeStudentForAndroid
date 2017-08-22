package com.glorystudent.golflife;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.glorystudent.entity.DrawType;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.widget.DrawDesignView;
import com.glorystudent.widget.NumberSeekBar;
import com.glorystudent.widget.ScaleLineView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Gavin.J on 2017/8/1.
 */

public class VideoContrastActivity extends BaseActivity implements TextureView.SurfaceTextureListener,
        RadioGroup.OnCheckedChangeListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "VideoContrastActivity";
    @Bind(R.id.texture_one)
    TextureView textureOne;
    @Bind(R.id.tv_add_video_one)
    TextView tvAddVideoOne;
    @Bind(R.id.draw_one)
    DrawDesignView drawOne;
    @Bind(R.id.texture_two)
    TextureView textureTwo;
    @Bind(R.id.tv_add_video_two)
    TextView tvAddVideoTwo;
    @Bind(R.id.draw_two)
    DrawDesignView drawTwo;
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.contrast)
    ImageView contrast;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.iv_combine)
    ImageView ivCombine;
    @Bind(R.id.seek_bar_one)
    NumberSeekBar seekBarOne;
    @Bind(R.id.scale_one)
    ScaleLineView scaleOne;
    @Bind(R.id.seek_bar_two)
    NumberSeekBar seekBarTwo;
    @Bind(R.id.scale_two)
    ScaleLineView scaleTwo;
    @Bind(R.id.rl_wheel_one)
    RelativeLayout rlWheelOne;
    @Bind(R.id.iv_combine_green)
    ImageView ivCombineGreen;
    @Bind(R.id.scale_three)
    ScaleLineView scaleThree;
    @Bind(R.id.rl_wheel_two)
    RelativeLayout rlWheelTwo;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.iv_return)
    ImageView ivReturn;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.iv_pen)
    ImageView ivPen;
    @Bind(R.id.iv_packup)
    ImageView ivPackup;
    @Bind(R.id.rb_line)
    RadioButton rbLine;
    @Bind(R.id.rb_circle)
    RadioButton rbCircle;
    @Bind(R.id.rb_angle)
    RadioButton rbAngle;
    @Bind(R.id.rb_arrow)
    RadioButton rbArrow;
    @Bind(R.id.rb_curve)
    RadioButton rbCurve;
    @Bind(R.id.rb_rect)
    RadioButton rbRect;
    @Bind(R.id.rg_shape)
    RadioGroup rgShape;
    @Bind(R.id.iv_pen_color)
    ImageView ivPenColor;
    @Bind(R.id.ll_more)
    LinearLayout llMore;
    @Bind(R.id.rl_right)
    RelativeLayout rlRight;
    @Bind(R.id.rb_yellow)
    RadioButton rbYellow;
    @Bind(R.id.rb_red)
    RadioButton rbRed;
    @Bind(R.id.rb_green)
    RadioButton rbGreen;
    @Bind(R.id.rb_blue)
    RadioButton rbBlue;
    @Bind(R.id.rg_color)
    RadioGroup rgColor;
    private String path1;
    private boolean colorFlag;
    private Surface surface;
    private MediaPlayer mediaPlayerOne;
    private float mDensity;
    private int currentProgressOne;
    private int durationOne;

    @Override
    protected int getContentId() {
        return R.layout.activity_video_contrast;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        path1 = intent.getStringExtra("path");

        mDensity = getResources().getDisplayMetrics().density;

        textureOne.setSurfaceTextureListener(this);
        textureTwo.setSurfaceTextureListener(this);
        rgColor.setOnCheckedChangeListener(this);
        rgShape.setOnCheckedChangeListener(this);
    }

    //横竖屏切换执行的方法
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //横屏

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //竖屏

        }
    }

    @OnClick({R.id.tv_add_video_one, R.id.tv_add_video_two, R.id.close, R.id.contrast, R.id.cancel, R.id.iv_combine,
            R.id.iv_combine_green, R.id.iv_delete, R.id.iv_return, R.id.iv_pen, R.id.iv_packup, R.id.iv_pen_color})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_video_one:
                //添加视频

                break;
            case R.id.tv_add_video_two:
                //更换视频

                break;
            case R.id.close:
                //关闭
                break;
            case R.id.contrast:
                //对比

                break;
            case R.id.cancel:
                //取消

                break;
            case R.id.iv_combine:
                //合并

                break;
            case R.id.iv_combine_green:
                //分开

                break;
            case R.id.iv_delete:
                //删除

                break;
            case R.id.iv_return:
                //撤销

                break;
            case R.id.iv_pen:
                //打开画笔

                break;
            case R.id.iv_packup:
                //关闭画笔

                break;
            case R.id.iv_pen_color:
                //打开颜色

                break;
        }
    }

    /**
     * 初始化播放器
     */
    private void initMediaPlayer() {
        try {
            if (mediaPlayerOne == null) {
                mediaPlayerOne = new MediaPlayer();
            }
            mediaPlayerOne.reset();
            //设置资源文件，绑定视图，准备播放
            mediaPlayerOne.setDataSource(path1);
            mediaPlayerOne.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置视频流类型
//            mediaPlayerOne.setDisplay(surfaceHolder);
            mediaPlayerOne.setSurface(surface);
            mediaPlayerOne.prepareAsync();
            mediaPlayerOne.setOnPreparedListener(this);
            mediaPlayerOne.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放器准备完成的方法
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayerOne.seekTo(currentProgressOne);
        //获取播放时长
        durationOne = mediaPlayerOne.getDuration();
        seekBarOne.setTextColor(getResources().getColor(R.color.colorBlack6));
        seekBarOne.setTextSize((int) (16 * mDensity));
        seekBarOne.setProgressText(getProgressTime(currentProgressOne));
        seekBarOne.setMax(durationOne);
        seekBarOne.setOnSeekBarChangeListener(this);
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
     * 播放完成的回调
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        mediaPlayerOne.pause();
        mediaPlayerOne.seekTo(0);
        seekBarOne.setProgress(0);
        seekBarOne.setProgressText(getProgressTime(0));
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
        textureOne = null;
        textureTwo = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

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
                        drawOne.setColor(Color.YELLOW);
                        drawTwo.setColor(Color.YELLOW);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_yellow);
                        break;
                    case R.id.rb_red:
                        drawOne.setColor(Color.RED);
                        drawTwo.setColor(Color.RED);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_red);
                        break;
                    case R.id.rb_green:
                        drawOne.setColor(Color.GREEN);
                        drawTwo.setColor(Color.GREEN);
                        ivPenColor.setImageResource(R.drawable.btn_vcr_green);
                        break;
                    case R.id.rb_blue:
                        drawOne.setColor(Color.BLUE);
                        drawTwo.setColor(Color.BLUE);
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
                        drawOne.setDrawType(DrawType.line);
                        drawTwo.setDrawType(DrawType.line);
                        break;
                    case R.id.rb_circle:
                        //画圆
                        drawOne.setDrawType(DrawType.circle);
                        drawTwo.setDrawType(DrawType.circle);
                        break;
                    case R.id.rb_angle:
                        //画角度
                        drawOne.setDrawType(DrawType.angle);
                        drawTwo.setDrawType(DrawType.angle);
                        break;
                    case R.id.rb_arrow:
                        //画箭头
                        drawOne.setDrawType(DrawType.arrow);
                        drawTwo.setDrawType(DrawType.arrow);
                        break;
                    case R.id.rb_curve:
                        //画曲线
                        drawOne.setDrawType(DrawType.curve);
                        drawTwo.setDrawType(DrawType.curve);
                        break;
                    case R.id.rb_rect:
                        //画矩形
                        drawOne.setDrawType(DrawType.rect);
                        drawTwo.setDrawType(DrawType.rect);
                        break;
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
