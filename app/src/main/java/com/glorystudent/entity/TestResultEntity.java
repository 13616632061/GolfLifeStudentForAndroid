package com.glorystudent.entity;

import java.util.List;

/**
 * Created by hyj on 2017/1/5.
 */
public class TestResultEntity {

    /**
     * testid : 11
     * testresult : {"parcount":50,"createtime":"2017-01-05T00:00:00","players_username":"何跃进","testhole":[{"fairwayname":"开球上球道率","fairwayrate":"56%","tenrate":"30%","ninerate":"40%","eightrate":"50%"},{"fairwayname":"标准杆上果岭率","fairwayrate":"67%","tenrate":"20%","ninerate":"30%","eightrate":"40%"},{"fairwayname":"切杆救球成功率","fairwayrate":"100%","tenrate":"20%","ninerate":"30%","eightrate":"40%"},{"fairwayname":"沙坑救球成功率","fairwayrate":"0%","tenrate":"10%","ninerate":"20%","eightrate":"30%"},{"fairwayname":"Birdie","fairwayrate":"0","tenrate":"1","ninerate":"2","eightrate":"3"},{"fairwayname":"Par","fairwayrate":"6","tenrate":"3","ninerate":"6","eightrate":"9"},{"fairwayname":"Bogey","fairwayrate":"0","tenrate":"9","ninerate":"6","eightrate":"3"},{"fairwayname":"Double Bogey","fairwayrate":"3","tenrate":"5","ninerate":"4","eightrate":"3"}]}
     * version : 1.1.106
     * datetime : 2017/1/5 17:56:27
     * accesstoken : jT3SQVXQqI7TjBcGo39ieJA7Y2khZkF6ispt3bMwRVsupYnxcxLfQCLtLKyL7XcV+Q7xVv2w+6mWpV+f/d/mXDm+O8zn1KZXnmuajzE8OlDFggEUnbJ+Hum2lqA31y0Scvp/OEgDPt+wbGraz0llXJD+fWP3NZnjZrTXRj4ZRcHtg5xhK05aUqW0OvZtREjUdlAPqRdl6W5q1hImb2e3LltUC7TigDIFWbZf062mcLZ8EL5MrS9yVhkTfB/AgK7ByxR35RNlrZKYQS90Q0wo5QrkwLc0QyYvjA7hn9fPNZjJ5noK/DT6UNeBZe4sn8cp
     * statuscode : 1
     * statusmessage : 添加成功
     * totalrownum : null
     * totalpagenum : null
     * nowpagenum : null
     * pagerownum : null
     */

    private String testid;
    private TestresultBean testresult;
    private String version;
    private String datetime;
    private String accesstoken;
    private int statuscode;
    private String statusmessage;
    private Object totalrownum;
    private Object totalpagenum;
    private Object nowpagenum;
    private Object pagerownum;

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public TestresultBean getTestresult() {
        return testresult;
    }

    public void setTestresult(TestresultBean testresult) {
        this.testresult = testresult;
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

    public static class TestresultBean {
        /**
         * parcount : 50
         * createtime : 2017-01-05T00:00:00
         * players_username : 何跃进
         * testhole : [{"fairwayname":"开球上球道率","fairwayrate":"56%","tenrate":"30%","ninerate":"40%","eightrate":"50%"},{"fairwayname":"标准杆上果岭率","fairwayrate":"67%","tenrate":"20%","ninerate":"30%","eightrate":"40%"},{"fairwayname":"切杆救球成功率","fairwayrate":"100%","tenrate":"20%","ninerate":"30%","eightrate":"40%"},{"fairwayname":"沙坑救球成功率","fairwayrate":"0%","tenrate":"10%","ninerate":"20%","eightrate":"30%"},{"fairwayname":"Birdie","fairwayrate":"0","tenrate":"1","ninerate":"2","eightrate":"3"},{"fairwayname":"Par","fairwayrate":"6","tenrate":"3","ninerate":"6","eightrate":"9"},{"fairwayname":"Bogey","fairwayrate":"0","tenrate":"9","ninerate":"6","eightrate":"3"},{"fairwayname":"Double Bogey","fairwayrate":"3","tenrate":"5","ninerate":"4","eightrate":"3"}]
         */

        private int parcount;
        private String createtime;
        private String players_username;
        private List<TestholeBean> testhole;

        public int getParcount() {
            return parcount;
        }

        public void setParcount(int parcount) {
            this.parcount = parcount;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPlayers_username() {
            return players_username;
        }

        public void setPlayers_username(String players_username) {
            this.players_username = players_username;
        }

        public List<TestholeBean> getTesthole() {
            return testhole;
        }

        public void setTesthole(List<TestholeBean> testhole) {
            this.testhole = testhole;
        }

        public static class TestholeBean {
            /**
             * fairwayname : 开球上球道率
             * fairwayrate : 56%
             * tenrate : 30%
             * ninerate : 40%
             * eightrate : 50%
             */

            private String fairwayname;
            private String fairwayrate;
            private String tenrate;
            private String ninerate;
            private String eightrate;

            public String getFairwayname() {
                return fairwayname;
            }

            public void setFairwayname(String fairwayname) {
                this.fairwayname = fairwayname;
            }

            public String getFairwayrate() {
                return fairwayrate;
            }

            public void setFairwayrate(String fairwayrate) {
                this.fairwayrate = fairwayrate;
            }

            public String getTenrate() {
                return tenrate;
            }

            public void setTenrate(String tenrate) {
                this.tenrate = tenrate;
            }

            public String getNinerate() {
                return ninerate;
            }

            public void setNinerate(String ninerate) {
                this.ninerate = ninerate;
            }

            public String getEightrate() {
                return eightrate;
            }

            public void setEightrate(String eightrate) {
                this.eightrate = eightrate;
            }
        }
    }
}
