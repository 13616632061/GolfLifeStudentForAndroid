package com.glorystudent.entity;

/**
 * Created by Gavin.J on 2017/5/23.
 */

public class AddTeamRequestEntity {
    private ApplyTeamUserBean applyTeamUser;

    public ApplyTeamUserBean getApplyTeamUser() {
        return applyTeamUser;
    }

    public void setApplyTeamUser(ApplyTeamUserBean applyTeamUser) {
        this.applyTeamUser = applyTeamUser;
    }


    public static class ApplyTeamUserBean {
        private Integer teamId;
        private String name;
        private String telphone;
        private String handicap;
        private char sex;

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getHandicap() {
            return handicap;
        }

        public void setHandicap(String handicap) {
            this.handicap = handicap;
        }

        public char getSex() {
            return sex;
        }

        public void setSex(char sex) {
            this.sex = sex;
        }
    }
}
