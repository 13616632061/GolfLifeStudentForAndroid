package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/6.
 */
public class ApplyFriendsEntity {
    private ApplyFriendsBean applyfriends;
    private Integer applycategory;
    private String username;
    private String usertel;
    private Integer page;

    public static class ApplyFriendsBean {
        private Integer applyfriends_id;
        private String applyfriends_type;
        private Integer applyuserid;
        private Integer answeruserid;
        private String applyremark;
        private String applystatus;
        private String refuseremark;
        private String applytype;

        public Integer getApplyfriends_id() {
            return applyfriends_id;
        }

        public void setApplyfriends_id(Integer applyfriends_id) {
            this.applyfriends_id = applyfriends_id;
        }

        public String getApplyfriends_type() {
            return applyfriends_type;
        }

        public void setApplyfriends_type(String applyfriends_type) {
            this.applyfriends_type = applyfriends_type;
        }

        public Integer getApplyuserid() {
            return applyuserid;
        }

        public void setApplyuserid(Integer applyuserid) {
            this.applyuserid = applyuserid;
        }

        public Integer getAnsweruserid() {
            return answeruserid;
        }

        public void setAnsweruserid(Integer answeruserid) {
            this.answeruserid = answeruserid;
        }

        public String getApplyremark() {
            return applyremark;
        }

        public void setApplyremark(String applyremark) {
            this.applyremark = applyremark;
        }

        public String getApplystatus() {
            return applystatus;
        }

        public void setApplystatus(String applystatus) {
            this.applystatus = applystatus;
        }

        public String getRefuseremark() {
            return refuseremark;
        }

        public void setRefuseremark(String refuseremark) {
            this.refuseremark = refuseremark;
        }

        public String getApplytype() {
            return applytype;
        }

        public void setApplytype(String applytype) {
            this.applytype = applytype;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ApplyFriendsBean getApplyfriends() {
        return applyfriends;
    }

    public void setApplyfriends(ApplyFriendsBean applyfriends) {
        this.applyfriends = applyfriends;
    }

    public Integer getApplycategory() {
        return applycategory;
    }

    public void setApplycategory(Integer applycategory) {
        this.applycategory = applycategory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertel() {
        return usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }
}
