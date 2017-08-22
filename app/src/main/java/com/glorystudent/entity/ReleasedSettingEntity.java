package com.glorystudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/17.
 */

public class ReleasedSettingEntity implements Parcelable {
    private String organizer;
    private String signUpBeginTime;
    private String signUpEndTime;
    private boolean ifShowSignName;
    private boolean ifShowPhotoWall;

    public ReleasedSettingEntity() {
    }

    protected ReleasedSettingEntity(Parcel in) {
        organizer = in.readString();
        signUpBeginTime = in.readString();
        signUpEndTime = in.readString();
        ifShowSignName = in.readByte() != 0;
        ifShowPhotoWall = in.readByte() != 0;
    }

    public static final Creator<ReleasedSettingEntity> CREATOR = new Creator<ReleasedSettingEntity>() {
        @Override
        public ReleasedSettingEntity createFromParcel(Parcel in) {
            return new ReleasedSettingEntity(in);
        }

        @Override
        public ReleasedSettingEntity[] newArray(int size) {
            return new ReleasedSettingEntity[size];
        }
    };

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getSignUpBeginTime() {
        return signUpBeginTime;
    }

    public void setSignUpBeginTime(String signUpBeginTime) {
        this.signUpBeginTime = signUpBeginTime;
    }

    public String getSignUpEndTime() {
        return signUpEndTime;
    }

    public void setSignUpEndTime(String signUpEndTime) {
        this.signUpEndTime = signUpEndTime;
    }

    public boolean isIfShowSignName() {
        return ifShowSignName;
    }

    public void setIfShowSignName(boolean ifShowSignName) {
        this.ifShowSignName = ifShowSignName;
    }

    public boolean isIfShowPhotoWall() {
        return ifShowPhotoWall;
    }

    public void setIfShowPhotoWall(boolean ifShowPhotoWall) {
        this.ifShowPhotoWall = ifShowPhotoWall;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(organizer);
        dest.writeString(signUpBeginTime);
        dest.writeString(signUpEndTime);
        dest.writeByte((byte) (ifShowSignName ? 1 : 0));
        dest.writeByte((byte) (ifShowPhotoWall ? 1 : 0));
    }
}
