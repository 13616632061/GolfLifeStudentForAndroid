package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.VideoDetailsListAdapter;
import com.glorystudent.dialog.SharePopupWindow;
import com.glorystudent.entity.CollectEntity;
import com.glorystudent.entity.CollectEntity.CollectBean;
import com.glorystudent.entity.ShareModel;
import com.glorystudent.entity.VideoEntity.ListTeachingVideoBean;
import com.glorystudent.entity.VideoReviewEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.widget.PullableNoUpListView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 视频播放详情
 * Created by hyj on 2016/12/30.
 */
public class VideoDetailsActivity extends BaseActivity implements View.OnClickListener, OkGoRequest.OnOkGoUtilListener, AdapterView.OnItemClickListener, VideoDetailsListAdapter.OnLikeCheckedChangeListener, UniversalVideoView.VideoViewCallback, PlatformActionListener {
    private final static String TAG = "VideoDetailsActivity";
    private ImageView iv_play;//播放按钮
    private TextView teaching_video_name;//视频标题
    private ImageView iv_first_frame;//第一帧

    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private  String VIDEO_URL = "https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/CoBg87lzza67JwHlppWA%3D%3D/videos/d8060db5a38e966bc6e96b8887151567.mp4";

    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;

    View mBottomLayout;
    View mVideoLayout;
    TextView mStart;

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;

    @Bind(R.id.video_details_lv)
    public PullableNoUpListView video_details_lv;

    @Bind(R.id.published)
    public LinearLayout published;

    @Bind(R.id.rl_root)
    public RelativeLayout rl_root;

    private ListTeachingVideoBean video;
    private SurfaceView sv;
    private MediaPlayer mp;
    private boolean playState = false; //true播放 false暂停
    private TextView tv_video_level;//视频等级
    private TextView tv_time_count;//总时长
    private TextView tv_video_content;//视频描述
    private ImageView back;//返回
    private ImageView video_collect;//收藏
    private ImageView video_share;//分享
    private PopupWindow window;
    private EditText et_comment;
    private VideoDetailsListAdapter videoDetailsListAdapter;
    private boolean isCollect;
    private int collectid;
    private SharePopupWindow share;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };
    private OnekeyShare oks;
    private File file;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    @Override
    protected int getContentId() {
        return R.layout.activity_video_details;
    }

    @Override
    protected void init() {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        video = (ListTeachingVideoBean) bundle.getSerializable("video");
        collectid = bundle.getInt("collectid", -1);
        videoDetailsListAdapter = new VideoDetailsListAdapter(this);
        videoDetailsListAdapter.setOnLikeCheckedChangeListener(this);
        video_details_lv.setAdapter(videoDetailsListAdapter);
        video_details_lv.setOnItemClickListener(this);
        published.setOnClickListener(this);
        if (video != null) {
            VIDEO_URL = video.getTeachingvideo_path();
            initHead();
        }
    }

    /**
     * 初始化ListView的头部
     */
    private void initHead() {
        View head = LayoutInflater.from(this).inflate(R.layout.item_video_details_head, null);
        video_details_lv.addHeaderView(head);
        mVideoView = (UniversalVideoView) head.findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) head.findViewById(R.id.media_controller);
        mVideoLayout = head.findViewById(R.id.video_layout);
        mBottomLayout = head.findViewById(R.id.bottom_layout);
        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(this);


        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion ");
            }
        });


        iv_play = (ImageView) head.findViewById(R.id.iv_play);
        teaching_video_name = (TextView) head.findViewById(R.id.teaching_video_name);
        iv_first_frame = (ImageView) head.findViewById(R.id.iv_first_frame);
        tv_video_level = (TextView) head.findViewById(R.id.tv_video_level);
        tv_time_count = (TextView) head.findViewById(R.id.tv_time_count);
        tv_video_content = (TextView) head.findViewById(R.id.tv_video_content);
        back = (ImageView) head.findViewById(R.id.back);
        back.setOnClickListener(this);
        video_collect = (ImageView) head.findViewById(R.id.video_collect);
        if(collectid != -1){
            video_collect.setImageResource(R.drawable.nav_home_star_h);
            isCollect = true;
        }
        video_collect.setOnClickListener(this);
        video_share = (ImageView) head.findViewById(R.id.video_share);
        video_share.setOnClickListener(this);
        if (video != null) {
            teaching_video_name.setText(video.getTeachingvideo_tittle());
            tv_video_level.setText(video.getTeachingvideo_level());
            tv_time_count.setText((String) video.getTeachingvideo_length());
            tv_video_content.setText(video.getTeachingvideo_context());
            GlideUtil.loadImageView(this, video.getTeachingvideo_picture(), iv_first_frame);
            iv_play.setOnClickListener(this);
        }
    }



    @Override
    protected void loadDatas() {
        String url = "/api/APITVideoComment/QueryTVideoComment";
        String request = RequestUtil.queryTVideoComment(this, video.getTeachingvideo_id()+"");

        new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                iv_first_frame.setVisibility(View.GONE);
                iv_play.setVisibility(View.GONE);
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }
                mVideoView.start();
                break;

            case R.id.back:
                //返回
                finish();
                break;
            case R.id.video_collect:
                //收藏
                if (isCollect = !isCollect) {
                    //收藏
                    String url = "/api/APICollect/AddCollect";
                    String requestJson = createRequestJson();
                    OkGo.post(Constants.BASE_URL + url)
                            .tag(this)
                            .params("request", requestJson)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jo = new JSONObject(s);
                                        String statuscode = jo.getString("statuscode");
                                        String statusmessage = jo.getString("statusmessage");
                                        if(statuscode.equals("1")){
                                            collectid = jo.getInt("collectid");
                                            Toast.makeText(VideoDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                            video_collect.setImageResource(R.drawable.nav_home_star_h);
                                        }else{
                                            Toast.makeText(VideoDetailsActivity.this, "收藏失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }else{
                    //取消收藏
                    CollectEntity collectEntity = new CollectEntity();
                    CollectBean collectBean = new CollectBean();
                    collectBean.setCollectid(collectid);
                    collectEntity.setCollect(collectBean);
                    String json = new Gson().toJson(collectEntity);
                    String requestJson = RequestUtil.getRequestJson(this, json);
                    String url = "/api/APICollect/DeleteCollect";
                    OkGo.post(Constants.BASE_URL + url)
                            .tag(this)
                            .params("request", requestJson)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jo = new JSONObject(s);
                                        String statuscode = jo.getString("statuscode");
                                        String statusmessage = jo.getString("statusmessage");
                                        if(statuscode.equals("1")){
                                            Toast.makeText(VideoDetailsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                            video_collect.setImageResource(R.drawable.nav_home_star_n);
                                        }else{
                                            Toast.makeText(VideoDetailsActivity.this, "取消收藏失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
                break;
            case R.id.video_share:
                //分享
                share = new SharePopupWindow(this);
                share.setPlatformActionListener(this);
                ShareModel model = new ShareModel();
                model.setImageUrl(video.getTeachingvideo_picture());
                model.setText(video.getTeachingvideo_context());
                model.setTitle(video.getTeachingvideo_tittle());
                model.setUrl("http://www.pgagolf.cn/home/video?variable=" + video.getTeachingvideo_path());
                share.initShareParams(model);
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(this.findViewById(R.id.video_share),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                share.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.published:
                //发表评论
                showPopwindow();
                break;
        }
    }



    private void showShare(String picPath) {
        Log.d(TAG, "showPopwindow: --->点击了分享");
        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(video.getTeachingvideo_tittle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://www.pgagolf.cn/home/video?variable=" + video.getTeachingvideo_path());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(video.getTeachingvideo_context());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setVideoUrl("http://www.pgagolf.cn/home/video?variable=" + video.getTeachingvideo_path());
        oks.setImageUrl(video.getTeachingvideo_picture());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.pgagolf.cn/home/video?variable=" + video.getTeachingvideo_path());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        oks.setImagePath(picPath);
        oks.show(this);
    }



    private String createRequestJson() {
        CollectEntity collectEntity = new CollectEntity();
        CollectBean collect = new CollectBean();
        collect.setCollectobjectid(video.getTeachingvideo_id());
        collect.setCollecttype(5);
        collect.setCollectdate(RequestUtil.getCurrentTime());
        collect.setCollecturl(video.getTeachingvideo_path());
        collect.setCollecttag(video.getTeachingvideo_context());
        collect.setCollecttitle(video.getTeachingvideo_tittle());
        collect.setCollectpicurl(video.getTeachingvideo_picture());
        collectEntity.setCollect(collect);
        String json = new Gson().toJson(collectEntity);
        return RequestUtil.getRequestJson(this, json);
    }

    @Override
    public void parseDatas(String json) {
        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            if(statuscode.equals("1")){
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                VideoReviewEntity videoReviewEntity = new Gson().fromJson(jo.toString(), VideoReviewEntity.class);
                Map<Integer, Boolean> likeMap = new HashMap<>();
                List<VideoReviewEntity.ListTVideoCommentBean> listTVideoComment = videoReviewEntity.getListTVideoComment();
                List<VideoReviewEntity.ListtvideocommentlikeBean> listtvideocommentlike = videoReviewEntity.getListtvideocommentlike();
                for (VideoReviewEntity.ListtvideocommentlikeBean listtvideocommentlikeBean : listtvideocommentlike) {
                    likeMap.put(listtvideocommentlikeBean.getComment_tvideoid(), true);
                }
                if (listTVideoComment != null) {
                    videoDetailsListAdapter.setauxiliaryDatas(likeMap);
                    videoDetailsListAdapter.setDatas(listTVideoComment);
                }
            }else if(statuscode.equals("2")){
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }else {
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestFailed() {
        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
    }
    /**
     * 显示popupWindow
     */
    private void showPopwindow() {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_pop_comment, null);
        et_comment = (EditText) view.findViewById(R.id.et_comment);

        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        TextView tv_published = (TextView) view.findViewById(R.id.tv_published);
        tv_published.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_comment.getText().toString().isEmpty()) {
                    Toast.makeText(VideoDetailsActivity.this, "评论不能为空哟～", Toast.LENGTH_SHORT).show();
                }else{
                    window.dismiss();
                    String request = RequestUtil.addTVideoComment(VideoDetailsActivity.this,et_comment.getText().toString(), video.getTeachingvideo_id()+"");
                    Log.d(TAG, "onClick: -->" + request);
                    OkGo.post(Constants.BASE_URL + "/api/APITVideoComment/AddTVideoComment")
                            .tag(this)
                            .params("request", request)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jo = new JSONObject(s);
                                        String statuscode = jo.getString("statuscode");
                                        String statusmessage = jo.getString("statusmessage");
                                        if(statuscode.equals("1")){
                                            Log.d(TAG, "onSuccess: -->" + s);
                                            Toast.makeText(VideoDetailsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                            loadDatas();
                                        }else{
                                            Toast.makeText(VideoDetailsActivity.this, "评论失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                 WindowManager.LayoutParams.MATCH_PARENT,
                 WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xff000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(VideoDetailsActivity.this.findViewById(R.id.published),
                Gravity.CENTER, 0, 0);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果开启
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.showSoftInput(et_comment,InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onlikeCheckedChanged(CompoundButton buttonView, boolean isChecked, int comment_id) {
        final int commentpraise;//1点赞 -1取消点赞
        if (isChecked) {
            commentpraise = 1;
        }else{
            commentpraise = -1;
        }
        String request = RequestUtil.editTVideoCommentBy(this, comment_id + "", commentpraise + "");
        OkGo.post(Constants.BASE_URL + "/api/APITVideoComment/EditTVideoCommentBy")
                .tag(this)
                .params("request", request)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if(statuscode.equals("1")){
                                if(commentpraise == 1){
                                    Toast.makeText(VideoDetailsActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(VideoDetailsActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(VideoDetailsActivity.this, "点赞失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(VIDEO_URL);
                mVideoView.requestFocus();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int widthPixels = displayMetrics.widthPixels;
            int heightPixels = displayMetrics.heightPixels;
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = widthPixels;
            layoutParams.height = heightPixels + DensityUtil.dip2px(this, 10);
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);
            published.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            mMediaController.setTitle(video.getTeachingvideo_tittle());
        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
            published.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            mMediaController.setTitle("");
        }

        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.d(TAG, "onComplete: 成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.d(TAG, "onError: 失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
