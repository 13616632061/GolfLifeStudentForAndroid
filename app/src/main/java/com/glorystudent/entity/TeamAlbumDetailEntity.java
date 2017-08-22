package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/26.
 */

public class TeamAlbumDetailEntity {

    /**
     * photolist : [{"eid":null,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/af4983fe3c4612e717f89340d8aa33fe.png","eventactivity_id":null,"eventactivity_type":null,"user_name":null,"user_id":null,"upload_time":null},{"eid":null,"eventactivity_picpath":"http://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D%2Fcompetitions%2FphotosWalls%2F14957861930.png","eventactivity_id":null,"eventactivity_type":null,"user_name":null,"user_id":null,"upload_time":null},{"eid":null,"eventactivity_picpath":"http://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D%2Fcompetitions%2FphotosWalls%2F14957863150.png","eventactivity_id":null,"eventactivity_type":null,"user_name":null,"user_id":null,"upload_time":null}]
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<PhotolistBean> photolist;

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

    public Object getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(Object totalrownum) {
        this.totalrownum = totalrownum;
    }

    public Object getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(Object totalpagenum) {
        this.totalpagenum = totalpagenum;
    }

    public Object getNowpagenum() {
        return nowpagenum;
    }

    public void setNowpagenum(Object nowpagenum) {
        this.nowpagenum = nowpagenum;
    }

    public Object getPagerownum() {
        return pagerownum;
    }

    public void setPagerownum(Object pagerownum) {
        this.pagerownum = pagerownum;
    }

    public List<PhotolistBean> getPhotolist() {
        return photolist;
    }

    public void setPhotolist(List<PhotolistBean> photolist) {
        this.photolist = photolist;
    }

    public static class PhotolistBean {
        /**
         * eid : null
         * eventactivity_picpath : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/af4983fe3c4612e717f89340d8aa33fe.png
         * eventactivity_id : null
         * eventactivity_type : null
         * user_name : null
         * user_id : null
         * upload_time : null
         */

        private Object eid;
        private String eventactivity_picpath;
        private Object eventactivity_id;
        private Object eventactivity_type;
        private Object user_name;
        private Object user_id;
        private Object upload_time;

        public Object getEid() {
            return eid;
        }

        public void setEid(Object eid) {
            this.eid = eid;
        }

        public String getEventactivity_picpath() {
            return eventactivity_picpath;
        }

        public void setEventactivity_picpath(String eventactivity_picpath) {
            this.eventactivity_picpath = eventactivity_picpath;
        }

        public Object getEventactivity_id() {
            return eventactivity_id;
        }

        public void setEventactivity_id(Object eventactivity_id) {
            this.eventactivity_id = eventactivity_id;
        }

        public Object getEventactivity_type() {
            return eventactivity_type;
        }

        public void setEventactivity_type(Object eventactivity_type) {
            this.eventactivity_type = eventactivity_type;
        }

        public Object getUser_name() {
            return user_name;
        }

        public void setUser_name(Object user_name) {
            this.user_name = user_name;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public Object getUpload_time() {
            return upload_time;
        }

        public void setUpload_time(Object upload_time) {
            this.upload_time = upload_time;
        }
    }
}
