package com.glorystudent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.glorystudent.entity.TestResultEntity.TestresultBean.TestholeBean;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflibrary.util.DensityUtil;
import com.glorystudent.golflife.R;

/**
 * 评测结果
 * Created by hyj on 2017/1/5.
 */
public class TestResultListAdapter extends AbsBaseAdapter<TestholeBean>{
    public TestResultListAdapter(Context context) {
        super(context, R.layout.item_test_grade_list);
    }
    @Override
    public void bindView(ViewHolder viewHolder, TestholeBean data) {
        Log.d("ce", "bindView: --->" + getCurrentPosition() + " " + sizeCount);
        if (getCurrentPosition() == sizeCount - 1) {
            LinearLayout ll = (LinearLayout) viewHolder.getView(R.id.ll_overall);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();
            layoutParams.setMargins(DensityUtil.dip2px(context, 12), 0, DensityUtil.dip2px(context, 12), DensityUtil.dip2px(context, 10));
            ll.setLayoutParams(layoutParams);
        }else{
            LinearLayout ll = (LinearLayout) viewHolder.getView(R.id.ll_overall);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll.getLayoutParams();
            layoutParams.setMargins(DensityUtil.dip2px(context, 12), 0, DensityUtil.dip2px(context, 12), 0);
            ll.setLayoutParams(layoutParams);
        }
        TextView tv_left_side = (TextView) viewHolder.getView(R.id.tv_left_side);
        if(getCurrentPosition() % 2 == 0){
            tv_left_side.setBackgroundResource(R.drawable.shape_tv_orangetest);
        }else{
            tv_left_side.setBackgroundResource(R.drawable.shape_tv_graytest);
        }
        viewHolder.setTextView(R.id.tv_success_rate, data.getFairwayname());
        TextView tv_percent = (TextView) viewHolder.getView(R.id.tv_percent);
        String fairwayrate = data.getFairwayrate();
        int i = fairwayrate.indexOf("%");
        String percent = "0";
        int p;
        if(i != -1){
            tv_percent.setVisibility(View.VISIBLE);
            percent = fairwayrate.substring(0, fairwayrate.length() - 1);
            viewHolder.setTextView(R.id.tv_percentage_number, percent);
            p = Integer.valueOf(percent);
        }else{
            tv_percent.setVisibility(View.GONE);
            viewHolder.setTextView(R.id.tv_percentage_number, fairwayrate);
            p = Integer.valueOf(fairwayrate);
        }


        String tenrate = data.getTenrate();
        int i1 = tenrate.indexOf("%");
        int tenratepercent;
        if (i1 != -1) {
            tenratepercent = Integer.valueOf(tenrate.substring(0, tenrate.length() - 1));
        }else{
            tenratepercent = Integer.valueOf(tenrate);
        }


        String ninerate = data.getNinerate();
        int i2 = ninerate.indexOf("%");
        int nineratepercent;
        if (i2 != -1) {
            nineratepercent = Integer.valueOf(ninerate.substring(0, ninerate.length() - 1));
        }else{
            nineratepercent = Integer.valueOf(ninerate);
        }

        String eightrate = data.getEightrate();
        int i3 = eightrate.indexOf("%");
        int eightratepercent;
        if (i3 != -1) {
            eightratepercent = Integer.valueOf(eightrate.substring(0, eightrate.length() - 1));
        }else{
            eightratepercent = Integer.valueOf(eightrate);
        }
        TextView hundredview = (TextView) viewHolder.getView(R.id.tv_hundred);
        TextView nineview = (TextView) viewHolder.getView(R.id.tv_nine);
        TextView eightview = (TextView) viewHolder.getView(R.id.tv_eight);

        if(eightratepercent > nineratepercent && nineratepercent > tenratepercent){
            if(p >= eightratepercent){
                eightview.setBackgroundResource(R.drawable.shape_tv_thirtycircle);
            }else{
                eightview.setBackgroundResource(R.color.colorWhite);
            }

            if(p >= nineratepercent && p < eightratepercent){
                nineview.setBackgroundResource(R.drawable.shape_tv_thirtycircle);
            }else{
                nineview.setBackgroundResource(R.color.colorWhite);
            }

            if (p >= tenratepercent && p < nineratepercent) {
                hundredview.setBackgroundResource(R.drawable.shape_tv_thirtycircle);
            }else{
                hundredview.setBackgroundResource(R.color.colorWhite);
            }
        }else{
            if (p >= tenratepercent){
                hundredview.setBackgroundResource(R.drawable.shape_tv_thirtycircle);
            }else{
                hundredview.setBackgroundResource(R.color.colorWhite);
            }

            if(p >= nineratepercent && p < tenratepercent){
                nineview.setBackgroundResource(R.drawable.shape_tv_thirtycircle);
            }else{
                nineview.setBackgroundResource(R.color.colorWhite);
            }

            if(p >= eightratepercent && p < nineratepercent){
                eightview.setBackgroundResource(R.drawable.shape_tv_thirtycircle);
            }else{
                eightview.setBackgroundResource(R.color.colorWhite);
            }
        }
        hundredview.setText(data.getTenrate());
        nineview.setText(data.getNinerate());
        eightview.setText(data.getEightrate());
    }
}
