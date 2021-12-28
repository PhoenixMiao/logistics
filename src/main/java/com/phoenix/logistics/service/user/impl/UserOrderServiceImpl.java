package com.phoenix.logistics.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.controller.response.BriefUserOrder;
import com.phoenix.logistics.controller.response.TmpAdminOrder;
import com.phoenix.logistics.controller.response.TmpUserOrder;
import com.phoenix.logistics.entity.*;
import com.phoenix.logistics.enums.GoodsType;
import com.phoenix.logistics.mapper.*;
import com.phoenix.logistics.service.user.UserOrderService;
import com.phoenix.logistics.util.DisTranUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AdminOrderMapper adminOrderMapper;

    @Override
    public HashMap<String,Long> submitUserOrder(String username,SubmitUserOrderRequest submitUserOrderRequest){
        Integer tranTime = DisTranUtil.tranTime(submitUserOrderRequest.getOriginLng(),submitUserOrderRequest.getOriginLat(),submitUserOrderRequest.getDestinationLng(),submitUserOrderRequest.getDestinationLat());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        GoodsType goodsType = GoodsType.valueOf(submitUserOrderRequest.getGoodsType());
        Goods goods = new Goods(submitUserOrderRequest.getGoodsName(),goodsType,submitUserOrderRequest.getGoodsVolume(),submitUserOrderRequest.getGoodsWeight(),submitUserOrderRequest.getGoodsValue());
        goodsMapper.addGoods(goods);
        UserOrder userOrder = new UserOrder(username,submitUserOrderRequest.getReceiverUsername(),goods.getId(),0,currentTime,submitUserOrderRequest.getOriginLocation(),submitUserOrderRequest.getDestinationLocation(),currentTime,tranTime,0);
        userOrderMapper.submitUserOrder(userOrder);
        AdminOrder adminOrder = new AdminOrder(userOrder.getId(),goods.getId(),0,currentTime,tranTime,0);
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
        userOrderMapper.readUserOrder(0,id);
        userOrderMapper.receiveGoods(currentTime,id);
        AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(userOrder.getAdminOrderId());
        if(adminOrder.getStatus()!=2) return 0;
        adminOrderMapper.changeStatus(3,currentTime,adminOrder.getId());
        adminOrderMapper.readAdminOrder(0, adminOrder.getId());
        return 1;
    }


    @Override
    public UserOrder getUserOrderById(Long id){
        return userOrderMapper.getUserOrderById(id);
    }


    private void updateTransportingUserOrderStatus(String username){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        List<UserOrder> userOrderList = userOrderMapper.getTransportingUserOrderByStatusAndUsername(1,username);
        for(UserOrder userOrder:userOrderList){
            AdminOrder adminOrder = adminOrderMapper.getAdminOrderById(userOrder.getAdminOrderId());
            if(adminOrder.getArriveTime().compareTo(currentTime)<0){
                userOrderMapper.changStatus(2,adminOrder.getArriveTime(),userOrder.getId());
                userOrderMapper.readUserOrder(0, userOrder.getId());
                if(adminOrderMapper.getAdminOrderById(userOrder.getAdminOrderId()).getStatus()==1){
                    adminOrderMapper.changeStatus(2,adminOrder.getArriveTime(),adminOrder.getId());
                    userOrderMapper.readUserOrder(0, adminOrder.getId());
                }
            }
        }

    }



    @Override
    public Page<TmpUserOrder> getBriefUserOrderListByCondition(int pageNum, int pageSize, String username, int sendOrReceive, int status) {
//        com.github.pagehelper.Page<TmpUserOrder> tmp = new com.github.pagehelper.Page<TmpUserOrder>();
        Page<TmpUserOrder> tmpUserOrderPage = new Page<>();
        PageHelper.startPage(pageNum, pageSize, "statusUpdateTime desc");
        if (sendOrReceive == 0) {
            if(status !=4){
                if(status!=0) updateTransportingUserOrderStatus(username);
                tmpUserOrderPage =  new Page<TmpUserOrder>(new PageInfo<>(userOrderMapper.getBriefSendUserOrderListByStatus(username,status)));
            }
            else{
                tmpUserOrderPage =  new Page<TmpUserOrder>(new PageInfo<>(userOrderMapper.getBriefSendUserOrderList(username)));
            }
        }
        if (sendOrReceive == 1) {
            if(status != 4) {
                if(status!=0) updateTransportingUserOrderStatus(username);
                tmpUserOrderPage =  new Page<TmpUserOrder>(new PageInfo<>(userOrderMapper.getBriefReceiveUserOrderListByStatus(username,status)));
            }
            else{
                tmpUserOrderPage =  new Page<TmpUserOrder>(new PageInfo<>(userOrderMapper.getBriefReceiveUserOrderList(username)));
            }
        }
        List<TmpUserOrder> tmpUserOrderList = tmpUserOrderPage.getItems();
        for(TmpUserOrder tmpUserOrder:tmpUserOrderList){
            Goods goods = goodsMapper.getGoodsById(tmpUserOrder.getGoodsId());
            tmpUserOrder.setGoodsName(goods.getName());
            tmpUserOrder.setGoodsType(goods.getType().getDescription());
        }
        tmpUserOrderPage.setItems(tmpUserOrderList);
        return tmpUserOrderPage;
    }


    @Override
    public List<BriefUserOrder> getUserMessageList(String username){
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getUserMessage(0,username);
        ArrayList<BriefUserOrder> briefUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder:tmpUserOrderList){
            Goods goods = goodsMapper.getGoodsById(tmpUserOrder.getGoodsId());
            if(tmpUserOrder.getSenderUsername().equals(username)){
                briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime(),goods.getName(),goods.getType().getDescription()));
            }else{
                briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getSenderUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime(),goods.getName(),goods.getType().getDescription()));
            }
        }
        return briefUserOrderArrayList;
    }

    @Override
    public Page<TmpUserOrder> searchUserOrder(int pageNum, int pageSize, Integer id,String username,int sendOrReceive){
        updateTransportingUserOrderStatus(username);
        if(id==null) return getBriefUserOrderListByCondition(pageNum,pageSize,username,sendOrReceive,4);
        Page<TmpUserOrder> briefUserOrderPage = new Page<>();
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        if(sendOrReceive==0)
            briefUserOrderPage = new Page<TmpUserOrder>(new PageInfo<TmpUserOrder>(userOrderMapper.searchSendUserOrder(username,id)));
        else
            briefUserOrderPage = new Page<TmpUserOrder>(new PageInfo<TmpUserOrder>(userOrderMapper.searchReceiveUserOrder(username,id)));
        List<TmpUserOrder> briefUserOrderArrayList = briefUserOrderPage.getItems();
        for(TmpUserOrder tmpUserOrder:briefUserOrderArrayList){
            UserOrder userOrder = userOrderMapper.getUserOrderById(tmpUserOrder.getId());
            Goods goods = goodsMapper.getGoodsById(userOrder.getGoodsId());
            tmpUserOrder.setGoodsName(goods.getName());
            tmpUserOrder.setGoodsType(goods.getType().getDescription());
            tmpUserOrder.setSenderUsername(userOrder.getSenderUsername());
            tmpUserOrder.setReceiverUsername(userOrder.getReceiverUsername());
        }
        return briefUserOrderPage;
    }
}
