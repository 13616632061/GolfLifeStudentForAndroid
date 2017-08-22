package com.glorystudent.entity;

/**
 * Created by hyj on 2017/3/21.
 */
public class ScoreCardRequestEntity {
    private ScoreCardBean scorecard;
    public static class ScoreCardBean{
        private Integer scorecard_id;
        private Integer userid;
        private String username;
        private Integer coachid;
        private String coachname;
        private String secoredatetime;
        private String teetype;
        private String golfcourseid;
        private String golfcoursename;
        private String inname;
        private String outname;
        private String scorecard_status;
        private String scorecard_image;

        public Integer getScorecard_id() {
            return scorecard_id;
        }

        public void setScorecard_id(Integer scorecard_id) {
            this.scorecard_id = scorecard_id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getCoachid() {
            return coachid;
        }

        public void setCoachid(Integer coachid) {
            this.coachid = coachid;
        }

        public String getCoachname() {
            return coachname;
        }

        public void setCoachname(String coachname) {
            this.coachname = coachname;
        }

        public String getSecoredatetime() {
            return secoredatetime;
        }

        public void setSecoredatetime(String secoredatetime) {
            this.secoredatetime = secoredatetime;
        }

        public String getTeetype() {
            return teetype;
        }

        public void setTeetype(String teetype) {
            this.teetype = teetype;
        }

        public String getGolfcourseid() {
            return golfcourseid;
        }

        public void setGolfcourseid(String golfcourseid) {
            this.golfcourseid = golfcourseid;
        }

        public String getGolfcoursename() {
            return golfcoursename;
        }

        public void setGolfcoursename(String golfcoursename) {
            this.golfcoursename = golfcoursename;
        }

        public String getInname() {
            return inname;
        }

        public void setInname(String inname) {
            this.inname = inname;
        }

        public String getOutname() {
            return outname;
        }

        public void setOutname(String outname) {
            this.outname = outname;
        }

        public String getScorecard_status() {
            return scorecard_status;
        }

        public void setScorecard_status(String scorecard_status) {
            this.scorecard_status = scorecard_status;
        }

        public String getScorecard_image() {
            return scorecard_image;
        }

        public void setScorecard_image(String scorecard_image) {
            this.scorecard_image = scorecard_image;
        }
    }

    public ScoreCardBean getScorecard() {
        return scorecard;
    }

    public void setScorecard(ScoreCardBean scorecard) {
        this.scorecard = scorecard;
    }
}
