package com.glorystudent.entity;

/**
 * Created by Administrator on 2017/4/1.
 */

public class CompetityRequestEntity {
    private EventactivityBean eventactivity;
    private int page;

    public EventactivityBean getEventactivity() {
        return eventactivity;
    }

    public void setEventactivity(EventactivityBean eventactivity) {
        this.eventactivity = eventactivity;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static class EventactivityBean {
        private Integer eventActivity_id;
        private String eventactivity_name;
        private String eventactivity_type;
        private String eventactivity_costtype;
        private String eventactivity_begintime;
        private String eventactivity_endtime;
        private Integer eventactivity_state;
        private Integer eventactivity_publisherid;
        private String longitude;
        private String latitude;
        private Integer eventactivity_teamid;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public Integer getEventActivity_id() {
            return eventActivity_id;
        }

        public void setEventActivity_id(Integer eventActivity_id) {
            this.eventActivity_id = eventActivity_id;
        }

        public String getEventactivity_name() {
            return eventactivity_name;
        }

        public void setEventactivity_name(String eventactivity_name) {
            this.eventactivity_name = eventactivity_name;
        }

        public String getEventactivity_type() {
            return eventactivity_type;
        }

        public void setEventactivity_type(String eventactivity_type) {
            this.eventactivity_type = eventactivity_type;
        }

        public String getEventactivity_costtype() {
            return eventactivity_costtype;
        }

        public void setEventactivity_costtype(String eventactivity_costtype) {
            this.eventactivity_costtype = eventactivity_costtype;
        }

        public String getEventactivity_begintime() {
            return eventactivity_begintime;
        }

        public void setEventactivity_begintime(String eventactivity_begintime) {
            this.eventactivity_begintime = eventactivity_begintime;
        }

        public String getEventactivity_endtime() {
            return eventactivity_endtime;
        }

        public void setEventactivity_endtime(String eventactivity_endtime) {
            this.eventactivity_endtime = eventactivity_endtime;
        }

        public Integer getEventactivity_state() {
            return eventactivity_state;
        }

        public void setEventactivity_state(Integer eventactivity_state) {
            this.eventactivity_state = eventactivity_state;
        }

        public Integer getEventactivity_publisherid() {
            return eventactivity_publisherid;
        }

        public void setEventactivity_publisherid(Integer eventactivity_publisherid) {
            this.eventactivity_publisherid = eventactivity_publisherid;
        }

        public Integer getEventactivity_teamid() {
            return eventactivity_teamid;
        }

        public void setEventactivity_teamid(Integer eventactivity_teamid) {
            this.eventactivity_teamid = eventactivity_teamid;
        }
    }
}
