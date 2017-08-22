package com.glorystudent.entity;

import java.util.List;

/**
 * 赛事信息
 * Created by hyj on 2017/1/11.
 */
public class EventInformationEntity {

    /**
     * list_Event : [{"events_id":3,"events_name":"2016现代汽车中国女子公开赛","events_begintime":"2016-12-16T00:00:00","events_endtime":"2016-12-18T00:00:00","events_logo":"https://www.pgagolf.cn/images/files/2016-12-10/3.jpg","events_status":"1","events_detail":"该赛事为女子中巡与女子韩巡联合认证的比赛，从2006年开始至今已经有9个年头，是中国国内举办时间最久的国际女子职业高尔夫比赛。","events_type":"1","coachgroupname":"","events_picture":"https://www.pgagolf.cn/images/files/2016-12-10/3.jpg","events_number":10,"events_address":"西丽社区坪山路8号","events_champion":"金孝周","events_level":"职业","events_bonus":"＄550,000","pga_id":0,"isCollect":null},{"events_id":4,"events_name":"2016年现代汽车·中国女子公开赛资格赛","events_begintime":"2016-12-12T00:00:00","events_endtime":"2016-12-12T00:00:00","events_logo":"https://www.pgagolf.cn/images/files/2016-12-10/4.jpg","events_status":"1","events_detail":"该赛事为女子中巡与女子韩巡联合认证的比赛，从2006年开始至今已经有9个年头，是中国国内举办时间最久的国际女子职业高尔夫比赛。","events_type":"1","coachgroupname":"","events_picture":"https://www.pgagolf.cn/images/files/2016-12-10/4.jpg","events_number":10,"events_address":"西丽社区坪山路8号","events_champion":"黄靖崔慧珍","events_level":"职业","events_bonus":"","pga_id":0,"isCollect":null},{"events_id":5,"events_name":"2016海南公开赛暨国际业余锦标赛","events_begintime":"2016-12-05T00:00:00","events_endtime":"2016-12-09T00:00:00","events_logo":"https://www.pgagolf.cn/images/files/2016-12-10/5.jpg","events_status":"1","events_detail":"由海南省人民政府、国家体育总局小球运动管理中心和中国高尔夫球协会主办，省文体厅和省高尔夫球协会协办。比赛日期12月5日-9日。","events_type":"1","coachgroupname":"","events_picture":"https://www.pgagolf.cn/images/files/2016-12-10/5.jpg","events_number":10,"events_address":"西丽街道学府路1号","events_champion":"韩雨廷 王梅杏","events_level":"业余","events_bonus":"","pga_id":0,"isCollect":null},{"events_id":109,"events_name":"2016现代汽车中国女子公开赛   ","events_begintime":"2016-12-16T00:00:00","events_endtime":"2016-12-18T00:00:00","events_logo":"","events_status":"2","events_detail":"该赛事为女子中巡与女子韩巡联合认证的比赛，从2006年开始至今已经有9个年头，是中国国内举办时间最久的国际女子职业高尔夫比赛。","events_type":"1","coachgroupname":"","events_picture":"","events_number":-1,"events_address":"","events_champion":"金孝周","events_level":"职业","events_bonus":"＄550,000","pga_id":216,"isCollect":null},{"events_id":110,"events_name":"2016年现代汽车·中国女子公开赛资格赛","events_begintime":"2016-12-12T00:00:00","events_endtime":"2016-12-12T00:00:00","events_logo":"","events_status":"2","events_detail":"该赛事为女子中巡与女子韩巡联合认证的比赛，从2006年开始至今已经有9个年头，是中国国内举办时间最久的国际女子职业高尔夫比赛。","events_type":"1","coachgroupname":"","events_picture":"","events_number":-1,"events_address":"","events_champion":"黄靖崔慧珍","events_level":"职业","events_bonus":"","pga_id":153,"isCollect":null},{"events_id":111,"events_name":"2016海南公开赛暨国际业余锦标赛","events_begintime":"2016-12-05T00:00:00","events_endtime":"2016-12-09T00:00:00","events_logo":"","events_status":"2","events_detail":"由海南省人民政府、国家体育总局小球运动管理中心和中国高尔夫球协会主办，省文体厅和省高尔夫球协会协办。比赛日期12月5日-9日。","events_type":"1","coachgroupname":"","events_picture":"","events_number":-1,"events_address":"","events_champion":"韩雨廷 王梅杏","events_level":"业余","events_bonus":"","pga_id":254,"isCollect":null}]
     * eventurl : https://192.168.26.248:4431/home/eventsDetail?id=
     * eventsurl : null
     * version : 1.1.106
     * datetime : 2017/1/11 8:57:26
     * accesstoken : jT3SQVXQqI7TjBcGo39ieC05GxWrbwx/8F3sru81GdLpWmX76IAIfoZYh0/CCACUpb2TSAseouIKSELcjNTkifDqCpUdOGi5VR44krZV1S5YvoT3fSZ1gE4o35BM54WNnnu4gxxpmKJx0Vt3jBZrMg49Ww6h85Zb0rb77N9QudJAdQypCum1PkLlkAbKoCkfhgokBiXAXoc77Lzr5npK5A+DMal6WjrQNq9wrpJpbjU5relx1yoSrcWCtlKZRMbrq2tiv3CLX0k+q9VHA9ksN/2dUs8wlugpnNC93ooRGAMJxyQ9qxw8VvUKYr3/5EwP
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 6
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private String eventurl;
    private Object eventsurl;
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

    public Object getEventsurl() {
        return eventsurl;
    }

    public void setEventsurl(Object eventsurl) {
        this.eventsurl = eventsurl;
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

    public static class ListEventBean {
        /**
         * events_id : 3
         * events_name : 2016现代汽车中国女子公开赛
         * events_begintime : 2016-12-16T00:00:00
         * events_endtime : 2016-12-18T00:00:00
         * events_logo : https://www.pgagolf.cn/images/files/2016-12-10/3.jpg
         * events_status : 1
         * events_detail : 该赛事为女子中巡与女子韩巡联合认证的比赛，从2006年开始至今已经有9个年头，是中国国内举办时间最久的国际女子职业高尔夫比赛。
         * events_type : 1
         * coachgroupname :
         * events_picture : https://www.pgagolf.cn/images/files/2016-12-10/3.jpg
         * events_number : 10
         * events_address : 西丽社区坪山路8号
         * events_champion : 金孝周
         * events_level : 职业
         * events_bonus : ＄550,000
         * pga_id : 0
         * isCollect : null
         */

        private int events_id;
        private String events_name;
        private String events_begintime;
        private String events_endtime;
        private String events_logo;
        private String events_status;
        private String events_detail;
        private String events_type;
        private String coachgroupname;
        private String events_picture;
        private int events_number;
        private String events_address;
        private String events_champion;
        private String events_level;
        private String events_bonus;
        private int pga_id;
        private Object isCollect;



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

        public String getCoachgroupname() {
            return coachgroupname;
        }

        public void setCoachgroupname(String coachgroupname) {
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

        public String getEvents_champion() {
            return events_champion;
        }

        public void setEvents_champion(String events_champion) {
            this.events_champion = events_champion;
        }

        public String getEvents_level() {
            return events_level;
        }

        public void setEvents_level(String events_level) {
            this.events_level = events_level;
        }

        public String getEvents_bonus() {
            return events_bonus;
        }

        public void setEvents_bonus(String events_bonus) {
            this.events_bonus = events_bonus;
        }

        public int getPga_id() {
            return pga_id;
        }

        public void setPga_id(int pga_id) {
            this.pga_id = pga_id;
        }

        public Object getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(Object isCollect) {
            this.isCollect = isCollect;
        }

        @Override
        public String toString() {
            return "ListEventBean{" +
                    "events_id=" + events_id +
                    ", events_name='" + events_name + '\'' +
                    ", events_begintime='" + events_begintime + '\'' +
                    ", events_endtime='" + events_endtime + '\'' +
                    ", events_logo='" + events_logo + '\'' +
                    ", events_status='" + events_status + '\'' +
                    ", events_detail='" + events_detail + '\'' +
                    ", events_type='" + events_type + '\'' +
                    ", coachgroupname='" + coachgroupname + '\'' +
                    ", events_picture='" + events_picture + '\'' +
                    ", events_number=" + events_number +
                    ", events_address='" + events_address + '\'' +
                    ", events_champion='" + events_champion + '\'' +
                    ", events_level='" + events_level + '\'' +
                    ", events_bonus='" + events_bonus + '\'' +
                    ", pga_id=" + pga_id +
                    ", isCollect=" + isCollect +
                    '}';
        }
    }
}
