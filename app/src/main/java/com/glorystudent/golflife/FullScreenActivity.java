package com.glorystudent.golflife;

import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.glorystudent.golflibrary.base.BaseActivity;

import butterknife.Bind;

/**
 * 全屏播放视频
 * Created by hyj on 2017/3/16.
 */
public class FullScreenActivity extends BaseActivity {
    @Bind(R.id.video_surface)
    public SurfaceView video_surface;

    @Bind(R.id.iv_first_frame)
    public ImageView iv_first_frame;

    @Override
    protected int getContentId() {
        return R.layout.activity_full_screen;
    }

    @Override
    protected void init() {
        MediaPlayer mediaPlayer = new MediaPlayer();
    }
}
