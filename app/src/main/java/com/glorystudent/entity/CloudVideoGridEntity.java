package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/6/23.
 */

public class CloudVideoGridEntity {
    private String label;
    private List<CloudVideoEntity.ListvideosBean> videoDatas;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<CloudVideoEntity.ListvideosBean> getVideoDatas() {
        return videoDatas;
    }

    public void setVideoDatas(List<CloudVideoEntity.ListvideosBean> videoDatas) {
        this.videoDatas = videoDatas;
    }
}
