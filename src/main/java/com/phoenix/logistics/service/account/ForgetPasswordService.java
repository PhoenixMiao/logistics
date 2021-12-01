package com.phoenix.logistics.service.account;

public interface ForgetPasswordService {

    //忘记密码，发验证码短信
    void forgetPassword(String username);

    //修改密码
    void revisePassword(String username,String verifyCode,String password);

}
