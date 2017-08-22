package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/15.
 */
public class UserRequestEntity {
    private UserBean user;
    public static class UserBean{
        private Integer userid;
        private String username;
        private String username_en;
        private String gender;
        private String loginname;
        private String phonenumber;
        private String birthday;
        private String joindate;
        private String status;
        private Integer golfage;
        private Integer par;
        private Integer technicallevelid;
        private String openid;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
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

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
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

        public Integer getGolfage() {
            return golfage;
        }

        public void setGolfage(Integer golfage) {
            this.golfage = golfage;
        }

        public Integer getPar() {
            return par;
        }

        public void setPar(Integer par) {
            this.par = par;
        }

        public Integer getTechnicallevelid() {
            return technicallevelid;
        }

        public void setTechnicallevelid(Integer technicallevelid) {
            this.technicallevelid = technicallevelid;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
