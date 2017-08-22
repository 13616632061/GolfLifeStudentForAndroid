package com.glorystudent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.glorystudent.entity.TeamAlbumDetailEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * Created by Gavin.J on 2017/5/18.
 */

public class TeamAlbumDetailGridAdapter extends AbsBaseAdapter<TeamAlbumDetailEntity.PhotolistBean> {

    public TeamAlbumDetailGridAdapter(Context context) {
        super(context, R.layout.item_team_album_photo_grid);
    }

    @Override
    public void bindView(ViewHolder viewHolder, TeamAlbumDetailEntity.PhotolistBean data) {
        GlideUtil.loadImageView(context, data.getEventactivity_picpath(), (ImageView) viewHolder.getView(R.id.iv_album_photo));
    }
}
