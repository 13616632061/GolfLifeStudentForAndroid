package com.glorystudent.widget.residentview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SpiritCircle extends BaseSpirit {
    private PointF pointFUp,pointFDown;
    private int visible= View.VISIBLE;
    public SpiritCircle(Texture texture, float x, float y) {
        super(texture, x, y);
        spiritType=SpiritType.CIRCEL;
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(255,34,34));
        paint.setAntiAlias(true);

        if(visible==View.VISIBLE){
            canvas.drawCircle(x,y,height,paint);
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(width);
        canvas.drawCircle(x,y,height/2,paint);


        if(pointFUp!=null){
            canvas.drawLine(x,y,pointFUp.x,pointFUp.y,paint);
        }
        if(pointFDown!=null){
            canvas.drawLine(x,y,pointFDown.x,pointFDown.y,paint);
        }
    }

    @Override
    protected void moveTo(PointF point) {
        x=point.x;
        y=point.y;
    }

    @Override
    public boolean ifPointInShape(PointF point) {
        PointF pointStart=new PointF(x-height,y-height);
        PointF pointEnd=new PointF(x+height,y+height);
        if((point.x>=pointStart.x && point.x<=pointEnd.x) &&(point.y>=pointStart.y && point.y<=pointEnd.y)){
            return true;
        }
        return false;
    }
    public void lineTo(PointF pointFUp, PointF pointFDown){
        this.pointFUp=pointFUp;
        this.pointFDown=pointFDown;
    }

    public void setVisible(int visible){
        this.visible=visible;
    }


}
