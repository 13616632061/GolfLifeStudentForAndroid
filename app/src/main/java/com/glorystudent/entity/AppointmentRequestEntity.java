package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/14.
 */
public class AppointmentRequestEntity {
    private String datetimes;
    private String coachid;
    private String usersid;
    private Integer positionsid;

    public String getDatetimes() {
        return datetimes;
    }

    public void setDatetimes(String datetimes) {
        this.datetimes = datetimes;
    }

    public String getCoachid() {
        return coachid;
    }

    public void setCoachid(String coachid) {
        this.coachid = coachid;
    }

    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }

    public Integer getPositionsid() {
        return positionsid;
    }

    public void setPositionsid(Integer positionsid) {
        this.positionsid = positionsid;
    }
}
