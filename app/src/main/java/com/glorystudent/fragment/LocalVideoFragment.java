package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveimagelib.serivice.ExecutorManager;
import com.glorystudent.adapter.LocalVideoDayAdapter;
import com.glorystudent.adapter.LocalVideoListAdapter;
import com.glorystudent.adapter.LocalVideoMonthAdapter;
import com.glorystudent.entity.DrawShapeEntity;
import com.glorystudent.entity.LocalVideoGridEntity;
import com.glorystudent.entity.ProgressAndShapeEntity;
import com.glorystudent.entity.VideoModelEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflife.VideoAnalyzeActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.UpLoadVideoActivity;
import com.glorystudent.golflife.VideoEditActivity;
import com.glorystudent.util.FFmpegKit;
import com.glorystudent.util.FileUtil;
import com.glorystudent.widget.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 本地视频Fragment
 * Created by hyj on 2016/12/20.
 */
public class LocalVideoFragment extends BaseFragment implements TextWatcher, AdapterView.OnItemClickListener {
    private final static String TAG = "LocalVideoFragment";
    private SQLiteDatabase sqLiteDatabase;

    @Bind(R.id.ll_down)
    public LinearLayout ll_down;

    @Bind(R.id.lv_video_list)
    public ListView listView;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    private View searchView;
    private View uploadView;
    private View emptyView;
    private RelativeLayout rl_bottom;
    private ImageView iv_rec;
    private EditText search_address;
    private TextView tv_upload_number;
    private int videoType = 0;//0列表，1月视图，2日视图
    private boolean isChoose = false;
    private LocalVideoListAdapter localVideoListAdapter;
    private LocalVideoMonthAdapter localVideoMonthAdapter;
    private LocalVideoDayAdapter localVideoDayAdapter;
    private List<VideoModelEntity> datas;//数据库的数据
    private List<VideoModelEntity> showDatas;//用于显示的数据源(列表视图数据源)
    private List<LocalVideoGridEntity> monthDatas;//月视图数据源
    private List<LocalVideoGridEntity> dayDatas;//日视图数据源
    private ArrayList<Integer> ids;//选中时的id值
    private ArrayList<Integer> uploadIds;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            String action = intent.getAction();
            Log.d(TAG, "onReceive: --->" + action);
            switch (action) {
                case "com.glory.broadcast.NATIVE_CHOOSE":
                    rl_bottom.setVisibility(View.GONE);
                    iv_rec.setVisibility(View.GONE);
                    ll_down.setVisibility(View.VISIBLE);
                    isChoose = true;
                    setChoose();
                    break;
                case "com.glory.broadcast.NATIVE_CLOSE":
                    ll_down.setVisibility(View.GONE);
                    rl_bottom.setVisibility(View.VISIBLE);
                    iv_rec.setVisibility(View.VISIBLE);
                    isChoose = false;
                    setChoose();
                    break;
                case "com.glory.broadcast.NATIVE_UPLOADING":
                    int uploadCount = intent.getIntExtra("uploadCount", -1);
                    if (uploadCount != -1) {
                        tv_upload_number.setVisibility(View.VISIBLE);
                        tv_upload_number.setText(uploadCount + "");
                    }
                    break;
                case "com.glory.broadcast.NATIVE_UPLOADED":
                    uploadIds = null;
                    tv_upload_number.setVisibility(View.GONE);
                    break;
                case "com.glory.broadcast.NATIVE_TYPE":
                    videoType = intent.getIntExtra("type", -1);
                    changeVideoViewType();
                    break;
            }
        }
    };
    private List<ProgressAndShapeEntity> progressAndShapeEntities;
    private String timeStamp;
    private String sdPath;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected int getContentId() {
        return R.layout.fragment_native_video;
    }

    @Override
    protected void init(View view) {
        EventBus.getDefault().register(this);
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                initDatas();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
            }
        });
        //获取屏幕宽和高
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.NATIVE_CHOOSE");
        intentFilter.addAction("com.glory.broadcast.NATIVE_CLOSE");
        intentFilter.addAction("com.glory.broadcast.NATIVE_UPLOADING");
        intentFilter.addAction("com.glory.broadcast.NATIVE_UPLOADED");
        intentFilter.addAction("com.glory.broadcast.NATIVE_TYPE");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getActivity().getDatabasePath("video.db"), null);
        sdPath = getSDPath();
        initView();
        initDatas();//获取本地数据源
    }

    private void initView() {
        rl_bottom = (RelativeLayout) getActivity().findViewById(R.id.rl_bottom);
        iv_rec = (ImageView) getActivity().findViewById(R.id.iv_rec);

        searchView = View.inflate(getActivity(), R.layout.item_search_list, null);
        search_address = (EditText) searchView.findViewById(R.id.search_address);
        search_address.addTextChangedListener(this);

        uploadView = View.inflate(getActivity(), R.layout.item_video_upload, null);
        tv_upload_number = (TextView) uploadView.findViewById(R.id.tv_upload_number);

        emptyView = View.inflate(getActivity(), R.layout.item_video_empty, null);
        TextView text = (TextView) emptyView.findViewById(R.id.tv_text);
        SpannableString spannableString = new SpannableString(getString(R.string.video_text14));
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fe9042")), 17, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableString);

        listView.addHeaderView(searchView, null, true);
        listView.addHeaderView(uploadView, null, true);
        listView.addFooterView(emptyView, null, true);
        //设置头部和尾部没有分割线
        listView.setFooterDividersEnabled(false);
        listView.setHeaderDividersEnabled(false);

        listView.setOnItemClickListener(this);

        localVideoListAdapter = new LocalVideoListAdapter(getActivity());
        localVideoListAdapter.setOnItemDeleteListener(new LocalVideoListAdapter.OnItemDeleteListener() {
            @Override
            public void onDelete(int video_id) {
                deleteOne(video_id);
                //重新加载数据
                initDatas();
            }
        });
        localVideoMonthAdapter = new LocalVideoMonthAdapter(getActivity());
        localVideoDayAdapter = new LocalVideoDayAdapter(getActivity());
        //默认加载列表视图
        listView.setAdapter(localVideoListAdapter);
        //sqlite中的数据
        datas = new ArrayList<>();
        //用于适配显示的数据
        showDatas = new ArrayList<>();
        dayDatas = new ArrayList<>();
        monthDatas = new ArrayList<>();
        //用于多选时选中的id
        ids = new ArrayList<>();
    }

    /**
     * 本地数据源
     */
    private void initDatas() {
        //移除头和尾视图
        removeHeaderFooterView();

        List<Integer> noExistFiles = new ArrayList<>();
        datas.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel", null);
        int count = cursor.getCount();
        Log.i(TAG, "initDatas: " + count);
        if (cursor != null && count > 0) {
            while (cursor.moveToPosition(count - 1)) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String path = cursor.getString(cursor.getColumnIndex("path"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String duration = cursor.getString(cursor.getColumnIndex("duration"));
                String fileMd5 = cursor.getString(cursor.getColumnIndex("fileMd5"));
                byte[] picBytes = cursor.getBlob(cursor.getColumnIndex("picBytes"));

                if (new File(path).exists()) {
                    VideoModelEntity videoModelEntity = new VideoModelEntity(id, title, picBytes, content, date, path, type, duration, fileMd5);
                    datas.add(videoModelEntity);
                } else {
                    noExistFiles.add(id);
                }
                count--;
                if (count < 0) {
                    cursor.close();
                    break;
                }
            }
            if (datas != null && datas.size() > 0) {
                //删除数据库中不存在的视频
                if (noExistFiles != null && noExistFiles.size() > 0) {
                    for (Integer id : noExistFiles) {
                        sqLiteDatabase.execSQL("delete from videoModel where id = ?", new String[]{id + ""});
                    }
                }
                listView.addHeaderView(searchView, null, true);
                listView.addHeaderView(uploadView, null, true);
            } else {
                listView.addFooterView(emptyView, null, true);
            }
        } else {
            Log.i(TAG, "initDatas: 尾部。1");
            listView.addFooterView(emptyView, null, true);
        }
        //显示
        createShowDatas(datas);
        changeVideoViewType();
    }

    private void removeHeaderFooterView() {
        listView.removeFooterView(emptyView);
        listView.removeHeaderView(searchView);
        listView.removeHeaderView(uploadView);
    }

    /**
     * 创建需要显示的数据
     */
    private void createShowDatas(List<VideoModelEntity> datas) {
        showDatas.clear();
        monthDatas.clear();
        dayDatas.clear();
        showDatas.addAll(datas);
        if (showDatas.size() > 0) {
            //创建月视图数据源
            createMonthDatas();
            //创建日视图数据源
            createDayDatas();
        }
    }

    /**
     * 创建日视图数据源
     */
    private void createDayDatas() {
        //获取日视图标签集合
        List<String> labelDayDatas = new ArrayList<>();
        labelDayDatas.add(showDatas.get(0).getDate());
        for (int i = 0; i < showDatas.size(); i++) {
            String date = showDatas.get(i).getDate();
            if (!date.equals(labelDayDatas.get(labelDayDatas.size() - 1))) {
                labelDayDatas.add(date);
            }
        }
        //生成日视图数据源
        for (String labelDayData : labelDayDatas) {
            LocalVideoGridEntity localVideoGridEntity = new LocalVideoGridEntity();
            localVideoGridEntity.setLabel(labelDayData);
            List<VideoModelEntity> videoDayDatas = new ArrayList<>();
            for (VideoModelEntity showData : showDatas) {
                if (showData.getDate().equals(labelDayData)) {
                    videoDayDatas.add(showData);
                }
            }
            localVideoGridEntity.setVideoDatas(videoDayDatas);
            dayDatas.add(localVideoGridEntity);
        }
    }

    /**
     * 创建月视图数据源
     */
    private void createMonthDatas() {
        //获取月视图标签集合
        List<String> labelMonthDatas = new ArrayList<>();
        labelMonthDatas.add(tranYearMonth(showDatas.get(0).getDate()));
        for (int i = 0; i < showDatas.size(); i++) {
            String date = showDatas.get(i).getDate();
            if (!date.contains(labelMonthDatas.get(labelMonthDatas.size() - 1))) {
                labelMonthDatas.add(tranYearMonth(date));
            }
        }
        //生成月视图数据源
        for (String labelMonthData : labelMonthDatas) {
            LocalVideoGridEntity localVideoGridEntity = new LocalVideoGridEntity();
            localVideoGridEntity.setLabel(tranDate(labelMonthData));
            List<VideoModelEntity> videoMonthDatas = new ArrayList<>();
            for (VideoModelEntity showData : showDatas) {
                if (showData.getDate().contains(labelMonthData)) {
                    videoMonthDatas.add(showData);
                }
            }
            localVideoGridEntity.setVideoDatas(videoMonthDatas);
            monthDatas.add(localVideoGridEntity);
        }
    }

    /**
     * 转换2017-06-20成2017-06
     *
     * @param date
     * @return
     */
    private String tranYearMonth(String date) {
        String[] split = date.split("-");
        return split[0] + "-" + split[1];
    }

    /**
     * 转换2017-06成2017 六月
     *
     * @param yearMonth
     * @return
     */
    private String tranDate(String yearMonth) {
        String[] split = yearMonth.split("-");
        String dateStr = split[0];
        switch (split[1]) {
            case "01":
                dateStr = dateStr + " 一月";
                break;
            case "02":
                dateStr = dateStr + " 二月";
                break;
            case "03":
                dateStr = dateStr + " 三月";
                break;
            case "04":
                dateStr = dateStr + " 四月";
                break;
            case "05":
                dateStr = dateStr + " 五月";
                break;
            case "06":
                dateStr = dateStr + " 六月";
                break;
            case "07":
                dateStr = dateStr + " 七月";
                break;
            case "08":
                dateStr = dateStr + " 八月";
                break;
            case "09":
                dateStr = dateStr + " 九月";
                break;
            case "10":
                dateStr = dateStr + " 十月";
                break;
            case "11":
                dateStr = dateStr + " 十一月";
                break;
            case "12":
                dateStr = dateStr + " 十二月";
                break;
        }
        return dateStr;
    }

    /**
     * 设置是否是选择模式
     */
    private void setChoose() {
        //清空所有选中状态和展开状态,重新加载原始数据
        for (VideoModelEntity showData : showDatas) {
            showData.setSelectFlag(false);
            showData.setExpandFlag(false);
        }
        switch (videoType) {
            case 0:
                localVideoListAdapter.setIsChoose(isChoose);
                break;
            case 1:
                localVideoMonthAdapter.setIsChoose(isChoose);
                break;
            case 2:
                localVideoDayAdapter.setIsChoose(isChoose);
                break;
        }
    }

    /**
     * 更改视图显示
     */
    private void changeVideoViewType() {
        switch (videoType) {
            case 0://列表视图
                showList();
                break;
            case 1://月视图
                showMonth();
                break;
            case 2://日视图
                showDay();
                break;
        }
    }

    /**
     * 更新数据
     */
    private void notifyDataChange() {
        switch (videoType) {
            case 0://列表视图
                localVideoListAdapter.notifyDataSetChanged();
                break;
            case 1://月视图
                localVideoMonthAdapter.notifyDataSetChanged();
                break;
            case 2://日视图
                localVideoDayAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 显示列表视图
     */
    private void showList() {
        listView.setAdapter(localVideoListAdapter);
        localVideoListAdapter.setDatas(showDatas);
        localVideoListAdapter.setIsChoose(isChoose);
    }

    /**
     * 显示月视图
     */
    private void showMonth() {
        listView.setAdapter(localVideoMonthAdapter);
        localVideoMonthAdapter.setDatas(monthDatas);
        localVideoMonthAdapter.setIsChoose(isChoose);
    }

    /**
     * 显示日视图
     */
    private void showDay() {
        listView.setAdapter(localVideoDayAdapter);
        localVideoDayAdapter.setDatas(dayDatas);
        localVideoDayAdapter.setIsChoose(isChoose);
    }

    /**
     * 获取选中id
     */
    private void accesssCheckedId() {
        ids.clear();
        for (int i = 0; i < showDatas.size(); i++) {
            if (showDatas.get(i).isSelectFlag()) {
                //选中状态
                ids.add(showDatas.get(i).getId());
            }
        }
        for (Integer id : ids) {
            Log.i(TAG, "accesssCheckedId: " + id);
        }
    }

    @OnClick({R.id.tv_edit, R.id.tv_upload, R.id.tv_delete})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                //多选编辑
                MultiSelectEditor();
                break;
            case R.id.tv_upload:
                //多选上传
                MultiSelectUpload();
                break;
            case R.id.tv_delete:
                //多选删除
                MultiSelectDelete();
                break;
        }
    }

    /**
     * 多选上传
     */
    private void MultiSelectUpload() {
        accesssCheckedId();
        if (ids != null && ids.size() > 0) {
            //有选中视频
            Log.i(TAG, "onClick: +上传");
            setUploadIds(ids);
            Intent intent = new Intent(getActivity(), UpLoadVideoActivity.class);
            intent.putIntegerArrayListExtra("ids", ids);
            startActivity(intent);
            cancelSelectState();
        } else {
            //没有选中视频
            Toast.makeText(getActivity(), "请至少选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置正在上传的视频id
     */
    private void setUploadIds(List<Integer> ids) {
        uploadIds = new ArrayList<>();
        uploadIds.addAll(ids);
    }

    /**
     * 多选删除
     */
    private void MultiSelectDelete() {
        accesssCheckedId();
        if (ids != null && ids.size() > 0) {
            //有选中视频
            new AlertDialog(getActivity()).builder()
                    .setTitle("确认删除选中视频")
                    .setPositiveButton("删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showWaiting();
                            Log.d(TAG, "MultiSelectDelete: --->多选删除");
                            for (int i = 0; i < ids.size(); i++) {
                                deleteOne(ids.get(i));
                            }
                            //取消选择状态
                            cancelSelectState();
                            //重新加载数据
                            initDatas();
                            dismissWaiting();
                        }
                    })
                    .setNegativeButton("取消", null).show();
        } else {
            //没有选中视频
            Toast.makeText(getActivity(), "请至少选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除一条数据
     *
     * @param video_id
     */
    private void deleteOne(int video_id) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from videoModel where id = ?", new String[]{video_id + ""});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String path = cursor.getString(cursor.getColumnIndex("path"));
                //本地导入的视频，不删除源文件，只删除数据库数据
                if (!type.equals("2")) {
                    //拍摄的视频，删除时删除源文件
                    Log.i(TAG, "MultiSelectDelete:这是在查询 --->" + path);
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
            cursor.close();
        }
        //删除数据库数据
        sqLiteDatabase.execSQL("delete from videoModel where id = ?", new String[]{video_id + ""});
    }

    /**
     * 多选编辑
     */
    private void MultiSelectEditor() {
        Log.d(TAG, "MultiSelectEditor: --->多选编辑");
        accesssCheckedId();
        if (ids != null && ids.size() > 0) {
            //有选中视频
            Intent intent = new Intent(getActivity(), VideoEditActivity.class);
            intent.putIntegerArrayListExtra("ids", ids);
            startActivity(intent);
            cancelSelectState();
        } else {
            //没有选中视频
            Toast.makeText(getActivity(), "请至少选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消当前选择状态
     */
    private void cancelSelectState() {
        Intent intent = new Intent("com.glory.broadcast.NATIVE_CANCEL");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //由于添加了两个头部搜索和上传进度
        if (position > 1) {
            if (videoType == 0) {
                //列表视图
                if (isChoose) {
                    //选择模式
                    Log.i(TAG, "onItemClick: 选择" + position);
                    VideoModelEntity videoModelEntity = showDatas.get(position - 2);
                    videoModelEntity.setSelectFlag(!videoModelEntity.isSelectFlag());
                    notifyDataChange();
                } else {
                    //正常模式
                    Intent intent = new Intent(getActivity(), VideoAnalyzeActivity.class);
                    VideoModelEntity item = showDatas.get(position - 2);
                    if (!new File(item.getPath()).exists()) {
                        Toast.makeText(getActivity(), "视频文件不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.i(TAG, "onItemClick: " + item.getPath());
                    intent.putExtra("path", item.getPath());
                    if (item.getTitle() != null && !item.getTitle().isEmpty()) {
                        intent.putExtra("title", item.getTitle());
                    }
                    if (item.getContent() != null && !item.getContent().isEmpty()) {
                        intent.putExtra("content", item.getContent());
                    }
                    startActivity(intent);
                }
            }//另外两个视图在内部处理
        } else if (position == 1) {
            //点击了上传进度
            Intent intent = new Intent(getActivity(), UpLoadVideoActivity.class);
            intent.putIntegerArrayListExtra("ids", uploadIds);
            startActivity(intent);
        } else {//搜索，不执行操作
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sqLiteDatabase.close();
        EventBus.getDefault().unregister(this);
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        List<VideoModelEntity> searchDatas = new ArrayList<>();
        if (str.isEmpty()) {
            searchDatas.addAll(datas);
        } else {
            for (int i = 0; i < datas.size(); i++) {
                String title = datas.get(i).getTitle();
                if (title != null) {
                    if (title.contains(str)) {
                        searchDatas.add(datas.get(i));
                    }
                }
            }
        }
        createShowDatas(searchDatas);
        notifyDataChange();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventDatas(Map<String, ArrayList<Integer>> map) {
        if (map.containsKey("LocalVideoFragment")) {
            if (map.get("LocalVideoFragment") instanceof ArrayList) {
                ArrayList<Integer> ids = map.get("LocalVideoFragment");
                setUploadIds(ids);
                ids = null;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("LocalVideoFragment")) {
            if (map.get("LocalVideoFragment").equals("refresh")) {
                Log.i(TAG, "getEvent: 执行了");
                initDatas();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<String, List<ProgressAndShapeEntity>> map) {
        if (map.containsKey("Analyze")) {
            if (map.get("Analyze") instanceof List) {
                progressAndShapeEntities = map.get("Analyze");
                if (progressAndShapeEntities != null && progressAndShapeEntities.size() > 0) {
                    timeStamp = progressAndShapeEntities.get(0).getTimeStamp();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final long startTime = System.currentTimeMillis();
                            analyze();
                            final long endTime = System.currentTimeMillis();
                            Log.i(TAG, "run: 写图片到本地用时(s)：" + (endTime - startTime) / 1000);
                            String path = sdPath + "/golf/compound/" + timeStamp + "/";
                            Log.i(TAG, "run: 之后的长度：" + ExecutorManager.getInstance().getList().size());
                            while (ExecutorManager.getInstance().getList().size() == progressAndShapeEntities.size()) {
                                String[] commands = new String[13];
                                commands[0] = "ffmpeg";
                                commands[1] = "-threads";
                                commands[2] = "2";
                                commands[3] = "-y";
                                commands[4] = "-r";
                                commands[5] = "30";
                                commands[6] = "-i";
                                commands[7] = path + "imgtmp/image%d.jpg";
                                commands[8] = "-i";
                                commands[9] = path + "audio.mp4";
                                commands[10] = "-absf";
                                commands[11] = "aac_adtstoasc";
                                commands[12] = path + "output.mp4";

                                String cmd = "";
                                for (String s : commands) {
                                    cmd += s + " ";
                                }
                                Log.i(TAG, "run: 命令：" + cmd);
//                                FFmpegKit.execute(commands, new FFmpegKit.KitInterface() {
//                                    @Override
//                                    public void onStart() {
//                                        Log.d(TAG, "FFmpeg 命令行开始执行了...");
//                                    }
//
//                                    @Override
//                                    public void onProgress(int progress) {
//                                        Log.d(TAG, "done com" + "FFmpeg 命令行执行进度..." + progress);
//                                    }
//
//                                    @Override
//                                    public void onEnd(int result) {
//                                        Log.d("FFmpegLog LOGCAT", "FFmpeg 命令行执行完成...");
//                                        long time = System.currentTimeMillis();
//                                        Log.i(TAG, "run: 图片合成耗时(s)：" + (time - endTime) / 1000);
//                                    }
//                                });
                                FFmpegKit.execute(commands);
                                long time = System.currentTimeMillis();
                                Log.i(TAG, "run: 图片合成耗时(s)：" + (time - endTime) / 1000);
                            }
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * 开始分析视频
     */
    private void analyze() {
        String videoPath = sdPath + "/golf/compound/" + timeStamp + "/" + "copy_video.mp4";
        if (!new File(videoPath).exists()) {
            return;
        }
        for (int i = 0; i < progressAndShapeEntities.size(); i++) {
            ProgressAndShapeEntity entity = progressAndShapeEntities.get(i);
            if (mediaMetadataRetriever == null) {
                mediaMetadataRetriever = new MediaMetadataRetriever();
            }
            mediaMetadataRetriever.setDataSource(videoPath);
//           int videoWidth = Integer.valueOf(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)); // 视频宽度
//           int videoHeight = Integer.valueOf(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)); // 视频高度
//           Log.i(TAG, "init: 高：" + videoHeight + ",宽：" + videoWidth);
            //视频宽高和取出的帧图片宽高相反
            Bitmap bmp = mediaMetadataRetriever.getFrameAtTime(entity.getProgress() * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            int videoWidth = bmp.getWidth();
            int videoHeight = bmp.getHeight();
            Log.i(TAG, "analyze: 视频帧图片:宽" + videoWidth + "--->高" + videoHeight);

            Bitmap drawBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
            drawBitmap.eraseColor(Color.argb(0, 0, 0, 0));
            Canvas canvas = new Canvas(drawBitmap);
            canvas.drawColor(Color.TRANSPARENT);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setDither(true);

            canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.DITHER_FLAG));
            Log.i(TAG, "analyze: " + entity.getShapeEntities().size());
            //计算缩放比例
            float zoomW = screenWidth * 1.0f / videoWidth;
            float zoomH = screenHeight * 1.0f / videoHeight;
            float zoom = 0;//缩放比例
            float outW = 0;//宽度方向超出屏幕的大小
            float outH = 0;//高度方向超出屏幕的大小
            if (zoomW > zoomH) {
                zoom = zoomW;
                outH = (videoHeight * zoom - screenHeight) / 2;
            } else {
                zoom = zoomH;
                outW = (videoWidth * zoom - screenWidth) / 2;
            }
            Log.i(TAG, "analyze: zoom" + zoom + ",zoomW:" + zoomW + ",zoomH:" + zoomH + ",outH:" + outH + ",outW" + outW);
            for (DrawShapeEntity shapeEntity : entity.getShapeEntities()) {
                //计算缩放后的坐标
                shapeEntity.transPoint(-outW, outH);
                shapeEntity.zoom(1 / zoom);
                shapeEntity.draw(canvas, paint);
            }
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            try {
                String bitmapPath = sdPath + "/golf/compound/" + timeStamp + "/imgtmp/image" + i + ".jpg";
//                File file = new File(bitmapPath);
//                FileOutputStream fos = null;
                File file = FileUtil.createFile(bitmapPath);
//                if (!file.exists()) {
//                    file.createNewFile();
////                    fos = new FileOutputStream(file);
//                }
                ExecutorManager.getInstance().run(drawBitmap, bitmapPath);
//                drawBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
}
