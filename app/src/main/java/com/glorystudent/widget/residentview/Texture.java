package com.glorystudent.widget.residentview;

import android.content.Context;
import android.support.annotation.DrawableRes;

/**
 * Created by Administrator on 2017/7/20.
 */

public class Texture {
    private float width;
    private float height;
    private int resourceId;
    private Context context;

    public Texture(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public Texture(Context context, @DrawableRes int resourceId, float scaleWidth, float scaleHeight) {

        this.resourceId = resourceId;
        this.context=context;
        this.width=scaleWidth;
        this.height=scaleHeight;



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

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Context getContext() {
        return context;
    }
}
