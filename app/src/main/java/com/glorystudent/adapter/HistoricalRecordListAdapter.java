package com.glorystudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.glorystudent.entity.HistoricalRecordEntity;
import com.glorystudent.golflibrary.adapter.AbsBaseAdapter;
import com.glorystudent.golflife.HistoricalRecordActivity;
import com.glorystudent.golflife.R;

/**
 * 历史纪录适配器
 * Created by hyj on 2017/1/6.
 */
public class HistoricalRecordListAdapter extends AbsBaseAdapter<HistoricalRecordEntity.TestsListBean> {

    private int lastPosition = -1;
    private float lastXOffset = 0;
    private float downX =  0;
    private boolean isRight = false;
    private HorizontalScrollView hs_scrollview;
    private View resetView;

    public HistoricalRecordListAdapter(Context context) {
        super(context, R.layout.item_historical_record_list);
    }


    @Override
    public void bindView(final ViewHolder viewHolder, HistoricalRecordEntity.TestsListBean data) {
        viewHolder.setTextView(R.id.tv_test_name, data.getPlayersUserName());
        String test_createtime = data.getTestCreateTime();
        String[] ts = test_createtime.split("T");
        viewHolder.setTextView(R.id.tv_test_date, ts[0]);

        //设置item宽度充满屏幕
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        RelativeLayout layout= (RelativeLayout) viewHolder.getView(R.id.rl_item);
        ViewGroup.LayoutParams linearParams =layout.getLayoutParams();
        linearParams.width=wm.getDefaultDisplay().getWidth();
        layout.setLayoutParams(linearParams);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_historical_record_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        //側拉删除按钮
        hs_scrollview = (HorizontalScrollView) viewHolder.getView(R.id.hs_scrollview);
        hsScroll(convertView,position);

//        currentPosition = position;
        //给不同的控制设置值
        bindView(viewHolder, getDatas(position));
        TextView tv_shop_delete= (TextView) viewHolder.getView(R.id.tv_shop_delete);
        tv_shop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoricalRecordActivity.clickCallBack.deleteClickCallBack(position);
                ((HorizontalScrollView) resetView).fullScroll(View.FOCUS_LEFT);
            }
        });


        return convertView;
    }

    //側拉
    private void hsScroll(final View convertView, final int position){

        hs_scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                final View view = v;
                if (lastPosition!=position&&resetView!=null){
                    ((HorizontalScrollView) resetView).fullScroll(View.FOCUS_LEFT);
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        if (lastPosition != -1 && lastPosition !=position) {
                            View openedItemView = convertView;
                            if (openedItemView != null) {
                                final HorizontalScrollView horizontalScrollView = ((HorizontalScrollView)openedItemView.findViewById(R.id.hs_scrollview));
                                horizontalScrollView.smoothScrollTo(0, 0);
                            }

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getX() > lastXOffset) {
                            isRight = true;
                        } else {
                            isRight = false;
                        }
                        lastXOffset = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float distance = Math.abs(event.getX() - downX);
                        if (distance == 0.0) {
                            if (lastPosition == position) {
                                v.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((HorizontalScrollView)view).fullScroll(View.FOCUS_LEFT);
                                        lastPosition = -1;
                                    }
                                });
                            } else if (lastPosition == -1) {
                                //条目点击事件
//                                Toast.makeText(context, "触发了点击事件"+position, Toast.LENGTH_SHORT).show();
                                HistoricalRecordActivity.clickCallBack.itemClickCallBack(position);
                            } else {
                                lastPosition = -1;
                            }
                        } else if (distance > 0 && distance < dpToPx(40)) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isRight) {
                                        ((HorizontalScrollView) view).fullScroll(View.FOCUS_RIGHT);
                                    } else {
                                        ((HorizontalScrollView)view).fullScroll(View.FOCUS_LEFT);
                                        lastPosition = -1;

                                    }
                                }
                            });
                        } else {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isRight) {
                                        ((HorizontalScrollView) view).fullScroll(View.FOCUS_LEFT);
                                        lastPosition = -1;

                                    } else {
                                        resetView=v;
                                        lastPosition = position;
                                        ((HorizontalScrollView)view).fullScroll(View.FOCUS_RIGHT);
                                    }
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }


    public int dpToPx(int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * ((float) dp)+0.5);
    }
}
