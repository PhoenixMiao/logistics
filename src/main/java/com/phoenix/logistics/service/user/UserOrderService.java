package com.phoenix.logistics.service.user;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.controller.response.BriefUserOrder;
import com.phoenix.logistics.entity.UserOrder;
import com.phoenix.logistics.entity.UserOrder;

import java.util.HashMap;

public interface UserOrderService {
    HashMap<String,Long> submitUserOrder(String username,SubmitUserOrderRequest submitUserOrderRequest);

    int receiveGoods(Long id);

    UserOrder getUserOrderById(Long id);

    Page<BriefUserOrder> getBriefUserOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserSendOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserRecieveOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserSendAndUntreatedOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserSendAndUTransportingOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserSendAndUnrecievedOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserSendAndRecievedOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserRecieveAndRecievedOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserRecieveAndUnrecievedOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserRecieveAndTransportingOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserRecieveAndUntreatedOrderList(int pageNum, int pageSize,String username);
}
