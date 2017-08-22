package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Gavin.J on 2017/6/29.
 */

public abstract class DrawShapeEntity {
    public PointF firstPoint;
    public PointF endPoint;
    public int color;//画笔颜色
    public float lineWidth;//画笔线宽
    public float circleRadius;//端点圆圈半径
    public boolean showCircle;//是否显示端点圆圈即编辑模式
    public DrawType drawType;//类型
    public float scale;//缩放比例
    //一条直线对应的矩形四个点
    private PointF a;
    private PointF b;
    private PointF c;
    private PointF d;

    public DrawShapeEntity(int color, float lineWidth, float circleRadius) {
        this.color = color;
        this.lineWidth = lineWidth;
        this.circleRadius = circleRadius;
    }

    public void onDrawDown(PointF point) {
        this.firstPoint = point;
    }

    public void onDrawMove(PointF point) {
        this.endPoint = point;
    }

    /**
     * 平移点坐标
     *
     * @param dx
     * @param dy
     */
    public void transPoint(float dx, float dy) {
        firstPoint.offset(dx, dy);
        if (endPoint != null) {
            endPoint.offset(dx, dy);
        }
    }

    /**
     * 缩放点坐标和大小
     *
     * @param zoom
     */
    public void zoom(float zoom) {
        firstPoint.x *= zoom;
        firstPoint.y *= zoom;
        if (endPoint != null) {
            endPoint.x *= zoom;
            endPoint.y *= zoom;
        }
        lineWidth *= zoom;
        circleRadius *= zoom;
    }

    /**
     * 手指抬起后将path路径绘制到画布上的方法
     *
     * @param canvas
     */
    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * 判断点是否在图形上
     *
     * @param point
     * @return
     */
    public boolean ifPointInShape(PointF point) {
        return false;
    }

    /**
     * 获取点在图形端点上的位置
     *
     * @param point
     * @return 0=不在端点上，1=firstPoint，2=endPoint，3=secondPoint
     */
    public int getPointInShapePointPosition(PointF point) {
        if (ifInPointCircle(firstPoint, point)) {
            return 1;
        } else if (ifInPointCircle(endPoint, point)) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 计算直线矩形区域四个角点
     *
     * @param firstPoint
     * @param endPoint
     */
    private void calcLineRectPoint(PointF firstPoint, PointF endPoint) {
        //使用向量方法计算,a点和b点的中点是firstPoint,c点和d点的中点是否endPoint,a点和d点同侧，b点和c点同侧
        a = new PointF();
        b = new PointF();
        c = new PointF();
        d = new PointF();

        float x0 = firstPoint.x - endPoint.x;
        float y0 = firstPoint.y - endPoint.y;
        if (y0 != 0) {
            //使用向量解方程后得到
            a.x = (float) (firstPoint.x - Math.sqrt((circleRadius * circleRadius) / (1 + (x0 * x0) / (y0 * y0))));
            a.y = firstPoint.y + x0 * (firstPoint.x - a.x) / y0;
        } else {
            a.x = firstPoint.x;
            a.y = firstPoint.y - circleRadius;
        }
        //使用向量相等算出d点
        d.x = a.x - x0;
        d.y = a.y - y0;
        //使用中点公式算出b点和c点
        b.x = firstPoint.x * 2 - a.x;
        b.y = firstPoint.y * 2 - a.y;

        c.x = endPoint.x * 2 - d.x;
        c.y = endPoint.y * 2 - d.y;
    }

    /**
     * 判断目标点是否在p1,p2所在直线上
     *
     * @param p1
     * @param p2
     * @param target
     * @return
     */
    protected boolean ifInLineRect(PointF p1, PointF p2, PointF target) {
        //计算直线区域矩形四个角点
        calcLineRectPoint(p1, p2);
        //将目标点带入四条边的直线方程中
        float ab = calcLineEquationValue(a, b, target);
        float bc = calcLineEquationValue(b, c, target);
        float cd = calcLineEquationValue(c, d, target);
        float ad = calcLineEquationValue(a, d, target);
        if (ab * cd < 0 && bc * ad < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断目标点是否在端点圆内
     *
     * @param p
     * @param target
     * @return
     */
    protected boolean ifInPointCircle(PointF p, PointF target) {
        if (getDistance(p, target) <= circleRadius) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算目标点在直线方程中的值
     *
     * @param p1
     * @param p2
     * @param target
     * @return
     */
    private float calcLineEquationValue(PointF p1, PointF p2, PointF target) {
        float value = 0;
        if (p1.x != p2.x) {//有斜率
            float k = (p1.y - p2.y) / (p1.x - p2.x);
            value = target.y - k * (target.x - p1.x) - p1.y;
        } else {
            value = target.x - p1.x;
        }
        return value;
    }

    /**
     * 计算两点之间的距离
     *
     * @param a
     * @param b
     * @return
     */
    protected float getDistance(PointF a, PointF b) {
        float x = Math.abs(a.x - b.x);
        float y = Math.abs(a.y - b.y);
        return (float) Math.sqrt(x * x + y * y);
    }

    public abstract DrawShapeEntity copy();
}
