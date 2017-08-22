package com.glorystudent.entity;

/**
 * Created by hyj on 2017/3/9.
 */
public class FileUpRequestEntity {
    private String filemd5;
    private String filename;
    private Integer partid;
    private Integer filepartcount;
    private String partmd5;
    private Integer filesize;
    private Integer partsize;
    private String partdata;
    private Integer pictype;

    public String getFilemd5() {
        return filemd5;
    }

    public void setFilemd5(String filemd5) {
        this.filemd5 = filemd5;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getPartid() {
        return partid;
    }

    public void setPartid(Integer partid) {
        this.partid = partid;
    }

    public Integer getFilepartcount() {
        return filepartcount;
    }

    public void setFilepartcount(Integer filepartcount) {
        this.filepartcount = filepartcount;
    }

    public String getPartmd5() {
        return partmd5;
    }

    public void setPartmd5(String partmd5) {
        this.partmd5 = partmd5;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Integer getPartsize() {
        return partsize;
    }

    public void setPartsize(Integer partsize) {
        this.partsize = partsize;
    }

    public String getPartdata() {
        return partdata;
    }

    public void setPartdata(String partdata) {
        this.partdata = partdata;
    }

    public Integer getPictype() {
        return pictype;
    }

    public void setPictype(Integer pictype) {
        this.pictype = pictype;
    }
}
