package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.ScorecardDetailEntity.ListScorecardDetailBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.widget.oywidget.MyGridView;
import com.glorystudent.golflife.R;

import java.util.List;

/**
 * Created by hyj on 2017/3/22.
 */
public class ScoreDetailListAdapter extends AbsBaseAdapter<List<ListScorecardDetailBean>>{
    public ScoreDetailListAdapter(Context context) {
        super(context, R.layout.item_grid_view_list);
    }
    @Override
    public void bindView(ViewHolder viewHolder, List<ListScorecardDetailBean> data) {
        MyGridView gv = (MyGridView) viewHolder.getView(R.id.score_gv);
        ScoreDetailGirdAdapter scoreDetailGirdAdapter = new ScoreDetailGirdAdapter(context);
        gv.setAdapter(scoreDetailGirdAdapter);
        scoreDetailGirdAdapter.setDatas(data);
    }
}
