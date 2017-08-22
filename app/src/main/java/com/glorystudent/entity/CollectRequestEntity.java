package com.glorystudent.entity;

/**
 * Created by hyj on 2017/2/9.
 */
public class CollectRequestEntity {
    private CollectBean collect;
    public static class CollectBean{
        private Integer collectid;
        private Integer userid;
        private Integer collectobjectid;
        private Integer collecttype;
        private String collectdate;
        private String collecttag;
        private String collecttitle;
        private String collectpicurl;

        public Integer getCollectid() {
            return collectid;
        }

        public void setCollectid(Integer collectid) {
            this.collectid = collectid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getCollectobjectid() {
            return collectobjectid;
        }

        public void setCollectobjectid(Integer collectobjectid) {
            this.collectobjectid = collectobjectid;
        }

        public Integer getCollecttype() {
            return collecttype;
        }

        public void setCollecttype(Integer collecttype) {
            this.collecttype = collecttype;
        }

        public String getCollectdate() {
            return collectdate;
        }

        public void setCollectdate(String collectdate) {
            this.collectdate = collectdate;
        }

        public String getCollecttag() {
            return collecttag;
        }

        public void setCollecttag(String collecttag) {
            this.collecttag = collecttag;
        }

        public String getCollecttitle() {
            return collecttitle;
        }

        public void setCollecttitle(String collecttitle) {
            this.collecttitle = collecttitle;
        }

        public String getCollectpicurl() {
            return collectpicurl;
        }

        public void setCollectpicurl(String collectpicurl) {
            this.collectpicurl = collectpicurl;
        }
    }

    public CollectBean getCollect() {
        return collect;
    }

    public void setCollect(CollectBean collect) {
        this.collect = collect;
    }
}
