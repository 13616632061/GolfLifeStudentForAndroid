package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2016/12/20.
 */
public class VideoEntity implements Serializable{

    /**
     * listTeachingVideo : [{"teachingvideo_id":1,"teachingvideo_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/470ca2b052e8aead67ad6d4520a82276.mp4","teachingvideo_tittle":"xcv","teachingvideo_teacher":"xasf","teachingvideo_level":"入门视频","teachingvideo_price":0,"teachingvideo_picture":"https://192.168.26.248:4431/images/newsfiles/2016-12-21/glof(110101).jpg","teachingvideo_context":"cjhiusdhui","teachingvideo_createtime":"2016-10-11T00:00:00","teachingvideo_length":null},{"teachingvideo_id":3,"teachingvideo_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/470ca2b052e8aead67ad6d4520a82276.mp4","teachingvideo_tittle":"xcv","teachingvideo_teacher":"xasf","teachingvideo_level":"入门视频","teachingvideo_price":20,"teachingvideo_picture":"https://192.168.26.248:4431/images/newsfiles/2016-12-8/12453784314962jij5yczpc.jpg","teachingvideo_context":"cjhiusdhui","teachingvideo_createtime":"2016-10-11T00:10:00","teachingvideo_length":null},{"teachingvideo_id":15,"teachingvideo_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/470ca2b052e8aead67ad6d4520a82276.mp4","teachingvideo_tittle":"xcv","teachingvideo_teacher":"xasf","teachingvideo_level":"入门视频","teachingvideo_price":20,"teachingvideo_picture":"https://192.168.26.248:4431/images/newsfiles/2016-12-8/12453784314962jij5yczpc.jpg","teachingvideo_context":"cjhiusdhui","teachingvideo_createtime":"2016-10-11T00:10:00","teachingvideo_length":null}]
     * listvideocollect : [{"_UserID":154,"_CollectObjectID":3,"_CollectType":5,"collectid":56,"userid":154,"collectobjectid":3,"collecttype":5,"collectdate":"2017-01-03T00:00:00","collecturl":"http://honor1.oss-cn-shenzhen.aliyuncs.com/470ca2b052e8aead67ad6d4520a82276.mp4","collecttag":"5-3","collecttitle":"xcv","collectpicurl":"https://192.168.26.248:4431/images/newsfiles/2016-12-8/12453784314962jij5yczpc.jpg"}]
     * version : 1.1.106
     * datetime : 2017/1/3 14:03:19
     * accesstoken : QqmGIuJnU8sblqZV7FG93dlHzBbKFVz5Apk2b2h0du0Yz7B6aAxB5qT+cLEEKMVIZyQNwlLbtUcNwh83Sfbt+G2FwuAErX0NKnbG52N0Arqtobo885aAcuwNd0BRFL3ycHTZiAn8UNhNZXKd6IW/WwJBmuqsqAgbL41k4GrzkOIJBgza3jiT8AYB1eizTLif6NF+EnA+RKSN52/bAeYHQtrxH9MfXrzlZyd08wYu9Ufu+G3sGh7uP+Qi1y3DOikNJqGf2lt2O8xyxAbI0LT5YqByhwoMrjdgXbKj9oSQbYP88v3He+n42BmMcZG8ZPbL
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 3
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 10
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
    private List<ListTeachingVideoBean> listTeachingVideo;
    private List<ListvideocollectBean> listvideocollect;

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

    public List<ListTeachingVideoBean> getListTeachingVideo() {
        return listTeachingVideo;
    }

    public void setListTeachingVideo(List<ListTeachingVideoBean> listTeachingVideo) {
        this.listTeachingVideo = listTeachingVideo;
    }

    public List<ListvideocollectBean> getListvideocollect() {
        return listvideocollect;
    }

    public void setListvideocollect(List<ListvideocollectBean> listvideocollect) {
        this.listvideocollect = listvideocollect;
    }

    public static class ListTeachingVideoBean implements Serializable{
        /**
         * teachingvideo_id : 1
         * teachingvideo_path : http://honor1.oss-cn-shenzhen.aliyuncs.com/470ca2b052e8aead67ad6d4520a82276.mp4
         * teachingvideo_tittle : xcv
         * teachingvideo_teacher : xasf
         * teachingvideo_level : 入门视频
         * teachingvideo_price : 0.0
         * teachingvideo_picture : https://192.168.26.248:4431/images/newsfiles/2016-12-21/glof(110101).jpg
         * teachingvideo_context : cjhiusdhui
         * teachingvideo_createtime : 2016-10-11T00:00:00
         * teachingvideo_length : null
         */

        private int teachingvideo_id;
        private String teachingvideo_path;
        private String teachingvideo_tittle;
        private String teachingvideo_teacher;
        private String teachingvideo_level;
        private double teachingvideo_price;
        private String teachingvideo_picture;
        private String teachingvideo_context;
        private String teachingvideo_createtime;
        private Object teachingvideo_length;

        public int getTeachingvideo_id() {
            return teachingvideo_id;
        }

        public void setTeachingvideo_id(int teachingvideo_id) {
            this.teachingvideo_id = teachingvideo_id;
        }

        public String getTeachingvideo_path() {
            return teachingvideo_path;
        }

        public void setTeachingvideo_path(String teachingvideo_path) {
            this.teachingvideo_path = teachingvideo_path;
        }

        public String getTeachingvideo_tittle() {
            return teachingvideo_tittle;
        }

        public void setTeachingvideo_tittle(String teachingvideo_tittle) {
            this.teachingvideo_tittle = teachingvideo_tittle;
        }

        public String getTeachingvideo_teacher() {
            return teachingvideo_teacher;
        }

        public void setTeachingvideo_teacher(String teachingvideo_teacher) {
            this.teachingvideo_teacher = teachingvideo_teacher;
        }

        public String getTeachingvideo_level() {
            return teachingvideo_level;
        }

        public void setTeachingvideo_level(String teachingvideo_level) {
            this.teachingvideo_level = teachingvideo_level;
        }

        public double getTeachingvideo_price() {
            return teachingvideo_price;
        }

        public void setTeachingvideo_price(double teachingvideo_price) {
            this.teachingvideo_price = teachingvideo_price;
        }

        public String getTeachingvideo_picture() {
            return teachingvideo_picture;
        }

        public void setTeachingvideo_picture(String teachingvideo_picture) {
            this.teachingvideo_picture = teachingvideo_picture;
        }

        public String getTeachingvideo_context() {
            return teachingvideo_context;
        }

        public void setTeachingvideo_context(String teachingvideo_context) {
            this.teachingvideo_context = teachingvideo_context;
        }

        public String getTeachingvideo_createtime() {
            return teachingvideo_createtime;
        }

        public void setTeachingvideo_createtime(String teachingvideo_createtime) {
            this.teachingvideo_createtime = teachingvideo_createtime;
        }

        public Object getTeachingvideo_length() {
            return teachingvideo_length;
        }

        public void setTeachingvideo_length(Object teachingvideo_length) {
            this.teachingvideo_length = teachingvideo_length;
        }
    }

    public static class ListvideocollectBean implements Serializable{
        /**
         * _UserID : 154
         * _CollectObjectID : 3
         * _CollectType : 5
         * collectid : 56
         * userid : 154
         * collectobjectid : 3
         * collecttype : 5
         * collectdate : 2017-01-03T00:00:00
         * collecturl : http://honor1.oss-cn-shenzhen.aliyuncs.com/470ca2b052e8aead67ad6d4520a82276.mp4
         * collecttag : 5-3
         * collecttitle : xcv
         * collectpicurl : https://192.168.26.248:4431/images/newsfiles/2016-12-8/12453784314962jij5yczpc.jpg
         */

        private int _UserID;
        private int _CollectObjectID;
        private int _CollectType;
        private int collectid;
        private int userid;
        private int collectobjectid;
        private int collecttype;
        private String collectdate;
        private String collecturl;
        private String collecttag;
        private String collecttitle;
        private String collectpicurl;

        public int get_UserID() {
            return _UserID;
        }

        public void set_UserID(int _UserID) {
            this._UserID = _UserID;
        }

        public int get_CollectObjectID() {
            return _CollectObjectID;
        }

        public void set_CollectObjectID(int _CollectObjectID) {
            this._CollectObjectID = _CollectObjectID;
        }

        public int get_CollectType() {
            return _CollectType;
        }

        public void set_CollectType(int _CollectType) {
            this._CollectType = _CollectType;
        }

        public int getCollectid() {
            return collectid;
        }

        public void setCollectid(int collectid) {
            this.collectid = collectid;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getCollectobjectid() {
            return collectobjectid;
        }

        public void setCollectobjectid(int collectobjectid) {
            this.collectobjectid = collectobjectid;
        }

        public int getCollecttype() {
            return collecttype;
        }

        public void setCollecttype(int collecttype) {
            this.collecttype = collecttype;
        }

        public String getCollectdate() {
            return collectdate;
        }

        public void setCollectdate(String collectdate) {
            this.collectdate = collectdate;
        }

        public String getCollecturl() {
            return collecturl;
        }

        public void setCollecturl(String collecturl) {
            this.collecturl = collecturl;
        }

        public String getCollecttag() {
            return collecttag;
        }

        public void setCollecttag(String collecttag) {
            this.collecttag = collecttag;
        }

        public String getCollecttitle() {
            return collecttitle;
        }

        public void setCollecttitle(String collecttitle) {
            this.collecttitle = collecttitle;
        }

        public String getCollectpicurl() {
            return collectpicurl;
        }

        public void setCollectpicurl(String collectpicurl) {
            this.collectpicurl = collectpicurl;
        }
    }
}
