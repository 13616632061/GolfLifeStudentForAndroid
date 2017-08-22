package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/4/24.
 */
public class MyCourseEntity implements Serializable{


    /**
     * listcontract : [{"contractid":1038,"userid":1212,"coachgroupid":2,"coachid":1217,"contractdate":"2017-04-18T14:50:30","effectivedates":"2017-04-18T14:50:30","expirydate":"2018-04-18T14:50:30","contractprice":300,"realprice":300,"freeprice":0,"freetimeprice":false,"effective":true,"status":1,"coachname":"新用户_371","coachpicture":"","contractname":"实地教学","contractTraineeID":null,"refuseRemark":null}]
     * ccourselist : [{"coursetotalcount":6,"coursefinishfcount":3,"addressid":224,"groupname":"广东省深圳市龙岗区正中高尔夫俱乐部","contractid":1038,"userid":1212,"coachgroupid":2,"coachid":1217,"contractdate":"2017-04-18T14:50:30","effectivedates":"2017-04-18T14:50:30","expirydate":"2018-04-18T14:50:30","contractprice":300,"realprice":300,"freeprice":0,"freetimeprice":false,"effective":true,"status":1,"coachname":"新用户_371","coachpicture":"","contractname":"实地教学","contractTraineeID":null,"refuseRemark":null}]
     * addressid : null
     * version : 1.1.106
     * datetime : 2017/4/24 14:03:35
     * accesstoken : NqOSdMU4ONSIqW1pIKarB/2Zwx2hsRf2Im74I+fjFOGa/w1HQsfGuBvf2lDPAJujFHfLXlLxNLaIQBJNUXUmISwKOYpudime3Aq1Xd5qAFwYztEpf+8M8T/ofh9jX5yuo7gBsdPiudqQmmtJlo5bwxSj37PGxitMEJD5ao+9iTHvMB00qRr2msMbZCIdZMMoiPxBw9PqFwvLm7YuCtu+cBCuWRec5CDO3ZA6gJVz020dbagENPK7VtFjXYJKzO+AcvxS6rGxGwGNrULUl8jKjwuUJz997ZfCzjUCb0nz8y3ZdeeNiiC88roi+ZvP4SGLRW1tbDGFnHzOMsvMlP0yvg==
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object addressid;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListcontractBean> listcontract;
    private List<CcourselistBean> ccourselist;

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

    public List<ListcontractBean> getListcontract() {
        return listcontract;
    }

    public void setListcontract(List<ListcontractBean> listcontract) {
        this.listcontract = listcontract;
    }

    public List<CcourselistBean> getCcourselist() {
        return ccourselist;
    }

    public void setCcourselist(List<CcourselistBean> ccourselist) {
        this.ccourselist = ccourselist;
    }

    public static class ListcontractBean implements Serializable{
        /**
         * contractid : 1038
         * userid : 1212
         * coachgroupid : 2
         * coachid : 1217
         * contractdate : 2017-04-18T14:50:30
         * effectivedates : 2017-04-18T14:50:30
         * expirydate : 2018-04-18T14:50:30
         * contractprice : 300.0
         * realprice : 300.0
         * freeprice : 0.0
         * freetimeprice : false
         * effective : true
         * status : 1
         * coachname : 新用户_371
         * coachpicture :
         * contractname : 实地教学
         * contractTraineeID : null
         * refuseRemark : null
         */

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
        private Object contractTraineeID;
        private Object refuseRemark;

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

        public Object getContractTraineeID() {
            return contractTraineeID;
        }

        public void setContractTraineeID(Object contractTraineeID) {
            this.contractTraineeID = contractTraineeID;
        }

        public Object getRefuseRemark() {
            return refuseRemark;
        }

        public void setRefuseRemark(Object refuseRemark) {
            this.refuseRemark = refuseRemark;
        }
    }

    public static class CcourselistBean implements Serializable{
        /**
         * coursetotalcount : 6
         * coursefinishfcount : 3
         * addressid : 224
         * groupname : 广东省深圳市龙岗区正中高尔夫俱乐部
         * contractid : 1038
         * userid : 1212
         * coachgroupid : 2
         * coachid : 1217
         * contractdate : 2017-04-18T14:50:30
         * effectivedates : 2017-04-18T14:50:30
         * expirydate : 2018-04-18T14:50:30
         * contractprice : 300.0
         * realprice : 300.0
         * freeprice : 0.0
         * freetimeprice : false
         * effective : true
         * status : 1
         * coachname : 新用户_371
         * coachpicture :
         * contractname : 实地教学
         * contractTraineeID : null
         * refuseRemark : null
         */

        private int coursetotalcount;
        private int coursefinishfcount;
        private int addressid;
        private String groupname;
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
        private Object contractTraineeID;
        private Object refuseRemark;

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

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
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

        public Object getContractTraineeID() {
            return contractTraineeID;
        }

        public void setContractTraineeID(Object contractTraineeID) {
            this.contractTraineeID = contractTraineeID;
        }

        public Object getRefuseRemark() {
            return refuseRemark;
        }

        public void setRefuseRemark(Object refuseRemark) {
            this.refuseRemark = refuseRemark;
        }
    }
}
