package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/1/9.
 */
public class ContractUserEntity implements Serializable{

    /**
     * listUsers : null
     * status : null
     * listfriends : [{"friendid":1074,"userid":111,"friend_userid":1127,"relationshiptime":"1753-01-01T00:00:00","groupid":2,"lastcontacttime":"1753-01-01T00:00:00","remark":"何","studentstatus":3,"username":"何","friend_username":"何","friendremark":null},{"friendid":1075,"userid":111,"friend_userid":1129,"relationshiptime":"2017-01-06T20:34:26","groupid":2,"lastcontacttime":"2017-01-06T20:34:26","remark":"heyuejin","studentstatus":3,"username":null,"friend_username":null,"friendremark":null},{"friendid":1077,"userid":111,"friend_userid":1147,"relationshiptime":"1753-01-01T00:00:00","groupid":2,"lastcontacttime":"1753-01-01T00:00:00","remark":"无敌","studentstatus":1,"username":"无敌","friend_username":"无敌","friendremark":null},{"friendid":1078,"userid":111,"friend_userid":1148,"relationshiptime":"2017-01-09T10:42:10","groupid":2,"lastcontacttime":"2017-01-09T10:42:10","remark":"何跃进2","studentstatus":2,"username":"何跃进2","friend_username":"何跃进2","friendremark":null}]
     * userid : null
     * groupid : null
     * userinfo : null
     * listContractuser : [{"_UserID":1147,"_Status":0,"userid":1147,"status":0,"username":"无敌","customerphoto":null},{"_UserID":1127,"_Status":8,"userid":1127,"status":8,"username":"何跃进","customerphoto":null},{"_UserID":1129,"_Status":8,"userid":1129,"status":8,"username":"heyuejin","customerphoto":null}]
     * version : 1.1.106
     * datetime : 2017/1/9 10:44:55
     * accesstoken : jT3SQVXQqI7TjBcGo39ieL8fIB02wXHgOtSRVM2NkVr3MDg60ixb/xB38xTyQO5SOqUVlk3R6Ztg73yXJP6nqTnNbQpI2+NbfLWGlRYT30OpetwfW1TIGVhbXRNL7/lbrRO6qvKEyo7ui1OOcrjeciZq/28OHaMrJcj8AW0GL7FbbLrJoRiKtjx3LjmraCXlV3Q+otsouL/eqDfCxM534IfViP5zQ6bPmnsKo7Zh8KEt6jS/vWPLLDLW5ky/hZ03g27eVnzTh6I8Rn1zeYNS5tT766c+LPgWKlK+nYuG1RIEzACUgR2HzKalAdRaWGTj
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 3
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object listUsers;
    private Object status;
    private Object userid;
    private Object groupid;
    private Object userinfo;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListfriendsBean> listfriends;
    private List<ListContractuserBean> listContractUserExt;
    public Object getListUsers() {
        return listUsers;
    }

    public void setListUsers(Object listUsers) {
        this.listUsers = listUsers;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
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

    public List<ListfriendsBean> getListfriends() {
        return listfriends;
    }

    public void setListfriends(List<ListfriendsBean> listfriends) {
        this.listfriends = listfriends;
    }

    public List<ListContractuserBean> getListContractuser() {
        return listContractUserExt;
    }

    public void setListContractuser(List<ListContractuserBean> listContractUserExt) {
        this.listContractUserExt = listContractUserExt;
    }

    public static class ListfriendsBean implements Serializable{
        /**
         * friendid : 1074
         * userid : 111
         * friend_userid : 1127
         * relationshiptime : 1753-01-01T00:00:00
         * groupid : 2
         * lastcontacttime : 1753-01-01T00:00:00
         * remark : 何
         * studentstatus : 3
         * username : 何
         * friend_username : 何
         * friendremark : null
         */

        private int friendid;
        private int userid;
        private int friend_userid;
        private String relationshiptime;
        private int groupid;
        private String lastcontacttime;
        private String remark;
        private int studentstatus;
        private String username;
        private String friend_username;
        private String friendremark;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFriend_username() {
            return friend_username;
        }

        public void setFriend_username(String friend_username) {
            this.friend_username = friend_username;
        }

        public String getFriendremark() {
            return friendremark;
        }

        public void setFriendremark(String friendremark) {
            this.friendremark = friendremark;
        }
    }

    public static class ListContractuserBean implements Serializable{
        /**
         * _UserID : 1147
         * _Status : 0
         * userid : 1147
         * status : 0
         * username : 无敌
         * customerphoto : null
         */
        private int _UserID;
        private int _Status;
        private int userid;
        private int status;
        private String username;
        private String customerphoto;
        private int finishNum;
        private int totalNum;
        private String chinacity_name;
        private String phonenumber;
        private String contractid;


        public String getChinacity_name() {
            return chinacity_name;
        }

        public void setChinacity_name(String chinacity_name) {
            this.chinacity_name = chinacity_name;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public int get_UserID() {
            return _UserID;
        }

        public void set_UserID(int _UserID) {
            this._UserID = _UserID;
        }

        public int get_Status() {
            return _Status;
        }

        public void set_Status(int _Status) {
            this._Status = _Status;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }

        public int getFinishNum() {
            return finishNum;
        }

        public void setFinishNum(int finishNum) {
            this.finishNum = finishNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }
    }
}
