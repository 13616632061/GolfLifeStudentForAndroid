package com.glorystudent.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.glorystudent.adapter.CalendarViewPagerAdapter;
import com.glorystudent.adapter.EventCalendarListAdapter;
import com.glorystudent.entity.EventInformationEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflife.R;
import com.glorystudent.util.RequestUtil;
import com.glorystudent.widget.PullToRefreshLayout;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 赛事日历
 * Created by hyj on 2017/1/10.
 */
public class EventCalendarFragment extends BaseFragment implements ViewPager.OnPageChangeListener, CalendarFragment.OnCaleadarDayClick {
    private final static String TAG = "EventCalendarFragment";


    @Bind(R.id.calendar_vp)
    public ViewPager calendar_vp;

    @Bind(R.id.tv_calendar)
    public TextView tv_calendar;
    @Bind(R.id.no_event)
    public TextView tvNoEvent;
    @Bind(R.id.event_information_lv)
    public ListView eventInfoLv;
    private EventCalendarListAdapter eventCalendarListAdapter;
    private Map<String, List<EventInformationEntity.ListEventBean>> listMap;

    private CalendarViewPagerAdapter calendarFragmentViewPager;

    private int currentPosition;
    private int year;
    private int month;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh = true;//true 是下拉刷新， false 是上拉加载

    @Override
    protected int getContentId() {
        return R.layout.fragment_event_calendar;
    }

    @Override
    protected void init(View view) {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.NOMORE);
            }
        });

        calendar_vp.setOffscreenPageLimit(0);
        String currentTime = RequestUtil.getCurrentTime();
        String[] split = currentTime.split("-");
        year = Integer.valueOf(split[0]);
        month = Integer.valueOf(split[1]);
        tv_calendar.setText(year + "年" + month + "月");
        calendarFragmentViewPager = new CalendarViewPagerAdapter(getChildFragmentManager(), year, month, this);
        calendar_vp.setAdapter(calendarFragmentViewPager);
        currentPosition = Integer.MAX_VALUE / 2;
        calendar_vp.setCurrentItem(currentPosition);
        calendar_vp.setOffscreenPageLimit(2);//设置缓存页数2，即前后各一张
        calendar_vp.addOnPageChangeListener(this);

        eventCalendarListAdapter = new EventCalendarListAdapter(getActivity());
        eventInfoLv.setAdapter(eventCalendarListAdapter);
    }

    @Override
    protected void loadDatas() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //核心算法(使日历左右滑动,显示正确的日历)
        month = month + position - currentPosition;
        if (month <= 0) {
            year--;
            month = 12 + month;
        } else if (month > 12) {
            year++;
            month = month % 12;
        }
        currentPosition = position;
        tv_calendar.setText(year + "年" + month + "月");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @OnClick({R.id.left, R.id.right})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.left:
                //上一月
                calendar_vp.setCurrentItem(currentPosition - 1);
                break;
            case R.id.right:
                //下一月
                calendar_vp.setCurrentItem(currentPosition + 1);
                break;
        }
    }

    @Override
    public void onCaleadarDayClick(String time, Map<String, List<EventInformationEntity.ListEventBean>> map) {
        Log.i(TAG, "onCaleadarDayClick: 执行" + map.size() + "-->" + time);
        if (map.containsKey(time)) {
            Log.i(TAG, "onCaleadarDayClick: 包含");
            eventInfoLv.setVisibility(View.VISIBLE);
            eventCalendarListAdapter.setDatas(map.get(time));
            tvNoEvent.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "onCaleadarDayClick: 不包含");
            eventInfoLv.setVisibility(View.GONE);
            tvNoEvent.setVisibility(View.VISIBLE);
        }
    }
}
