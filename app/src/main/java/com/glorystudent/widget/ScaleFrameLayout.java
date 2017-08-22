package com.glorystudent.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.TextureView;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Gavin.J on 2017/7/26.
 */

public class ScaleFrameLayout extends FrameLayout {
    private static final String TAG = "ScaleFrameLayout";

    private long lastMultiTouchTime;// 记录多点触控缩放后的时间
    private ScaleGestureDetector mScaleGestureDetector;

    private int left;// view的左坐标值
    private int top;// view的上坐标值
    private int right;// view的右坐标值
    private int bottom;// view的下坐标值

    private int mode = 0;// 初始状态,记录是拖拉照片模式还是放大缩小照片模式
    private static final int MODE_DRAG = 1;//拖拉照片模式
    private static final int MODE_ZOOM = 2;//放大缩小照片模式

    private int start_Top = -1, start_Right = -1, start_Left = -1, start_Bottom = -1;
    private int start_x, start_y, current_x, current_y;
    private int currentViewWidth = 0;
    private int currentViewHeight = 0;
    private int initViewWidth = 0;
    private int initViewHeight = 0;
    private int distanceX = 0;
    private int distanceY = 0;
    private boolean isControl_Vertical = false;
    private boolean isControl_Horizal = false;
    private int touchSlop;

    private boolean scaleFlag;
    private float scale;
    private float preScale = 1;// 默认前一次缩放比例为1
    private float translationX;
    private float translationY;
    private ViewDragHelper mDragHelper;

    public ScaleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleFrameLayout(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.getTouchSlop();
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureListener());
        mDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (start_Top == -1) {
            start_Top = top;
            start_Left = left;
            start_Right = right;
            start_Bottom = bottom;
            initViewWidth = getMeasuredWidth();
            initViewHeight = getMeasuredHeight();
            Log.i(TAG, "onLayout: top:" + top + ",left:" + left + ",right:" + right + ",bottom:" + bottom + ",width:" + initViewWidth + ",height:" + initViewHeight);
        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
//        // 由mDragHelper决定是否拦截事件，并传递给onTouchEvent
//        return mDragHelper.shouldInterceptTouchEvent(ev);
//    }

    private boolean needToHandle = true;

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        int pointerCount = event.getPointerCount(); // 获得多少点
//        if (pointerCount > 1) {// 多点触控，
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    needToHandle = false;
//                    break;
//                case MotionEvent.ACTION_MOVE:
//
//                    break;
//                case MotionEvent.ACTION_POINTER_2_UP://第二个手指抬起的时候
//                    needToHandle = true;
//                    break;
//
//                default:
//                    break;
//            }
//            return mScaleGestureDetector.onTouchEvent(event);//让mScaleGestureDetector处理触摸事件
//        } else {
//            long currentTimeMillis = System.currentTimeMillis();
//            if (currentTimeMillis - lastMultiTouchTime > 200 && needToHandle) {
////                  多点触控全部手指抬起后要等待200毫秒才能执行单指触控的操作，避免多点触控后出现颤抖的情况
//                try {
//                    mDragHelper.processTouchEvent(event);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
////            }
//        }
//        return false;
//    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                onPointerDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
            default:
                break;
        }
        return mScaleGestureDetector.onTouchEvent(event);
    }

    /**
     * 滑动事件分发
     *
     * @param event
     */
    private void onTouchMove(MotionEvent event) {
//        int left = 0, top = 0, right = 0, bottom = 0;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastMultiTouchTime > 200 && mode == MODE_DRAG) {
            if (getChildCount() > 0) {
                left = getChildAt(0).getLeft();
                right = getChildAt(0).getRight();
                top = getChildAt(0).getTop();
                bottom = getChildAt(0).getBottom();
            }
//            left = getLeft();
//            right = getRight();
//            top = getTop();
//            bottom = getBottom();
            distanceX = (int) (event.getRawX() - start_x);
            distanceY = (int) (event.getRawY() - start_y);
//            if (icallBack != null) {
//                icallBack.getAngle((int) getX(), this.getWidth());
//            }
            if (touchSlop <= getDistance(distanceX, distanceY)) {
                float dx = distanceX + translationX;
                float dy = distanceY + translationY;
                Log.i(TAG, "onTouchMove: 左右上下：" + left + "," + right + "," + top + "," + bottom);
                Log.i(TAG, "onTouchMove: dx,dy：" + distanceX + "," + distanceY);
                Log.i(TAG, "onTouchMove:getx,gety: " + getX() + "," + getY());
//                if (getX() > 0) {
//                    dx = 0;
//                }
//                if (getY() > 0) {
//                    dy = 0;
//                }
                setTranslation(dx, dy);
//                left = left + distanceX;
//                right = right + distanceX;
//                bottom = bottom + distanceY;
//                top = top + distanceY;
//                Log.i(TAG, "onTouchMove: 单指滑动是否执行：" + isControl_Horizal + "," + isControl_Vertical);
//                // 水平判断
//                if (isControl_Horizal) {
//                    if (left >= 0) {
//                        left = 0;
//                        right = currentViewWidth;
//                    }
//                    if (right <= start_Right) {
//                        right = start_Right;
//                        left = start_Right - currentViewWidth;
//                    }
//                } else {
//                    left = start_Left;
//                    right = start_Right;
//                }
//                // 垂直判断
//                if (isControl_Vertical) {
//                    if (top > 0) {
//                        top = 0;
//                        bottom = currentViewHeight;
//                    }
//                    if (bottom <= start_Bottom) {
//                        bottom = start_Bottom;
//                        top = start_Bottom - currentViewHeight;
//                    }
//                } else {
//                    top = start_Top;
//                    bottom = start_Bottom;
//                }
//                if (isControl_Horizal || isControl_Vertical) {
//                    Log.i(TAG, "onTouchMove: 拖拽移动执行左右上下:" + left + "," + right + "," + top + "," + bottom);
//                    this.setLayout(left, top, right, bottom);
//                }
                current_x = (int) event.getRawX();
                current_y = (int) event.getRawY();
            }
        }
    }

    /**
     * 多点触控时
     *
     * @param event
     */
    private void onPointerDown(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            mode = MODE_ZOOM;
            Log.i(TAG, "onPointerDown: 多点触控：" + mode);
            Log.i(TAG, "onTouchMove:getx,gety: " + getX() + "," + getY());
        }
    }

    /**
     * 按下时的事件
     *
     * @param event
     */

    private void onTouchDown(MotionEvent event) {
        mode = MODE_DRAG;
        start_x = (int) event.getRawX();
        start_y = (int) event.getRawY();
        current_x = (int) event.getRawX();
        current_y = (int) event.getRawY();

        translationX = getTranslationX();
        translationY = getTranslationY();
//        currentViewWidth = getWidth();
//        currentViewHeight = getHeight();
        if (this.getChildCount() > 0) {
            currentViewWidth = getChildAt(0).getWidth();
            currentViewHeight = getChildAt(0).getHeight();
        }
        if (currentViewHeight > initViewHeight) {
            isControl_Vertical = true;
        } else {
            isControl_Vertical = false;
        }
        if (currentViewWidth > initViewWidth) {
            isControl_Horizal = true;
        } else {
            isControl_Horizal = false;
        }
        Log.i(TAG, "onTouchDown: 单指按下:" + mode + ",水平+垂直：" + isControl_Horizal + "," + isControl_Vertical);
        Log.i(TAG, "onTouchMove:getx,gety: " + getX() + "," + getY());
    }

    private void setTranslation(float dx, float dy) {
        Log.i(TAG, "setTranslation: " + dx + ",dy:" + dy);
        this.setTranslationX(dx);
        this.setTranslationY(dy);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 用于判断是否捕获当前child的触摸事件
         *
         * @param child 当前触摸的子view
         * @param pointerId
         * @return true就捕获并解析；false不捕获
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (preScale > 1)
                return true;
            return false;
        }

        /**
         * 控制水平方向上的位置
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            if (left < (initViewWidth - initViewWidth * preScale) / 2)
                left = (int) (initViewWidth - initViewWidth * preScale) / 2;// 限制mainView可向左移动到的位置
            if (left > (initViewWidth * preScale - initViewWidth) / 2)
                left = (int) (initViewWidth * preScale - initViewWidth) / 2;// 限制mainView可向右移动到的位置
            return left;
        }

        public int clampViewPositionVertical(View child, int top, int dy) {

            if (top < (initViewHeight - initViewHeight * preScale) / 2) {
                top = (int) (initViewHeight - initViewHeight * preScale) / 2;// 限制mainView可向上移动到的位置
            }
            if (top > (initViewHeight * preScale - initViewHeight) / 2) {
                top = (int) (initViewHeight * preScale - initViewHeight) / 2;// 限制mainView可向上移动到的位置
            }
            return top;
        }

    };

    /**
     * 实现拖动的处理
     */
    private void setLayout(int left, int top, int right, int bottom) {
//        this.layout(left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(left, top, right, bottom);
            if (view instanceof DrawDesignView && scaleFlag) {
                ((DrawDesignView) view).setScale(scale);
            }
        }
    }

    private void setScale(float scale) {
//        Matrix matrix = this.getMatrix();
//        matrix.postScale(scale,scale,initViewWidth/2,initViewHeight/2);
        this.setScaleX(scale);
        this.setScaleY(scale);
        Log.i(TAG, "setScale: this:" + this.getMeasuredWidth() + "," + this.getMeasuredHeight());
        Log.i(TAG, "setScale: this:" + this.getLeft() + "," + this.getTop());
        Log.i(TAG, "onTouchMove:getx,gety: " + getX() + "," + getY());

        TextureView textureView = (TextureView) getChildAt(0);
//        textureView.setScaleY(scale);
//        textureView.setScaleX(scale);
//        FrameLayout.LayoutParams layoutParams = (LayoutParams) surfaceView.getLayoutParams();
//        layoutParams.height = (int) (this.getHeight() * scale);
//        layoutParams.width = (int) (this.getWidth() * scale);
//        surfaceView.setLayoutParams(layoutParams);
//        int left = -(surfaceView.getWidth() - initViewWidth) / 2;
//        int right = (surfaceView.getWidth() - initViewWidth) / 2;
//        int top = -(surfaceView.getHeight() - initViewHeight) / 2;
//        int bottom = (surfaceView.getHeight() - initViewHeight) / 2;
//        surfaceView.layout(left, right, top, bottom);
//
//        DrawDesignView drawView = (DrawDesignView) getChildAt(1);
//        FrameLayout.LayoutParams lp = (LayoutParams) drawView.getLayoutParams();
//        lp.height = (int) (this.getHeight() * scale);
//        lp.width = (int) (this.getWidth() * scale);
//        drawView.setLayoutParams(lp);

//        drawView.setScaleX(scale);
//        drawView.setScaleY(scale);

        Log.i(TAG, "setScale: TextureView" + textureView.getWidth() + "," + textureView.getHeight());
        Log.i(TAG, "setScale: TextureView" + textureView.getLeft() + "," + textureView.getTop());
        Log.i(TAG, "setScale: TextureView" + textureView.getX() + "," + textureView.getY());
        View drawView = getChildAt(1);
//        drawView.setScaleY(scale);
//        drawView.setScaleX(scale);
        Log.i(TAG, "setScale: drawView" + drawView.getWidth() + "," + drawView.getHeight());
        Log.i(TAG, "setScale: drawView" + drawView.getLeft() + "," + drawView.getTop());
        Log.i(TAG, "setScale: drawView" + drawView.getX() + "," + drawView.getY());
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        for (int i = 0; i < getChildCount(); i++) {
//            View view = getChildAt(i);
//            view.setScaleX(scale);
//            view.setScaleY(scale);
//        }
    }

    private void setAnimation(float scale) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(this, "scaleX", preScale, scale);
        ObjectAnimator animY = ObjectAnimator.ofFloat(this, "scaleY", preScale, scale);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.play(animX).with(animY);
        animationSet.setDuration(10);
        animationSet.start();

        Log.i(TAG, "setAnimation: ScaleFrameLayout：w,h:" + ScaleFrameLayout.this.getWidth() + "," + ScaleFrameLayout.this.getHeight());
        Log.i(TAG, "setAnimation: ScaleFrameLayout：l,t:" + ScaleFrameLayout.this.getLeft() + "," + ScaleFrameLayout.this.getTop());
        Log.i(TAG, "setAnimation: ScaleFrameLayout：x,y:" + ScaleFrameLayout.this.getX() + "," + ScaleFrameLayout.this.getY());
    }

    /**
     * 获取两点的距离
     **/
    private float getDistance(float distanceX, float distanceY) {
        return (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (mode == MODE_ZOOM) {
                scaleFlag = true;
                float previousSpan = detector.getPreviousSpan();// 前一次双指间距
                float currentSpan = detector.getCurrentSpan();// 本次双指间距
                Log.i(TAG, "onScale: 上次+本次：" + previousSpan + "," + currentSpan);
                scale = preScale + (currentSpan - previousSpan) / 1000;
                Log.i(TAG, "onScale: scale:" + scale + ",preScale:" + preScale);
//                int left = 0, right = 0, top = 0, bottom = 0;
                if (scale >= 1 && scale <= 3) {
//                    //正常缩放
//                    float dScale = scale - preScale;
//                    left = (int) (left - dScale * initViewWidth / 2);
//                    right = (int) (right + dScale * initViewWidth / 2);
//                    top = (int) (top - dScale * initViewHeight / 2);
//                    bottom = (int) (bottom + dScale * initViewHeight / 2);
//                    if (left >= 0) {
//                        left = 0;
//                        right = (int) (initViewWidth * scale);
//                    }
//                    if (right <= initViewWidth) {
//                        left = (int) (initViewWidth - initViewWidth * scale);
//                        right = initViewWidth;
//                    }
//                    if (top >= 0) {
//                        top = 0;
//                        bottom = (int) (initViewHeight * scale);
//                    }
//                    if (bottom <= initViewHeight) {
//                        top = (int) (initViewHeight - initViewHeight * scale);
//                        bottom = initViewHeight;
//                    }
//                    setLayout(left, top, right, bottom);
////                    if (newWidth <= (initViewWidth * 3) && newHeight <= (initViewHeight * 3)) {
////                        setScale(scale);
////                    }
//                    setScale(scale);
//                    ViewHelper.setScaleX(ScaleFrameLayout.this, scale);
//                    ViewHelper.setScaleY(ScaleFrameLayout.this, scale);
                    setAnimation(scale);
                } else if (scale < 1) {
//                    //不能缩小
                    scale = 1;
////                    setScale(scale);
//                    setLayout(start_Left, start_Top, start_Right, start_Bottom);
                } else {
                    scale = 3;
                }
            }
            preScale = scale;
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.i(TAG, "onScaleEnd: 开始");
            // 一定要返回true才会进入onScale()这个函数
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.i(TAG, "onScaleEnd: 结束");
//            preScale = scale;// 记录本次缩放比例
            scaleFlag = false;
            lastMultiTouchTime = System.currentTimeMillis();// 记录双指缩放后的时间
        }
    }

    public interface CallBack {
        float getScale();
    }
}
