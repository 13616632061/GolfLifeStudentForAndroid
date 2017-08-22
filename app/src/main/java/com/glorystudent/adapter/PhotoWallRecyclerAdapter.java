package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.glorystudent.entity.LookCertificateEntity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class PhotoWallRecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "PhotoWallAdapter";
    private Context context;
    private List<LookCertificateEntity.ListeventactivityBean.ListeventpicwallBean> datas;

    private OnRecyclerViewItemClickListener onItemClickListener;

    //自定义item的点击事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public PhotoWallRecyclerAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDatas(List<LookCertificateEntity.ListeventactivityBean.ListeventpicwallBean> datas) {
//        this.datas.clear();
//        this.datas.addAll(datas);
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_photo_wall_recycler, null);
        return new PhotoWallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: 加载图片索引" + position);
        final PhotoWallViewHolder viewHolder = (PhotoWallViewHolder) holder;
        //加载缩略图
        String url = datas.get(position).getEventactivity_picpath() + "?x-oss-process=image/resize,w_100";
        GlideUtil.loadImageView(context, url, viewHolder.img);
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, viewHolder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + datas.size());
        return datas.size();
    }

    public class PhotoWallViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public PhotoWallViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_photo_wall);
        }
    }
}
