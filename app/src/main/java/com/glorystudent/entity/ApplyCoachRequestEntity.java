package com.glorystudent.entity;

/**
 * Created by hyj on 2017/4/28.
 */
public class ApplyCoachRequestEntity {
    private ApplyCoachGroupBean applyCoachGroup;

    public ApplyCoachGroupBean getApplyCoachGroup() {
        return applyCoachGroup;
    }

    public void setApplyCoachGroup(ApplyCoachGroupBean applyCoachGroup) {
        this.applyCoachGroup = applyCoachGroup;
    }

    public static class ApplyCoachGroupBean{
        private String profile;
        private String coachage;
        private String coachvostro;
        private String coachgroupid;

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getCoachage() {
            return coachage;
        }

        public void setCoachage(String coachage) {
            this.coachage = coachage;
        }

        public String getCoachvostro() {
            return coachvostro;
        }

        public void setCoachvostro(String coachvostro) {
            this.coachvostro = coachvostro;
        }

        public String getCoachgroupid() {
            return coachgroupid;
        }

        public void setCoachgroupid(String coachgroupid) {
            this.coachgroupid = coachgroupid;
        }
    }
}
