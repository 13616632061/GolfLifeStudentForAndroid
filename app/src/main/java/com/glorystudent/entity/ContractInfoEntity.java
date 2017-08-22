package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/4/20.
 */
public class ContractInfoEntity implements Serializable{


    /**
     * contract : null
     * listcontract : null
     * listcontractcourse : null
     * listcontracts : [{"applyStatus":null,"contractid":1046,"userid":3,"coachgroupid":2,"coachid":1212,"contractdate":"2017-04-21T20:27:08","effectivedates":"2017-04-21T20:27:08","expirydate":"2018-04-21T20:27:08","contractprice":500,"realprice":500,"freeprice":0,"freetimeprice":false,"effective":true,"status":0,"coachname":"","coachpicture":"","contractname":"基础教学","contractTraineeID":null,"refuseRemark":null},{"applyStatus":null,"contractid":1050,"userid":3,"coachgroupid":2,"coachid":1212,"contractdate":"2017-04-23T10:57:39","effectivedates":"2017-04-23T10:57:39","expirydate":"2018-04-23T10:57:39","contractprice":300,"realprice":300,"freeprice":0,"freetimeprice":false,"effective":true,"status":1,"coachname":"","coachpicture":"","contractname":"实地教学","contractTraineeID":null,"refuseRemark":null},{"applyStatus":null,"contractid":1053,"userid":3,"coachgroupid":2,"coachid":1212,"contractdate":"2017-04-23T11:06:30","effectivedates":"2017-04-23T11:06:30","expirydate":"2018-04-23T11:06:30","contractprice":700,"realprice":700,"freeprice":0,"freetimeprice":false,"effective":true,"status":0,"coachname":"","coachpicture":"","contractname":"套餐一","contractTraineeID":null,"refuseRemark":null},{"applyStatus":null,"contractid":1072,"userid":3,"coachgroupid":2,"coachid":1212,"contractdate":"2017-04-23T18:11:30","effectivedates":"2017-04-23T18:11:30","expirydate":"2018-04-23T18:11:30","contractprice":300,"realprice":300,"freeprice":0,"freetimeprice":false,"effective":true,"status":1,"coachname":"","coachpicture":"","contractname":"实地教学","contractTraineeID":null,"refuseRemark":null}]
     * listContractCourseDetail : [{"_ContractID":1046,"_CoachID":1212,"appointmentTime":"2017-04-24 9:00","appointments":{"appointmentid":2173,"coachgroupid":2,"userid":84,"positionsid":216,"coachid":1212,"ccoursedetailid":1113,"appointmenttel":"18680178858","appointmentname":"张鲁","freepositions":false,"apponitmentstatus":"4","binarynumber":1,"remark":null,"appointmentdate":"2017-04-24T00:00:00","courtid":0,"courtname":null,"coachname":"何跃进","coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","ccoursedetailname":"挥杆","positionsname":"打位2","coachgrouplogo":"https://app.pgagolf.cn/images/pengtingLogo.PNG","coachtel":"18219140229","coachgrouptel":"8668821","coursecode":"1","appontimentheadpic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","appontimentage":1,"appontimengolfage":3,"packagename":"基础教学","userBirthday":"2016-11-29T00:00:00","timeDefinition":"9:00-10:00"},"ccoursedetailid":1113,"contractid":1046,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":0,"finish":false,"appointment":true,"ccoursedetailname":"挥杆","remark":null,"coursecode":"1"},{"_ContractID":1046,"_CoachID":1212,"appointmentTime":"2017-04-24 13:00","appointments":{"appointmentid":2177,"coachgroupid":2,"userid":84,"positionsid":221,"coachid":1212,"ccoursedetailid":1114,"appointmenttel":"18680178858","appointmentname":"张鲁","freepositions":false,"apponitmentstatus":"4","binarynumber":8,"remark":null,"appointmentdate":"2017-04-24T00:00:00","courtid":0,"courtname":null,"coachname":"何跃进","coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","ccoursedetailname":"技术要领","positionsname":"打位1","coachgrouplogo":"https://app.pgagolf.cn/images/pengtingLogo.PNG","coachtel":"18219140229","coachgrouptel":"8668821","coursecode":"2","appontimentheadpic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","appontimentage":1,"appontimengolfage":3,"packagename":"基础教学","userBirthday":"2016-11-29T00:00:00","timeDefinition":"13:00-14:00"},"ccoursedetailid":1114,"contractid":1046,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":500,"realmoney":500,"coachcommision":0,"coachincome":0,"finish":false,"appointment":true,"ccoursedetailname":"技术要领","remark":null,"coursecode":"2"},{"_ContractID":1046,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1115,"contractid":1046,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":300,"realmoney":300,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"比赛教学","remark":null,"coursecode":"3"},{"_ContractID":1050,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1116,"contractid":1050,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"挥杆","remark":null,"coursecode":"1"},{"_ContractID":1050,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1117,"contractid":1050,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"视频教学","remark":null,"coursecode":"2"},{"_ContractID":1053,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1118,"contractid":1053,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":500,"realmoney":500,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"技术要领","remark":null,"coursecode":"1"},{"_ContractID":1053,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1119,"contractid":1053,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"视频教学","remark":null,"coursecode":"2"},{"_ContractID":1072,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1158,"contractid":1072,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"挥杆","remark":null,"coursecode":"1"},{"_ContractID":1072,"_CoachID":1212,"appointmentTime":null,"appointments":null,"ccoursedetailid":1159,"contractid":1072,"coachid":1212,"coachname":"","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":0,"finish":false,"appointment":false,"ccoursedetailname":"视频教学","remark":null,"coursecode":"2"}]
     * version : 1.1.106
     * datetime : 2017/4/24 16:28:31
     * accesstoken : NqOSdMU4ONSIqW1pIKarBxWWvZ647rur1qQqfa7902DJJrB4/fldamLD5rdogE22rsTbnSZSZOZCg97Q N 1TL7mxnlv8pF9LqYMHz yLKtzJLtcB79qoTy4jNigXNoi4FEtA95Q7KlbC8yDJs/mRyUCpVrwK4EEfPcdiDgD8fsIiPtuW0kNHCzgMxEeTg6TVtR46FEe3SUEloHNWPbp/HmgO88NjBmBKAOwQnS9mIOt8/Ar1TNDStsoIod9wSO1q   qqyhzCUrr8V2D5gYKMy1xCZRKPxyvlFOhZzsZB7bZUt9mQTyTiAyGWg6xraESNigQGh7GZ5GKj0NVZ8lpg==
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object contract;
    private Object listcontract;
    private Object listcontractcourse;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListcontractsBean> listcontracts;
    private List<ListContractCourseDetailBean> listContractCourseDetail;

    public Object getContract() {
        return contract;
    }

    public void setContract(Object contract) {
        this.contract = contract;
    }

    public Object getListcontract() {
        return listcontract;
    }

    public void setListcontract(Object listcontract) {
        this.listcontract = listcontract;
    }

    public Object getListcontractcourse() {
        return listcontractcourse;
    }

    public void setListcontractcourse(Object listcontractcourse) {
        this.listcontractcourse = listcontractcourse;
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

    public List<ListcontractsBean> getListcontracts() {
        return listcontracts;
    }

    public void setListcontracts(List<ListcontractsBean> listcontracts) {
        this.listcontracts = listcontracts;
    }

    public List<ListContractCourseDetailBean> getListContractCourseDetail() {
        return listContractCourseDetail;
    }

    public void setListContractCourseDetail(List<ListContractCourseDetailBean> listContractCourseDetail) {
        this.listContractCourseDetail = listContractCourseDetail;
    }

    public static class ListcontractsBean implements Serializable{
        /**
         * applyStatus : null
         * contractid : 1046
         * userid : 3
         * coachgroupid : 2
         * coachid : 1212
         * contractdate : 2017-04-21T20:27:08
         * effectivedates : 2017-04-21T20:27:08
         * expirydate : 2018-04-21T20:27:08
         * contractprice : 500
         * realprice : 500
         * freeprice : 0
         * freetimeprice : false
         * effective : true
         * status : 0
         * coachname :
         * coachpicture :
         * contractname : 基础教学
         * contractTraineeID : null
         * refuseRemark : null
         */

        private String applyStatus;
        private int contractid;
        private int userid;
        private int coachgroupid;
        private int coachid;
        private String contractdate;
        private String effectivedates;
        private String expirydate;
        private int contractprice;
        private int realprice;
        private int freeprice;
        private boolean freetimeprice;
        private boolean effective;
        private int status;
        private String coachname;
        private String coachpicture;
        private String contractname;
        private Object contractTraineeID;
        private Object refuseRemark;
        private List<ContractInfoEntity.ListContractCourseDetailBean> listContractDetail;

        public List<ListContractCourseDetailBean> getListContractDetail() {
            return listContractDetail;
        }

        public void setListContractDetail(List<ListContractCourseDetailBean> listContractDetail) {
            this.listContractDetail = listContractDetail;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
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

        public int getContractprice() {
            return contractprice;
        }

        public void setContractprice(int contractprice) {
            this.contractprice = contractprice;
        }

        public int getRealprice() {
            return realprice;
        }

        public void setRealprice(int realprice) {
            this.realprice = realprice;
        }

        public int getFreeprice() {
            return freeprice;
        }

        public void setFreeprice(int freeprice) {
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

    public static class ListContractCourseDetailBean implements Serializable{
        /**
         * _ContractID : 1046
         * _CoachID : 1212
         * appointmentTime : 2017-04-24 9:00
         * appointments : {"appointmentid":2173,"coachgroupid":2,"userid":84,"positionsid":216,"coachid":1212,"ccoursedetailid":1113,"appointmenttel":"18680178858","appointmentname":"张鲁","freepositions":false,"apponitmentstatus":"4","binarynumber":1,"remark":null,"appointmentdate":"2017-04-24T00:00:00","courtid":0,"courtname":null,"coachname":"何跃进","coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","ccoursedetailname":"挥杆","positionsname":"打位2","coachgrouplogo":"https://app.pgagolf.cn/images/pengtingLogo.PNG","coachtel":"18219140229","coachgrouptel":"8668821","coursecode":"1","appontimentheadpic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","appontimentage":1,"appontimengolfage":3,"packagename":"基础教学","userBirthday":"2016-11-29T00:00:00","timeDefinition":"9:00-10:00"}
         * ccoursedetailid : 1113
         * contractid : 1046
         * coachid : 1212
         * coachname :
         * free : false
         * give : false
         * detailmoney : 200
         * realmoney : 200
         * coachcommision : 0
         * coachincome : 0
         * finish : false
         * appointment : true
         * ccoursedetailname : 挥杆
         * remark : null
         * coursecode : 1
         */

        private int _ContractID;
        private int _CoachID;
        private String appointmentTime;
        private AppointmentsBean appointments;
        private int ccoursedetailid;
        private int contractid;
        private int coachid;
        private String coachname;
        private boolean free;
        private boolean give;
        private int detailmoney;
        private int realmoney;
        private int coachcommision;
        private int coachincome;
        private boolean finish;
        private boolean appointment;
        private String ccoursedetailname;
        private Object remark;
        private String coursecode;
        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

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

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public AppointmentsBean getAppointments() {
            return appointments;
        }

        public void setAppointments(AppointmentsBean appointments) {
            this.appointments = appointments;
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

        public int getDetailmoney() {
            return detailmoney;
        }

        public void setDetailmoney(int detailmoney) {
            this.detailmoney = detailmoney;
        }

        public int getRealmoney() {
            return realmoney;
        }

        public void setRealmoney(int realmoney) {
            this.realmoney = realmoney;
        }

        public int getCoachcommision() {
            return coachcommision;
        }

        public void setCoachcommision(int coachcommision) {
            this.coachcommision = coachcommision;
        }

        public int getCoachincome() {
            return coachincome;
        }

        public void setCoachincome(int coachincome) {
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

        public static class AppointmentsBean implements Serializable{
            /**
             * appointmentid : 2173
             * coachgroupid : 2
             * userid : 84
             * positionsid : 216
             * coachid : 1212
             * ccoursedetailid : 1113
             * appointmenttel : 18680178858
             * appointmentname : 张鲁
             * freepositions : false
             * apponitmentstatus : 4
             * binarynumber : 1
             * remark : null
             * appointmentdate : 2017-04-24T00:00:00
             * courtid : 0
             * courtname : null
             * coachname : 何跃进
             * coachgroupname : 广东省深圳市龙岗区正中高尔夫俱乐部
             * ccoursedetailname : 挥杆
             * positionsname : 打位2
             * coachgrouplogo : https://app.pgagolf.cn/images/pengtingLogo.PNG
             * coachtel : 18219140229
             * coachgrouptel : 8668821
             * coursecode : 1
             * appontimentheadpic : http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png
             * appontimentage : 1
             * appontimengolfage : 3
             * packagename : 基础教学
             * userBirthday : 2016-11-29T00:00:00
             * timeDefinition : 9:00-10:00
             */

            private int appointmentid;
            private int coachgroupid;
            private int userid;
            private int positionsid;
            private int coachid;
            private int ccoursedetailid;
            private String appointmenttel;
            private String appointmentname;
            private boolean freepositions;
            private String apponitmentstatus;
            private int binarynumber;
            private Object remark;
            private String appointmentdate;
            private int courtid;
            private Object courtname;
            private String coachname;
            private String coachgroupname;
            private String ccoursedetailname;
            private String positionsname;
            private String coachgrouplogo;
            private String coachtel;
            private String coachgrouptel;
            private String coursecode;
            private String appontimentheadpic;
            private int appontimentage;
            private int appontimengolfage;
            private String packagename;
            private String userBirthday;
            private String timeDefinition;

            public int getAppointmentid() {
                return appointmentid;
            }

            public void setAppointmentid(int appointmentid) {
                this.appointmentid = appointmentid;
            }

            public int getCoachgroupid() {
                return coachgroupid;
            }

            public void setCoachgroupid(int coachgroupid) {
                this.coachgroupid = coachgroupid;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public int getPositionsid() {
                return positionsid;
            }

            public void setPositionsid(int positionsid) {
                this.positionsid = positionsid;
            }

            public int getCoachid() {
                return coachid;
            }

            public void setCoachid(int coachid) {
                this.coachid = coachid;
            }

            public int getCcoursedetailid() {
                return ccoursedetailid;
            }

            public void setCcoursedetailid(int ccoursedetailid) {
                this.ccoursedetailid = ccoursedetailid;
            }

            public String getAppointmenttel() {
                return appointmenttel;
            }

            public void setAppointmenttel(String appointmenttel) {
                this.appointmenttel = appointmenttel;
            }

            public String getAppointmentname() {
                return appointmentname;
            }

            public void setAppointmentname(String appointmentname) {
                this.appointmentname = appointmentname;
            }

            public boolean isFreepositions() {
                return freepositions;
            }

            public void setFreepositions(boolean freepositions) {
                this.freepositions = freepositions;
            }

            public String getApponitmentstatus() {
                return apponitmentstatus;
            }

            public void setApponitmentstatus(String apponitmentstatus) {
                this.apponitmentstatus = apponitmentstatus;
            }

            public int getBinarynumber() {
                return binarynumber;
            }

            public void setBinarynumber(int binarynumber) {
                this.binarynumber = binarynumber;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getAppointmentdate() {
                return appointmentdate;
            }

            public void setAppointmentdate(String appointmentdate) {
                this.appointmentdate = appointmentdate;
            }

            public int getCourtid() {
                return courtid;
            }

            public void setCourtid(int courtid) {
                this.courtid = courtid;
            }

            public Object getCourtname() {
                return courtname;
            }

            public void setCourtname(Object courtname) {
                this.courtname = courtname;
            }

            public String getCoachname() {
                return coachname;
            }

            public void setCoachname(String coachname) {
                this.coachname = coachname;
            }

            public String getCoachgroupname() {
                return coachgroupname;
            }

            public void setCoachgroupname(String coachgroupname) {
                this.coachgroupname = coachgroupname;
            }

            public String getCcoursedetailname() {
                return ccoursedetailname;
            }

            public void setCcoursedetailname(String ccoursedetailname) {
                this.ccoursedetailname = ccoursedetailname;
            }

            public String getPositionsname() {
                return positionsname;
            }

            public void setPositionsname(String positionsname) {
                this.positionsname = positionsname;
            }

            public String getCoachgrouplogo() {
                return coachgrouplogo;
            }

            public void setCoachgrouplogo(String coachgrouplogo) {
                this.coachgrouplogo = coachgrouplogo;
            }

            public String getCoachtel() {
                return coachtel;
            }

            public void setCoachtel(String coachtel) {
                this.coachtel = coachtel;
            }

            public String getCoachgrouptel() {
                return coachgrouptel;
            }

            public void setCoachgrouptel(String coachgrouptel) {
                this.coachgrouptel = coachgrouptel;
            }

            public String getCoursecode() {
                return coursecode;
            }

            public void setCoursecode(String coursecode) {
                this.coursecode = coursecode;
            }

            public String getAppontimentheadpic() {
                return appontimentheadpic;
            }

            public void setAppontimentheadpic(String appontimentheadpic) {
                this.appontimentheadpic = appontimentheadpic;
            }

            public int getAppontimentage() {
                return appontimentage;
            }

            public void setAppontimentage(int appontimentage) {
                this.appontimentage = appontimentage;
            }

            public int getAppontimengolfage() {
                return appontimengolfage;
            }

            public void setAppontimengolfage(int appontimengolfage) {
                this.appontimengolfage = appontimengolfage;
            }

            public String getPackagename() {
                return packagename;
            }

            public void setPackagename(String packagename) {
                this.packagename = packagename;
            }

            public String getUserBirthday() {
                return userBirthday;
            }

            public void setUserBirthday(String userBirthday) {
                this.userBirthday = userBirthday;
            }

            public String getTimeDefinition() {
                return timeDefinition;
            }

            public void setTimeDefinition(String timeDefinition) {
                this.timeDefinition = timeDefinition;
            }
        }
    }
}
