package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin.J on 2017/7/3.
 */

public class DrawCurveEntity extends DrawShapeEntity implements Cloneable {
    private List<PointF> changePoints;

    public DrawCurveEntity(int color, float lineWidth, float circleRadius) {
        super(color, lineWidth, circleRadius);
        changePoints = new ArrayList<>();
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
        path.moveTo(firstPoint.x, firstPoint.y);
        for (PointF changePoint : changePoints) {
            path.lineTo(changePoint.x, changePoint.y);
        }
        return path;
    }

    @Override
    public void transPoint(float dx, float dy) {
        super.transPoint(dx, dy);
        for (PointF changePoint : changePoints) {
            changePoint.offset(dx, dy);
        }
    }

    @Override
    public void zoom(float zoom) {
        super.zoom(zoom);
        for (PointF changePoint : changePoints) {
            changePoint.x *= zoom;
            changePoint.y *= zoom;
        }
    }

    @Override
    public void onDrawMove(PointF point) {
        changePoints.add(point);
    }

    @Override
    public DrawCurveEntity copy() {
        DrawCurveEntity drawCurveEntity = new DrawCurveEntity(this.color, this.lineWidth, this.circleRadius);
        drawCurveEntity.showCircle = this.showCircle;
        drawCurveEntity.firstPoint = new PointF(firstPoint.x, firstPoint.y);
        List<PointF> newChangePoints = new ArrayList<>();
        for (PointF changePoint : changePoints) {
            newChangePoints.add(new PointF(changePoint.x, changePoint.y));
        }
        drawCurveEntity.changePoints = newChangePoints;
        return drawCurveEntity;
    }
}
