package com.phoenix.logistics.service.company;

import com.phoenix.logistics.entity.Member_message;

public interface MessageRemindService {

    //如果有新的通知消息，就获取（获取一条即可）
    Member_message getMessage_remind_new(int user_id);

    //提醒公司报价
    void remindMessage(long company_id);

    //已读通知消息（把新消息全部已读）
    void setMessage_remind_read(int user_id);

}
