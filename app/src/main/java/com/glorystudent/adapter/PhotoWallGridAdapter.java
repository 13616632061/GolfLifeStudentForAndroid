package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.glorystudent.entity.PhotoWallEntity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/24.
 */

public class PhotoWallGridAdapter extends BaseAdapter {
    private Context context;
    private List<PhotoWallEntity.EventPicWallListBean> datas;

    public PhotoWallGridAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<PhotoWallEntity.EventPicWallListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<PhotoWallEntity.EventPicWallListBean> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    @Override
    public PhotoWallEntity.EventPicWallListBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_photo_wall_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideUtil.loadImageView(context, datas.get(position).getEventactivity_picpath(), viewHolder.photoWall);
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.iv_photo_wall)
        ImageView photoWall;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
