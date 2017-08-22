package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.MyCourseEntity.CcourselistBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/2/17.
 */
public class StudentCourseListAdapter extends AbsBaseAdapter<CcourselistBean>{
    public StudentCourseListAdapter(Context context) {
        super(context, R.layout.item_my_course_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, CcourselistBean data) {
        viewHolder.setTextView(R.id.tv_type, data.getContractname());
        viewHolder.setTextView(R.id.tv_school, data.getGroupname());
        viewHolder.setTextView(R.id.tv_finish, data.getCoursefinishfcount() + "");
        viewHolder.setTextView(R.id.tv_sum, data.getCoursetotalcount() + "");
        viewHolder.setTextView(R.id.tv_name, data.getCoachname());
    }
}
