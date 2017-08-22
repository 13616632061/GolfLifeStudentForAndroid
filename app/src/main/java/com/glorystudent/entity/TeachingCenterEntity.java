package com.glorystudent.entity;

import java.util.List;

/**
 * 所属教学中心
 * Created by hyj on 2017/1/9.
 */
public class TeachingCenterEntity {

    /**
     * listgroupaddress : [{"addressid":218,"coachgroupid":2,"addressname":"广东省深圳市龙岗区","detailaddress":"广东省深圳市龙岗区正中高尔夫俱乐部","longitude":"112.39806","latitude":"30.368236","addressenable":true,"coachgroupname":"正中高尔夫俱乐部","addresspicture":"https://www.pgagolf.cn/images/newsfiles/2016-11-26/glof(1).jpg"}]
     * version : 1.1.106
     * datetime : 2017/1/9 20:06:29
     * accesstoken : jT3SQVXQqI7TjBcGo39ieJ9xyqzwXaPON1LnNr2QMdRBAFzwR128o2PRBmE4fBN4+JPJ0G4DEuyBBkHCUPpHI74KuBtzZiChrIHlwnIXgdeLlVjwvc/worWVSuDJXzqBY0j/Yj9PDVHii2Xb1fTrrsKxxpOtQGDTzMKrZXII/oOj00ZopuyeXfW9u2BgoJa5C11gtIxPBJCNwtDtkgZKNgwoPXbn12M/aL2c5mGBKJ7I8Uk8zZvkdskNyfPBhtp3eguBMwZ8e3tzvC5yoqjYgtsQ1u5j+GmZgLowAlBnQr1chsr3nOLe7FZSFW1ZsE/v
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 1
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
