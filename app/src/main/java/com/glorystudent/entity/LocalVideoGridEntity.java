package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/6/22.
 */

public class LocalVideoGridEntity {
    private String label;
    private List<VideoModelEntity> videoDatas;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<VideoModelEntity> getVideoDatas() {
        return videoDatas;
    }

    public void setVideoDatas(List<VideoModelEntity> videoDatas) {
        this.videoDatas = videoDatas;
    }
}
