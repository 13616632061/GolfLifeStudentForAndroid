package com.glorystudent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by hyj on 2017/2/17.
 */
public class PullableNoUpLinearLayout extends LinearLayout implements Pullable{
    public PullableNoUpLinearLayout(Context context) {
        super(context);
    }

    public PullableNoUpLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableNoUpLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
