package com.glorystudent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.entity.ContractTraineeEntity.ListContractUserExtBean;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/4/21.
 */
public class StudentFileListAdapter extends AbsBaseAdapter<ListContractUserExtBean> {
    public StudentFileListAdapter(Context context) {
        super(context, R.layout.item_student_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListContractUserExtBean data) {
        ImageView iv = (ImageView) viewHolder.getView(R.id.iv_head_portrait);
        GlideUtil.loadCircleImageView(context, data.getCustomerphoto(), iv, R.drawable.head_default);
        viewHolder.setTextView(R.id.tv_username, data.getContracttraineename());
        viewHolder.setTextView(R.id.tv_count, data.getPackageNum() + "");
    }
}
