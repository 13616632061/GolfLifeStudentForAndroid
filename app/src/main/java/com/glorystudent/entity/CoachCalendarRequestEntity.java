package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/20.
 */
public class CoachCalendarRequestEntity {
    private String beginDate;
    private String endDate;
    private Integer coachId;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }
}
