package com.phoenix.logistics.service.account;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.controller.request.UpdateUserMessageRequest;
import com.phoenix.logistics.entity.User;

public interface AccountService {

    //注册
    void signUp(String username,String password);

    int changePassword(String username,String password);

    boolean checkPassword(String username,String password);

    void updateUserMessage(String username, UpdateUserMessageRequest updateUserMessageRequest);

    boolean checkUsername(String username);

    User getUser(String username);
}
