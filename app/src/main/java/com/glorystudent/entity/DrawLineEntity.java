package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by Gavin.J on 2017/6/29.
 */

public class DrawLineEntity extends DrawShapeEntity {

    public DrawLineEntity(int color, float lineWidth, float circleRadius) {
        super(color, lineWidth, circleRadius);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);
        canvas.drawPath(getPath(), paint);
    }

    @Override
    public boolean ifPointInShape(PointF point) {
        boolean inRectFlag = ifInLineRect(firstPoint, endPoint, point);
        boolean inCircleFlag1 = ifInPointCircle(firstPoint, point);
        boolean inCircleFlag2 = ifInPointCircle(endPoint, point);
        return inRectFlag || inCircleFlag1 || inCircleFlag2;
    }

    /**
     * 生成图形路径
     *
     * @return
     */
    private Path getPath() {
        Path path = new Path();
        path.reset();
        path.moveTo(firstPoint.x, firstPoint.y);
        path.lineTo(endPoint.x, endPoint.y);
        if (showCircle) {
            path.addCircle(firstPoint.x, firstPoint.y, circleRadius, Path.Direction.CCW);
            path.addCircle(endPoint.x, endPoint.y, circleRadius, Path.Direction.CCW);
        }
        return path;
    }

    @Override
    public DrawLineEntity copy() {
        DrawLineEntity drawLineEntity = new DrawLineEntity(this.color, this.lineWidth, this.circleRadius);
        drawLineEntity.showCircle = this.showCircle;
        drawLineEntity.firstPoint = new PointF(firstPoint.x, firstPoint.y);
        if (endPoint != null) {
            drawLineEntity.endPoint = new PointF(endPoint.x, endPoint.y);
        }
        return drawLineEntity;
    }
}
