package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/4/21.
 */
public class ContractTraineeEntity implements Serializable{

    /**
     * listContractTrainee : null
     * listContractUserExt : [{"friends":{"friendid":1267,"userid":1212,"friend_userid":84,"relationshiptime":"1753-01-01T00:00:00","groupid":18,"lastcontacttime":"1753-01-01T00:00:00","remark":"Aww","studentstatus":1,"username":null,"friend_username":null,"friendremark":null,"friend_HeadPic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png"},"packageNum":3,"contracttraineeid":3,"contracttraineeuserid":84,"contracttraineename":"kk","contracttraineegender":"1","customerphoto":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","phonenumber":"18680178858","chinacityname":"中国 广东省 深圳市","coachid":1212,"coachname":"何跃进       ","contracttraineetype":"1","contracttraineestatus":"1","createdatetime":"2017-04-21T17:32:39","remark":null},{"friends":null,"packageNum":3,"contracttraineeid":8,"contracttraineeuserid":1141,"contracttraineename":"何跃进","contracttraineegender":"1","customerphoto":"http://wx.qlogo.cn/mmopen/xLIHPEDcjCTjVUJP3NBGVJYo7WMgpdswxJTGgmVxkCRsiaDEEia7CegiaF5SFPNwkXoDo8Ue5d0HlJQ5rRKG2soiaQluJnqzKqIv/0","phonenumber":"15502047481","chinacityname":"河北省  石家庄市  裕华区","coachid":1212,"coachname":"何跃进       ","contracttraineetype":null,"contracttraineestatus":"1","createdatetime":"2017-04-21T18:43:25","remark":null},{"friends":null,"packageNum":0,"contracttraineeid":13,"contracttraineeuserid":1223,"contracttraineename":"kk","contracttraineegender":"1","customerphoto":"http://app.pgagolf.cn/img/newUser.jpg","phonenumber":"18680178850","chinacityname":null,"coachid":1212,"coachname":"何跃进       ","contracttraineetype":null,"contracttraineestatus":"1","createdatetime":"2017-04-21T18:45:37","remark":null},{"friends":null,"packageNum":0,"contracttraineeid":14,"contracttraineeuserid":1226,"contracttraineename":"kk","contracttraineegender":null,"customerphoto":null,"phonenumber":"18680178850","chinacityname":null,"coachid":1212,"coachname":"何跃进       ","contracttraineetype":null,"contracttraineestatus":"1","createdatetime":"2017-04-21T18:50:27","remark":null},{"friends":{"friendid":1267,"userid":1212,"friend_userid":84,"relationshiptime":"1753-01-01T00:00:00","groupid":18,"lastcontacttime":"1753-01-01T00:00:00","remark":"Aww","studentstatus":1,"username":null,"friend_username":null,"friendremark":null,"friend_HeadPic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png"},"packageNum":3,"contracttraineeid":15,"contracttraineeuserid":84,"contracttraineename":"kk","contracttraineegender":"1","customerphoto":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","phonenumber":"18680178858","chinacityname":"中国 广东省 深圳市","coachid":1212,"coachname":"何跃进       ","contracttraineetype":null,"contracttraineestatus":"1","createdatetime":"2017-04-21T19:15:58","remark":null}]
     * version : 1.1.106
     * datetime : 2017/4/21 19:17:35
     * accesstoken : NqOSdMU4ONSIqW1pIKarB2eZHO962NhnySLr1uR7/GADqixLMfV20xcNqr7cD6OYuqRVddQy4N0isQMzd+XhzeG6jlGLMqFiTqsMMHT8o74xypgYmTLFj8L/sGDquPxjGcSBT7jfhhXqAU9KiYtLRkU1tE7CDVYtt9csEs30X7dVR0VxrK6Ij49tsMqY/GLrO/ueJBelwEmVF+wTtPozD6I1dJV3IZ8v32Y4ZyzlrYPyjB4B1iWEb8LzF0KcV0VhXS+RcFMgMGUQykVwBhNJg1asAKH48rtDaUAk/Fhh+EzDEo4S4Wt8aJPxeylL4T4kWjsR+kL1T6aYKs7NVWuo+Q==
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object listContractTrainee;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListContractUserExtBean> listContractUserExt;

    public Object getListContractTrainee() {
        return listContractTrainee;
    }

    public void setListContractTrainee(Object listContractTrainee) {
        this.listContractTrainee = listContractTrainee;
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

    public List<ListContractUserExtBean> getListContractUserExt() {
        return listContractUserExt;
    }

    public void setListContractUserExt(List<ListContractUserExtBean> listContractUserExt) {
        this.listContractUserExt = listContractUserExt;
    }

    public static class ListContractUserExtBean implements Serializable{
        /**
         * friends : {"friendid":1267,"userid":1212,"friend_userid":84,"relationshiptime":"1753-01-01T00:00:00","groupid":18,"lastcontacttime":"1753-01-01T00:00:00","remark":"Aww","studentstatus":1,"username":null,"friend_username":null,"friendremark":null,"friend_HeadPic":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png"}
         * packageNum : 3
         * contracttraineeid : 3
         * contracttraineeuserid : 84
         * contracttraineename : kk
         * contracttraineegender : 1
         * customerphoto : http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png
         * phonenumber : 18680178858
         * chinacityname : 中国 广东省 深圳市
         * coachid : 1212
         * coachname : 何跃进
         * contracttraineetype : 1
         * contracttraineestatus : 1
         * createdatetime : 2017-04-21T17:32:39
         * remark : null
         */

        private FriendsBean friends;
        private int packageNum;
        private int contracttraineeid;
        private int contracttraineeuserid;
        private String contracttraineename;
        private String contracttraineegender;
        private String customerphoto;
        private String phonenumber;
        private String chinacityname;
        private int coachid;
        private String coachname;
        private String contracttraineetype;
        private String contracttraineestatus;
        private String createdatetime;
        private String remark;

        public FriendsBean getFriends() {
            return friends;
        }

        public void setFriends(FriendsBean friends) {
            this.friends = friends;
        }

        public int getPackageNum() {
            return packageNum;
        }

        public void setPackageNum(int packageNum) {
            this.packageNum = packageNum;
        }

        public int getContracttraineeid() {
            return contracttraineeid;
        }

        public void setContracttraineeid(int contracttraineeid) {
            this.contracttraineeid = contracttraineeid;
        }

        public int getContracttraineeuserid() {
            return contracttraineeuserid;
        }

        public void setContracttraineeuserid(int contracttraineeuserid) {
            this.contracttraineeuserid = contracttraineeuserid;
        }

        public String getContracttraineename() {
            return contracttraineename;
        }

        public void setContracttraineename(String contracttraineename) {
            this.contracttraineename = contracttraineename;
        }

        public String getContracttraineegender() {
            return contracttraineegender;
        }

        public void setContracttraineegender(String contracttraineegender) {
            this.contracttraineegender = contracttraineegender;
        }

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getChinacityname() {
            return chinacityname;
        }

        public void setChinacityname(String chinacityname) {
            this.chinacityname = chinacityname;
        }

        public int getCoachid() {
            return coachid;
        }

        public void setCoachid(int coachid) {
            this.coachid = coachid;
        }

        public String getCoachname() {
            return coachname;
        }

        public void setCoachname(String coachname) {
            this.coachname = coachname;
        }

        public String getContracttraineetype() {
            return contracttraineetype;
        }

        public void setContracttraineetype(String contracttraineetype) {
            this.contracttraineetype = contracttraineetype;
        }

        public String getContracttraineestatus() {
            return contracttraineestatus;
        }

        public void setContracttraineestatus(String contracttraineestatus) {
            this.contracttraineestatus = contracttraineestatus;
        }

        public String getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(String createdatetime) {
            this.createdatetime = createdatetime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public static class FriendsBean implements Serializable{
            /**
             * friendid : 1267
             * userid : 1212
             * friend_userid : 84
             * relationshiptime : 1753-01-01T00:00:00
             * groupid : 18
             * lastcontacttime : 1753-01-01T00:00:00
             * remark : Aww
             * studentstatus : 1
             * username : null
             * friend_username : null
             * friendremark : null
             * friend_HeadPic : http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png
             */

            private int friendid;
            private int userid;
            private int friend_userid;
            private String relationshiptime;
            private int groupid;
            private String lastcontacttime;
            private String remark;
            private int studentstatus;
            private Object username;
            private Object friend_username;
            private Object friendremark;
            private String friend_HeadPic;

            public int getFriendid() {
                return friendid;
            }

            public void setFriendid(int friendid) {
                this.friendid = friendid;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public int getFriend_userid() {
                return friend_userid;
            }

            public void setFriend_userid(int friend_userid) {
                this.friend_userid = friend_userid;
            }

            public String getRelationshiptime() {
                return relationshiptime;
            }

            public void setRelationshiptime(String relationshiptime) {
                this.relationshiptime = relationshiptime;
            }

            public int getGroupid() {
                return groupid;
            }

            public void setGroupid(int groupid) {
                this.groupid = groupid;
            }

            public String getLastcontacttime() {
                return lastcontacttime;
            }

            public void setLastcontacttime(String lastcontacttime) {
                this.lastcontacttime = lastcontacttime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStudentstatus() {
                return studentstatus;
            }

            public void setStudentstatus(int studentstatus) {
                this.studentstatus = studentstatus;
            }

            public Object getUsername() {
                return username;
            }

            public void setUsername(Object username) {
                this.username = username;
            }

            public Object getFriend_username() {
                return friend_username;
            }

            public void setFriend_username(Object friend_username) {
                this.friend_username = friend_username;
            }

            public Object getFriendremark() {
                return friendremark;
            }

            public void setFriendremark(Object friendremark) {
                this.friendremark = friendremark;
            }

            public String getFriend_HeadPic() {
                return friend_HeadPic;
            }

            public void setFriend_HeadPic(String friend_HeadPic) {
                this.friend_HeadPic = friend_HeadPic;
            }
        }
    }
}
