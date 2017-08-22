package com.glorystudent.entity;

import com.glorystudent.golflibrary.adapter.AbsMoreBaseAdapter;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/12.
 */

public class ApplyListEntity {

    /**
     * listsignup : [{"orderid":0,"eventactivity_id":0,"sign_list":null,"order":{"order_id":0,"order_flowno":null,"order_totalprice":0,"order_paymenttype":"\u0000","order_category":"\u0000","order_state":"\u0000","order_remark":null,"order_datetime":null,"order_usernametel":null,"order_username":null,"order_userid":null,"order_discount":0,"order_coach":0,"order_coachgroup":0,"eventactivity_id":null,"signup_id":0,"hedging_oid":0,"appid":null,"withdrawalsstatus":0},"eventActivity":{"listPic":[],"listSignUp":[],"eventActivity_id":0,"eventactivity_name":null,"eventactivity_begintime":null,"eventactivity_endtime":null,"eventactivity_detail":null,"eventactivity_number":null,"eventactivity_type":null,"eventactivity_state":null,"eventactivity_address":null,"longitude":null,"latitude":null,"coachgrouplogo":null,"coachgroupname":null,"eventactivity_champion":null,"eventactivity_level":null,"eventactivity_bonus":null,"eventactivity_bringguestsnum":null,"eventactivity_ifbringguests":null,"eventactivity_kickofftime":null,"eventactivity_ifpublicly":null,"eventactivity_pwd":null,"eventactivity_publisherlogo":null,"eventactivity_publisherid":null,"eventactivity_publishertel":null,"eventactivity_publishername":null,"eventactivity_costtype":null,"eventactivity_prepayment":null,"eventactivity_cost":null,"eventactivity_guestscost":null,"eventactivity_binarynumber":0,"eventactivity_signupbegintime":null,"eventactivity_signupendtime":null,"eventactivity_organizer":null,"eventactivity_organizertel":null,"eventactivity_ifshowsignname":null,"eventactivity_ifshowphotowall":null,"pga_id":null,"eventactivity_createtime":null,"eventactivity_guestprepayment":null,"eventactivity_guestcosttype":null,"eventactivity_teamid":null,"withdrawalsstatus":null,"eventActivity_AddressID":null},"eventPicList":[],"eventPicWallList":null,"sigUpNumber":2,"signup_id":614,"sign_activitiesname":"测试","sign_activitiesid":73,"sign_activitiestype":"2","sign_phone":"13458069807","sign_logo":null,"sign_name":"刚","sign_registrationfee":null,"sign_almost":0,"sign_ifbringguests":false,"sign_number":0,"sign_sex":"2","sign_cardnumber":null,"sign_company":null,"sign_team":null,"sign_club":null,"sign_member":null,"sign_state":"2","sign_upstate":"1","sign_datetime":"2017-05-12T14:17:12","sign_refuse":null,"sign_voucher":"636301954320853014","sign_ifallow":false,"sign_isguset":false,"sign_mainid":0,"userid":null,"sign_GuestRoom":null,"sign_Remark":null,"cancelCategory":"0"},{"orderid":0,"eventactivity_id":0,"sign_list":null,"order":{"order_id":0,"order_flowno":null,"order_totalprice":0,"order_paymenttype":"\u0000","order_category":"\u0000","order_state":"\u0000","order_remark":null,"order_datetime":null,"order_usernametel":null,"order_username":null,"order_userid":null,"order_discount":0,"order_coach":0,"order_coachgroup":0,"eventactivity_id":null,"signup_id":0,"hedging_oid":0,"appid":null,"withdrawalsstatus":0},"eventActivity":{"listPic":[],"listSignUp":[],"eventActivity_id":0,"eventactivity_name":null,"eventactivity_begintime":null,"eventactivity_endtime":null,"eventactivity_detail":null,"eventactivity_number":null,"eventactivity_type":null,"eventactivity_state":null,"eventactivity_address":null,"longitude":null,"latitude":null,"coachgrouplogo":null,"coachgroupname":null,"eventactivity_champion":null,"eventactivity_level":null,"eventactivity_bonus":null,"eventactivity_bringguestsnum":null,"eventactivity_ifbringguests":null,"eventactivity_kickofftime":null,"eventactivity_ifpublicly":null,"eventactivity_pwd":null,"eventactivity_publisherlogo":null,"eventactivity_publisherid":null,"eventactivity_publishertel":null,"eventactivity_publishername":null,"eventactivity_costtype":null,"eventactivity_prepayment":null,"eventactivity_cost":null,"eventactivity_guestscost":null,"eventactivity_binarynumber":0,"eventactivity_signupbegintime":null,"eventactivity_signupendtime":null,"eventactivity_organizer":null,"eventactivity_organizertel":null,"eventactivity_ifshowsignname":null,"eventactivity_ifshowphotowall":null,"pga_id":null,"eventactivity_createtime":null,"eventactivity_guestprepayment":null,"eventactivity_guestcosttype":null,"eventactivity_teamid":null,"withdrawalsstatus":null,"eventActivity_AddressID":null},"eventPicList":[],"eventPicWallList":null,"sigUpNumber":2,"signup_id":613,"sign_activitiesname":"测试","sign_activitiesid":73,"sign_activitiestype":"2","sign_phone":"18727907512","sign_logo":null,"sign_name":"小样","sign_registrationfee":null,"sign_almost":0,"sign_ifbringguests":false,"sign_number":0,"sign_sex":"1","sign_cardnumber":null,"sign_company":null,"sign_team":null,"sign_club":null,"sign_member":null,"sign_state":"2","sign_upstate":"1","sign_datetime":"2017-05-12T14:15:54","sign_refuse":null,"sign_voucher":"636301953545985641","sign_ifallow":false,"sign_isguset":false,"sign_mainid":0,"userid":null,"sign_GuestRoom":null,"sign_Remark":null,"cancelCategory":"0"}]
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
    private List<ListsignupBean> listsignup;

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

    public List<ListsignupBean> getListsignup() {
        return listsignup;
    }

    public void setListsignup(List<ListsignupBean> listsignup) {
        this.listsignup = listsignup;
    }

    public static class ListsignupBean implements AbsMoreBaseAdapter.OnType {
        /**
         * orderid : 0
         * eventactivity_id : 0
         * sign_list : null
         * order : {"order_id":0,"order_flowno":null,"order_totalprice":0,"order_paymenttype":"\u0000","order_category":"\u0000","order_state":"\u0000","order_remark":null,"order_datetime":null,"order_usernametel":null,"order_username":null,"order_userid":null,"order_discount":0,"order_coach":0,"order_coachgroup":0,"eventactivity_id":null,"signup_id":0,"hedging_oid":0,"appid":null,"withdrawalsstatus":0}
         * eventActivity : {"listPic":[],"listSignUp":[],"eventActivity_id":0,"eventactivity_name":null,"eventactivity_begintime":null,"eventactivity_endtime":null,"eventactivity_detail":null,"eventactivity_number":null,"eventactivity_type":null,"eventactivity_state":null,"eventactivity_address":null,"longitude":null,"latitude":null,"coachgrouplogo":null,"coachgroupname":null,"eventactivity_champion":null,"eventactivity_level":null,"eventactivity_bonus":null,"eventactivity_bringguestsnum":null,"eventactivity_ifbringguests":null,"eventactivity_kickofftime":null,"eventactivity_ifpublicly":null,"eventactivity_pwd":null,"eventactivity_publisherlogo":null,"eventactivity_publisherid":null,"eventactivity_publishertel":null,"eventactivity_publishername":null,"eventactivity_costtype":null,"eventactivity_prepayment":null,"eventactivity_cost":null,"eventactivity_guestscost":null,"eventactivity_binarynumber":0,"eventactivity_signupbegintime":null,"eventactivity_signupendtime":null,"eventactivity_organizer":null,"eventactivity_organizertel":null,"eventactivity_ifshowsignname":null,"eventactivity_ifshowphotowall":null,"pga_id":null,"eventactivity_createtime":null,"eventactivity_guestprepayment":null,"eventactivity_guestcosttype":null,"eventactivity_teamid":null,"withdrawalsstatus":null,"eventActivity_AddressID":null}
         * eventPicList : []
         * eventPicWallList : null
         * sigUpNumber : 2
         * signup_id : 614
         * sign_activitiesname : 测试
         * sign_activitiesid : 73
         * sign_activitiestype : 2
         * sign_phone : 13458069807
         * sign_logo : null
         * sign_name : 刚
         * sign_registrationfee : null
         * sign_almost : 0
         * sign_ifbringguests : false
         * sign_number : 0
         * sign_sex : 2
         * sign_cardnumber : null
         * sign_company : null
         * sign_team : null
         * sign_club : null
         * sign_member : null
         * sign_state : 2
         * sign_upstate : 1
         * sign_datetime : 2017-05-12T14:17:12
         * sign_refuse : null
         * sign_voucher : 636301954320853014
         * sign_ifallow : false
         * sign_isguset : false
         * sign_mainid : 0
         * userid : null
         * sign_GuestRoom : null
         * sign_Remark : null
         * cancelCategory : 0
         */

        private int orderid;
        private int eventactivity_id;
        private List<SignListBean> sign_list;
        private OrderBean order;
        private EventActivityBean eventActivity;
        private Object eventPicWallList;
        private int sigUpNumber;
        private int signup_id;
        private String sign_activitiesname;
        private int sign_activitiesid;
        private String sign_activitiestype;
        private String sign_phone;
        private Object sign_logo;
        private String sign_name;
        private Object sign_registrationfee;
        private int sign_almost;
        private boolean sign_ifbringguests;
        private int sign_number;
        private String sign_sex;
        private Object sign_cardnumber;
        private Object sign_company;
        private Object sign_team;
        private Object sign_club;
        private Object sign_member;
        private String sign_state;
        private String sign_upstate;
        private String sign_datetime;
        private Object sign_refuse;
        private String sign_voucher;
        private boolean sign_ifallow;
        private boolean sign_isguset;
        private int sign_mainid;
        private Object userid;
        private Object sign_GuestRoom;
        private Object sign_Remark;
        private String cancelCategory;
        private List<?> eventPicList;

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public int getEventactivity_id() {
            return eventactivity_id;
        }

        public void setEventactivity_id(int eventactivity_id) {
            this.eventactivity_id = eventactivity_id;
        }

        public List<SignListBean> getSign_list() {
            return sign_list;
        }

        public void setSign_list(List<SignListBean> sign_list) {
            this.sign_list = sign_list;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public EventActivityBean getEventActivity() {
            return eventActivity;
        }

        public void setEventActivity(EventActivityBean eventActivity) {
            this.eventActivity = eventActivity;
        }

        public Object getEventPicWallList() {
            return eventPicWallList;
        }

        public void setEventPicWallList(Object eventPicWallList) {
            this.eventPicWallList = eventPicWallList;
        }

        public int getSigUpNumber() {
            return sigUpNumber;
        }

        public void setSigUpNumber(int sigUpNumber) {
            this.sigUpNumber = sigUpNumber;
        }

        public int getSignup_id() {
            return signup_id;
        }

        public void setSignup_id(int signup_id) {
            this.signup_id = signup_id;
        }

        public String getSign_activitiesname() {
            return sign_activitiesname;
        }

        public void setSign_activitiesname(String sign_activitiesname) {
            this.sign_activitiesname = sign_activitiesname;
        }

        public int getSign_activitiesid() {
            return sign_activitiesid;
        }

        public void setSign_activitiesid(int sign_activitiesid) {
            this.sign_activitiesid = sign_activitiesid;
        }

        public String getSign_activitiestype() {
            return sign_activitiestype;
        }

        public void setSign_activitiestype(String sign_activitiestype) {
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

        public int getSign_almost() {
            return sign_almost;
        }

        public void setSign_almost(int sign_almost) {
            this.sign_almost = sign_almost;
        }

        public boolean isSign_ifbringguests() {
            return sign_ifbringguests;
        }

        public void setSign_ifbringguests(boolean sign_ifbringguests) {
            this.sign_ifbringguests = sign_ifbringguests;
        }

        public int getSign_number() {
            return sign_number;
        }

        public void setSign_number(int sign_number) {
            this.sign_number = sign_number;
        }

        public String getSign_sex() {
            return sign_sex;
        }

        public void setSign_sex(String sign_sex) {
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

        public String getSign_state() {
            return sign_state;
        }

        public void setSign_state(String sign_state) {
            this.sign_state = sign_state;
        }

        public String getSign_upstate() {
            return sign_upstate;
        }

        public void setSign_upstate(String sign_upstate) {
            this.sign_upstate = sign_upstate;
        }

        public String getSign_datetime() {
            return sign_datetime;
        }

        public void setSign_datetime(String sign_datetime) {
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

        public boolean isSign_ifallow() {
            return sign_ifallow;
        }

        public void setSign_ifallow(boolean sign_ifallow) {
            this.sign_ifallow = sign_ifallow;
        }

        public boolean isSign_isguset() {
            return sign_isguset;
        }

        public void setSign_isguset(boolean sign_isguset) {
            this.sign_isguset = sign_isguset;
        }

        public int getSign_mainid() {
            return sign_mainid;
        }

        public void setSign_mainid(int sign_mainid) {
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

        public String getCancelCategory() {
            return cancelCategory;
        }

        public void setCancelCategory(String cancelCategory) {
            this.cancelCategory = cancelCategory;
        }

        public List<?> getEventPicList() {
            return eventPicList;
        }

        public void setEventPicList(List<?> eventPicList) {
            this.eventPicList = eventPicList;
        }

        @Override
        public int getType() {
            if (sign_state.equals("4")) {//已拒绝
                return 2;
            } else if (sign_state.equals("3")) {//取消报名
                return 1;
            } else {//报名成功
                return 0;
            }
        }

        public static class SignListBean {
            /**
             * signup_id : 584
             * sign_activitiesname : 丰顺高尔夫球队季度赛
             * sign_activitiesid : 1
             * sign_activitiestype : 2
             * sign_phone : 11
             * sign_logo : null
             * sign_name : 1
             * sign_registrationfee : null
             * sign_almost : 0
             * sign_ifbringguests : null
             * sign_number : null
             * sign_sex : 2
             * sign_cardnumber : null
             * sign_company : null
             * sign_team : null
             * sign_club : null
             * sign_member : null
             * sign_state : 1
             * sign_upstate : 1
             * sign_datetime : 2017-03-30T09:08:14
             * sign_refuse : null
             * sign_voucher : 636264616943857998
             * sign_ifallow : false
             * sign_isguset : true
             * sign_mainid : 583
             * userid : 32
             * sign_GuestRoom : 1
             * sign_Remark : null
             * cancelCategory : 0
             */

            private int signup_id;
            private String sign_activitiesname;
            private int sign_activitiesid;
            private String sign_activitiestype;
            private String sign_phone;
            private Object sign_logo;
            private String sign_name;
            private Object sign_registrationfee;
            private double sign_almost;
            private Object sign_ifbringguests;
            private Object sign_number;
            private String sign_sex;
            private Object sign_cardnumber;
            private Object sign_company;
            private Object sign_team;
            private Object sign_club;
            private Object sign_member;
            private String sign_state;
            private String sign_upstate;
            private String sign_datetime;
            private Object sign_refuse;
            private String sign_voucher;
            private boolean sign_ifallow;
            private boolean sign_isguset;
            private int sign_mainid;
            private int userid;
            private String sign_GuestRoom;
            private Object sign_Remark;
            private String cancelCategory;

            public int getSignup_id() {
                return signup_id;
            }

            public void setSignup_id(int signup_id) {
                this.signup_id = signup_id;
            }

            public String getSign_activitiesname() {
                return sign_activitiesname;
            }

            public void setSign_activitiesname(String sign_activitiesname) {
                this.sign_activitiesname = sign_activitiesname;
            }

            public int getSign_activitiesid() {
                return sign_activitiesid;
            }

            public void setSign_activitiesid(int sign_activitiesid) {
                this.sign_activitiesid = sign_activitiesid;
            }

            public String getSign_activitiestype() {
                return sign_activitiestype;
            }

            public void setSign_activitiestype(String sign_activitiestype) {
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

            public double getSign_almost() {
                return sign_almost;
            }

            public void setSign_almost(double sign_almost) {
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

            public String getSign_sex() {
                return sign_sex;
            }

            public void setSign_sex(String sign_sex) {
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

            public String getSign_state() {
                return sign_state;
            }

            public void setSign_state(String sign_state) {
                this.sign_state = sign_state;
            }

            public String getSign_upstate() {
                return sign_upstate;
            }

            public void setSign_upstate(String sign_upstate) {
                this.sign_upstate = sign_upstate;
            }

            public String getSign_datetime() {
                return sign_datetime;
            }

            public void setSign_datetime(String sign_datetime) {
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

            public boolean isSign_ifallow() {
                return sign_ifallow;
            }

            public void setSign_ifallow(boolean sign_ifallow) {
                this.sign_ifallow = sign_ifallow;
            }

            public boolean isSign_isguset() {
                return sign_isguset;
            }

            public void setSign_isguset(boolean sign_isguset) {
                this.sign_isguset = sign_isguset;
            }

            public int getSign_mainid() {
                return sign_mainid;
            }

            public void setSign_mainid(int sign_mainid) {
                this.sign_mainid = sign_mainid;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public String getSign_GuestRoom() {
                return sign_GuestRoom;
            }

            public void setSign_GuestRoom(String sign_GuestRoom) {
                this.sign_GuestRoom = sign_GuestRoom;
            }

            public Object getSign_Remark() {
                return sign_Remark;
            }

            public void setSign_Remark(Object sign_Remark) {
                this.sign_Remark = sign_Remark;
            }

            public String getCancelCategory() {
                return cancelCategory;
            }

            public void setCancelCategory(String cancelCategory) {
                this.cancelCategory = cancelCategory;
            }
        }

        public static class OrderBean {
            /**
             * order_id : 0
             * order_flowno : null
             * order_totalprice : 0
             * order_paymenttype :
             * order_category :
             * order_state :
             * order_remark : null
             * order_datetime : null
             * order_usernametel : null
             * order_username : null
             * order_userid : null
             * order_discount : 0
             * order_coach : 0
             * order_coachgroup : 0
             * eventactivity_id : null
             * signup_id : 0
             * hedging_oid : 0
             * appid : null
             * withdrawalsstatus : 0
             */

            private int order_id;
            private Object order_flowno;
            private int order_totalprice;
            private String order_paymenttype;
            private String order_category;
            private String order_state;
            private Object order_remark;
            private Object order_datetime;
            private Object order_usernametel;
            private Object order_username;
            private Object order_userid;
            private int order_discount;
            private int order_coach;
            private int order_coachgroup;
            private Object eventactivity_id;
            private int signup_id;
            private int hedging_oid;
            private Object appid;
            private int withdrawalsstatus;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public Object getOrder_flowno() {
                return order_flowno;
            }

            public void setOrder_flowno(Object order_flowno) {
                this.order_flowno = order_flowno;
            }

            public int getOrder_totalprice() {
                return order_totalprice;
            }

            public void setOrder_totalprice(int order_totalprice) {
                this.order_totalprice = order_totalprice;
            }

            public String getOrder_paymenttype() {
                return order_paymenttype;
            }

            public void setOrder_paymenttype(String order_paymenttype) {
                this.order_paymenttype = order_paymenttype;
            }

            public String getOrder_category() {
                return order_category;
            }

            public void setOrder_category(String order_category) {
                this.order_category = order_category;
            }

            public String getOrder_state() {
                return order_state;
            }

            public void setOrder_state(String order_state) {
                this.order_state = order_state;
            }

            public Object getOrder_remark() {
                return order_remark;
            }

            public void setOrder_remark(Object order_remark) {
                this.order_remark = order_remark;
            }

            public Object getOrder_datetime() {
                return order_datetime;
            }

            public void setOrder_datetime(Object order_datetime) {
                this.order_datetime = order_datetime;
            }

            public Object getOrder_usernametel() {
                return order_usernametel;
            }

            public void setOrder_usernametel(Object order_usernametel) {
                this.order_usernametel = order_usernametel;
            }

            public Object getOrder_username() {
                return order_username;
            }

            public void setOrder_username(Object order_username) {
                this.order_username = order_username;
            }

            public Object getOrder_userid() {
                return order_userid;
            }

            public void setOrder_userid(Object order_userid) {
                this.order_userid = order_userid;
            }

            public int getOrder_discount() {
                return order_discount;
            }

            public void setOrder_discount(int order_discount) {
                this.order_discount = order_discount;
            }

            public int getOrder_coach() {
                return order_coach;
            }

            public void setOrder_coach(int order_coach) {
                this.order_coach = order_coach;
            }

            public int getOrder_coachgroup() {
                return order_coachgroup;
            }

            public void setOrder_coachgroup(int order_coachgroup) {
                this.order_coachgroup = order_coachgroup;
            }

            public Object getEventactivity_id() {
                return eventactivity_id;
            }

            public void setEventactivity_id(Object eventactivity_id) {
                this.eventactivity_id = eventactivity_id;
            }

            public int getSignup_id() {
                return signup_id;
            }

            public void setSignup_id(int signup_id) {
                this.signup_id = signup_id;
            }

            public int getHedging_oid() {
                return hedging_oid;
            }

            public void setHedging_oid(int hedging_oid) {
                this.hedging_oid = hedging_oid;
            }

            public Object getAppid() {
                return appid;
            }

            public void setAppid(Object appid) {
                this.appid = appid;
            }

            public int getWithdrawalsstatus() {
                return withdrawalsstatus;
            }

            public void setWithdrawalsstatus(int withdrawalsstatus) {
                this.withdrawalsstatus = withdrawalsstatus;
            }
        }

        public static class EventActivityBean {
            /**
             * listPic : []
             * listSignUp : []
             * eventActivity_id : 0
             * eventactivity_name : null
             * eventactivity_begintime : null
             * eventactivity_endtime : null
             * eventactivity_detail : null
             * eventactivity_number : null
             * eventactivity_type : null
             * eventactivity_state : null
             * eventactivity_address : null
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

            private int eventActivity_id;
            private Object eventactivity_name;
            private Object eventactivity_begintime;
            private Object eventactivity_endtime;
            private Object eventactivity_detail;
            private Object eventactivity_number;
            private Object eventactivity_type;
            private Object eventactivity_state;
            private Object eventactivity_address;
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
            private Object eventactivity_costtype;
            private Object eventactivity_prepayment;
            private Object eventactivity_cost;
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
            private List<?> listSignUp;

            public int getEventActivity_id() {
                return eventActivity_id;
            }

            public void setEventActivity_id(int eventActivity_id) {
                this.eventActivity_id = eventActivity_id;
            }

            public Object getEventactivity_name() {
                return eventactivity_name;
            }

            public void setEventactivity_name(Object eventactivity_name) {
                this.eventactivity_name = eventactivity_name;
            }

            public Object getEventactivity_begintime() {
                return eventactivity_begintime;
            }

            public void setEventactivity_begintime(Object eventactivity_begintime) {
                this.eventactivity_begintime = eventactivity_begintime;
            }

            public Object getEventactivity_endtime() {
                return eventactivity_endtime;
            }

            public void setEventactivity_endtime(Object eventactivity_endtime) {
                this.eventactivity_endtime = eventactivity_endtime;
            }

            public Object getEventactivity_detail() {
                return eventactivity_detail;
            }

            public void setEventactivity_detail(Object eventactivity_detail) {
                this.eventactivity_detail = eventactivity_detail;
            }

            public Object getEventactivity_number() {
                return eventactivity_number;
            }

            public void setEventactivity_number(Object eventactivity_number) {
                this.eventactivity_number = eventactivity_number;
            }

            public Object getEventactivity_type() {
                return eventactivity_type;
            }

            public void setEventactivity_type(Object eventactivity_type) {
                this.eventactivity_type = eventactivity_type;
            }

            public Object getEventactivity_state() {
                return eventactivity_state;
            }

            public void setEventactivity_state(Object eventactivity_state) {
                this.eventactivity_state = eventactivity_state;
            }

            public Object getEventactivity_address() {
                return eventactivity_address;
            }

            public void setEventactivity_address(Object eventactivity_address) {
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

            public Object getEventactivity_costtype() {
                return eventactivity_costtype;
            }

            public void setEventactivity_costtype(Object eventactivity_costtype) {
                this.eventactivity_costtype = eventactivity_costtype;
            }

            public Object getEventactivity_prepayment() {
                return eventactivity_prepayment;
            }

            public void setEventactivity_prepayment(Object eventactivity_prepayment) {
                this.eventactivity_prepayment = eventactivity_prepayment;
            }

            public Object getEventactivity_cost() {
                return eventactivity_cost;
            }

            public void setEventactivity_cost(Object eventactivity_cost) {
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

            public List<?> getListSignUp() {
                return listSignUp;
            }

            public void setListSignUp(List<?> listSignUp) {
                this.listSignUp = listSignUp;
            }
        }
    }
}
