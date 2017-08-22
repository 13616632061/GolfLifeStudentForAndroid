package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/22.
 */
public class CoachHomeEntity {
    private int state;
    private EventInformationEntity.ListEventBean listEventBean;
    private AppointmentListEntity.ListAppointmentBean appointmentBean;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public EventInformationEntity.ListEventBean getListEventBean() {
        return listEventBean;
    }

    public void setListEventBean(EventInformationEntity.ListEventBean listEventBean) {
        this.listEventBean = listEventBean;
    }

    public AppointmentListEntity.ListAppointmentBean getAppointmentBean() {
        return appointmentBean;
    }

    public void setAppointmentBean(AppointmentListEntity.ListAppointmentBean appointmentBean) {
        this.appointmentBean = appointmentBean;
    }
}
