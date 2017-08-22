package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/15.
 */
public class SubscribeRequestEntity {
    private AppointmentBean appointment;
    private Integer appointmenttype;

    public static class AppointmentBean{
        private Integer appointmentid;
        private Integer coachgroupid;
        private Integer userid;
        private Integer positionsid;
        private Integer coachid;
        private Integer ccoursedetailid;
        private String appointmenttel;
        private String appointmentname;
        private String freepositions;
        private String apponitmentstatus;
        private Integer binarynumber;
        private String remark;
        private String appointmentdate;
        private String courtid;
        private String courtname;
        private String coachname;
        private String coachgroupname;
        private String ccoursedetailname;
        private String positionsname;
        private String coachgrouplogo;
        private String coachtel;
        private String coachgrouptel;
        private String coursecode;
        private String appontimentheadpic;
        private Integer appontimentage;
        private Integer appontimengolfage;
        private String packagename;
        private String userBirthday;
        private String timeDefinition;

        public Integer getAppointmentid() {
            return appointmentid;
        }

        public void setAppointmentid(Integer appointmentid) {
            this.appointmentid = appointmentid;
        }

        public Integer getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(Integer coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getPositionsid() {
            return positionsid;
        }

        public void setPositionsid(Integer positionsid) {
            this.positionsid = positionsid;
        }

        public Integer getCoachid() {
            return coachid;
        }

        public void setCoachid(Integer coachid) {
            this.coachid = coachid;
        }

        public Integer getCcoursedetailid() {
            return ccoursedetailid;
        }

        public void setCcoursedetailid(Integer ccoursedetailid) {
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

        public String getFreepositions() {
            return freepositions;
        }

        public void setFreepositions(String freepositions) {
            this.freepositions = freepositions;
        }

        public String getApponitmentstatus() {
            return apponitmentstatus;
        }

        public void setApponitmentstatus(String apponitmentstatus) {
            this.apponitmentstatus = apponitmentstatus;
        }

        public Integer getBinarynumber() {
            return binarynumber;
        }

        public void setBinarynumber(Integer binarynumber) {
            this.binarynumber = binarynumber;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAppointmentdate() {
            return appointmentdate;
        }

        public void setAppointmentdate(String appointmentdate) {
            this.appointmentdate = appointmentdate;
        }

        public String getCourtid() {
            return courtid;
        }

        public void setCourtid(String courtid) {
            this.courtid = courtid;
        }

        public String getCourtname() {
            return courtname;
        }

        public void setCourtname(String courtname) {
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

        public Integer getAppontimentage() {
            return appontimentage;
        }

        public void setAppontimentage(Integer appontimentage) {
            this.appontimentage = appontimentage;
        }

        public Integer getAppontimengolfage() {
            return appontimengolfage;
        }

        public void setAppontimengolfage(Integer appontimengolfage) {
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
    private Integer coachid;
    private Integer usersid;

    public Integer getAppointmenttype() {
        return appointmenttype;
    }

    public void setAppointmenttype(Integer appointmenttype) {
        this.appointmenttype = appointmenttype;
    }
    public AppointmentBean getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentBean appointment) {
        this.appointment = appointment;
    }

    public Integer getCoachid() {
        return coachid;
    }

    public void setCoachid(Integer coachid) {
        this.coachid = coachid;
    }

    public Integer getUsersid() {
        return usersid;
    }

    public void setUsersid(Integer usersid) {
        this.usersid = usersid;
    }
}
