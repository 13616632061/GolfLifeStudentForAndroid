package com.glorystudent.entity;

/**
 * Created by hyj on 2017/1/10.
 */
public class IndustryEventRequestEntity {
    private IndustryEventsBean industryevents;
    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public static class IndustryEventsBean {
        private Integer industryevents_id;
        private String industryevents_tittle;
        private String industryevents_organizer;
        private String industryevents_context;
        private String industryevents_Begindate;
        private String industryevents_Enddate;

        public Integer getIndustryevents_id() {
            return industryevents_id;
        }

        public void setIndustryevents_id(Integer industryevents_id) {
            this.industryevents_id = industryevents_id;
        }

        public String getIndustryevents_tittle() {
            return industryevents_tittle;
        }

        public void setIndustryevents_tittle(String industryevents_tittle) {
            this.industryevents_tittle = industryevents_tittle;
        }

        public String getIndustryevents_organizer() {
            return industryevents_organizer;
        }

        public void setIndustryevents_organizer(String industryevents_organizer) {
            this.industryevents_organizer = industryevents_organizer;
        }

        public String getIndustryevents_context() {
            return industryevents_context;
        }

        public void setIndustryevents_context(String industryevents_context) {
            this.industryevents_context = industryevents_context;
        }

        public String getIndustryevents_Begindate() {
            return industryevents_Begindate;
        }

        public void setIndustryevents_Begindate(String industryevents_Begindate) {
            this.industryevents_Begindate = industryevents_Begindate;
        }

        public String getIndustryevents_Enddate() {
            return industryevents_Enddate;
        }

        public void setIndustryevents_Enddate(String industryevents_Enddate) {
            this.industryevents_Enddate = industryevents_Enddate;
        }
    }

    public IndustryEventsBean getIndustryevents() {
        return industryevents;
    }

    public void setIndustryevents(IndustryEventsBean industryevents) {
        this.industryevents = industryevents;
    }
}
