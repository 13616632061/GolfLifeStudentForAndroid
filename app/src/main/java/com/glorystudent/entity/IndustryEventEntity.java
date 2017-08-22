package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/1/10.
 */
public class IndustryEventEntity {

    /**
     * list_industryevents : [{"industryevents_id":6,"industryevents_tittle":"PGA教练教学峰会","industryevents_Begindate":"2017-01-16T00:00:00","industryevents_Enddate":null,"industryevents_organizer":"鹏廷体育","industryevents_context":"中国高尔夫经历了不平凡的一年","chinacity_id":440303,"chinacity_name":"广东省  深圳市  罗湖区","createTime":"2017-01-16T00:00:00"},{"industryevents_id":7,"industryevents_tittle":"2017鹏廷高尔夫球博览会","industryevents_Begindate":"2017-01-26T00:00:00","industryevents_Enddate":null,"industryevents_organizer":"鹏廷体育","industryevents_context":"鹏廷高尔夫球博览会","chinacity_id":440307,"chinacity_name":"广东省  深圳市  龙岗区","createTime":"2017-01-16T00:00:00"},{"industryevents_id":8,"industryevents_tittle":"第十届鹏廷高尔夫圆桌会议","industryevents_Begindate":"2017-01-27T00:00:00","industryevents_Enddate":null,"industryevents_organizer":"鹏廷体育","industryevents_context":"鹏廷高尔夫球协会","chinacity_id":440306,"chinacity_name":"广东省  深圳市  宝安区","createTime":"2017-01-16T00:00:00"},{"industryevents_id":9,"industryevents_tittle":"沃尔沃中国公开赛","industryevents_Begindate":"2017-04-12T14:30:33","industryevents_Enddate":null,"industryevents_organizer":"中国高尔夫球协会","industryevents_context":"沃尔沃中国公开赛\u2014\u2014天津滨海森林资格赛将在4月10日至4月12日开赛","chinacity_id":120100,"chinacity_name":"天津  天津市  和平区","createTime":"2017-01-16T00:00:00"},{"industryevents_id":10,"industryevents_tittle":"中国公开赛","industryevents_Begindate":"2017-04-27T00:00:00","industryevents_Enddate":null,"industryevents_organizer":"中国","industryevents_context":"沃尔沃高尔夫中国公开赛","chinacity_id":440103,"chinacity_name":"广东省  广州市  荔湾区","createTime":"2017-04-20T19:15:31"}]
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
    private List<ListIndustryeventsBean> list_industryevents;

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

    public List<ListIndustryeventsBean> getList_industryevents() {
        return list_industryevents;
    }

    public void setList_industryevents(List<ListIndustryeventsBean> list_industryevents) {
        this.list_industryevents = list_industryevents;
    }

    public static class ListIndustryeventsBean {
        /**
         * industryevents_id : 6
         * industryevents_tittle : PGA教练教学峰会
         * industryevents_Begindate : 2017-01-16T00:00:00
         * industryevents_Enddate : null
         * industryevents_organizer : 鹏廷体育
         * industryevents_context : 中国高尔夫经历了不平凡的一年
         * chinacity_id : 440303
         * chinacity_name : 广东省  深圳市  罗湖区
         * createTime : 2017-01-16T00:00:00
         */

        private int industryevents_id;
        private String industryevents_tittle;
        private String industryevents_Begindate;
        private Object industryevents_Enddate;
        private String industryevents_organizer;
        private String industryevents_context;
        private int chinacity_id;
        private String chinacity_name;
        private String createTime;
        private String url;
        private Object chinacity_Address;

        public int getIndustryevents_id() {
            return industryevents_id;
        }

        public void setIndustryevents_id(int industryevents_id) {
            this.industryevents_id = industryevents_id;
        }

        public String getIndustryevents_tittle() {
            return industryevents_tittle;
        }

        public void setIndustryevents_tittle(String industryevents_tittle) {
            this.industryevents_tittle = industryevents_tittle;
        }

        public String getIndustryevents_Begindate() {
            return industryevents_Begindate;
        }

        public void setIndustryevents_Begindate(String industryevents_Begindate) {
            this.industryevents_Begindate = industryevents_Begindate;
        }

        public Object getIndustryevents_Enddate() {
            return industryevents_Enddate;
        }

        public void setIndustryevents_Enddate(Object industryevents_Enddate) {
            this.industryevents_Enddate = industryevents_Enddate;
        }

        public String getIndustryevents_organizer() {
            return industryevents_organizer;
        }

        public void setIndustryevents_organizer(String industryevents_organizer) {
            this.industryevents_organizer = industryevents_organizer;
        }

        public String getIndustryevents_context() {
            return industryevents_context;
        }

        public void setIndustryevents_context(String industryevents_context) {
            this.industryevents_context = industryevents_context;
        }

        public int getChinacity_id() {
            return chinacity_id;
        }

        public void setChinacity_id(int chinacity_id) {
            this.chinacity_id = chinacity_id;
        }

        public String getChinacity_name() {
            return chinacity_name;
        }

        public void setChinacity_name(String chinacity_name) {
            this.chinacity_name = chinacity_name;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getChinacity_Address() {
            return chinacity_Address;
        }

        public void setChinacity_Address(Object chinacity_Address) {
            this.chinacity_Address = chinacity_Address;
        }
    }
}