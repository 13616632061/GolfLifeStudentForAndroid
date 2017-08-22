package com.glorystudent.entity;

import java.util.List;

/**
 * 视频评论实体类
 * Created by hyj on 2016/12/30.
 */
public class VideoReviewEntity {

    /**
     * listTVideoComment : [{"comment_id":26,"comment_userid":154,"comment_time":"2016-12-30T00:00:00","comment_context":"我的天呀","comment_status":"0","comment_tvideoid":3,"comment_username":"用户18219140","comment_customerphoto":null,"comment_praise":"1"}]
     * listtvideocommentlike : [{"lid":15,"comment_tvideoid":26,"userid":154,"username":null,"createdatetime":"2016-12-30T17:10:58"}]
     * version : 1.1.106
     * datetime : 2016/12/30 17:11:56
     * accesstoken : QqmGIuJnU8sblqZV7FG93Vkgc3O6Gelh9b2DlDsX0Y+NB9lJBmczeHm989T+2MATXlWBFs56YMnBrbGbYwetJCLSyYOaPMZ0j9LdNSq9egNAexBbUsxlDjjToeIcgJbfIXItWAbQ91ruInSuW/04R4BTYHg6A2Cqa5ALUuvHr5G0pJorLAyhef2IwcSJOpiLkCnE0MptPiyuQgM1yVU4kWFxu9fWfVBONAoLReX5zdeAB19GfnjPN5x9w13CbQTdtMcvLscVKtCXLKUeFN7o1kJnXS5WeCEwQh0HHOQBN9kNbIhrGXK3chQ0oxKhSRgA
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 1
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
    private List<ListTVideoCommentBean> listTVideoComment;
    private List<ListtvideocommentlikeBean> listtvideocommentlike;

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

    public List<ListTVideoCommentBean> getListTVideoComment() {
        return listTVideoComment;
    }

    public void setListTVideoComment(List<ListTVideoCommentBean> listTVideoComment) {
        this.listTVideoComment = listTVideoComment;
    }

    public List<ListtvideocommentlikeBean> getListtvideocommentlike() {
        return listtvideocommentlike;
    }

    public void setListtvideocommentlike(List<ListtvideocommentlikeBean> listtvideocommentlike) {
        this.listtvideocommentlike = listtvideocommentlike;
    }

    public static class ListTVideoCommentBean {
        /**
         * comment_id : 26
         * comment_userid : 154
         * comment_time : 2016-12-30T00:00:00
         * comment_context : 我的天呀
         * comment_status : 0
         * comment_tvideoid : 3
         * comment_username : 用户18219140
         * comment_customerphoto : null
         * comment_praise : 1
         */

        private int comment_id;
        private int comment_userid;
        private String comment_time;
        private String comment_context;
        private String comment_status;
        private int comment_tvideoid;
        private String comment_username;
        private Object comment_customerphoto;
        private String comment_praise;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getComment_userid() {
            return comment_userid;
        }

        public void setComment_userid(int comment_userid) {
            this.comment_userid = comment_userid;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getComment_context() {
            return comment_context;
        }

        public void setComment_context(String comment_context) {
            this.comment_context = comment_context;
        }

        public String getComment_status() {
            return comment_status;
        }

        public void setComment_status(String comment_status) {
            this.comment_status = comment_status;
        }

        public int getComment_tvideoid() {
            return comment_tvideoid;
        }

        public void setComment_tvideoid(int comment_tvideoid) {
            this.comment_tvideoid = comment_tvideoid;
        }

        public String getComment_username() {
            return comment_username;
        }

        public void setComment_username(String comment_username) {
            this.comment_username = comment_username;
        }

        public Object getComment_customerphoto() {
            return comment_customerphoto;
        }

        public void setComment_customerphoto(Object comment_customerphoto) {
            this.comment_customerphoto = comment_customerphoto;
        }

        public String getComment_praise() {
            return comment_praise;
        }

        public void setComment_praise(String comment_praise) {
            this.comment_praise = comment_praise;
        }
    }

    public static class ListtvideocommentlikeBean {
        /**
         * lid : 15
         * comment_tvideoid : 26
         * userid : 154
         * username : null
         * createdatetime : 2016-12-30T17:10:58
         */

        private int lid;
        private int comment_tvideoid;
        private int userid;
        private Object username;
        private String createdatetime;

        public int getLid() {
            return lid;
        }

        public void setLid(int lid) {
            this.lid = lid;
        }

        public int getComment_tvideoid() {
            return comment_tvideoid;
        }

        public void setComment_tvideoid(int comment_tvideoid) {
            this.comment_tvideoid = comment_tvideoid;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public String getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(String createdatetime) {
            this.createdatetime = createdatetime;
        }
    }
}
