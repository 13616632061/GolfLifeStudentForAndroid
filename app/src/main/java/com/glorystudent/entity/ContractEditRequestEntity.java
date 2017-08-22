package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/21.
 */
public class ContractEditRequestEntity {
    private ContractBean contract;
    private Integer contractTraineeID;
    private Integer packageID;
    private Integer contractUserID;

    public Integer getContractUserID() {
        return contractUserID;
    }

    public void setContractUserID(Integer contractUserID) {
        this.contractUserID = contractUserID;
    }

    public Integer getContractTraineeID() {
        return contractTraineeID;
    }

    public void setContractTraineeID(Integer contractTraineeID) {
        this.contractTraineeID = contractTraineeID;
    }

    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }
    public static class ContractBean{
        private Integer contractid;
        private Integer userid;
        private Integer groupid;
        private Integer coachid;
        private String contractdate;
        private String effectivedates;
        private String expirydate;
        private String contractprice;
        private String realprice;
        private String freeprice;
        private String freetimeprice;
        private String effective;
        private Integer status;
        private String coachname;
        private String coachpicture;
        private String username;
        private String customerphoto;




        public Integer getContractid() {
            return contractid;
        }

        public void setContractid(Integer contractid) {
            this.contractid = contractid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getGroupid() {
            return groupid;
        }

        public void setGroupid(Integer groupid) {
            this.groupid = groupid;
        }

        public Integer getCoachid() {
            return coachid;
        }

        public void setCoachid(Integer coachid) {
            this.coachid = coachid;
        }

        public String getContractdate() {
            return contractdate;
        }

        public void setContractdate(String contractdate) {
            this.contractdate = contractdate;
        }

        public String getEffectivedates() {
            return effectivedates;
        }

        public void setEffectivedates(String effectivedates) {
            this.effectivedates = effectivedates;
        }

        public String getExpirydate() {
            return expirydate;
        }

        public void setExpirydate(String expirydate) {
            this.expirydate = expirydate;
        }

        public String getContractprice() {
            return contractprice;
        }

        public void setContractprice(String contractprice) {
            this.contractprice = contractprice;
        }

        public String getRealprice() {
            return realprice;
        }

        public void setRealprice(String realprice) {
            this.realprice = realprice;
        }

        public String getFreeprice() {
            return freeprice;
        }

        public void setFreeprice(String freeprice) {
            this.freeprice = freeprice;
        }

        public String getFreetimeprice() {
            return freetimeprice;
        }

        public void setFreetimeprice(String freetimeprice) {
            this.freetimeprice = freetimeprice;
        }

        public String getEffective() {
            return effective;
        }

        public void setEffective(String effective) {
            this.effective = effective;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCoachname() {
            return coachname;
        }

        public void setCoachname(String coachname) {
            this.coachname = coachname;
        }

        public String getCoachpicture() {
            return coachpicture;
        }

        public void setCoachpicture(String coachpicture) {
            this.coachpicture = coachpicture;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCustomerphoto() {
            return customerphoto;
        }

        public void setCustomerphoto(String customerphoto) {
            this.customerphoto = customerphoto;
        }
    }

    public ContractBean getContract() {
        return contract;
    }

    public void setContract(ContractBean contract) {
        this.contract = contract;
    }
}
