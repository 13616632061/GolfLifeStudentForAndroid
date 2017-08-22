package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/4.
 */
public class NewsRequestEntity {
    private NewsBean news;
    private Integer page;

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public static class NewsBean {
        private Integer news_id;
        private Integer newgroup_id;
        private String news_title;
        private String news_subtitle;
        private String news_promulgator;
        private String news_content;
        private String news_reviewed;
        private String news_tips;
        private String news_datetime;
        private String news_type;
        private String news_top;

        public String getNews_top() {
            return news_top;
        }

        public void setNews_top(String news_top) {
            this.news_top = news_top;
        }

        public Integer getNews_id() {
            return news_id;
        }

        public void setNews_id(Integer news_id) {
            this.news_id = news_id;
        }

        public Integer getNewgroup_id() {
            return newgroup_id;
        }

        public void setNewgroup_id(Integer newgroup_id) {
            this.newgroup_id = newgroup_id;
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

        public String getNews_content() {
            return news_content;
        }

        public void setNews_content(String news_content) {
            this.news_content = news_content;
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

        public String getNews_datetime() {
            return news_datetime;
        }

        public void setNews_datetime(String news_datetime) {
            this.news_datetime = news_datetime;
        }

        public String getNews_type() {
            return news_type;
        }

        public void setNews_type(String news_type) {
            this.news_type = news_type;
        }
    }
}
