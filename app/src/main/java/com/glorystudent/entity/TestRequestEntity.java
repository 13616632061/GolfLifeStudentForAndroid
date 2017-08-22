package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/1/5.
 */
public class TestRequestEntity {
    private TestIntegrationBean Tests;
    public static class TestIntegrationBean{
        private Integer test_holecount;
        private String test_createtime;
        private TestplayerBean testplayer;


        public Integer getTest_holecount() {
            return test_holecount;
        }

        public void setTest_holecount(Integer test_holecount) {
            this.test_holecount = test_holecount;
        }

        public String getTest_createtime() {
            return test_createtime;
        }

        public void setTest_createtime(String test_createtime) {
            this.test_createtime = test_createtime;
        }
        public TestplayerBean getTestplayer() {
            return testplayer;
        }

        public void setTestplayer(TestplayerBean testplayer) {
            this.testplayer = testplayer;
        }
    }
    public static class TestplayerBean{
        private Integer test_id;
        private Integer players_userid;
        private String players_username;
        private List<ListtestholeBean> testHole;
        public Integer getTest_id() {
            return test_id;
        }

        public void setTest_id(Integer test_id) {
            this.test_id = test_id;
        }

        public Integer getPlayers_userid() {
            return players_userid;
        }

        public void setPlayers_userid(Integer players_userid) {
            this.players_userid = players_userid;
        }

        public String getPlayers_username() {
            return players_username;
        }

        public void setPlayers_username(String players_username) {
            this.players_username = players_username;
        }

        public List<ListtestholeBean> getListtesthole() {
            return testHole;
        }

        public void setListtesthole(List<ListtestholeBean> listtesthole) {
            this.testHole = listtesthole;
        }
    }
    public static class ListtestholeBean{
        private Integer players_id;
        private Integer hole_number;
        private Integer shots;
        private Integer fairway;
        private Integer parongreen;
        private String cut;
        private String bunker;




        public Integer getPlayers_id() {
            return players_id;
        }

        public void setPlayers_id(Integer players_id) {
            this.players_id = players_id;
        }

        public Integer getHole_number() {
            return hole_number;
        }

        public void setHole_number(Integer hole_number) {
            this.hole_number = hole_number;
        }

        public Integer getShots() {
            return shots;
        }

        public void setShots(Integer shots) {
            this.shots = shots;
        }

        public Integer getFairway() {
            return fairway;
        }

        public void setFairway(Integer fairway) {
            this.fairway = fairway;
        }

        public Integer getParongreen() {
            return parongreen;
        }

        public void setParongreen(Integer parongreen) {
            this.parongreen = parongreen;
        }

        public String getCut() {
            return cut;
        }

        public void setCut(String cut) {
            this.cut = cut;
        }

        public String getBunker() {
            return bunker;
        }

        public void setBunker(String bunker) {
            this.bunker = bunker;
        }
    }

    public TestIntegrationBean getTestIntegration() {
        return Tests;
    }

    public void setTestIntegration(TestIntegrationBean testIntegration) {
        this.Tests = testIntegration;
    }

}
