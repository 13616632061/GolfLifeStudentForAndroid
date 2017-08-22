package com.glorystudent.golflife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.glorystudent.adapter.DownLoadRecyclerAdapter;
import com.glorystudent.adapter.MyDecoration;
import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 阿里云下载进度
 * Created by hyj on 2017/1/20.
 */
public class DownLoadVideoActivity extends BaseActivity implements OnSwipeMenuItemClickListener {
    private final static String TAG = "DownLoadVideoActivity";
    @Bind(R.id.recycler_view)
    public SwipeMenuRecyclerView swipeMenuRecyclerView;

    private DownLoadRecyclerAdapter downLoadRecyclerAdapter;
    @Bind(R.id.ll_empty)
    public LinearLayout ll_empty;

    private ArrayList<CloudVideoEntity.ListvideosBean> datas;
    private LocalBroadcastManager localBroadcastManager;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected int getContentId() {
        return R.layout.activity_download_video;
    }

    @Override
    protected void init() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("video.db"), null);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Intent intent = getIntent();
        datas = (ArrayList<CloudVideoEntity.ListvideosBean>) intent.getSerializableExtra("listVideos");
        boolean singleFlag = intent.getBooleanExtra("single", false);
        if (singleFlag) {
            Map<String, ArrayList<CloudVideoEntity.ListvideosBean>> map = new HashMap<>();
            map.put("CloudVideoFragment", datas);
            EventBus.getDefault().post(map);
        }
        initViews();
        if (datas != null && datas.size() > 0) {
            ll_empty.setVisibility(View.GONE);
            checkExistDownload();
            downLoadRecyclerAdapter.setDatas(datas);
        } else {
            ll_empty.setVisibility(View.VISIBLE);
            sendBroadcase(0);
        }
    }

    /**
     * 检查是否有已经下载的文件
     */
    private void checkExistDownload() {
        for (CloudVideoEntity.ListvideosBean data : datas) {
            if (existInSqlite(data.getVideo_filemd5())) {
                data.setState("2");//视频已经存在
            } else {
                data.setState("1");
            }
        }
    }

    /**
     * 判断数据库中是否存在此文件
     *
     * @param fileMd5
     * @return true 存在，false 不存在
     */
    public boolean existInSqlite(String fileMd5) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel where fileMd5 = ?", new String[]{fileMd5});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    private void initViews() {
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipeMenuRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        swipeMenuRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(DownLoadVideoActivity.this)
                        .setBackgroundDrawable(getResources().getDrawable(R.color.colorRed))
                        .setText("删除") // 文字。
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(16) // 文字大小。
                        .setWidth(DensityUtil.dip2px(DownLoadVideoActivity.this, 100))
                        .setHeight(DensityUtil.dip2px(DownLoadVideoActivity.this, 80));
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单
            }
        });
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(this);

        downLoadRecyclerAdapter = new DownLoadRecyclerAdapter(this);
        swipeMenuRecyclerView.setAdapter(downLoadRecyclerAdapter);
    }

    /**
     * 计算发送正在上传的视频数量
     */
    private void calcUploadCount() {
        int count = 0;
        if (datas != null && datas.size() > 0) {
            for (CloudVideoEntity.ListvideosBean data : datas) {
                if (data.getState().equals("2")) {//1未下载，2已经下载
                    count++;
                }
            }
        }
        sendBroadcase(datas.size() - count);
    }

    /**
     * 通知上个页面显示上传视频个数
     */
    private void sendBroadcase(int downloadCount) {
        Intent intent;
        if (downloadCount <= 0) {
            intent = new Intent("com.glory.broadcast.CLOUD_DOWNLOADED");
        } else {
            intent = new Intent("com.glory.broadcast.CLOUD_DOWNLOADING");
            intent.putExtra("downloadCount", downloadCount);
        }
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
        downLoadRecyclerAdapter.delete(adapterPosition);
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
