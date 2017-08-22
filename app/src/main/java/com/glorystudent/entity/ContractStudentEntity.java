package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/13.
 */
public class ContractStudentEntity {
        /**
         * _UserID : 1168
         * _Status : 8
         * userid : 1168
         * status : 8
         * username : 何张飞（新）
         * customerphoto : null
         */
        private int _UserID;
        private int _Status;
        private int userid;
        private int status;
        private String username;
        private Object customerphoto;

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

        public Object getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(Object customerphoto) {
            this.customerphoto = customerphoto;
        }
}
