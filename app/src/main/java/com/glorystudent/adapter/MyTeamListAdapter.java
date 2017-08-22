package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.entity.TeamManagementEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * Created by Gavin.J on 2017/5/17.
 */

public class MyTeamListAdapter extends AbsBaseAdapter<TeamManagementEntity.MyTeamListBean> {
    public MyTeamListAdapter(Context context) {
        super(context, R.layout.item_team_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, TeamManagementEntity.MyTeamListBean data) {
        viewHolder.setTextView(R.id.tv_team_name, data.getTitle() + "(" + data.getPersonCount() + "人)");
        viewHolder.setTextView(R.id.tv_team_address, data.getRegionText());
        GlideUtil.loadImageView(context, data.getPic(), (ImageView) viewHolder.getView(R.id.iv_team_pic));
        if (data.isIsCaptain()) {
            viewHolder.getView(R.id.tv_team_dui).setVisibility(View.VISIBLE);
        } else {
            viewHolder.getView(R.id.tv_team_dui).setVisibility(View.GONE);
        }
    }
}
