package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/9.
 */
public class CollectVideoEntity {

    /**
     * listCollect : [{"_UserID":111,"_CollectObjectID":39,"_CollectType":5,"collectid":1091,"userid":111,"collectobjectid":39,"collecttype":5,"collectdate":"2017-02-08T00:00:00","collecturl":"https://honor1.oss-cn-shenzhen.aliyuncs.com/15.ReverseSpineAngle%E8%84%8A%E6%9F%B1%E8%A7%92%E5%BA%A6%E9%80%86%E8%BD%AC.mp4","collecttag":"5-39","collecttitle":"脊柱角度逆转","collectpicurl":"https://app.pgagolf.cn/images/files/2016-11-24/video.png"},{"_UserID":111,"_CollectObjectID":7,"_CollectType":5,"collectid":1092,"userid":111,"collectobjectid":7,"collecttype":5,"collectdate":"2017-02-09T00:00:00","collecturl":"https://honor1.oss-cn-shenzhen.aliyuncs.com/1.IntrototheBodySwingConnection%E8%BA%AB%E4%BD%93%E4%B8%8E%E6%8C%A5%E6%9D%86%E7%9A%84%E7%B4%A7%E5%AF%86%E8%81%94%E7%B3%BB%E4%BB%8B%E7%BB%8D.mp4","collecttag":"5-7","collecttitle":"身体与挥杆的紧密联系介绍","collectpicurl":"https://app.pgagolf.cn/images/files/2016-11-24/video.png"},{"_UserID":111,"_CollectObjectID":18,"_CollectType":5,"collectid":1093,"userid":111,"collectobjectid":18,"collecttype":5,"collectdate":"2017-02-09T00:00:00","collecturl":"https://honor1.oss-cn-shenzhen.aliyuncs.com/4.Introto3DMotionCapture%E4%B8%89%E7%BB%B4%E6%8D%95%E6%8D%89%E7%B3%BB%E7%BB%9F%E4%BB%8B%E7%BB%8D.mp4","collecttag":"5-18","collecttitle":"三维捕捉系统介绍","collectpicurl":"https://app.pgagolf.cn/images/files/2016-11-24/video.png"},{"_UserID":111,"_CollectObjectID":15,"_CollectType":5,"collectid":1094,"userid":111,"collectobjectid":15,"collecttype":5,"collectdate":"2017-02-09T00:00:00","collecturl":"https://honor1.oss-cn-shenzhen.aliyuncs.com/3.TPIsSwingPhilosophyTPI%E7%9A%84%E6%8C%A5%E6%9D%86%E7%90%86%E5%BF%B5.mp4","collecttag":"5-15","collecttitle":"挥杆理念","collectpicurl":"https://app.pgagolf.cn/images/files/2016-11-24/video.png"}]
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 4
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListCollectBean> listCollect;

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getDatetime() {
        return datetime;
    }

    public void setDatetime(Object datetime) {
        this.datetime = datetime;
    }

    public Object getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(Object accesstoken) {
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

    public List<ListCollectBean> getListCollect() {
        return listCollect;
    }

    public void setListCollect(List<ListCollectBean> listCollect) {
        this.listCollect = listCollect;
    }

    public static class ListCollectBean {
        /**
         * _UserID : 111
         * _CollectObjectID : 39
         * _CollectType : 5
         * collectid : 1091
         * userid : 111
         * collectobjectid : 39
         * collecttype : 5
         * collectdate : 2017-02-08T00:00:00
         * collecturl : https://honor1.oss-cn-shenzhen.aliyuncs.com/15.ReverseSpineAngle%E8%84%8A%E6%9F%B1%E8%A7%92%E5%BA%A6%E9%80%86%E8%BD%AC.mp4
         * collecttag : 5-39
         * collecttitle : 脊柱角度逆转
         * collectpicurl : https://app.pgagolf.cn/images/files/2016-11-24/video.png
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
