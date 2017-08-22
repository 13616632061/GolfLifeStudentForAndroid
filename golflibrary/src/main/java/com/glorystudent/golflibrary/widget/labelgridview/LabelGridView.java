package com.glorystudent.golflibrary.widget.labelgridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 便签GridView，实现在scrollview中显示完全
 */

public class LabelGridView extends GridView {
    public LabelGridView(Context context) {
        super(context);
    }

    public LabelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
