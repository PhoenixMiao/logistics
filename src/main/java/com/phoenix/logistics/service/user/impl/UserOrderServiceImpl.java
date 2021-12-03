package com.phoenix.logistics.service.user.impl;

import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.mapper.UserOrderMapper;
import com.phoenix.logistics.service.user.UserOrderService;
import com.phoenix.logistics.util.DatesUtil;
import com.phoenix.logistics.util.DisTranUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;


    @Override
    public void submitUserOrder(SubmitUserOrderRequest submitUserOrderRequest){
        Integer tranTime = DisTranUtil.tranTime(submitUserOrderRequest.getOriginLng(),submitUserOrderRequest.getOriginLat(),submitUserOrderRequest.getDestinationLng(),submitUserOrderRequest.getDestinationLat());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.HOUR, tranTime);
//        c.getTime();
        userOrderMapper.submitUserOrder(submitUserOrderRequest.getSenderUsername(),submitUserOrderRequest.getReceiverUsername(),0,submitUserOrderRequest.getOriginLocation(),submitUserOrderRequest.getDestinationLocation(),currentTime,tranTime);
    }
}
