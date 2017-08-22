package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/2/21.
 */
public class ContractEntity {

    /**
     * listcontract : [{"_UserID":111,"_CoachGroupID":2,"_CoachID":80,"_Status":0,"contractid":1027,"userid":111,"coachgroupid":2,"coachid":80,"contractdate":"2017-02-10T17:15:35","effectivedates":"2017-02-10T17:15:35","expirydate":"2018-02-10T17:15:35","contractprice":500,"realprice":500,"freeprice":0,"freetimeprice":false,"effective":true,"status":0,"coachname":"何跃进","coachpicture":"","contractname":"基础教学","groupname":"广东省深圳市龙岗区正中高尔夫俱乐部"},{"_UserID":111,"_CoachGroupID":2,"_CoachID":84,"_Status":0,"contractid":1029,"userid":111,"coachgroupid":2,"coachid":84,"contractdate":"2017-02-17T11:00:19","effectivedates":"2017-02-17T11:00:19","expirydate":"2018-02-17T11:00:19","contractprice":500,"realprice":500,"freeprice":0,"freetimeprice":false,"effective":true,"status":0,"coachname":"何跃进","coachpicture":"","contractname":"基础教学","groupname":"广东省深圳市龙岗区正中高尔夫俱乐部"}]
     * ccoursedetaillist : [{"contractid":1027,"coursetotalcount":3,"coursefinishfcount":0,"addressid":218},{"contractid":1029,"coursetotalcount":3,"coursefinishfcount":0,"addressid":218}]
     * addressid : null
     * version : 1.0.3
     * datetime : 2017/2/21 18:57:02
     * accesstoken : QwmMbG0f58T2ikXFq1NfU9eAqXDi6zK8UO/2KCRMyT4vZAQbVI2gYto4U2aK2V67XoNePnwjFvfBaw79zZENRYzKk1zdGVo1en0ilOXibMKhYlU1k0OSUoZYzogydW9UZQ+mo9e5arzfzrDvsnLUjHuod0EQNcNrx9Zq9NLWFL4zvys9TuclMK/s5oWefgAmYE9PMx34dfLfVsql/BBu0TBXZnqprWqVHXSaDDc65CJ1Hds1prQBx3D34Jh0GpPPzKSyYWf+BgpvcNY4a8OuMkUJcyHt4bL1tQC7VPXv1KHX5VjBeXbSjJe3QTB1LHKG
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 2
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object addressid;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListcontractBean> listcontract;
    private List<CcoursedetaillistBean> ccoursedetaillist;

    public Object getAddressid() {
        return addressid;
    }

    public void setAddressid(Object addressid) {
        this.addressid = addressid;
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

    public String getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(String totalrownum) {
        this.totalrownum = totalrownum;
    }

    public String getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(String totalpagenum) {
        this.totalpagenum = totalpagenum;
    }

    public Object getNowpagenum() {
        return nowpagenum;
    }

    public void setNowpagenum(Object nowpagenum) {
        this.nowpagenum = nowpagenum;
    }

    public String getPagerownum() {
        return pagerownum;
    }

    public void setPagerownum(String pagerownum) {
        this.pagerownum = pagerownum;
    }

    public List<ListcontractBean> getListcontract() {
        return listcontract;
    }

    public void setListcontract(List<ListcontractBean> listcontract) {
        this.listcontract = listcontract;
    }

    public List<CcoursedetaillistBean> getCcoursedetaillist() {
        return ccoursedetaillist;
    }

    public void setCcoursedetaillist(List<CcoursedetaillistBean> ccoursedetaillist) {
        this.ccoursedetaillist = ccoursedetaillist;
    }

    public static class ListcontractBean implements Serializable{
        /**
         * _UserID : 111
         * _CoachGroupID : 2
         * _CoachID : 80
         * _Status : 0
         * contractid : 1027
         * userid : 111
         * coachgroupid : 2
         * coachid : 80
         * contractdate : 2017-02-10T17:15:35
         * effectivedates : 2017-02-10T17:15:35
         * expirydate : 2018-02-10T17:15:35
         * contractprice : 500.0
         * realprice : 500.0
         * freeprice : 0.0
         * freetimeprice : false
         * effective : true
         * status : 0
         * coachname : 何跃进
         * coachpicture :
         * contractname : 基础教学
         * groupname : 广东省深圳市龙岗区正中高尔夫俱乐部
         */

        private int _UserID;
        private int _CoachGroupID;
        private int _CoachID;
        private int _Status;
        private int contractid;
        private int userid;
        private int coachgroupid;
        private int coachid;
        private String contractdate;
        private String effectivedates;
        private String expirydate;
        private double contractprice;
        private double realprice;
        private double freeprice;
        private boolean freetimeprice;
        private boolean effective;
        private int status;
        private String coachname;
        private String coachpicture;
        private String contractname;
        private String groupname;
        private CcoursedetaillistBean coursedetail;

        public CcoursedetaillistBean getCoursedetail() {
            return coursedetail;
        }

        public void setCoursedetail(CcoursedetaillistBean coursedetail) {
            this.coursedetail = coursedetail;
        }

        public int get_UserID() {
            return _UserID;
        }

        public void set_UserID(int _UserID) {
            this._UserID = _UserID;
        }

        public int get_CoachGroupID() {
            return _CoachGroupID;
        }

        public void set_CoachGroupID(int _CoachGroupID) {
            this._CoachGroupID = _CoachGroupID;
        }

        public int get_CoachID() {
            return _CoachID;
        }

        public void set_CoachID(int _CoachID) {
            this._CoachID = _CoachID;
        }

        public int get_Status() {
            return _Status;
        }

        public void set_Status(int _Status) {
            this._Status = _Status;
        }

        public int getContractid() {
            return contractid;
        }

        public void setContractid(int contractid) {
            this.contractid = contractid;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(int coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public int getCoachid() {
            return coachid;
        }

        public void setCoachid(int coachid) {
            this.coachid = coachid;
        }

        public String getContractdate() {
            return contractdate;
        }

        public void setContractdate(String contractdate) {
            this.contractdate = contractdate;
        }

        public String getEffectivedates() {
            return effectivedates;
        }

        public void setEffectivedates(String effectivedates) {
            this.effectivedates = effectivedates;
        }

        public String getExpirydate() {
            return expirydate;
        }

        public void setExpirydate(String expirydate) {
            this.expirydate = expirydate;
        }

        public double getContractprice() {
            return contractprice;
        }

        public void setContractprice(double contractprice) {
            this.contractprice = contractprice;
        }

        public double getRealprice() {
            return realprice;
        }

        public void setRealprice(double realprice) {
            this.realprice = realprice;
        }

        public double getFreeprice() {
            return freeprice;
        }

        public void setFreeprice(double freeprice) {
            this.freeprice = freeprice;
        }

        public boolean isFreetimeprice() {
            return freetimeprice;
        }

        public void setFreetimeprice(boolean freetimeprice) {
            this.freetimeprice = freetimeprice;
        }

        public boolean isEffective() {
            return effective;
        }

        public void setEffective(boolean effective) {
            this.effective = effective;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCoachname() {
            return coachname;
        }

        public void setCoachname(String coachname) {
            this.coachname = coachname;
        }

        public String getCoachpicture() {
            return coachpicture;
        }

        public void setCoachpicture(String coachpicture) {
            this.coachpicture = coachpicture;
        }

        public String getContractname() {
            return contractname;
        }

        public void setContractname(String contractname) {
            this.contractname = contractname;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }
    }

    public static class CcoursedetaillistBean implements Serializable{
        /**
         * contractid : 1027
         * coursetotalcount : 3
         * coursefinishfcount : 0
         * addressid : 218
         */

        private int contractid;
        private int coursetotalcount;
        private int coursefinishfcount;
        private int addressid;

        public int getContractid() {
            return contractid;
        }

        public void setContractid(int contractid) {
            this.contractid = contractid;
        }

        public int getCoursetotalcount() {
            return coursetotalcount;
        }

        public void setCoursetotalcount(int coursetotalcount) {
            this.coursetotalcount = coursetotalcount;
        }

        public int getCoursefinishfcount() {
            return coursefinishfcount;
        }

        public void setCoursefinishfcount(int coursefinishfcount) {
            this.coursefinishfcount = coursefinishfcount;
        }

        public int getAddressid() {
            return addressid;
        }

        public void setAddressid(int addressid) {
            this.addressid = addressid;
        }
    }
}
