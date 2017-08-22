package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/6.
 */
public class FriendsProfileEntity {

    /**
     * user : {"isMe":false,"isFriend":true,"remarkName":null,"userid":1255,"username":"就这样","username_en":null,"gender":"1","loginname":null,"password":null,"phonenumber":"13787108947","birthday":"2017-05-25T13:37:08","openid":null,"joindate":null,"status":null,"email":null,"qq":null,"golfage":null,"par":null,"technicallevelid":null,"customerphoto":"https://192.168.1.168:4431/images/headfiles/2017-5-25/1495690719485.png","usertype":null,"roleid":null,"chinacity_id":null,"chinacity_name":null,"uuid":null,"fromuserid":null,"fromrode":null,"jgpushid":null,"payopenid":null,"signature":null,"iSChangZhu":null}
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

    private UserBean user;
    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
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

    public static class UserBean {
        /**
         * isMe : false
         * isFriend : true
         * remarkName : null
         * userid : 1255
         * username : 就这样
         * username_en : null
         * gender : 1
         * loginname : null
         * password : null
         * phonenumber : 13787108947
         * birthday : 2017-05-25T13:37:08
         * openid : null
         * joindate : null
         * status : null
         * email : null
         * qq : null
         * golfage : null
         * par : null
         * technicallevelid : null
         * customerphoto : https://192.168.1.168:4431/images/headfiles/2017-5-25/1495690719485.png
         * usertype : null
         * roleid : null
         * chinacity_id : null
         * chinacity_name : null
         * uuid : null
         * fromuserid : null
         * fromrode : null
         * jgpushid : null
         * payopenid : null
         * signature : null
         * iSChangZhu : null
         */

        private boolean isMe;
        private boolean isFriend;
        private String remarkName;
        private int userid;
        private String username;
        private Object username_en;
        private String gender;
        private Object loginname;
        private Object password;
        private String phonenumber;
        private String birthday;
        private Object openid;
        private Object joindate;
        private Object status;
        private Object email;
        private Object qq;
        private String golfage;
        private Object par;
        private Object technicallevelid;
        private String customerphoto;
        private Object usertype;
        private Object roleid;
        private Object chinacity_id;
        private String chinacity_name;
        private Object uuid;
        private Object fromuserid;
        private Object fromrode;
        private Object jgpushid;
        private Object payopenid;
        private Object signature;
        private Object iSChangZhu;

        public boolean isIsMe() {
            return isMe;
        }

        public void setIsMe(boolean isMe) {
            this.isMe = isMe;
        }

        public boolean isIsFriend() {
            return isFriend;
        }

        public void setIsFriend(boolean isFriend) {
            this.isFriend = isFriend;
        }

        public String getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(String remarkName) {
            this.remarkName = remarkName;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getUsername_en() {
            return username_en;
        }

        public void setUsername_en(Object username_en) {
            this.username_en = username_en;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getLoginname() {
            return loginname;
        }

        public void setLoginname(Object loginname) {
            this.loginname = loginname;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getJoindate() {
            return joindate;
        }

        public void setJoindate(Object joindate) {
            this.joindate = joindate;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public String getGolfage() {
            return golfage;
        }

        public void setGolfage(String golfage) {
            this.golfage = golfage;
        }

        public Object getPar() {
            return par;
        }

        public void setPar(Object par) {
            this.par = par;
        }

        public Object getTechnicallevelid() {
            return technicallevelid;
        }

        public void setTechnicallevelid(Object technicallevelid) {
            this.technicallevelid = technicallevelid;
        }

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }

        public Object getUsertype() {
            return usertype;
        }

        public void setUsertype(Object usertype) {
            this.usertype = usertype;
        }

        public Object getRoleid() {
            return roleid;
        }

        public void setRoleid(Object roleid) {
            this.roleid = roleid;
        }

        public Object getChinacity_id() {
            return chinacity_id;
        }

        public void setChinacity_id(Object chinacity_id) {
            this.chinacity_id = chinacity_id;
        }

        public String getChinacity_name() {
            return chinacity_name;
        }

        public void setChinacity_name(String chinacity_name) {
            this.chinacity_name = chinacity_name;
        }

        public Object getUuid() {
            return uuid;
        }

        public void setUuid(Object uuid) {
            this.uuid = uuid;
        }

        public Object getFromuserid() {
            return fromuserid;
        }

        public void setFromuserid(Object fromuserid) {
            this.fromuserid = fromuserid;
        }

        public Object getFromrode() {
            return fromrode;
        }

        public void setFromrode(Object fromrode) {
            this.fromrode = fromrode;
        }

        public Object getJgpushid() {
            return jgpushid;
        }

        public void setJgpushid(Object jgpushid) {
            this.jgpushid = jgpushid;
        }

        public Object getPayopenid() {
            return payopenid;
        }

        public void setPayopenid(Object payopenid) {
            this.payopenid = payopenid;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public Object getISChangZhu() {
            return iSChangZhu;
        }

        public void setISChangZhu(Object iSChangZhu) {
            this.iSChangZhu = iSChangZhu;
        }
    }
}
