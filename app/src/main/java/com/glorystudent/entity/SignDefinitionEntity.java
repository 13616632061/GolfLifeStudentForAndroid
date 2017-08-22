package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SignDefinitionEntity {

    /**
     * listsigndefinition : [{"sdid":2,"sign_describe":"性别","sign_binarynumber":4,"sign_state":1},{"sdid":3,"sign_describe":"年龄","sign_binarynumber":8,"sign_state":1},{"sdid":4,"sign_describe":"公司","sign_binarynumber":16,"sign_state":1},{"sdid":5,"sign_describe":"球会","sign_binarynumber":32,"sign_state":1},{"sdid":6,"sign_describe":"球队","sign_binarynumber":64,"sign_state":1},{"sdid":7,"sign_describe":"会员号码","sign_binarynumber":128,"sign_state":1},{"sdid":8,"sign_describe":"差点","sign_binarynumber":256,"sign_state":1},{"sdid":9,"sign_describe":"身份证","sign_binarynumber":512,"sign_state":1},{"sdid":12,"sign_describe":"住房","sign_binarynumber":1024,"sign_state":1},{"sdid":13,"sign_describe":"备注","sign_binarynumber":2048,"sign_state":1}]
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : 10
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
    private List<ListsigndefinitionBean> listsigndefinition;

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

    public List<ListsigndefinitionBean> getListsigndefinition() {
        return listsigndefinition;
    }

    public void setListsigndefinition(List<ListsigndefinitionBean> listsigndefinition) {
        this.listsigndefinition = listsigndefinition;
    }

    public static class ListsigndefinitionBean {
        /**
         * sdid : 2
         * sign_describe : 性别
         * sign_binarynumber : 4
         * sign_state : 1
         */

        private int sdid;
        private String sign_describe;
        private int sign_binarynumber;
        private int sign_state;

        public int getSdid() {
            return sdid;
        }

        public void setSdid(int sdid) {
            this.sdid = sdid;
        }

        public String getSign_describe() {
            return sign_describe;
        }

        public void setSign_describe(String sign_describe) {
            this.sign_describe = sign_describe;
        }

        public int getSign_binarynumber() {
            return sign_binarynumber;
        }

        public void setSign_binarynumber(int sign_binarynumber) {
            this.sign_binarynumber = sign_binarynumber;
        }

        public int getSign_state() {
            return sign_state;
        }

        public void setSign_state(int sign_state) {
            this.sign_state = sign_state;
        }
    }
}
