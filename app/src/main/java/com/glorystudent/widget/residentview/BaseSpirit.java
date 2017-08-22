package com.glorystudent.widget.residentview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Administrator on 2017/7/19.
 */

public abstract class BaseSpirit {
    protected SpiritType spiritType= SpiritType.LINE;//精灵类型
    protected Texture texture;
    protected float x,y;//开始坐标点
    protected float width;//宽度
    protected float height;//高度
    protected Color color;
    protected int resourceId=-1;//资源ID
    public BaseSpirit(Texture texture, float x, float y) {
        this.texture = texture;
        this.resourceId=texture.getResourceId();
        this.x=x;
        this.y=y;
        width=texture.getWidth();
        height=texture.getHeight();
    }

    public BaseSpirit(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected abstract void onDraw(Canvas canvas, Paint paint);
    protected abstract void moveTo(PointF point);


    /**
     * 判断点是否在图形上
     *
     * @param point
     * @return  true yes false no
     */
    public boolean ifPointInShape(PointF point) {
        return false;
    }

    public enum SpiritType{
        LINE,BITMAP,RECT,
        SIDE,FRONT,CIRCEL
    }


    public SpiritType getSpiritType() {
        return spiritType;
    }

    public void setSpiritType(SpiritType spiritType) {
        this.spiritType = spiritType;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


}
