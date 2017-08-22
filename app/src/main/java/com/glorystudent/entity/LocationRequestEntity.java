package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/18.
 */
public class LocationRequestEntity {
    private String court_Longitude;
    private String court_Latitude;
    private Integer nowPageNum;
    private Integer pageRowNum;

    public String getCourt_Longitude() {
        return court_Longitude;
    }

    public void setCourt_Longitude(String court_Longitude) {
        this.court_Longitude = court_Longitude;
    }

    public String getCourt_Latitude() {
        return court_Latitude;
    }

    public void setCourt_Latitude(String court_Latitude) {
        this.court_Latitude = court_Latitude;
    }

    public Integer getNowPageNum() {
        return nowPageNum;
    }

    public void setNowPageNum(Integer nowPageNum) {
        this.nowPageNum = nowPageNum;
    }

    public Integer getPageRowNum() {
        return pageRowNum;
    }

    public void setPageRowNum(Integer pageRowNum) {
        this.pageRowNum = pageRowNum;
    }
}
