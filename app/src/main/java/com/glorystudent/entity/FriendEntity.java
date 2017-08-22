package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/4.
 */
public class FriendEntity {
    private String name;
    private String remarkname;
    private String phoneNumber;
    private String customerphoto;
    private String addState;
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAddState() {
        return addState;
    }

    public void setAddState(String addState) {
        this.addState = addState;
    }

    public String getCustomerphoto() {
        return customerphoto;
    }

    public void setCustomerphoto(String customerphoto) {
        this.customerphoto = customerphoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarkname() {
        return remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "FriendEntity{" +
                "name='" + name + '\'' +
                ", pickname='" + remarkname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
