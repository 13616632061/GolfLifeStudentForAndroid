package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.CitysEntity.ListchinacityBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 城市列表
 * Created by hyj on 2016/12/23.
 */
public class CitysListAdapter extends AbsBaseAdapter<ListchinacityBean> {

    public CitysListAdapter(Context context) {
        super(context, R.layout.item_city_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListchinacityBean data) {
        viewHolder.setTextView(R.id.lv_tv_cicy, data.getName());
    }
}
