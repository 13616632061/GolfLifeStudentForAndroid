package com.glorystudent.entity;

/**
 * Created by hyj on 2017/3/7.
 */
public class ExtEntity {
    private String videoMD5;
    private String videoFolderPath;
    private String zipMD5;
    private String zipFolderPath;

    public String getVideoMD5() {
        return videoMD5;
    }

    public void setVideoMD5(String videoMD5) {
        this.videoMD5 = videoMD5;
    }

    public String getVideoFolderPath() {
        return videoFolderPath;
    }

    public void setVideoFolderPath(String videoFolderPath) {
        this.videoFolderPath = videoFolderPath;
    }

    public String getZipMD5() {
        return zipMD5;
    }

    public void setZipMD5(String zipMD5) {
        this.zipMD5 = zipMD5;
    }

    public String getZipFolderPath() {
        return zipFolderPath;
    }

    public void setZipFolderPath(String zipFolderPath) {
        this.zipFolderPath = zipFolderPath;
    }

    @Override
    public String toString() {
        return "ExtEntity{" +
                "videoMD5='" + videoMD5 + '\'' +
                ", videoFolderPath='" + videoFolderPath + '\'' +
                ", zipMD5='" + zipMD5 + '\'' +
                ", zipFolderPath='" + zipFolderPath + '\'' +
                '}';
    }
}
