package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.dto.BuyHistoryDTO;
import com.phoenix.logistics.dto.Buy_historyStatics;
import com.phoenix.logistics.entity.Member;
import com.phoenix.logistics.entity.Tb_admin_user;

import java.sql.Timestamp;


public interface AdminService {

    //查看购买记录
    Page<BuyHistoryDTO> getBuy_historyAll(int pageNum, int pageSize);

    //查看某一天充值会员数，总金额
    Buy_historyStatics getBuy_historyStatics_day(Timestamp timestamp);

    //查看某个月充值会员数，总金额
    Buy_historyStatics getBuy_historyStatics_month(Timestamp timestamp);

    //查看会员列表
    Page<Member> getMemberAll(int pageNum, int pageSize);

    //查看普通管理员列表
    Page<Tb_admin_user> getAdminAll(int pageNum, int pageSize);

    //查看今日登录客户数
    int getUserLoginToday();

    //删除普通管理员
    void deleteAdmin(Integer id);

    Tb_admin_user addAdmin(String user_name, String user_password);
}
