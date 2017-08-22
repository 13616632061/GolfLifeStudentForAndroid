package com.glorystudent.entity;

import java.io.Serializable;

/**
 * Created by hyj on 2017/2/7.
 */
public class ApplyInfoListEntity implements Serializable{
    private String applyfriends_type;
    private String applystatus;
    private String applyremark;
    private Integer userid;
    private String username;
    private String phonenumber;
    private String customerphoto;
    private Integer applyfriends_id;
    private Integer answeruserid;

    public Integer getAnsweruserid() {
        return answeruserid;
    }

    public void setAnsweruserid(Integer answeruserid) {
        this.answeruserid = answeruserid;
    }

    public Integer getApplyfriends_id() {
        return applyfriends_id;
    }

    public void setApplyfriends_id(Integer applyfriends_id) {
        this.applyfriends_id = applyfriends_id;
    }

    public String getApplyfriends_type() {
        return applyfriends_type;
    }

    public void setApplyfriends_type(String applyfriends_type) {
        this.applyfriends_type = applyfriends_type;
    }

    public String getApplystatus() {
        return applystatus;
    }

    public void setApplystatus(String applystatus) {
        this.applystatus = applystatus;
    }

    public String getApplyremark() {
        return applyremark;
    }

    public void setApplyremark(String applyremark) {
        this.applyremark = applyremark;
    }

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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCustomerphoto() {
        return customerphoto;
    }

    public void setCustomerphoto(String customerphoto) {
        this.customerphoto = customerphoto;
    }
}
