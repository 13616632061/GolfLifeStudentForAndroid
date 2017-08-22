package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/1/20.
 */
public class CloudVideoEntity implements Serializable {

    /**
     * listvideos : [{"video_id":1036,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/f485b1bde9ff29204bb1b67f060a5f41?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484876345&Signature=GwrVESmn9X57X7GghmXIMMy6wrg%3D","video_datetime":"2017-01-20T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":"f485b1bde9ff29204bb1b67f060a5f41","video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1037,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/628100944dfda4fb75a90acfb5860a3c?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484882409&Signature=u4290sd8S4o8dPgd7YVJyidHj6g%3D","video_datetime":"2017-01-20T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":"628100944dfda4fb75a90acfb5860a3c","video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1038,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":"何跃进","video_type":"1","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/628100944dfda4fb75a90acfb5860a3c?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484883333&Signature=XKeOykXOpA3GwRx6X/eo1UZiJ%2B8%3D","video_datetime":"2017-01-20T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":"628100944dfda4fb75a90acfb5860a3c","video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1017,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484812884594?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484814783&Signature=ND0kGsy3SMEn8d6KrX749WpdMiI%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":null,"video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1016,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484812893954?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484814780&Signature=RTwuA9RCI8tshshvkheaVJdHvt0%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":null,"video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1015,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484812893946?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484814770&Signature=icZlqtyew81aZTODepWcmwPskKY%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":null,"video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1014,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484812893937?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484814756&Signature=a2955ZEzeY%2BQAUjkakRCdnQhHkA%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":null,"video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1013,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484812884582?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484814720&Signature=dsCY3zL0CDltPtwItxS7Fwh0FS8%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":null,"video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1012,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484810416511.mp4?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484812232&Signature=UpfuAAMwPNXxisXSLGFxrNFBXXk%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":"de8213657e043d2f47a3721447c6caf","video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null},{"video_id":1011,"video_belonsuserid":111,"video_userid":111,"video_username":null,"video_tag":null,"video_type":"\u0000","video_path":"http://honor1.oss-cn-shenzhen.aliyuncs.com/CoBg8/7lz/za67JwHlppWA%3D%3D1484810141067.mp4?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484811987&Signature=%2BknVrXPNFGyJIbfG6LYd2tmu9OA%3D","video_datetime":"2017-01-19T00:00:00","video_remark":null,"video_tips":null,"video_filemd5":"4afa39c8991b0147ccafe7c85fc7a78c","video_picpath":null,"video_filepicpath":null,"video_filePicMD5":null}]
     * version : 1.1.106
     * datetime : 2017/1/20 11:11:57
     * accesstoken : jT3SQVXQqI7TjBcGo39ieKvizNBBu3aEIiPwM4u08H2otqEZ0/bOb4DEhCaU tOXOr1CO3d8ZP5V66OsRB19ZeBAMhpk3dK4o6GiI01afdJCM 95tfl0g0TtloYTPamm8Snim9 hjcEJnscpuvSOEZjdUk/0ljGYUMZX7dJmslVRBQeLNxeU/v/5VgB/mxVZH4AsbbLLvgtZFXMISo0HZU5vdYXKp8gxrmGTSlgw9T1bKT3FyVkteaHJsb5CGbYjCGI7AxrlQz8lo5JyjEAsQ0mAxmMgTOlmFNdWL5JgjmNqgONfUFsEkISWzJ9G/e0e
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 32
     * totalpagenum : 4
     * nowpagenum : null
     * pagerownum : 10
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

    public static class ListvideosBean implements Serializable {
        /**
         * video_id : 1036
         * video_belonsuserid : 111
         * video_userid : 111
         * video_username : null
         * video_tag : null
         * video_type :
         * video_path : http://honor1.oss-cn-shenzhen.aliyuncs.com/f485b1bde9ff29204bb1b67f060a5f41?OSSAccessKeyId=LTAIxW9T5z7uLGcX&Expires=1484876345&Signature=GwrVESmn9X57X7GghmXIMMy6wrg%3D
         * video_datetime : 2017-01-20T00:00:00
         * video_remark : null
         * video_tips : null
         * video_filemd5 : f485b1bde9ff29204bb1b67f060a5f41
         * video_picpath : null
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
        private String video_tips;
        private String video_filemd5;
        private String video_picpath;
        private Object video_filepicpath;
        private Object video_filePicMD5;
        private boolean selectFlag;
        private boolean expandFlag;
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isExpandFlag() {
            return expandFlag;
        }

        public void setExpandFlag(boolean expandFlag) {
            this.expandFlag = expandFlag;
        }

        public boolean isSelectFlag() {
            return selectFlag;
        }

        public void setSelectFlag(boolean selectFlag) {
            this.selectFlag = selectFlag;
        }

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

        @Override
        public String toString() {
            return "ListvideosBean{" +
                    "video_id=" + video_id +
                    ", video_belonsuserid=" + video_belonsuserid +
                    ", video_userid=" + video_userid +
                    ", video_username=" + video_username +
                    ", video_tag='" + video_tag + '\'' +
                    ", video_type='" + video_type + '\'' +
                    ", video_path='" + video_path + '\'' +
                    ", video_datetime='" + video_datetime + '\'' +
                    ", video_remark=" + video_remark +
                    ", video_tips=" + video_tips +
                    ", video_filemd5='" + video_filemd5 + '\'' +
                    ", video_picpath='" + video_picpath + '\'' +
                    ", video_filepicpath=" + video_filepicpath +
                    ", video_filePicMD5=" + video_filePicMD5 +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CloudVideoEntity{" +
                "version='" + version + '\'' +
                ", datetime='" + datetime + '\'' +
                ", accesstoken='" + accesstoken + '\'' +
                ", statuscode=" + statuscode +
                ", statusmessage='" + statusmessage + '\'' +
                ", totalrownum='" + totalrownum + '\'' +
                ", totalpagenum='" + totalpagenum + '\'' +
                ", nowpagenum=" + nowpagenum +
                ", pagerownum='" + pagerownum + '\'' +
                ", listvideos=" + listvideos +
                '}';
    }
}
