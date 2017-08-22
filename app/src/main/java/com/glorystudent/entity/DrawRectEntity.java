package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Gavin.J on 2017/7/3.
 */

public class DrawRectEntity extends DrawShapeEntity implements Cloneable {
    public DrawRectEntity(int color, float lineWidth, float circleRadius) {
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
        path.addRect(getRect(), Path.Direction.CCW);
        if (showCircle) {
            path.addCircle(firstPoint.x, firstPoint.y, circleRadius, Path.Direction.CCW);
            path.addCircle(endPoint.x, endPoint.y, circleRadius, Path.Direction.CCW);
        }
        return path;
    }

    @Override
    public boolean ifPointInShape(PointF point) {
        PointF secondPoint = new PointF(firstPoint.x, endPoint.y);
        PointF ThirdPoint = new PointF(endPoint.x, firstPoint.y);
        boolean inRectFlag1 = ifInLineRect(firstPoint, secondPoint, point);
        boolean inRectFlag2 = ifInLineRect(firstPoint, ThirdPoint, point);
        boolean inRectFlag3 = ifInLineRect(secondPoint, endPoint, point);
        boolean inRectFlag4 = ifInLineRect(ThirdPoint, endPoint, point);
        boolean inCircleFlag1 = ifInPointCircle(firstPoint, point);
        boolean inCircleFlag2 = ifInPointCircle(endPoint, point);
        boolean inCircleFlag3 = ifInPointCircle(secondPoint, point);
        boolean inCircleFlag4 = ifInPointCircle(ThirdPoint, point);
        return inRectFlag1 || inRectFlag2 || inRectFlag3 || inRectFlag4 ||
                inCircleFlag1 || inCircleFlag2 || inCircleFlag3 || inCircleFlag4;
    }

    /**
     * 判断得到四个方向下的矩形
     *
     * @return
     */
    private RectF getRect() {
        RectF rect = null;
        if ((firstPoint.x <= endPoint.x && firstPoint.y <= endPoint.y)) {
            //右下
            rect = new RectF(firstPoint.x, firstPoint.y, endPoint.x, endPoint.y);
        } else if ((firstPoint.x <= endPoint.x && firstPoint.y >= endPoint.y)) {
            //右上
            rect = new RectF(firstPoint.x, endPoint.y, endPoint.x, firstPoint.y);
        } else if ((firstPoint.x > endPoint.x && firstPoint.y > endPoint.y)) {
            //左上
            rect = new RectF(endPoint.x, endPoint.y, firstPoint.x, firstPoint.y);
        } else if ((firstPoint.x > endPoint.x && firstPoint.y < endPoint.y)) {
            //左下
            rect = new RectF(endPoint.x, firstPoint.y, firstPoint.x, endPoint.y);
        }
        return rect;
    }

    @Override
    public DrawShapeEntity copy() {
        DrawRectEntity drawRectEntity = new DrawRectEntity(this.color, this.lineWidth, this.circleRadius);
        drawRectEntity.showCircle = showCircle;
        drawRectEntity.firstPoint = new PointF(firstPoint.x, firstPoint.y);
        if (endPoint != null) {
            drawRectEntity.endPoint = new PointF(endPoint.x, endPoint.y);
        }
        return drawRectEntity;
    }
}
