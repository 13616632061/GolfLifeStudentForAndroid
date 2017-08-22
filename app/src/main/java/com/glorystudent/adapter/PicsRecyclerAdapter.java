package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.glorystudent.entity.EventCompetityEntity;
import com.glorystudent.golflife.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class PicsRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<EventCompetityEntity.ListeventactivityBean.ListeventpicBean> datas;

    public PicsRecyclerAdapter(Context context, List<EventCompetityEntity.ListeventactivityBean.ListeventpicBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicsViewHolder(View.inflate(context, R.layout.item_pics_recycler, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PicsViewHolder viewHolder = (PicsViewHolder) holder;
        Glide.with(context)
                .load(datas.get(position).getEventactivity_picpath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class PicsViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public PicsViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.pics_img);
        }
    }
}
