package com.glorystudent.golflibrary.widget.oywidget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/24.
 */
public class CustomViewPager extends ViewPager {
        @IntDef({RESUME, PAUSE, DESTROY})
        @Retention(RetentionPolicy.SOURCE)
        public @interface LifeCycle {
        }

        public static final int RESUME = 0;
        public static final int PAUSE = 1;
        public static final int DESTROY = 2;
        /**
         * 生命周期状态，保证{@link #mCarouselTimer}在各生命周期选择执行策略
         */
        private int mLifeCycle = RESUME;
        /**
         * 是否正在触摸状态，用以防止触摸滑动和自动轮播冲突
         */
        private boolean mIsTouching = false;
        /**
         * 轮播定时器
         */
        private ScheduledExecutorService mCarouselTimer;

        public CustomViewPager(Context context) {
            super(context);
        }

        public CustomViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setLifeCycle(@LifeCycle int lifeCycle) {
            this.mLifeCycle = lifeCycle;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    mIsTouching = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mIsTouching = false;
                    break;
            }
            return super.onTouchEvent(ev);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            shutdownTimer();
            mCarouselTimer = Executors.newSingleThreadScheduledExecutor();
            mCarouselTimer.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    switch (mLifeCycle) {
                        case RESUME:
                            if (!mIsTouching
                                    && getAdapter() != null
                                    && getAdapter().getCount() > 1) {
                                post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setCurrentItem(getCurrentItem() + 1);
                                    }
                                });
                            }
                            break;
                        case PAUSE:
                            break;
                        case DESTROY:
                            shutdownTimer();
                            break;
                    }
                }
            }, 1000 * 2, 1000 * 2, TimeUnit.MILLISECONDS);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            shutdownTimer();
        }

        private void shutdownTimer() {
            if (mCarouselTimer != null && mCarouselTimer.isShutdown() == false) {
                mCarouselTimer.shutdown();
            }
            mCarouselTimer = null;
        }
}
