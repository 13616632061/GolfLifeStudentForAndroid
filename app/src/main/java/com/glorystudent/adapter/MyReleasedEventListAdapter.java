package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.entity.EventCompetityEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.EventDetail2Activity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.TimeUtil;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MyReleasedEventListAdapter extends AbsBaseAdapter<EventCompetityEntity.ListeventactivityBean> {
    public MyReleasedEventListAdapter(Context context) {
        super(context, R.layout.item_my_released_event_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final EventCompetityEntity.ListeventactivityBean data) {
        if (data != null) {
            viewHolder.setTextView(R.id.tv_my_released_event_name, data.getEventactivity_name());
            viewHolder.setTextView(R.id.tv_my_released_sign_up_number, data.getSignUpNumber() + "");
            if (data.getEventactivity_begintime() != null) {
                viewHolder.setTextView(R.id.tv_my_released_start_time, TimeUtil.getEventTime(TimeUtil.getStandardDate(data.getEventactivity_begintime())) + "开始");
            }
            if (data.getListeventpic().size() > 0) {
                GlideUtil.loadImageView(context, data.getListeventpic().get(0).getEventactivity_picpath(), (ImageView) viewHolder.getView(R.id.iv_my_released_pic));
            }
            viewHolder.getView(R.id.tv_look_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Map<String, EventCompetityEntity.ListeventactivityBean> map = new HashMap<>();
//                map.put("EventDetail2Activity", data);
//                EventBus.getDefault().postSticky(map);
                    Intent intent = new Intent(context, EventDetail2Activity.class);
                    intent.putExtra("id", data.getEventActivity_id());
                    intent.putExtra("state", data.getEventactivity_state());
                    context.startActivity(intent);
                }
            });
            //赛事状态 -0 未开赛 1 进行中 2已结束 3 已删除 4 取消活动
            if (data.getEventactivity_state().equals("4")) {//已取消活动
                viewHolder.getView(R.id.tv_released_event_if_cancel).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.tv_released_event_if_cancel).setVisibility(View.GONE);
            }
        }
    }
}
