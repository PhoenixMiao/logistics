package com.phoenix.logistics.service.message.impl;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.entity.Message;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.mapper.CompanyMapper;
import com.phoenix.logistics.mapper.MessageMapper;
import com.phoenix.logistics.service.message.MessageService;
import com.phoenix.logistics.util.AliMessageUtil;
import com.phoenix.logistics.util.DatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.entity.Tb_company;
import com.phoenix.logistics.util.RedisUtil;
import com.phoenix.logistics.util.RedisUtilComplex;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {


    /**
     * 当日消息数量命名规则
     * key格式：{项目名}:MESSAGE:TOTAL:{company_id}
     * 提醒公司company_id=12更新报价：
     * key为：STELL:MESSAGE:TOTAL:12
     * value为：当日已提醒的次数（发的消息数）
     */

    /**
     * 当日最新消息命名规则
     * key格式：{项目名}:MESSAGE:NEWEST:{company_id}
     * 提醒公司company_id=12更新报价：
     * key为：STELL:MESSAGE:NEWEST:12
     * value为：Timestamp的time（长整形）
     */

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RedisUtilComplex redisUtilComplex;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AliMessageUtil aliMessageUtil;

    @Override
    public void remindUpdateContent(long id) throws ClientException {
        Tb_company company = companyMapper.getCompanyById(id);
        Optional.ofNullable(company).orElseThrow(() -> new RRException(EnumExceptionType.COMPANY_NOT_EXIST));

        //网站上提醒
        Message message = Message.builder().company_id(id).content("客户提醒您更新报价!").build();
        messageMapper.insertMessage(message);


        //当日发送次数的key
        String messageKeyTotal = "STELL:MESSAGE:TOTAL:" + id;

        //检查当日是否已发送过两条短信
        Integer number = Optional.ofNullable((Integer) redisUtil.get(messageKeyTotal)).orElse(0);
        if(number >= 2){
            return;
        }

        //当日发送的最新消息的key
        String messageKeyNewest = "STELL:MESSAGE:NEWEST:" + id;

        //检查最近一小时内是不是发过
        long nowTime = System.currentTimeMillis();
        Long time = Optional.ofNullable((Long) redisUtil.get(messageKeyNewest)).orElse(0L);
        if(nowTime - time < 3600000){
            return;
        }


        //发送短信提醒
        String tel = companyMapper.getTelById(id);
        aliMessageUtil.sendRemindToUpdateContent(tel);

        //记录到缓存中
            //当日结束时间
        Date dayEnd = DatesUtil.getDayEnd();

        number++;
        redisUtilComplex.set(messageKeyTotal,number,dayEnd.getTime());
        redisUtilComplex.set(messageKeyNewest,nowTime,dayEnd.getTime());
    }

    @Override
    public Message getRemindUpdateContent(long user_id) {
        return messageMapper.getMessageByUser_id(user_id);
    }

    @Override
    public void readMessage(long user_id) {
        messageMapper.readMessageByUser_id(user_id);
    }
}
