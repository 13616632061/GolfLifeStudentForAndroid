package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/14.
 */
public class AllAppointmentEntity {

    /**
     * listtimeplan : [{"planid":914,"coachgroupid":2,"week":"2","timedefinitionid":8,"timesection":"11:00-12:00","timeprice":0,"positionsid":215,"positionsname":"打位1","holiday":false,"planstatus":"1","binarynumber":0},{"planid":749,"coachgroupid":2,"week":"2","timedefinitionid":6,"timesection":"10:00-11:00","timeprice":90,"positionsid":215,"positionsname":"打位1","holiday":false,"planstatus":"1","binarynumber":2}]
     * coachperiod : 0
     * userperiod : 0
     * planpauseperiod : 0
     * appointmentperiod : 0
     * version : 1.1.106
     * datetime : 2017/2/14 16:26:07
     * accesstoken : vJpykCSaib1L2J6Y/jMEyvIbgF3RG5lid1uYTIEFJXZUlFuQ4gvQSwP+eFNbnHvkOIbEe2Zji1G58eJLi7GOYKvFA8NCkjNrEvt3VxFAWwFDv4hRXLbyM95/AWTaGDLJeEgZR4CgsjLGcz8hpeYO5EqwjG1qbsajnOTA4glSrZsqoRH3qE2pgDMmzyasLxFSk+XqOB/2LPLHEy9fZPuAnhab0X4cGGaYqDllBf4FhREV210Ifz47DvMbFu0v92Q8rxHcWPWRS+KpzZvx79Pa928oqwicQCxA6XbLfsiP+4OwuMu5Xci9N2E3oJnPn7OC
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 0
     * totalpagenum : 0
     * nowpagenum : null
     * pagerownum : 20
     */

    private int coachperiod;
    private int userperiod;
    private int planpauseperiod;
    private int appointmentperiod;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListtimeplanBean> listtimeplan;

    public int getCoachperiod() {
        return coachperiod;
    }

    public void setCoachperiod(int coachperiod) {
        this.coachperiod = coachperiod;
    }

    public int getUserperiod() {
        return userperiod;
    }

    public void setUserperiod(int userperiod) {
        this.userperiod = userperiod;
    }

    public int getPlanpauseperiod() {
        return planpauseperiod;
    }

    public void setPlanpauseperiod(int planpauseperiod) {
        this.planpauseperiod = planpauseperiod;
    }

    public int getAppointmentperiod() {
        return appointmentperiod;
    }

    public void setAppointmentperiod(int appointmentperiod) {
        this.appointmentperiod = appointmentperiod;
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

    public List<ListtimeplanBean> getListtimeplan() {
        return listtimeplan;
    }

    public void setListtimeplan(List<ListtimeplanBean> listtimeplan) {
        this.listtimeplan = listtimeplan;
    }

    public static class ListtimeplanBean {
        /**
         * planid : 914
         * coachgroupid : 2
         * week : 2
         * timedefinitionid : 8
         * timesection : 11:00-12:00
         * timeprice : 0.0
         * positionsid : 215
         * positionsname : 打位1
         * holiday : false
         * planstatus : 1
         * binarynumber : 0
         */

        private int planid;
        private int coachgroupid;
        private String week;
        private int timedefinitionid;
        private String timesection;
        private double timeprice;
        private int positionsid;
        private String positionsname;
        private boolean holiday;
        private String planstatus;
        private int binarynumber;

        public int getPlanid() {
            return planid;
        }

        public void setPlanid(int planid) {
            this.planid = planid;
        }

        public int getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(int coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

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

        public double getTimeprice() {
            return timeprice;
        }

        public void setTimeprice(double timeprice) {
            this.timeprice = timeprice;
        }

        public int getPositionsid() {
            return positionsid;
        }

        public void setPositionsid(int positionsid) {
            this.positionsid = positionsid;
        }

        public String getPositionsname() {
            return positionsname;
        }

        public void setPositionsname(String positionsname) {
            this.positionsname = positionsname;
        }

        public boolean isHoliday() {
            return holiday;
        }

        public void setHoliday(boolean holiday) {
            this.holiday = holiday;
        }

        public String getPlanstatus() {
            return planstatus;
        }

        public void setPlanstatus(String planstatus) {
            this.planstatus = planstatus;
        }

        public int getBinarynumber() {
            return binarynumber;
        }

        public void setBinarynumber(int binarynumber) {
            this.binarynumber = binarynumber;
        }
    }
}
