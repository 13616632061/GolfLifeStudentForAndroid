package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/20.
 */
public class StudentRequestEntity {
    private FriendsBean friends;
    public static class FriendsBean{
        private Integer friendid;
        private Integer userid;
        private Integer friend_userID;
        private String relationshiptime;
        private Integer groupid;
        private String lastcontacttime;
        private String remark;
        private Integer studentstatus;
        private String username;
        private String friend_username;
        private String friendremark;

        public Integer getFriendid() {
            return friendid;
        }

        public void setFriendid(Integer friendid) {
            this.friendid = friendid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getFriend_userID() {
            return friend_userID;
        }

        public void setFriend_userID(Integer friend_userID) {
            this.friend_userID = friend_userID;
        }

        public String getRelationshiptime() {
            return relationshiptime;
        }

        public void setRelationshiptime(String relationshiptime) {
            this.relationshiptime = relationshiptime;
        }

        public Integer getGroupid() {
            return groupid;
        }

        public void setGroupid(Integer groupid) {
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

        public Integer getStudentstatus() {
            return studentstatus;
        }

        public void setStudentstatus(Integer studentstatus) {
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

    public FriendsBean getFriends() {
        return friends;
    }

    public void setFriends(FriendsBean friends) {
        this.friends = friends;
    }
}
