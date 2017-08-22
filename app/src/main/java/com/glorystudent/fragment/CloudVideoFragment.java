package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.CloudVideoDayAdapter;
import com.glorystudent.adapter.CloudVideoListAdapter;
import com.glorystudent.adapter.CloudVideoMonthAdapter;
import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.entity.CloudVideoGridEntity;
import com.glorystudent.entity.CloudVideoRequestEntity;
import com.glorystudent.entity.VideosRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflife.CloudVideoEditActivity;
import com.glorystudent.golflife.DownLoadVideoActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教练端云视频模块
 * Created by hyj on 2016/12/20.
 */
public class CloudVideoFragment extends BaseFragment implements TextWatcher, AdapterView.OnItemClickListener {
    private final static String TAG = "CloudVideoFragment";

    private int page = 1;//默认加载第一页

    @Bind(R.id.ll_down)
    public LinearLayout ll_down;
    @Bind(R.id.lv_video_list)
    public ListView listView;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh = true;//true 是下拉刷新， false 是上拉加载

    private View searchView;
    private View downloadView;
    private View emptyView;
    private RelativeLayout rl_bottom;
    private ImageView iv_rec;
    private EditText search_address;
    private TextView tv_download_number;
    private int videoType = 0;//0列表，1月视图，2日视图
    private boolean isChoose = false;
    private CloudVideoListAdapter cloudVideoListAdapter;
    private CloudVideoMonthAdapter cloudVideoMonthAdapter;
    private CloudVideoDayAdapter cloudVideoDayAdapter;

    private List<CloudVideoEntity.ListvideosBean> datas;//正在显示的数据源(保持不变)
    private List<CloudVideoEntity.ListvideosBean> showDatas;//用于显示的数据源(列表视图数据源)
    private List<CloudVideoGridEntity> monthDatas;//月视图数据源
    private List<CloudVideoGridEntity> dayDatas;//日视图数据源

    private ArrayList<CloudVideoEntity.ListvideosBean> selectedDatas;//选中时的数据源
    private ArrayList<CloudVideoEntity.ListvideosBean> downloadDatas;//下载使用的数据源
    private LocalBroadcastManager localBroadcastManager;

    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.CLOUD_CHOOSE":
                    rl_bottom.setVisibility(View.GONE);
                    iv_rec.setVisibility(View.GONE);
                    ll_down.setVisibility(View.VISIBLE);
                    isChoose = true;
                    setChoose();
                    break;
                case "com.glory.broadcast.CLOUD_CLOSE":
                    ll_down.setVisibility(View.GONE);
                    rl_bottom.setVisibility(View.VISIBLE);
                    iv_rec.setVisibility(View.VISIBLE);
                    isChoose = false;
                    setChoose();
                    break;
                case "com.glory.broadcast.CLOUD_DOWNLOADING":
                    //正在下载
                    int downloadCount = intent.getIntExtra("downloadCount", -1);
                    Log.i(TAG, "onReceive: " + downloadCount);
                    if (downloadCount != -1) {
                        tv_download_number.setVisibility(View.VISIBLE);
                        tv_download_number.setText(downloadCount + "");
                    }
                    break;
                case "com.glory.broadcast.CLOUD_DOWNLOADED":
                    //下载完成
                    Log.i(TAG, "onReceive: 执行空");
                    downloadDatas = null;
                    tv_download_number.setVisibility(View.GONE);
                    break;
                case "com.glory.broadcast.NATIVE_TYPE":
                    videoType = intent.getIntExtra("type", -1);
                    changeVideoViewType();
                    break;
            }
        }
    };


    @Override
    protected int getContentId() {
        return R.layout.fragment_cloud_video;
    }

    @Override
    protected void init(View view) {
        showLoading();
        EventBus.getDefault().register(this);
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                page = 1;
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                page++;
                loadDatas();
            }
        });

        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.CLOUD_CHOOSE");
        intentFilter.addAction("com.glory.broadcast.CLOUD_CLOSE");
        intentFilter.addAction("com.glory.broadcast.CLOUD_DOWNLOADING");
        intentFilter.addAction("com.glory.broadcast.CLOUD_DOWNLOADED");
        intentFilter.addAction("com.glory.broadcast.NATIVE_TYPE");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        initView();
    }

    @Override
    protected void loadDatas() {
        VideosRequestEntity videosRequestEntity = new VideosRequestEntity();
        videosRequestEntity.setPage(page);
        VideosRequestEntity.VideosBean videosBean = new VideosRequestEntity.VideosBean();
        videosRequestEntity.setVideos(videosBean);
        String request = new Gson().toJson(videosRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APIVideos/QueryVideos";
        Log.i(TAG, "loadDatas: " + requestJson);
        Log.i(TAG, "loadDatas: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        //有数据则显示头隐藏尾
                        removeHeaderFooterView();
                        listView.addHeaderView(searchView, null, true);
                        listView.addHeaderView(downloadView, null, true);
                        CloudVideoEntity cloudVideoEntity = new Gson().fromJson(json, CloudVideoEntity.class);
                        List<CloudVideoEntity.ListvideosBean> listvideos = cloudVideoEntity.getListvideos();
                        if (listvideos != null && listvideos.size() > 0) {
                            Log.i(TAG, "parseDatas: 加载数据" + listvideos.size());
                            if (isRefresh) {
                                datas.clear();
                                datas.addAll(listvideos);
                            } else {
                                datas.addAll(listvideos);
                            }
                            createShowDatas(datas);
                            changeVideoViewType();
                        }
                    } else if (statuscode.equals("2")) {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                        //刷新数据源
                        if (isRefresh) {
                            datas.clear();
                        }
                        createShowDatas(datas);
                        removeHeaderFooterView();
                        if (listView.getCount() > 2) {//listview的item多余2个(2个头)则代表当前有数据
                            listView.addHeaderView(searchView, null, true);
                            listView.addHeaderView(downloadView, null, true);
                        } else {
                            listView.addFooterView(emptyView, null, true);
                        }
                        changeVideoViewType();
                    } else {
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                page--;
            }
        }).getEntityData(getActivity(), url, requestJson);
    }

    private void removeHeaderFooterView() {
        listView.removeFooterView(emptyView);
        listView.removeHeaderView(searchView);
        listView.removeHeaderView(downloadView);
    }

    private void initView() {
        rl_bottom = (RelativeLayout) getActivity().findViewById(R.id.rl_bottom);
        iv_rec = (ImageView) getActivity().findViewById(R.id.iv_rec);

        searchView = View.inflate(getActivity(), R.layout.item_search_list, null);
        search_address = (EditText) searchView.findViewById(R.id.search_address);
        search_address.addTextChangedListener(this);

        downloadView = View.inflate(getActivity(), R.layout.item_video_download, null);
        tv_download_number = (TextView) downloadView.findViewById(R.id.tv_download_number);

        emptyView = View.inflate(getActivity(), R.layout.item_video_cloud_empty, null);

        listView.addHeaderView(searchView, null, true);
        listView.addHeaderView(downloadView, null, true);
        listView.addFooterView(emptyView, null, true);
        //设置头部和尾部没有分割线
        listView.setFooterDividersEnabled(false);
        listView.setHeaderDividersEnabled(false);

        listView.setOnItemClickListener(this);

        cloudVideoListAdapter = new CloudVideoListAdapter(getActivity());
        //设置删除点击监听
        cloudVideoListAdapter.setOnItemDeleteListener(new CloudVideoListAdapter.OnItemDeleteListener() {
            @Override
            public void onDelete(int video_id) {
                deleteOne(video_id);
            }
        });
        cloudVideoMonthAdapter = new CloudVideoMonthAdapter(getActivity());
        cloudVideoDayAdapter = new CloudVideoDayAdapter(getActivity());

        //用于适配显示的数据
        datas = new ArrayList<>();
        showDatas = new ArrayList<>();
        dayDatas = new ArrayList<>();
        monthDatas = new ArrayList<>();
        //多选的数据源
        selectedDatas = new ArrayList<>();
    }

    /**
     * 创建需要显示的数据
     */
    private void createShowDatas(List<CloudVideoEntity.ListvideosBean> datas) {
        monthDatas.clear();
        dayDatas.clear();
        showDatas.clear();
        showDatas.addAll(datas);
        if (showDatas.size() > 0) {
            //创建月视图数据源
            createMonthDatas();
            //创建日视图数据源
            createDayDatas();
        }
        Log.i(TAG, "createShowDatas: " + showDatas.size());
    }

    /**
     * 创建日视图数据源
     */
    private void createDayDatas() {
        //获取日视图标签集合
        List<String> labelDayDatas = new ArrayList<>();
        labelDayDatas.add(formatStandardTime(showDatas.get(0).getVideo_datetime()));
        for (int i = 0; i < showDatas.size(); i++) {
            String date = formatStandardTime(showDatas.get(i).getVideo_datetime());
            if (!date.equals(labelDayDatas.get(labelDayDatas.size() - 1))) {
                labelDayDatas.add(date);
            }
        }
        //生成日视图数据源
        for (String labelDayData : labelDayDatas) {
            CloudVideoGridEntity cloudVideoGridEntity = new CloudVideoGridEntity();
            cloudVideoGridEntity.setLabel(labelDayData);
            List<CloudVideoEntity.ListvideosBean> videoDayDatas = new ArrayList<>();
            for (CloudVideoEntity.ListvideosBean showData : showDatas) {
                String date = formatStandardTime(showData.getVideo_datetime());
                if (date.equals(labelDayData)) {
                    videoDayDatas.add(showData);
                }
            }
            cloudVideoGridEntity.setVideoDatas(videoDayDatas);
            dayDatas.add(cloudVideoGridEntity);
        }
    }

    /**
     * 创建月视图数据源
     */
    private void createMonthDatas() {
        //获取月视图标签集合
        List<String> labelMonthDatas = new ArrayList<>();
        labelMonthDatas.add(tranYearMonth(showDatas.get(0).getVideo_datetime()));
        for (int i = 0; i < showDatas.size(); i++) {
            String date = showDatas.get(i).getVideo_datetime();
            if (!date.contains(labelMonthDatas.get(labelMonthDatas.size() - 1))) {
                labelMonthDatas.add(tranYearMonth(date));
            }
        }
        //生成月视图数据源
        for (String labelMonthData : labelMonthDatas) {
            CloudVideoGridEntity cloudVideoGridEntity = new CloudVideoGridEntity();
            cloudVideoGridEntity.setLabel(tranDate(labelMonthData));
            List<CloudVideoEntity.ListvideosBean> videoMonthDatas = new ArrayList<>();
            for (CloudVideoEntity.ListvideosBean showData : showDatas) {
                if (showData.getVideo_datetime().contains(labelMonthData)) {
                    videoMonthDatas.add(showData);
                }
            }
            cloudVideoGridEntity.setVideoDatas(videoMonthDatas);
            monthDatas.add(cloudVideoGridEntity);
        }
    }

    /**
     * 转换2017-01-20T00:00:00为2017-01-20
     */
    private String formatStandardTime(String dateTime) {
        String[] dt = dateTime.split("T");
        return dt[0];
    }

    /**
     * 转换2017-01-20T00:00:00成2017-01
     *
     * @param date
     * @return
     */
    private String tranYearMonth(String date) {
        String s = formatStandardTime(date);
        String[] split = s.split("-");
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
        //清空所有选中状态,重新加载原始数据
        for (CloudVideoEntity.ListvideosBean showData : showDatas) {
            showData.setSelectFlag(false);
            showData.setExpandFlag(false);
        }
        switch (videoType) {
            case 0:
                cloudVideoListAdapter.setIsChoose(isChoose);
                break;
            case 1:
                cloudVideoMonthAdapter.setIsChoose(isChoose);
                break;
            case 2:
                cloudVideoDayAdapter.setIsChoose(isChoose);
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
                cloudVideoListAdapter.notifyDataSetChanged();
                break;
            case 1://月视图
                cloudVideoMonthAdapter.notifyDataSetChanged();
                break;
            case 2://日视图
                cloudVideoDayAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 显示列表视图
     */
    private void showList() {
        listView.setAdapter(cloudVideoListAdapter);
        cloudVideoListAdapter.setDatas(showDatas);
        cloudVideoListAdapter.setIsChoose(isChoose);
    }

    /**
     * 显示月视图
     */
    private void showMonth() {
        listView.setAdapter(cloudVideoMonthAdapter);
        cloudVideoMonthAdapter.setDatas(monthDatas);
        cloudVideoMonthAdapter.setIsChoose(isChoose);
    }

    /**
     * 显示日视图
     */
    private void showDay() {
        listView.setAdapter(cloudVideoDayAdapter);
        cloudVideoDayAdapter.setDatas(dayDatas);
        cloudVideoDayAdapter.setIsChoose(isChoose);
    }

    /**
     * 获取选中的数据源
     */
    private void accessCheckedId() {
        selectedDatas.clear();
        for (int i = 0; i < showDatas.size(); i++) {
            if (showDatas.get(i).isSelectFlag()) {
                //选中状态
                selectedDatas.add(showDatas.get(i));
            }
        }
    }

    @OnClick({R.id.tv_edit, R.id.tv_download, R.id.tv_delete})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                //多选编辑
                MultiSelectEditor();
                break;
            case R.id.tv_download:
                //多选下载
                MultiSelectDownload();
                break;
            case R.id.tv_delete:
                //多选删除
                MultiSelectDelete();
                break;
        }
    }

    /**
     * 多选编辑
     */
    private void MultiSelectEditor() {
        Log.d(TAG, "MultiSelectEditor: --->多选编辑");
        accessCheckedId();
        if (selectedDatas != null && selectedDatas.size() > 0) {
            //有选中视频
            Intent intent = new Intent(getActivity(), CloudVideoEditActivity.class);
            intent.putExtra("selectedDatas", selectedDatas);
            startActivity(intent);
            cancelSelectState();
        } else {
            //没有选中视频
            Toast.makeText(getActivity(), "请至少选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 多选下载
     */
    private void MultiSelectDownload() {
        accessCheckedId();
        if (selectedDatas != null && selectedDatas.size() > 0) {
            //有选中视频
            Log.i(TAG, "MultiSelectDownload: +下载");
            setDownloadDatas(selectedDatas);
            Intent intent = new Intent(getActivity(), DownLoadVideoActivity.class);
            intent.putExtra("listVideos", selectedDatas);
            startActivity(intent);
            //取消选择状态
            cancelSelectState();
        } else {
            //没有选中视频
            Toast.makeText(getActivity(), "请至少选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 多选删除
     */
    private void MultiSelectDelete() {
        accessCheckedId();
        if (selectedDatas != null && selectedDatas.size() > 0) {
            //有选中视频
            new AlertDialog(getActivity()).builder()
                    .setTitle("确认删除选中视频")
                    .setPositiveButton("删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "MultiSelectDelete: --->多选删除");
                            deleteListVideos(selectedDatas);
                        }
                    })
                    .setNegativeButton("取消", null).show();
        } else {
            //没有选中视频
            Toast.makeText(getActivity(), "请至少选择一个视频", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置下载进度的数据源
     */
    private void setDownloadDatas(List<CloudVideoEntity.ListvideosBean> selectedDatas) {
        downloadDatas = new ArrayList<>();
        downloadDatas.addAll(selectedDatas);
    }

    /**
     * 取消当前选择状态
     */
    private void cancelSelectState() {
        Intent intent = new Intent("com.glory.broadcast.NATIVE_CANCEL");
        localBroadcastManager.sendBroadcast(intent);
    }

    private void deleteOne(final int id) {
        CloudVideoRequestEntity cloudVideoRequestEntity = new CloudVideoRequestEntity();
        CloudVideoRequestEntity.VideosBean videosBean = new CloudVideoRequestEntity.VideosBean();
        videosBean.setVideo_id(id);
        cloudVideoRequestEntity.setVideos(videosBean);
        String request = new Gson().toJson(cloudVideoRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APIVideos/DeleteVideo";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        isRefresh = true;
                        page = 1;
                        loadDatas();
                    } else {
                        Toast.makeText(getActivity(), "删除失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissWaiting();
            }

            @Override
            public void requestFailed() {
                dismissWaiting();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(getActivity(), url, requestJson);
    }

    private void deleteListVideos(List<CloudVideoEntity.ListvideosBean> selectDatas) {
        showLoading();
        CloudVideoRequestEntity cloudVideoRequestEntity = new CloudVideoRequestEntity();
        List<CloudVideoRequestEntity.VideosBean> listVideos = new ArrayList<>();
        for (int i = 0; i < selectDatas.size(); i++) {
            CloudVideoRequestEntity.VideosBean videosBean = new CloudVideoRequestEntity.VideosBean();
            videosBean.setVideo_id(selectDatas.get(i).getVideo_id());
            listVideos.add(videosBean);
        }
        cloudVideoRequestEntity.setListvideos(listVideos);
        String request = new Gson().toJson(cloudVideoRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), request);
        String url = "/api/APIVideos/DeleteListVideo";
        Log.i(TAG, "deleteListVideos: " + requestJson);
        Log.i(TAG, "deleteListVideos: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        //取消选择状态
                        cancelSelectState();
                        //重新加载数据
                        isRefresh = true;
                        page = 1;
                        loadDatas();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(getActivity(), url, requestJson);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
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
                    CloudVideoEntity.ListvideosBean listvideosBean = showDatas.get(position - 2);
                    listvideosBean.setSelectFlag(!listvideosBean.isSelectFlag());
                    notifyDataChange();
                } else {
                    //正常模式

                }
            }//另外两个视图在内部处理
        } else if (position == 1) {
            //点击了下载进度
            Intent intent = new Intent(getActivity(), DownLoadVideoActivity.class);
            intent.putExtra("listVideos", downloadDatas);
            startActivity(intent);
        } else {//搜索，不执行操作
        }
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
        List<CloudVideoEntity.ListvideosBean> searchDatas = new ArrayList<>();
        if (str.isEmpty()) {
            searchDatas.addAll(datas);
        } else {
            for (int i = 0; i < datas.size(); i++) {
                String title = datas.get(i).getVideo_tag();
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
    public void getEventDatas(Map<String, ArrayList<CloudVideoEntity.ListvideosBean>> map) {
        if (map.containsKey("CloudVideoFragment")) {
            if (map.get("CloudVideoFragment") instanceof ArrayList) {
                ArrayList<CloudVideoEntity.ListvideosBean> listVideos = map.get("CloudVideoFragment");
                setDownloadDatas(listVideos);
                listVideos = null;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<String, String> map) {
        if (map.containsKey("CloudVideoFragment")) {
            if (map.get("CloudVideoFragment").equals("refresh")) {
                isRefresh = true;
                page = 1;
                loadDatas();
            }
        }
    }
}
