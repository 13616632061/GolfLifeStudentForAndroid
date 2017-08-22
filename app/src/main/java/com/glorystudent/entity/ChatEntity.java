package com.glorystudent.entity;

import com.glorystudent.golflibrary.adapter.AbsMoreBaseAdapter;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;

/**
 * Created by hyj on 2017/2/9.
 */
public class ChatEntity implements AbsMoreBaseAdapter.OnType{
    private String username;
    private String chatType;
    private String videoType;
    private String txt;
    private String time;
    private String videoPath;
    private String voicePath;
    private int length;
    private String chatTime;
    private int upState;
    private String textType;
    private SystemExtMessageEntity systemExtMessageEntity;
    private ExtEntity ext;

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public SystemExtMessageEntity getSystemExtMessageEntity() {
        return systemExtMessageEntity;
    }

    public void setSystemExtMessageEntity(SystemExtMessageEntity systemExtMessageEntity) {
        this.systemExtMessageEntity = systemExtMessageEntity;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public ExtEntity getExt() {
        return ext;
    }

    public void setExt(ExtEntity ext) {
        this.ext = ext;
    }

    public int getUpState() {
        return upState;
    }

    public void setUpState(int upState) {
        this.upState = upState;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getType() {
        if (chatTime != null) {
            return 2;
        }

        if(username.equals(SharedUtil.getString(Constants.PHONE_NUMBER))){
            return 1;
        }

        return 0;
    }
}
