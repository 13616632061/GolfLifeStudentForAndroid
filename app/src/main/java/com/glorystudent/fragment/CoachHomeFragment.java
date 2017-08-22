package com.glorystudent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.adapter.CalendarWeekGridAdapter;
import com.glorystudent.adapter.CoachHomeViewPagerAdapter;
import com.glorystudent.adapter.HomeDayViewPagerAdapter;
import com.glorystudent.adapter.HomeListAdapter;
import com.glorystudent.dialog.Dialog;
import com.glorystudent.entity.AdEntity;
import com.glorystudent.entity.AdRequestEntity;
import com.glorystudent.entity.NewsEntity;
import com.glorystudent.entity.NewsRequestEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflibrary.widget.oywidget.MyListView;
import com.glorystudent.golflife.AssessmentClassifyActivity;
import com.glorystudent.golflife.CourseTableActivity;
import com.glorystudent.golflife.EventActivity;
import com.glorystudent.golflife.IndustryCalendarActivity;
import com.glorystudent.golflife.MyAttestationActivity;
import com.glorystudent.golflife.NewsDetailsActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.StudentsFileActivity;
import com.glorystudent.golflife.TeamManagementActivity;
import com.glorystudent.golflife.TodayRecommendationActivity;
import com.glorystudent.golflife.UploadScorecardActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.widget.PullToRefreshLayout;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.util.TimeUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 教练端首页模块
 * Created by hyj on 2016/12/14.
 */
public class CoachHomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {
    private static String TAG = "CoachHomeFragment";
    private int newsPage = 1;//默认加载第一页
    private NewsEntity newsEntity;

    @Bind(R.id.gv_week)
    public GridView gv_week;
    private String[] week = new String[]{"一", "二", "三", "四", "五", "六", "日"};
    private CalendarWeekGridAdapter calendarWeekGridAdapter;

    @Bind(R.id.day_vp)
    public ViewPager day_vp;
    private HomeDayViewPagerAdapter dayViewPagerAdapter;

    @Bind(R.id.vp_head)
    public ViewPager vp_head;

    @Bind(R.id.iv_ad)
    public ImageView iv_ad;

    @Bind(R.id.home_lv)
    public MyListView home_lv;

    @Bind(R.id.rl_ad)
    public RelativeLayout rl_ad;

    private List<String> datas;
    private PopupWindow window;
    private HomeListAdapter homeListAdapter;//ListView设配器
    private List<NewsEntity.ListnewsBean> news;
    private List<NewsEntity.ListnewsBean> newsDatas;
    @Bind(R.id.tv_date)
    public TextView tv_date;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.glory.broadcast.HomeChooseDate":
                    String date = intent.getStringExtra("date");
                    vp_head.setCurrentItem(Constants.MAX / 2 + TimeUtil.daysBetween(date, RequestUtil.getCurrentTime()));
                    break;
            }
        }
    };
    private String currentTime;
    private CoachHomeViewPagerAdapter coachHomeViewPagerAdapter;
    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载
    private int p, q;
    private int currentPage;
    private String startDay, endDay;
    private boolean isloadingAD = false;
    private boolean isloadingNews = false;
    private String groupid;

    @Override
    protected int getContentId() {
        return R.layout.fragment_coach_home;
    }

    /**
     * TODO 初始化
     * 初始化
     *
     * @param view
     */
    @Override
    protected void init(View view) {
        EventBus.getDefault().register(this);
        showLoading();
        groupid = SharedUtil.getString(Constants.GROUP_ID);
        currentPage = Constants.MAX / 2;
        int week = TimeUtil.getWeek(RequestUtil.getCurrentTime());
        startDay = TimeUtil.subDate(week);
        endDay = TimeUtil.subDate(-(7 - week + 1));
        coachHomeViewPagerAdapter = new CoachHomeViewPagerAdapter(getChildFragmentManager(), RequestUtil.getCurrentTime());
        vp_head.setAdapter(coachHomeViewPagerAdapter);
        vp_head.setOffscreenPageLimit(3);
        vp_head.setCurrentItem(Constants.MAX / 2);
        vp_head.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int subDay = Constants.MAX / 2 - position;
                String date = TimeUtil.subDate(subDay);
                if (TimeUtil.isInDate(date, "1990-01-01", startDay)) {
                    //往左翻页
                    day_vp.setCurrentItem(--currentPage);
                    int week = TimeUtil.getWeek(date);
                    startDay = TimeUtil.subDate(week, date);
                    endDay = TimeUtil.subDate(-(7 - week + 1), date);
                } else if (TimeUtil.isInDate(date, endDay, "2100-01-01")) {
                    //往右翻页
                    day_vp.setCurrentItem(++currentPage);
                    int week = TimeUtil.getWeek(date);
                    startDay = TimeUtil.subDate(week, date);
                    endDay = TimeUtil.subDate(-(7 - week + 1), date);
                }
                SharedUtil.putString(Constants.HOME_CALENDAR_CLICK, date);
                Intent intent = new Intent();
                intent.setAction("com.glory.broadcast.Calendar");
                localBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.glory.broadcast.HomeChooseDate");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                newsPage = 1;
                EventBus.getDefault().post(EventBusMapUtil.getIntMap(5, 1));
                EventBus.getDefault().post(EventBusMapUtil.getIntMap(4, 1));
                loadDatas();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                newsPage++;
                loadDatas();
            }
        });
        addGridView();
        addHeadViewPager();
        initList();
    }

    private void initList() {
        homeListAdapter = new HomeListAdapter(getActivity());
        home_lv.setAdapter(homeListAdapter);
        home_lv.setOnItemClickListener(this);
    }


    private void addGridView() {
        currentTime = RequestUtil.getCurrentTime();
        String[] split = currentTime.split("-");
        tv_date.setText(split[0] + "年" + split[1] + "月");
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        SharedUtil.putString(Constants.HOME_CALENDAR_TODAY, RequestUtil.getCurrentTime());
        SharedUtil.putString(Constants.HOME_CALENDAR_CLICK, "");
        calendarWeekGridAdapter = new CalendarWeekGridAdapter(getActivity());
        gv_week.setAdapter(calendarWeekGridAdapter);
        List<String> weekdatas = Arrays.asList(week);
        calendarWeekGridAdapter.setDatas(weekdatas);

        dayViewPagerAdapter = new HomeDayViewPagerAdapter(getChildFragmentManager());
        day_vp.setAdapter(dayViewPagerAdapter);
        day_vp.setCurrentItem(10000 / 2);
        day_vp.addOnPageChangeListener(this);
        day_vp.setOffscreenPageLimit(2);
    }

    /**
     * 添加头部ViewPager
     */
    private void addHeadViewPager() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Intent intent = new Intent();
        intent.setAction("com.glory.broadcast.Calendar");
        localBroadcastManager.sendBroadcast(intent);
        int sub = (5000 - position) * 7 + TimeUtil.getWeek(RequestUtil.getCurrentTime()) - 1;
        String time = TimeUtil.subDate(sub);
        String[] split = time.split("-");
        currentPage = position;
        tv_date.setText(split[0] + "年" + split[1] + "月");
        int week = TimeUtil.getWeek(RequestUtil.getCurrentTime());
        startDay = TimeUtil.subDate(week + (10000 / 2 - position) * 7);
        endDay = TimeUtil.subDate(-(7 - week + 1) + (10000 / 2 - position) * 7);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsEntity.ListnewsBean data = homeListAdapter.getData(position);
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("newsid", data.getNews_id());
        Bundle bundle = new Bundle();
        bundle.putSerializable("newsBean", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * TODO 加载数据
     */
    @Override
    protected void loadDatas() {
        AdRequestEntity adRequestEntity = new AdRequestEntity();
        AdRequestEntity.AdBean adBean = new AdRequestEntity.AdBean();
        adBean.setAd_enable("true");
        adBean.setAd_location("2");
        adRequestEntity.setAd(adBean);
        String json = new Gson().toJson(adRequestEntity);
        String requestJson = RequestUtil.getRequestJson(getActivity(), json);
        OkGo.post(Constants.BASE_URL + "/api/APIAD/QueryAD")
                .tag(this)//
                .params("request", requestJson)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject jo = new JSONObject(s);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                AdEntity adEntity = new Gson().fromJson(jo.toString(), AdEntity.class);
                                List<AdEntity.ListadBean> listad = adEntity.getListad();
                                if (listad != null) {
                                    String ad_picture = listad.get(0).getAd_picture();
                                    GlideUtil.loadImageView(getActivity(), ad_picture, iv_ad);
                                    rl_ad.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        isloadingAD = true;
                        if (isloadingAD && isloadingNews) {
                            dismissLoading();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        isloadingAD = true;
                        if (isloadingAD && isloadingNews) {
                            dismissLoading();
                        }
                    }
                });


        NewsRequestEntity requestEntity = new NewsRequestEntity();
        NewsRequestEntity.NewsBean newsBean = new NewsRequestEntity.NewsBean();
        requestEntity.setNews(newsBean);
        requestEntity.setPage(newsPage);
        String newsJson = new Gson().toJson(requestEntity);
        String requestNewsJson = RequestUtil.getRequestJson(getActivity(), newsJson);
        Log.d(TAG, "loadDatas: --->" + requestNewsJson);
        OkGo.post(Constants.BASE_URL + "/api/APINews/QueryNews")
                .tag(this)//
                .params("request", requestNewsJson)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jo = new JSONObject(s);
                            String statuscode = jo.getString("statuscode");
                            String statusmessage = jo.getString("statusmessage");
                            if (statuscode.equals("1")) {
                                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
                                newsEntity = new Gson().fromJson(jo.toString(), NewsEntity.class);
                                if (newsEntity.getListnews() != null) {
                                    initDatas();
                                }
                            } else {
                                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        isloadingNews = true;
                        if (isloadingAD && isloadingNews) {
                            dismissLoading();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        isloadingNews = true;
                        if (isloadingAD && isloadingNews) {
                            dismissLoading();
                        }
                        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                        refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
                        //访问失败则当前页需要重新加载
                        newsPage--;
                    }
                });
    }


    /**
     * 初始化数据
     */
    private void initDatas() {
        newsDatas = new ArrayList<>();
        news = newsEntity.getListnews();
        for (int i = 0; i < news.size(); i++) {
            if (news.get(i).getNews_top().equals("0")) {
                newsDatas.add(news.get(i));
            }
        }
        if (isRefresh) {
            homeListAdapter.setDatas(newsDatas);
        } else {
            homeListAdapter.addDatas(newsDatas);
        }
    }

    @OnClick({R.id.iv_course_timetable, R.id.iv_upload_scorecard, R.id.rv_event, R.id.rv_evaluation, R.id.rv_record,
            R.id.rv_calendar, R.id.ll_recommended, R.id.back_today, R.id.iv_close_ad, R.id.rv_team})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_course_timetable:
                //课表
                if (groupid != null) {
                    startActivity(new Intent(getActivity(), CourseTableActivity.class));
                } else {
                    Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                        @Override
                        public void onSure() {
                            startActivity(new Intent(getActivity(), MyAttestationActivity.class));
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).showDialog(getActivity(), "此功能只有教练才能使用", "是否申请教练", "前往");
                }
                break;
            case R.id.back_today:
                //回到今天
                SharedUtil.putString(Constants.HOME_CALENDAR_TODAY, RequestUtil.getCurrentTime());
                SharedUtil.putString(Constants.HOME_CALENDAR_CLICK, "");
                dayViewPagerAdapter = new HomeDayViewPagerAdapter(getChildFragmentManager());
                day_vp.setAdapter(dayViewPagerAdapter);
                day_vp.setCurrentItem(10000 / 2);
                currentPage = Constants.MAX / 2;
                int week = TimeUtil.getWeek(RequestUtil.getCurrentTime());
                startDay = TimeUtil.subDate(week);
                endDay = TimeUtil.subDate(-(7 - week + 1));
                coachHomeViewPagerAdapter = new CoachHomeViewPagerAdapter(getChildFragmentManager(), RequestUtil.getCurrentTime());
                vp_head.setAdapter(coachHomeViewPagerAdapter);
                vp_head.setOffscreenPageLimit(3);
                vp_head.setCurrentItem(Constants.MAX / 2);
                break;
            case R.id.iv_upload_scorecard:
                //上传记分卡
                if (SharedUtil.getBoolean(Constants.SCORECARD_POPUP_WINDOW_STATE)) {
                    startActivity(new Intent(getActivity(), UploadScorecardActivity.class));
                } else {
                    showPopwindow();
                }
                break;
            case R.id.rv_event:
                //赛事活动
                startActivity(new Intent(getActivity(), EventActivity.class));
                break;
            case R.id.rv_evaluation:
                //测试数据
                startActivity(new Intent(getActivity(), AssessmentClassifyActivity.class));
                break;
            case R.id.rv_record:
                //学员档案
                if (groupid != null) {
                    startActivity(new Intent(getActivity(), StudentsFileActivity.class));
                } else {
                    Dialog.getInstance().setOnShowDialogListener(new Dialog.OnShowDialogListener() {
                        @Override
                        public void onSure() {
                            startActivity(new Intent(getActivity(), MyAttestationActivity.class));
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).showDialog(getActivity(), "此功能只有教练才能使用", "是否申请教练", "前往");
                }
                break;
            case R.id.rv_calendar:
                //行业日历
                startActivity(new Intent(getActivity(), IndustryCalendarActivity.class));
                break;
            case R.id.rv_team:
                //球队管理
                startActivity(new Intent(getActivity(), TeamManagementActivity.class));
                break;
            case R.id.ll_recommended:
                //查看更多
                startActivity(new Intent(getActivity(), TodayRecommendationActivity.class));
                break;
            case R.id.iv_close_ad:
                //关闭广告
                rl_ad.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_upload_scorecard, null);
        TextView tv_upload = (TextView) view.findViewById(R.id.tv_upload);
        TextView tv_not_prompt = (TextView) view.findViewById(R.id.tv_not_prompt);
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                startActivity(new Intent(getActivity(), UploadScorecardActivity.class));
            }
        });
        tv_not_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                startActivity(new Intent(getActivity(), UploadScorecardActivity.class));
                SharedUtil.putBoolean(Constants.SCORECARD_POPUP_WINDOW_STATE, true);
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(getActivity().findViewById(R.id.iv_upload_scorecard),
                Gravity.BOTTOM, 0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("CoachHome")) {
            if (map.get("CoachHome").equals("expand")) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 180));
                vp_head.setLayoutParams(lp);
            } else if (map.get("CoachHome").equals("packup")) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 78));
                vp_head.setLayoutParams(lp);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedUtil.putString(Constants.HOME_CALENDAR_CLICK, "");
        EventBus.getDefault().unregister(this);
    }
}
