package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import org.apache.ibatis.annotations.Select;
import com.phoenix.logistics.entity.Member_message;

import java.util.List;

public interface Member_messageMapper extends MyMapper<Member_message> {

    @Select("SELECT * FROM member_message WHERE user_id=#{user_id} ORDER BY creatime DESC")
    List<Member_message> getMember_messagesByUser_id(Integer user_id);

}
