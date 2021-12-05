package com.phoenix.logistics.service.user.impl;

import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.entity.Goods;
import com.phoenix.logistics.entity.UserOrder;
import com.phoenix.logistics.enums.GoodsType;
import com.phoenix.logistics.mapper.*;
import com.phoenix.logistics.service.user.UserOrderService;
import com.phoenix.logistics.util.DatesUtil;
import com.phoenix.logistics.util.DisTranUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AdminOrderMapper adminOrderMapper;

    @Override
    public HashMap<String,Long> submitUserOrder(SubmitUserOrderRequest submitUserOrderRequest){
        Integer tranTime = DisTranUtil.tranTime(submitUserOrderRequest.getOriginLng(),submitUserOrderRequest.getOriginLat(),submitUserOrderRequest.getDestinationLng(),submitUserOrderRequest.getDestinationLat());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        GoodsType goodsType = GoodsType.valueOf(submitUserOrderRequest.getGoodsType());
        Goods goods = new Goods(submitUserOrderRequest.getGoodsName(),goodsType,submitUserOrderRequest.getGoodsVolume(),submitUserOrderRequest.getGoodsWeight(),submitUserOrderRequest.getGoodsValue());
        goodsMapper.addGoods(goods);
        UserOrder userOrder = new UserOrder(submitUserOrderRequest.getSenderUsername(),submitUserOrderRequest.getReceiverUsername(),goods.getId(),0,currentTime,submitUserOrderRequest.getOriginLocation(),submitUserOrderRequest.getDestinationLocation(),currentTime,tranTime);
        userOrderMapper.submitUserOrder(userOrder);
        AdminOrder adminOrder = new AdminOrder(userOrder.getId(),goods.getId(),0,currentTime,tranTime);
        adminOrderMapper.newAdminOrder(adminOrder);
        userOrderMapper.beDealed(adminOrder.getId(),userOrder.getId());
        HashMap<String,Long> res = new HashMap<>();
        res.put("goods_id",goods.getId());
        res.put("user_order_id",userOrder.getId());
        res.put("admin_order_id",adminOrder.getId());
        return res;
    }

    @Override
    public int receiveGoods(Long id){
        UserOrder userOrder = userOrderMapper.getUserOrderById(id);
        if(userOrder.getStatus()!=2) return 0;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        userOrderMapper.changStatus(3,currentTime,id);
        AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(userOrder.getAdminOrderId());
        if(adminOrder.getStatus()!=2) return 0;
        adminOrderMapper.changeStatus(3,currentTime,adminOrder.getId());
        return 1;
    }

}
