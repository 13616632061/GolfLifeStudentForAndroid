package com.glorystudent.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by Gavin.J on 2017/7/3.
 */

public class DrawArrowEntity extends DrawShapeEntity implements Cloneable {
    private double H = 30; // 箭头高度
    private double L = 20; // 底边的一半
    private double arrowAngle = Math.atan(L / H); // 箭头角度的一半
    private double arrowLen = Math.sqrt(L * L + H * H); // 箭头的长度
    private PointF p1, p2;

    public DrawArrowEntity(int color, float lineWidth, float circleRadius) {
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
        path.moveTo(firstPoint.x, firstPoint.y);
        path.lineTo(endPoint.x, endPoint.y);
        //计算箭头两个端点坐标
        calcArrowPoint(firstPoint.x, firstPoint.y, endPoint.x, endPoint.y);
        path.moveTo(p1.x, p1.y);
        path.lineTo(endPoint.x, endPoint.y);
        path.lineTo(p2.x, p2.y);
        if (showCircle) {
            path.addCircle(firstPoint.x, firstPoint.y, circleRadius, Path.Direction.CCW);
            path.addCircle(endPoint.x, endPoint.y, circleRadius, Path.Direction.CCW);
        }
        return path;
    }

    @Override
    public void zoom(float zoom) {
        super.zoom(zoom);
        arrowLen *= zoom;
    }

    @Override
    public boolean ifPointInShape(PointF point) {
        boolean inRectFlag = ifInLineRect(firstPoint, endPoint, point);
        boolean inCircleFlag1 = ifInPointCircle(firstPoint, point);
        boolean inCircleFlag2 = ifInPointCircle(endPoint, point);
        return inRectFlag || inCircleFlag1 || inCircleFlag2;
    }

    /**
     * 画箭头
     *
     * @param sx
     * @param sy
     * @param ex
     * @param ey
     */
    public void calcArrowPoint(float sx, float sy, float ex, float ey) {
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, arrowAngle, true, arrowLen);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -arrowAngle, true, arrowLen);
        float x1 = (float) (ex - arrXY_1[0]); // (x1,y1)是第一端点
        float y1 = (float) (ey - arrXY_1[1]);
        float x2 = (float) (ex - arrXY_2[0]); // (x2,y2)是第二端点
        float y2 = (float) (ey - arrXY_2[1]);
        p1 = new PointF(x1, y1);
        p2 = new PointF(x2, y2);
    }

    // 计算
    public double[] rotateVec(float px, float py, double ang, boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }

    @Override
    public DrawShapeEntity copy() {
        DrawArrowEntity drawArrowEntity = new DrawArrowEntity(this.color, this.lineWidth, this.circleRadius);
        drawArrowEntity.showCircle = this.showCircle;
        drawArrowEntity.firstPoint = new PointF(firstPoint.x, firstPoint.y);
        if (endPoint != null) {
            drawArrowEntity.endPoint = new PointF(endPoint.x, endPoint.y);
        }
        return drawArrowEntity;
    }
}
