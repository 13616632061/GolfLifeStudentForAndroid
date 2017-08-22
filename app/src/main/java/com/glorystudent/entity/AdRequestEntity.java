package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/4.
 */
public class AdRequestEntity {
    private AdBean ad;

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public static class AdBean {
        private Integer ad_id;
        private String ad_tittle;
        private String ad_type;
        private String ad_enable;
        private String ad_datetime;
        private String ad_deadline;
        private String ad_location;

        public String getAd_location() {
            return ad_location;
        }

        public void setAd_location(String ad_location) {
            this.ad_location = ad_location;
        }

        public Integer getAd_id() {
            return ad_id;
        }

        public void setAd_id(Integer ad_id) {
            this.ad_id = ad_id;
        }

        public String getAd_tittle() {
            return ad_tittle;
        }

        public void setAd_tittle(String ad_tittle) {
            this.ad_tittle = ad_tittle;
        }

        public String getAd_type() {
            return ad_type;
        }

        public void setAd_type(String ad_type) {
            this.ad_type = ad_type;
        }

        public String getAd_enable() {
            return ad_enable;
        }

        public void setAd_enable(String ad_enable) {
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
