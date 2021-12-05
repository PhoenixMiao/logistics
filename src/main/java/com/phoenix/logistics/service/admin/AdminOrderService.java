package com.phoenix.logistics.service.admin;

public interface AdminOrderService {
    Long dealAdminOrder(Long id,Long carId,Long driverId,String adminUsername);

    int goodsArrive(Long id);
}
