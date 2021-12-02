package com.phoenix.logistics.service.account;

import com.aliyuncs.exceptions.ClientException;

public interface SignUpService {

    //注册
    public void signUp(String username,String password);

}
