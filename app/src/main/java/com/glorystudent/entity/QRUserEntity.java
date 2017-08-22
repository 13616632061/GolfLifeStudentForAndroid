package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/3/8.
 */
public class QRUserEntity {

    /**
     * listUsers : [{"userid":84,"username":"张鲁","username_en":"kk","gender":"1","loginname":null,"password":"A9B8B9C146F9F8A800A4320C4514EE62","phonenumber":"18680178858","birthday":"2016-11-29T00:00:00","openid":"o0_oxv59dTg5yjw-QbK7x68ClVik","joindate":"2016-11-29T17:54:01","status":"1","email":null,"qq":null,"golfage":3,"par":0,"technicallevelid":12,"customerphoto":"http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png","usertype":"1","roleid":24,"chinacity_id":0,"chinacity_name":"中国 广东省 深圳市","uuid":"34c02360-ef78-11e6-a373-71cd516344a6","fromuserid":0,"fromrode":null,"jgpushid":"141fe1da9ea7fead7c6","payopenid":""}]
     * status : 2~00~00
     * listfriends : null
     * userid : null
     * groupid : null
     * userinfo : null
     * listContractuser : null
     * version : 1.1.106
     * datetime : 2017/3/8 10:50:40
     * accesstoken : QwmMbG0f58T2ikXFq1NfU+r+/Jhnzjj0NdnAu5aFI43JDCkBWTfBuBv1bLDUMpndT13mRBDvGuT8+CQaohKfc0QVVmTX893DhEV22vTBdBhImRCBbr0pTi2eKL8LeZg84KRi9R6KpIpji5LxrPcjSANNKdR9CPdOK0XblW/TETh+b3U9ApZaGmf7kzeawNoDwOIRzvz74Hvqu4/bRe9QGlTwoOKPm1Uo5nqo63wxS5MFy+Bn4FCkX3Qr5Qg9aCU1L7XML+gcfkHYZE8ZV091ovBJoBuNBA0JRRXqff6XJQhnxJXoHmT2HdBP4zM/0Iaj
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 1
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private String status;
    private Object listfriends;
    private Object userid;
    private Object groupid;
    private Object userinfo;
    private Object listContractuser;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListUsersBean> listUsers;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getListfriends() {
        return listfriends;
    }

    public void setListfriends(Object listfriends) {
        this.listfriends = listfriends;
    }

    public Object getUserid() {
        return userid;
    }

    public void setUserid(Object userid) {
        this.userid = userid;
    }

    public Object getGroupid() {
        return groupid;
    }

    public void setGroupid(Object groupid) {
        this.groupid = groupid;
    }

    public Object getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Object userinfo) {
        this.userinfo = userinfo;
    }

    public Object getListContractuser() {
        return listContractuser;
    }

    public void setListContractuser(Object listContractuser) {
        this.listContractuser = listContractuser;
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

    public List<ListUsersBean> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<ListUsersBean> listUsers) {
        this.listUsers = listUsers;
    }

    public static class ListUsersBean {
        /**
         * userid : 84
         * username : 张鲁
         * username_en : kk
         * gender : 1
         * loginname : null
         * password : A9B8B9C146F9F8A800A4320C4514EE62
         * phonenumber : 18680178858
         * birthday : 2016-11-29T00:00:00
         * openid : o0_oxv59dTg5yjw-QbK7x68ClVik
         * joindate : 2016-11-29T17:54:01
         * status : 1
         * email : null
         * qq : null
         * golfage : 3.0
         * par : 0
         * technicallevelid : 12
         * customerphoto : http://app.pgagolf.cn/images/headfiles/2017-2-20/(49)icon.png
         * usertype : 1
         * roleid : 24
         * chinacity_id : 0
         * chinacity_name : 中国 广东省 深圳市
         * uuid : 34c02360-ef78-11e6-a373-71cd516344a6
         * fromuserid : 0
         * fromrode : null
         * jgpushid : 141fe1da9ea7fead7c6
         * payopenid :
         */

        private int userid;
        private String username;
        private String username_en;
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
        private String customerphoto;
        private String usertype;
        private int roleid;
        private int chinacity_id;
        private String chinacity_name;
        private String uuid;
        private int fromuserid;
        private Object fromrode;
        private String jgpushid;
        private String payopenid;

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

        public String getUsername_en() {
            return username_en;
        }

        public void setUsername_en(String username_en) {
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

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
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

        public String getChinacity_name() {
            return chinacity_name;
        }

        public void setChinacity_name(String chinacity_name) {
            this.chinacity_name = chinacity_name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getFromuserid() {
            return fromuserid;
        }

        public void setFromuserid(int fromuserid) {
            this.fromuserid = fromuserid;
        }

        public Object getFromrode() {
            return fromrode;
        }

        public void setFromrode(Object fromrode) {
            this.fromrode = fromrode;
        }

        public String getJgpushid() {
            return jgpushid;
        }

        public void setJgpushid(String jgpushid) {
            this.jgpushid = jgpushid;
        }

        public String getPayopenid() {
            return payopenid;
        }

        public void setPayopenid(String payopenid) {
            this.payopenid = payopenid;
        }
    }
}
