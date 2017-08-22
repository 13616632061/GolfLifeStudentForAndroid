package com.glorystudent.adapter;

import android.content.Context;

import com.glorystudent.entity.NewsEntity.ListnewsBean;
import com.glorystudent.golflibrary.adapter.AbsMoreBaseAdapter;
import com.glorystudent.golflife.R;
import com.glorystudent.util.TimeUtil;

/**
 * Created by hyj on 2016/12/20.
 */
public class HomeListAdapter extends AbsMoreBaseAdapter<ListnewsBean> {

    public HomeListAdapter(Context context) {
        super(context, R.layout.item_home_list1, R.layout.item_home_list2, R.layout.item_home_list3, R.layout.item_home_list4);
    }

    @Override
    public void bindDatas(AbsMoreBaseAdapter<ListnewsBean>.ViewHolder viewHolder, ListnewsBean datas, int typeView) {
        switch (typeView) {
            case 0:
                //0图片
                //设置文字
                viewHolder.bindTextView(R.id.home_lv_title, datas.getNews_title());
                viewHolder.bindTextView(R.id.home_lv_source, datas.getNews_promulgator());
                viewHolder.bindTextView(R.id.home_lv_time, TimeUtil.getTime(datas.getNews_datetime()));
                String[] imgs = datas.getNews_picture().split(",");
                //设置图片
                if (imgs.length == 3) {
                    viewHolder.bindImageView(R.id.home_lv_iv1, imgs[0], R.drawable.pic_placeholder);
                    viewHolder.bindImageView(R.id.home_lv_iv2, imgs[1], R.drawable.pic_placeholder);
                    viewHolder.bindImageView(R.id.home_lv_iv3, imgs[2], R.drawable.pic_placeholder);
                }
                break;
            case 1:
                //1图文
                //设置文字
                viewHolder.bindTextView(R.id.home_lv_title, datas.getNews_title());
                viewHolder.bindTextView(R.id.home_lv_source, datas.getNews_promulgator());
                viewHolder.bindTextView(R.id.home_lv_time, TimeUtil.getTime(datas.getNews_datetime()));
                //设置图片
                viewHolder.bindImageView(R.id.home_lv_iv, datas.getNews_picture(), R.drawable.pic_placeholder);
                break;
            case 2:
                //2视频(暂不开放)

                break;
            case 3:
                //3全文字
                //设置文字
                viewHolder.bindTextView(R.id.home_lv_title, datas.getNews_title());
                viewHolder.bindTextView(R.id.home_lv_source, datas.getNews_promulgator());
                viewHolder.bindTextView(R.id.home_lv_time, TimeUtil.getTime(datas.getNews_datetime()));
                viewHolder.bindTextView(R.id.home_lv_tvcontent, datas.getNews_subtitle());
                break;
        }
    }
}
