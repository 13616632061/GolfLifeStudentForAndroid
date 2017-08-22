package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/14.
 */
public class PositionEntity {

    /**
     * listpositions : [{"positionsid":215,"positionslocation":"96.102","positionsname":"打位1","positionsstatus":"1","areaid":235,"positionsnumber":9},{"positionsid":216,"positionslocation":"102.36","positionsname":"打位2","positionsstatus":"1","areaid":235,"positionsnumber":90},{"positionsid":217,"positionslocation":"77.46","positionsname":"打位3","positionsstatus":"0","areaid":235,"positionsnumber":3},{"positionsid":218,"positionslocation":"96.102","positionsname":"打位4","positionsstatus":"0","areaid":235,"positionsnumber":4}]
     * version : 1.1.106
     * datetime : 2017/2/14 15:45:14
     * accesstoken : vJpykCSaib1L2J6Y/jMEyvIbgF3RG5lid1uYTIEFJXZUlFuQ4gvQSwP+eFNbnHvkOIbEe2Zji1G58eJLi7GOYKvFA8NCkjNrEvt3VxFAWwFDv4hRXLbyM95/AWTaGDLJeEgZR4CgsjLGcz8hpeYO5EqwjG1qbsajnOTA4glSrZsqoRH3qE2pgDMmzyasLxFSk+XqOB/2LPLHEy9fZPuAnhab0X4cGGaYqDllBf4FhREV210Ifz47DvMbFu0v92Q8rxHcWPWRS+KpzZvx79Pa928oqwicQCxA6XbLfsiP+4OwuMu5Xci9N2E3oJnPn7OC
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 4
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
    private List<ListpositionsBean> listpositions;

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

    public List<ListpositionsBean> getListpositions() {
        return listpositions;
    }

    public void setListpositions(List<ListpositionsBean> listpositions) {
        this.listpositions = listpositions;
    }

    public static class ListpositionsBean {
        /**
         * positionsid : 215
         * positionslocation : 96.102
         * positionsname : 打位1
         * positionsstatus : 1
         * areaid : 235
         * positionsnumber : 9
         */

        private int positionsid;
        private String positionslocation;
        private String positionsname;
        private String positionsstatus;
        private int areaid;
        private int positionsnumber;

        public int getPositionsid() {
            return positionsid;
        }

        public void setPositionsid(int positionsid) {
            this.positionsid = positionsid;
        }

        public String getPositionslocation() {
            return positionslocation;
        }

        public void setPositionslocation(String positionslocation) {
            this.positionslocation = positionslocation;
        }

        public String getPositionsname() {
            return positionsname;
        }

        public void setPositionsname(String positionsname) {
            this.positionsname = positionsname;
        }

        public String getPositionsstatus() {
            return positionsstatus;
        }

        public void setPositionsstatus(String positionsstatus) {
            this.positionsstatus = positionsstatus;
        }

        public int getAreaid() {
            return areaid;
        }

        public void setAreaid(int areaid) {
            this.areaid = areaid;
        }

        public int getPositionsnumber() {
            return positionsnumber;
        }

        public void setPositionsnumber(int positionsnumber) {
            this.positionsnumber = positionsnumber;
        }
    }
}
