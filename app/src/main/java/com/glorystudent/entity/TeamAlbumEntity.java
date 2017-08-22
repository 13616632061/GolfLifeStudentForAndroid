package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/26.
 */

public class TeamAlbumEntity {

    /**
     * eventactivityphotolist : [{"eventactivity_id":94,"eventactivity_name":"测试球队活动","eventactivity_begintime":"2017-05-26T00:00:00","photo_url":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/af4983fe3c4612e717f89340d8aa33fe.png","photo_count":1}]
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
    private List<EventactivityphotolistBean> eventactivityphotolist;

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

    public List<EventactivityphotolistBean> getEventactivityphotolist() {
        return eventactivityphotolist;
    }

    public void setEventactivityphotolist(List<EventactivityphotolistBean> eventactivityphotolist) {
        this.eventactivityphotolist = eventactivityphotolist;
    }

    public static class EventactivityphotolistBean {
        /**
         * eventactivity_id : 94
         * eventactivity_name : 测试球队活动
         * eventactivity_begintime : 2017-05-26T00:00:00
         * photo_url : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/af4983fe3c4612e717f89340d8aa33fe.png
         * photo_count : 1
         */

        private int eventactivity_id;
        private String eventactivity_name;
        private String eventactivity_begintime;
        private String photo_url;
        private int photo_count;

        public int getEventactivity_id() {
            return eventactivity_id;
        }

        public void setEventactivity_id(int eventactivity_id) {
            this.eventactivity_id = eventactivity_id;
        }

        public String getEventactivity_name() {
            return eventactivity_name;
        }

        public void setEventactivity_name(String eventactivity_name) {
            this.eventactivity_name = eventactivity_name;
        }

        public String getEventactivity_begintime() {
            return eventactivity_begintime;
        }

        public void setEventactivity_begintime(String eventactivity_begintime) {
            this.eventactivity_begintime = eventactivity_begintime;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public int getPhoto_count() {
            return photo_count;
        }

        public void setPhoto_count(int photo_count) {
            this.photo_count = photo_count;
        }
    }
}
