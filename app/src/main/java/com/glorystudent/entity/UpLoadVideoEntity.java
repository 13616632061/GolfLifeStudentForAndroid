package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/3/14.
 */
public class UpLoadVideoEntity {

    /**
     * listvideos : [{"video_id":1163,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":"测试机","video_type":"2","video_path":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/CoBg87lzza67JwHlppWA%3D%3D/videos/ba3e7a67322e1a44ab51adfb4ff2c246_da1eb54a49f655b9d0a8e31b5405c93e.mp4","video_datetime":"2017-03-14T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":"ba3e7a67322e1a44ab51adfb4ff2c246_da1eb54a49f655b9d0a8e31b5405c93e","video_picpath":"https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/61f1109767442403540001e02fe744bd.png","video_filepicpath":null,"video_filePicMD5":null}]
     * version : 1.0.3
     * datetime : 2017/3/14 14:51:55
     * accesstoken : QwmMbG0f58T2ikXFq1NfUzWbVkMfBdDJ1P7p1vat28Yt90v8ZU6aRjgBoEg0qaIaAStIxxnRHWXjZ8YPB4bGMVf/hNysXj2w8l+mS+9RQxuXY8UFkIlLzwGXv2zemJtv4lfxsReqDb5oqUEBr+blZt2h/9mkN18TY/KRVqOAMPT6mu5IW6lET1eVOpun1j9Oy6Depx55KOKGm0kPNBvI8q0i8DcDVqHn2/rdzx/d4nudDIBtYPyFDYI6xzsCv4LALz17yS5c/RgChH6qt58rrVNwmVk1xJ676aIJwuvyZ/HdfcUOFgHAgKjcgPdWv6sy
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 1
     * totalpagenum : 1
     * nowpagenum : null
     * pagerownum : 20
     */

    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private String totalrownum;
    private String totalpagenum;
    private Object nowpagenum;
    private String pagerownum;
    private List<ListvideosBean> listvideos;

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

    public String getTotalpagenum() {
        return totalpagenum;
    }

    public void setTotalpagenum(String totalpagenum) {
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

    public List<ListvideosBean> getListvideos() {
        return listvideos;
    }

    public void setListvideos(List<ListvideosBean> listvideos) {
        this.listvideos = listvideos;
    }

    public static class ListvideosBean {
        /**
         * video_id : 1163
         * video_belonsuserid : 111
         * video_userid : 111
         * video_username : null
         * video_tag : 测试机
         * video_type : 2
         * video_path : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/CoBg87lzza67JwHlppWA%3D%3D/videos/ba3e7a67322e1a44ab51adfb4ff2c246_da1eb54a49f655b9d0a8e31b5405c93e.mp4
         * video_datetime : 2017-03-14T00:00:00
         * video_remark : null
         * video_tips : null
         * video_filemd5 : ba3e7a67322e1a44ab51adfb4ff2c246_da1eb54a49f655b9d0a8e31b5405c93e
         * video_picpath : https://glorygolflife.oss-cn-shenzhen.aliyuncs.com/61f1109767442403540001e02fe744bd.png
         * video_filepicpath : null
         * video_filePicMD5 : null
         */

        private int video_id;
        private int video_belonsuserid;
        private int video_userid;
        private Object video_username;
        private String video_tag;
        private String video_type;
        private String video_path;
        private String video_datetime;
        private Object video_remark;
        private Object video_tips;
        private String video_filemd5;
        private String video_picpath;
        private Object video_filepicpath;
        private Object video_filePicMD5;

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public int getVideo_belonsuserid() {
            return video_belonsuserid;
        }

        public void setVideo_belonsuserid(int video_belonsuserid) {
            this.video_belonsuserid = video_belonsuserid;
        }

        public int getVideo_userid() {
            return video_userid;
        }

        public void setVideo_userid(int video_userid) {
            this.video_userid = video_userid;
        }

        public Object getVideo_username() {
            return video_username;
        }

        public void setVideo_username(Object video_username) {
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

        public Object getVideo_remark() {
            return video_remark;
        }

        public void setVideo_remark(Object video_remark) {
            this.video_remark = video_remark;
        }

        public Object getVideo_tips() {
            return video_tips;
        }

        public void setVideo_tips(Object video_tips) {
            this.video_tips = video_tips;
        }

        public String getVideo_filemd5() {
            return video_filemd5;
        }

        public void setVideo_filemd5(String video_filemd5) {
            this.video_filemd5 = video_filemd5;
        }

        public String getVideo_picpath() {
            return video_picpath;
        }

        public void setVideo_picpath(String video_picpath) {
            this.video_picpath = video_picpath;
        }

        public Object getVideo_filepicpath() {
            return video_filepicpath;
        }

        public void setVideo_filepicpath(Object video_filepicpath) {
            this.video_filepicpath = video_filepicpath;
        }

        public Object getVideo_filePicMD5() {
            return video_filePicMD5;
        }

        public void setVideo_filePicMD5(Object video_filePicMD5) {
            this.video_filePicMD5 = video_filePicMD5;
        }
    }
}
