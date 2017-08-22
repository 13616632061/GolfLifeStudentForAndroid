package com.glorystudent.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.glorystudent.entity.EventCompetityEntity;
import com.glorystudent.golflibrary.adapter.AbsMoreBaseAdapter;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflife.R;
import com.glorystudent.util.TimeUtil;

import java.text.DecimalFormat;

/**
 * 赛事适配器
 * Created by hyj on 2016/12/21.
 */
public class EventListAdapter extends AbsMoreBaseAdapter<EventCompetityEntity.ListeventactivityBean> {

    private DecimalFormat df;

    public EventListAdapter(Context context) {
        super(context, R.layout.item_event_list1, R.layout.item_event_list2);
        df = new DecimalFormat("0.00");
    }

    @Override
    public void bindDatas(ViewHolder viewHolder, EventCompetityEntity.ListeventactivityBean datas, int typeView) {
        switch (typeView) {
            case 0:
                initValue0(viewHolder, datas);
                break;
            case 1:
                initValue1(viewHolder, datas);
                break;
            default:
                break;
        }
    }

    /**
     * 多张赛事图片的布局
     *
     * @param viewHolder
     * @param datas
     */
    private void initValue1(ViewHolder viewHolder, EventCompetityEntity.ListeventactivityBean datas) {
        initPublicValue(viewHolder, datas);
        //设置活动的图片
        RecyclerView recyclerView = (RecyclerView) viewHolder.getView(R.id.rv_compentition_pics);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new PicsRecyclerAdapter(context, datas.getListeventpic()));
    }

    /**
     * 一张赛事图片的布局
     *
     * @param viewHolder
     * @param datas
     */
    private void initValue0(ViewHolder viewHolder, EventCompetityEntity.ListeventactivityBean datas) {
        initPublicValue(viewHolder, datas);
        //设置赛事活动图片
        ImageView picView = (ImageView) viewHolder.getView(R.id.iv_competition_image);
        GlideUtil.loadImageView(context, datas.getListeventpic().get(0).getEventactivity_picpath(), picView);
    }

    private void initPublicValue(ViewHolder viewHolder, EventCompetityEntity.ListeventactivityBean datas) {
        //设置活动发布人用户名
        viewHolder.bindTextView(R.id.tv_competition_user_name, datas.getEventactivity_publishername());
        //设置是否免费及金额
        if (datas.getEventactivity_cost() == 0) {
            viewHolder.getView(R.id.tv_competition_cost_free).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.tv_competition_cost).setVisibility(View.GONE);
        } else {
            viewHolder.getView(R.id.tv_competition_cost_free).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_competition_cost).setVisibility(View.VISIBLE);
            viewHolder.bindTextView(R.id.tv_competition_cost, "￥" + df.format(datas.getEventactivity_cost()));
        }
        //设置活动名称
        viewHolder.bindTextView(R.id.tv_competition_name, datas.getEventactivity_name());
        //设置活动时间
        viewHolder.bindTextView(R.id.tv_competition_date, TimeUtil.getCompetitionTime(datas.getEventactivity_begintime()));
        //设置活动地址
        viewHolder.bindTextView(R.id.tv_competition_address, datas.getEventactivity_address());
        //设置报名人数
        viewHolder.bindTextView(R.id.tv_competition_sign_up_count, "" + datas.getSignUpNumber());
//        viewHolder.setTextView(R.id.tv_competition_sign_up_all_Count, "" + data.getEventactivity_number());
        //设置活动发布人logo
        ImageView imageView = (ImageView) viewHolder.getView(R.id.iv_competition_user_portrait);
        GlideUtil.loadCircleImageView(context, datas.getEventactivity_publisherlogo(), imageView, R.drawable.pic_default_avatar);

    }
}
