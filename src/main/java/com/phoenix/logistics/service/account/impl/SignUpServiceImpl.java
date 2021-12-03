package com.phoenix.logistics.service.account.impl;

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
import com.phoenix.logistics.service.account.SignUpService;


@Service
@Transactional
public class SignUpServiceImpl extends BaseService implements SignUpService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void signUp(String userName,String password) {

        User user = User.builder().userName(userName).build();

        //查看用户是否已存在
        if (userMapper.getUserByUsername(userName) != null || adminMapper.getAdminByUsername(userName) != null)
            throw new RRException(EnumExceptionType.USER_ALREADY_EXIST);

        user.setPassword(PasswordUtil.convert(password));

        //注册
        if (userMapper.insert(user) != 1) {
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }
    }
}
