package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glorystudent.entity.MessageChatEntity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.widget.circleview.CircleImageView;
import com.glorystudent.golflife.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面的适配器
 * Created by hyj on 2017/2/8.
 */
public class MessageRecyclerAdapter extends SwipeMenuAdapter<MessageRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<MessageChatEntity> datas;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public MessageRecyclerAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setUnRead(int position) {
        datas.get(position).setUnReadCount(0);
        notifyItemChanged(position);
    }

    public void setDatas(List<MessageChatEntity> datas){
        this.datas = new ArrayList<>();
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void delete(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }
    public MessageChatEntity getData(int position) {
        return datas.get(position);
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_chat_message_list, null);
        return inflate;
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("adapter", "o适配器机制nBindViewHolder: " + position);
        MessageChatEntity data = datas.get(position);
        GlideUtil.loadCircleImageView(context, data.getCustomerPhoto(), holder.iv_header, R.drawable.icon_chat_golffriend);

        String username = null;
        if (data.getRemarkName()!=null && !data.getRemarkName().isEmpty()) {
            username = data.getRemarkName();
        }else{
            username = data.getName();
        }

        holder.tv_nickname.setText(username);

        holder.tv_message.setText(data.getLastMessage());
        holder.tv_last_time.setText(data.getLastDate());

        if (data.getUnReadCount() > 0) {
            holder.tv_un_read.setVisibility(View.VISIBLE);
            holder.tv_un_read.setText(data.getUnReadCount() + "");
        }else{
            holder.tv_un_read.setVisibility(View.GONE);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
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

    class ViewHolder extends RecyclerView.ViewHolder{
        private View item;
        private CircleImageView iv_header;
        private TextView tv_nickname, tv_message, tv_last_time, tv_un_read;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_header = (CircleImageView) itemView.findViewById(R.id.iv_header);
            tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            tv_last_time = (TextView) itemView.findViewById(R.id.tv_last_time);
            tv_un_read = (TextView) itemView.findViewById(R.id.tv_un_read);
            item = itemView;
        }
    }
    public interface OnRecyclerItemClickListener{
        void onItemClick(int position);
    }
}
