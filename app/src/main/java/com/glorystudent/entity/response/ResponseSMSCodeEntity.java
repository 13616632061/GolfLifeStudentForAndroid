package com.glorystudent.entity.response;

/**
 * Created by billlamian on 17-8-2.
 * 获取验证码响应信息
 */

public class ResponseSMSCodeEntity extends BaseResponseEntity {
    private String phonecodekey;

    public String getPhonecodekey() {
        return phonecodekey;
    }

    public void setPhonecodekey(String phonecodekey) {
        this.phonecodekey = phonecodekey;
    }
}
