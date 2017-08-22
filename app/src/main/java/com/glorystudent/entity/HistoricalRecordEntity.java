package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/1/6.
 */
public class HistoricalRecordEntity {


    /**
     * tests : null
     * testsList : [{"testID":1244737,"testCourtID":null,"testCourtName":null,"coachID":1265,"playersTeeType":null,"playersUserName":"12345678","playersUserID":0,"testType":3,"testCreateTime":"2017-07-21T16:52:44"},{"testID":1477454,"testCourtID":null,"testCourtName":null,"coachID":1265,"playersTeeType":null,"playersUserName":"12345678","playersUserID":0,"testType":3,"testCreateTime":"2017-07-24T09:31:21"},{"testID":1477980,"testCourtID":null,"testCourtName":null,"coachID":1265,"playersTeeType":null,"playersUserName":"12345678","playersUserID":0,"testType":3,"testCreateTime":"2017-07-24T09:40:07"},{"testID":1478621,"testCourtID":null,"testCourtName":null,"coachID":1265,"playersTeeType":null,"playersUserName":"12345678","playersUserID":0,"testType":1,"testCreateTime":"2017-07-24T09:50:48"},{"testID":1478795,"testCourtID":null,"testCourtName":null,"coachID":1265,"playersTeeType":null,"playersUserName":"12345678","playersUserID":0,"testType":1,"testCreateTime":"2017-07-24T09:53:42"}]
     * testresult : null
     * version : null
     * datetime : null
     * accesstoken : null
     * statuscode : 1
     * statusmessage : 消息处理成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private Object tests;
    private Object testresult;
    private Object version;
    private Object datetime;
    private Object accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;
    private List<TestsListBean> testsList;

    public Object getTests() {
        return tests;
    }

    public void setTests(Object tests) {
        this.tests = tests;
    }

    public Object getTestresult() {
        return testresult;
    }

    public void setTestresult(Object testresult) {
        this.testresult = testresult;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getDatetime() {
        return datetime;
    }

    public void setDatetime(Object datetime) {
        this.datetime = datetime;
    }

    public Object getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(Object accesstoken) {
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

    public List<TestsListBean> getTestsList() {
        return testsList;
    }

    public void setTestsList(List<TestsListBean> testsList) {
        this.testsList = testsList;
    }

    public static class TestsListBean {
        /**
         * testID : 1244737
         * testCourtID : null
         * testCourtName : null
         * coachID : 1265
         * playersTeeType : null
         * playersUserName : 12345678
         * playersUserID : 0
         * testType : 3
         * testCreateTime : 2017-07-21T16:52:44
         */

        private int testID;
        private Object testCourtID;
        private Object testCourtName;
        private int coachID;
        private Object playersTeeType;
        private String playersUserName;
        private int playersUserID;
        private int testType;
        private String testCreateTime;

        public int getTestID() {
            return testID;
        }

        public void setTestID(int testID) {
            this.testID = testID;
        }

        public Object getTestCourtID() {
            return testCourtID;
        }

        public void setTestCourtID(Object testCourtID) {
            this.testCourtID = testCourtID;
        }

        public Object getTestCourtName() {
            return testCourtName;
        }

        public void setTestCourtName(Object testCourtName) {
            this.testCourtName = testCourtName;
        }

        public int getCoachID() {
            return coachID;
        }

        public void setCoachID(int coachID) {
            this.coachID = coachID;
        }

        public Object getPlayersTeeType() {
            return playersTeeType;
        }

        public void setPlayersTeeType(Object playersTeeType) {
            this.playersTeeType = playersTeeType;
        }

        public String getPlayersUserName() {
            return playersUserName;
        }

        public void setPlayersUserName(String playersUserName) {
            this.playersUserName = playersUserName;
        }

        public int getPlayersUserID() {
            return playersUserID;
        }

        public void setPlayersUserID(int playersUserID) {
            this.playersUserID = playersUserID;
        }

        public int getTestType() {
            return testType;
        }

        public void setTestType(int testType) {
            this.testType = testType;
        }

        public String getTestCreateTime() {
            return testCreateTime;
        }

        public void setTestCreateTime(String testCreateTime) {
            this.testCreateTime = testCreateTime;
        }
    }
}
