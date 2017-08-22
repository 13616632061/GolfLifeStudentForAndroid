package com.glorystudent.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.entity.VideoReviewEntity.ListTVideoCommentBean;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

import java.util.Map;

/**
 * 视频详情
 * Created by hyj on 2016/12/30.
 */
public class VideoDetailsListAdapter extends AbsBaseMoreListAdapter<ListTVideoCommentBean> implements CompoundButton.OnCheckedChangeListener {

    private TextView tv_num;
    private int comment_id;
    private boolean isLikeChecked = false;//false 未点赞， true 已点赞
    private OnLikeCheckedChangeListener onLikeCheckedChangeListener;
    private CheckBox cb_like;

    public void setOnLikeCheckedChangeListener(OnLikeCheckedChangeListener onLikeCheckedChangeListener) {
        this.onLikeCheckedChangeListener = onLikeCheckedChangeListener;
    }

    public VideoDetailsListAdapter(Context context) {
        super(context, R.layout.item_video_details_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListTVideoCommentBean data, Map<Integer, Boolean> auxiliaryDatas) {
        comment_id = data.getComment_id();
        Log.d("print", "bindView: --->" + comment_id);
        ImageView head_portrait = (ImageView) viewHolder.getView(R.id.iv_head_portrait);
        GlideUtil.loadCircleImageView(context, (String) data.getComment_customerphoto(), head_portrait);
        viewHolder.setTextView(R.id.tv_nickname, data.getComment_username());
        viewHolder.setTextView(R.id.tv_context, data.getComment_context());
        cb_like = (CheckBox) viewHolder.getView(R.id.cb_like);
        cb_like.setOnCheckedChangeListener(this);
        cb_like.setTag(comment_id);

        tv_num = (TextView) viewHolder.getView(R.id.tv_num);
        cb_like.setTag(R.id.tag_text, tv_num);

        if (auxiliaryDatas.containsKey(data.getComment_id())) {
            cb_like.setBackgroundResource(R.drawable.icon_home_praise_h);
            tv_num.setText("1");
            isLikeChecked = true;
        }else{
            cb_like.setBackgroundResource(R.drawable.icon_home_praise_n);
            tv_num.setText("0");
            isLikeChecked = false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox checkBox = (CheckBox) buttonView;
        isLikeChecked = !isLikeChecked;
        if (onLikeCheckedChangeListener != null) {
            int tag = (int) checkBox.getTag();
            onLikeCheckedChangeListener.onlikeCheckedChanged(buttonView, isLikeChecked , tag);
        }
        TextView tv = (TextView) checkBox.getTag(R.id.tag_text);
        if (isLikeChecked) {
                Log.d("print", "onCheckedChanged: --->1");
                checkBox.setBackgroundResource(R.drawable.icon_home_praise_h);
                tv.setText("1");
            }else{
                Log.d("print", "onCheckedChanged: --->2");
                checkBox.setBackgroundResource(R.drawable.icon_home_praise_n);
                tv.setText("0");
        }
    }

    public interface OnLikeCheckedChangeListener{
        void onlikeCheckedChanged(CompoundButton buttonView, boolean isChecked, int comment_id);
    }
}
