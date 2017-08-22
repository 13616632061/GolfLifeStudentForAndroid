package com.glorystudent.entity;

/**
 * 测试数据
 * Created by hyj on 2017/1/5.
 */
public class TestScoreEntity {
    private int position;
    private int par;
    private int fairway;
    private int bunkers;
    private int upgreen;
    private int followball;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getFairway() {
        return fairway;
    }

    public void setFairway(int fairway) {
        this.fairway = fairway;
    }

    public int getBunkers() {
        return bunkers;
    }

    public void setBunkers(int bunkers) {
        this.bunkers = bunkers;
    }

    public int getUpgreen() {
        return upgreen;
    }

    public void setUpgreen(int upgreen) {
        this.upgreen = upgreen;
    }

    public int getFollowball() {
        return followball;
    }

    public void setFollowball(int followball) {
        this.followball = followball;
    }

    @Override
    public String toString() {
        return "TestScoreEntity{" +
                "position=" + position +
                ", par=" + par +
                ", fairway=" + fairway +
                ", bunkers=" + bunkers +
                ", upgreen=" + upgreen +
                ", followball=" + followball +
                '}';
    }
}
