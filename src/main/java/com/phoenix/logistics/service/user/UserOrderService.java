package com.phoenix.logistics.service.user;

import com.phoenix.logistics.controller.request.SubmitUserOrderRequest;

public interface UserOrderService {
    void submitUserOrder(SubmitUserOrderRequest submitUserOrderRequest);
}
