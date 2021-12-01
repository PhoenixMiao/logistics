package com.phoenix.logistics.service.account.impl;

import com.phoenix.logistics.util.DatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.service.account.SignInService;
import com.phoenix.logistics.util.RedisUtil;
import com.phoenix.logistics.util.RedisUtilComplex;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {

    /**
     * 用户登录命名规则（当天过期）
     * key格式：{项目名}:USERLOGIN:{日期}:{用户id}
     * value：今日该用户登录次数
     * 例如：2020-12-20当天用户(id=3)登录了5次
     * key为：STELL:USERLOGIN:2020-12-20:3
     * value为：5
     */

    /**
     * 用户登录统计命名规则（一年过期）
     * key格式：{项目名}:USERLOGIN:{日期}
     * value：当日用户登录数
     * 例如：2020-12-20当天有28个用户登录
     * key为：STELL:USERLOGIN:2020-12-20
     * value为：28
     */

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisUtilComplex redisUtilComplex;

    @Override
    public void recordUserLogin(int id) {

        Timestamp today = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //用户当日登录数key
        String userLoginTimesKey = "STELL:USERLOGIN:" + format.format(today) + ":" + id;

        //当日登录统计key
        String todayLoginTimesKey = "STELL:USERLOGIN:" + format.format(today);

        //记录该用户登录
        int loginTimes = Optional.ofNullable((Integer) redisUtil.get(userLoginTimesKey)).orElse(0);
        loginTimes ++;
        Date dayEnd = DatesUtil.getDayEnd();
        redisUtilComplex.set(userLoginTimesKey,loginTimes,dayEnd.getTime());

        //加入统计
        int todayLoginTimes = Optional.ofNullable((Integer) redisUtil.get(todayLoginTimesKey)).orElse(0);
        todayLoginTimes ++;
        Date endDayOfYear = DatesUtil.getEndDayOfYear();
        redisUtilComplex.set(todayLoginTimesKey,todayLoginTimes,endDayOfYear.getTime());
    }
}
