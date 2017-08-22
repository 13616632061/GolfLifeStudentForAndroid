package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by Gavin.J on 2017/7/3.
 */

public class DrawCircleEntity extends DrawShapeEntity implements Cloneable {

    private PointF centerPoint;//圆心坐标
    private float radius;//半径

    public DrawCircleEntity(int color, float lineWidth, float circleRadius) {
        super(color, lineWidth, circleRadius);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);
        canvas.drawPath(getPath(), paint);
    }

    private Path getPath() {
        Path path = new Path();
        path.reset();
        radius = getDistance(firstPoint, endPoint) / 2;
        centerPoint = getRadiusCoordinate(firstPoint, endPoint);
        path.addCircle(centerPoint.x, centerPoint.y, radius, Path.Direction.CCW);
        if (showCircle) {
            path.addCircle(firstPoint.x, firstPoint.y, circleRadius, Path.Direction.CCW);
            path.addCircle(endPoint.x, endPoint.y, circleRadius, Path.Direction.CCW);
        }
        return path;
    }

    @Override
    public boolean ifPointInShape(PointF point) {
        float distance = getDistance(centerPoint, point);
        if (distance <= radius + circleRadius && distance >= radius - circleRadius) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算圆心坐标
     *
     * @param a
     * @param b
     * @return
     */
    public PointF getRadiusCoordinate(PointF a, PointF b) {
        PointF rp = new PointF();
        rp.x = (a.x + b.x) / 2;
        rp.y = (a.y + b.y) / 2;
        return rp;
    }

    @Override
    public DrawShapeEntity copy() {
        DrawCircleEntity drawCircleEntity = new DrawCircleEntity(this.color, this.lineWidth, this.circleRadius);
        drawCircleEntity.showCircle = showCircle;
        drawCircleEntity.firstPoint = new PointF(firstPoint.x, firstPoint.y);
        if (endPoint != null) {
            drawCircleEntity.endPoint = new PointF(endPoint.x, endPoint.y);
        }
        return drawCircleEntity;
    }
}
