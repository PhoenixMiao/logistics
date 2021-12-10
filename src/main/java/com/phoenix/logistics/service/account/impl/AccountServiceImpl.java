package com.phoenix.logistics.service.account.impl;

import com.phoenix.logistics.controller.request.UpdateUserMessageRequest;
import com.phoenix.logistics.entity.User;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.mapper.AdminMapper;
import com.phoenix.logistics.mapper.UserMapper;
import com.phoenix.logistics.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.service.BaseService;
import com.phoenix.logistics.service.account.AccountService;


@Service
@Transactional
public class AccountServiceImpl extends BaseService implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void signUp(String username,String password) {

        User user = User.builder().username(username).build();

        //查看用户是否已存在
        if (userMapper.getUserByUsername(username) != null || adminMapper.getAdminByUsername(username) != null)
            throw new RRException(EnumExceptionType.USER_ALREADY_EXIST);

        user.setPassword(PasswordUtil.convert(password));

        //注册
        if (userMapper.insertUser(user.getUsername(),user.getPassword()) != 1) {
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }
    }

    @Override
    public int changePassword(String username,String password){
        String originalpassword = userMapper.getPasswordByUsername(username);
        if(originalpassword.equals(PasswordUtil.convert(password))){
            return -1;
        }
        userMapper.updatePasswordByUsername(PasswordUtil.convert(password),username);
        return 0;
    }


    @Override
    public void updateUserMessage(String username, UpdateUserMessageRequest updateUserMessageRequest){
        if(updateUserMessageRequest.getGender()!=null) userMapper.updateUserGender(updateUserMessageRequest.getGender(),username);
        if(updateUserMessageRequest.getResidence()!=null) userMapper.updateUserResidence(updateUserMessageRequest.getResidence(),username);
        if(updateUserMessageRequest.getTelephone()!=null) userMapper.updateUserTelephone(updateUserMessageRequest.getTelephone(),username);
    }

    @Override
    public boolean checkUsername(String username){
        User user = userMapper.getUserByUsername(username);
        if(user!=null){
            return false;
        }
        return true;
    }

    @Override
    public User getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    @Override
    public boolean checkPassword(String username,String password){
        if(userMapper.getPasswordByUsername(username).equals(password)) return false;
        return true;
    }
}
