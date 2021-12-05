package com.phoenix.logistics.service.user;

import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;

import java.util.HashMap;

public interface UserOrderService {
    HashMap<String,Long> submitUserOrder(SubmitUserOrderRequest submitUserOrderRequest);

    int receiveGoods(Long id);
}
