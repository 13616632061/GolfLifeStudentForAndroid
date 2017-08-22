package com.glorystudent.entity;

/**
 * Created by hyj on 2017/3/22.
 */
public class ScorecardDetailRequestEntity {
    private ScorecardDetailBean scorecardDetail;
    public static class ScorecardDetailBean{
        private Integer scorecarddetail_id;
        private Integer scorecard_id;
        private Integer holenumber;
        private Integer par;
        private Integer score;
        private Integer putts;
        private Integer distance;

        public Integer getScorecarddetail_id() {
            return scorecarddetail_id;
        }

        public void setScorecarddetail_id(Integer scorecarddetail_id) {
            this.scorecarddetail_id = scorecarddetail_id;
        }

        public Integer getScorecard_id() {
            return scorecard_id;
        }

        public void setScorecard_id(Integer scorecard_id) {
            this.scorecard_id = scorecard_id;
        }

        public Integer getHolenumber() {
            return holenumber;
        }

        public void setHolenumber(Integer holenumber) {
            this.holenumber = holenumber;
        }

        public Integer getPar() {
            return par;
        }

        public void setPar(Integer par) {
            this.par = par;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getPutts() {
            return putts;
        }

        public void setPutts(Integer putts) {
            this.putts = putts;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }
    }

    public ScorecardDetailBean getScorecardDetail() {
        return scorecardDetail;
    }

    public void setScorecardDetail(ScorecardDetailBean scorecardDetail) {
        this.scorecardDetail = scorecardDetail;
    }
}
