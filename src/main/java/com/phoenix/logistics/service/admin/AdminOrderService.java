package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.entity.AdminOrder;

public interface AdminOrderService {
    void dealAdminOrder(Long id,Long carId,Long driverId,String adminUsername);

    int goodsArrive(Long id);

    AdminOrder getAdminOrderById(Long id);

}
