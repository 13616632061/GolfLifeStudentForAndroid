package com.glorystudent.entity;

import java.util.Map;

/**
 * Created by lucy on 2017/7/21.
 */
public class TpiTestEntiy {

    /**
     * userId : vmUbPNTWI+yv3Y1diSQiBRS/U+RRGcX2VarvuW/W04w=
     * testTPI : {"HoldShoulderLeft":"","HoldShoulderRight":"","MovementQuality":"","MovementRange":"","NotHoldShoulderLeft":"","NotHoldShoulderRight":"","StartRelvicPosition":""}
     */


    private TestTPIBean Tests;



    public TestTPIBean getTests() {
        return Tests;
    }

    public void setTests(TestTPIBean tests) {
        Tests = tests;
    }


    public static class TestTPIBean {
        private Map<String,Integer> testTPI;

        //测试者名字
        private String playersUserName;

        //测试好友id 如果不是好友为0
        private int playersUserID;

        //9洞穿1 18洞穿2 tpi传3
        private int testType;

        public Map<String, Integer> getTestTPI() {
            return testTPI;
        }

        public void setTestTPI(Map<String, Integer> testTPI) {
            this.testTPI = testTPI;
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

    }
}
