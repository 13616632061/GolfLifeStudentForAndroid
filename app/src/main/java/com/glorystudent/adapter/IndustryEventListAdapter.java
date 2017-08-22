package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.IndustryEventEntity.ListIndustryeventsBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/1/10.
 */
public class IndustryEventListAdapter extends AbsBaseAdapter<ListIndustryeventsBean> {
    public IndustryEventListAdapter(Context context) {
        super(context, R.layout.item_industry_event_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListIndustryeventsBean data) {
        String industryevents_date = data.getIndustryevents_Begindate();
        if (industryevents_date != null) {
            String[] split;
            if (industryevents_date.contains("T")) {
                String[] ts = industryevents_date.split("T");
                split = ts[0].split("-");
            } else {
                split = new String[]{industryevents_date};
            }
            viewHolder.setTextView(R.id.tv_time, split[2]);
            viewHolder.setTextView(R.id.tv_address, data.getChinacity_name());
            viewHolder.setTextView(R.id.tv_title, data.getIndustryevents_tittle());
            viewHolder.setTextView(R.id.tv_organizers_name, data.getIndustryevents_organizer());
        }
    }
}
