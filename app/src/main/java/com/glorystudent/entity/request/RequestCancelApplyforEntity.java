package com.glorystudent.entity.request;

/**
 * Created by billlamian on 17-8-4.
 * 取消提现申请
 */

public class RequestCancelApplyforEntity {
    private int Id;//:int						申请ID
    private int ApplyState;//:int					取消状态值(4)

    public RequestCancelApplyforEntity(int id, int applyState) {
        Id = id;
        ApplyState = applyState;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getApplyState() {
        return ApplyState;
    }

    public void setApplyState(int applyState) {
        ApplyState = applyState;
    }
}
