package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.controller.response.BriefAdminOrder;
import com.phoenix.logistics.controller.response.OrderDetailResponse;
import com.phoenix.logistics.controller.response.TmpAdminOrder;
import com.phoenix.logistics.controller.response.TmpUserOrder;
import com.phoenix.logistics.entity.AdminOrder;

import java.util.List;

public interface AdminOrderService {
    void dealAdminOrder(Long id,Long carId,Long driverId,String adminUsername);

    AdminOrder getAdminOrderById(Long id);

    OrderDetailResponse getOrderDetailResponse(Long adminOrderId);

    Page<TmpAdminOrder> getBriefAdminOrderListByStatus(int pageNum, int pageSize, int status);

    List<BriefAdminOrder> getAdminMessageList();

    Page<TmpAdminOrder> searchAdminOrder(int pageNum,int pageSize,Integer id);
}
