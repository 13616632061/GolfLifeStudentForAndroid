package com.glorystudent.entity;

/**
 * 获取视频信息
 * Created by hyj on 2017/1/9.
 */
public class VideosRequestEntity {
    private VideosBean videos;
    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public static class VideosBean {
        private Integer video_id;
        private Integer video_belonsuserid;
        private Integer video_userid;
        private String video_username;
        private String video_tag;
        private String video_type;
        private String video_datetime;
        private String video_remark;
        private String video_tips;
        private String video_filemd5;

        public Integer getVideo_id() {
            return video_id;
        }

        public void setVideo_id(Integer video_id) {
            this.video_id = video_id;
        }

        public Integer getVideo_belonsuserid() {
            return video_belonsuserid;
        }

        public void setVideo_belonsuserid(Integer video_belonsuserid) {
            this.video_belonsuserid = video_belonsuserid;
        }

        public Integer getVideo_userid() {
            return video_userid;
        }

        public void setVideo_userid(Integer video_userid) {
            this.video_userid = video_userid;
        }

        public String getVideo_username() {
            return video_username;
        }

        public void setVideo_username(String video_username) {
            this.video_username = video_username;
        }

        public String getVideo_tag() {
            return video_tag;
        }

        public void setVideo_tag(String video_tag) {
            this.video_tag = video_tag;
        }

        public String getVideo_type() {
            return video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getVideo_datetime() {
            return video_datetime;
        }

        public void setVideo_datetime(String video_datetime) {
            this.video_datetime = video_datetime;
        }

        public String getVideo_remark() {
            return video_remark;
        }

        public void setVideo_remark(String video_remark) {
            this.video_remark = video_remark;
        }

        public String getVideo_tips() {
            return video_tips;
        }

        public void setVideo_tips(String video_tips) {
            this.video_tips = video_tips;
        }

        public String getVideo_filemd5() {
            return video_filemd5;
        }

        public void setVideo_filemd5(String video_filemd5) {
            this.video_filemd5 = video_filemd5;
        }
    }

    public VideosBean getVideos() {
        return videos;
    }

    public void setVideos(VideosBean videos) {
        this.videos = videos;
    }
}