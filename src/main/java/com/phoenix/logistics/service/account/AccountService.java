package com.phoenix.logistics.service.account;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.controller.request.UpdateUserMessageRequest;

public interface AccountService {

    //注册
    void signUp(String username,String password);

    int changePassword(String username,String password);

    void updateUserMessage(String username, UpdateUserMessageRequest updateUserMessageRequest);
}
