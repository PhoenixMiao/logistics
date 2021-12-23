package com.phoenix.logistics.service.account.impl;

import com.phoenix.logistics.controller.request.UpdateUserMessageRequest;
import com.phoenix.logistics.entity.Admin;
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
    public void changePassword(String username,String password){
        String originalPassword = userMapper.getPasswordByUsername(username);
        if(originalPassword.equals(PasswordUtil.convert(password))){
            throw new RRException(EnumExceptionType.PASSWORD_SAME);
        }
        userMapper.updatePasswordByUsername(PasswordUtil.convert(password),username);
    }


    @Override
    public void updateUserMessage(String username, UpdateUserMessageRequest updateUserMessageRequest){
        if(updateUserMessageRequest.getGender()!=null) userMapper.updateUserGender(updateUserMessageRequest.getGender(),username);
        if(updateUserMessageRequest.getResidence()!=null) userMapper.updateUserResidence(updateUserMessageRequest.getResidence(),username);
        if(updateUserMessageRequest.getTelephone()!=null) userMapper.updateUserTelephone(updateUserMessageRequest.getTelephone(),username);
    }

    @Override
    public void checkUsername(String username){
        User user = userMapper.getUserByUsername(username);
        if(user!=null){
            throw new RRException(EnumExceptionType.PASSWORD_SAME);
        }
    }

    @Override
    public User getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    @Override
    public void checkPassword(String username,String password){
        if(userMapper.getPasswordByUsername(username).equals(password)){
            throw new RRException(EnumExceptionType.USER_ALREADY_EXIST);
        }
    }

    @Override
    public Admin getAdmin(String username){
        return userMapper.getAdminByUsername(username);
    }
}
