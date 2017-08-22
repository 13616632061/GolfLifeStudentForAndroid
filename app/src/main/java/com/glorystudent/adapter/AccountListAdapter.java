package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.entity.response.ResponseQueryBlanceListEntity;
import com.glorystudent.golflife.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/17.
 */

public class AccountListAdapter extends SwipeMenuAdapter<AccountListAdapter.ViewHolder> {
    private Context context;
    private List<ResponseQueryBlanceListEntity.UserBanksBean> data;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public AccountListAdapter(Context context,List<ResponseQueryBlanceListEntity.UserBanksBean> data) {
        this.context = context;
        this.data = data;
    }


    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return View.inflate(context, R.layout.item_account_list, null);
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getAccountName()+ "(" + data.get(position).getAccountNum() + ")");
        holder.head.setImageResource(R.drawable.icon_zhifubao);

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
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView name;
        private ImageView head;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_account_name);
            head = (ImageView) itemView.findViewById(R.id.iv_account_head);
            this.itemView = itemView;
        }
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position);
    }
}
