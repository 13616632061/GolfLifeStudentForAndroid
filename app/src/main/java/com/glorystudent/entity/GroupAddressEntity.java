package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/14.
 */
public class GroupAddressEntity {

    /**
     * listgroupaddress : [{"addressid":218,"coachgroupid":2,"addressname":"广东省深圳市龙岗区","detailaddress":"广东省深圳市龙岗区正中高尔夫俱乐部","longitude":"112.39806","latitude":"30.368236","addressenable":true,"coachgroupname":"正中高尔夫俱乐部","addresspicture":"https://www.pgagolf.cn/images/newsfiles/2016-11-26/glof(1).jpg"},{"addressid":235,"coachgroupid":2,"addressname":"广东省深圳市","detailaddress":"广东省, 深圳市, 福田区益田路,6003号号","longitude":"114.062585","latitude":"22.548802","addressenable":true,"coachgroupname":"广东省深圳市龙岗区正中高尔夫俱乐部","addresspicture":null}]
     * version : 1.1.106
     * datetime : 2017/2/14 15:16:29
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
    private List<ListgroupaddressBean> listgroupaddress;

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

    public List<ListgroupaddressBean> getListgroupaddress() {
        return listgroupaddress;
    }

    public void setListgroupaddress(List<ListgroupaddressBean> listgroupaddress) {
        this.listgroupaddress = listgroupaddress;
    }

    public static class ListgroupaddressBean {
        /**
         * addressid : 218
         * coachgroupid : 2
         * addressname : 广东省深圳市龙岗区
         * detailaddress : 广东省深圳市龙岗区正中高尔夫俱乐部
         * longitude : 112.39806
         * latitude : 30.368236
         * addressenable : true
         * coachgroupname : 正中高尔夫俱乐部
         * addresspicture : https://www.pgagolf.cn/images/newsfiles/2016-11-26/glof(1).jpg
         */

        private int addressid;
        private int coachgroupid;
        private String addressname;
        private String detailaddress;
        private String longitude;
        private String latitude;
        private boolean addressenable;
        private String coachgroupname;
        private String addresspicture;

        public int getAddressid() {
            return addressid;
        }

        public void setAddressid(int addressid) {
            this.addressid = addressid;
        }

        public int getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(int coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public String getAddressname() {
            return addressname;
        }

        public void setAddressname(String addressname) {
            this.addressname = addressname;
        }

        public String getDetailaddress() {
            return detailaddress;
        }

        public void setDetailaddress(String detailaddress) {
            this.detailaddress = detailaddress;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public boolean isAddressenable() {
            return addressenable;
        }

        public void setAddressenable(boolean addressenable) {
            this.addressenable = addressenable;
        }

        public String getCoachgroupname() {
            return coachgroupname;
        }

        public void setCoachgroupname(String coachgroupname) {
            this.coachgroupname = coachgroupname;
        }

        public String getAddresspicture() {
            return addresspicture;
        }

        public void setAddresspicture(String addresspicture) {
            this.addresspicture = addresspicture;
        }
    }
}
