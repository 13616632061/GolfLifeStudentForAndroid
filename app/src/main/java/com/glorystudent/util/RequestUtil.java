package com.glorystudent.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.glorystudent.entity.AliyunRequestEntity;
import com.glorystudent.entity.FileUpRequestEntity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hyj on 2016/12/15.
 */
public class RequestUtil {

    /**
     * TODO 获取当前时间
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * TODO 获取应用版本号
     * 获取当前应用的版本号
     *
     * @return
     */
    public static String getVersion(Context context) {
        try {
            if (context != null) {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                String version = info.versionName;
                return version;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1 + "";
    }

    /**
     * TODO 获取虚拟验证码JSON
     * 获取虚拟验证码
     *
     * @param context
     * @return
     */
    public static String getSMSCheck(Context context, String phonenum) {
        String json = "{" + "\"userid\":" + "\"\","
                + "\"coachgroupid\":" + "\"\","
                + "\"accesstoken\":" + "\"\","
                + "\"version\":" + "\"" + getVersion(context) + "\","
                + "\"datetime\":" + "\"" + getCurrentTime() + "\","
                + "\"messagetoken\":" + "\"\","
                + "\"phonenum\":" + "\"" + phonenum + "\","
                + "\"DeviceType\":" + "\"4\","
                + "\"ipaddress\":" + "\"\""
                + "}";
        return json;
    }

    public static String getLogin(Context context, String phonenum, String code) {
        String login = "\"phonenum\":" + "\"" + phonenum + "\","
                + "\"smsCheckCode\":" + "\"" + code + "\","
                + "\"jgpushid\":" + "\"" + SharedUtil.getString(Constants.REGISTRATION_ID) + "\"";
        return getJson(context, login);
    }

    public static AliyunRequestEntity getAliyunOSS() {
        AliyunRequestEntity aliyunRequestEntity = new AliyunRequestEntity();
        List<AliyunRequestEntity.ListsettingvalueBean> listset = new ArrayList<>();
        for (int i = 0; i < Constants.SETTING.length; i++) {
            AliyunRequestEntity.ListsettingvalueBean set = new AliyunRequestEntity.ListsettingvalueBean();
            String setting = SharedUtil.getString(Constants.SETTING[i]);
            if (setting != null) {
                set.setSettingvalue(setting);
                listset.add(set);
            } else {
                return null;
            }
        }
        aliyunRequestEntity.setListsettingvalue(listset);
        return aliyunRequestEntity;
    }

    /**
     * TODO 获取广告数据JSON
     *
     * @param context
     * @return
     */
    public static String getAD(Context context) {
        String queryAd = "\"ad\":" + "{}";
        return getJson(context, queryAd);
    }

    /**
     * TODO 获取首页listView的数据JSON
     *
     * @param context
     * @return
     */
    public static String getListDatas(Context context) {
        String json = "\"news\":" + "{" + "\"news_top\":" + "\"0\"," + "\"news_reviewed\":" + "\"1\"" + "}";
        return getJson(context, json);
    }

    /**
     * TODO 获取首页头条数据JSON
     * 获取首页数据
     *
     * @param context
     */
    public static String getNews(Context context, int nowpagenum) {
        String json = "\"news\":" + "{}," + "\"nowpagenum\":" + "\"" + nowpagenum + "\"," + "\"pagerownum\":" + "\"20\"";
        return getJson(context, json);
    }

    /**
     * TODO 请求数据包头封装
     * 请求数据包头封装
     *
     * @param context
     * @param query
     * @return
     */
    public static String getJson(Context context, String query) {
        String json = "{" + "\"userid\":" + "\"" + SharedUtil.getString(Constants.USER_ID) + "\","
                + "\"groupid\":" + "\"" + SharedUtil.getString(Constants.GROUP_ID) + "\","
                + "\"accesstoken\":" + "\"" + SharedUtil.getString(Constants.ACCESS_TOKEN) + "\","
                + "\"version\":" + "\"" + getVersion(context) + "\","
                + "\"datetime\":" + "\"" + getCurrentTime() + "\","
                + "\"messagetoken\":" + "\"\","
                + "\"devicetype\":" + "\"4\","
                + query
                + "}";
        return json;
    }

    /**
     * TODO 获取城市区域信息
     *
     * @param context
     * @return
     */
    public static String getCitys(Context context) {
        String json = "\"chinacity\":" + "{" + "\"pid\":" + "\"0\"" + "}";
        return getJson(context, json);
    }


    /**
     * TODO 获取教学视频信息
     *
     * @param context
     * @param pagenum
     * @return
     */
    public static String getTeachVideo(Context context, String teachingvideo_level, int pagenum) {
        String json = "\"pagerownum\":" + "\"10\","
                + "\"nowpagenum\":" + "\"" + pagenum + "\","
                + "\"teachingVideo\":" + "{" + "\"teachingvideo_level\":" + "\"" + teachingvideo_level + "\"," + "\"teachingvideo_price\":" + "\"-1\"" + "}";
        return getJson(context, json);
    }

    /**
     * 获取球场地址信息
     *
     * @param context
     * @return
     */
    public static String getGolfCourse(Context context) {
        String query = "\"court\":" + "{}";
        return getJson(context, query);
    }

    public static String getSignDefinition(Context context) {
        String query = "\"signdefinition\":" + "{}";
        return getJson(context, query);
    }

    /**
     * TODO 获取赛事活动信息
     *
     * @param context
     * @return
     */
    public static String getCompetition(Context context) {
        String query = "\"eventactivity\":" + "{}";
        return getJson(context, query);
    }

    /**
     * TODO 修改用户信息
     *
     * @param context
     * @param info
     * @return
     */
    public static String getEditUserInfo(Context context, String info) {
        String json = "\"user\":" + "{" + info + "}";
        return getJson(context, json);
    }

    /**
     * TODO 仅含包头
     *
     * @param context
     * @return
     */
    public static String getEmptyParameter(Context context) {
        String json = "{" + "\"userid\":" + "\"" + SharedUtil.getString(Constants.USER_ID) + "\","
                + "\"groupid\":" + "\"" + SharedUtil.getString(Constants.GROUP_ID) + "\","
                + "\"accesstoken\":" + "\"" + SharedUtil.getString(Constants.ACCESS_TOKEN) + "\","
                + "\"version\":" + "\"" + getVersion(context) + "\","
                + "\"datetime\":" + "\"" + getCurrentTime() + "\","
                + "\"messagetoken\":" + "\"\","
                + "\"DeviceType\":" + "\"4\""
                + "}";
        return json;
    }

    /**
     * TODO 教练认证申请
     *
     * @param context
     * @param coachgroupname
     * @param coachage
     * @param profile
     * @param coachvostro
     * @return
     */
    public static String getCoachCertification(Context context, String coachgroupname, String coachage, String profile, String coachvostro) {
        String json = "\"coach\":" + "{"
                + "\"coachgroupname\":" + "\"" + coachgroupname + "\","
                + "\"coachage\":" + "\"" + coachage + "\","
                + "\"profile\":" + "\"" + profile + "\","
                + "\"coachvostro\":" + "\"" + coachvostro + "\""
                + "}";
        return getJson(context, json);
    }

    /**
     * TODO 获得教练认证状态
     *
     * @param context
     * @return
     */
    public static String getCertificationState(Context context) {
        String json = "\"coach\":" + "{" + "}";
        return getJson(context, json);
    }

    /**
     * 上传头像
     *
     * @param context
     * @param filemd5
     * @param filename
     * @param partmd5
     * @param filesize
     * @param partsize
     * @param partdata
     * @return
     */
    public static String getFileUp(Context context, String filemd5, String filename, String partmd5, int filesize, int partsize, String partdata) {
        FileUpRequestEntity fileUpRequestEntity = new FileUpRequestEntity();
        fileUpRequestEntity.setFilemd5(filemd5);
        fileUpRequestEntity.setFilename(filename);
        fileUpRequestEntity.setPartid(1);
        fileUpRequestEntity.setFilepartcount(1);
        fileUpRequestEntity.setPartmd5(partmd5);
        fileUpRequestEntity.setFilesize(filesize);
        fileUpRequestEntity.setPartsize(partsize);
        fileUpRequestEntity.setPartdata(partdata);
        fileUpRequestEntity.setPictype(1);
        String json = new Gson().toJson(fileUpRequestEntity);
        return getRequestJson(context, json);
    }

    /**
     * TODO 意见反馈
     *
     * @param context
     * @param feedbackcontext
     * @return
     */
    public static String getOpinion(Context context, String feedbackcontext) {
        String json = "\"feedback\":" + "{" + "\"feedbackcontext\":" + "\"" + feedbackcontext + "\","
                + "\"feedbackdatetime\":" + "\"" + getCurrentTime() + "\"" + "}";
        return getJson(context, json);
    }

    /**
     * TODO 上传记分卡
     *
     * @param context
     * @param golfcoursename
     * @param secoredatetime
     * @param scorecard_image
     * @return
     */
    public static String uploadFile(Context context, String golfcoursename, String secoredatetime, String scorecard_image) {
        String json = "\"scorecard\":" + "{" + "\"golfcoursename\":" + "\"" + golfcoursename + "\","
                + "\"secoredatetime\":" + "\"" + secoredatetime + "\","
                + "\"scorecard_image\":" + "\"" + scorecard_image + "\""
                + "}";
        return getJson(context, json);
    }

    /**
     * TODO 删除记分卡
     *
     * @param context
     * @param picfailePath
     * @return
     */
    public static String delImgFile(Context context, String picfailePath) {
        String json = "\"picfailePath\":" + "\"" + picfailePath + "\"";
        return getJson(context, json);
    }

    /**
     * TODO 评论视频
     *
     * @param context
     * @param comment_context
     * @param comment_tvideoid
     * @return
     */
    public static String addTVideoComment(Context context, String comment_context, String comment_tvideoid) {
        String json = "\"tVideoComment\":" + "{"
                + "\"comment_time\":" + "\"" + getCurrentTime() + "\","
                + "\"comment_context\":" + "\"" + comment_context + "\","
                + "\"comment_status\":" + "\"0\","
                + "\"comment_tvideoid\":" + "\"" + comment_tvideoid + "\""
                + "}";
        return getJson(context, json);
    }

    /**
     * TODO 获取视频评论列表
     *
     * @param context
     * @param comment_tvideoid`1
     * @return
     */
    public static String queryTVideoComment(Context context, String comment_tvideoid) {
        String json = "\"tVideoComment\":" + "{"
                + "\"comment_tvideoid\":" + "\"" + comment_tvideoid + "\""
                + "}";
        return getJson(context, json);
    }

    /**
     * TODO 修改视频点赞
     *
     * @param context
     * @param comment_id
     * @param commentpraise
     * @return
     */
    public static String editTVideoCommentBy(Context context, String comment_id, String commentpraise) {
        String json = "\"tVideoComment\":" + "{"
                + "\"comment_id\":" + "\"" + comment_id + "\""
                + "},"
                + "\"commentpraise\":" + "\"" + commentpraise + "\"";
        return getJson(context, json);
    }

    /**
     * TODO 确认上传头像
     */
    public static String editUser(Context context, String customerphoto) {
        String json = "\"user\":" + "{"
                + "\"customerphoto\":" + "\"" + customerphoto + "\""
                + "}";
        return getJson(context, json);
    }

    /**
     * TODO 万能请求url
     */
    public static String getRequestJson(Context context, String json) {
        String requestJson = json.substring(1, json.length() - 1);
        return getJson(context, requestJson);
    }
}
