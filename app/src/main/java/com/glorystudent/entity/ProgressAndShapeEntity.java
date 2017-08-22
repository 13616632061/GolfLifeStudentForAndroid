package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/7/6.
 */

public class ProgressAndShapeEntity {
    private String timeStamp;
    private int progress;
    private List<DrawShapeEntity> shapeEntities;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public List<DrawShapeEntity> getShapeEntities() {
        return shapeEntities;
    }

    public void setShapeEntities(List<DrawShapeEntity> shapeEntities) {
        this.shapeEntities = shapeEntities;
    }
}
