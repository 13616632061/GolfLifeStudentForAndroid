package com.glorystudent.entity.request;

/**
 * Created by billlamian on 17-8-4.
 * 查询提现记录
 */

public class RequestApplyforRecordQueryEntity {
    private int ApplyState;

    public RequestApplyforRecordQueryEntity(int applyState) {
        ApplyState = applyState;
    }

    public int getApplyState() {

        return ApplyState;
    }

    public void setApplyState(int applyState) {
        ApplyState = applyState;
    }
}
