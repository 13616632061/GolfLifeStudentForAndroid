package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.FriendEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/2/13.
 */
public class AddressFriendsListAdapter extends AbsBaseAdapter<FriendEntity> {
    public AddressFriendsListAdapter(Context context) {
        super(context, R.layout.item_address_friends_list);
    }

    @Override
    public void bindView(ViewHolder viewHolder, FriendEntity data) {
        viewHolder.setTextView(R.id.tv_name, data.getName());
    }
}
