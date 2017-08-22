package com.glorystudent.entity;

/**
 * Created by Gavin.J on 2017/6/20.
 */

public class VideoModelEntity {
    private int id;
    private String title;
    private String content;
    private byte[] picBytes;
    private String date;
    private String path;
    private String type;
    private String duration;
    private String fileMd5;
    private boolean selectFlag;
    private boolean expandFlag;

    public VideoModelEntity() {
    }

    public VideoModelEntity(int id, String title, byte[] picBytes, String content, String date, String path, String type, String duration, String fileMd5) {
        this.id = id;
        this.title = title;
        this.picBytes = picBytes;
        this.content = content;
        this.date = date;
        this.path = path;
        this.type = type;
        this.duration = duration;
        this.fileMd5 = fileMd5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getPicBytes() {
        return picBytes;
    }

    public void setPicBytes(byte[] picBytes) {
        this.picBytes = picBytes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public boolean isSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(boolean selectFlag) {
        this.selectFlag = selectFlag;
    }

    public boolean isExpandFlag() {
        return expandFlag;
    }

    public void setExpandFlag(boolean expandFlag) {
        this.expandFlag = expandFlag;
    }
}
