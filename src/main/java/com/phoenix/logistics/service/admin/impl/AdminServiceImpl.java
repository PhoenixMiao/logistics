package com.phoenix.logistics.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.service.admin.AdminService;
import com.phoenix.logistics.util.PasswordUtil;
import com.phoenix.logistics.util.RedisUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    /**
     * 用户登录统计命名规则
     * key格式：{项目名}:USERLOGIN:{日期}
     * value：当日用户登录数
     * 例如：2020-12-20当天有28个用户登录
     * key为：STELL:USERLOGIN:2020-12-20
     * value为：28
     */


    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public Page<Admin> getAdminAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList = adminMapper.getAdminAll();
        return new Page<>(new PageInfo<>(adminList));
    }


//    @Override
//    public void deleteAdmin(Integer id) {
//        if(adminMapper.deleteAdmin(id) < 1){
//            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
//        }
//    }
//
//    @Override
//    public Admin addAdmin(String user_name, String user_password) {
//        if(adminMapper.getAdminByUsername(user_name) != null){
//            throw new RRException(EnumExceptionType.ADMIN_ALREADY_EXISTS);
//        }
//
//        user_password = PasswordUtil.convert(user_password);
//        Admin admin = Admin.builder().userName(user_name).password(user_password).build();
//        adminMapper.insertAdmin(admin);
//        return adminMapper.getAdminByUsername(user_name);
//    }
}
