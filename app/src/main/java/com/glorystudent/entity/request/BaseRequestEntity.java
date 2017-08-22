package com.glorystudent.entity.request;

import android.content.Context;

import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by billlamian on 17-8-2.
 */

public class BaseRequestEntity {
    private String userid;//用户id
    private String groupid;//组ID
    private String accesstoken;
    private String version;
    private String datetime;
    private String messagetoken;
    private Integer devicetype=4;

    private Context context;
    public BaseRequestEntity(Context context) {
        this.context=context;
    }

    public String getUserid() {
        userid= SharedUtil.getString(Constants.USER_ID);
        return userid;
    }

    public String getGroupid() {
        groupid=SharedUtil.getString(Constants.GROUP_ID);
        return groupid;
    }

    public String getAccesstoken() {
        accesstoken=SharedUtil.getString(Constants.ACCESS_TOKEN);
        return accesstoken;
    }

    public String getVersion() {
        version= RequestUtil.getVersion(context);
        return version;
    }

    public String getDatetime() {
        datetime=RequestUtil.getCurrentTime();
        return datetime;
    }

    public String getMessagetoken() {
        messagetoken="";
        return messagetoken;
    }

    public Integer getDevicetype() {
        devicetype=4;
        return devicetype;
    }

    public Map<String,Object> getRequestMap(){
        Map<String, Object> map=new HashMap<>();
        map.put("userid",getUserid());
        map.put("groupid",getGroupid());
        map.put("accesstoken",getAccesstoken());
        map.put("version",getVersion());
        map.put("datetime",getDatetime());
        map.put("messagetoken",getMessagetoken());
        map.put("devicetype",getDevicetype());

        return map;
    }
}
