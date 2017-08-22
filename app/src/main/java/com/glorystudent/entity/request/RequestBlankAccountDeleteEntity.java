package com.glorystudent.entity.request;

/**
 * Created by billlamian on 17-8-2.
 * 删除已绑定银行卡请求
 */

public class RequestBlankAccountDeleteEntity {
    private int ID;//提现账户ID

    public RequestBlankAccountDeleteEntity(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
