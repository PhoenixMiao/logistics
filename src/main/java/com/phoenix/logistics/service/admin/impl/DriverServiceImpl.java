package com.phoenix.logistics.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Car;
import com.phoenix.logistics.entity.Driver;
import com.phoenix.logistics.service.admin.DriverService;
import com.phoenix.logistics.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.mapper.DriverMapper;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverMapper DriverMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public Page<Driver> getAllDrivers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Driver> DriverList = DriverMapper.getAllDrivers();
        return new Page<>(new PageInfo<>(DriverList));
    }

    @Override
    public void addDriver(Integer num){
        for(int i = 0;i<num;i++){
            DriverMapper.insertDriver(0);
        }
    }

    @Override
    public int deleteDriver(Long id){
        Driver Driver = DriverMapper.getDriverById(id);
        if(Driver.getStatus()==1) return 1;
        DriverMapper.deleteDriver(id);
        return 0;
    }

    @Override
    public Page<Driver> getAllFreeDrivers(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Driver> driverList = DriverMapper.getAllFreeDrivers(0);
        return new Page<>(new PageInfo<>(driverList));
    }

    @Override
    public List<Driver> getAllFreeDrivers(){
        return DriverMapper.getAllFreeDrivers(0);
    }
}
