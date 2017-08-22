package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/21.
 */
public class QueryContractRequestEntity {
    private Integer page;
    private ContractTraineeBean contractTrainee;
    public static class ContractTraineeBean{
        private Integer contracttraineeid;
        private Integer contracttraineeuserid;
        private String contracttraineetype;
        private String contracttraineestatus;

        public Integer getContracttraineeid() {
            return contracttraineeid;
        }

        public void setContracttraineeid(Integer contracttraineeid) {
            this.contracttraineeid = contracttraineeid;
        }

        public Integer getContracttraineeuserid() {
            return contracttraineeuserid;
        }

        public void setContracttraineeuserid(Integer contracttraineeuserid) {
            this.contracttraineeuserid = contracttraineeuserid;
        }

        public String getContracttraineetype() {
            return contracttraineetype;
        }

        public void setContracttraineetype(String contracttraineetype) {
            this.contracttraineetype = contracttraineetype;
        }

        public String getContracttraineestatus() {
            return contracttraineestatus;
        }

        public void setContracttraineestatus(String contracttraineestatus) {
            this.contracttraineestatus = contracttraineestatus;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ContractTraineeBean getContractTrainee() {
        return contractTrainee;
    }

    public void setContractTrainee(ContractTraineeBean contractTrainee) {
        this.contractTrainee = contractTrainee;
    }
}
