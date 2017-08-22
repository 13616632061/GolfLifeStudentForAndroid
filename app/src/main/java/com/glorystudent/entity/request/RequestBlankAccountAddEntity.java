package com.glorystudent.entity.request;

/**
 * Created by billlamian on 17-8-2.
 * 添加银行卡请求
 */

public class RequestBlankAccountAddEntity {
//    private int Userid;//:int							用户ID
    private boolean Isdefault;//:bool						是否默认银行
    private int BankType;//						枚举值(哪个银行)
    private String AccountNum;//:string					帐号
    private String AccountName;//:string				开户人姓名
//    private String AcountAddress;//:string				开户行 //废弃


    public RequestBlankAccountAddEntity( boolean isdefault, int bankType, String acountNum, String acountName) {
//        Userid = userid;
        Isdefault = isdefault;
        BankType = bankType;
        AccountNum = acountNum;
        AccountName = acountName;
    }

//    public int getUserid() {
//        return Userid;
//    }
//
//    public void setUserid(int userid) {
//        Userid = userid;
//    }

    public boolean isdefault() {
        return Isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        Isdefault = isdefault;
    }

    public int getBankType() {
        return BankType;
    }

    public void setBankType(int bankType) {
        BankType = bankType;
    }

    public String getAcountNum() {
        return AccountNum;
    }

    public void setAcountNum(String acountNum) {
        AccountNum = acountNum;
    }

    public String getAcountName() {
        return AccountName;
    }

    public void setAcountName(String acountName) {
        AccountName = acountName;
    }
}
