package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.VideoEntity.ListTeachingVideoBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2016/12/20.
 */
public class VideoListAdapter extends AbsBaseAdapter<ListTeachingVideoBean> {
    public VideoListAdapter(Context context) {
        super(context, R.layout.item_video_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListTeachingVideoBean data) {
        viewHolder.setImageView(R.id.video_lv_iv, data.getTeachingvideo_picture(), R.drawable.pic_placeholder);
        viewHolder.setTextView(R.id.video_lv_title, data.getTeachingvideo_tittle());
        viewHolder.setTextView(R.id.video_lv_context, data.getTeachingvideo_context());
    }
}
