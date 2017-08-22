package com.glorystudent.entity;

import java.util.List;

/**
 * 套餐信息
 * Created by hyj on 2017/1/6.
 */
public class PackageEntity {

    /**
     * listPackage : [{"packageid":132,"packagecode":"00010001","packagename":"基础教学","coursenumbercount":3,"packageprice":200,"coachgroupid":2,"status":1},{"packageid":136,"packagecode":"00010002","packagename":"实地教学","coursenumbercount":4,"packageprice":400,"coachgroupid":2,"status":1}]
     * packagecommodity : []
     * packagecourse : [{"_CourseNumber":3,"_GourpID":2,"_Status":1,"courseid":7,"coursecode":"01","coursename":"挥杆","coursenumber":3,"courseprice":200,"gourpid":2,"status":1,"remark":"1"},{"_CourseNumber":3,"_GourpID":2,"_Status":1,"courseid":15,"coursecode":"02","coursename":"技术要领","coursenumber":3,"courseprice":500,"gourpid":2,"status":1,"remark":"1"},{"_CourseNumber":1,"_GourpID":2,"_Status":1,"courseid":16,"coursecode":"03","coursename":"视频教学","coursenumber":1,"courseprice":200,"gourpid":2,"status":1,"remark":"1"},{"_CourseNumber":1000,"_GourpID":2,"_Status":1,"courseid":17,"coursecode":"04","coursename":"远程教学","coursenumber":1000,"courseprice":28000,"gourpid":2,"status":1,"remark":"1"},{"_CourseNumber":2,"_GourpID":2,"_Status":1,"courseid":19,"coursecode":"05","coursename":"比赛教学","coursenumber":2,"courseprice":300,"gourpid":2,"status":1,"remark":"1"}]
     * version : 1.1.106
     * datetime : 2017/1/6 18:06:01
     * accesstoken : jT3SQVXQqI7TjBcGo39ieCuDWPKINx/zwLcoklT7QR9WKAi/UTFvskIqZBZTcrDA2aDkuB/+A6X6T51ZbYFwZuIwL+40SaBKEeGRr6F3m9/ssqmHvmdCHsd8jJCVaJ5ml4uI901UsmDZcxGg0iBp6cjYlx1dn4gpkIcgaNYtdRacnbtibaumfzvaMTD8xqEy+cQCods+duTK5j5qX4iM359jRiV/MwAuJHQkoD4j+vIDIPFo1p4zxJBL1D7ryKG5Efr8Y2CF/wVp3lK4Xw43b0Zp88MnO0FxvZ5WXFzG8gfVfXXmjhaDvLwugvBFr4PX
     * statuscode : 1
     * statusmessage : 成功
     * totalrownum : 2
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
    private List<ListPackageBean> listPackage;
    private List<?> packagecommodity;
    private List<PackagecourseBean> packagecourse;

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

    public List<ListPackageBean> getListPackage() {
        return listPackage;
    }

    public void setListPackage(List<ListPackageBean> listPackage) {
        this.listPackage = listPackage;
    }

    public List<?> getPackagecommodity() {
        return packagecommodity;
    }

    public void setPackagecommodity(List<?> packagecommodity) {
        this.packagecommodity = packagecommodity;
    }

    public List<PackagecourseBean> getPackagecourse() {
        return packagecourse;
    }

    public void setPackagecourse(List<PackagecourseBean> packagecourse) {
        this.packagecourse = packagecourse;
    }

    public static class ListPackageBean {
        /**
         * packageid : 132
         * packagecode : 00010001
         * packagename : 基础教学
         * coursenumbercount : 3
         * packageprice : 200.0
         * coachgroupid : 2
         * status : 1
         */

        private int packageid;
        private String packagecode;
        private String packagename;
        private int coursenumbercount;
        private double packageprice;
        private int coachgroupid;
        private int status;

        public int getPackageid() {
            return packageid;
        }

        public void setPackageid(int packageid) {
            this.packageid = packageid;
        }

        public String getPackagecode() {
            return packagecode;
        }

        public void setPackagecode(String packagecode) {
            this.packagecode = packagecode;
        }

        public String getPackagename() {
            return packagename;
        }

        public void setPackagename(String packagename) {
            this.packagename = packagename;
        }

        public int getCoursenumbercount() {
            return coursenumbercount;
        }

        public void setCoursenumbercount(int coursenumbercount) {
            this.coursenumbercount = coursenumbercount;
        }

        public double getPackageprice() {
            return packageprice;
        }

        public void setPackageprice(double packageprice) {
            this.packageprice = packageprice;
        }

        public int getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(int coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class PackagecourseBean {
        /**
         * _CourseNumber : 3
         * _GourpID : 2
         * _Status : 1
         * courseid : 7
         * coursecode : 01
         * coursename : 挥杆
         * coursenumber : 3
         * courseprice : 200.0
         * gourpid : 2
         * status : 1
         * remark : 1
         */

        private int _CourseNumber;
        private int _GourpID;
        private int _Status;
        private int courseid;
        private String coursecode;
        private String coursename;
        private int coursenumber;
        private double courseprice;
        private int gourpid;
        private int status;
        private String remark;

        public int get_CourseNumber() {
            return _CourseNumber;
        }

        public void set_CourseNumber(int _CourseNumber) {
            this._CourseNumber = _CourseNumber;
        }

        public int get_GourpID() {
            return _GourpID;
        }

        public void set_GourpID(int _GourpID) {
            this._GourpID = _GourpID;
        }

        public int get_Status() {
            return _Status;
        }

        public void set_Status(int _Status) {
            this._Status = _Status;
        }

        public int getCourseid() {
            return courseid;
        }

        public void setCourseid(int courseid) {
            this.courseid = courseid;
        }

        public String getCoursecode() {
            return coursecode;
        }

        public void setCoursecode(String coursecode) {
            this.coursecode = coursecode;
        }

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
        }

        public int getCoursenumber() {
            return coursenumber;
        }

        public void setCoursenumber(int coursenumber) {
            this.coursenumber = coursenumber;
        }

        public double getCourseprice() {
            return courseprice;
        }

        public void setCourseprice(double courseprice) {
            this.courseprice = courseprice;
        }

        public int getGourpid() {
            return gourpid;
        }

        public void setGourpid(int gourpid) {
            this.gourpid = gourpid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
