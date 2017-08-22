package com.glorystudent.entity;

import java.util.List;

/**
 * 活动详情含报名定义信息的实体类
 * Created by Gavin.J on 2017/5/26.
 */

public class EventWithSignInfoEntity {

    /**
     * eventactivity : {"listPic":[],"listSignUp":[],"listEventPicCount":0,"listEventPicWallCount":0,"listeventpic":[{"eid":593,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/f51d093cdccdfc5096d76e42621e1a06.png","eventactivity_id":94,"eventactivity_type":"1","user_name":null,"user_id":1217,"upload_time":"2017-05-25T18:22:50"}],"listeventpicwall":null,"listsign":null,"signUpNumber":0,"orderstate":0,"orderid":0,"cancelrefuse":null,"eventActivity_id":94,"eventactivity_name":"测试球队活动","eventactivity_begintime":"2017-05-26T00:00:00","eventactivity_endtime":"2017-05-31T00:00:00","eventactivity_detail":"测试球队！","eventactivity_number":50,"eventactivity_type":"2","eventactivity_state":"0","eventactivity_address":"深圳市南山区西丽街道","longitude":"113.9632100000","latitude":"22.5998000000","coachgrouplogo":null,"coachgroupname":null,"eventactivity_champion":null,"eventactivity_level":null,"eventactivity_bonus":null,"eventactivity_bringguestsnum":0,"eventactivity_ifbringguests":false,"eventactivity_kickofftime":"2017-05-30T09:00:00","eventactivity_ifpublicly":true,"eventactivity_pwd":null,"eventactivity_publisherlogo":"https://192.168.1.168:4431/images/headfiles/2017-5-8/1494229875634.png","eventactivity_publisherid":1217,"eventactivity_publishertel":"18727907512","eventactivity_publishername":"叶与影","eventactivity_costtype":"1","eventactivity_prepayment":0,"eventactivity_cost":0,"eventactivity_guestscost":0,"eventactivity_binarynumber":64,"eventactivity_signupbegintime":"2017-05-25T18:22:26","eventactivity_signupendtime":"2017-05-26T00:00:00","eventactivity_organizer":null,"eventactivity_organizertel":"18727907512","eventactivity_ifshowsignname":false,"eventactivity_ifshowphotowall":true,"pga_id":null,"eventactivity_createtime":"2017-05-25T18:22:26","eventactivity_guestprepayment":0,"eventactivity_guestcosttype":"1","eventactivity_teamid":23,"withdrawalsstatus":1,"eventActivity_AddressID":552,"eventactivity_Cancel":null}
     * listsigndefinition : [{"sdid":2,"sign_describe":"性别","sign_binarynumber":4,"sign_state":1},{"sdid":3,"sign_describe":"年龄","sign_binarynumber":8,"sign_state":1},{"sdid":4,"sign_describe":"公司","sign_binarynumber":16,"sign_state":1},{"sdid":5,"sign_describe":"球会","sign_binarynumber":32,"sign_state":1},{"sdid":6,"sign_describe":"球队","sign_binarynumber":64,"sign_state":1},{"sdid":7,"sign_describe":"会员号码","sign_binarynumber":128,"sign_state":1},{"sdid":8,"sign_describe":"差点","sign_binarynumber":256,"sign_state":1},{"sdid":10,"sign_describe":"身份证","sign_binarynumber":512,"sign_state":1},{"sdid":11,"sign_describe":"住房","sign_binarynumber":1024,"sign_state":1},{"sdid":12,"sign_describe":"备注","sign_binarynumber":2048,"sign_state":1}]
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

    private EventactivityBean eventactivity;
    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListsigndefinitionBean> listsigndefinition;

    public EventactivityBean getEventactivity() {
        return eventactivity;
    }

    public void setEventactivity(EventactivityBean eventactivity) {
        this.eventactivity = eventactivity;
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

    public List<ListsigndefinitionBean> getListsigndefinition() {
        return listsigndefinition;
    }

    public void setListsigndefinition(List<ListsigndefinitionBean> listsigndefinition) {
        this.listsigndefinition = listsigndefinition;
    }

    public static class EventactivityBean {
        /**
         * listPic : []
         * listSignUp : []
         * listEventPicCount : 0
         * listEventPicWallCount : 0
         * listeventpic : [{"eid":593,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/f51d093cdccdfc5096d76e42621e1a06.png","eventactivity_id":94,"eventactivity_type":"1","user_name":null,"user_id":1217,"upload_time":"2017-05-25T18:22:50"}]
         * listeventpicwall : null
         * listsign : null
         * signUpNumber : 0
         * orderstate : 0
         * orderid : 0
         * cancelrefuse : null
         * eventActivity_id : 94
         * eventactivity_name : 测试球队活动
         * eventactivity_begintime : 2017-05-26T00:00:00
         * eventactivity_endtime : 2017-05-31T00:00:00
         * eventactivity_detail : 测试球队！
         * eventactivity_number : 50
         * eventactivity_type : 2
         * eventactivity_state : 0
         * eventactivity_address : 深圳市南山区西丽街道
         * longitude : 113.9632100000
         * latitude : 22.5998000000
         * coachgrouplogo : null
         * coachgroupname : null
         * eventactivity_champion : null
         * eventactivity_level : null
         * eventactivity_bonus : null
         * eventactivity_bringguestsnum : 0
         * eventactivity_ifbringguests : false
         * eventactivity_kickofftime : 2017-05-30T09:00:00
         * eventactivity_ifpublicly : true
         * eventactivity_pwd : null
         * eventactivity_publisherlogo : https://192.168.1.168:4431/images/headfiles/2017-5-8/1494229875634.png
         * eventactivity_publisherid : 1217
         * eventactivity_publishertel : 18727907512
         * eventactivity_publishername : 叶与影
         * eventactivity_costtype : 1
         * eventactivity_prepayment : 0
         * eventactivity_cost : 0
         * eventactivity_guestscost : 0
         * eventactivity_binarynumber : 64
         * eventactivity_signupbegintime : 2017-05-25T18:22:26
         * eventactivity_signupendtime : 2017-05-26T00:00:00
         * eventactivity_organizer : null
         * eventactivity_organizertel : 18727907512
         * eventactivity_ifshowsignname : false
         * eventactivity_ifshowphotowall : true
         * pga_id : null
         * eventactivity_createtime : 2017-05-25T18:22:26
         * eventactivity_guestprepayment : 0
         * eventactivity_guestcosttype : 1
         * eventactivity_teamid : 23
         * withdrawalsstatus : 1
         * eventActivity_AddressID : 552
         * eventactivity_Cancel : null
         */

        private int listEventPicCount;
        private int listEventPicWallCount;
        private List<ListeventpicwallBean> listeventpicwall;
        private Object listsign;
        private int signUpNumber;
        private int orderstate;
        private int orderid;
        private Object cancelrefuse;
        private int eventActivity_id;
        private String eventactivity_name;
        private String eventactivity_begintime;
        private String eventactivity_endtime;
        private String eventactivity_detail;
        private int eventactivity_number;
        private String eventactivity_type;
        private String eventactivity_state;
        private String eventactivity_address;
        private String longitude;
        private String latitude;
        private Object coachgrouplogo;
        private String coachgroupname;
        private Object eventactivity_champion;
        private Object eventactivity_level;
        private Object eventactivity_bonus;
        private int eventactivity_bringguestsnum;
        private boolean eventactivity_ifbringguests;
        private String eventactivity_kickofftime;
        private boolean eventactivity_ifpublicly;
        private String eventactivity_pwd;
        private String eventactivity_publisherlogo;
        private int eventactivity_publisherid;
        private String eventactivity_publishertel;
        private String eventactivity_publishername;
        private String eventactivity_costtype;
        private int eventactivity_prepayment;
        private int eventactivity_cost;
        private int eventactivity_guestscost;
        private int eventactivity_binarynumber;
        private String eventactivity_signupbegintime;
        private String eventactivity_signupendtime;
        private String eventactivity_organizer;
        private String eventactivity_organizertel;
        private boolean eventactivity_ifshowsignname;
        private boolean eventactivity_ifshowphotowall;
        private Object pga_id;
        private String eventactivity_createtime;
        private int eventactivity_guestprepayment;
        private String eventactivity_guestcosttype;
        private int eventactivity_teamid;
        private int withdrawalsstatus;
        private int eventActivity_AddressID;
        private Object eventactivity_Cancel;
        private List<?> listPic;
        private List<?> listSignUp;
        private List<ListeventpicBean> listeventpic;

        public int getListEventPicCount() {
            return listEventPicCount;
        }

        public void setListEventPicCount(int listEventPicCount) {
            this.listEventPicCount = listEventPicCount;
        }

        public int getListEventPicWallCount() {
            return listEventPicWallCount;
        }

        public void setListEventPicWallCount(int listEventPicWallCount) {
            this.listEventPicWallCount = listEventPicWallCount;
        }

        public List<ListeventpicwallBean> getListeventpicwall() {
            return listeventpicwall;
        }

        public void setListeventpicwall(List<ListeventpicwallBean> listeventpicwall) {
            this.listeventpicwall = listeventpicwall;
        }

        public Object getListsign() {
            return listsign;
        }

        public void setListsign(Object listsign) {
            this.listsign = listsign;
        }

        public int getSignUpNumber() {
            return signUpNumber;
        }

        public void setSignUpNumber(int signUpNumber) {
            this.signUpNumber = signUpNumber;
        }

        public int getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(int orderstate) {
            this.orderstate = orderstate;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public Object getCancelrefuse() {
            return cancelrefuse;
        }

        public void setCancelrefuse(Object cancelrefuse) {
            this.cancelrefuse = cancelrefuse;
        }

        public int getEventActivity_id() {
            return eventActivity_id;
        }

        public void setEventActivity_id(int eventActivity_id) {
            this.eventActivity_id = eventActivity_id;
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

        public String getEventactivity_endtime() {
            return eventactivity_endtime;
        }

        public void setEventactivity_endtime(String eventactivity_endtime) {
            this.eventactivity_endtime = eventactivity_endtime;
        }

        public String getEventactivity_detail() {
            return eventactivity_detail;
        }

        public void setEventactivity_detail(String eventactivity_detail) {
            this.eventactivity_detail = eventactivity_detail;
        }

        public int getEventactivity_number() {
            return eventactivity_number;
        }

        public void setEventactivity_number(int eventactivity_number) {
            this.eventactivity_number = eventactivity_number;
        }

        public String getEventactivity_type() {
            return eventactivity_type;
        }

        public void setEventactivity_type(String eventactivity_type) {
            this.eventactivity_type = eventactivity_type;
        }

        public String getEventactivity_state() {
            return eventactivity_state;
        }

        public void setEventactivity_state(String eventactivity_state) {
            this.eventactivity_state = eventactivity_state;
        }

        public String getEventactivity_address() {
            return eventactivity_address;
        }

        public void setEventactivity_address(String eventactivity_address) {
            this.eventactivity_address = eventactivity_address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public Object getCoachgrouplogo() {
            return coachgrouplogo;
        }

        public void setCoachgrouplogo(Object coachgrouplogo) {
            this.coachgrouplogo = coachgrouplogo;
        }

        public String getCoachgroupname() {
            return coachgroupname;
        }

        public void setCoachgroupname(String coachgroupname) {
            this.coachgroupname = coachgroupname;
        }

        public Object getEventactivity_champion() {
            return eventactivity_champion;
        }

        public void setEventactivity_champion(Object eventactivity_champion) {
            this.eventactivity_champion = eventactivity_champion;
        }

        public Object getEventactivity_level() {
            return eventactivity_level;
        }

        public void setEventactivity_level(Object eventactivity_level) {
            this.eventactivity_level = eventactivity_level;
        }

        public Object getEventactivity_bonus() {
            return eventactivity_bonus;
        }

        public void setEventactivity_bonus(Object eventactivity_bonus) {
            this.eventactivity_bonus = eventactivity_bonus;
        }

        public int getEventactivity_bringguestsnum() {
            return eventactivity_bringguestsnum;
        }

        public void setEventactivity_bringguestsnum(int eventactivity_bringguestsnum) {
            this.eventactivity_bringguestsnum = eventactivity_bringguestsnum;
        }

        public boolean isEventactivity_ifbringguests() {
            return eventactivity_ifbringguests;
        }

        public void setEventactivity_ifbringguests(boolean eventactivity_ifbringguests) {
            this.eventactivity_ifbringguests = eventactivity_ifbringguests;
        }

        public String getEventactivity_kickofftime() {
            return eventactivity_kickofftime;
        }

        public void setEventactivity_kickofftime(String eventactivity_kickofftime) {
            this.eventactivity_kickofftime = eventactivity_kickofftime;
        }

        public boolean isEventactivity_ifpublicly() {
            return eventactivity_ifpublicly;
        }

        public void setEventactivity_ifpublicly(boolean eventactivity_ifpublicly) {
            this.eventactivity_ifpublicly = eventactivity_ifpublicly;
        }

        public String getEventactivity_pwd() {
            return eventactivity_pwd;
        }

        public void setEventactivity_pwd(String eventactivity_pwd) {
            this.eventactivity_pwd = eventactivity_pwd;
        }

        public String getEventactivity_publisherlogo() {
            return eventactivity_publisherlogo;
        }

        public void setEventactivity_publisherlogo(String eventactivity_publisherlogo) {
            this.eventactivity_publisherlogo = eventactivity_publisherlogo;
        }

        public int getEventactivity_publisherid() {
            return eventactivity_publisherid;
        }

        public void setEventactivity_publisherid(int eventactivity_publisherid) {
            this.eventactivity_publisherid = eventactivity_publisherid;
        }

        public String getEventactivity_publishertel() {
            return eventactivity_publishertel;
        }

        public void setEventactivity_publishertel(String eventactivity_publishertel) {
            this.eventactivity_publishertel = eventactivity_publishertel;
        }

        public String getEventactivity_publishername() {
            return eventactivity_publishername;
        }

        public void setEventactivity_publishername(String eventactivity_publishername) {
            this.eventactivity_publishername = eventactivity_publishername;
        }

        public String getEventactivity_costtype() {
            return eventactivity_costtype;
        }

        public void setEventactivity_costtype(String eventactivity_costtype) {
            this.eventactivity_costtype = eventactivity_costtype;
        }

        public int getEventactivity_prepayment() {
            return eventactivity_prepayment;
        }

        public void setEventactivity_prepayment(int eventactivity_prepayment) {
            this.eventactivity_prepayment = eventactivity_prepayment;
        }

        public int getEventactivity_cost() {
            return eventactivity_cost;
        }

        public void setEventactivity_cost(int eventactivity_cost) {
            this.eventactivity_cost = eventactivity_cost;
        }

        public int getEventactivity_guestscost() {
            return eventactivity_guestscost;
        }

        public void setEventactivity_guestscost(int eventactivity_guestscost) {
            this.eventactivity_guestscost = eventactivity_guestscost;
        }

        public int getEventactivity_binarynumber() {
            return eventactivity_binarynumber;
        }

        public void setEventactivity_binarynumber(int eventactivity_binarynumber) {
            this.eventactivity_binarynumber = eventactivity_binarynumber;
        }

        public String getEventactivity_signupbegintime() {
            return eventactivity_signupbegintime;
        }

        public void setEventactivity_signupbegintime(String eventactivity_signupbegintime) {
            this.eventactivity_signupbegintime = eventactivity_signupbegintime;
        }

        public String getEventactivity_signupendtime() {
            return eventactivity_signupendtime;
        }

        public void setEventactivity_signupendtime(String eventactivity_signupendtime) {
            this.eventactivity_signupendtime = eventactivity_signupendtime;
        }

        public String getEventactivity_organizer() {
            return eventactivity_organizer;
        }

        public void setEventactivity_organizer(String eventactivity_organizer) {
            this.eventactivity_organizer = eventactivity_organizer;
        }

        public String getEventactivity_organizertel() {
            return eventactivity_organizertel;
        }

        public void setEventactivity_organizertel(String eventactivity_organizertel) {
            this.eventactivity_organizertel = eventactivity_organizertel;
        }

        public boolean isEventactivity_ifshowsignname() {
            return eventactivity_ifshowsignname;
        }

        public void setEventactivity_ifshowsignname(boolean eventactivity_ifshowsignname) {
            this.eventactivity_ifshowsignname = eventactivity_ifshowsignname;
        }

        public boolean isEventactivity_ifshowphotowall() {
            return eventactivity_ifshowphotowall;
        }

        public void setEventactivity_ifshowphotowall(boolean eventactivity_ifshowphotowall) {
            this.eventactivity_ifshowphotowall = eventactivity_ifshowphotowall;
        }

        public Object getPga_id() {
            return pga_id;
        }

        public void setPga_id(Object pga_id) {
            this.pga_id = pga_id;
        }

        public String getEventactivity_createtime() {
            return eventactivity_createtime;
        }

        public void setEventactivity_createtime(String eventactivity_createtime) {
            this.eventactivity_createtime = eventactivity_createtime;
        }

        public int getEventactivity_guestprepayment() {
            return eventactivity_guestprepayment;
        }

        public void setEventactivity_guestprepayment(int eventactivity_guestprepayment) {
            this.eventactivity_guestprepayment = eventactivity_guestprepayment;
        }

        public String getEventactivity_guestcosttype() {
            return eventactivity_guestcosttype;
        }

        public void setEventactivity_guestcosttype(String eventactivity_guestcosttype) {
            this.eventactivity_guestcosttype = eventactivity_guestcosttype;
        }

        public int getEventactivity_teamid() {
            return eventactivity_teamid;
        }

        public void setEventactivity_teamid(int eventactivity_teamid) {
            this.eventactivity_teamid = eventactivity_teamid;
        }

        public int getWithdrawalsstatus() {
            return withdrawalsstatus;
        }

        public void setWithdrawalsstatus(int withdrawalsstatus) {
            this.withdrawalsstatus = withdrawalsstatus;
        }

        public int getEventActivity_AddressID() {
            return eventActivity_AddressID;
        }

        public void setEventActivity_AddressID(int eventActivity_AddressID) {
            this.eventActivity_AddressID = eventActivity_AddressID;
        }

        public Object getEventactivity_Cancel() {
            return eventactivity_Cancel;
        }

        public void setEventactivity_Cancel(Object eventactivity_Cancel) {
            this.eventactivity_Cancel = eventactivity_Cancel;
        }

        public List<?> getListPic() {
            return listPic;
        }

        public void setListPic(List<?> listPic) {
            this.listPic = listPic;
        }

        public List<?> getListSignUp() {
            return listSignUp;
        }

        public void setListSignUp(List<?> listSignUp) {
            this.listSignUp = listSignUp;
        }

        public List<ListeventpicBean> getListeventpic() {
            return listeventpic;
        }

        public void setListeventpic(List<ListeventpicBean> listeventpic) {
            this.listeventpic = listeventpic;
        }

        public static class ListeventpicBean {
            /**
             * eid : 593
             * eventactivity_picpath : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/f51d093cdccdfc5096d76e42621e1a06.png
             * eventactivity_id : 94
             * eventactivity_type : 1
             * user_name : null
             * user_id : 1217
             * upload_time : 2017-05-25T18:22:50
             */

            private int eid;
            private String eventactivity_picpath;
            private int eventactivity_id;
            private String eventactivity_type;
            private Object user_name;
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

            public Object getUser_name() {
                return user_name;
            }

            public void setUser_name(Object user_name) {
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

        public static class ListeventpicwallBean {
            /**
             * eid : 593
             * eventactivity_picpath : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/f51d093cdccdfc5096d76e42621e1a06.png
             * eventactivity_id : 94
             * eventactivity_type : 1
             * user_name : null
             * user_id : 1217
             * upload_time : 2017-05-25T18:22:50
             */

            private int eid;
            private String eventactivity_picpath;
            private int eventactivity_id;
            private String eventactivity_type;
            private Object user_name;
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

            public Object getUser_name() {
                return user_name;
            }

            public void setUser_name(Object user_name) {
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
