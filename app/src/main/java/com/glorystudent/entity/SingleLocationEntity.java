package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/18.
 */
public class SingleLocationEntity {
    private CourtBean court;
    private Integer page;

    public CourtBean getCourt() {
        return court;
    }

    public void setCourt(CourtBean court) {
        this.court = court;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public static class CourtBean {
        private String court_longitude;
        private String court_latitude;
        private String court_address;
        private String court_name;
        private String court_city;

        public String getCourt_address() {
            return court_address;
        }

        public void setCourt_address(String court_address) {
            this.court_address = court_address;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
        }

        public String getCourt_city() {
            return court_city;
        }

        public void setCourt_city(String court_city) {
            this.court_city = court_city;
        }

        public String getCourt_longitude() {
            return court_longitude;
        }

        public void setCourt_longitude(String court_longitude) {
            this.court_longitude = court_longitude;
        }

        public String getCourt_latitude() {
            return court_latitude;
        }

        public void setCourt_latitude(String court_latitude) {
            this.court_latitude = court_latitude;
        }
    }
}
