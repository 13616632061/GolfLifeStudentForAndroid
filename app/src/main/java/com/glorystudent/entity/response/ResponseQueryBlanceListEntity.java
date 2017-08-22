package com.glorystudent.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by billlamian on 17-8-3.
 * 查询绑定提现账户列表请求
 */

public class ResponseQueryBlanceListEntity extends BaseResponseEntity {

    /**
     * userBank : null
     * userBanks : [{"ID":24,"userid":1266,"isDefault":true,"bankType":1,"accountNum":"1113","accountName":"测试账号1113","AccountAddress":null,"createTime":"2017-08-03T14:25:25"},{"ID":23,"userid":1266,"isDefault":false,"bankType":1,"accountNum":"1111","accountName":"1111","AccountAddress":null,"createTime":"2017-08-03T14:20:22"}]
     */

    private Object userBank;
    private List<UserBanksBean> userBanks;

    public Object getUserBank() {
        return userBank;
    }

    public void setUserBank(Object userBank) {
        this.userBank = userBank;
    }

    public List<UserBanksBean> getUserBanks() {
        return userBanks;
    }

    public void setUserBanks(List<UserBanksBean> userBanks) {
        this.userBanks = userBanks;
    }

    public static class UserBanksBean implements Serializable{
        /**
         * ID : 24
         * userid : 1266
         * isDefault : true
         * bankType : 1
         * accountNum : 1113
         * accountName : 测试账号1113
         * AccountAddress : null
         * createTime : 2017-08-03T14:25:25
         */

        private int ID;
        private int userid;
        private boolean isDefault;
        private int bankType;
        private String accountNum;
        private String accountName;
        private Object AccountAddress;
        private String createTime;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public boolean isIsDefault() {
            return isDefault;
        }

        public void setIsDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        public int getBankType() {
            return bankType;
        }

        public void setBankType(int bankType) {
            this.bankType = bankType;
        }

        public String getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(String accountNum) {
            this.accountNum = accountNum;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Object getAccountAddress() {
            return AccountAddress;
        }

        public void setAccountAddress(Object AccountAddress) {
            this.AccountAddress = AccountAddress;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
