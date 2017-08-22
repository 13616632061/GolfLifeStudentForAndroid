package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.EventInformationEntity.ListEventBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 赛事信息适配器
 * Created by hyj on 2017/1/11.
 */
public class EventCalendarListAdapter extends AbsBaseAdapter<ListEventBean>{

    public EventCalendarListAdapter(Context context) {
        super(context, R.layout.item_event_calendar_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListEventBean data) {
        String[] begintime = data.getEvents_begintime().split("T");
        String[] endtime = data.getEvents_endtime().split("T");
        viewHolder.setTextView(R.id.tv_events_name, data.getEvents_name());
        viewHolder.setTextView(R.id.tv_events_start, begintime[0]);
        viewHolder.setTextView(R.id.tv_events_end, endtime[0]);
    }
}
