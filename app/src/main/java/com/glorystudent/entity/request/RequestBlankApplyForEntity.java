package com.glorystudent.entity.request;

/**
 * Created by billlamian on 17-8-3.
 * 申请提现申请
 */

public class RequestBlankApplyForEntity {
    private int ApplyBankID;//提现账户ID
    private float ApplyMoney;//提现金额

    public RequestBlankApplyForEntity(int applyBankID, float applyMoney) {
        ApplyBankID = applyBankID;
        ApplyMoney = applyMoney;
    }

    public int getApplyBankID() {
        return ApplyBankID;
    }

    public void setApplyBankID(int applyBankID) {
        ApplyBankID = applyBankID;
    }

    public float getApplyMoney() {
        return ApplyMoney;
    }

    public void setApplyMoney(float applyMoney) {
        ApplyMoney = applyMoney;
    }
}
