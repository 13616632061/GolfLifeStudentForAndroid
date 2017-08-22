package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.ScorecardDetailEntity.ListScorecardDetailBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/3/22.
 */
public class ScoreDetailGirdAdapter extends AbsBaseAdapter<ListScorecardDetailBean> {

    public ScoreDetailGirdAdapter(Context context) {
        super(context, R.layout.item_score_grade_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ListScorecardDetailBean data) {
        viewHolder.setTextView(R.id.tv_par, data.getPar() + "");
        viewHolder.setTextView(R.id.tv_score, data.getScore() + "");
        viewHolder.setTextView(R.id.tv_putts, data.getPutts() + "");
    }
}
