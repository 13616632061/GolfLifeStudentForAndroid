package com.glorystudent.entity;

/**
 * Created by Gavin.J on 2017/5/24.
 */

public class TeamRequestEntity {
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
        private String summary;
        private Integer regionid;
        private String regionText;
        private String contacts;
        private String telphone;
        private Integer status;
        private Integer captainid;
        private String createdate;

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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCaptainid() {
            return captainid;
        }

        public void setCaptainid(Integer captainid) {
            this.captainid = captainid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
