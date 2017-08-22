package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.SchoolEntity.ListcoachgroupBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 选择院校
 * Created by hyj on 2016/12/26.
 */
public class ChooseSchoolAdapter extends AbsBaseAdapter<ListcoachgroupBean>{
    public ChooseSchoolAdapter(Context context) {
        super(context, R.layout.item_simple_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListcoachgroupBean data) {
        viewHolder.setTextView(R.id.lv_address_tv, data.getGroupname());
    }
}
