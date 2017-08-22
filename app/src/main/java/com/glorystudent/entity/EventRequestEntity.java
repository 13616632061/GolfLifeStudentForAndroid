package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/10.
 */
public class EventRequestEntity {
    private EventBean event;
    private Integer page;

    public static class EventBean {
        private Integer events_id;
        private String events_name;
        private String events_begintime;
        private String events_endtime;
        private String events_status;
        private String events_type;

        public Integer getEvents_id() {
            return events_id;
        }

        public void setEvents_id(Integer events_id) {
            this.events_id = events_id;
        }

        public String getEvents_name() {
            return events_name;
        }

        public void setEvents_name(String events_name) {
            this.events_name = events_name;
        }

        public String getEvents_begintime() {
            return events_begintime;
        }

        public void setEvents_begintime(String events_begintime) {
            this.events_begintime = events_begintime;
        }

        public String getEvents_endtime() {
            return events_endtime;
        }

        public void setEvents_endtime(String events_endtime) {
            this.events_endtime = events_endtime;
        }

        public String getEvents_status() {
            return events_status;
        }

        public void setEvents_status(String events_status) {
            this.events_status = events_status;
        }

        public String getEvents_type() {
            return events_type;
        }

        public void setEvents_type(String events_type) {
            this.events_type = events_type;
        }
    }

    public EventBean getEvent() {
        return event;
    }

    public void setEvent(EventBean event) {
        this.event = event;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
