package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.ScoreCardEntity.ListScorecardBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/3/21.
 */
public class ScoreCardListAdapter extends AbsBaseAdapter<ListScorecardBean>{
    public ScoreCardListAdapter(Context context) {
        super(context, R.layout.item_score_card_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListScorecardBean data) {
        viewHolder.setTextView(R.id.tv_username, data.getGolfcoursename());
        viewHolder.setTextView(R.id.tv_pole_number, data.getTotalcount() + "杆");
    }
}
