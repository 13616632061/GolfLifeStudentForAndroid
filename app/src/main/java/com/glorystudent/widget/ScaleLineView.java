package com.glorystudent.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

/**
 * Created by Gavin.J on 2017/7/6.
 */

public class ScaleLineView extends View {

    private Scroller mScroller;
    private float mDensity;
    private int mMinVelocity;
    private int mWidth;
    private int mHeight;
    public static final int MAX_TYPE_HALF = 10;
    public static final int MID_TYPE_HALF = 5;

    private static final int ITEM_HALF_DIVIDER = 5;

    private static final int ITEM_MAX_HEIGHT = 42;
    private static final int ITEM_MID_HEIGHT = 28;
    private static final int ITEM_MIN_HEIGHT = 14;
    private int mValue = 50000, mMaxValue = 150000, mLineDivider = ITEM_HALF_DIVIDER;
    private int mLastX, mMove;
    private VelocityTracker mVelocityTracker;
    private OnValueChangeListener onValueChangeListener;

    public ScaleLineView(Context context) {
        super(context);
        init();
    }

    public ScaleLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mDensity = getContext().getResources().getDisplayMetrics().density;

        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    public void setValueChangeListener(OnValueChangeListener listener) {
        onValueChangeListener = listener;
    }

    /**
     * 获取当前刻度值
     *
     * @return
     */
    public float getValue() {
        return mValue;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //从中间往两边画刻度线
        drawScaleLine(canvas);
    }

    /**
     * 从中间往两边开始画刻度线
     *
     * @param canvas
     */
    private void drawScaleLine(Canvas canvas) {
        canvas.save();

        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setColor(Color.BLACK);

        int width = mWidth, drawCount = 0;
        float xPosition = 0;
        for (int i = 0; drawCount <= 4 * width; i++) {
            int numSize = String.valueOf(mValue + i).length();
            //往右画
            xPosition = (width / 2 - mMove) + i * mLineDivider * mDensity;
            if (xPosition + getPaddingRight() < mWidth) {
                if ((mValue + i) % MAX_TYPE_HALF == 0) {
                    linePaint.setColor(Color.RED);
                    canvas.drawLine(xPosition, getPaddingTop() + mHeight - mDensity * ITEM_MAX_HEIGHT, xPosition, getPaddingTop() + mHeight, linePaint);
                } else if ((mValue + i) % MID_TYPE_HALF == 0 && (mValue + i) % MAX_TYPE_HALF != 0) {
                    linePaint.setColor(Color.WHITE);
                    canvas.drawLine(xPosition, getPaddingTop() + mHeight - mDensity * ITEM_MID_HEIGHT, xPosition, getPaddingTop() + mHeight, linePaint);
                } else {
                    linePaint.setColor(Color.WHITE);
                    canvas.drawLine(xPosition, getPaddingTop() + mHeight - mDensity * ITEM_MIN_HEIGHT, xPosition, getPaddingTop() + mHeight, linePaint);
                }
            }
            //往左画
            xPosition = (width / 2 - mMove) - i * mLineDivider * mDensity;
            if (xPosition > getPaddingLeft()) {
                if ((mValue - i) % MAX_TYPE_HALF == 0) {
                    linePaint.setColor(Color.RED);
                    canvas.drawLine(xPosition, getPaddingTop() + mHeight - mDensity * ITEM_MAX_HEIGHT, xPosition, getPaddingTop() + mHeight, linePaint);
                } else if ((mValue - i) % MID_TYPE_HALF == 0 && (mValue - i) % MAX_TYPE_HALF != 0) {
                    linePaint.setColor(Color.WHITE);
                    canvas.drawLine(xPosition, getPaddingTop() + mHeight - mDensity * ITEM_MID_HEIGHT, xPosition, getPaddingTop() + mHeight, linePaint);
                } else {
                    linePaint.setColor(Color.WHITE);
                    canvas.drawLine(xPosition, getPaddingTop() + mHeight - mDensity * ITEM_MIN_HEIGHT, xPosition, getPaddingTop() + mHeight, linePaint);
                }
            }
            drawCount += 2 * mLineDivider * mDensity;
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int xPosition = (int) event.getX();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mScroller.forceFinished(true);

                mLastX = xPosition;
                mMove = 0;
                touchDown();
                break;
            case MotionEvent.ACTION_MOVE:
                mMove += (mLastX - xPosition);
                touchMove();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchEnd();
                countVelocityTracker(event);
                return false;
//            break;
            default:
                break;
        }
        mLastX = xPosition;
        return true;
    }

    /**
     *
     */
    private void touchDown() {
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueDown(mValue);
        }
    }

    private void touchMove() {
        int tValue = (int) (mMove / (mLineDivider * mDensity));
        if (Math.abs(tValue) > 0) {
            mValue += tValue;
            mMove -= tValue * mLineDivider * mDensity;
            if (mValue <= 0 || mValue > mMaxValue) {
                mValue = mValue <= 0 ? 0 : mMaxValue;
                mMove = 0;
                mScroller.forceFinished(true);
            }
            if (null != onValueChangeListener) {
                onValueChangeListener.onValueMove(mValue);
            }
        }
        postInvalidate();
    }

    private void touchEnd() {
        int roundMove = Math.round(mMove / (mLineDivider * mDensity));
        mValue = mValue + roundMove;
        mValue = mValue <= 0 ? 0 : mValue;
        mValue = mValue > mMaxValue ? mMaxValue : mValue;

        mLastX = 0;
        mMove = 0;
        if (null != onValueChangeListener) {
            onValueChangeListener.onValueUp(mValue);
        }
        postInvalidate();
    }

    private void countVelocityTracker(MotionEvent event) {
        mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity) > mMinVelocity) {
            mScroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mScroller.getCurrX() == mScroller.getFinalX()) { // over
                touchEnd();
            } else {
                int xPosition = mScroller.getCurrX();
                mMove += (mLastX - xPosition);
                touchMove();
                mLastX = xPosition;
            }
        }
    }

    public interface OnValueChangeListener {
        void onValueMove(int value);

        void onValueDown(int value);

        void onValueUp(int value);
    }
}
