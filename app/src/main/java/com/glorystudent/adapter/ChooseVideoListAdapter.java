package com.glorystudent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.glorystudent.entity.LocalVideoEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/3/8.
 */
public class ChooseVideoListAdapter extends AbsBaseAdapter<LocalVideoEntity> {

    public ChooseVideoListAdapter(Context context) {
        super(context, R.layout.item_choose_video_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, LocalVideoEntity data) {
        ImageView iv_video_pic = (ImageView) viewHolder.getView(R.id.iv_video_pic);
        iv_video_pic.setImageBitmap(data.getBitmap());
        viewHolder.setTextView(R.id.tv_title, data.getTitle());
        viewHolder.setTextView(R.id.tv_context, data.getContent());
        viewHolder.setTextView(R.id.tv_time, data.getDate());
    }
}
