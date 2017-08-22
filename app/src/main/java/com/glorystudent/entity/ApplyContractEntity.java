package com.glorystudent.entity;

/**
 * 合约
 * Created by hyj on 2017/1/6.
 */
public class ApplyContractEntity {
    private ApplyContractBean applycontract;
    private String username;
    public static class ApplyContractBean{
        private String usertel;
        private String packageid;

        public String getUsertel() {
            return usertel;
        }

        public void setUsertel(String usertel) {
            this.usertel = usertel;
        }

        public String getPackageid() {
            return packageid;
        }

        public void setPackageid(String packageid) {
            this.packageid = packageid;
        }
    }

    public ApplyContractBean getApplycontract() {
        return applycontract;
    }

    public void setApplycontract(ApplyContractBean applycontract) {
        this.applycontract = applycontract;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
