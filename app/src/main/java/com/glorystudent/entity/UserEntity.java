package com.glorystudent.entity;

/**
 * Created by hyj on 2016/12/16.
 */
public class UserEntity {

    /**
     * userinfo : {"userid":103,"username":null,"username_en":null,"gender":"1","loginname":null,"password":"A906449D5769FA7361D7ECC6AA3F6D28","phonenumber":"18219140229","birthday":"2016-12-16T12:46:16","openid":null,"joindate":"2016-12-16T12:46:16","status":"1","email":null,"qq":null,"golfage":0,"par":0,"technicallevelid":12,"customerphoto":null,"usertype":"0","roleid":24,"chinacity_id":0,"chinacity_name":null,"uuid":"33a34f40-c366-11e6-a185-eb8abceac359"}
     * userid : ljJwHBxQqshLqYV/7LPeWQ==
     * groupid : dQ854iRixVyBvpHYJqxSKA==
     * version : null
     * datetime : 2016/12/16 16:56:59
     * accesstoken : luFf65RPvRm8liuLjJfZrfC5DWWV/7rwVNkbGZ+11kxPH/bYqXXncfzhz6KDSazViuosNu8LktY34MZESQ6vJ/HBHhOrWtnDjd0dDTEG4MhY+FZWclYyLF+nUBRrX7KCdB95SbgQtXMDES8k35EhkR7B1lZxmaWGVfizqSIddIPthN6zTVB5ADs/LgKqYFFfZ3PbdJYGTJ8BKGajve1rcPZ/lPtU1Lhu0jl8qUB+3o9E33/0imOgp8DAq5psOzT8BiO3NltRxelKKEz8xfF4WR6HoU48xmxxMzq6Wx3AKIJpQwN9ZqJg4bg17W+zGByG
     * statuscode : 1
     * statusmessage : 登录成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private UserinfoBean userinfo;
    private String userid;
    private String groupid;
    private Object version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
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

    public static class UserinfoBean {
        /**
         * userid : 103
         * username : null
         * username_en : null
         * gender : 1
         * loginname : null
         * password : A906449D5769FA7361D7ECC6AA3F6D28
         * phonenumber : 18219140229
         * birthday : 2016-12-16T12:46:16
         * openid : null
         * joindate : 2016-12-16T12:46:16
         * status : 1
         * email : null
         * qq : null
         * golfage : 0.0
         * par : 0
         * technicallevelid : 12
         * customerphoto : null
         * usertype : 0
         * roleid : 24
         * chinacity_id : 0
         * chinacity_name : null
         * uuid : 33a34f40-c366-11e6-a185-eb8abceac359
         */

        private int userid;
        private Object username;
        private Object username_en;
        private String gender;
        private Object loginname;
        private String password;
        private String phonenumber;
        private String birthday;
        private String openid;
        private String joindate;
        private String status;
        private Object email;
        private Object qq;
        private double golfage;
        private int par;
        private int technicallevelid;
        private Object customerphoto;
        private String usertype;
        private int roleid;
        private int chinacity_id;
        private Object chinacity_name;
        private String uuid;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
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

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getJoindate() {
            return joindate;
        }

        public void setJoindate(String joindate) {
            this.joindate = joindate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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

        public double getGolfage() {
            return golfage;
        }

        public void setGolfage(double golfage) {
            this.golfage = golfage;
        }

        public int getPar() {
            return par;
        }

        public void setPar(int par) {
            this.par = par;
        }

        public int getTechnicallevelid() {
            return technicallevelid;
        }

        public void setTechnicallevelid(int technicallevelid) {
            this.technicallevelid = technicallevelid;
        }

        public Object getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(Object customerphoto) {
            this.customerphoto = customerphoto;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public int getRoleid() {
            return roleid;
        }

        public void setRoleid(int roleid) {
            this.roleid = roleid;
        }

        public int getChinacity_id() {
            return chinacity_id;
        }

        public void setChinacity_id(int chinacity_id) {
            this.chinacity_id = chinacity_id;
        }

        public Object getChinacity_name() {
            return chinacity_name;
        }

        public void setChinacity_name(Object chinacity_name) {
            this.chinacity_name = chinacity_name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
