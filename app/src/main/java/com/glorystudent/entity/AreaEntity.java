package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/14.
 */
public class AreaEntity {

    /**
     * listarea : [{"_AddressID":218,"areaid":235,"addressid":218,"areaname":"正中高尔夫普通区","areaenable":true},{"_AddressID":218,"areaid":237,"addressid":218,"areaname":"易高尔夫00","areaenable":true}]
     * version : 1.1.106
     * datetime : 2017/2/14 15:28:36
     * accesstoken : vJpykCSaib1L2J6Y/jMEyvIbgF3RG5lid1uYTIEFJXZUlFuQ4gvQSwP+eFNbnHvkOIbEe2Zji1G58eJLi7GOYKvFA8NCkjNrEvt3VxFAWwFDv4hRXLbyM95/AWTaGDLJeEgZR4CgsjLGcz8hpeYO5EqwjG1qbsajnOTA4glSrZsqoRH3qE2pgDMmzyasLxFSk+XqOB/2LPLHEy9fZPuAnhab0X4cGGaYqDllBf4FhREV210Ifz47DvMbFu0v92Q8rxHcWPWRS+KpzZvx79Pa928oqwicQCxA6XbLfsiP+4OwuMu5Xci9N2E3oJnPn7OC
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 2
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
    private List<ListareaBean> listarea;

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

    public List<ListareaBean> getListarea() {
        return listarea;
    }

    public void setListarea(List<ListareaBean> listarea) {
        this.listarea = listarea;
    }

    public static class ListareaBean {
        /**
         * _AddressID : 218
         * areaid : 235
         * addressid : 218
         * areaname : 正中高尔夫普通区
         * areaenable : true
         */

        private int _AddressID;
        private int areaid;
        private int addressid;
        private String areaname;
        private boolean areaenable;

        public int get_AddressID() {
            return _AddressID;
        }

        public void set_AddressID(int _AddressID) {
            this._AddressID = _AddressID;
        }

        public int getAreaid() {
            return areaid;
        }

        public void setAreaid(int areaid) {
            this.areaid = areaid;
        }

        public int getAddressid() {
            return addressid;
        }

        public void setAddressid(int addressid) {
            this.addressid = addressid;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public boolean isAreaenable() {
            return areaenable;
        }

        public void setAreaenable(boolean areaenable) {
            this.areaenable = areaenable;
        }
    }
}
