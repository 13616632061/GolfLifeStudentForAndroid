package com.glorystudent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.R;

/**
 * Created by hyj on 2017/2/7.
 */
public class FaceGridAdapter extends AbsBaseAdapter<String> {

    public FaceGridAdapter(Context context) {
        super(context, R.layout.item_face_grid);
    }

    @Override
    public void bindView(ViewHolder viewHolder, String data) {
        TextView tv = (TextView) viewHolder.getView(R.id.tv_face);
        ImageView iv = (ImageView) viewHolder.getView(R.id.iv_delete);
        if (data.equals("-1")) {
            tv.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);
            viewHolder.setTextView(R.id.tv_face, data);
        }
    }
}
