package com.glorystudent.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by hyj on 2017/2/17.
 */
public class PullableViewPager extends ViewPager implements Pullable{
    public PullableViewPager(Context context)
    {
        super(context);
    }

    public PullableViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
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
