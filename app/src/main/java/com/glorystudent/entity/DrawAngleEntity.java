package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Gavin.J on 2017/7/3.
 */

public class DrawAngleEntity extends DrawShapeEntity implements Cloneable {
    public PointF secondPoint;//另一个端点
    private float angleRadius;//角度圆弧半径
    private int sweepAngle;//角度数值
    private PointF referencePoint;//参考点

    public DrawAngleEntity(int color, float lineWidth, float circleRadius) {
        super(color, lineWidth, circleRadius);
        angleRadius = 2 * circleRadius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);
        canvas.drawPath(getPath(), paint);
        //文字大小设为圆圈半径一样
        Paint numPaint = new Paint();
        numPaint.setColor(color);
        numPaint.setTextSize(circleRadius);
        //角度显示在交点的下方
        canvas.drawText(getSweepAngle(), endPoint.x, endPoint.y + 2 * circleRadius, numPaint);
    }

    @Override
    public void onDrawDown(PointF point) {
        this.firstPoint = point;
        //初始化默认第三个点
        calcSecondPoint();
    }

    @Override
    public void transPoint(float dx, float dy) {
        super.transPoint(dx, dy);
        secondPoint.offset(dx, dy);
        referencePoint.offset(dx, dy);
    }

    @Override
    public void zoom(float zoom) {
        super.zoom(zoom);
        secondPoint.x *= zoom;
        secondPoint.y *= zoom;
        referencePoint.x *= zoom;
        referencePoint.y *= zoom;
        angleRadius *= zoom;
    }

    private Path getPath() {
        Path path = new Path();
        path.reset();
        //设置角度两条边路径
        path.moveTo(firstPoint.x, firstPoint.y);
        path.lineTo(endPoint.x, endPoint.y);
        path.lineTo(secondPoint.x, secondPoint.y);

        //角顶点的右侧参考点
        referencePoint = new PointF(endPoint.x + 10, endPoint.y);
        //设置角度的圆弧路径
        RectF rectF = new RectF(endPoint.x - angleRadius, endPoint.y - angleRadius, endPoint.x + angleRadius, endPoint.y + angleRadius);
        //计算起始角度(起点线与x轴的夹角)
        float firstAngle = getAngle(firstPoint, endPoint, referencePoint);
        float startAngle = 0;
        if (firstPoint.y < endPoint.y) {
            startAngle = -firstAngle;
        } else {
            startAngle = firstAngle;
        }
        //计算终点角度(终点线与x轴的夹角)
        float endAngle = getAngle(secondPoint, endPoint, referencePoint);
        float secondAngle = 0;
        if (secondPoint.y < endPoint.y) {
            secondAngle = -endAngle;
        } else {
            secondAngle = endAngle;
        }
        //计算圆弧角度
        if (secondAngle - startAngle > 180) {
            sweepAngle = (int) (secondAngle - startAngle - 360);
        } else if (secondAngle - startAngle < -180) {
            sweepAngle = (int) (secondAngle - startAngle + 360);
        } else {
            sweepAngle = (int) (secondAngle - startAngle);
        }
        path.addArc(rectF, startAngle, sweepAngle);
        if (showCircle) {
            path.addCircle(firstPoint.x, firstPoint.y, circleRadius, Path.Direction.CCW);
            path.addCircle(endPoint.x, endPoint.y, circleRadius, Path.Direction.CCW);
            path.addCircle(secondPoint.x, secondPoint.y, circleRadius, Path.Direction.CCW);
        }
        return path;
    }

    @Override
    public int getPointInShapePointPosition(PointF point) {
        if (ifInPointCircle(firstPoint, point)) {
            return 1;
        } else if (ifInPointCircle(endPoint, point)) {
            return 2;
        } else if (ifInPointCircle(secondPoint, point)) {
            return 3;
        } else {
            return 0;
        }
    }

    @Override
    public boolean ifPointInShape(PointF point) {
        boolean inRectFlag1 = ifInLineRect(firstPoint, endPoint, point);
        boolean inRectFlag2 = ifInLineRect(secondPoint, endPoint, point);
        boolean inCircleFlag1 = ifInPointCircle(firstPoint, point);
        boolean inCircleFlag2 = ifInPointCircle(endPoint, point);
        boolean inCircleFlag3 = ifInPointCircle(secondPoint, point);
        return inRectFlag1 || inRectFlag2 || inCircleFlag1 || inCircleFlag2 || inCircleFlag3;
    }

    /**
     * 初始化第三个点
     */
    private void calcSecondPoint() {
        secondPoint = new PointF(firstPoint.x + 200, firstPoint.y + 200);
    }

    /**
     * 获取角度字符串
     *
     * @return
     */
    public String getSweepAngle() {
        return String.valueOf(Math.abs(sweepAngle));
    }

    /**
     * 根据三个点坐标计算p2点的夹角
     *
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public float getAngle(PointF p1, PointF p2, PointF p3) {
        float a = getDistance(p2, p3);
        float b = getDistance(p1, p3);
        float c = getDistance(p1, p2);
        float cosB = (float) ((c * c + a * a - b * b) / (2.0 * c * a));
        return (float) (Math.acos(cosB) * 180 / Math.PI);
    }

    @Override
    public DrawAngleEntity copy() {
        DrawAngleEntity drawAngleEntity = new DrawAngleEntity(this.color, this.lineWidth, this.circleRadius);
        drawAngleEntity.showCircle = showCircle;
        drawAngleEntity.firstPoint = new PointF(firstPoint.x, firstPoint.y);
        drawAngleEntity.secondPoint = new PointF(secondPoint.x, secondPoint.y);
        drawAngleEntity.referencePoint = new PointF(referencePoint.x, referencePoint.y);
        if (endPoint != null) {
            drawAngleEntity.endPoint = new PointF(endPoint.x, endPoint.y);
        }
        return drawAngleEntity;
    }
}
