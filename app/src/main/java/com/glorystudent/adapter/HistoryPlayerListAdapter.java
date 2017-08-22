package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.HistoryPlayerEntity.ListtestuserlogBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * 历史球员测评适配器
 * Created by hyj on 2017/1/4.
 */
public class HistoryPlayerListAdapter extends AbsBaseAdapter<ListtestuserlogBean>{
    public HistoryPlayerListAdapter(Context context) {
        super(context, R.layout.item_history_player_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListtestuserlogBean data) {
        viewHolder.setTextView(R.id.tv_player_name, data.getUsername());
    }
}
