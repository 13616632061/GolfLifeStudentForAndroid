package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2016/12/26.
 */
public class SchoolEntity {

    /**
     * coachgroup : null
     * listcoachgroup : [{"coachgroupid":2,"groupname":"奇瑞","coachid":32,"groupcreatedate":"2016-12-26T16:41:45","groupabout":"     nba哦哦激动房间工商分局随时看房片  nba哦哦激动房间工商分局随时看房片  nba哦哦激动房间工商分局随时看房片  nba哦哦激动房间工商分局随时看房片nba哦哦激动房间工商分局随时看片","contactname":"张三","contracttel":"15366599986","servicetel":"02088888888","grade_service":45,"grade_environment":123,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/golf(698763646).jpg","coachgroupstatus":"2"},{"coachgroupid":3,"groupname":"进易","coachid":4,"groupcreatedate":"2016-09-17T00:00:00","groupabout":"  nba哦哦激动房间工商分局随时看房片","contactname":"李四","contracttel":"16544523365","servicetel":"8669547","grade_service":7,"grade_environment":5,"coachpicture":"https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(4).jpg","coachgroupstatus":"\u0000"},{"coachgroupid":29,"groupname":"3434","coachid":33,"groupcreatedate":"2016-12-23T09:31:16","groupabout":"4243443","contactname":"43434","contracttel":"24224334343","servicetel":"43","grade_service":2432,"grade_environment":43,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"2"},{"coachgroupid":30,"groupname":"656","coachid":33,"groupcreatedate":"2016-12-23T10:23:11","groupabout":"65656","contactname":"6565","contracttel":"5656","servicetel":"656","grade_service":65,"grade_environment":656,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"0"},{"coachgroupid":31,"groupname":"4656565","coachid":33,"groupcreatedate":"2016-12-23T10:23:55","groupabout":"544特特认同","contactname":"严格","contracttel":"18820395654","servicetel":"0316464646","grade_service":54,"grade_environment":316464600,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/golf(698763646).jpg","coachgroupstatus":"1"},{"coachgroupid":32,"groupname":"43","coachid":33,"groupcreatedate":"2016-12-23T10:47:32","groupabout":"433","contactname":"43","contracttel":"18464646666","servicetel":"434","grade_service":43,"grade_environment":434,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"0"},{"coachgroupid":33,"groupname":"4343","coachid":33,"groupcreatedate":"2016-12-23T10:47:59","groupabout":"433","contactname":"43","contracttel":"43443443443","servicetel":"434","grade_service":43,"grade_environment":434,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"2"},{"coachgroupid":34,"groupname":"yange","coachid":33,"groupcreatedate":"2016-12-23T10:48:38","groupabout":"343冯绍峰沙发上冯绍峰安抚是否","contactname":"严格","contracttel":"18820395656","servicetel":"20025161602","grade_service":34,"grade_environment":20025160000,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"0"},{"coachgroupid":35,"groupname":"严格","coachid":33,"groupcreatedate":"2016-12-23T10:49:54","groupabout":"得到是地方撒的是地方撒的","contactname":"严格严格","contracttel":"18820365987","servicetel":"02561664","grade_service":43,"grade_environment":2561664,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/golf(698763646).jpg","coachgroupstatus":"2"},{"coachgroupid":36,"groupname":"燕儿","coachid":33,"groupcreatedate":"2016-12-23T13:34:57","groupabout":"454544545","contactname":"严格","contracttel":"18820365246","servicetel":"02025654556","grade_service":54,"grade_environment":2025655000,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/golf(698763646).jpg","coachgroupstatus":"0"},{"coachgroupid":37,"groupname":"565","coachid":33,"groupcreatedate":"2016-12-23T13:58:38","groupabout":"6565656","contactname":"6565","contracttel":"18851516161","servicetel":"65656","grade_service":656,"grade_environment":65656,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"1"},{"coachgroupid":38,"groupname":"严格","coachid":33,"groupcreatedate":"2016-12-23T14:00:02","groupabout":"发的个人的郭德纲梵蒂冈梵蒂冈发给","contactname":"养鹅","contracttel":"18820664646","servicetel":"20202020202","grade_service":43,"grade_environment":20202020000,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"2"},{"coachgroupid":39,"groupname":"雅阁","coachid":33,"groupcreatedate":"2016-12-23T14:02:22","groupabout":"5454454","contactname":"羊肉","contracttel":"18850336594","servicetel":"18820395651","grade_service":54,"grade_environment":18820400000,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/golf(698763646).jpg","coachgroupstatus":"2"},{"coachgroupid":40,"groupname":"严格","coachid":33,"groupcreatedate":"2016-12-23T14:14:57","groupabout":"是否顺丰到付撒f","contactname":"张鲁","contracttel":"18820395650","servicetel":"02088886666","grade_service":60,"grade_environment":2088887000,"coachpicture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/gaoerfu(997dfd00).jpg","coachgroupstatus":"1"},{"coachgroupid":41,"groupname":"67","coachid":33,"groupcreatedate":"2016-12-26T08:55:38","groupabout":"7676666767","contactname":"76","contracttel":"18820395654","servicetel":"67","grade_service":6,"grade_environment":67,"coachpicture":"https://192.168.26.248:4431/images/headfiles/2016-12-22/glof(52000).jpg","coachgroupstatus":"2"}]
     * version : 1.1.106
     * datetime : 2016/12/26 20:13:02
     * accesstoken : RQitshDwnRTAulf2GXndzf0OJD6F0avJHqACeMJ2Wqr/ugmSg91xs74MymRQZgZTLvjTK67oeUQYjH4MPTmxBGHw7h83NsiuxKICR3wJIgHlONs2jqWX9E6EbZ4V+yfBgqV/9vLe+6LAB7s6BhcdCauF7FWG781OxAy6UTzuweilKmOxHjIt2ZCkNsJlUBkUa7HuchZvwQPSlnpK6NKTHBHBWwH/fU5VMCZIh0yVCGJHd9B8tciOdRxXmd/11X91s0vPzZXE+ZE7HossBnm9wHsZaCbohp1B3lG9+UEoGLFhdz5IaZ4dCEkfqxsv7CKw
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object coachgroup;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListcoachgroupBean> listcoachgroup;

    public Object getCoachgroup() {
        return coachgroup;
    }

    public void setCoachgroup(Object coachgroup) {
        this.coachgroup = coachgroup;
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

    public List<ListcoachgroupBean> getListcoachgroup() {
        return listcoachgroup;
    }

    public void setListcoachgroup(List<ListcoachgroupBean> listcoachgroup) {
        this.listcoachgroup = listcoachgroup;
    }

    public static class ListcoachgroupBean {
        /**
         * coachgroupid : 2
         * groupname : 奇瑞
         * coachid : 32
         * groupcreatedate : 2016-12-26T16:41:45
         * groupabout :      nba哦哦激动房间工商分局随时看房片  nba哦哦激动房间工商分局随时看房片  nba哦哦激动房间工商分局随时看房片  nba哦哦激动房间工商分局随时看房片nba哦哦激动房间工商分局随时看片
         * contactname : 张三
         * contracttel : 15366599986
         * servicetel : 02088888888
         * grade_service : 45
         * grade_environment : 123
         * coachpicture : https://192.168.26.248:4431/images/newsfiles/2016-12-21/golf(698763646).jpg
         * coachgroupstatus : 2
         */

        private String coachgroupid;
        private String groupname;
        private String coachid;
        private String groupcreatedate;
        private String groupabout;
        private String contactname;
        private String contracttel;
        private String servicetel;
        private String grade_service;
        private String grade_environment;
        private String coachpicture;
        private String coachgroupstatus;

        public String getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(String coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getCoachid() {
            return coachid;
        }

        public void setCoachid(String coachid) {
            this.coachid = coachid;
        }

        public String getGroupcreatedate() {
            return groupcreatedate;
        }

        public void setGroupcreatedate(String groupcreatedate) {
            this.groupcreatedate = groupcreatedate;
        }

        public String getGroupabout() {
            return groupabout;
        }

        public void setGroupabout(String groupabout) {
            this.groupabout = groupabout;
        }

        public String getContactname() {
            return contactname;
        }

        public void setContactname(String contactname) {
            this.contactname = contactname;
        }

        public String getContracttel() {
            return contracttel;
        }

        public void setContracttel(String contracttel) {
            this.contracttel = contracttel;
        }

        public String getServicetel() {
            return servicetel;
        }

        public void setServicetel(String servicetel) {
            this.servicetel = servicetel;
        }

        public String getGrade_service() {
            return grade_service;
        }

        public void setGrade_service(String grade_service) {
            this.grade_service = grade_service;
        }

        public String getGrade_environment() {
            return grade_environment;
        }

        public void setGrade_environment(String grade_environment) {
            this.grade_environment = grade_environment;
        }

        public String getCoachpicture() {
            return coachpicture;
        }

        public void setCoachpicture(String coachpicture) {
            this.coachpicture = coachpicture;
        }

        public String getCoachgroupstatus() {
            return coachgroupstatus;
        }

        public void setCoachgroupstatus(String coachgroupstatus) {
            this.coachgroupstatus = coachgroupstatus;
        }
    }
}
