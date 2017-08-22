package com.glorystudent.entity;

/**
 * Created by lucy on 2017/7/19.
 */
public class ItemModle {

    private String name;

    private boolean flag=false;

    public ItemModle() {
    }
    public ItemModle(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
