package com.glorystudent.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.entity.VideoModelEntity;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gavin.J on 2017/6/21.
 */

public class LocalVideoGridAdapter extends BaseAdapter {
    private Context context;
    private List<VideoModelEntity> datas;
    private boolean isChoose;

    public LocalVideoGridAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<VideoModelEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public VideoModelEntity getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_video_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        VideoModelEntity item = getItem(position);
        byte[] picBytes = item.getPicBytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
        viewHolder.ivVideoPic.setImageBitmap(bitmap);
        viewHolder.tvDuration.setText(item.getDuration());

        if (isChoose) {
            viewHolder.cbChoose.setVisibility(View.VISIBLE);
            viewHolder.cbChoose.setChecked(item.isSelectFlag());
        } else {
            viewHolder.cbChoose.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_video_pic)
        ImageView ivVideoPic;
        @Bind(R.id.tv_duration)
        TextView tvDuration;
        @Bind(R.id.cb_choose)
        CheckBox cbChoose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
