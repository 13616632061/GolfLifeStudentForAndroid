package com.glorystudent.golflife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glorystudent.adapter.MyDecoration;
import com.glorystudent.adapter.UpLoadRecyclerAdapter;
import com.glorystudent.entity.ListMD5RequestEntity;
import com.glorystudent.entity.UpLoadAliyunEntity;
import com.glorystudent.entity.UpLoadVideoEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 上传阿里云视频
 * Created by hyj on 2017/1/16.
 */
public class UpLoadVideoActivity extends BaseActivity implements OnSwipeMenuItemClickListener {
    private final static String TAG = "UpLoadVideoActivity";

    @Bind(R.id.recycler_view)
    public SwipeMenuRecyclerView swipeMenuRecyclerView;

    private UpLoadRecyclerAdapter upLoadRecyclerAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private List<UpLoadAliyunEntity> datas;
    private LocalBroadcastManager localBroadcastManager;

    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;
    private ArrayList<Integer> ids;

    @Override
    protected int getContentId() {
        return R.layout.activity_upload_video;
    }

    @Override
    protected void init() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Intent intent = getIntent();
        ids = intent.getIntegerArrayListExtra("ids");
        boolean singleFlag = intent.getBooleanExtra("single", false);
        if (singleFlag) {
            Map<String, ArrayList<Integer>> map = new HashMap<>();
            map.put("LocalVideoFragment", ids);
            EventBus.getDefault().post(map);
        }
        initDatas();
        initViews();
        if (datas != null && datas.size() > 0) {
            ll_empty.setVisibility(View.GONE);
            //检查已经上传的视频
            checkExistUploaded();
        } else {
            ll_empty.setVisibility(View.VISIBLE);
            //发送广播更新正在上传视频的数量
            sendBroadcase(0);
        }
    }

    /**
     * 检查视频中已经上传的视频
     */
    private void checkExistUploaded() {
        showLoading();
        List<String> listMd5 = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            listMd5.add(datas.get(i).getFileMD5());
        }
        ListMD5RequestEntity listMD5RequestEntity = new ListMD5RequestEntity();
        listMD5RequestEntity.setListmd5name(listMd5);
        String json = new Gson().toJson(listMD5RequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        String url = "/api/APIVideos/QueryVideosByMD5Name";
        Log.i(TAG, "init: " + requestJson);
        Log.i(TAG, "init: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        UpLoadVideoEntity upLoadVideoEntity = new Gson().fromJson(jo.toString(), UpLoadVideoEntity.class);
                        List<UpLoadVideoEntity.ListvideosBean> listvideos = upLoadVideoEntity.getListvideos();
                        if (listvideos != null) {
                            for (int j = 0; j < datas.size(); j++) {
                                for (int i = 0; i < listvideos.size(); i++) {
                                    if (listvideos.get(i).getVideo_filemd5().equals(datas.get(j).getFileMD5())) {
                                        //存在已上传的视频，将其状态设为2
                                        datas.get(j).setState("2");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissLoading();
                upLoadRecyclerAdapter.setDatas(datas);
                //计算发送正在上传的视频数量
                calcUploadCount();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(UpLoadVideoActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 计算发送正在上传的视频数量
     */
    private void calcUploadCount() {
        int count = 0;
        if (datas != null && datas.size() > 0) {
            for (UpLoadAliyunEntity data : datas) {
                if (data.getState().equals("2")) {
                    count++;
                }
            }
        }
        sendBroadcase(datas.size() - count);
    }

    /**
     * 通知上个页面显示上传视频个数
     */
    private void sendBroadcase(int uploadCount) {
        Intent intent;
        if (uploadCount <= 0) {
            intent = new Intent("com.glory.broadcast.NATIVE_UPLOADED");
        } else {
            intent = new Intent("com.glory.broadcast.NATIVE_UPLOADING");
            intent.putExtra("uploadCount", uploadCount);
        }
        localBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 初始化视图控件
     */
    private void initViews() {
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipeMenuRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        swipeMenuRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(UpLoadVideoActivity.this)
                        .setBackgroundDrawable(getResources().getDrawable(R.color.colorRed))
                        .setText("删除") // 文字。
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(16) // 文字大小。
                        .setWidth(DensityUtil.dip2px(UpLoadVideoActivity.this, 100))
                        .setHeight(DensityUtil.dip2px(UpLoadVideoActivity.this, 80));
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单
            }
        });
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(this);

        upLoadRecyclerAdapter = new UpLoadRecyclerAdapter(UpLoadVideoActivity.this);
        swipeMenuRecyclerView.setAdapter(upLoadRecyclerAdapter);
    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        datas = new ArrayList<>();
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel where id = ?", new String[]{ids.get(i) + ""});
                if (cursor != null) {
                    cursor.moveToFirst();
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    String path = cursor.getString(cursor.getColumnIndex("path"));
                    String fileMd5 = cursor.getString(cursor.getColumnIndex("fileMd5"));
                    byte[] picBytes = cursor.getBlob(cursor.getColumnIndex("picBytes"));
                    UpLoadAliyunEntity upLoadAliyunEntity = new UpLoadAliyunEntity(ids.get(i), title, path, content, fileMd5, picBytes);
                    //1未上传过的视频，2已上传的视频。
                    upLoadAliyunEntity.setState("1");
                    datas.add(upLoadAliyunEntity);
                    cursor.close();
                }
            }
        }

    }

    @Override
    public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
        upLoadRecyclerAdapter.delete(adapterPosition);
        closeable.smoothCloseRightMenu();
    }

    @OnClick({R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
