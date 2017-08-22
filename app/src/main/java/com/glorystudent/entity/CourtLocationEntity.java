package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/4/18.
 */
public class CourtLocationEntity {

    /**
     * listCourt : [{"court_id":556,"court_name":"广东深圳碧海湾高尔夫球场","court_context":null,"court_longitude":"113.8520110000","court_latitude":"22.5838800000","court_address":"深圳市宝安区新湖路","court_picture":null,"court_city":"广东省"},{"court_id":552,"court_name":"广东深圳西丽高尔夫球场","court_context":null,"court_longitude":"113.9620610000","court_latitude":"22.5417190000","court_address":"深圳市南山区西丽街道","court_picture":null,"court_city":"广东省"},{"court_id":553,"court_name":"广东深圳名商高尔夫球场","court_context":null,"court_longitude":"113.9620610000","court_latitude":"22.5417190000","court_address":"深圳市南山区沙河街道","court_picture":null,"court_city":"广东省"},{"court_id":550,"court_name":"广东深圳沙河高尔夫球场","court_context":null,"court_longitude":"113.9597430000","court_latitude":"22.5264250000","court_address":"深圳市南山区沙河街道","court_picture":null,"court_city":"广东省"},{"court_id":536,"court_name":"广东深圳东部华侨城高尔夫球场","court_context":null,"court_longitude":"114.2869110000","court_latitude":"22.6291440000","court_address":"深圳市盐田区大梅沙深圳东部华侨城云海谷体育公园","court_picture":null,"court_city":"广东省"},{"court_id":554,"court_name":"广东深圳高尔夫球场","court_context":null,"court_longitude":"113.8756940000","court_latitude":"22.6413080000","court_address":"深圳市福田区深南大道6003号","court_picture":null,"court_city":"广东省"},{"court_id":184,"court_name":"广东深圳港中旅聚豪高尔夫球场","court_context":null,"court_longitude":"113.8725940000","court_latitude":"22.6413330000","court_address":"深圳市宝安区西乡街道","court_picture":null,"court_city":"广东省"},{"court_id":225,"court_name":"广东江门市台山高尔夫球场","court_context":null,"court_longitude":"112.8671300000","court_latitude":"22.6012830000","court_address":"江门市台山市水步镇密冲村委会","court_picture":null,"court_city":"广东省"},{"court_id":591,"court_name":"广东江门鹤山高尔夫球场","court_context":null,"court_longitude":"112.8678820000","court_latitude":"22.6015800000","court_address":"江门市鹤山市共和镇","court_picture":null,"court_city":"广东省"},{"court_id":555,"court_name":"广东深圳航港高尔夫球场","court_context":null,"court_longitude":"113.8124580000","court_latitude":"22.6558390000","court_address":"深圳市宝安区福永街道","court_picture":null,"court_city":"广东省"},{"court_id":590,"court_name":"广东江门市五邑蒲葵高尔夫球场","court_context":null,"court_longitude":"113.0862280000","court_latitude":"22.6320380000","court_address":"江门市北环路60号","court_picture":null,"court_city":"广东省"},{"court_id":538,"court_name":"广东深圳世纪海景高尔夫球场","court_context":null,"court_longitude":"114.4957660000","court_latitude":"22.5120390000","court_address":"深圳市大鹏新区南澳街道","court_picture":null,"court_city":"广东省"},{"court_id":557,"court_name":"广东深圳龙岗公众高尔夫球场","court_context":null,"court_longitude":"114.2033220000","court_latitude":"22.6737940000","court_address":"深圳市龙岗区龙城街道","court_picture":null,"court_city":"广东省"},{"court_id":593,"court_name":"广东中山长江高尔夫球场","court_context":null,"court_longitude":"113.4477470000","court_latitude":"22.4911390000","court_address":"中山市东区长江旅游区景区","court_picture":null,"court_city":"广东省"},{"court_id":585,"court_name":"广东惠州金海湾宝兴国际高尔夫球场（一期）","court_context":null,"court_longitude":"114.7555300000","court_latitude":"22.6794680000","court_address":"惠州市惠东县巽寮滨海旅游度假区","court_picture":null,"court_city":"广东省"},{"court_id":133,"court_name":"广东惠州金海湾宝兴国际高尔夫球场（二期）","court_context":null,"court_longitude":"114.7555300000","court_latitude":"22.6794680000","court_address":"惠州市惠东县巽寮滨海旅游度假区","court_picture":null,"court_city":"广东省"},{"court_id":569,"court_name":"广东东莞凤凰山高尔夫球场","court_context":null,"court_longitude":"114.1544750000","court_latitude":"22.7005980000","court_address":"东莞市长安镇莲花山边","court_picture":null,"court_city":"广东省"},{"court_id":551,"court_name":"广东深圳正中高尔夫球场","court_context":null,"court_longitude":"114.2619430000","court_latitude":"22.6985670000","court_address":"深圳市龙岗区龙岗街道","court_picture":null,"court_city":"广东省"},{"court_id":537,"court_name":"广东深圳九龙山高尔夫球场","court_context":null,"court_longitude":"114.0059290000","court_latitude":"22.7134110000","court_address":"深圳市龙华新区观澜街道","court_picture":null,"court_city":"广东省"}]
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : 19
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
    private List<ListCourtBean> listCourt;

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

    public List<ListCourtBean> getListCourt() {
        return listCourt;
    }

    public void setListCourt(List<ListCourtBean> listCourt) {
        this.listCourt = listCourt;
    }

    public static class ListCourtBean {
        /**
         * court_id : 556
         * court_name : 广东深圳碧海湾高尔夫球场
         * court_context : null
         * court_longitude : 113.8520110000
         * court_latitude : 22.5838800000
         * court_address : 深圳市宝安区新湖路
         * court_picture : null
         * court_city : 广东省
         */

        private int court_id;
        private String court_name;
        private Object court_context;
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

        public Object getCourt_context() {
            return court_context;
        }

        public void setCourt_context(Object court_context) {
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
