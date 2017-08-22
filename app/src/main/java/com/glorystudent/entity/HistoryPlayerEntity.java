package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/1/4.
 */
public class HistoryPlayerEntity {

    /**
     * listtest : null
     * listtestuserlog : [{"tid":2,"username":"1"},{"tid":3,"username":"2"},{"tid":1,"username":"23"},{"tid":1,"username":"3"},{"tid":5,"username":"484"},{"tid":2,"username":"56"},{"tid":1,"username":"565"},{"tid":1,"username":"5965"},{"tid":1,"username":"65"},{"tid":1,"username":"659"},{"tid":1,"username":"88"},{"tid":1,"username":"Dsfdsfsd"},{"tid":1,"username":"Sfddsfsd"},{"tid":1,"username":"Sfsff"},{"tid":1,"username":"Test1"}]
     * version : 1.1.106
     * datetime : 2017/1/4 15:29:07
     * accesstoken : QqmGIuJnU8sblqZV7FG93SRjEvSiPMBCD5Lu0YEIBs8ZAsoN9TFkTVdtGcx6KRQdtC0LxKIBmrCuOD5buhyiYJl82H6CK+/l349FSD2DXWUu8Gy0LLmsLwaDbvp1/2SCY8E1u0Uz7a9bnkQ6c6bwvUEH5IekVuOuNPoWBnFCglL5zIBlGoNC2bfpOUWVt16mEqfhjN2uXDJDOTXDTQeVCiigWOU/RNHhabbnQKLmzerrbgY7ZiIQcRpAXj6gBcbXRDh60D6qtt3KI+4bMj0rXfVAkORMLzlKakqHi9T+I1bzCB2QIykvPODKJWF56v0m
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object listtest;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListtestuserlogBean> listtestuserlog;

    public Object getListtest() {
        return listtest;
    }

    public void setListtest(Object listtest) {
        this.listtest = listtest;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public Object getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(Object totalrownum) {
        this.totalrownum = totalrownum;
    }

    public Object getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(Object totalpagenum) {
        this.totalpagenum = totalpagenum;
    }

    public Object getNowpagenum() {
        return nowpagenum;
    }

    public void setNowpagenum(Object nowpagenum) {
        this.nowpagenum = nowpagenum;
    }

    public Object getPagerownum() {
        return pagerownum;
    }

    public void setPagerownum(Object pagerownum) {
        this.pagerownum = pagerownum;
    }

    public List<ListtestuserlogBean> getListtestuserlog() {
        return listtestuserlog;
    }

    public void setListtestuserlog(List<ListtestuserlogBean> listtestuserlog) {
        this.listtestuserlog = listtestuserlog;
    }

    public static class ListtestuserlogBean {
        /**
         * tid : 2
         * username : 1
         */

        private int tid;
        private String username;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
