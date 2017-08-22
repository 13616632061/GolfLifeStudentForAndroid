package com.glorystudent.golflibrary.widget.oywidget;

import android.content.Context;
import android.util.AttributeSet;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * Created by Gavin.J on 2017/6/9.
 */

public class MySwipeMenuRecyclerView extends SwipeMenuRecyclerView {
    public MySwipeMenuRecyclerView(Context context) {
        super(context);
    }

    public MySwipeMenuRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeMenuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        // TODO 自动生成的方法存根
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
