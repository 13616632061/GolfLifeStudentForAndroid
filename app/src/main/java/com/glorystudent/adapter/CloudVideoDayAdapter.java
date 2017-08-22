package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glorystudent.entity.CloudVideoEntity;
import com.glorystudent.entity.CloudVideoGridEntity;
import com.glorystudent.golflibrary.widget.oywidget.MyGridView;
import com.glorystudent.golflife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gavin.J on 2017/6/21.
 */

public class CloudVideoDayAdapter extends BaseAdapter {
    private Context context;
    private List<CloudVideoGridEntity> datas;
    private boolean isChoose;

    public CloudVideoDayAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<CloudVideoGridEntity> datas) {
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
    public CloudVideoGridEntity getItem(int position) {
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
        CloudVideoGridEntity item = getItem(position);
        viewHolder.tvLabelDate.setText(item.getLabel());

        List<CloudVideoEntity.ListvideosBean> videoDatas = item.getVideoDatas();
        final CloudVideoGridAdapter adapter = new CloudVideoGridAdapter(context);
        viewHolder.gridView.setAdapter(adapter);
        final int parentPosition = position;
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isChoose) {
                    //选择模式
                    CloudVideoEntity.ListvideosBean listvideosBean = datas.get(parentPosition).getVideoDatas().get(position);
                    //选中点击变为未选中
                    listvideosBean.setSelectFlag(!listvideosBean.isSelectFlag());
                    adapter.notifyDataSetChanged();
                } else {

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
