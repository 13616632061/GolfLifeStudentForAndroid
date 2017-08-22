package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/3/21.
 */
public class ScoreCardEntity {

    /**
     * listScorecard : [{"scorecard_id":5,"userid":111,"username":"何跃进","coachid":0,"coachname":null,"secoredatetime":"2017-03-16T00:00:00","teetype":"","golfcourseid":0,"golfcoursename":"看看","inname":null,"outname":null,"scorecard_image":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/CoBg87lzza67JwHlppWA%3D%3D/scorecards/49ab65f5c1d86f0a77e8cb5885ea75b0.png","scorecard_status":"","holecount":0,"totalcount":0},{"scorecard_id":4,"userid":84,"username":"张鲁","coachid":0,"coachname":null,"secoredatetime":"2017-01-01T00:00:00","teetype":"","golfcourseid":0,"golfcoursename":"西丽球场","inname":null,"outname":null,"scorecard_image":"http://app.pgagolf.cn/images/files/2017-1-11/148412719687999.png","scorecard_status":"","holecount":88,"totalcount":99}]
     * version : 1.0.3
     * datetime : 2017/3/21 19:26:20
     * accesstoken : /rAYgH5wKlukuhJACB24Kmqmx/o+jC4wFO97s8Zo5F9J1Fi8UnCUt4235rH99ev9+bZaUWrV2b3SsCaR6QMHG7ryXhgAmA817q5VfSRR2f6BTYz0wksxiUeJVNsuNu8Sk6UgEH6DvuS5H7d3sXnNx4WYCyyN9iUffXspKY+kV1uXHUEzVe/b43is9TA/NnNMxAvarHqtUY+O+hfjuLgaC72KLclVbfCJoLwZDiWIHHiwaEkHCPI4EstQbDmapWMeOy94XWZVx5ia2N0xkSBB/QfEIXSz+EmTvGduyhvQ9DVtoP1kGN9R+s9jqp2EAKvHFlF1L70VZsC12gD05xuGqw==
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 2
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
    private List<ListScorecardBean> listScorecard;

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

    public List<ListScorecardBean> getListScorecard() {
        return listScorecard;
    }

    public void setListScorecard(List<ListScorecardBean> listScorecard) {
        this.listScorecard = listScorecard;
    }

    public static class ListScorecardBean {
        /**
         * scorecard_id : 5
         * userid : 111
         * username : 何跃进
         * coachid : 0
         * coachname : null
         * secoredatetime : 2017-03-16T00:00:00
         * teetype :
         * golfcourseid : 0
         * golfcoursename : 看看
         * inname : null
         * outname : null
         * scorecard_image : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/CoBg87lzza67JwHlppWA%3D%3D/scorecards/49ab65f5c1d86f0a77e8cb5885ea75b0.png
         * scorecard_status :
         * holecount : 0
         * totalcount : 0
         */

        private int scorecard_id;
        private int userid;
        private String username;
        private int coachid;
        private Object coachname;
        private String secoredatetime;
        private String teetype;
        private int golfcourseid;
        private String golfcoursename;
        private Object inname;
        private Object outname;
        private String scorecard_image;
        private String scorecard_status;
        private int holecount;
        private int totalcount;

        public int getScorecard_id() {
            return scorecard_id;
        }

        public void setScorecard_id(int scorecard_id) {
            this.scorecard_id = scorecard_id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getCoachid() {
            return coachid;
        }

        public void setCoachid(int coachid) {
            this.coachid = coachid;
        }

        public Object getCoachname() {
            return coachname;
        }

        public void setCoachname(Object coachname) {
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

        public int getGolfcourseid() {
            return golfcourseid;
        }

        public void setGolfcourseid(int golfcourseid) {
            this.golfcourseid = golfcourseid;
        }

        public String getGolfcoursename() {
            return golfcoursename;
        }

        public void setGolfcoursename(String golfcoursename) {
            this.golfcoursename = golfcoursename;
        }

        public Object getInname() {
            return inname;
        }

        public void setInname(Object inname) {
            this.inname = inname;
        }

        public Object getOutname() {
            return outname;
        }

        public void setOutname(Object outname) {
            this.outname = outname;
        }

        public String getScorecard_image() {
            return scorecard_image;
        }

        public void setScorecard_image(String scorecard_image) {
            this.scorecard_image = scorecard_image;
        }

        public String getScorecard_status() {
            return scorecard_status;
        }

        public void setScorecard_status(String scorecard_status) {
            this.scorecard_status = scorecard_status;
        }

        public int getHolecount() {
            return holecount;
        }

        public void setHolecount(int holecount) {
            this.holecount = holecount;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }
    }
}
