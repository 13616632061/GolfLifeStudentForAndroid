package com.glorystudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.glorystudent.entity.CourseDetailsEntity.ContractcourseBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 学员端预约
 * Created by hyj on 2017/2/22.
 */
public class StudentAppointmentListAdapter extends AbsBaseAdapter<ContractcourseBean> {
    private OnAppointmentClickListener onAppointmentClickListener;
    private OnDetailsClickListener onDetailsClickListener;

    public void setOnDetailsClickListener(OnDetailsClickListener onDetailsClickListener) {
        this.onDetailsClickListener = onDetailsClickListener;
    }

    public void setOnAppointmentClickListener(OnAppointmentClickListener onAppointmentClickListener) {
        this.onAppointmentClickListener = onAppointmentClickListener;
    }

    public StudentAppointmentListAdapter(Context context) {
        super(context, R.layout.item_student_appointment_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ContractcourseBean data) {
        viewHolder.setTextView(R.id.tv_num, "第" + data.getCoursecode() + "节");
        viewHolder.setTextView(R.id.tv_name, data.getCcoursedetailname());
        TextView tv_appointment = (TextView) viewHolder.getView(R.id.tv_appointment);
        if (data.isFinish()) {
            tv_appointment.setText(R.string.my_text22);
            tv_appointment.setBackgroundColor(Color.TRANSPARENT);
            tv_appointment.setTextColor(context.getResources().getColor(R.color.colorGray3));
        } else if (data.isAppointment()) {
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
                    ContractcourseBean tag = (ContractcourseBean) v.getTag();
                    if (onDetailsClickListener != null) {
                        onDetailsClickListener.onClick(tag);
                    }
                }
            });

        }else {
            tv_appointment.setText(R.string.my_text20);
            tv_appointment.setBackgroundResource(R.drawable.shape_tv_appointment);
            tv_appointment.setTextColor(context.getResources().getColor(R.color.colorWhite));
            tv_appointment.setTag(getCurrentPosition());
            tv_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    if (onAppointmentClickListener != null) {
                        onAppointmentClickListener.onClick(tag);
                    }
                }
            });
        }
    }

    public interface OnAppointmentClickListener{
        void onClick(int position);
    }

    public interface OnDetailsClickListener{
        void onClick(ContractcourseBean data);
    }
}
