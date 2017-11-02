package com.glorystudent.util;


/**
 * 静态常量池
 * Created by hyj on 2016/12/16.
 */
public interface Constants {
    String BASE_URL2 = "https://app.pgagolf.cn";
    String BASE_URL3 = "https://192.168.1.168:4431";
    String BASE_URL4 = "http://192.168.1.199:8000";
    String BASE_URL = BASE_URL4;
    String DEFAULT_USERNAME = "用户";
    String USER_ID = "userid";
    String NUMBER_ID = "numberid";
    String GROUP_ID = "groupid";
    String ACCESS_TOKEN = "accesstoken";
    String PHONE_NUMBER = "phonenumber";//手机号(账号)
    String LOGIN_STATE = "logined";
    String EVENT_SHARE_URL = BASE_URL + "/Activity/eventActivities?eventActivity_id=%d";//分享赛事活动
    String EVENT_DETAIL_URL = BASE_URL + "/ActivityAPP/EventActivities?eventActivity_id=%d&userId=%s&isAndroid=Android";//查看赛事活动
    String EVENT_PREVIEW_URL = BASE_URL + "/ActivityAPP/EventActivitiesShow?eventActivity_id=%d";//预览赛事活动
    String NEWS_URL = BASE_URL + "/home/newsDetail?id=%d";//新闻详情
    String SERVICE_TERMS = BASE_URL + "/home/license";//服务条款
    String ABOUT_US_URL = "https://www.pgagolf.cn/home/about?VersionNumber=%s";//关于我们
    String HEAD_PORTRAIT = "customerphoto";//头像
    String NICKNAME = "username";//昵称
    String USER_TYPE = "usertype";//用户类型
    String SEX = "gender";//性别
    String VETERAN = "golfage";//球龄
    String ADDRESS = "address";//所在地区(所在城市)
    String PROVINCES_NAME = "provincesname";//省名字
    String CITY_NAME = "cityname";//城市名字
    String COUNTY_NAME = "countyname";//县区名字
    String CLOSE = "close";//关闭activity
    String PROFILE = "profile";//个人简介
    String COACH_AGE = "coachage";//球龄
    String COACH_VOSTRO = "coachvostro";//所获成就
    String COACH_GROUP_NAME = "coachgroupname";//所属学院
    String SWITCH_VERSION = "switchV";//切换版本
    String ATTESTATION_STATE = "attestationstate";//教练认证状态
    String SCORECARD_POPUP_WINDOW_STATE = "scorepopstate";//记分卡弹窗
    String DAY_CALENDAR_TODAY = "today";//今天
    String DAY_CALENDAR_CLICK = "clickday";//点击的某天
    String COURSE_CALENDAR_TODAY = "coursetoday";//今天
    String COURSE_CALENDAR_CLICK = "courseclickday";//点击的某天
    String HOME_CALENDAR_TODAY = "hometoday";//教练端home今天
    String HOME_CALENDAR_CLICK = "homeclickday";//教练端点击的某天
    String NEW_FRIENDS_COUNT = "newfriendscount";//新的朋友条数
    String NOTIFICATION = "NotificationState";//新消息通知状态
    String[] SETTING = new String[]{"setting1", "setting2", "setting3", "setting4"};
    String REGISTRATION_ID = "registrationid";
    String[] LABEL = new String[]{"1号木", "3号木", "4铁木", "5号木", "2号铁", "3号铁", "4号铁", "5号铁", "6号铁"
            , "7号铁", "8号铁", "9号铁", "推杆", "P杆(劈起杆)", "A杆(挖掘杆)", "S杆(沙坑杆)", "48°特殊杆"
            , "50°特殊杆", "52°特殊杆", "53°特殊杆", "54°特殊杆", "56°特殊杆", "58°特殊杆"
            , "60°特殊杆", "19°铁木", "20°铁木", "21°铁木", "22°铁木", "23°铁木", "5铁木", "全挥杆"
            , "半挥杆", "切杆", "推杆", "长切", "短切", "左旋", "由旋"};
    int[] FACE = new int[]{0x1F60a, 0x1F603, 0x1F609, 0x1F62e, 0x1F60b, 0x1F60e, 0x1F621, 0x1F616, 0x1F633,
            0x1F61e, 0x1F62d, 0x1F610, 0x1F607, 0x1F62c, 0x1F606, 0x1F631, 0x1F385, 0x1F634, 0x1F615, 0x1F637,
            0x1F62f, 0x1F60f, 0x1F611, 0x1F496, 0x1F494, 0x1F319, 0x1f31f, 0x1f31e, 0x1F308, 0x1F60d, 0x1F61a,
            0x1F48b, 0x1F339, 0x1F342, 0x1F44d, 0x1F602, 0x1F603, 0x1F604, 0x1F609, 0x1F613, 0x1F614, 0x1F616,
            0x1F618, 0x1F61a, 0x1F61c, 0x1F61d, 0x1F61e, 0x1F620, 0x1F621, 0x1F622, 0x1F623, 0x1F628, 0x1F62a,
            0x1F62d, 0x1F630, 0x1F631, 0x1F632, 0x1F633, 0x1F645, 0x1F646, 0x1F647, 0x1F64c, 0x1F6a5, 0x1F6a7,
            0x1F6b2, 0x1F6b6, 0x1F302, 0x1F319, 0x1F31f};

    int MAX = 10000;
    int[] DRAWTYPE = new int[]{-1, 1, 2, 3, 4, 5, 6};
    String REFRESH_TIME = "refreshtime";//下拉刷新时间
    String WX_APPID = "wxd2ec5fc5fab63695";
    String WX_AppSecret = "5275c0374acde6a637d15560dffc6314";
    String QQ_APPID = "1105909466";
    String QQ_APPKEY = "V3H7UNp3juSUCPQv";
    String ORDER_ID = "orderId";//订单id

}
