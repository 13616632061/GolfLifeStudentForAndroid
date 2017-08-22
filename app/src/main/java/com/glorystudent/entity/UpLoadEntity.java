package com.glorystudent.entity;

import android.graphics.Bitmap;

/**
 * Created by hyj on 2017/1/16.
 */
public class UpLoadEntity {
    private int id;
    private String title;
    private Bitmap bitmap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
