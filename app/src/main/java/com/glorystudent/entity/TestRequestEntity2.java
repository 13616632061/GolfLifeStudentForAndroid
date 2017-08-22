package com.glorystudent.entity;

import java.util.List;

/**
 * Created by lucy on 2017/7/21.
 */
public class TestRequestEntity2 {

    /**
     * Tests : {"test_createtime":"2017-07-21","test_holecount":9,"testHole":[{"bunker":"1","cut":"1","fairway":1,"hole_number":0,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":1,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":2,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":3,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":4,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":5,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":6,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":7,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":8,"parongreen":1,"shots":0}]}
     */

    private TestsBean Tests;

    public TestsBean getTests() {
        return Tests;
    }

    public void setTests(TestsBean Tests) {
        this.Tests = Tests;
    }

    public static class TestsBean {
        /**
         * test_createtime : 2017-07-21
         * test_holecount : 9
         * testHole : [{"bunker":"1","cut":"1","fairway":1,"hole_number":0,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":1,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":2,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":3,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":4,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":5,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":6,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":7,"parongreen":1,"shots":0},{"bunker":"1","cut":"1","fairway":1,"hole_number":8,"parongreen":1,"shots":0}]
         */

        private String test_createtime;
        private List<TestHoleBean> testHole;
        private int testType;
        private String playersUserName;
        private int playersUserID;

        public int getPlayersUserID() {
            return playersUserID;
        }

        public void setPlayersUserID(int playersUserID) {
            this.playersUserID = playersUserID;
        }

        public String getPlayersUserName() {
            return playersUserName;
        }

        public void setPlayersUserName(String playersUserName) {
            this.playersUserName = playersUserName;
        }

        public int getTestType() {
            return testType;
        }

        public void setTestType(int testType) {
            this.testType = testType;
        }

        public String getTest_createtime() {
            return test_createtime;
        }

        public void setTest_createtime(String test_createtime) {
            this.test_createtime = test_createtime;
        }




        public List<TestHoleBean> getTestHole() {
            return testHole;
        }

        public void setTestHole(List<TestHoleBean> testHole) {
            this.testHole = testHole;
        }

        public static class TestHoleBean {
            /**
             * bunker : 1
             * cut : 1
             * fairway : 1
             * hole_number : 0
             * parongreen : 1
             * shots : 0
             */

            private String bunker;
            private String cut;
            private int fairway;
            private int hole_number;
            private int parongreen;
            private int shots;
            private Integer par=0;
            private Integer pushcount=0;
            private Integer cutcount=0;
            private Integer cutsucess=0;
            private Integer bunkercount=0;
            private Integer bunkersucess=0;

            public String getBunker() {
                return bunker;
            }

            public void setBunker(String bunker) {
                this.bunker = bunker;
            }

            public String getCut() {
                return cut;
            }

            public void setCut(String cut) {
                this.cut = cut;
            }

            public int getFairway() {
                return fairway;
            }

            public void setFairway(int fairway) {
                this.fairway = fairway;
            }

            public int getHole_number() {
                return hole_number;
            }

            public void setHole_number(int hole_number) {
                this.hole_number = hole_number;
            }

            public int getParongreen() {
                return parongreen;
            }

            public void setParongreen(int parongreen) {
                this.parongreen = parongreen;
            }

            public int getShots() {
                return shots;
            }

            public void setShots(int shots) {
                this.shots = shots;
            }
        }
    }
}
