package com.glorystudent.entity.request;

/**
 * Created by billlamian on 17-8-4.
 * 查询提现记录详情
 */

public class RequestApplyforDetailQueryEntity {
    private int ID;

    public RequestApplyforDetailQueryEntity(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
