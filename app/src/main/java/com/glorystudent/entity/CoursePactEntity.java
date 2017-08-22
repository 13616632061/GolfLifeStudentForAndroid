package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/22.
 */
public class CoursePactEntity {

    /**
     * listAppointment : [{"appointmentid":2144,"coachgroupid":2,"userid":111,"positionsid":215,"coachid":80,"ccoursedetailid":1068,"appointmenttel":"18219140229","appointmentname":"何跃进","freepositions":false,"apponitmentstatus":"1","binarynumber":2,"remark":null,"appointmentdate":"2017-02-23T00:00:00","courtid":0,"courtname":null,"coachname":"燕宏朋","coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","ccoursedetailname":"技术要领","positionsname":"打位1","coachgrouplogo":"https://app.pgagolf.cn/images/pengtingLogo.PNG","coachtel":"18612565664","coachgrouptel":"8668821","coursecode":"2","appontimentheadpic":"https://app.pgagolf.cn/images/headfiles/2017-1-4/1483544616141.jpg","appontimentage":1,"appontimengolfage":4,"packagename":"基础教学","userBirthday":"2016-12-28T00:00:00","timeDefinition":"10:00-11:00"},{"appointmentid":2145,"coachgroupid":2,"userid":111,"positionsid":215,"coachid":80,"ccoursedetailid":1069,"appointmenttel":"18219140229","appointmentname":"何跃进","freepositions":false,"apponitmentstatus":"1","binarynumber":16,"remark":null,"appointmentdate":"2017-02-23T00:00:00","courtid":0,"courtname":null,"coachname":"燕宏朋","coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","ccoursedetailname":"比赛教学","positionsname":"打位1","coachgrouplogo":"https://app.pgagolf.cn/images/pengtingLogo.PNG","coachtel":"18612565664","coachgrouptel":"8668821","coursecode":"3","appontimentheadpic":"https://app.pgagolf.cn/images/headfiles/2017-1-4/1483544616141.jpg","appontimentage":1,"appontimengolfage":4,"packagename":"基础教学","userBirthday":"2016-12-28T00:00:00","timeDefinition":"14:00-15:00"}]
     * binarynumbers : null
     * version : 1.0.3
     * datetime : 2017/2/22 13:08:51
     * accesstoken : QwmMbG0f58T2ikXFq1NfU8LwHrHwS55wD2hH4csf3QQTPO8HIqM/0197h7vls4p7d/dcFsVu5Sk+UQVqQjgOO8fIj1oQiuRhTxagDQ8mxa4fJQCfPxpKVb+5KqL0ei2D1DwXdgfShBzL8MlhEpBCDSo+EBTolZaFFDyJviJ6nGq+G6uoXDUkBesBD3MGYRv/iBlztDmOtM9ZG7MNQYIhMkDE1o8J3YBYs3Sm1F9hbHeuyX9vDm6FB7qIKYmfpVwkIO3fvrtKNe1LRF8nYLVgZfmRRE7Nt0iuHFU5cO5HzjWh2zed7IB+ltucXZWFnMSv
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 2
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object binarynumbers;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean> listAppointment;

    public Object getBinarynumbers() {
        return binarynumbers;
    }

    public void setBinarynumbers(Object binarynumbers) {
        this.binarynumbers = binarynumbers;
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

    public List<ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean> getListAppointment() {
        return listAppointment;
    }

    public void setListAppointment(List<ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean> listAppointment) {
        this.listAppointment = listAppointment;
    }

    //    public static class ListAppointmentBean implements Serializable{
//        /**
//         * appointmentid : 2144
//         * coachgroupid : 2
//         * userid : 111
//         * positionsid : 215
//         * coachid : 80
//         * ccoursedetailid : 1068
//         * appointmenttel : 18219140229
//         * appointmentname : 何跃进
//         * freepositions : false
//         * apponitmentstatus : 1
//         * binarynumber : 2
//         * remark : null
//         * appointmentdate : 2017-02-23T00:00:00
//         * courtid : 0
//         * courtname : null
//         * coachname : 燕宏朋
//         * coachgroupname : 广东省深圳市龙岗区正中高尔夫俱乐部
//         * ccoursedetailname : 技术要领
//         * positionsname : 打位1
//         * coachgrouplogo : https://app.pgagolf.cn/images/pengtingLogo.PNG
//         * coachtel : 18612565664
//         * coachgrouptel : 8668821
//         * coursecode : 2
//         * appontimentheadpic : https://app.pgagolf.cn/images/headfiles/2017-1-4/1483544616141.jpg
//         * appontimentage : 1
//         * appontimengolfage : 4.0
//         * packagename : 基础教学
//         * userBirthday : 2016-12-28T00:00:00
//         * timeDefinition : 10:00-11:00
//         */
//
//        private int appointmentid;
//        private int coachgroupid;
//        private int userid;
//        private int positionsid;
//        private int coachid;
//        private int ccoursedetailid;
//        private String appointmenttel;
//        private String appointmentname;
//        private boolean freepositions;
//        private String apponitmentstatus;
//        private int binarynumber;
//        private Object remark;
//        private String appointmentdate;
//        private int courtid;
//        private Object courtname;
//        private String coachname;
//        private String coachgroupname;
//        private String ccoursedetailname;
//        private String positionsname;
//        private String coachgrouplogo;
//        private String coachtel;
//        private String coachgrouptel;
//        private String coursecode;
//        private String appontimentheadpic;
//        private int appontimentage;
//        private double appontimengolfage;
//        private String packagename;
//        private String userBirthday;
//        private String timeDefinition;
//
//        public int getAppointmentid() {
//            return appointmentid;
//        }
//
//        public void setAppointmentid(int appointmentid) {
//            this.appointmentid = appointmentid;
//        }
//
//        public int getCoachgroupid() {
//            return coachgroupid;
//        }
//
//        public void setCoachgroupid(int coachgroupid) {
//            this.coachgroupid = coachgroupid;
//        }
//
//        public int getUserid() {
//            return userid;
//        }
//
//        public void setUserid(int userid) {
//            this.userid = userid;
//        }
//
//        public int getPositionsid() {
//            return positionsid;
//        }
//
//        public void setPositionsid(int positionsid) {
//            this.positionsid = positionsid;
//        }
//
//        public int getCoachid() {
//            return coachid;
//        }
//
//        public void setCoachid(int coachid) {
//            this.coachid = coachid;
//        }
//
//        public int getCcoursedetailid() {
//            return ccoursedetailid;
//        }
//
//        public void setCcoursedetailid(int ccoursedetailid) {
//            this.ccoursedetailid = ccoursedetailid;
//        }
//
//        public String getAppointmenttel() {
//            return appointmenttel;
//        }
//
//        public void setAppointmenttel(String appointmenttel) {
//            this.appointmenttel = appointmenttel;
//        }
//
//        public String getAppointmentname() {
//            return appointmentname;
//        }
//
//        public void setAppointmentname(String appointmentname) {
//            this.appointmentname = appointmentname;
//        }
//
//        public boolean isFreepositions() {
//            return freepositions;
//        }
//
//        public void setFreepositions(boolean freepositions) {
//            this.freepositions = freepositions;
//        }
//
//        public String getApponitmentstatus() {
//            return apponitmentstatus;
//        }
//
//        public void setApponitmentstatus(String apponitmentstatus) {
//            this.apponitmentstatus = apponitmentstatus;
//        }
//
//        public int getBinarynumber() {
//            return binarynumber;
//        }
//
//        public void setBinarynumber(int binarynumber) {
//            this.binarynumber = binarynumber;
//        }
//
//        public Object getRemark() {
//            return remark;
//        }
//
//        public void setRemark(Object remark) {
//            this.remark = remark;
//        }
//
//        public String getAppointmentdate() {
//            return appointmentdate;
//        }
//
//        public void setAppointmentdate(String appointmentdate) {
//            this.appointmentdate = appointmentdate;
//        }
//
//        public int getCourtid() {
//            return courtid;
//        }
//
//        public void setCourtid(int courtid) {
//            this.courtid = courtid;
//        }
//
//        public Object getCourtname() {
//            return courtname;
//        }
//
//        public void setCourtname(Object courtname) {
//            this.courtname = courtname;
//        }
//
//        public String getCoachname() {
//            return coachname;
//        }
//
//        public void setCoachname(String coachname) {
//            this.coachname = coachname;
//        }
//
//        public String getCoachgroupname() {
//            return coachgroupname;
//        }
//
//        public void setCoachgroupname(String coachgroupname) {
//            this.coachgroupname = coachgroupname;
//        }
//
//        public String getCcoursedetailname() {
//            return ccoursedetailname;
//        }
//
//        public void setCcoursedetailname(String ccoursedetailname) {
//            this.ccoursedetailname = ccoursedetailname;
//        }
//
//        public String getPositionsname() {
//            return positionsname;
//        }
//
//        public void setPositionsname(String positionsname) {
//            this.positionsname = positionsname;
//        }
//
//        public String getCoachgrouplogo() {
//            return coachgrouplogo;
//        }
//
//        public void setCoachgrouplogo(String coachgrouplogo) {
//            this.coachgrouplogo = coachgrouplogo;
//        }
//
//        public String getCoachtel() {
//            return coachtel;
//        }
//
//        public void setCoachtel(String coachtel) {
//            this.coachtel = coachtel;
//        }
//
//        public String getCoachgrouptel() {
//            return coachgrouptel;
//        }
//
//        public void setCoachgrouptel(String coachgrouptel) {
//            this.coachgrouptel = coachgrouptel;
//        }
//
//        public String getCoursecode() {
//            return coursecode;
//        }
//
//        public void setCoursecode(String coursecode) {
//            this.coursecode = coursecode;
//        }
//
//        public String getAppontimentheadpic() {
//            return appontimentheadpic;
//        }
//
//        public void setAppontimentheadpic(String appontimentheadpic) {
//            this.appontimentheadpic = appontimentheadpic;
//        }
//
//        public int getAppontimentage() {
//            return appontimentage;
//        }
//
//        public void setAppontimentage(int appontimentage) {
//            this.appontimentage = appontimentage;
//        }
//
//        public double getAppontimengolfage() {
//            return appontimengolfage;
//        }
//
//        public void setAppontimengolfage(double appontimengolfage) {
//            this.appontimengolfage = appontimengolfage;
//        }
//
//        public String getPackagename() {
//            return packagename;
//        }
//
//        public void setPackagename(String packagename) {
//            this.packagename = packagename;
//        }
//
//        public String getUserBirthday() {
//            return userBirthday;
//        }
//
//        public void setUserBirthday(String userBirthday) {
//            this.userBirthday = userBirthday;
//        }
//
//        public String getTimeDefinition() {
//            return timeDefinition;
//        }
//
//        public void setTimeDefinition(String timeDefinition) {
//            this.timeDefinition = timeDefinition;
//        }
//    }
}
