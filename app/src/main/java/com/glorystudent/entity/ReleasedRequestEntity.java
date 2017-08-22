package com.glorystudent.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ReleasedRequestEntity {
    private EventactivityBean eventactivity;

    public ReleasedRequestEntity() {
    }

    public EventactivityBean getEventactivity() {
        return eventactivity;
    }

    public void setEventactivity(EventactivityBean eventactivity) {
        this.eventactivity = eventactivity;
    }

    public static class EventactivityBean {
        private Integer eventActivity_id;
        private Integer eventactivity_publisherid;
        private String eventactivity_name;
        private String eventactivity_begintime;
        private String eventactivity_endtime;
        private String eventactivity_detail;
        private Integer eventactivity_number;
        private String eventactivity_type;
        private String eventactivity_address;
        private Integer eventActivity_AddressID;
        private String longitude;
        private String latitude;
        private String eventactivity_champion;
        private String eventactivity_level;
        private BigDecimal eventactivity_bonus;
        private Boolean eventactivity_ifbringguests;
        private Integer eventactivity_bringguestsnum;
        private String eventactivity_kickofftime;
        private Boolean eventactivity_ifpublicly;
        private String eventactivity_pwd;
        private String eventactivity_costtype;
        private BigDecimal eventactivity_cost;
        private BigDecimal eventactivity_prepayment;
        private BigDecimal eventactivity_guestscost;
        private Integer eventactivity_binarynumber;
        private String eventactivity_signupbegintime;
        private String eventactivity_signupendtime;
        private String eventactivity_organizer;
        private String eventactivity_organizertel;
        private Boolean eventactivity_ifshowsignname;
        private Boolean eventactivity_ifshowphotowall;
        private Integer pga_id;
        private String eventactivity_guestcosttype;
        private BigDecimal eventactivity_guestprepayment;
        private Integer eventactivity_teamid;
        private List<ListeventpicBean> listeventpic;

        public EventactivityBean() {
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

        public Integer getEventActivity_id() {
            return eventActivity_id;
        }

        public void setEventActivity_id(Integer eventActivity_id) {
            this.eventActivity_id = eventActivity_id;
        }

        public BigDecimal getEventactivity_guestprepayment() {
            return eventactivity_guestprepayment;
        }

        public void setEventactivity_guestprepayment(BigDecimal eventactivity_guestprepayment) {
            this.eventactivity_guestprepayment = eventactivity_guestprepayment;
        }

        public String getEventactivity_guestcosttype() {
            return eventactivity_guestcosttype;
        }

        public void setEventactivity_guestcosttype(String eventactivity_guestcosttype) {
            this.eventactivity_guestcosttype = eventactivity_guestcosttype;
        }

        public String getEventactivity_name() {
            return eventactivity_name;
        }

        public void setEventactivity_name(String eventactivity_name) {
            this.eventactivity_name = eventactivity_name;
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

        public String getEventactivity_detail() {
            return eventactivity_detail;
        }

        public void setEventactivity_detail(String eventactivity_detail) {
            this.eventactivity_detail = eventactivity_detail;
        }

        public Integer getEventactivity_number() {
            return eventactivity_number;
        }

        public void setEventactivity_number(Integer eventactivity_number) {
            this.eventactivity_number = eventactivity_number;
        }

        public String getEventactivity_type() {
            return eventactivity_type;
        }

        public void setEventactivity_type(String eventactivity_type) {
            this.eventactivity_type = eventactivity_type;
        }

        public Integer getEventActivity_AddressID() {
            return eventActivity_AddressID;
        }

        public void setEventActivity_AddressID(Integer eventActivity_AddressID) {
            this.eventActivity_AddressID = eventActivity_AddressID;
        }

        public String getEventactivity_address() {
            return eventactivity_address;
        }

        public void setEventactivity_address(String eventactivity_address) {
            this.eventactivity_address = eventactivity_address;
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

        public String getEventactivity_champion() {
            return eventactivity_champion;
        }

        public void setEventactivity_champion(String eventactivity_champion) {
            this.eventactivity_champion = eventactivity_champion;
        }

        public String getEventactivity_level() {
            return eventactivity_level;
        }

        public void setEventactivity_level(String eventactivity_level) {
            this.eventactivity_level = eventactivity_level;
        }

        public BigDecimal getEventactivity_bonus() {
            return eventactivity_bonus;
        }

        public void setEventactivity_bonus(BigDecimal eventactivity_bonus) {
            this.eventactivity_bonus = eventactivity_bonus;
        }

        public Boolean getEventactivity_ifbringguests() {
            return eventactivity_ifbringguests;
        }

        public void setEventactivity_ifbringguests(Boolean eventactivity_ifbringguests) {
            this.eventactivity_ifbringguests = eventactivity_ifbringguests;
        }

        public Integer getEventactivity_bringguestsnum() {
            return eventactivity_bringguestsnum;
        }

        public void setEventactivity_bringguestsnum(Integer eventactivity_bringguestsnum) {
            this.eventactivity_bringguestsnum = eventactivity_bringguestsnum;
        }

        public String getEventactivity_kickofftime() {
            return eventactivity_kickofftime;
        }

        public void setEventactivity_kickofftime(String eventactivity_kickofftime) {
            this.eventactivity_kickofftime = eventactivity_kickofftime;
        }

        public Boolean getEventactivity_ifpublicly() {
            return eventactivity_ifpublicly;
        }

        public void setEventactivity_ifpublicly(Boolean eventactivity_ifpublicly) {
            this.eventactivity_ifpublicly = eventactivity_ifpublicly;
        }

        public String getEventactivity_pwd() {
            return eventactivity_pwd;
        }

        public void setEventactivity_pwd(String eventactivity_pwd) {
            this.eventactivity_pwd = eventactivity_pwd;
        }

        public String getEventactivity_costtype() {
            return eventactivity_costtype;
        }

        public void setEventactivity_costtype(String eventactivity_costtype) {
            this.eventactivity_costtype = eventactivity_costtype;
        }

        public BigDecimal getEventactivity_cost() {
            return eventactivity_cost;
        }

        public void setEventactivity_cost(BigDecimal eventactivity_cost) {
            this.eventactivity_cost = eventactivity_cost;
        }

        public BigDecimal getEventactivity_prepayment() {
            return eventactivity_prepayment;
        }

        public void setEventactivity_prepayment(BigDecimal eventactivity_prepayment) {
            this.eventactivity_prepayment = eventactivity_prepayment;
        }

        public BigDecimal getEventactivity_guestscost() {
            return eventactivity_guestscost;
        }

        public void setEventactivity_guestscost(BigDecimal eventactivity_guestscost) {
            this.eventactivity_guestscost = eventactivity_guestscost;
        }

        public Integer getEventactivity_binarynumber() {
            return eventactivity_binarynumber;
        }

        public void setEventactivity_binarynumber(Integer eventactivity_binarynumber) {
            this.eventactivity_binarynumber = eventactivity_binarynumber;
        }

        public String getEventactivity_signupbegintime() {
            return eventactivity_signupbegintime;
        }

        public void setEventactivity_signupbegintime(String eventactivity_signupbegintime) {
            this.eventactivity_signupbegintime = eventactivity_signupbegintime;
        }

        public String getEventactivity_signupendtime() {
            return eventactivity_signupendtime;
        }

        public void setEventactivity_signupendtime(String eventactivity_signupendtime) {
            this.eventactivity_signupendtime = eventactivity_signupendtime;
        }

        public String getEventactivity_organizer() {
            return eventactivity_organizer;
        }

        public void setEventactivity_organizer(String eventactivity_organizer) {
            this.eventactivity_organizer = eventactivity_organizer;
        }

        public String getEventactivity_organizertel() {
            return eventactivity_organizertel;
        }

        public void setEventactivity_organizertel(String eventactivity_organizertel) {
            this.eventactivity_organizertel = eventactivity_organizertel;
        }

        public Boolean getEventactivity_ifshowsignname() {
            return eventactivity_ifshowsignname;
        }

        public void setEventactivity_ifshowsignname(Boolean eventactivity_ifshowsignname) {
            this.eventactivity_ifshowsignname = eventactivity_ifshowsignname;
        }

        public Boolean getEventactivity_ifshowphotowall() {
            return eventactivity_ifshowphotowall;
        }

        public void setEventactivity_ifshowphotowall(Boolean eventactivity_ifshowphotowall) {
            this.eventactivity_ifshowphotowall = eventactivity_ifshowphotowall;
        }

        public Integer getPga_id() {
            return pga_id;
        }

        public void setPga_id(Integer pga_id) {
            this.pga_id = pga_id;
        }

        public List<ListeventpicBean> getListeventpic() {
            return listeventpic;
        }

        public void setListeventpic(List<ListeventpicBean> listeventpic) {
            this.listeventpic = listeventpic;
        }

        public static class ListeventpicBean {
            private Integer eventactivity_id;
            private String eventactivity_picpath;
            private String eventactivity_type;
            private String upload_time;

            public ListeventpicBean() {
            }

            public Integer getEventactivity_id() {
                return eventactivity_id;
            }

            public void setEventactivity_id(Integer eventactivity_id) {
                this.eventactivity_id = eventactivity_id;
            }

            public String getEventactivity_picpath() {
                return eventactivity_picpath;
            }

            public void setEventactivity_picpath(String eventactivity_picpath) {
                this.eventactivity_picpath = eventactivity_picpath;
            }

            public String getEventactivity_type() {
                return eventactivity_type;
            }

            public void setEventactivity_type(String eventactivity_type) {
                this.eventactivity_type = eventactivity_type;
            }

            public String getUpload_time() {
                return upload_time;
            }

            public void setUpload_time(String upload_time) {
                this.upload_time = upload_time;
            }

        }
    }
}