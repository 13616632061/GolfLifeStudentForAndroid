package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/9.
 */
public class FriendsRequestEntity {
    private FriendsBean friends;
    public static class FriendsBean{
        private Integer userid;
        private Integer friend_userid;
        private String relationshiptime;
        private Integer groupid;
        private String remark;
        private Integer studentstatus;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getFriend_userid() {
            return friend_userid;
        }

        public void setFriend_userid(Integer friend_userid) {
            this.friend_userid = friend_userid;
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
    }

    public FriendsBean getFriends() {
        return friends;
    }

    public void setFriends(FriendsBean friends) {
        this.friends = friends;
    }
}
