package com.glorystudent.entity;

import java.util.List;

/**
 * 意向学员
 * Created by hyj on 2017/1/9.
 */
public class OpinionUserEntity {

    /**
     * listusers : [{"userid":1148,"username":"何跃进2","username_en":null,"gender":"1","loginname":null,"password":"A9B8B9C146F9F8A800A4320C4514EE62","phonenumber":"18219140226","birthday":"2017-01-09T10:42:10","openid":null,"joindate":"2017-01-09T10:42:10","status":"1","email":null,"qq":null,"golfage":0,"par":0,"technicallevelid":12,"customerphoto":null,"usertype":"0","roleid":24,"chinacity_id":0,"chinacity_name":null,"uuid":null,"fromuserid":0,"fromrode":null,"jgpushid":null},{"userid":1150,"username":"我的天","username_en":null,"gender":"1","loginname":null,"password":"A9B8B9C146F9F8A800A4320C4514EE62","phonenumber":"18219140224","birthday":"2017-01-09T11:09:23","openid":null,"joindate":"2017-01-09T11:09:23","status":"1","email":null,"qq":null,"golfage":0,"par":0,"technicallevelid":12,"customerphoto":null,"usertype":"0","roleid":24,"chinacity_id":0,"chinacity_name":null,"uuid":null,"fromuserid":0,"fromrode":null,"jgpushid":null},{"userid":1152,"username":"何跃进意向","username_en":null,"gender":"1","loginname":null,"password":"A9B8B9C146F9F8A800A4320C4514EE62","phonenumber":"18219140222","birthday":"2017-01-09T11:20:37","openid":null,"joindate":"2017-01-09T11:20:37","status":"1","email":null,"qq":null,"golfage":0,"par":0,"technicallevelid":12,"customerphoto":null,"usertype":"0","roleid":24,"chinacity_id":0,"chinacity_name":null,"uuid":null,"fromuserid":0,"fromrode":null,"jgpushid":null}]
     * listfriends : [{"friendid":1078,"userid":111,"friend_userid":1148,"relationshiptime":"2017-01-09T10:42:10","groupid":2,"lastcontacttime":"2017-01-09T10:42:10","remark":"何跃进2","studentstatus":2,"username":"何跃进2","friend_username":"何跃进2","friendremark":null},{"friendid":1081,"userid":111,"friend_userid":1150,"relationshiptime":"2017-01-09T11:09:23","groupid":2,"lastcontacttime":"2017-01-09T11:09:23","remark":"我的天","studentstatus":2,"username":"我的天","friend_username":"我的天","friendremark":null},{"friendid":1084,"userid":111,"friend_userid":1152,"relationshiptime":"2017-01-09T11:20:37","groupid":2,"lastcontacttime":"2017-01-09T11:20:37","remark":"何跃进意向","studentstatus":2,"username":"何跃进意向","friend_username":"何跃进意向","friendremark":null}]
     * listfriendsby : null
     * version : 1.1.106
     * datetime : 2017/1/9 11:20:41
     * accesstoken : jT3SQVXQqI7TjBcGo39ieL8fIB02wXHgOtSRVM2NkVr3MDg60ixb/xB38xTyQO5SOqUVlk3R6Ztg73yXJP6nqTnNbQpI2+NbfLWGlRYT30OpetwfW1TIGVhbXRNL7/lbrRO6qvKEyo7ui1OOcrjeciZq/28OHaMrJcj8AW0GL7FbbLrJoRiKtjx3LjmraCXlV3Q+otsouL/eqDfCxM534IfViP5zQ6bPmnsKo7Zh8KEt6jS/vWPLLDLW5ky/hZ03g27eVnzTh6I8Rn1zeYNS5tT766c+LPgWKlK+nYuG1RIEzACUgR2HzKalAdRaWGTj
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 3
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object listfriendsby;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListusersBean> listusers;
    private List<ContractUserEntity.ListfriendsBean> listfriends;

    public Object getListfriendsby() {
        return listfriendsby;
    }

    public void setListfriendsby(Object listfriendsby) {
        this.listfriendsby = listfriendsby;
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

    public List<ListusersBean> getListusers() {
        return listusers;
    }

    public void setListusers(List<ListusersBean> listusers) {
        this.listusers = listusers;
    }

    public List<ContractUserEntity.ListfriendsBean> getListfriends() {
        return listfriends;
    }

    public void setListfriends(List<ContractUserEntity.ListfriendsBean> listfriends) {
        this.listfriends = listfriends;
    }

    public static class ListusersBean {
        /**
         * userid : 1148
         * username : 何跃进2
         * username_en : null
         * gender : 1
         * loginname : null
         * password : A9B8B9C146F9F8A800A4320C4514EE62
         * phonenumber : 18219140226
         * birthday : 2017-01-09T10:42:10
         * openid : null
         * joindate : 2017-01-09T10:42:10
         * status : 1
         * email : null
         * qq : null
         * golfage : 0
         * par : 0
         * technicallevelid : 12
         * customerphoto : null
         * usertype : 0
         * roleid : 24
         * chinacity_id : 0
         * chinacity_name : null
         * uuid : null
         * fromuserid : 0
         * fromrode : null
         * jgpushid : null
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
        private Object chinacity_name;
        private Object uuid;
        private int fromuserid;
        private Object fromrode;
        private Object jgpushid;

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

        public Object getChinacity_name() {
            return chinacity_name;
        }

        public void setChinacity_name(Object chinacity_name) {
            this.chinacity_name = chinacity_name;
        }

        public Object getUuid() {
            return uuid;
        }

        public void setUuid(Object uuid) {
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

        public Object getJgpushid() {
            return jgpushid;
        }

        public void setJgpushid(Object jgpushid) {
            this.jgpushid = jgpushid;
        }
    }
}
