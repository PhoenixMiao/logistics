package com.phoenix.logistics.service.message;

import com.aliyuncs.exceptions.ClientException;
import com.phoenix.logistics.entity.Message;

public interface MessageService {

    //提醒公司更新报价
    void remindUpdateContent(long id) throws ClientException;

    //获取是否有被提醒更新报价的通知，有就获取最新一条
     Message getRemindUpdateContent(long user_id);

     //更新消息至已读状态
    void readMessage(long user_id);

}
