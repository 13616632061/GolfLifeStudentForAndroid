package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.glorystudent.entity.SignDefinitionEntity;
import com.glorystudent.golflife.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class LabelGridAdapter extends BaseAdapter {
    private Context context;
    private List<SignDefinitionEntity.ListsigndefinitionBean> datas;

    public LabelGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    @Override
    public SignDefinitionEntity.ListsigndefinitionBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_released_label_grid, null);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.cb_label);
        checkBox.setText(getItem(position).getSign_describe());
        if (getItem(position).getSign_state() == 0) {
            checkBox.setTextColor(context.getResources().getColor(R.color.colorWhite));
            checkBox.setBackground(context.getResources().getDrawable(R.drawable.shape_cb_label_checked));
        } else {
            checkBox.setTextColor(context.getResources().getColor(R.color.colorGray11));
            checkBox.setBackground(context.getResources().getDrawable(R.drawable.shape_cb_label));
        }
        checkBox.setChecked(getItem(position).getSign_state() == 0 ? true : false);
        if (position != 0 && position != 1) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    datas.get(position).setSign_state(isChecked == true ? 0 : 1);
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    public void setDatas(List<SignDefinitionEntity.ListsigndefinitionBean> labelList) {
        this.datas = labelList;
        notifyDataSetChanged();
    }
}
