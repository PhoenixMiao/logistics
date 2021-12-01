package com.phoenix.logistics.service.account.impl;

import com.phoenix.logistics.entity.User_role;
import com.phoenix.logistics.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.entity.Tb_company;
import com.phoenix.logistics.mapper.UserMapper;
import com.phoenix.logistics.service.BaseService;
import com.phoenix.logistics.service.account.UpgradeAuthService;

@Service
@Transactional
public class UpgradeAuthServiceImpl extends BaseService implements UpgradeAuthService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void upgradeAuth(String username) {

        User_role new_user_role = User_role.builder().username(username).company(1).build();

        User_role user_role = user_roleMapper.selectOne(User_role.builder().username(username).build());
        if(user_role == null){
            if(user_roleMapper.insertUser_role(new_user_role)!= 1){
                throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
            }
        }else{
            if(user_role.getCompany() == 1){
                throw new RRException(EnumExceptionType.AUTH_ALREADY_UPGRADE);
            }

            if(user_roleMapper.updateCompanyByUsername(1,username) != 1){
                throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
            }

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
