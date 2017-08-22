package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.entity.TeamMemberEntity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin.J on 2017/5/17.
 */

public class TeamMemberRecylerAdapter extends RecyclerView.Adapter<TeamMemberRecylerAdapter.TeamMemberViewHolder> {
    private Context context;
    private List<TeamMemberEntity.TeamUserListBean> datas;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public TeamMemberRecylerAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<TeamMemberEntity.TeamUserListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public TeamMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_team_member_recycler, null);
        return new TeamMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamMemberViewHolder holder, final int position) {
        holder.remarks.setText(datas.get(position).getName());
        GlideUtil.loadCircleImageView(context, datas.get(position).getCustomerphoto(), holder.image, R.drawable.pic_default_avatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class TeamMemberViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView remarks;
        private ImageView image;

        public TeamMemberViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            remarks = (TextView) itemView.findViewById(R.id.tv_member_remarks);
            image = (ImageView) itemView.findViewById(R.id.iv_member_head);
        }
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position);
    }
}
