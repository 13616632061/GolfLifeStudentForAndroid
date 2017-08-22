package com.glorystudent.widget.residentview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SpiritLineEntity extends BaseSpirit {
    PointF pointF;
    public SpiritLineEntity(Texture texture, float x, float y) {
        super(texture, x, y);
        spiritType=SpiritType.LINE;
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint) {

        paint.setColor(Color.rgb(227,178,46));
        paint.setStrokeWidth(5);

        if(pointF!=null)
            canvas.drawLine(pointF.x,y,pointF.x,pointF.y,paint);
        else
            canvas.drawLine(x,y,width,height,paint);
    }

    @Override
    protected void moveTo(PointF point) {
        this.pointF=point;
    }
}
