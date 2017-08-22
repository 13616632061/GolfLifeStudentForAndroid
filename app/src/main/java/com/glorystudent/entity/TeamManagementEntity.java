package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/22.
 */

public class TeamManagementEntity {

    /**
     * myTeamList : [{"personCount":3,"isUser":false,"isCaptain":false,"id":1,"logo":"","pic":"","title":"荣耀高尔夫球队123","captainid":null,"regionid":null,"regionText":"深圳市","createdate":null,"summary":null,"createby":null,"telphone":null,"status":null}]
     * teamList : [{"personCount":3,"isUser":false,"isCaptain":false,"id":1,"logo":"","pic":"","title":"荣耀高尔夫球队123","captainid":null,"regionid":null,"regionText":"深圳市","createdate":null,"summary":null,"createby":null,"telphone":null,"status":null},{"personCount":1,"isUser":false,"isCaptain":false,"id":2,"logo":"","pic":"","title":"鹏廷高尔夫球队","captainid":null,"regionid":null,"regionText":"重庆市","createdate":null,"summary":null,"createby":null,"telphone":null,"status":null},{"personCount":0,"isUser":false,"isCaptain":false,"id":3,"logo":"","pic":"","title":"小鹰高尔夫球队","captainid":null,"regionid":null,"regionText":"北京市","createdate":null,"summary":null,"createby":null,"telphone":null,"status":null}]
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
    private List<MyTeamListBean> myTeamList;
    private List<TeamListBean> teamList;

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

    public List<MyTeamListBean> getMyTeamList() {
        return myTeamList;
    }

    public void setMyTeamList(List<MyTeamListBean> myTeamList) {
        this.myTeamList = myTeamList;
    }

    public List<TeamListBean> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamListBean> teamList) {
        this.teamList = teamList;
    }

    public static class MyTeamListBean {
        /**
         * personCount : 3
         * isUser : false
         * isCaptain : false
         * id : 1
         * logo :
         * pic :
         * title : 荣耀高尔夫球队123
         * captainid : null
         * regionid : null
         * regionText : 深圳市
         * createdate : null
         * summary : null
         * createby : null
         * telphone : null
         * status : null
         */

        private int personCount;
        private boolean isUser;
        private boolean isCaptain;
        private int id;
        private String logo;
        private String pic;
        private String title;
        private Object captainid;
        private Object regionid;
        private String regionText;
        private Object createdate;
        private Object summary;
        private Object createby;
        private Object telphone;
        private Object status;

        public int getPersonCount() {
            return personCount;
        }

        public void setPersonCount(int personCount) {
            this.personCount = personCount;
        }

        public boolean isIsUser() {
            return isUser;
        }

        public void setIsUser(boolean isUser) {
            this.isUser = isUser;
        }

        public boolean isIsCaptain() {
            return isCaptain;
        }

        public void setIsCaptain(boolean isCaptain) {
            this.isCaptain = isCaptain;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getCaptainid() {
            return captainid;
        }

        public void setCaptainid(Object captainid) {
            this.captainid = captainid;
        }

        public Object getRegionid() {
            return regionid;
        }

        public void setRegionid(Object regionid) {
            this.regionid = regionid;
        }

        public String getRegionText() {
            return regionText;
        }

        public void setRegionText(String regionText) {
            this.regionText = regionText;
        }

        public Object getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Object createdate) {
            this.createdate = createdate;
        }

        public Object getSummary() {
            return summary;
        }

        public void setSummary(Object summary) {
            this.summary = summary;
        }

        public Object getCreateby() {
            return createby;
        }

        public void setCreateby(Object createby) {
            this.createby = createby;
        }

        public Object getTelphone() {
            return telphone;
        }

        public void setTelphone(Object telphone) {
            this.telphone = telphone;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }

    public static class TeamListBean {
        /**
         * personCount : 3
         * isUser : false
         * isCaptain : false
         * id : 1
         * logo :
         * pic :
         * title : 荣耀高尔夫球队123
         * captainid : null
         * regionid : null
         * regionText : 深圳市
         * createdate : null
         * summary : null
         * createby : null
         * telphone : null
         * status : null
         */

        private int personCount;
        private boolean isUser;
        private boolean isCaptain;
        private int id;
        private String logo;
        private String pic;
        private String title;
        private Object captainid;
        private Object regionid;
        private String regionText;
        private Object createdate;
        private Object summary;
        private Object createby;
        private Object telphone;
        private Object status;

        public int getPersonCount() {
            return personCount;
        }

        public void setPersonCount(int personCount) {
            this.personCount = personCount;
        }

        public boolean isIsUser() {
            return isUser;
        }

        public void setIsUser(boolean isUser) {
            this.isUser = isUser;
        }

        public boolean isIsCaptain() {
            return isCaptain;
        }

        public void setIsCaptain(boolean isCaptain) {
            this.isCaptain = isCaptain;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getCaptainid() {
            return captainid;
        }

        public void setCaptainid(Object captainid) {
            this.captainid = captainid;
        }

        public Object getRegionid() {
            return regionid;
        }

        public void setRegionid(Object regionid) {
            this.regionid = regionid;
        }

        public String getRegionText() {
            return regionText;
        }

        public void setRegionText(String regionText) {
            this.regionText = regionText;
        }

        public Object getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Object createdate) {
            this.createdate = createdate;
        }

        public Object getSummary() {
            return summary;
        }

        public void setSummary(Object summary) {
            this.summary = summary;
        }

        public Object getCreateby() {
            return createby;
        }

        public void setCreateby(Object createby) {
            this.createby = createby;
        }

        public Object getTelphone() {
            return telphone;
        }

        public void setTelphone(Object telphone) {
            this.telphone = telphone;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }
}
