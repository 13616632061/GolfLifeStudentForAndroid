package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/2/22.
 */
public class CourseDetailsEntity {

    /**
     * contractcourse : [{"_ContractID":1027,"_CoachID":80,"ccoursedetailid":1067,"contractid":1027,"coachid":80,"coachname":"何跃进","free":false,"give":false,"detailmoney":200,"realmoney":200,"coachcommision":0,"coachincome":500,"finish":false,"appointment":true,"ccoursedetailname":"挥杆","remark":null,"coursecode":"1"},{"_ContractID":1027,"_CoachID":80,"ccoursedetailid":1068,"contractid":1027,"coachid":80,"coachname":"何跃进","free":false,"give":false,"detailmoney":500,"realmoney":500,"coachcommision":0,"coachincome":500,"finish":false,"appointment":false,"ccoursedetailname":"技术要领","remark":null,"coursecode":"2"},{"_ContractID":1027,"_CoachID":80,"ccoursedetailid":1069,"contractid":1027,"coachid":80,"coachname":"何跃进","free":false,"give":false,"detailmoney":300,"realmoney":300,"coachcommision":0,"coachincome":500,"finish":false,"appointment":false,"ccoursedetailname":"比赛教学","remark":null,"coursecode":"3"}]
     * version : 1.0.3
     * datetime : 2017/2/22 9:11:29
     * accesstoken : QwmMbG0f58T2ikXFq1NfU9eAqXDi6zK8UO/2KCRMyT4vZAQbVI2gYto4U2aK2V67XoNePnwjFvfBaw79zZENRYzKk1zdGVo1en0ilOXibMKhYlU1k0OSUoZYzogydW9UZQ+mo9e5arzfzrDvsnLUjHuod0EQNcNrx9Zq9NLWFL4zvys9TuclMK/s5oWefgAmYE9PMx34dfLfVsql/BBu0TBXZnqprWqVHXSaDDc65CJ1Hds1prQBx3D34Jh0GpPPzKSyYWf+BgpvcNY4a8OuMkUJcyHt4bL1tQC7VPXv1KHX5VjBeXbSjJe3QTB1LHKG
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 3
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ContractcourseBean> listContractCourseDetail;

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

    public List<ContractcourseBean> getContractcourse() {
        return listContractCourseDetail;
    }

    public void setContractcourse(List<ContractcourseBean> contractcourse) {
        this.listContractCourseDetail = contractcourse;
    }

    public static class ContractcourseBean implements Serializable{
        /**
         * _ContractID : 1027
         * _CoachID : 80
         * ccoursedetailid : 1067
         * contractid : 1027
         * coachid : 80
         * coachname : 何跃进
         * free : false
         * give : false
         * detailmoney : 200.0
         * realmoney : 200.0
         * coachcommision : 0.0
         * coachincome : 500.0
         * finish : false
         * appointment : true
         * ccoursedetailname : 挥杆
         * remark : null
         * coursecode : 1
         */

        private int _ContractID;
        private int _CoachID;
        private String appointmentTime;
        private ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean appointments;
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

        public ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean getAppointments() {
            return appointments;
        }

        public void setAppointments(ContractInfoEntity.ListContractCourseDetailBean.AppointmentsBean appointments) {
            this.appointments = appointments;
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
