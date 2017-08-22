package com.glorystudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.LocalVideoGridEntity;
import com.glorystudent.entity.VideoModelEntity;
import com.glorystudent.golflibrary.widget.oywidget.MyGridView;
import com.glorystudent.golflife.VideoAnalyzeActivity;
import com.glorystudent.golflife.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gavin.J on 2017/6/21.
 */

public class LocalVideoMonthAdapter extends BaseAdapter {
    private Context context;
    private List<LocalVideoGridEntity> datas;
    private boolean isChoose;

    public LocalVideoMonthAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<LocalVideoGridEntity> datas) {
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
    public LocalVideoGridEntity getItem(int position) {
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
            convertView = View.inflate(context, R.layout.item_video_month_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LocalVideoGridEntity item = getItem(position);
        viewHolder.tvLabelDate.setText(item.getLabel());

        List<VideoModelEntity> videoDatas = item.getVideoDatas();
        final LocalVideoGridAdapter adapter = new LocalVideoGridAdapter(context);
        viewHolder.gridView.setAdapter(adapter);
        final int parentPosition = position;
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoModelEntity videoModelEntity = datas.get(parentPosition).getVideoDatas().get(position);
                if (isChoose) {
                    //选择模式
                    //选中点击变为未选中
                    videoModelEntity.setSelectFlag(!videoModelEntity.isSelectFlag());
                    adapter.notifyDataSetChanged();
                } else {
                    //正常点击模式
                    Intent intent = new Intent(context, VideoAnalyzeActivity.class);
                    if (!new File(videoModelEntity.getPath()).exists()) {
                        Toast.makeText(context, "视频文件不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent.putExtra("path", videoModelEntity.getPath());
                    if (videoModelEntity.getTitle() != null && !videoModelEntity.getTitle().isEmpty()) {
                        intent.putExtra("title", videoModelEntity.getTitle());
                    }
                    if (videoModelEntity.getContent() != null && !videoModelEntity.getContent().isEmpty()) {
                        intent.putExtra("content", videoModelEntity.getContent());
                    }
                    context.startActivity(intent);
                }
            }
        });
        adapter.setDatas(videoDatas);
        adapter.setIsChoose(isChoose);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_label_date)
        TextView tvLabelDate;
        @Bind(R.id.grid_view)
        MyGridView gridView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
