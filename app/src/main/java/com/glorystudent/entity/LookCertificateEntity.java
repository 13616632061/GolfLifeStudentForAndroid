package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/12.
 */

public class LookCertificateEntity {

    /**
     * listeventactivity : [{"listPic":[],"listSignUp":[{"signup_id":597,"sign_activitiesname":null,"sign_activitiesid":66,"sign_activitiestype":null,"sign_phone":"13587456987","sign_logo":null,"sign_name":"测试","sign_registrationfee":null,"sign_almost":null,"sign_ifbringguests":null,"sign_number":null,"sign_sex":null,"sign_cardnumber":null,"sign_company":null,"sign_team":null,"sign_club":null,"sign_member":null,"sign_state":null,"sign_upstate":null,"sign_datetime":null,"sign_refuse":null,"sign_voucher":"636300138580101554","sign_ifallow":null,"sign_isguset":null,"sign_mainid":null,"userid":null,"sign_GuestRoom":null,"sign_Remark":null,"cancelCategory":null}],"listEventPicCount":1,"listEventPicWallCount":1,"listeventpic":null,"listeventpicwall":[{"eid":527,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/70baa5232992f538c4a1023f73768106.png","eventactivity_id":66,"eventactivity_type":"2","user_name":null,"user_id":null,"upload_time":null}],"listsign":null,"signnumber":1,"orderstate":0,"orderid":0,"cancelrefuse":null,"eventActivity_id":66,"eventactivity_name":"Dd11221","eventactivity_begintime":"2017-06-01T00:00:00","eventactivity_endtime":null,"eventactivity_detail":null,"eventactivity_number":1000,"eventactivity_type":null,"eventactivity_state":"0","eventactivity_address":"中山市三乡镇雍陌村","longitude":null,"latitude":null,"coachgrouplogo":null,"coachgroupname":null,"eventactivity_champion":null,"eventactivity_level":null,"eventactivity_bonus":null,"eventactivity_bringguestsnum":null,"eventactivity_ifbringguests":null,"eventactivity_kickofftime":null,"eventactivity_ifpublicly":null,"eventactivity_pwd":null,"eventactivity_publisherlogo":null,"eventactivity_publisherid":null,"eventactivity_publishertel":null,"eventactivity_publishername":null,"eventactivity_costtype":null,"eventactivity_prepayment":null,"eventactivity_cost":null,"eventactivity_guestscost":null,"eventactivity_binarynumber":0,"eventactivity_signupbegintime":null,"eventactivity_signupendtime":null,"eventactivity_organizer":null,"eventactivity_organizertel":null,"eventactivity_ifshowsignname":null,"eventactivity_ifshowphotowall":null,"pga_id":null,"eventactivity_createtime":null,"eventactivity_guestprepayment":null,"eventactivity_guestcosttype":null,"eventactivity_teamid":null,"withdrawalsstatus":null,"eventActivity_AddressID":null}]
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
    private List<ListeventactivityBean> listeventactivity;

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

    public List<ListeventactivityBean> getListeventactivity() {
        return listeventactivity;
    }

    public void setListeventactivity(List<ListeventactivityBean> listeventactivity) {
        this.listeventactivity = listeventactivity;
    }

    public static class ListeventactivityBean {
        /**
         * listPic : []
         * listSignUp : [{"signup_id":597,"sign_activitiesname":null,"sign_activitiesid":66,"sign_activitiestype":null,"sign_phone":"13587456987","sign_logo":null,"sign_name":"测试","sign_registrationfee":null,"sign_almost":null,"sign_ifbringguests":null,"sign_number":null,"sign_sex":null,"sign_cardnumber":null,"sign_company":null,"sign_team":null,"sign_club":null,"sign_member":null,"sign_state":null,"sign_upstate":null,"sign_datetime":null,"sign_refuse":null,"sign_voucher":"636300138580101554","sign_ifallow":null,"sign_isguset":null,"sign_mainid":null,"userid":null,"sign_GuestRoom":null,"sign_Remark":null,"cancelCategory":null}]
         * listEventPicCount : 1
         * listEventPicWallCount : 1
         * listeventpic : null
         * listeventpicwall : [{"eid":527,"eventactivity_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/70baa5232992f538c4a1023f73768106.png","eventactivity_id":66,"eventactivity_type":"2","user_name":null,"user_id":null,"upload_time":null}]
         * listsign : null
         * signnumber : 1
         * orderstate : 0
         * orderid : 0
         * cancelrefuse : null
         * eventActivity_id : 66
         * eventactivity_name : Dd11221
         * eventactivity_begintime : 2017-06-01T00:00:00
         * eventactivity_endtime : null
         * eventactivity_detail : null
         * eventactivity_number : 1000
         * eventactivity_type : null
         * eventactivity_state : 0
         * eventactivity_address : 中山市三乡镇雍陌村
         * longitude : null
         * latitude : null
         * coachgrouplogo : null
         * coachgroupname : null
         * eventactivity_champion : null
         * eventactivity_level : null
         * eventactivity_bonus : null
         * eventactivity_bringguestsnum : null
         * eventactivity_ifbringguests : null
         * eventactivity_kickofftime : null
         * eventactivity_ifpublicly : null
         * eventactivity_pwd : null
         * eventactivity_publisherlogo : null
         * eventactivity_publisherid : null
         * eventactivity_publishertel : null
         * eventactivity_publishername : null
         * eventactivity_costtype : null
         * eventactivity_prepayment : null
         * eventactivity_cost : null
         * eventactivity_guestscost : null
         * eventactivity_binarynumber : 0
         * eventactivity_signupbegintime : null
         * eventactivity_signupendtime : null
         * eventactivity_organizer : null
         * eventactivity_organizertel : null
         * eventactivity_ifshowsignname : null
         * eventactivity_ifshowphotowall : null
         * pga_id : null
         * eventactivity_createtime : null
         * eventactivity_guestprepayment : null
         * eventactivity_guestcosttype : null
         * eventactivity_teamid : null
         * withdrawalsstatus : null
         * eventActivity_AddressID : null
         */

        private int listEventPicCount;
        private int listEventPicWallCount;
        private Object listeventpic;
        private Object listsign;
        private int signUpNumber;
        private int orderstate;
        private int orderid;
        private Object cancelrefuse;
        private int eventActivity_id;
        private String eventactivity_name;
        private String eventactivity_begintime;
        private String eventactivity_endtime;
        private Object eventactivity_detail;
        private int eventactivity_number;
        private Object eventactivity_type;
        private String eventactivity_state;
        private String eventactivity_address;
        private Object longitude;
        private Object latitude;
        private Object coachgrouplogo;
        private Object coachgroupname;
        private Object eventactivity_champion;
        private Object eventactivity_level;
        private Object eventactivity_bonus;
        private Object eventactivity_bringguestsnum;
        private Object eventactivity_ifbringguests;
        private Object eventactivity_kickofftime;
        private Object eventactivity_ifpublicly;
        private Object eventactivity_pwd;
        private Object eventactivity_publisherlogo;
        private Object eventactivity_publisherid;
        private Object eventactivity_publishertel;
        private Object eventactivity_publishername;
        private String eventactivity_costtype;
        private Object eventactivity_prepayment;
        private double eventactivity_cost;
        private Object eventactivity_guestscost;
        private int eventactivity_binarynumber;
        private Object eventactivity_signupbegintime;
        private Object eventactivity_signupendtime;
        private Object eventactivity_organizer;
        private Object eventactivity_organizertel;
        private Object eventactivity_ifshowsignname;
        private Object eventactivity_ifshowphotowall;
        private Object pga_id;
        private Object eventactivity_createtime;
        private Object eventactivity_guestprepayment;
        private Object eventactivity_guestcosttype;
        private Object eventactivity_teamid;
        private Object withdrawalsstatus;
        private Object eventActivity_AddressID;
        private List<?> listPic;
        private List<ListSignUpBean> listSignUp;
        private List<ListeventpicwallBean> listeventpicwall;

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

        public Object getListeventpic() {
            return listeventpic;
        }

        public void setListeventpic(Object listeventpic) {
            this.listeventpic = listeventpic;
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

        public Object getEventactivity_detail() {
            return eventactivity_detail;
        }

        public void setEventactivity_detail(Object eventactivity_detail) {
            this.eventactivity_detail = eventactivity_detail;
        }

        public int getEventactivity_number() {
            return eventactivity_number;
        }

        public void setEventactivity_number(int eventactivity_number) {
            this.eventactivity_number = eventactivity_number;
        }

        public Object getEventactivity_type() {
            return eventactivity_type;
        }

        public void setEventactivity_type(Object eventactivity_type) {
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

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getCoachgrouplogo() {
            return coachgrouplogo;
        }

        public void setCoachgrouplogo(Object coachgrouplogo) {
            this.coachgrouplogo = coachgrouplogo;
        }

        public Object getCoachgroupname() {
            return coachgroupname;
        }

        public void setCoachgroupname(Object coachgroupname) {
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

        public Object getEventactivity_bringguestsnum() {
            return eventactivity_bringguestsnum;
        }

        public void setEventactivity_bringguestsnum(Object eventactivity_bringguestsnum) {
            this.eventactivity_bringguestsnum = eventactivity_bringguestsnum;
        }

        public Object getEventactivity_ifbringguests() {
            return eventactivity_ifbringguests;
        }

        public void setEventactivity_ifbringguests(Object eventactivity_ifbringguests) {
            this.eventactivity_ifbringguests = eventactivity_ifbringguests;
        }

        public Object getEventactivity_kickofftime() {
            return eventactivity_kickofftime;
        }

        public void setEventactivity_kickofftime(Object eventactivity_kickofftime) {
            this.eventactivity_kickofftime = eventactivity_kickofftime;
        }

        public Object getEventactivity_ifpublicly() {
            return eventactivity_ifpublicly;
        }

        public void setEventactivity_ifpublicly(Object eventactivity_ifpublicly) {
            this.eventactivity_ifpublicly = eventactivity_ifpublicly;
        }

        public Object getEventactivity_pwd() {
            return eventactivity_pwd;
        }

        public void setEventactivity_pwd(Object eventactivity_pwd) {
            this.eventactivity_pwd = eventactivity_pwd;
        }

        public Object getEventactivity_publisherlogo() {
            return eventactivity_publisherlogo;
        }

        public void setEventactivity_publisherlogo(Object eventactivity_publisherlogo) {
            this.eventactivity_publisherlogo = eventactivity_publisherlogo;
        }

        public Object getEventactivity_publisherid() {
            return eventactivity_publisherid;
        }

        public void setEventactivity_publisherid(Object eventactivity_publisherid) {
            this.eventactivity_publisherid = eventactivity_publisherid;
        }

        public Object getEventactivity_publishertel() {
            return eventactivity_publishertel;
        }

        public void setEventactivity_publishertel(Object eventactivity_publishertel) {
            this.eventactivity_publishertel = eventactivity_publishertel;
        }

        public Object getEventactivity_publishername() {
            return eventactivity_publishername;
        }

        public void setEventactivity_publishername(Object eventactivity_publishername) {
            this.eventactivity_publishername = eventactivity_publishername;
        }

        public String getEventactivity_costtype() {
            return eventactivity_costtype;
        }

        public void setEventactivity_costtype(String eventactivity_costtype) {
            this.eventactivity_costtype = eventactivity_costtype;
        }

        public Object getEventactivity_prepayment() {
            return eventactivity_prepayment;
        }

        public void setEventactivity_prepayment(Object eventactivity_prepayment) {
            this.eventactivity_prepayment = eventactivity_prepayment;
        }

        public double getEventactivity_cost() {
            return eventactivity_cost;
        }

        public void setEventactivity_cost(double eventactivity_cost) {
            this.eventactivity_cost = eventactivity_cost;
        }

        public Object getEventactivity_guestscost() {
            return eventactivity_guestscost;
        }

        public void setEventactivity_guestscost(Object eventactivity_guestscost) {
            this.eventactivity_guestscost = eventactivity_guestscost;
        }

        public int getEventactivity_binarynumber() {
            return eventactivity_binarynumber;
        }

        public void setEventactivity_binarynumber(int eventactivity_binarynumber) {
            this.eventactivity_binarynumber = eventactivity_binarynumber;
        }

        public Object getEventactivity_signupbegintime() {
            return eventactivity_signupbegintime;
        }

        public void setEventactivity_signupbegintime(Object eventactivity_signupbegintime) {
            this.eventactivity_signupbegintime = eventactivity_signupbegintime;
        }

        public Object getEventactivity_signupendtime() {
            return eventactivity_signupendtime;
        }

        public void setEventactivity_signupendtime(Object eventactivity_signupendtime) {
            this.eventactivity_signupendtime = eventactivity_signupendtime;
        }

        public Object getEventactivity_organizer() {
            return eventactivity_organizer;
        }

        public void setEventactivity_organizer(Object eventactivity_organizer) {
            this.eventactivity_organizer = eventactivity_organizer;
        }

        public Object getEventactivity_organizertel() {
            return eventactivity_organizertel;
        }

        public void setEventactivity_organizertel(Object eventactivity_organizertel) {
            this.eventactivity_organizertel = eventactivity_organizertel;
        }

        public Object getEventactivity_ifshowsignname() {
            return eventactivity_ifshowsignname;
        }

        public void setEventactivity_ifshowsignname(Object eventactivity_ifshowsignname) {
            this.eventactivity_ifshowsignname = eventactivity_ifshowsignname;
        }

        public Object getEventactivity_ifshowphotowall() {
            return eventactivity_ifshowphotowall;
        }

        public void setEventactivity_ifshowphotowall(Object eventactivity_ifshowphotowall) {
            this.eventactivity_ifshowphotowall = eventactivity_ifshowphotowall;
        }

        public Object getPga_id() {
            return pga_id;
        }

        public void setPga_id(Object pga_id) {
            this.pga_id = pga_id;
        }

        public Object getEventactivity_createtime() {
            return eventactivity_createtime;
        }

        public void setEventactivity_createtime(Object eventactivity_createtime) {
            this.eventactivity_createtime = eventactivity_createtime;
        }

        public Object getEventactivity_guestprepayment() {
            return eventactivity_guestprepayment;
        }

        public void setEventactivity_guestprepayment(Object eventactivity_guestprepayment) {
            this.eventactivity_guestprepayment = eventactivity_guestprepayment;
        }

        public Object getEventactivity_guestcosttype() {
            return eventactivity_guestcosttype;
        }

        public void setEventactivity_guestcosttype(Object eventactivity_guestcosttype) {
            this.eventactivity_guestcosttype = eventactivity_guestcosttype;
        }

        public Object getEventactivity_teamid() {
            return eventactivity_teamid;
        }

        public void setEventactivity_teamid(Object eventactivity_teamid) {
            this.eventactivity_teamid = eventactivity_teamid;
        }

        public Object getWithdrawalsstatus() {
            return withdrawalsstatus;
        }

        public void setWithdrawalsstatus(Object withdrawalsstatus) {
            this.withdrawalsstatus = withdrawalsstatus;
        }

        public Object getEventActivity_AddressID() {
            return eventActivity_AddressID;
        }

        public void setEventActivity_AddressID(Object eventActivity_AddressID) {
            this.eventActivity_AddressID = eventActivity_AddressID;
        }

        public List<?> getListPic() {
            return listPic;
        }

        public void setListPic(List<?> listPic) {
            this.listPic = listPic;
        }

        public List<ListSignUpBean> getListSignUp() {
            return listSignUp;
        }

        public void setListSignUp(List<ListSignUpBean> listSignUp) {
            this.listSignUp = listSignUp;
        }

        public List<ListeventpicwallBean> getListeventpicwall() {
            return listeventpicwall;
        }

        public void setListeventpicwall(List<ListeventpicwallBean> listeventpicwall) {
            this.listeventpicwall = listeventpicwall;
        }

        public static class ListSignUpBean {
            /**
             * signup_id : 597
             * sign_activitiesname : null
             * sign_activitiesid : 66
             * sign_activitiestype : null
             * sign_phone : 13587456987
             * sign_logo : null
             * sign_name : 测试
             * sign_registrationfee : null
             * sign_almost : null
             * sign_ifbringguests : null
             * sign_number : null
             * sign_sex : null
             * sign_cardnumber : null
             * sign_company : null
             * sign_team : null
             * sign_club : null
             * sign_member : null
             * sign_state : null
             * sign_upstate : null
             * sign_datetime : null
             * sign_refuse : null
             * sign_voucher : 636300138580101554
             * sign_ifallow : null
             * sign_isguset : null
             * sign_mainid : null
             * userid : null
             * sign_GuestRoom : null
             * sign_Remark : null
             * cancelCategory : null
             */

            private int signup_id;
            private Object sign_activitiesname;
            private int sign_activitiesid;
            private Object sign_activitiestype;
            private String sign_phone;
            private Object sign_logo;
            private String sign_name;
            private Object sign_registrationfee;
            private Object sign_almost;
            private Object sign_ifbringguests;
            private Object sign_number;
            private Object sign_sex;
            private Object sign_cardnumber;
            private Object sign_company;
            private Object sign_team;
            private Object sign_club;
            private Object sign_member;
            private Object sign_state;
            private Object sign_upstate;
            private Object sign_datetime;
            private Object sign_refuse;
            private String sign_voucher;
            private Object sign_ifallow;
            private Object sign_isguset;
            private Object sign_mainid;
            private Object userid;
            private Object sign_GuestRoom;
            private Object sign_Remark;
            private Object cancelCategory;

            public int getSignup_id() {
                return signup_id;
            }

            public void setSignup_id(int signup_id) {
                this.signup_id = signup_id;
            }

            public Object getSign_activitiesname() {
                return sign_activitiesname;
            }

            public void setSign_activitiesname(Object sign_activitiesname) {
                this.sign_activitiesname = sign_activitiesname;
            }

            public int getSign_activitiesid() {
                return sign_activitiesid;
            }

            public void setSign_activitiesid(int sign_activitiesid) {
                this.sign_activitiesid = sign_activitiesid;
            }

            public Object getSign_activitiestype() {
                return sign_activitiestype;
            }

            public void setSign_activitiestype(Object sign_activitiestype) {
                this.sign_activitiestype = sign_activitiestype;
            }

            public String getSign_phone() {
                return sign_phone;
            }

            public void setSign_phone(String sign_phone) {
                this.sign_phone = sign_phone;
            }

            public Object getSign_logo() {
                return sign_logo;
            }

            public void setSign_logo(Object sign_logo) {
                this.sign_logo = sign_logo;
            }

            public String getSign_name() {
                return sign_name;
            }

            public void setSign_name(String sign_name) {
                this.sign_name = sign_name;
            }

            public Object getSign_registrationfee() {
                return sign_registrationfee;
            }

            public void setSign_registrationfee(Object sign_registrationfee) {
                this.sign_registrationfee = sign_registrationfee;
            }

            public Object getSign_almost() {
                return sign_almost;
            }

            public void setSign_almost(Object sign_almost) {
                this.sign_almost = sign_almost;
            }

            public Object getSign_ifbringguests() {
                return sign_ifbringguests;
            }

            public void setSign_ifbringguests(Object sign_ifbringguests) {
                this.sign_ifbringguests = sign_ifbringguests;
            }

            public Object getSign_number() {
                return sign_number;
            }

            public void setSign_number(Object sign_number) {
                this.sign_number = sign_number;
            }

            public Object getSign_sex() {
                return sign_sex;
            }

            public void setSign_sex(Object sign_sex) {
                this.sign_sex = sign_sex;
            }

            public Object getSign_cardnumber() {
                return sign_cardnumber;
            }

            public void setSign_cardnumber(Object sign_cardnumber) {
                this.sign_cardnumber = sign_cardnumber;
            }

            public Object getSign_company() {
                return sign_company;
            }

            public void setSign_company(Object sign_company) {
                this.sign_company = sign_company;
            }

            public Object getSign_team() {
                return sign_team;
            }

            public void setSign_team(Object sign_team) {
                this.sign_team = sign_team;
            }

            public Object getSign_club() {
                return sign_club;
            }

            public void setSign_club(Object sign_club) {
                this.sign_club = sign_club;
            }

            public Object getSign_member() {
                return sign_member;
            }

            public void setSign_member(Object sign_member) {
                this.sign_member = sign_member;
            }

            public Object getSign_state() {
                return sign_state;
            }

            public void setSign_state(Object sign_state) {
                this.sign_state = sign_state;
            }

            public Object getSign_upstate() {
                return sign_upstate;
            }

            public void setSign_upstate(Object sign_upstate) {
                this.sign_upstate = sign_upstate;
            }

            public Object getSign_datetime() {
                return sign_datetime;
            }

            public void setSign_datetime(Object sign_datetime) {
                this.sign_datetime = sign_datetime;
            }

            public Object getSign_refuse() {
                return sign_refuse;
            }

            public void setSign_refuse(Object sign_refuse) {
                this.sign_refuse = sign_refuse;
            }

            public String getSign_voucher() {
                return sign_voucher;
            }

            public void setSign_voucher(String sign_voucher) {
                this.sign_voucher = sign_voucher;
            }

            public Object getSign_ifallow() {
                return sign_ifallow;
            }

            public void setSign_ifallow(Object sign_ifallow) {
                this.sign_ifallow = sign_ifallow;
            }

            public Object getSign_isguset() {
                return sign_isguset;
            }

            public void setSign_isguset(Object sign_isguset) {
                this.sign_isguset = sign_isguset;
            }

            public Object getSign_mainid() {
                return sign_mainid;
            }

            public void setSign_mainid(Object sign_mainid) {
                this.sign_mainid = sign_mainid;
            }

            public Object getUserid() {
                return userid;
            }

            public void setUserid(Object userid) {
                this.userid = userid;
            }

            public Object getSign_GuestRoom() {
                return sign_GuestRoom;
            }

            public void setSign_GuestRoom(Object sign_GuestRoom) {
                this.sign_GuestRoom = sign_GuestRoom;
            }

            public Object getSign_Remark() {
                return sign_Remark;
            }

            public void setSign_Remark(Object sign_Remark) {
                this.sign_Remark = sign_Remark;
            }

            public Object getCancelCategory() {
                return cancelCategory;
            }

            public void setCancelCategory(Object cancelCategory) {
                this.cancelCategory = cancelCategory;
            }
        }

        public static class ListeventpicwallBean {
            /**
             * eid : 527
             * eventactivity_picpath : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/LMk5VxO6JIOqxrLZExRpTGZbcP67wRove5hPC81GfU%3D/images/70baa5232992f538c4a1023f73768106.png
             * eventactivity_id : 66
             * eventactivity_type : 2
             * user_name : null
             * user_id : null
             * upload_time : null
             */

            private int eid;
            private String eventactivity_picpath;
            private int eventactivity_id;
            private String eventactivity_type;
            private Object user_name;
            private Object user_id;
            private Object upload_time;

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
}
