package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/14.
 */
public class ContractCourseEntity {

    /**
     * contract : null
     * listcontract : [{"_UserID":32,"_CoachGroupID":2,"_CoachID":111,"_Status":0,"contractid":1014,"userid":32,"coachgroupid":2,"coachid":111,"contractdate":"2017-02-14T12:52:42","effectivedates":"2017-02-14T12:52:42","expirydate":"2018-02-14T12:52:42","contractprice":30500,"realprice":30500,"freeprice":0,"freetimeprice":false,"effective":true,"status":0,"coachname":"kk","coachpicture":"","contractname":"基础教学","groupname":"广东省深圳市龙岗区正中高尔夫俱乐部"}]
     * listcontractcourse : [{"_ContractID":1014,"_CoachID":111,"ccoursedetailid":1021,"contractid":1014,"coachid":111,"coachname":"kk","free":false,"give":false,"detailmoney":2000,"realmoney":2000,"coachcommision":0,"coachincome":30500,"finish":false,"appointment":false,"ccoursedetailname":"挥杆","remark":null,"coursecode":"1"},{"_ContractID":1014,"_CoachID":111,"ccoursedetailid":1022,"contractid":1014,"coachid":111,"coachname":"kk","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":30500,"finish":false,"appointment":false,"ccoursedetailname":"视频教学","remark":null,"coursecode":"2"},{"_ContractID":1014,"_CoachID":111,"ccoursedetailid":1023,"contractid":1014,"coachid":111,"coachname":"kk","free":false,"give":false,"detailmoney":28000,"realmoney":28000,"coachcommision":0,"coachincome":30500,"finish":false,"appointment":false,"ccoursedetailname":"远程教学","remark":null,"coursecode":"3"},{"_ContractID":1014,"_CoachID":111,"ccoursedetailid":1024,"contractid":1014,"coachid":111,"coachname":"kk","free":false,"give":false,"detailmoney":300,"realmoney":300,"coachcommision":0,"coachincome":30500,"finish":false,"appointment":false,"ccoursedetailname":"比赛教学","remark":null,"coursecode":"4"}]
     * version : 1.1.106
     * datetime : 2017/2/14 13:17:55
     * accesstoken : vJpykCSaib1L2J6Y/jMEyvIbgF3RG5lid1uYTIEFJXZUlFuQ4gvQSwP+eFNbnHvkOIbEe2Zji1G58eJLi7GOYKvFA8NCkjNrEvt3VxFAWwFDv4hRXLbyM95/AWTaGDLJeEgZR4CgsjLGcz8hpeYO5EqwjG1qbsajnOTA4glSrZsqoRH3qE2pgDMmzyasLxFSk+XqOB/2LPLHEy9fZPuAnhab0X4cGGaYqDllBf4FhREV210Ifz47DvMbFu0v92Q8rxHcWPWRS+KpzZvx79Pa928oqwicQCxA6XbLfsiP+4OwuMu5Xci9N2E3oJnPn7OC
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 4
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object contract;
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
    private List<ListcontractcourseBean> listcontractcourse;

    public Object getContract() {
        return contract;
    }

    public void setContract(Object contract) {
        this.contract = contract;
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

    public List<ListcontractcourseBean> getListcontractcourse() {
        return listcontractcourse;
    }

    public void setListcontractcourse(List<ListcontractcourseBean> listcontractcourse) {
        this.listcontractcourse = listcontractcourse;
    }

    public static class ListcontractBean {
        /**
         * _UserID : 32
         * _CoachGroupID : 2
         * _CoachID : 111
         * _Status : 0
         * contractid : 1014
         * userid : 32
         * coachgroupid : 2
         * coachid : 111
         * contractdate : 2017-02-14T12:52:42
         * effectivedates : 2017-02-14T12:52:42
         * expirydate : 2018-02-14T12:52:42
         * contractprice : 30500.0
         * realprice : 30500.0
         * freeprice : 0.0
         * freetimeprice : false
         * effective : true
         * status : 0
         * coachname : kk
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

    public static class ListcontractcourseBean {
        /**
         * _ContractID : 1014
         * _CoachID : 111
         * ccoursedetailid : 1021
         * contractid : 1014
         * coachid : 111
         * coachname : kk
         * free : false
         * give : false
         * detailmoney : 2000.0
         * realmoney : 2000.0
         * coachcommision : 0.0
         * coachincome : 30500.0
         * finish : false
         * appointment : false
         * ccoursedetailname : 挥杆
         * remark : null
         * coursecode : 1
         */

        private int _ContractID;
        private int _CoachID;
        private int ccoursedetailid;
        private int contractid;
        private int coachid;
        private String coachname;
        private boolean free;
        private boolean give;
        private double detailmoney;
        private double realmoney;
        private double coachcommision;
        private double coachincome;
        private boolean finish;
        private boolean appointment;
        private String ccoursedetailname;
        private Object remark;
        private String coursecode;

        public int get_ContractID() {
            return _ContractID;
        }

        public void set_ContractID(int _ContractID) {
            this._ContractID = _ContractID;
        }

        public int get_CoachID() {
            return _CoachID;
        }

        public void set_CoachID(int _CoachID) {
            this._CoachID = _CoachID;
        }

        public int getCcoursedetailid() {
            return ccoursedetailid;
        }

        public void setCcoursedetailid(int ccoursedetailid) {
            this.ccoursedetailid = ccoursedetailid;
        }

        public int getContractid() {
            return contractid;
        }

        public void setContractid(int contractid) {
            this.contractid = contractid;
        }

        public int getCoachid() {
            return coachid;
        }

        public void setCoachid(int coachid) {
            this.coachid = coachid;
        }

        public String getCoachname() {
            return coachname;
        }

        public void setCoachname(String coachname) {
            this.coachname = coachname;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public boolean isGive() {
            return give;
        }

        public void setGive(boolean give) {
            this.give = give;
        }

        public double getDetailmoney() {
            return detailmoney;
        }

        public void setDetailmoney(double detailmoney) {
            this.detailmoney = detailmoney;
        }

        public double getRealmoney() {
            return realmoney;
        }

        public void setRealmoney(double realmoney) {
            this.realmoney = realmoney;
        }

        public double getCoachcommision() {
            return coachcommision;
        }

        public void setCoachcommision(double coachcommision) {
            this.coachcommision = coachcommision;
        }

        public double getCoachincome() {
            return coachincome;
        }

        public void setCoachincome(double coachincome) {
            this.coachincome = coachincome;
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public boolean isAppointment() {
            return appointment;
        }

        public void setAppointment(boolean appointment) {
            this.appointment = appointment;
        }

        public String getCcoursedetailname() {
            return ccoursedetailname;
        }

        public void setCcoursedetailname(String ccoursedetailname) {
            this.ccoursedetailname = ccoursedetailname;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getCoursecode() {
            return coursecode;
        }

        public void setCoursecode(String coursecode) {
            this.coursecode = coursecode;
        }
    }
}
