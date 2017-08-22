package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.entity.CourtLocationEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/4/18.
 */
public class ChoosePitchListAdapter extends AbsBaseAdapter<CourtLocationEntity.ListCourtBean> {
    private int choosePosition = 0;
    public ChoosePitchListAdapter(Context context) {
        super(context, R.layout.item_pitch_list);
    }

    public void setChooseCourt(int position) {
        choosePosition = position;
        notifyDataSetChanged();
    }

    public int getChooseCourtPosition() {
        return choosePosition;
    }

    @Override
    public void bindView(ViewHolder viewHolder, CourtLocationEntity.ListCourtBean data) {
        ImageView iv_pic = (ImageView) viewHolder.getView(R.id.iv_pic);
        ImageView iv_choose = (ImageView) viewHolder.getView(R.id.iv_choose);
        if (choosePosition == getCurrentPosition()) {
            iv_choose.setVisibility(View.VISIBLE);
        }else {
            iv_choose.setVisibility(View.GONE);
        }
        GlideUtil.loadCircleImageView(context, data.getCourt_picture(), iv_pic, R.drawable.icon_chat_golffriend);
        viewHolder.setTextView(R.id.tv_court_name, data.getCourt_name());
    }
}
