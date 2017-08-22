package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/16.
 */
public class TimeDefineEntity {

    /**
     * listtimedefinition : [{"timedefinitionid":4,"timesection":"9:00-10:00","binarynumber":1},{"timedefinitionid":6,"timesection":"10:00-11:00","binarynumber":2},{"timedefinitionid":8,"timesection":"11:00-12:00","binarynumber":4},{"timedefinitionid":12,"timesection":"13:00-14:00","binarynumber":8},{"timedefinitionid":13,"timesection":"14:00-15:00","binarynumber":16},{"timedefinitionid":14,"timesection":"15:00-16:00","binarynumber":32},{"timedefinitionid":15,"timesection":"16:00-17:00","binarynumber":64}]
     * version : 1.0.3
     * datetime : 2017/2/16 15:14:48
     * accesstoken : QwmMbG0f58T2ikXFq1NfUyMGtHvF7P5kRroxKWPfNmy8TAFzYb83qnYbHXt6Z+edQ7F22F2japJQ/TqINb5cau5az1K+Z2Vgh50Ko4AzDSqWrjNBRg4iHGvZFejeNv/3q/b5OjV71VF6FR9/g96M3RmEG+6LwW5wJl3q5MDwPHAIcNLU7e569E+NmLy4tN2AmBg0+g3IQRy9pIhFdLwMf/Ap7IfMwug9TgtYzASCpJ0IbW7sfQ+0VstB6n6otuzvSFOkCfTZDliGBBnsUn82xw9sb1IG85AbX9uYFSpadw10ca7ZdCsC/T7DKp5FeYET
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 7
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
    private List<ListtimedefinitionBean> listtimedefinition;


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

    public List<ListtimedefinitionBean> getListtimedefinition() {
        return listtimedefinition;
    }

    public void setListtimedefinition(List<ListtimedefinitionBean> listtimedefinition) {
        this.listtimedefinition = listtimedefinition;
    }

    public static class ListtimedefinitionBean {
        /**
         * timedefinitionid : 4
         * timesection : 9:00-10:00
         * binarynumber : 1
         */
        private TimedefinitionBean timedefinitionBean;
        private int binarynumber;

        public TimedefinitionBean getTimedefinitionBean() {
            return timedefinitionBean;
        }

        public void setTimedefinitionBean(TimedefinitionBean timedefinitionBean) {
            this.timedefinitionBean = timedefinitionBean;
        }

        public int getBinarynumber() {
            return binarynumber;
        }

        public void setBinarynumber(int binarynumber) {
            this.binarynumber = binarynumber;
        }

        public static class TimedefinitionBean {

            private int timedefinitionid;
            private String timesection;
            private int binarynumber;
            private AppointmentListEntity.ListAppointmentBean listAppointmentBean;

            public AppointmentListEntity.ListAppointmentBean getListAppointmentBean() {
                return listAppointmentBean;
            }

            public void setListAppointmentBean(AppointmentListEntity.ListAppointmentBean listAppointmentBean) {
                this.listAppointmentBean = listAppointmentBean;
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

            public int getBinarynumber() {
                return binarynumber;
            }

            public void setBinarynumber(int binarynumber) {
                this.binarynumber = binarynumber;
            }

            @Override
            public String toString() {
                return "ListtimedefinitionBean{" +
                        "timedefinitionid=" + timedefinitionid +
                        ", timesection='" + timesection + '\'' +
                        ", binarynumber=" + binarynumber +
                        ", listAppointmentBean=" + listAppointmentBean +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "TimeDefineEntity{" +
                "listtimedefinition=" + listtimedefinition +
                '}';
    }
}
