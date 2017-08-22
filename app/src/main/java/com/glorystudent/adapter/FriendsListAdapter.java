package com.glorystudent.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.glorystudent.entity.FriendEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/2/13.
 */
public class FriendsListAdapter extends AbsBaseAdapter<FriendEntity>{
    public FriendsListAdapter(Context context) {
        super(context, R.layout.item_add_friends_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, FriendEntity data) {
        ImageView iv_header = (ImageView) viewHolder.getView(R.id.iv_header);
        GlideUtil.loadCircleImageView(context, data.getCustomerphoto(), iv_header, R.drawable.icon_chat_golffriend);
        viewHolder.setTextView(R.id.tv_name, data.getName());
    }
}
