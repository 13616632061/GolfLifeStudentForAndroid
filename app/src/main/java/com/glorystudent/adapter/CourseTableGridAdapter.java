package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.glorystudent.entity.TimeDefineEntity.ListtimedefinitionBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * 课表
 * Created by hyj on 2017/1/12.
 */
public class CourseTableGridAdapter extends AbsBaseAdapter<ListtimedefinitionBean> {
    private OnTimeClickListener onTimeClickListener;

    private RelativeLayout saveRl;
    public void setOnTimeClickListener(OnTimeClickListener onTimeClickListener) {
        this.onTimeClickListener = onTimeClickListener;
    }

    public CourseTableGridAdapter(Context context) {
        super(context, R.layout.item_course_table_grid);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ListtimedefinitionBean data) {
        viewHolder.setTextView(R.id.tv_time, data.getTimedefinitionBean().getTimesection());
        ImageView iv_head = (ImageView) viewHolder.getView(R.id.iv_head);
        if (data.getTimedefinitionBean().getListAppointmentBean() != null) {
            iv_head.setVisibility(View.VISIBLE);
            GlideUtil.loadCircleImageView(context, data.getTimedefinitionBean().getListAppointmentBean().getCustomerPhoto(), iv_head, R.drawable.icon_chat_golffriend);
        }else{
            iv_head.setVisibility(View.GONE);
        }

        RelativeLayout rl = (RelativeLayout) viewHolder.getView(R.id.rl_table);
        rl.setTag(getCurrentPosition());
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (saveRl != null) {
                    saveRl.setBackgroundResource(R.drawable.shape_layout_whiteborder);
                }
                v.setBackgroundResource(R.drawable.shape_layout_orgborder);
                if (onTimeClickListener != null) {
                    onTimeClickListener.onClick(tag, data.getTimedefinitionBean().getTimesection());
                }
                saveRl = (RelativeLayout) v;
            }
        });
    }

    public interface OnTimeClickListener{
        void onClick(int position, String time);
    }
}
