package com.phoenix.logistics.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.entity.Car;
import com.phoenix.logistics.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.service.admin.CarService;
import com.phoenix.logistics.util.RedisUtil;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {


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

    @Override
    public int deleteCar(Long id){
        Car car = carMapper.getCarById(id);
        if(car.getStatus()==1) return 1;
        carMapper.deleteCar(id);
        return 0;
    }

}
