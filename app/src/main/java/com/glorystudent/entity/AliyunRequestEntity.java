package com.glorystudent.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyj on 2017/1/16.
 */
public class AliyunRequestEntity implements Serializable{

    /**
     * settingvalue : null
     * listsettingvalue : [{"settingid":5,"settingkey":"ALiOSSAKeyID","settingvalue":"LTAIxW9T5z7uLGcX","settingremark":"阿里OSS账户KeyID","isenable":true},{"settingid":7,"settingkey":"ALiOSSKeySecret","settingvalue":"fafCZgz3ySaTc2KMflVB2q64h9XYZD","settingremark":"阿里OSS账户KeySecret","isenable":true},{"settingid":8,"settingkey":"ALiOSSRegion","settingvalue":"oss-cn-shenzhen","settingremark":"阿里OSSURL","isenable":true},{"settingid":10,"settingkey":"ALiOSSBucket","settingvalue":"honor1","settingremark":"阿里OSSURL","isenable":true}]
     * version : 1.1.106
     * datetime : 2017/1/16 13:29:59
     * accesstoken : jT3SQVXQqI7TjBcGo39ieHXv6P8O6LnMghTxhcx+1AWOWpVBzJhW7C9+i79q1Kr+6634uoGymuqyzkTREl/MzgQUIqov3M7gfnkprkz7wOPALcBQMhHZZP6yXkfDon4cpVksxoh9+hR2vyM4abW6zYeVnF01lCjd+zBlPyDRwtYKH5dOBaZQtIptz06LGNhIqwHRQd5/FNrbEPWA97vOxYW1g8ZVcGail4aqOyX0WrEf5ywcTe4tt41CYQSMKC03MRhg+w0+OSwZM6tMv7AD6DN8UZGj18Zb2vR62WuoYioH3D48jMniYl6LALcyc+UA
     * statuscode : 1
     * statusmessage : 获取数据成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object settingvalue;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<ListsettingvalueBean> listsettingvalue;

    public Object getSettingvalue() {
        return settingvalue;
    }

    public void setSettingvalue(Object settingvalue) {
        this.settingvalue = settingvalue;
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

    public Object getTotalrownum() {
        return totalrownum;
    }

    public void setTotalrownum(Object totalrownum) {
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

    public Object getPagerownum() {
        return pagerownum;
    }

    public void setPagerownum(Object pagerownum) {
        this.pagerownum = pagerownum;
    }

    public List<ListsettingvalueBean> getListsettingvalue() {
        return listsettingvalue;
    }

    public void setListsettingvalue(List<ListsettingvalueBean> listsettingvalue) {
        this.listsettingvalue = listsettingvalue;
    }

    public static class ListsettingvalueBean implements Serializable{
        /**
         * settingid : 5
         * settingkey : ALiOSSAKeyID
         * settingvalue : LTAIxW9T5z7uLGcX
         * settingremark : 阿里OSS账户KeyID
         * isenable : true
         */

        private int settingid;
        private String settingkey;
        private String settingvalue;
        private String settingremark;
        private boolean isenable;

        public int getSettingid() {
            return settingid;
        }

        public void setSettingid(int settingid) {
            this.settingid = settingid;
        }

        public String getSettingkey() {
            return settingkey;
        }

        public void setSettingkey(String settingkey) {
            this.settingkey = settingkey;
        }

        public String getSettingvalue() {
            return settingvalue;
        }

        public void setSettingvalue(String settingvalue) {
            this.settingvalue = settingvalue;
        }

        public String getSettingremark() {
            return settingremark;
        }

        public void setSettingremark(String settingremark) {
            this.settingremark = settingremark;
        }

        public boolean isIsenable() {
            return isenable;
        }

        public void setIsenable(boolean isenable) {
            this.isenable = isenable;
        }
    }
}
