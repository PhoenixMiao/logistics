package com.phoenix.logistics.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.entity.Car;
import com.phoenix.logistics.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.service.admin.AdminService;
import com.phoenix.logistics.util.RedisUtil;

import java.util.List;

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
    CarMapper carMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public Page<Car> getAllCars(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Car> carList = carMapper.getAllCars();
        return new Page<>(new PageInfo<>(carList));
    }

    @Override
    public void addCar(Integer num){
        for(int i = 0;i<num;i++){
            carMapper.insertCar(0);
        }
    }

}
