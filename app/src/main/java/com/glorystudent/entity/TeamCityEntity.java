package com.glorystudent.entity;

import java.util.List;

/**
 * Created by Gavin.J on 2017/5/22.
 */

public class TeamCityEntity {

    private List<HotCityListBean> hotCityList;
    private List<CityListBean> cityList;

    public List<HotCityListBean> getHotCityList() {
        return hotCityList;
    }

    public void setHotCityList(List<HotCityListBean> hotCityList) {
        this.hotCityList = hotCityList;
    }

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class HotCityListBean {
        /**
         * cid : 110000
         * name : 北京市
         * pid : 110001
         */

        private int cid;
        private String name;
        private int pid;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }

    public static class CityListBean {
        /**
         * cid : 110000
         * name : 北京市
         * pid : 110001
         */

        private int cid;
        private String name;
        private int pid;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
