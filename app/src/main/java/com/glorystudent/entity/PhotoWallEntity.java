package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/11.
 */

public class PhotoWallEntity {

    /**
     * picwallid : null
     * picwallPath : null
     * eventPicWallList : [{"eid":502,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/dfa5723d5a330759e512f4e82000812f.png","eventactivity_id":66,"eventactivity_type":"2","user_name":"叶与影","user_id":1217,"upload_time":"2017-05-11T18:00:49"},{"eid":501,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/534b209880d6e604004353855de88252.png","eventactivity_id":66,"eventactivity_type":"2","user_name":"叶与影","user_id":1217,"upload_time":"2017-05-11T17:58:09"},{"eid":500,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/1bd322225f5dffd2f860a0cf319872e.png","eventactivity_id":66,"eventactivity_type":"2","user_name":"叶与影","user_id":1217,"upload_time":"2017-05-11T17:43:43"},{"eid":499,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/e9a7306e3be223ec4eff4bb1f38ad0bb.png","eventactivity_id":66,"eventactivity_type":"2","user_name":"叶与影","user_id":1217,"upload_time":"2017-05-11T17:43:10"},{"eid":497,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/e9a499f2a126aaba2b7d5dac4cf1cb12.png","eventactivity_id":66,"eventactivity_type":"2","user_name":"叶与影","user_id":1217,"upload_time":"2017-05-11T17:37:12"}]
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

    private Object picwallid;
    private Object picwallPath;
    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<EventPicWallListBean> eventPicWallList;

    public Object getPicwallid() {
        return picwallid;
    }

    public void setPicwallid(Object picwallid) {
        this.picwallid = picwallid;
    }

    public Object getPicwallPath() {
        return picwallPath;
    }

    public void setPicwallPath(Object picwallPath) {
        this.picwallPath = picwallPath;
    }

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

    public List<EventPicWallListBean> getEventPicWallList() {
        return eventPicWallList;
    }

    public void setEventPicWallList(List<EventPicWallListBean> eventPicWallList) {
        this.eventPicWallList = eventPicWallList;
    }

    public static class EventPicWallListBean {
        /**
         * eid : 502
         * eventactivity_picpath : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/dfa5723d5a330759e512f4e82000812f.png
         * eventactivity_id : 66
         * eventactivity_type : 2
         * user_name : 叶与影
         * user_id : 1217
         * upload_time : 2017-05-11T18:00:49
         */

        private int eid;
        private String eventactivity_picpath;
        private int eventactivity_id;
        private String eventactivity_type;
        private String user_name;
        private int user_id;
        private String upload_time;

        public int getEid() {
            return eid;
        }

        public void setEid(int eid) {
            this.eid = eid;
        }

        public String getEventactivity_picpath() {
            return eventactivity_picpath;
        }

        public void setEventactivity_picpath(String eventactivity_picpath) {
            this.eventactivity_picpath = eventactivity_picpath;
        }

        public int getEventactivity_id() {
            return eventactivity_id;
        }

        public void setEventactivity_id(int eventactivity_id) {
            this.eventactivity_id = eventactivity_id;
        }

        public String getEventactivity_type() {
            return eventactivity_type;
        }

        public void setEventactivity_type(String eventactivity_type) {
            this.eventactivity_type = eventactivity_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUpload_time() {
            return upload_time;
        }

        public void setUpload_time(String upload_time) {
            this.upload_time = upload_time;
        }
    }
}
