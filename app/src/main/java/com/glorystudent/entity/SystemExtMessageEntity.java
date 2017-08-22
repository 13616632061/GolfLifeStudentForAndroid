package com.glorystudent.entity;

/**
 * Created by hyj on 2017/3/15.
 */
public class SystemExtMessageEntity {
    private DialogBean dialog;
    private String ifjump;
    private String picPath;
    private String url;
    private String appPage;

    public String getAppPage() {
        return appPage;
    }

    public void setAppPage(String appPage) {
        this.appPage = appPage;
    }

    public DialogBean getDialog() {
        return dialog;
    }

    public void setDialog(DialogBean dialog) {
        this.dialog = dialog;
    }

    public String getIfjump() {
        return ifjump;
    }

    public void setIfjump(String ifjump) {
        this.ifjump = ifjump;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class DialogBean{
        private String button;
        private ButtonCallBackBean buttonCallBack;

        public String getButton() {
            return button;
        }

        public void setButton(String button) {
            this.button = button;
        }

        public ButtonCallBackBean getButtonCallBack() {
            return buttonCallBack;
        }

        public void setButtonCallBack(ButtonCallBackBean buttonCallBack) {
            this.buttonCallBack = buttonCallBack;
        }

        public static class ButtonCallBackBean{
            private Integer appointmentid;
            private String cancel;
            private Integer ccoursedetailid;
            private String ok;
            private String title;

            public Integer getAppointmentid() {
                return appointmentid;
            }

            public void setAppointmentid(Integer appointmentid) {
                this.appointmentid = appointmentid;
            }

            public String getCancel() {
                return cancel;
            }

            public void setCancel(String cancel) {
                this.cancel = cancel;
            }

            public Integer getCcoursedetailid() {
                return ccoursedetailid;
            }

            public void setCcoursedetailid(Integer ccoursedetailid) {
                this.ccoursedetailid = ccoursedetailid;
            }

            public String getOk() {
                return ok;
            }

            public void setOk(String ok) {
                this.ok = ok;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "ButtonCallBackBean{" +
                        "appointmentid='" + appointmentid + '\'' +
                        ", cancel='" + cancel + '\'' +
                        ", ccoursedetailid='" + ccoursedetailid + '\'' +
                        ", ok='" + ok + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DialogBean{" +
                    "button='" + button + '\'' +
                    ", buttonCallBack=" + buttonCallBack +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SystemExtMessageEntity{" +
                "dialog=" + dialog +
                ", ifjump='" + ifjump + '\'' +
                ", picPath='" + picPath + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
