package com.glorystudent.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.FileToMD5Util;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.glorystudent.util.UriToPathUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教练端视频模块
 * Created by hyj on 2016/12/14.
 */
public class CoachVideoFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private final static String TAG = "CoachVideoFragment";
    @Bind(R.id.video_rg)
    public RadioGroup video_rg;
    @Bind(R.id.ll_video_type)
    public LinearLayout llVideoTypeLayout;
    @Bind(R.id.iv_video_type)
    public ImageView ivVideoType;

    @Bind(R.id.to_lead)
    public TextView to_lead;

    @Bind(R.id.tv_choose)
    public TextView tv_choose;

    private LocalBroadcastManager localBroadcastManager;

    private boolean isCancel = false;//true取消 false选择
    private IntentFilter intentFilter;

    private boolean isNative = true;//true为本地视频， false为云端视频
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.NATIVE_CANCEL":
                    tv_choose.performClick();
                    break;
            }
        }
    };
    private CustomPopWindow videoTypeWindow;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected int getContentId() {
        return R.layout.fragment_coach_video;
    }

    @Override
    protected void init(View view) {
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.NATIVE_CANCEL");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getActivity().getDatabasePath("video.db"), null);

        video_rg.setOnCheckedChangeListener(this);
        video_rg.getChildAt(0).performClick();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.video_rb1:
                //切换本地视频
                showFragment(R.id.video_fragment, new LocalVideoFragment());
                isNative = true;
                to_lead.setVisibility(View.VISIBLE);
                break;
            case R.id.video_rb2:
                //切换云端视频
                showFragment(R.id.video_fragment, new CloudVideoFragment());
                isNative = false;
                to_lead.setVisibility(View.GONE);
                break;
        }
        isCancel = true;
        tv_choose.performClick();
    }

    @OnClick({R.id.to_lead, R.id.tv_choose, R.id.ll_video_type})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.to_lead:
                //导入
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 0x100);
                break;
            case R.id.tv_choose:
                //选择获取取消
                selectOrCancel();
                break;
            case R.id.ll_video_type:
                //展开视图
                showVideoType();
                break;
        }
    }

    /**
     * 选择或取消的方法
     */
    private void selectOrCancel() {
        Intent intent;
        if (!isCancel) {
            //取消
            if (isNative) {
                intent = new Intent("com.glory.broadcast.NATIVE_CHOOSE");
            } else {
                intent = new Intent("com.glory.broadcast.CLOUD_CHOOSE");
            }
            isCancel = true;
            tv_choose.setText("取消");
        } else {
            //选择
            if (isNative) {
                intent = new Intent("com.glory.broadcast.NATIVE_CLOSE");
            } else {
                intent = new Intent("com.glory.broadcast.CLOUD_CLOSE");
            }
            isCancel = false;
            tv_choose.setText("选择");
        }
        localBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 展示视频显示方式
     */
    private void showVideoType() {
        View view = View.inflate(getActivity(), R.layout.popup_video_type, null);
        View llMonth = view.findViewById(R.id.ll_video_month);
        View llDay = view.findViewById(R.id.ll_video_day);
        View llList = view.findViewById(R.id.ll_video_list);
        llMonth.setOnClickListener(this);
        llDay.setOnClickListener(this);
        llList.setOnClickListener(this);
        videoTypeWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(view)//显示的布局，还可以通过设置一个View
                //     .size(600,400) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAsDropDown(llVideoTypeLayout, -DensityUtil.dip2px(getActivity(), 30), 10);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // 选取图片的返回值
            if (requestCode == 0x100) {
                try {
                    Uri uri = data.getData();
                    Log.i(TAG, "onActivityResult: " + uri);
//                    String path = uri.getPath();
                    String path = UriToPathUtil.getPath(getActivity(), uri);
//                    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//                    cursor.moveToFirst();
//                    String path = cursor.getString(1); // 视频文件路径
//                    cursor.close();
                    Log.i(TAG, "onActivityResult: " + path);
                    String fileMD5 = FileToMD5Util.getFileMD5(new File(path));
                    File file = new File(path);
                    Log.i(TAG, "onActivityResult: 是否存在此文件" + file.exists());
                    if (!isDataExist(fileMD5)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("type", "2");//1拍摄的视频，2本地导入的视频，3正在合成的视频
                        contentValues.put("path", path);
                        contentValues.put("date", RequestUtil.getCurrentTime());

                        MediaMetadataRetriever media = new MediaMetadataRetriever();
                        media.setDataSource(path);
                        String durationMs = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                        String durationTime = TimeUtil.getToDay(Integer.valueOf(durationMs));
                        contentValues.put("duration", durationTime);

                        contentValues.put("fileMd5", fileMD5);

                        Bitmap bitmap = media.getFrameAtTime();//获取第一帧图片
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        contentValues.put("picBytes", out.toByteArray());
                        sqLiteDatabase.insert("videoModel", null, contentValues);
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("LocalVideoFragment", "refresh"));
                    } else {
                        Toast.makeText(getActivity(), "视频已存在", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i(TAG, "异常: " + e.getMessage());
                    e.getStackTrace();
                }
            }
        }
    }

    public boolean isDataExist(String filemd5) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel where fileMd5 = ?", new String[]{filemd5});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        videoTypeWindow.dissmiss();
        Intent intent = new Intent("com.glory.broadcast.NATIVE_TYPE");
        switch (v.getId()) {
            case R.id.ll_video_month:
                //月视图
                ivVideoType.setImageResource(R.drawable.icon_video_month);
                intent.putExtra("type", 1);
                break;
            case R.id.ll_video_day:
                //日视图
                ivVideoType.setImageResource(R.drawable.icon_video_day);
                intent.putExtra("type", 2);
                break;
            case R.id.ll_video_list:
                //列表视图
                ivVideoType.setImageResource(R.drawable.icon_video_list);
                intent.putExtra("type", 0);
                break;
        }
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
