package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/3/22.
 */
public class ScorecardDetailEntity {
    /**
     * listScorecardDetail : [{"scorecarddetail_id":2,"scorecard_id":4,"holenumber":1,"par":2,"score":2,"putts":1,"distance":1},{"scorecarddetail_id":3,"scorecard_id":4,"holenumber":2,"par":1,"score":5,"putts":0,"distance":1},{"scorecarddetail_id":4,"scorecard_id":4,"holenumber":3,"par":2,"score":8,"putts":1,"distance":0},{"scorecarddetail_id":5,"scorecard_id":4,"holenumber":4,"par":5,"score":4,"putts":1,"distance":0},{"scorecarddetail_id":6,"scorecard_id":4,"holenumber":5,"par":6,"score":1,"putts":1,"distance":0},{"scorecarddetail_id":7,"scorecard_id":4,"holenumber":6,"par":8,"score":3,"putts":1,"distance":1},{"scorecarddetail_id":8,"scorecard_id":4,"holenumber":7,"par":9,"score":6,"putts":1,"distance":1},{"scorecarddetail_id":9,"scorecard_id":4,"holenumber":8,"par":7,"score":9,"putts":1,"distance":1},{"scorecarddetail_id":10,"scorecard_id":4,"holenumber":9,"par":3,"score":8,"putts":0,"distance":1}]
     * version : 1.1.106
     * datetime : 2017/3/22 10:39:26
     * accesstoken : QwmMbG0f58T2ikXFq1NfU8ytAcf4rFVkt1ApuMq8CNJ9iMY/JkDl83XKdPu10OXTCDKQ9MTgg44pN9EmvQAIQBIYLAj2sLJyR5/9A4VvvTg5MpL3IH9iqqeQkOC6xyQOTi4xJDhVbEArLerPcRIDNkuYSSrjSgLmQoCdXB2i5ffdkDeaKqW//W0WYrudnM1vrGPyq8Y2jhYHHPu2d5P5rjNVNz5IuNUzpmBoWKeFsYOCyhbSPiFgPexu01o78e1pnwKVMbp1EWm6JFACAsCGR9maJ6WJMpN6xYw4bS3YHWD+8l3vv8uKXt4glhGEzFSZ
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 9
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListScorecardDetailBean> listScorecardDetail;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public String getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(String totalrownum) {
        this.totalrownum = totalrownum;
    }

    public String getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(String totalpagenum) {
        this.totalpagenum = totalpagenum;
    }

    public Object getNowpagenum() {
        return nowpagenum;
    }

    public void setNowpagenum(Object nowpagenum) {
        this.nowpagenum = nowpagenum;
    }

    public String getPagerownum() {
        return pagerownum;
    }

    public void setPagerownum(String pagerownum) {
        this.pagerownum = pagerownum;
    }

    public List<ListScorecardDetailBean> getListScorecardDetail() {
        return listScorecardDetail;
    }

    public void setListScorecardDetail(List<ListScorecardDetailBean> listScorecardDetail) {
        this.listScorecardDetail = listScorecardDetail;
    }

    public static class ListScorecardDetailBean {
        /**
         * scorecarddetail_id : 2
         * scorecard_id : 4
         * holenumber : 1
         * par : 2
         * score : 2
         * putts : 1
         * distance : 1
         */

        private int scorecarddetail_id;
        private int scorecard_id;
        private int holenumber;
        private int par;
        private int score;
        private int putts;
        private int distance;

        public int getScorecarddetail_id() {
            return scorecarddetail_id;
        }

        public void setScorecarddetail_id(int scorecarddetail_id) {
            this.scorecarddetail_id = scorecarddetail_id;
        }

        public int getScorecard_id() {
            return scorecard_id;
        }

        public void setScorecard_id(int scorecard_id) {
            this.scorecard_id = scorecard_id;
        }

        public int getHolenumber() {
            return holenumber;
        }

        public void setHolenumber(int holenumber) {
            this.holenumber = holenumber;
        }

        public int getPar() {
            return par;
        }

        public void setPar(int par) {
            this.par = par;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getPutts() {
            return putts;
        }

        public void setPutts(int putts) {
            this.putts = putts;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
