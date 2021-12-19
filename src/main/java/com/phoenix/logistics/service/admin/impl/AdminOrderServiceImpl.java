package com.phoenix.logistics.service.admin.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.response.BriefAdminOrder;
import com.phoenix.logistics.controller.response.OrderDetailResponse;
import com.phoenix.logistics.controller.response.TmpAdminOrder;
import com.phoenix.logistics.controller.response.TmpUserOrder;
import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.entity.Goods;
import com.phoenix.logistics.entity.UserOrder;
import com.phoenix.logistics.mapper.*;
import com.phoenix.logistics.service.admin.AdminOrderService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void dealAdminOrder(Long id,Long carId,Long driverId,String adminUsername){
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
        adminOrderMapper.readAdminOrder(0,id);
        carMapper.allocateCar(1,carId);
        driverMapper.allocateDriver(1,driverId);
        AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(id);
        userOrderMapper.changStatus(adminOrder.getStatus(),adminOrder.getStatusUpdateTime(),adminOrder.getUserOrderId());
        userOrderMapper.readUserOrder(0,adminOrder.getUserOrderId());
    }


    @Override
    public AdminOrder getAdminOrderById(Long id){
        return adminOrderMapper.getAdminOrderById(id);
    }

    @Override
    public OrderDetailResponse getOrderDetailResponse(Long adminOrderId){
        AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(adminOrderId);
        UserOrder userOrder = userOrderMapper.getUserOrderById(adminOrder.getUserOrderId());
        Goods goods = goodsMapper.getGoodsById(userOrder.getGoodsId());
        adminOrderMapper.readAdminOrder(1,adminOrder.getId());
        userOrderMapper.readUserOrder(1,userOrder.getId());
        return new OrderDetailResponse(goods.getId(),goods.getName(),goods.getType(),goods.getVolume(),goods.getWeight(),goods.getValue(),userOrder.getSenderUsername(),userOrder.getReceiverUsername(),userOrder.getStatus(),userOrder.getStatusUpdateTime(),userOrder.getOriginLocation(),userOrder.getDestinationLocation(),userOrder.getCommitTime(),userOrder.getReceiveTime(),adminOrder.getCarId(),adminOrder.getDriverId(),adminOrder.getAdminUsername(),adminOrder.getTransportTime(),adminOrder.getSendTime(),adminOrder.getArriveTime());
    }

    private void updateTransportingAdminOrderStatus(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        List<AdminOrder> adminOrderList = adminOrderMapper.getAdminOrderByStatus(1);
        for(AdminOrder adminOrder:adminOrderList){
            if(adminOrder.getArriveTime().compareTo(currentTime)<0){
                adminOrderMapper.changeStatus(2,adminOrder.getArriveTime(),adminOrder.getId());
                adminOrderMapper.readAdminOrder(0,adminOrder.getId());
                carMapper.allocateCar(0,adminOrder.getCarId());
                driverMapper.allocateDriver(0,adminOrder.getDriverId());
                if(userOrderMapper.getUserOrderById(adminOrder.getUserOrderId()).getStatus()==1)
                    userOrderMapper.changStatus(2,adminOrder.getArriveTime(),adminOrder.getUserOrderId());
                    userOrderMapper.readUserOrder(0,adminOrder.getUserOrderId());
            }
        }
    }

    @Override
    public Page<TmpAdminOrder> getBriefAdminOrderListByStatus(int pageNum, int pageSize,int status){
        if(status==0)PageHelper.startPage(pageNum, pageSize,"statusUpdateTime asc");
        else PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        if(status==2||status==3) updateTransportingAdminOrderStatus();
        Page<TmpAdminOrder> briefAdminOrderPage = new Page<>();
        if(status!=4)  briefAdminOrderPage = new Page<TmpAdminOrder>(new PageInfo<TmpAdminOrder>(adminOrderMapper.getBriefAdminOrderListByStatus(status)));
        else briefAdminOrderPage = new Page<TmpAdminOrder>(new PageInfo<TmpAdminOrder>(adminOrderMapper.getBriefAdminOrderList()));
        List<TmpAdminOrder> briefAdminOrderArrayList = briefAdminOrderPage.getItems();
        for(TmpAdminOrder adminOrder:briefAdminOrderArrayList){
            UserOrder userOrder = userOrderMapper.getUserOrderById(adminOrder.getUserOrderId());
            Goods goods = goodsMapper.getGoodsById(adminOrder.getGoodsId());
            adminOrder.setGoodsName(goods.getName());
            adminOrder.setGoodsType(goods.getType().getDescription());
            adminOrder.setSenderUsername(userOrder.getSenderUsername());
            adminOrder.setReceiverUsername(userOrder.getReceiverUsername());
        }
        briefAdminOrderPage.setItems(briefAdminOrderArrayList);
        return briefAdminOrderPage;
    }


    @Override
    public List<BriefAdminOrder> getAdminMessageList(){
        List<TmpAdminOrder> tmpAdminOrderList = adminOrderMapper.getAdminMessage(0);
        ArrayList<BriefAdminOrder> briefAdminOrderArrayList = new ArrayList<>();
        for(TmpAdminOrder tmpAdminOrder:tmpAdminOrderList){
            UserOrder userOrder = userOrderMapper.getUserOrderById(tmpAdminOrder.getUserOrderId());
            Goods goods = goodsMapper.getGoodsById(tmpAdminOrder.getGoodsId());
            briefAdminOrderArrayList.add(new BriefAdminOrder(tmpAdminOrder.getId(),userOrder.getSenderUsername(),userOrder.getReceiverUsername(),tmpAdminOrder.getStatus(),tmpAdminOrder.getStatusUpdateTime(),goods.getName(),goods.getType().getDescription()));
        }
        return briefAdminOrderArrayList;
    }

    @Override
    public Page<TmpAdminOrder> searchAdminOrder(int pageNum, int pageSize,Integer id){
        updateTransportingAdminOrderStatus();
        if(id==null) return getBriefAdminOrderListByStatus(pageNum,pageSize,4);
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        Page<TmpAdminOrder> briefAdminOrderPage = new Page<TmpAdminOrder>(new PageInfo<TmpAdminOrder>(adminOrderMapper.searchAdminOrder(id)));
        List<TmpAdminOrder> briefAdminOrderArrayList = briefAdminOrderPage.getItems();
        for(TmpAdminOrder adminOrder:briefAdminOrderArrayList){
            UserOrder userOrder = userOrderMapper.getUserOrderById(adminOrder.getUserOrderId());
            Goods goods = goodsMapper.getGoodsById(adminOrder.getGoodsId());
            adminOrder.setGoodsName(goods.getName());
            adminOrder.setGoodsType(goods.getType().getDescription());
            adminOrder.setSenderUsername(userOrder.getSenderUsername());
            adminOrder.setReceiverUsername(userOrder.getReceiverUsername());
        }
        return briefAdminOrderPage;
    }
}
