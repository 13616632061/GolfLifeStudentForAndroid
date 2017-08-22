package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class GolfCourseEntity {

    /**
     * listCourt : [{"court_id":56,"court_name":"正中高尔夫练习场","court_context":"正中高尔夫俱乐部练习场","court_longitude":"22.54","court_latitude":"114.0","court_address":"深圳市","court_picture":"https://app.pgagolf.cn/images/headfiles/2016-12-27/glof(60313).jpg","court_city":"广东省"},{"court_id":57,"court_name":"中山温泉高尔夫球场","court_context":"中山温泉高尔夫球场","court_longitude":"113.462833","court_latitude":"22.378865","court_address":"中山市","court_picture":"https://app.pgagolf.cn/images/files/2016-12-28/zsCourt.png","court_city":"广东省"},{"court_id":58,"court_name":"中山雅居乐长江高尔夫球会","court_context":"中山雅居乐长江高尔夫球会","court_longitude":null,"court_latitude":null,"court_address":"中山市","court_picture":"https://app.pgagolf.cn/images/files/2016-12-28/zsCourt.png","court_city":"广东省"}]
     * version : 1.0.3
     * datetime : 2017/4/11 11:20:35
     * accesstoken : j7RgQqqNJw19lSWQFZkOcE76EoiVGdNYiSa0u6XLzmguLuv8R49pnFI0lO1fwUqfJIGmGa/w2nr12o9AKFIgUIfdj8KfgM3GWyhVV40nBwTCl8v212WooYIn/dT cdUhod4e4VQwR0F6Ob3N0AV7VChdLfxsOizuJtjR6CJsywERmob49kBDS5s/Ai5nqjdqoVO cT2Z GkSZY2L BQ3DBLmPAi29V3nluZWttNIwCaPAYwL0OM/dp2xrd RerB5ZXx19OvUqcB4C1coKit9D3PNMeBa86t6HKGmoGCG9We1bLO XdDLvZxEJymywZGLiCj4ynRACSCaz GIonw8Xw==
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : 3
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
    private List<ListCourtBean> listCourt;

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

    public List<ListCourtBean> getListCourt() {
        return listCourt;
    }

    public void setListCourt(List<ListCourtBean> listCourt) {
        this.listCourt = listCourt;
    }

    public static class ListCourtBean {
        /**
         * court_id : 56
         * court_name : 正中高尔夫练习场
         * court_context : 正中高尔夫俱乐部练习场
         * court_longitude : 22.54
         * court_latitude : 114.0
         * court_address : 深圳市
         * court_picture : https://app.pgagolf.cn/images/headfiles/2016-12-27/glof(60313).jpg
         * court_city : 广东省
         */

        private int court_id;
        private String court_name;
        private String court_context;
        private String court_longitude;
        private String court_latitude;
        private String court_address;
        private String court_picture;
        private String court_city;

        public int getCourt_id() {
            return court_id;
        }

        public void setCourt_id(int court_id) {
            this.court_id = court_id;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
        }

        public String getCourt_context() {
            return court_context;
        }

        public void setCourt_context(String court_context) {
            this.court_context = court_context;
        }

        public String getCourt_longitude() {
            return court_longitude;
        }

        public void setCourt_longitude(String court_longitude) {
            this.court_longitude = court_longitude;
        }

        public String getCourt_latitude() {
            return court_latitude;
        }

        public void setCourt_latitude(String court_latitude) {
            this.court_latitude = court_latitude;
        }

        public String getCourt_address() {
            return court_address;
        }

        public void setCourt_address(String court_address) {
            this.court_address = court_address;
        }

        public String getCourt_picture() {
            return court_picture;
        }

        public void setCourt_picture(String court_picture) {
            this.court_picture = court_picture;
        }

        public String getCourt_city() {
            return court_city;
        }

        public void setCourt_city(String court_city) {
            this.court_city = court_city;
        }
    }
}
