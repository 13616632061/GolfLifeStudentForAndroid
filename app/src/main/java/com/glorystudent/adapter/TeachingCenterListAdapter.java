package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.TeachingCenterEntity.ListgroupaddressBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/1/9.
 */
public class TeachingCenterListAdapter extends AbsBaseAdapter<ListgroupaddressBean> {
    public TeachingCenterListAdapter(Context context) {
        super(context, R.layout.item_teaching_center_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListgroupaddressBean data) {
        viewHolder.setTextView(R.id.tv_teaching_center, data.getDetailaddress());
    }
}
