package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/18.
 */
public class UpLoadAliyunEntity {
    private int id;
    private String title;
    private String path;
    private String content;
    private String fileMD5;
    private String state;
    private byte[] picBytes;

    public UpLoadAliyunEntity() {

    }

    public UpLoadAliyunEntity(int id, String title, String path, String content, String fileMD5, byte[] picBytes) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.content = content;
        this.picBytes = picBytes;
        this.fileMD5 = fileMD5;
    }

    public byte[] getPicBytes() {
        return picBytes;
    }

    public void setPicBytes(byte[] picBytes) {
        this.picBytes = picBytes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
