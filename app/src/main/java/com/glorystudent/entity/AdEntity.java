package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2016/12/20.
 */
public class AdEntity {

    /**
     * listad : [{"ad_id":5,"ad_tittle":"222","ad_link":"","ad_type":"1","ad_picture":"https://192.168.26.248/images/headfiles/2016-11-26/golf(513).jpg","ad_video":"5555","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-02-02T00:00:00"},{"ad_id":6,"ad_tittle":"22","ad_link":"","ad_type":"2","ad_picture":"https://192.168.26.248/images/headfiles/2016-11-26/glof(60313).jpg","ad_video":"666","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-09-22T00:00:00"},{"ad_id":7,"ad_tittle":"33","ad_link":"","ad_type":"1","ad_picture":"https://192.168.26.248/images/headfiles/2016-11-26/golf(513).jpg","ad_video":"3","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-10-27T00:00:00"},{"ad_id":8,"ad_tittle":"44","ad_link":"","ad_type":"2","ad_picture":"https://192.168.26.248/images/headfiles/2016-11-26/glof(60313).jpg","ad_video":"33333","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-10-08T00:00:00"},{"ad_id":9,"ad_tittle":"444","ad_link":"","ad_type":"1","ad_picture":"https://192.168.26.248/images/headfiles/2016-11-26/golf(513).jpg","ad_video":"qwqqwq","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-10-27T00:00:00"},{"ad_id":10,"ad_tittle":"666","ad_link":"","ad_type":"2","ad_picture":"333","ad_video":"333","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-10-08T00:00:00"},{"ad_id":12,"ad_tittle":"夏天","ad_link":"","ad_type":"1","ad_picture":"https://192.168.26.248/images/headfiles/2016-11-26/golf(513).jpg","ad_video":"fyygj","ad_enable":false,"ad_datetime":"2016-10-27T00:00:00","ad_deadline":"2016-10-31T00:00:00"}]
     * version : 1.1.106
     * datetime : 2016/12/20 9:56:16
     * accesstoken : jzJTb/l5dFWTBFM8yuONDubDMs1k5XGpJMbvji2Bifw84Oz64Hn2vSmDVXFW+jUfOW53MsuU+2Z72b2YdHXkWeDebT9Gel3sP6XPPGeHokWqH4bB4HeHHHNkVShWITO+SqlQdbbTRTCgu4HmHM0IOjRuiHq56EofzuFDRd6gPml8b/r5PEL8+osfRy4kCZtjLrHRR8IFRX0uCrPkYA3rTiDRPJ/hoVNBCCvAoGcB2UMlurud8/MOa+xVHbogOCcFpIRO+eTsZjyiDLB/icGNXxgoRdJde5bAyh3Dj8YxxQaVHwFESegLQm+UlJZyC3ro
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
    private List<ListadBean> listad;

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

    public List<ListadBean> getListad() {
        return listad;
    }

    public void setListad(List<ListadBean> listad) {
        this.listad = listad;
    }

    public static class ListadBean {
        /**
         * ad_id : 5
         * ad_tittle : 222
         * ad_link :
         * ad_type : 1
         * ad_picture : https://192.168.26.248/images/headfiles/2016-11-26/golf(513).jpg
         * ad_video : 5555
         * ad_enable : false
         * ad_datetime : 2016-10-27T00:00:00
         * ad_deadline : 2016-02-02T00:00:00
         */

        private int ad_id;
        private String ad_tittle;
        private String ad_link;
        private String ad_type;
        private String ad_picture;
        private String ad_video;
        private boolean ad_enable;
        private String ad_datetime;
        private String ad_deadline;

        public int getAd_id() {
            return ad_id;
        }

        public void setAd_id(int ad_id) {
            this.ad_id = ad_id;
        }

        public String getAd_tittle() {
            return ad_tittle;
        }

        public void setAd_tittle(String ad_tittle) {
            this.ad_tittle = ad_tittle;
        }

        public String getAd_link() {
            return ad_link;
        }

        public void setAd_link(String ad_link) {
            this.ad_link = ad_link;
        }

        public String getAd_type() {
            return ad_type;
        }

        public void setAd_type(String ad_type) {
            this.ad_type = ad_type;
        }

        public String getAd_picture() {
            return ad_picture;
        }

        public void setAd_picture(String ad_picture) {
            this.ad_picture = ad_picture;
        }

        public String getAd_video() {
            return ad_video;
        }

        public void setAd_video(String ad_video) {
            this.ad_video = ad_video;
        }

        public boolean isAd_enable() {
            return ad_enable;
        }

        public void setAd_enable(boolean ad_enable) {
            this.ad_enable = ad_enable;
        }

        public String getAd_datetime() {
            return ad_datetime;
        }

        public void setAd_datetime(String ad_datetime) {
            this.ad_datetime = ad_datetime;
        }

        public String getAd_deadline() {
            return ad_deadline;
        }

        public void setAd_deadline(String ad_deadline) {
            this.ad_deadline = ad_deadline;
        }
    }
}
