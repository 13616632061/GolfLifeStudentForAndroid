package com.glorystudent.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.glorystudent.entity.LocalVideoEntity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gavin.J on 2017/6/16.
 */

public class RecPreviewRecyclerAdapter extends RecyclerView.Adapter<RecPreviewRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<LocalVideoEntity> datas;
    private OnRecyclerViewItemClickListener onRecyclerItemClickListener;

    public RecPreviewRecyclerAdapter(Context context) {
        this.context = context;
        datas = new LinkedList<>();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerViewItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void setDatas(List<LocalVideoEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context, R.layout.item_rec_recycler, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        datas.get(position).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(context).load(bytes).diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideUtil.CornersTransform(context, 10.0f))
                .into(holder.ivPic);
//        holder.ivPic.setImageBitmap(datas.get(position).getBitmap());
        holder.tvDuration.setText(datas.get(position).getTime());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPic;
        private TextView tvDuration;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_rec_pic);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            this.itemView = itemView;
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }
}
