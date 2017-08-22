package com.glorystudent.entity;

import java.util.List;

/**
 * Created by lucy on 2017/7/19.
 */
public class OptionModle {
    private String name;
    private List<ItemModle> list;
    private String field;
    private boolean flag=false;
    public OptionModle() {

    }
    public OptionModle(String name, List<ItemModle> list) {
        this.name = name;
        this.list = list;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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

    public List<ItemModle> getList() {
        return list;
    }

    public void setList(List<ItemModle> list) {
        this.list = list;
    }
}
