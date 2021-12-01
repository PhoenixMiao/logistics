package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.phoenix.logistics.entity.Message;

public interface MessageMapper extends MyMapper<Message> {

    @Insert("INSERT INTO message(company_id,content,creatime,is_del) VALUES " +
            "(#{company_id},#{content},current_timestamp,0)")
    int insertMessage(Message message);

    //limit0,1是为了避免重复收到提醒更新报价的通知
    @Select("SELECT * FROM message WHERE company_id=#{company_id} AND is_del=0 LIMIT 0,1")
    Message getMessageByCompany_id(Long company_id);
    @Select("SELECT * FROM message WHERE company_id=(" +
            "SELECT id FROM tb_company WHERE user_id=#{user_id}) " +
            "AND is_del=0 LIMIT 0,1")
    Message getMessageByUser_id(Long user_id);

    //更新为已读
    @Update("UPDATE message SET is_del=1 WHERE company_id=#{company_id}")
    int readMessageByCompany_id(Long company_id);
    @Update("UPDATE message SET is_del=1 WHERE company_id=(" +
            "SELECT id FROM tb_company WHERE user_id=#{user_id})")
    int readMessageByUser_id(Long user_id);
}
