package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/19.
 */
public class AliyunVideoRequestEntity {
    private VideosBean videos;
    public static class VideosBean{
        private String video_belonsuserid;
        private String video_userid;
        private String video_username;
        private String video_tag;
        private String video_type;
        private String video_path;
        private String video_datetime;
        private String video_remark;
        private String video_tips;
        private String video_filemd5;
        private String video_picpath;
        private String video_filepicpath;
        private String video_filePicMD5;

        public String getVideo_picpath() {
            return video_picpath;
        }

        public void setVideo_picpath(String video_picpath) {
            this.video_picpath = video_picpath;
        }

        public String getVideo_filepicpath() {
            return video_filepicpath;
        }

        public void setVideo_filepicpath(String video_filepicpath) {
            this.video_filepicpath = video_filepicpath;
        }

        public String getVideo_filePicMD5() {
            return video_filePicMD5;
        }

        public void setVideo_filePicMD5(String video_filePicMD5) {
            this.video_filePicMD5 = video_filePicMD5;
        }

        public String getVideo_belonsuserid() {
            return video_belonsuserid;
        }

        public void setVideo_belonsuserid(String video_belonsuserid) {
            this.video_belonsuserid = video_belonsuserid;
        }

        public String getVideo_userid() {
            return video_userid;
        }

        public void setVideo_userid(String video_userid) {
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

        public String getVideo_path() {
            return video_path;
        }

        public void setVideo_path(String video_path) {
            this.video_path = video_path;
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
