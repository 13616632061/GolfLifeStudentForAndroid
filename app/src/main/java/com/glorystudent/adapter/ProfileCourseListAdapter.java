package com.glorystudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.glorystudent.entity.ContractInfoEntity.ListContractCourseDetailBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hyj on 2017/2/13.
 */
public class ProfileCourseListAdapter extends AbsBaseAdapter<ListContractCourseDetailBean> {
    private OnAppointmentClickListener onAppointmentClickListener;

    public void setOnAppointmentClickListener(OnAppointmentClickListener onAppointmentClickListener) {
        this.onAppointmentClickListener = onAppointmentClickListener;
    }

    public ProfileCourseListAdapter(Context context) {
        super(context, R.layout.item_profile_course_list);
    }


    @Override
    public void bindView(ViewHolder viewHolder, ListContractCourseDetailBean data) {
        TextView tv_serial = (TextView) viewHolder.getView(R.id.tv_serial);
        TextView tv_name = (TextView) viewHolder.getView(R.id.tv_name);
        tv_serial.setText(getCurrentPosition() + 1 + "");
        tv_name.setText(data.getCcoursedetailname());
        TextView tv_appointment = (TextView) viewHolder.getView(R.id.tv_appointment);
        if (data.isFinish()) {
            tv_appointment.setClickable(false);
            tv_serial.setTextColor(context.getResources().getColor(R.color.colorGray9));
            tv_name.setTextColor(context.getResources().getColor(R.color.colorGray9));
            if (data.getAppointmentTime() != null && !data.getAppointmentTime().isEmpty()) {
                tv_appointment.setText("已完成");
            }else {
                tv_appointment.setText(data.getAppointmentTime());
            }
            tv_appointment.setBackgroundColor(Color.TRANSPARENT);
            tv_appointment.setTextColor(context.getResources().getColor(R.color.colorGray9));

        } else if (data.isAppointment()) {
            tv_serial.setTextColor(context.getResources().getColor(R.color.colorOrange));
            tv_name.setTextColor(context.getResources().getColor(R.color.colorOrange));
            if (data.getAppointmentTime() != null && !data.getAppointmentTime().isEmpty()) {
                tv_appointment.setText(data.getAppointmentTime());
            }else {
                tv_appointment.setText(data.getAppointmentTime());
            }
            tv_appointment.setBackgroundColor(Color.TRANSPARENT);
            tv_appointment.setTextColor(context.getResources().getColor(R.color.colorOrange));
            tv_appointment.setTag(data);
            tv_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListContractCourseDetailBean tag = (ListContractCourseDetailBean) v.getTag();
                    EventBus.getDefault().post(tag);
                }
            });
        } else {
            tv_appointment.setClickable(true);
            tv_serial.setTextColor(context.getResources().getColor(R.color.primaryColor));
            tv_name.setTextColor(context.getResources().getColor(R.color.primaryColor));
            tv_appointment.setText("预约");
            tv_appointment.setBackgroundResource(R.drawable.shape_tv_appointment);
            tv_appointment.setTextColor(context.getResources().getColor(R.color.colorWhite));
            tv_appointment.setTag(data);
            tv_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("print", "onClick: ");
                    ListContractCourseDetailBean tag = (ListContractCourseDetailBean) v.getTag();
                    if (onAppointmentClickListener != null) {
                        onAppointmentClickListener.onClick(tag);
                    }
                }
            });
        }
    }




    interface OnAppointmentClickListener{
        void onClick(ListContractCourseDetailBean data);
    }
}
