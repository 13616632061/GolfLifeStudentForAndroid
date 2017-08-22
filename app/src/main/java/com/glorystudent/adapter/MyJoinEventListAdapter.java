package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.entity.MyJoinEventEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.LookCertificateActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.util.TimeUtil;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MyJoinEventListAdapter extends AbsBaseAdapter<MyJoinEventEntity.ListsignupBean> {

    private static final String TAG = "MyJoinEventListAdapter";


    public MyJoinEventListAdapter(Context context) {
        super(context, R.layout.item_my_join_event_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, final MyJoinEventEntity.ListsignupBean data) {
        if (data != null) {
            viewHolder.setTextView(R.id.tv_join_event_name, data.getEventactivityname());
            //返回时间类型 2017/5/16 0:00:00
            if (data.getEventactivitybegintime() != null) {
                viewHolder.setTextView(R.id.tv_start_time, TimeUtil.getEventTime(TimeUtil.getDateFromJoin(data.getEventactivitybegintime())) + "开始");
            }
            viewHolder.setTextView(R.id.tv_sign_up_number, data.getSignUpNumber() + "");
            GlideUtil.loadImageView(context, data.getEventactivitymainpic(), (ImageView) viewHolder.getView(R.id.iv_pic));

            switch (data.getSign_state()) {
                case "1"://未付款
                    viewHolder.setTextView(R.id.tv_join_event_if_cancel, "待付款");
                    break;
                case "2"://报名成功
                    viewHolder.setTextView(R.id.tv_join_event_if_cancel, "已报名");
                    break;
                case "3"://取消报名
                    viewHolder.setTextView(R.id.tv_join_event_if_cancel, "已取消");
                    break;
                case "4"://已拒绝
                    viewHolder.setTextView(R.id.tv_join_event_if_cancel, "已拒绝");
                    break;
//            case "5"://已付款
//                break;
                default:
                    break;
            }
            viewHolder.getView(R.id.tv_look_certificate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Map<String, MyJoinEventEntity.ListsignupBean> map = new HashMap<>();
//                map.put("LookCertificateActivity", data);
//                EventBus.getDefault().postSticky(data);
                    Intent intent = new Intent(context, LookCertificateActivity.class);
                    intent.putExtra("signUpId", data.getSignup_id());
                    intent.putExtra("eventActivityId", data.getSign_activitiesid());
                    context.startActivity(intent);
                }
            });
        }
    }
}
