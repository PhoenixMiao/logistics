package com.phoenix.logistics.service.account.impl;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.entity.Invite;
import com.phoenix.logistics.entity.Tb_user;
import com.phoenix.logistics.entity.User_role;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.util.AliMessageUtil;
import com.phoenix.logistics.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.entity.Tb_company;
import com.phoenix.logistics.service.BaseService;
import com.phoenix.logistics.service.account.SignUpService;


@Service
@Transactional
public class SignUpServiceImpl extends BaseService implements SignUpService {

    @Autowired
    AliMessageUtil aliMessageUtil;

    @Override
    public String SendSMS(String username) throws ClientException {
        String verifyCode = aliMessageUtil.sendSignUpVerifyCode(username);
        redisUtil.set(username,verifyCode,60);
        return verifyCode;
    }

    @Override
    public void signUp(String username, String verifyCode,String password,int accountType,String inviter) {
        //验证
        if(!verifyCode.equals(redisUtil.get(username))){
            throw new RRException(EnumExceptionType.VERIFY_FAILED);
        }

        Tb_user user = Tb_user.builder().user_name(username).build();

        //查看用户是否已存在
        if(userMapper.selectOne(user) != null){

            //如果已经是个人账户，但申请注册企业账户，就提醒是否要升级
            if(accountType == 1){

                if(user_roleMapper.getUsernameByUsernameAndCompany(username,1) == null){
                    throw new RRException(EnumExceptionType.USER_ALREADY_EXIST_BUT_CAN_UPGRADE);
                }
            }
            throw new RRException(EnumExceptionType.USER_ALREADY_EXIST);
        }

        user.setPassword(PasswordUtil.convert(password));

        //查看邀请人是否存在
        if(inviter != null && inviter.length() > 0) {
            if (userMapper.selectOne(Tb_user.builder().user_name(inviter).build()) == null) {
                throw new RRException(EnumExceptionType.INVITER_NOT_EXIST);
            }
            Invite invite = Invite.builder().inviter(inviter).username(username).build();
            //加入邀请表
            if(inviteMapper.insert(invite) != 1){
                throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
            }
        }

        //注册
        if(userMapper.insert(user) != 1){
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }


        //如果是企业账号，提升权限
        if(accountType == 1) {
            User_role new_user_role = User_role.builder().username(username).company(1).build();

            if (user_roleMapper.insert(new_user_role) != 1) {
                throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
            }

            //插入公司
            Long user_id = userMapper.getUserByUsername(username).getId();
            Tb_company company = Tb_company.builder().user_id(user_id).build();

            //插入数据
            //如果有就什么都不做，没有就插入
            Long id = companyMapper.getIdByUser_id(company.getUser_id());
            if(id == null){
                companyMapper.insert(company);
            }
        }


    }
}
