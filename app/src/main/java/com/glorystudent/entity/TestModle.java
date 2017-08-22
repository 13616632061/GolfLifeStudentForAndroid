package com.glorystudent.entity;

import java.util.List;

/**
 * Created by lucy on 2017/7/19.
 */
public class TestModle {
    private String name;
    private List<OptionModle> list;
    private boolean flag=false;
    //是否里面问题回答完毕
    private boolean ROTATE=true;

    public TestModle() {

    }
    public TestModle(String name, List<OptionModle> list) {
        this.name = name;
        this.list = list;
    }

    public boolean isROTATE() {
        return ROTATE;
    }

    public void setROTATE(boolean ROTATE) {
        this.ROTATE = ROTATE;
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

    public List<OptionModle> getList() {
        return list;
    }

    public void setList(List<OptionModle> list) {
        this.list = list;
    }
}
