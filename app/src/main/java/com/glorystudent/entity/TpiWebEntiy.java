package com.glorystudent.entity;

import java.util.Map;

/**
 * Created by lucy on 2017/7/21.
 */
public class TpiWebEntiy {

    private String playersUserName;

    private String testCreateTime;

    private Map<String, Integer> testTPI;


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

    public String getTestCreateTime() {
        return testCreateTime;
    }

    public void setTestCreateTime(String testCreateTime) {
        this.testCreateTime = testCreateTime;
    }


}