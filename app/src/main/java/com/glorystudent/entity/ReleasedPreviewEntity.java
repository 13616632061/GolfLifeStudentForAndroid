package com.glorystudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ReleasedPreviewEntity implements Parcelable {
    private String eventName;
    private Date beginTime;
    private Date endTime;
    private Date kickoffTime;
    private String eventAddress;
    private String eventMoney;
    private String eventInfo;
    private List<String> imageList;

    public ReleasedPreviewEntity() {
    }

    protected ReleasedPreviewEntity(Parcel in) {
        eventName = in.readString();
        eventAddress = in.readString();
        eventMoney = in.readString();
        eventInfo = in.readString();
        imageList = in.createStringArrayList();
    }

    public static final Creator<ReleasedPreviewEntity> CREATOR = new Creator<ReleasedPreviewEntity>() {
        @Override
        public ReleasedPreviewEntity createFromParcel(Parcel in) {
            return new ReleasedPreviewEntity(in);
        }

        @Override
        public ReleasedPreviewEntity[] newArray(int size) {
            return new ReleasedPreviewEntity[size];
        }
    };

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getKickoffTime() {
        return kickoffTime;
    }

    public void setKickoffTime(Date kickoffTime) {
        this.kickoffTime = kickoffTime;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventMoney() {
        return eventMoney;
    }

    public void setEventMoney(String eventMoney) {
        this.eventMoney = eventMoney;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventAddress);
        dest.writeString(eventMoney);
        dest.writeString(eventInfo);
        dest.writeStringList(imageList);
    }
}
