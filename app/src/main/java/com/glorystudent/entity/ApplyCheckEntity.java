package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/24.
 */

public class ApplyCheckEntity {

    /**
     * applyTeamUserList : [{"customerphoto":"https://192.168.1.168:4431/images/headfiles/2017-5-9/20170509105354.png","id":14,"teamid":18,"name":"111","sex":"F","telphone":"13800138000","handicap":222,"remarks":null,"createby":32,"createtime":"2017-05-24T19:24:07","status":0}]
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ApplyTeamUserListBean> applyTeamUserList;

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getDatetime() {
        return datetime;
    }

    public void setDatetime(Object datetime) {
        this.datetime = datetime;
    }

    public Object getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(Object accesstoken) {
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

    public List<ApplyTeamUserListBean> getApplyTeamUserList() {
        return applyTeamUserList;
    }

    public void setApplyTeamUserList(List<ApplyTeamUserListBean> applyTeamUserList) {
        this.applyTeamUserList = applyTeamUserList;
    }

    public static class ApplyTeamUserListBean {
        /**
         * customerphoto : https://192.168.1.168:4431/images/headfiles/2017-5-9/20170509105354.png
         * id : 14
         * teamid : 18
         * name : 111
         * sex : F
         * telphone : 13800138000
         * handicap : 222
         * remarks : null
         * createby : 32
         * createtime : 2017-05-24T19:24:07
         * status : 0
         */

        private String customerphoto;
        private int id;
        private int teamid;
        private String name;
        private String sex;
        private String telphone;
        private double handicap;
        private String remarks;
        private int createby;
        private String createtime;
        private int status;

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTeamid() {
            return teamid;
        }

        public void setTeamid(int teamid) {
            this.teamid = teamid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public double getHandicap() {
            return handicap;
        }

        public void setHandicap(double handicap) {
            this.handicap = handicap;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getCreateby() {
            return createby;
        }

        public void setCreateby(int createby) {
            this.createby = createby;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
