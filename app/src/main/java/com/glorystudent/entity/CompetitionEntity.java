package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2016/12/21.
 */
public class CompetitionEntity implements Serializable{

    /**
     * list_Event : [{"events_id":52,"events_name":"大师公开赛","events_begintime":"2016-12-08T05:00:00","events_endtime":"2016-12-09T00:00:00","events_logo":"https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(698763646).jpg","events_status":"1","events_detail":"  新浪体育讯　　随着时间推移，最热的时间已经过去，天气变得渐渐凉爽起来。天高气爽，正是来石老人高尔夫打一场球的好时候！石老人高尔夫在8月份隆重推出三大赛事，价格优惠，天气凉爽，海风拂面，让球友们得以尽兴挥杆！","events_type":"1","coachgroupname":null,"events_picture":"https://192.168.26.248:4431/images/headfiles/2016-12-21/glof(52000).jpg,https://192.168.26.248:4431/images/headfiles/2016-12-21/glof(110101).jpg,https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(698763646).jpg","events_number":60,"events_address":"宝安"},{"events_id":65,"events_name":"trt","events_begintime":"2016-12-12T00:00:00","events_endtime":"2016-12-29T05:00:00","events_logo":"https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(4).jpg","events_status":"0","events_detail":"  新浪体育讯　　北京时间12月21日消息，随着女子欧巡资格学校考试最后一关第四轮在摩洛哥萨玛娜高尔夫俱乐部(Samanah Golf Club)落幕，厦门姑娘刘艳争取参赛资格的希望只剩下18个洞就能实现了。","events_type":"2","coachgroupname":null,"events_picture":"https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(8).jpg,https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(98700).jpg,https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(4).jpg","events_number":800,"events_address":"5"}]
     * eventurl : https://192.168.26.248:4431/home/eventsDetail?id=
     * version : 1.1.106
     * datetime : 2016/12/21 15:05:42
     * accesstoken : RQitshDwnRTAulf2GXndzc5Kfcdt4/nI60lQQvJt/TEIoCCx+agayPCN7S7hk770u3Ii6hq0hNrsxwXSpZpVnYVADj8xIdDjFotISii0Xhy7zf2GMAskcEL4MnQNld3o1K1bmuUqA+hdukdft/z0BEFSyVItj8lN/YbWu9yIZd5cRereojr1IPAvekRcOnR2aqgUOpsGL7DtIS5Fj5ZarITtwXxiwBeSfzEP0lSpF+ln8rB8spTTZYsldC1juNVXIeCUk1XlxbTyKvyZo9QOeN2uJbX0f86jUG7jY5aSc0rEGwzSPqjSXTTKAQlPQeru
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 2
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private String eventurl;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListEventBean> list_Event;

    public String getEventurl() {
        return eventurl;
    }

    public void setEventurl(String eventurl) {
        this.eventurl = eventurl;
    }

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

    public List<ListEventBean> getList_Event() {
        return list_Event;
    }

    public void setList_Event(List<ListEventBean> list_Event) {
        this.list_Event = list_Event;
    }

    public static class ListEventBean implements Serializable {
        /**
         * events_id : 52
         * events_name : 大师公开赛
         * events_begintime : 2016-12-08T05:00:00
         * events_endtime : 2016-12-09T00:00:00
         * events_logo : https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(698763646).jpg
         * events_status : 1
         * events_detail :   新浪体育讯　　随着时间推移，最热的时间已经过去，天气变得渐渐凉爽起来。天高气爽，正是来石老人高尔夫打一场球的好时候！石老人高尔夫在8月份隆重推出三大赛事，价格优惠，天气凉爽，海风拂面，让球友们得以尽兴挥杆！
         * events_type : 1
         * coachgroupname : null
         * events_picture : https://192.168.26.248:4431/images/headfiles/2016-12-21/glof(52000).jpg,https://192.168.26.248:4431/images/headfiles/2016-12-21/glof(110101).jpg,https://192.168.26.248:4431/images/headfiles/2016-12-21/golf(698763646).jpg
         * events_number : 60
         * events_address : 宝安
         */

        private int events_id;
        private String events_name;
        private String events_begintime;
        private String events_endtime;
        private String events_logo;
        private String events_status;
        private String events_detail;
        private String events_type;
        private Object coachgroupname;
        private String events_picture;
        private int events_number;
        private String events_address;

        public int getEvents_id() {
            return events_id;
        }

        public void setEvents_id(int events_id) {
            this.events_id = events_id;
        }

        public String getEvents_name() {
            return events_name;
        }

        public void setEvents_name(String events_name) {
            this.events_name = events_name;
        }

        public String getEvents_begintime() {
            return events_begintime;
        }

        public void setEvents_begintime(String events_begintime) {
            this.events_begintime = events_begintime;
        }

        public String getEvents_endtime() {
            return events_endtime;
        }

        public void setEvents_endtime(String events_endtime) {
            this.events_endtime = events_endtime;
        }

        public String getEvents_logo() {
            return events_logo;
        }

        public void setEvents_logo(String events_logo) {
            this.events_logo = events_logo;
        }

        public String getEvents_status() {
            return events_status;
        }

        public void setEvents_status(String events_status) {
            this.events_status = events_status;
        }

        public String getEvents_detail() {
            return events_detail;
        }

        public void setEvents_detail(String events_detail) {
            this.events_detail = events_detail;
        }

        public String getEvents_type() {
            return events_type;
        }

        public void setEvents_type(String events_type) {
            this.events_type = events_type;
        }

        public Object getCoachgroupname() {
            return coachgroupname;
        }

        public void setCoachgroupname(Object coachgroupname) {
            this.coachgroupname = coachgroupname;
        }

        public String getEvents_picture() {
            return events_picture;
        }

        public void setEvents_picture(String events_picture) {
            this.events_picture = events_picture;
        }

        public int getEvents_number() {
            return events_number;
        }

        public void setEvents_number(int events_number) {
            this.events_number = events_number;
        }

        public String getEvents_address() {
            return events_address;
        }

        public void setEvents_address(String events_address) {
            this.events_address = events_address;
        }
    }
}
