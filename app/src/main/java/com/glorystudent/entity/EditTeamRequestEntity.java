package com.glorystudent.entity;

/**
 * Created by Gavin.J on 2017/6/7.
 */

public class EditTeamRequestEntity {
    private TeamBean team;

    public TeamBean getTeam() {
        return team;
    }

    public void setTeam(TeamBean team) {
        this.team = team;
    }

    public static class TeamBean {
        private Integer id;
        private String logo;
        private String pic;
        private String title;
        private Integer captainid;
        private Integer regionid;
        private String regionText;
        private String createdate;
        private String summary;
        private Integer createby;
        private Integer status;
        private String telphone;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getCaptainid() {
            return captainid;
        }

        public void setCaptainid(Integer captainid) {
            this.captainid = captainid;
        }

        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getRegionText() {
            return regionText;
        }

        public void setRegionText(String regionText) {
            this.regionText = regionText;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Integer getCreateby() {
            return createby;
        }

        public void setCreateby(Integer createby) {
            this.createby = createby;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }
    }
}
