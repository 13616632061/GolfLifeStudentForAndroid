package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/2/15.
 */
public class UserInformationEntity {

    /**
     * listUsers : [{"userid":111,"username":"何跃进","username_en":null,"gender":"2","loginname":null,"password":"A9B8B9C146F9F8A800A4320C4514EE62","phonenumber":"18219140229","birthday":"2016-12-28T11:31:41","openid":null,"joindate":"2016-12-28T11:31:41","status":"1","email":null,"qq":null,"golfage":4,"par":0,"technicallevelid":12,"customerphoto":"https://app.pgagolf.cn/images/headfiles/2017-1-4/1483544616141.jpg","usertype":"1","roleid":24,"chinacity_id":0,"chinacity_name":"内蒙古自治区 包头市 东河区","uuid":"c54c8940-cec7-11e6-a91f-17757917ee0a","fromuserid":0,"fromrode":null,"jgpushid":"160a3797c8083c13077"}]
     * status : null
     * listfriends : null
     * userid : null
     * groupid : null
     * userinfo : null
     * listContractuser : null
     * version : 1.0.3
     * datetime : 2017/2/15 10:38:29
     * accesstoken : QwmMbG0f58T2ikXFq1NfU78LPCJeV8is3LJHec0UL03yS470UArFPu7fuB0PYTNYe9JsTcEgZcx3euHb/uqtrzlRKOj7nHoSBUVCKeBet82y1ZqZAmhglNBU0mQKNloYcwgMdxw6SG/egyzgH1t/EqA5yOiYvAWBU/SaG06a+qT4PGNb2c3DNsDmUg5EGExNVhPWV1PX25J7dGfbYeXqAT49O6NsOXkGmC0QrLkMPW3+Xr3TNHHaJ3eJSdFi3Rd9LEJMRvvlFBf8UceXHfyk5WuFYr0sHAYDC0WuZXRXs5E7TvRGlGgmU1TooCY1BZY/
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 1
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object status;
    private Object listfriends;
    private Object userid;
    private String groupid;
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
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

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
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
         * userid : 111
         * username : 何跃进
         * username_en : null
         * gender : 2
         * loginname : null
         * password : A9B8B9C146F9F8A800A4320C4514EE62
         * phonenumber : 18219140229
         * birthday : 2016-12-28T11:31:41
         * openid : null
         * joindate : 2016-12-28T11:31:41
         * status : 1
         * email : null
         * qq : null
         * golfage : 4.0
         * par : 0
         * technicallevelid : 12
         * customerphoto : https://app.pgagolf.cn/images/headfiles/2017-1-4/1483544616141.jpg
         * usertype : 1
         * roleid : 24
         * chinacity_id : 0
         * chinacity_name : 内蒙古自治区 包头市 东河区
         * uuid : c54c8940-cec7-11e6-a91f-17757917ee0a
         * fromuserid : 0
         * fromrode : null
         * jgpushid : 160a3797c8083c13077
         */

        private int userid;
        private String username;
        private Object username_en;
        private String gender;
        private Object loginname;
        private String password;
        private String phonenumber;
        private String birthday;
        private Object openid;
        private String joindate;
        private String status;
        private Object email;
        private Object qq;
        private int golfage;
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

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
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

        public int getGolfage() {
            return golfage;
        }

        public void setGolfage(int golfage) {
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
    }
}
