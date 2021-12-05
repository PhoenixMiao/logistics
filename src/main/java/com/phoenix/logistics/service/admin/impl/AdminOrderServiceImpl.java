package com.phoenix.logistics.service.admin.impl;


import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.mapper.AdminOrderMapper;
import com.phoenix.logistics.mapper.CarMapper;
import com.phoenix.logistics.mapper.DriverMapper;
import com.phoenix.logistics.mapper.UserOrderMapper;
import com.phoenix.logistics.service.admin.AdminOrderService;
import com.phoenix.logistics.util.DisTranUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    AdminOrderMapper adminOrderMapper;

    @Autowired
    UserOrderMapper userOrderMapper;

    @Autowired
    CarMapper carMapper;

    @Autowired
    DriverMapper driverMapper;

    @Override
    public Long dealAdminOrder(Long id,Long carId,Long driverId,String adminUsername){
        //Integer tranTime = DisTranUtil.tranTime(submitUserOrderRequest.getOriginLng(),submitUserOrderRequest.getOriginLat(),submitUserOrderRequest.getDestinationLng(),submitUserOrderRequest.getDestinationLat());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        Integer transportTime = adminOrderMapper.getTransportTimeById(id);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, transportTime);
        Date arriveDate = c.getTime();
        String arriveTime = simpleDateFormat.format(arriveDate);
        adminOrderMapper.DealAdminOrder(carId,driverId,adminUsername,1,currentTime,arriveTime,currentTime,id);
        carMapper.allocateCar(1,carId);
        driverMapper.allocateDriver(1,driverId);
        AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(id);
        userOrderMapper.changStatus(1,currentTime,adminOrder.getUserOrderId());
        return adminOrder.getUserOrderId();
    }

    @Override
    public int goodsArrive(Long id){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(id);
        if(adminOrder.getStatus()==0) return 0;
        adminOrderMapper.changeStatus(2,currentTime,id);
        carMapper.allocateCar(0,adminOrder.getCarId());
        driverMapper.allocateDriver(0,adminOrder.getDriverId());
        userOrderMapper.changStatus(2,currentTime,adminOrder.getUserOrderId());
        return 1;
    }
}
