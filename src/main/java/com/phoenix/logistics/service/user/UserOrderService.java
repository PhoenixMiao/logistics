package com.phoenix.logistics.service.user;

import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;
import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.entity.UserOrder;

import java.util.HashMap;

public interface UserOrderService {
    HashMap<String,Long> submitUserOrder(SubmitUserOrderRequest submitUserOrderRequest);

    int receiveGoods(Long id);

    void changeUserOrderStatus(Long id);

    UserOrder getAdminOrderById(Long adminOrderId);
}
