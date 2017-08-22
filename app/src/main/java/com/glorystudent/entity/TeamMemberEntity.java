package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/23.
 */

public class TeamMemberEntity {

    /**
     * teamUserList : [{"telphone":"15502047482","customerphoto":null,"id":null,"userid":1191,"name":"Did","teamid":null,"status":null,"createtime":null},{"telphone":"18727907512","customerphoto":"https://192.168.1.168:4431/images/headfiles/2017-5-8/1494229875634.png","id":null,"userid":1217,"name":"小样","teamid":null,"status":null,"createtime":null}]
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : null
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private Object statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<TeamUserListBean> teamUserList;

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

    public Object getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(Object statusmessage) {
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

    public List<TeamUserListBean> getTeamUserList() {
        return teamUserList;
    }

    public void setTeamUserList(List<TeamUserListBean> teamUserList) {
        this.teamUserList = teamUserList;
    }

    public static class TeamUserListBean {
        /**
         * telphone : 15502047482
         * customerphoto : null
         * id : null
         * userid : 1191
         * name : Did
         * teamid : null
         * status : null
         * createtime : null
         */

        private String telphone;
        private String customerphoto;
        private Object id;
        private int userid;
        private String name;
        private Object teamid;
        private Object status;
        private Object createtime;

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getTeamid() {
            return teamid;
        }

        public void setTeamid(Object teamid) {
            this.teamid = teamid;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }
    }
}
