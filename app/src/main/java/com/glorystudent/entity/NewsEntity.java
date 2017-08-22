package com.glorystudent.entity;

import com.glorystudent.golflibrary.adapter.AbsMoreBaseAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gavin.J on 2017/5/12.
 */

public class NewsEntity {

    /**
     * listnews : [{"news_id":210,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"前莱德杯欧洲队长麦克金尼：伍兹复出是宣传噱头","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161205/tPfK-fxyipxf7582094.jpg","news_video":null,"news_top":"1","news_reviewed":"\u0000","news_tips":"高尔夫,麦克金尼,伍兹,复出","news_type":"2"},{"news_id":211,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"2016年度高尔夫新闻盘点之\u2014\u2014特朗普争议","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161113/72Dp-fxxswfv1844647.jpg","news_video":null,"news_top":"1","news_reviewed":"\u0000","news_tips":"高尔夫,特朗普,争议","news_type":"2"},{"news_id":212,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"2016年高球界发生的15件小事 回顾耐人寻味的一年 ","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/D--x-fxyxusa4755021.jpg","news_video":null,"news_top":"1","news_reviewed":"\u0000","news_tips":"高球界,2016年,小事,回顾","news_type":"2"},{"news_id":213,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"福瑞克09年世锦赛不堪回首：达斯汀开球甩我50码","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/wKgz-fxyxury7955078.jpg","news_video":null,"news_top":"1","news_reviewed":"\u0000","news_tips":"高尔夫,福瑞克,达斯汀,奎罗斯","news_type":"2"},{"news_id":214,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"与老虎伍兹的同组记忆：基恩-弗格斯（1996年）","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/X0Vt-fxyxute2742886.jpg","news_video":null,"news_top":"1","news_reviewed":"\u0000","news_tips":"伍兹,老虎,与伍兹同组,弗格斯,美巡赛","news_type":"2"},{"news_id":215,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"尴尬！14岁少年球场解决尿急遭禁赛 高球热情被浇灭","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/O0nN-fxytqax7008336.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"禁赛,青少年,流动厕所,尿急","news_type":"2"},{"news_id":216,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"傲娇！达斯汀儿子不屑圣诞老人 人小鬼大淡定十足","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/0aaa-fxytqaw0246126.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"达斯汀,圣诞老人,塔藤姆","news_type":"2"},{"news_id":217,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"五场美巡赛冠军曾降级打威巡 伯德乐观心态值得学习","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/uWCW-fxytqec1620586.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"美巡赛,冠军,乔纳森-伯德,威巡赛","news_type":"2"},{"news_id":218,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"回顾全年击球 斯滕森皇家特隆50英尺长推射门最佳","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/6y9u-fxytqaw0219315.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"斯滕森,沃克尔,戴伊,年度击球","news_type":"2"},{"news_id":219,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"芬臣：高尔夫应设\u201c国际足联\u201d 新机构统领全球高坛","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/LKL5-fxytqax6958070.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"芬臣,美巡赛,欧巡赛,国际足联,全球巡回赛","news_type":"2"},{"news_id":220,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"斯皮思不满2016赛季铁杆表现 加强训练欲重回巅峰","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/_B6E-fxytqec1585960.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"斯皮思,巅峰,短杆,2016赛季","news_type":"2"},{"news_id":221,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"2016年度十大\u201c激情\u201d瞬间 回顾血脉喷张动人时刻","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161220/3bA1-fxytqec1517168.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,达斯汀,瑞德,麦克罗伊","news_type":"2"},{"news_id":222,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"奥运高尔夫球场运作难 没人打球政府无法负担","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161220/mBH2-fxytqax6840894.jpg,https://app.pgagolf.cn/images/files/20161222/sports/transform/20161220/IPw9-fxytyzp5219670.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,奥运会,球场,巴西","news_type":"2"},{"news_id":223,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"2016年度高尔夫新闻盘点之\u2014\u2014莱德杯","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161220/MdGG-fxytqax6785976.jpg,https://app.pgagolf.cn/images/files/20161222/sports/transform/20161220/RuDE-fxytyzp5178774.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,莱德杯,美国队,拉夫三世","news_type":"2"},{"news_id":224,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"达利与前妻争吵乱撒钱 曾将5万美元现金扔窗外 ","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161218/2KKf-fxytqec1262084.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,达利,前妻","news_type":"2"},{"news_id":225,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"大暖男！麦克罗伊到访家乡儿童康复中心献爱心","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/Cp5X-fxyxute2864629.jpg,https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/mRTE-fxyxusa4797058.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,麦克罗伊,康复中心,捐款","news_type":"2"},{"news_id":226,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"斯滕森开始高尔夫球场设计生意 第一单签瑞典球场","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161221/PwZs-fxytqax7002044.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"斯滕森,高尔夫球场,球场设计,奥斯泰里克,SOL","news_type":"2"},{"news_id":227,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"2016亚巡年度奖项出炉 奖金王亨德加冕最佳球手","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/Vp3n-fxyxute2860903.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"奖金王,亨德,澳洲,亚巡赛,年度颁奖","news_type":"2"},{"news_id":228,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"菲律宾球员神奇一年 大满贯世锦赛奥运会全体验","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/7aiL-fxytyzp5445552.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,大满贯,世锦赛,奥运会","news_type":"2"},{"news_id":229,"newgroup_id":null,"news_datetime":"2017-05-12T14:06:20","news_title":"高颜值配神球技！网红美女佩琪60码外击中水杯","news_subtitle":null,"news_promulgator":"新浪体育","news_content":null,"news_picture":"https://app.pgagolf.cn/images/files/20161222/sports/transform/20161222/slsz-fxytyzp5460614.jpg","news_video":null,"news_top":"0","news_reviewed":"\u0000","news_tips":"高尔夫,佩琪,网红","news_type":"2"}]
     * newurl : null
     * newsurl : http://192.168.1.168:4431/home/newsDetail?id=
     * version : 1.1.106
     * datetime : 2017/5/12 18:51:13
     * accesstoken :  xSGIX09tgoxCyvvtHXxvCuPGQjNR95pWkUDDoZ0OMa2G7wOjvhcTZDwBGBxesjB/vQWCo3aD4sB4RPnJSEF2k4PD3ofElkzNjNVLhidOQbNIRHvOxeLCUhDpVnIwswZ0SN6DhRHoUC1hbx2bd1NBRN7vcgduOsRPkWEeq9eHMOAtpqj0PmJ5o2 xQEfodNr4qYxDZPU2 9ik mc3s4 MjCi7S4i8twxAI47HtqYtfahGL9ISH7MyqUx3IrNTJmsU92uR1GTbEUf6/FpLvr1AlJ5kAC2LllDn4xiDMIQ5FKb5wVCUE/Yv9WA6Gbc4scXTRyfsA2BRNrVvb g/AgWFw==
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : 719
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : 20
     */

    private Object newurl;
    private String newsurl;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListnewsBean> listnews;

    public Object getNewurl() {
        return newurl;
    }

    public void setNewurl(Object newurl) {
        this.newurl = newurl;
    }

    public String getNewsurl() {
        return newsurl;
    }

    public void setNewsurl(String newsurl) {
        this.newsurl = newsurl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public String getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(String totalrownum) {
        this.totalrownum = totalrownum;
    }

    public Object getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(Object totalpagenum) {
        this.totalpagenum = totalpagenum;
    }

    public Object getNowpagenum() {
        return nowpagenum;
    }

    public void setNowpagenum(Object nowpagenum) {
        this.nowpagenum = nowpagenum;
    }

    public String getPagerownum() {
        return pagerownum;
    }

    public void setPagerownum(String pagerownum) {
        this.pagerownum = pagerownum;
    }

    public List<ListnewsBean> getListnews() {
        return listnews;
    }

    public void setListnews(List<ListnewsBean> listnews) {
        this.listnews = listnews;
    }

    public static class ListnewsBean implements AbsMoreBaseAdapter.OnType, Serializable {
        /**
         * news_id : 210
         * newgroup_id : null
         * news_datetime : 2017-05-12T14:06:20
         * news_title : 前莱德杯欧洲队长麦克金尼：伍兹复出是宣传噱头
         * news_subtitle : null
         * news_promulgator : 新浪体育
         * news_content : null
         * news_picture : https://app.pgagolf.cn/images/files/20161222/sports/transform/20161205/tPfK-fxyipxf7582094.jpg
         * news_video : null
         * news_top : 1
         * news_reviewed :
         * news_tips : 高尔夫,麦克金尼,伍兹,复出
         * news_type : 2
         */

        private int news_id;
        private Object newgroup_id;
        private String news_datetime;
        private String news_title;
        private String news_subtitle;
        private String news_promulgator;
        private Object news_content;
        private String news_picture;
        private Object news_video;
        private String news_top;
        private String news_reviewed;
        private String news_tips;
        private String news_type;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public Object getNewgroup_id() {
            return newgroup_id;
        }

        public void setNewgroup_id(Object newgroup_id) {
            this.newgroup_id = newgroup_id;
        }

        public String getNews_datetime() {
            return news_datetime;
        }

        public void setNews_datetime(String news_datetime) {
            this.news_datetime = news_datetime;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_subtitle() {
            return news_subtitle;
        }

        public void setNews_subtitle(String news_subtitle) {
            this.news_subtitle = news_subtitle;
        }

        public String getNews_promulgator() {
            return news_promulgator;
        }

        public void setNews_promulgator(String news_promulgator) {
            this.news_promulgator = news_promulgator;
        }

        public Object getNews_content() {
            return news_content;
        }

        public void setNews_content(Object news_content) {
            this.news_content = news_content;
        }

        public String getNews_picture() {
            return news_picture;
        }

        public void setNews_picture(String news_picture) {
            this.news_picture = news_picture;
        }

        public Object getNews_video() {
            return news_video;
        }

        public void setNews_video(Object news_video) {
            this.news_video = news_video;
        }

        public String getNews_top() {
            return news_top;
        }

        public void setNews_top(String news_top) {
            this.news_top = news_top;
        }

        public String getNews_reviewed() {
            return news_reviewed;
        }

        public void setNews_reviewed(String news_reviewed) {
            this.news_reviewed = news_reviewed;
        }

        public String getNews_tips() {
            return news_tips;
        }

        public void setNews_tips(String news_tips) {
            this.news_tips = news_tips;
        }

        public String getNews_type() {
            return news_type;
        }

        public void setNews_type(String news_type) {
            this.news_type = news_type;
        }

        @Override
        public int getType() {
            if (news_type.equals("1")) {
                return 0;
            } else if (news_type.equals("2")) {
                return 1;
            } else if (news_type.equals("4")) {
                return 3;
            } else {
                return 2;
            }
        }
    }
}
