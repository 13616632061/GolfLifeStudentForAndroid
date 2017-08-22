package com.glorystudent.entity;

/**
 * Created by Gavin.J on 2017/5/24.
 */

public class ApplyTeamRequestEntity {
    private ApplyTeamBean applyTeam;

    public ApplyTeamBean getApplyTeam() {
        return applyTeam;
    }

    public void setApplyTeam(ApplyTeamBean applyTeam) {
        this.applyTeam = applyTeam;
    }

    public static class ApplyTeamBean {
        private String logo;
        private String pic;
        private String title;
        private Integer regionid;
        private String regionText;
        private String summary;
        private String contacts;
        private String telphone;
        private Integer status;
        private String createdate;

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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
    }
}
