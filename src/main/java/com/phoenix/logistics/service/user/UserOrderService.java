package com.phoenix.logistics.service.user;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.controller.response.BriefUserOrder;
import com.phoenix.logistics.entity.UserOrder;
import com.phoenix.logistics.entity.UserOrder;

import java.util.HashMap;
import java.util.List;

public interface UserOrderService {
    HashMap<String,Long> submitUserOrder(String username,SubmitUserOrderRequest submitUserOrderRequest);

    int receiveGoods(Long id);

    UserOrder getUserOrderById(Long id);

    Page<BriefUserOrder> getBriefUserOrderList(int pageNum, int pageSize,String username);

    Page<BriefUserOrder> getBriefUserOrderListByCondition(int pageNum, int pageSize,String username,int sendOrReceive,int status);

    List<BriefUserOrder> getUserMessageList(String username);
}
