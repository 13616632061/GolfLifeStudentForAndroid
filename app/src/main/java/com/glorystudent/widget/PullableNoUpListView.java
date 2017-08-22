package com.glorystudent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableNoUpListView extends ListView implements Pullable {

    public PullableNoUpListView(Context context) {
        super(context);
    }

    public PullableNoUpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableNoUpListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        try {
            if (getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (getFirstVisiblePosition() == 0
                    && getChildAt(0).getTop() >= 0) {
                // 滑到ListView的顶部了
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
