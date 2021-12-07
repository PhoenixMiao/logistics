package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.response.BriefAdminOrder;
import com.phoenix.logistics.controller.response.OrderDetailResponse;
import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.entity.UserOrder;

public interface AdminOrderService {
    void dealAdminOrder(Long id,Long carId,Long driverId,String adminUsername);

    AdminOrder getAdminOrderById(Long id);

    OrderDetailResponse getOrderDetailResponse(Long adminOrderId);

    Page<BriefAdminOrder> getBriefAdminOrderList(int pageNum, int pageSize);
}
