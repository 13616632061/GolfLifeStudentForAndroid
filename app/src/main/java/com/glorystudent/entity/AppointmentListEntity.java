package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/16.
 */
public class AppointmentListEntity {


    /**
     * listAppointment : [{"customerPhoto":null,"remarks":null,"appointmentid":2202,"coachgroupid":2,"userid":84,"positionsid":220,"coachid":1212,"ccoursedetailid":1182,"appointmenttel":"18680178858","appointmentname":"张鲁","freepositions":false,"apponitmentstatus":"1","binarynumber":42,"remark":null,"appointmentdate":"2017-04-27T00:00:00","courtid":0,"courtname":null,"coachname":"何跃进","coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","ccoursedetailname":"技术要领","positionsname":"打位1","coachgrouplogo":"https://app.pgagolf.cn/images/pengtingLogo.PNG","coachtel":"18219140229","coachgrouptel":"8668821","coursecode":"1","appontimentheadpic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","appontimentage":1,"appontimengolfage":3,"packagename":"套餐一","userBirthday":"2016-11-29T00:00:00","timeDefinition":"10:00-11:00,13:00-14:00,15:00-16:00","contractTraineeID":3}]
     * listTimeDefinition : [{"timedefinitionid":4,"timesection":"9:00-10:00","binarynumber":1},{"timedefinitionid":6,"timesection":"10:00-11:00","binarynumber":2},{"timedefinitionid":8,"timesection":"11:00-12:00","binarynumber":4},{"timedefinitionid":12,"timesection":"13:00-14:00","binarynumber":8},{"timedefinitionid":13,"timesection":"14:00-15:00","binarynumber":16},{"timedefinitionid":14,"timesection":"15:00-16:00","binarynumber":32},{"timedefinitionid":15,"timesection":"16:00-17:00","binarynumber":64}]
     * version : 1.1.106
     * datetime : 2017/4/26 9:41:57
     * accesstoken : NqOSdMU4ONSIqW1pIKarB2A3pxVReuWsVLjJjmG  4UeEvHhnHXV4fzeXGnbBsqBjAPw6/MKZPL1kh86kXDYrVX n8MCjWcWY5gGmZcJUApBwgRy 5EPBu/zMZPkh/eF3B e7FZCGg3f/GlWH1YuN16mYgcFoJy5SIIxPb57VrSqiVE3Pql0KOrAwI69ouFXn2fNyLiSm/1oDsvjuYwRGjEn3/n2nMxVy7p9r9H59bxOkBRepm PXbWvAidS4I/Z2Uk6FmUYO20gEkLmxYMfeWRDNvyycqDjesOPKF0OdEtnB/KfoJCSFeqrWIMsl5FeMN2VW6uvdepdBw7BnPwAAA==
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListAppointmentBean> listAppointment;
    private List<ListTimeDefinitionBean> listTimeDefinition;

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

    public List<ListAppointmentBean> getListAppointment() {
        return listAppointment;
    }

    public void setListAppointment(List<ListAppointmentBean> listAppointment) {
        this.listAppointment = listAppointment;
    }

    public List<ListTimeDefinitionBean> getListTimeDefinition() {
        return listTimeDefinition;
    }

    public void setListTimeDefinition(List<ListTimeDefinitionBean> listTimeDefinition) {
        this.listTimeDefinition = listTimeDefinition;
    }

    public static class ListAppointmentBean {
        /**
         * customerPhoto : null
         * remarks : null
         * appointmentid : 2202
         * coachgroupid : 2
         * userid : 84
         * positionsid : 220
         * coachid : 1212
         * ccoursedetailid : 1182
         * appointmenttel : 18680178858
         * appointmentname : 张鲁
         * freepositions : false
         * apponitmentstatus : 1
         * binarynumber : 42
         * remark : null
         * appointmentdate : 2017-04-27T00:00:00
         * courtid : 0
         * courtname : null
         * coachname : 何跃进
         * coachgroupname : 广东省深圳市龙岗区正中高尔夫俱乐部
         * ccoursedetailname : 技术要领
         * positionsname : 打位1
         * coachgrouplogo : https://app.pgagolf.cn/images/pengtingLogo.PNG
         * coachtel : 18219140229
         * coachgrouptel : 8668821
         * coursecode : 1
         * appontimentheadpic : http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png
         * appontimentage : 1
         * appontimengolfage : 3
         * packagename : 套餐一
         * userBirthday : 2016-11-29T00:00:00
         * timeDefinition : 10:00-11:00,13:00-14:00,15:00-16:00
         * contractTraineeID : 3
         */

        private String customerPhoto;
        private String remarks;
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
        private int contractTraineeID;

        public String getCustomerPhoto() {
            return customerPhoto;
        }

        public void setCustomerPhoto(String customerPhoto) {
            this.customerPhoto = customerPhoto;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

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

        public int getContractTraineeID() {
            return contractTraineeID;
        }

        public void setContractTraineeID(int contractTraineeID) {
            this.contractTraineeID = contractTraineeID;
        }
    }

    public static class ListTimeDefinitionBean {
        /**
         * timedefinitionid : 4
         * timesection : 9:00-10:00
         * binarynumber : 1
         */

        private int timedefinitionid;
        private String timesection;
        private int binarynumber;

        public int getTimedefinitionid() {
            return timedefinitionid;
        }

        public void setTimedefinitionid(int timedefinitionid) {
            this.timedefinitionid = timedefinitionid;
        }

        public String getTimesection() {
            return timesection;
        }

        public void setTimesection(String timesection) {
            this.timesection = timesection;
        }

        public int getBinarynumber() {
            return binarynumber;
        }

        public void setBinarynumber(int binarynumber) {
            this.binarynumber = binarynumber;
        }
    }
}
