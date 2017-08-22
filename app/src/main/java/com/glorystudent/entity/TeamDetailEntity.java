package com.glorystudent.entity;

/**
 * Created by Gavin.J on 2017/5/23.
 */

public class TeamDetailEntity {

    /**
     * teamDetail : {"captainName":"Did","personCount":1,"isUser":false,"isCaptain":false,"id":2,"logo":"","pic":"","title":"鹏廷高尔夫球队","captainid":1191,"regionid":500000,"regionText":"重庆市","createdate":"2016-03-01T00:00:00","summary":"鹏廷高尔夫球队test","createby":82,"telphone":"13711111111","status":0}
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

    private TeamDetailBean teamDetail;
    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;

    public TeamDetailBean getTeamDetail() {
        return teamDetail;
    }

    public void setTeamDetail(TeamDetailBean teamDetail) {
        this.teamDetail = teamDetail;
    }

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

    public static class TeamDetailBean {
        /**
         * captainName : Did
         * personCount : 1
         * isUser : false
         * isCaptain : false
         * id : 2
         * logo :
         * pic :
         * title : 鹏廷高尔夫球队
         * captainid : 1191
         * regionid : 500000
         * regionText : 重庆市
         * createdate : 2016-03-01T00:00:00
         * summary : 鹏廷高尔夫球队test
         * createby : 82
         * telphone : 13711111111
         * status : 0
         */

        private String captainName;
        private int personCount;
        private boolean isUser;
        private boolean isCaptain;
        private int id;
        private String logo;
        private String pic;
        private String title;
        private int captainid;
        private int regionid;
        private String regionText;
        private String createdate;
        private String summary;
        private int createby;
        private String telphone;
        private int status;

        public String getCaptainName() {
            return captainName;
        }

        public void setCaptainName(String captainName) {
            this.captainName = captainName;
        }

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

        public int getCaptainid() {
            return captainid;
        }

        public void setCaptainid(int captainid) {
            this.captainid = captainid;
        }

        public int getRegionid() {
            return regionid;
        }

        public void setRegionid(int regionid) {
            this.regionid = regionid;
        }

        public String getRegionText() {
            return regionText;
        }

        public void setRegionText(String regionText) {
            this.regionText = regionText;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getCreateby() {
            return createby;
        }

        public void setCreateby(int createby) {
            this.createby = createby;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
