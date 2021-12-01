package com.phoenix.logistics.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.dto.BuyHistoryDTO;
import com.phoenix.logistics.dto.Buy_historyStatics;
import com.phoenix.logistics.entity.Member;
import com.phoenix.logistics.entity.Tb_admin_user;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.mapper.Buy_historyMapper;
import com.phoenix.logistics.mapper.MemberMapper;
import com.phoenix.logistics.service.admin.AdminService;
import com.phoenix.logistics.util.PasswordUtil;
import com.phoenix.logistics.util.RedisUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    /**
     * 用户登录统计命名规则
     * key格式：{项目名}:USERLOGIN:{日期}
     * value：当日用户登录数
     * 例如：2020-12-20当天有28个用户登录
     * key为：STELL:USERLOGIN:2020-12-20
     * value为：28
     */

    @Autowired
    Buy_historyMapper buy_historyMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Page<BuyHistoryDTO> getBuy_historyAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BuyHistoryDTO> buy_historyList = buy_historyMapper.getAll();
        return new Page<>(new PageInfo<>(buy_historyList));

    }

    @Override
    public Buy_historyStatics getBuy_historyStatics_day(Timestamp timestamp) {
        return Buy_historyStatics.builder().buy_times(Optional.ofNullable(buy_historyMapper.getLinesDay(timestamp)).orElse(0))
                .total_price(Optional.ofNullable(buy_historyMapper.getTotalPriceDay(timestamp)).orElse(0.0)).build();
    }

    @Override
    public Buy_historyStatics getBuy_historyStatics_month(Timestamp timestamp) {
        return Buy_historyStatics.builder().buy_times(Optional.ofNullable(buy_historyMapper.getLinesMonth(timestamp)).orElse(0))
                .total_price(Optional.ofNullable(buy_historyMapper.getTotalPriceMonth(timestamp)).orElse(0.0)).build();
    }

    @Override
    public Page<Member> getMemberAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Member> memberList = memberMapper.getMemberAll();
        return new Page<>(new PageInfo<>(memberList));
    }

    @Override
    public Page<Tb_admin_user> getAdminAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tb_admin_user> adminList = adminMapper.getAdminAll();
        return new Page<>(new PageInfo<>(adminList));
    }

    @Override
    public int getUserLoginToday() {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String key = "STELL:USERLOGIN:" + format.format(today);
        return Optional.ofNullable((Integer)redisUtil.get(key)).orElse(0);
    }

    @Override
    public void deleteAdmin(Integer id) {
        if(adminMapper.deleteAdmin(id) < 1){
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }
    }

    @Override
    public Tb_admin_user addAdmin(String user_name, String user_password) {
        if(adminMapper.getAdminByUsername(user_name) != null){
            throw new RRException(EnumExceptionType.ADMIN_ALREADY_EXISTS);
        }

        user_password = PasswordUtil.convert(user_password);
        Tb_admin_user admin = Tb_admin_user.builder().user_name(user_name).user_password(user_password).build();
        adminMapper.insertAdmin(admin);
        return adminMapper.getAdminByUsername(user_name);
    }
}
