package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/23.
 */
public class DateChangeRequestEntity {
    private ApplyContractDateChangeBean applyContractDateChange;

    public ApplyContractDateChangeBean getApplyContractDateChange() {
        return applyContractDateChange;
    }

    public void setApplyContractDateChange(ApplyContractDateChangeBean applyContractDateChange) {
        this.applyContractDateChange = applyContractDateChange;
    }

    public static class ApplyContractDateChangeBean{
        private Integer coachgroupid;
        private Integer coachid;
        private String coachname;
        private Integer contractid;
        private String contractname;
        private String delaytime;
        private String createtime;
        private String applycreatetime;
        private Integer reviewerid;
        private String remark;
        private String refuseremark;
        private String applystatus;

        public Integer getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(Integer coachgroupid) {
            this.coachgroupid = coachgroupid;
        }

        public Integer getCoachid() {
            return coachid;
        }

        public void setCoachid(Integer coachid) {
            this.coachid = coachid;
        }

        public String getCoachname() {
            return coachname;
        }

        public void setCoachname(String coachname) {
            this.coachname = coachname;
        }

        public Integer getContractid() {
            return contractid;
        }

        public void setContractid(Integer contractid) {
            this.contractid = contractid;
        }

        public String getContractname() {
            return contractname;
        }

        public void setContractname(String contractname) {
            this.contractname = contractname;
        }

        public String getDelaytime() {
            return delaytime;
        }

        public void setDelaytime(String delaytime) {
            this.delaytime = delaytime;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getApplycreatetime() {
            return applycreatetime;
        }

        public void setApplycreatetime(String applycreatetime) {
            this.applycreatetime = applycreatetime;
        }

        public Integer getReviewerid() {
            return reviewerid;
        }

        public void setReviewerid(Integer reviewerid) {
            this.reviewerid = reviewerid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRefuseremark() {
            return refuseremark;
        }

        public void setRefuseremark(String refuseremark) {
            this.refuseremark = refuseremark;
        }

        public String getApplystatus() {
            return applystatus;
        }

        public void setApplystatus(String applystatus) {
            this.applystatus = applystatus;
        }
    }
}
