package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glorystudent.entity.ContractUserEntity.ListContractuserBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * 合约学员适配器
 * Created by hyj on 2017/1/9.
 */
public class ContractUserListAdapter extends AbsBaseAdapter<ListContractuserBean>{

    public ContractUserListAdapter(Context context) {
        super(context, R.layout.item_student_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListContractuserBean data) {
        ImageView iv = (ImageView) viewHolder.getView(R.id.iv_head_portrait);
        GlideUtil.loadCircleImageView(context, data.getCustomerphoto(), iv, R.drawable.head_default);
        viewHolder.setTextView(R.id.tv_username, data.getUsername());
        TextView tv_state = (TextView) viewHolder.getView(R.id.tv_state);
        LinearLayout ll_progress = (LinearLayout) viewHolder.getView(R.id.ll_progress);
        int status = data.getStatus();
        switch (status) {
            case 8:
                tv_state.setVisibility(View.VISIBLE);
                ll_progress.setVisibility(View.GONE);
                tv_state.setText("审核中");
                break;
            case 0:
                tv_state.setVisibility(View.GONE);
                ll_progress.setVisibility(View.VISIBLE);
                viewHolder.setTextView(R.id.tv_finish, data.getFinishNum() + "");
                viewHolder.setTextView(R.id.tv_sum, data.getTotalNum() + "");
                break;
            case 1:
                tv_state.setText("已过期");
                tv_state.setVisibility(View.VISIBLE);
                ll_progress.setVisibility(View.GONE);
                break;
            case 2:
                tv_state.setText("已完成");
                tv_state.setVisibility(View.VISIBLE);
                ll_progress.setVisibility(View.GONE);
                break;
            case 3:
                tv_state.setText("无效");
                tv_state.setVisibility(View.VISIBLE);
                ll_progress.setVisibility(View.GONE);
                break;
        }
    }
}
