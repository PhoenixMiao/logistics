package com.phoenix.logistics.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.controller.response.BriefAdminOrder;
import com.phoenix.logistics.controller.response.BriefUserOrder;
import com.phoenix.logistics.dto.TmpUserOrder;
import com.phoenix.logistics.entity.*;
import com.phoenix.logistics.enums.GoodsType;
import com.phoenix.logistics.mapper.*;
import com.phoenix.logistics.service.user.UserOrderService;
import com.phoenix.logistics.util.DatesUtil;
import com.phoenix.logistics.util.DisTranUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
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
    public Page<BriefUserOrder> getBriefUserOrderList(int pageNum, int pageSize,String username){
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getBriefUserOrderList(username);
        ArrayList<BriefUserOrder> briefUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder : tmpUserOrderList){
            briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime()));
        }
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        return new Page<BriefUserOrder>(new PageInfo<>(briefUserOrderArrayList));
    }

    @Override
    public Page<BriefUserOrder> getBriefUserSendOrderList(int pageNum, int pageSize,String username){
        updateTransportingUserOrderStatus(username);
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getBriefUserSendOrderList(username);
        ArrayList<BriefUserOrder> briefUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder : tmpUserOrderList){
            briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime()));
        }
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        return new Page<BriefUserOrder>(new PageInfo<>(briefUserOrderArrayList));
    }

    @Override
    public Page<BriefUserOrder> getBriefUserRecieveOrderList(int pageNum, int pageSize,String username){
        updateTransportingUserOrderStatus(username);
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getBriefReceiveUserOrderList(username);
        ArrayList<BriefUserOrder> briefUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder : tmpUserOrderList){
            briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime()));
        }
        return new Page<BriefUserOrder>(new PageInfo<>(briefUserOrderArrayList));
    }

    private Page<BriefUserOrder> getBriefUserSendOrderListBystatus(int pageNum, int pageSize,String username,int status){
        updateTransportingUserOrderStatus(username);
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getBriefUserSendOrderList(username);
        ArrayList<BriefUserOrder> briefSpecificUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder:tmpUserOrderList){
            if(tmpUserOrder.getStatus()==status){
                briefSpecificUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime()));
            }
        }
        return new Page<BriefUserOrder>(new PageInfo<>(briefSpecificUserOrderArrayList));
    }

    @Override
    public Page<BriefUserOrder> getBriefUserSendAndUntreatedOrderList(int pageNum, int pageSize,String username){
        return getBriefUserSendOrderListBystatus(pageNum,pageSize,username,0);
    }

    @Override
    public Page<BriefUserOrder> getBriefUserSendAndUTransportingOrderList(int pageNum, int pageSize,String username){
        updateTransportingUserOrderStatus(username);
        return getBriefUserSendOrderListBystatus(pageNum,pageSize,username,1);
    }

    @Override
    public Page<BriefUserOrder> getBriefUserSendAndUnrecievedOrderList(int pageNum, int pageSize,String username){
        updateTransportingUserOrderStatus(username);
        return getBriefUserSendOrderListBystatus(pageNum,pageSize,username,2);
    }

    @Override
    public Page<BriefUserOrder> getBriefUserSendAndRecievedOrderList(int pageNum, int pageSize,String username){
        return getBriefUserSendOrderListBystatus(pageNum,pageSize,username,3);
    }

    private Page<BriefUserOrder> getBriefUserRecieveOrderListBystatus(int pageNum, int pageSize,String username,int status){
        PageHelper.startPage(pageNum, pageSize,"statusUpdateTime desc");
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getBriefReceiveUserOrderList(username);
        ArrayList<BriefUserOrder> briefSpecificUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder:tmpUserOrderList){
            if(tmpUserOrder.getStatus()==status){
                briefSpecificUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(), tmpUserOrder.getStatusUpdateTime()));
            }
        }

        return new Page<BriefUserOrder>(new PageInfo<>(briefSpecificUserOrderArrayList));
    }

    @Override
    public Page<BriefUserOrder> getBriefUserRecieveAndRecievedOrderList(int pageNum, int pageSize,String username){
        return  getBriefUserRecieveOrderListBystatus(pageNum,pageSize,username,0);
    }

    @Override
    public Page<BriefUserOrder> getBriefUserRecieveAndUnrecievedOrderList(int pageNum, int pageSize,String username){
        return  getBriefUserRecieveOrderListBystatus(pageNum,pageSize,username,1);
    }

    @Override
    public Page<BriefUserOrder> getBriefUserRecieveAndTransportingOrderList(int pageNum, int pageSize,String username){
        return  getBriefUserRecieveOrderListBystatus(pageNum,pageSize,username,2);
    }

    @Override
    public Page<BriefUserOrder> getBriefUserRecieveAndUntreatedOrderList(int pageNum, int pageSize,String username){
        return getBriefUserRecieveOrderListBystatus(pageNum,pageSize,username,3);
    }

    @Override
    public List<BriefUserOrder> getUserMessageList(String username){
        List<TmpUserOrder> tmpUserOrderList = userOrderMapper.getUserMessage(0,username);
        ArrayList<BriefUserOrder> briefUserOrderArrayList = new ArrayList<>();
        for(TmpUserOrder tmpUserOrder:tmpUserOrderList){
            if(tmpUserOrder.getSenderUsername().equals(username)){
                briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getReceiverUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime()));
            }else{
                briefUserOrderArrayList.add(new BriefUserOrder(tmpUserOrder.getId(),tmpUserOrder.getSenderUsername(),tmpUserOrder.getStatus(),tmpUserOrder.getStatusUpdateTime()));
            }
        }
        return briefUserOrderArrayList;
    }
}
