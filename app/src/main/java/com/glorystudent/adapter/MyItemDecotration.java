package com.glorystudent.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ken on 2016/10/13.10:05
 * 自定义一个分割线
 */
public class MyItemDecotration extends RecyclerView.ItemDecoration {

    private int[] ATTR = {android.R.attr.listDivider};

    /**
     * 分割线的资源
     */
    private Drawable drawable;

    public MyItemDecotration(Context context){
        TypedArray typedArray = context.obtainStyledAttributes(ATTR);
        drawable = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    /**
     * 分割线的绘制
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            if(i == parent.getChildCount() - 1){
                return;
            }

            View itemview = parent.getChildAt(i);
            int left = itemview.getLeft();
            int top = itemview.getBottom();
            int rigth = itemview.getRight();
            int bottom = itemview.getBottom() + drawable.getIntrinsicHeight();
            drawable.setBounds(left, top, rigth, bottom);
            drawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
    }
}
