package com.glorystudent.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.glorystudent.entity.DrawAngleEntity;
import com.glorystudent.entity.DrawArrowEntity;
import com.glorystudent.entity.DrawCircleEntity;
import com.glorystudent.entity.DrawCurveEntity;
import com.glorystudent.entity.DrawLineEntity;
import com.glorystudent.entity.DrawRectEntity;
import com.glorystudent.entity.DrawShapeEntity;
import com.glorystudent.entity.DrawType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin.J on 2017/6/29.
 */

public class DrawDesignView extends View {
    private DrawType drawType = DrawType.line;
    private DrawType saveDrawType = DrawType.line;
    private int color = Color.YELLOW;//当前线 画笔的颜色
    private int saveColor = Color.YELLOW;//编辑时保存之前的颜色
    private Paint mPaint;//线画笔
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private List<DrawShapeEntity> shapeEntities;//存储画线的集合
    private List<DrawShapeEntity> currentShapeEntities;//当前时间点的画线集合，包括正在画的线
    private boolean drawFlag;//是否是画图模式
    private OnShowWipeLineListener onShowWipeLineListener;//删除与撤销的回调
    private PointF firstPoint;//按下的点
    private PointF changePoint;//移动中的点
    private boolean moveFlag = false;//移动距离的判断
    private boolean moveOneFlag = true;//控制move中只执行一次的标记
    private int selectPointPosition;//图形上的端点的位置，0=不在端点上，1=firstPoint，2=endPoint，3=secondPoint
    private DrawShapeEntity selectShapeEntity;//点击选中的画线图形
    private DrawShapeEntity drawShapeEntity;//创建的画线对象
    private float mDensity;//屏幕分辨率
    private int width;
    private int height;

    public DrawDesignView(Context context) {
        super(context);
        init();
    }

    public DrawDesignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawDesignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDensity = getContext().getResources().getDisplayMetrics().density;
        Log.i("drawDesignView", "init: " + mDensity);

        shapeEntities = new ArrayList<>();
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(color);//初始颜色黄色
        mPaint.setStrokeWidth((float) (2.5 * mDensity));//线宽2.5dp
    }

    /**
     * 初始化画布
     */
    private void initCanvas() {
        //画布大小
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        if (mCanvas == null) {
            mCanvas = new Canvas(mBitmap);  //所有mCanvas画的东西都被保存在了mBitmap中
        } else {
            mCanvas.setBitmap(mBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将前面已经画过得显示出来
        if (width != 0 && height != 0) {
            initCanvas();
            for (DrawShapeEntity shapeEntity : shapeEntities) {
                shapeEntity.draw(mCanvas, mPaint);
            }
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawFlag) {
            float x = event.getX();
            float y = event.getY();
            changePoint = new PointF(x, y);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    firstPoint = new PointF(x, y);
                    //判断是否需要创建对象
                    ifCreateShapeData();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //手指移动的方法
                    touchMove();
                    break;
                case MotionEvent.ACTION_UP:
                    //手指抬起的方法
                    touchUp();
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    /**
     * 手指移动的方法
     */
    private void touchMove() {
        //判断是否移动过
        if (moveOneFlag && getDistance(firstPoint, changePoint) > 10) {
            if (drawShapeEntity != null) {
                shapeEntities.add(drawShapeEntity);
            }
            if (selectShapeEntity != null) {
                //没有按下端点，则表示重绘，即重新设置起点终点
                if (selectPointPosition == 0) {
                    selectShapeEntity.onDrawDown(firstPoint);
                }
            }
            moveFlag = true;
            moveOneFlag = false;
        }
        if (moveFlag) {
            if (selectShapeEntity == null) {
                //正常画线，非编辑时
                drawShapeEntity.onDrawMove(changePoint);
            } else {
                if (selectPointPosition == 0) {
                    //编辑时的重绘
                    selectShapeEntity.onDrawMove(changePoint);
                } else if (selectPointPosition == 1) {
                    //编辑时拖动的是第一个点
                    selectShapeEntity.firstPoint = changePoint;
                } else if (selectPointPosition == 2) {
                    //编辑时拖动的是第终点个点(在角度模式下表示角点)
                    selectShapeEntity.endPoint = changePoint;
                } else if (selectPointPosition == 3) {
                    //在角度模式下表示第二个端点
                    DrawAngleEntity selectDrawAngleEntity = (DrawAngleEntity) selectShapeEntity;
                    selectDrawAngleEntity.secondPoint = changePoint;
                }
            }
            invalidate();
        }
    }

    /**
     * 手指抬起的方法
     */
    private void touchUp() {
        if (moveFlag) {//移动过了
            if (drawShapeEntity != null && drawShapeEntity.drawType == DrawType.angle) {
                //创建画线时如果是角度，则改为编辑模式
                selectShapeEntity = drawShapeEntity;
            }
            //通知显示
            if (onShowWipeLineListener != null) {
                onShowWipeLineListener.showWipeLine(shapeEntities.size());
            }
        } else {//没有移动操作
            //先将之前的编辑状态取消
            if (selectShapeEntity != null) {
                selectShapeEntity.showCircle = false;
            }
            //再次判断编辑状态，即判断点击的点是否在画线图形上。
            selectShapeEntity = getEntityPointInShape(firstPoint);
            if (selectShapeEntity != null) {
                selectShapeEntity.showCircle = true;
            }
        }
        //重置移动状态
        drawShapeEntity = null;
        moveFlag = false;
        moveOneFlag = true;
        invalidate();
    }

    /**
     * 判断是否需要创建好对象
     */
    private void ifCreateShapeData() {
        if (selectShapeEntity == null) {
            //创建画线，正常画线状态
            drawType = saveDrawType;
            color = saveColor;
            switch (drawType) {
                case line://直线
                    drawShapeEntity = new DrawLineEntity(color, mDensity * 2.5f, mDensity * 16);
                    drawShapeEntity.drawType = DrawType.line;
                    break;
                case angle://角度
                    drawShapeEntity = new DrawAngleEntity(color, mDensity * 2.5f, mDensity * 16);
                    drawShapeEntity.drawType = DrawType.angle;
                    //默认为编辑模式
                    drawShapeEntity.showCircle = true;
                    break;
                case circle://圆
                    drawShapeEntity = new DrawCircleEntity(color, mDensity * 2.5f, mDensity * 16);
                    drawShapeEntity.drawType = DrawType.circle;
                    break;
                case curve://曲线
                    drawShapeEntity = new DrawCurveEntity(color, mDensity * 2.5f, mDensity * 16);
                    drawShapeEntity.drawType = DrawType.curve;
                    break;
                case rect://矩形
                    drawShapeEntity = new DrawRectEntity(color, mDensity * 2.5f, mDensity * 16);
                    drawShapeEntity.drawType = DrawType.rect;
                    break;
                case arrow://箭头
                    drawShapeEntity = new DrawArrowEntity(color, mDensity * 2.5f, mDensity * 16);
                    drawShapeEntity.drawType = DrawType.arrow;
                    break;
            }
            drawShapeEntity.onDrawDown(firstPoint);
        } else {
            //编辑状态
            //先取出画线图形的类型、颜色和路径
            drawType = selectShapeEntity.drawType;
            color = selectShapeEntity.color;
//          获取按下点在画线图形上的端点位置，0=不在端点上，1=firstPoint，2=endPoint，3=secondPoint
            selectPointPosition = selectShapeEntity.getPointInShapePointPosition(firstPoint);
        }
    }

    /**
     * 获取点在画线图形上的对象
     *
     * @param point
     */
    private DrawShapeEntity getEntityPointInShape(PointF point) {
        for (int i = 0; i < shapeEntities.size(); i++) {
            DrawShapeEntity shapeEntity = shapeEntities.get(i);
            if (shapeEntity.ifPointInShape(point)) {
                //保存编辑画线图形在集合中的索引
                return shapeEntity;
            }
        }
        return null;
    }

    /**
     * 计算两点之间的距离
     *
     * @param a
     * @param b
     * @return
     */
    private float getDistance(PointF a, PointF b) {
        float x = Math.abs(a.x - b.x);
        float y = Math.abs(a.y - b.y);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        this.saveColor = color;
        mPaint.setColor(color);
    }

    /**
     * 设置画笔形状
     *
     * @param drawType
     */
    public void setDrawType(DrawType drawType) {
        //清空当前的编辑状态
        if (selectShapeEntity != null) {
            selectShapeEntity.showCircle = false;
            selectShapeEntity = null;
        }
        invalidate();
        this.drawType = drawType;
        this.saveDrawType = drawType;
    }

    /**
     * 设置是否是画图模式
     *
     * @param drawFlag
     */
    public void setDrawFlag(boolean drawFlag) {
        this.drawFlag = drawFlag;
    }

    /**
     * 移除所有画线
     */
    public void removeAll() {
        shapeEntities.clear();
        reDrawOnBitmap();
    }

    /**
     * 撤销上一次画线
     */
    public void revocation() {
        shapeEntities.remove(shapeEntities.size() - 1);
        reDrawOnBitmap();
    }

    /**
     * 重新绘制
     */
    private void reDrawOnBitmap() {
        //重置当前编辑状态
        if (selectShapeEntity != null) {
            selectShapeEntity.showCircle = false;
            selectShapeEntity = null;
        }
        invalidate();
        if (onShowWipeLineListener != null) {
            onShowWipeLineListener.showWipeLine(shapeEntities.size());
        }
    }

    public void setScale(float scale) {
        for (DrawShapeEntity shapeEntity : shapeEntities) {
            shapeEntity.scale = scale;
        }
        invalidate();
    }

    /**
     * 获取当前的图形集合，包括正在画的
     *
     * @return
     */
    public List<DrawShapeEntity> getShapeList() {
        currentShapeEntities = new ArrayList<>();
        for (DrawShapeEntity shapeEntity : shapeEntities) {
            currentShapeEntities.add(shapeEntity.copy());
        }
        return currentShapeEntities;
    }

    public void setOnShowWipeLineListener(OnShowWipeLineListener onShowWipeLineListener) {
        this.onShowWipeLineListener = onShowWipeLineListener;
    }

    /**
     * 是否显示清除画线按钮接口
     */
    public interface OnShowWipeLineListener {
        void showWipeLine(int size);
    }
}
