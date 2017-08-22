package com.glorystudent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.glorystudent.entity.OpinionUserEntity.ListusersBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * 意向学员适配器
 * Created by hyj on 2017/1/9.
 */
public class OpinionUserListAdapter extends AbsBaseAdapter<ListusersBean>{

    public OpinionUserListAdapter(Context context) {
        super(context, R.layout.item_student_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListusersBean data) {
        ImageView iv = (ImageView) viewHolder.getView(R.id.iv_head_portrait);
        GlideUtil.loadCircleImageView(context, data.getCustomerphoto(), iv, R.drawable.head_default);
        viewHolder.setTextView(R.id.tv_username, data.getUsername());
    }
}
