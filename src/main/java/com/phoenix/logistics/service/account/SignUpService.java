package com.phoenix.logistics.service.account;

import com.aliyuncs.exceptions.ClientException;

public interface SignUpService {

    //发送验证码邮件
    public String SendSMS(String username) throws ClientException;

    //注册
    public void signUp(String username, String verifyCode,String password,int accountType,String inviter);

}
