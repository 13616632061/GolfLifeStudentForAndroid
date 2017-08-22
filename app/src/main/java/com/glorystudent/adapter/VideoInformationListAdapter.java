package com.glorystudent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.glorystudent.entity.StuNewsEntity.ListnewsBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 视频资讯适配器
 * Created by hyj on 2017/1/9.
 */
public class VideoInformationListAdapter extends AbsBaseAdapter<ListnewsBean>{
    public VideoInformationListAdapter(Context context) {
        super(context, R.layout.item_video_information_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ListnewsBean data) {
        ImageView iv_homepage  = (ImageView) viewHolder.getView(R.id.iv_homepage);
        viewHolder.setTextView(R.id.tv_title, data.getNews_title());
        viewHolder.setImageView(R.id.iv_homepage, data.getNews_picture(), R.drawable.pic_default_avatar);
    }
}
