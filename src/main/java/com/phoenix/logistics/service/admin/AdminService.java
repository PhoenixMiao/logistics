package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Admin;


public interface AdminService {
    //查看普通管理员列表
    Page<Admin> getAdminAll(int pageNum, int pageSize);
}
