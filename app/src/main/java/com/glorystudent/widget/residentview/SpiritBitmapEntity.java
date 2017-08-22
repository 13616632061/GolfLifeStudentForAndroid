package com.glorystudent.widget.residentview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SpiritBitmapEntity extends BaseSpirit {
    private Bitmap bitmap;
    public SpiritBitmapEntity(Texture texture,float x,float y) {
        super(texture,x,y);
        spiritType=SpiritType.BITMAP;
        Bitmap bitmap= BitmapFactory.decodeResource(texture.getContext().getResources(),resourceId);
        Matrix matrix=new Matrix();
        matrix.postScale(1,texture.getHeight()/bitmap.getHeight());
        this.bitmap= Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(), bitmap.getHeight(),matrix,true);
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap,x,y,paint);
    }

    @Override
    protected void moveTo(PointF point) {

    }




}
