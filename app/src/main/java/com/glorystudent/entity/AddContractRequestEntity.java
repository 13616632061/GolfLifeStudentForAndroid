package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/21.
 */
public class AddContractRequestEntity {
    private ContractTraineeBean contractTrainee;

    public ContractTraineeBean getContractTrainee() {
        return contractTrainee;
    }

    public void setContractTrainee(ContractTraineeBean contractTrainee) {
        this.contractTrainee = contractTrainee;
    }

    public static class ContractTraineeBean{
        private String contracttraineename;
        private String phonenumber;
        private Integer contracttraineeid;
        private String customerphoto;
        private String remark;

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getContracttraineeid() {
            return contracttraineeid;
        }

        public void setContracttraineeid(Integer contracttraineeid) {
            this.contracttraineeid = contracttraineeid;
        }

        public String getContracttraineename() {
            return contracttraineename;
        }

        public void setContracttraineename(String contracttraineename) {
            this.contracttraineename = contracttraineename;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }
    }
}
