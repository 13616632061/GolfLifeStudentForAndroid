package com.glorystudent.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by billlamian on 17-8-4.
 * 查询申请记录响应信息
 */

public class ResponseApplyforRecordQuery extends BaseResponseEntity {

    private List<ApplyCashInfosBean> applyCashInfos;

    public List<ApplyCashInfosBean> getApplyCashInfos() {
        return applyCashInfos;
    }

    public void setApplyCashInfos(List<ApplyCashInfosBean> applyCashInfos) {
        this.applyCashInfos = applyCashInfos;
    }

    public static class ApplyCashInfosBean implements Serializable{
        /**
         * ID : 32
         * applyDate : 2017-08-04T10:51:11
         * applyUserID : 1266
         * applyBankID : 27
         * applyMoney : 100
         * applyState : 1
         * successTime : null
         * remark : null
         */

        private int ID;
        private String applyDate;
        private int applyUserID;
        private int applyBankID;
        private float applyMoney;
        private int applyState;
        private Object successTime;
        private String remark;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
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

        public Object getSuccessTime() {
            return successTime;
        }

        public void setSuccessTime(Object successTime) {
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
