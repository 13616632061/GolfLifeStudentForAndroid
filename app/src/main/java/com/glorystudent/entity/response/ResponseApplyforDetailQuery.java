package com.glorystudent.entity.response;

/**
 * Created by billlamian on 17-8-4.
 * 查询申请记录详情响应信息
 */

public class ResponseApplyforDetailQuery extends BaseResponseEntity {


    /**
     * applyCashInfos : null
     * applyCash : {"ID":37,"applyDate":"2017-08-04T14:29:54","applyUserID":1266,"applyBankID":28,"applyMoney":100,"applyState":1,"successTime":null,"remark":null}
     */

    private Object applyCashInfos;
    private ApplyCashBean applyCash;

    public Object getApplyCashInfos() {
        return applyCashInfos;
    }

    public void setApplyCashInfos(Object applyCashInfos) {
        this.applyCashInfos = applyCashInfos;
    }

    public ApplyCashBean getApplyCash() {
        return applyCash;
    }

    public void setApplyCash(ApplyCashBean applyCash) {
        this.applyCash = applyCash;
    }

    public static class ApplyCashBean {
        /**
         * ID : 37
         * applyDate : 2017-08-04T14:29:54
         * applyUserID : 1266
         * applyBankID : 28
         * applyMoney : 100
         * applyState : 1
         * successTime : null
         * remark : null
         */

        private String ID;
        private String applyDate;
        private int applyUserID;
        private int applyBankID;
        private float applyMoney;
        private int applyState;
        private String successTime;
        private String remark;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getApplyDate() {
            return applyDate;
        }

        public void setApplyDate(String applyDate) {
            this.applyDate = applyDate;
        }

        public int getApplyUserID() {
            return applyUserID;
        }

        public void setApplyUserID(int applyUserID) {
            this.applyUserID = applyUserID;
        }

        public int getApplyBankID() {
            return applyBankID;
        }

        public void setApplyBankID(int applyBankID) {
            this.applyBankID = applyBankID;
        }

        public float getApplyMoney() {
            return applyMoney;
        }

        public void setApplyMoney(float applyMoney) {
            this.applyMoney = applyMoney;
        }

        public int getApplyState() {
            return applyState;
        }

        public void setApplyState(int applyState) {
            this.applyState = applyState;
        }

        public String getSuccessTime() {
            return successTime;
        }

        public void setSuccessTime(String successTime) {
            this.successTime = successTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
