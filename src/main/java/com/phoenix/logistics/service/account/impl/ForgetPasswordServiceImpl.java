package com.phoenix.logistics.service.account.impl;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.entity.Tb_user;
import com.phoenix.logistics.util.AliMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.service.BaseService;
import com.phoenix.logistics.service.account.ForgetPasswordService;
import com.phoenix.logistics.util.PasswordUtil;

@Service
public class ForgetPasswordServiceImpl extends BaseService implements ForgetPasswordService {

    @Autowired
    AliMessageUtil aliMessageUtil;

    @Override
    public void forgetPassword(String username) {

        //查询用户是否存在
        if(userMapper.selectOne(Tb_user.builder().user_name(username).build()) == null){
            throw new RRException(EnumExceptionType.USER_NOT_EXIST);
        }

        String verifyCode = "";
        //发送短信
        try {
            verifyCode = aliMessageUtil.sendForgetPasswordVerifyCode(username);
        } catch (ClientException e) {
            throw new RRException(EnumExceptionType.ALIYUN_SMS_FAILURE);
        }

        redisUtil.set(username,verifyCode,60);
    }

    @Override
    public void revisePassword(String username, String verifyCode,String password) {
        String correct_verifyCode = redisUtil.get(username)+"";

        //核实验证码
        if(!(verifyCode+"").equals(correct_verifyCode)){
            throw new RRException(EnumExceptionType.VERIFY_FAILED);
        }

        //修改密码
        if(userMapper.updatePasswordByUsername(PasswordUtil.convert(password),username) != 1){
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }
    }
}
