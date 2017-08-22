package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.entity.ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 我的预约适配器
 * Created by hyj on 2017/2/22.
 */
public class CoursePactListAdapter extends AbsBaseAdapter<AppointmentsBean> {
    public CoursePactListAdapter(Context context) {
        super(context, R.layout.item_my_reservation_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, AppointmentsBean data) {
        String title = "第" + data.getCoursecode() + "课" + " " + data.getCcoursedetailname();
        viewHolder.setTextView(R.id.tv_title, title);
        viewHolder.setTextView(R.id.tv_content, data.getPackagename());
        String apponitmentstatus = data.getApponitmentstatus();
        ImageView iv_enter = (ImageView) viewHolder.getView(R.id.iv_enter);
        TextView tv_state = (TextView) viewHolder.getView(R.id.tv_state);
        switch (apponitmentstatus) {
            case "1":
                //未开始
                iv_enter.setVisibility(View.VISIBLE);
                tv_state.setVisibility(View.GONE);
                break;
            case "2":
                //已结束
                iv_enter.setVisibility(View.GONE);
                tv_state.setVisibility(View.VISIBLE);
                break;
        }
    }
}
