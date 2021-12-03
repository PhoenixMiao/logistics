package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.User;
import com.phoenix.logistics.entity.UserOrder;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserOrderMapper extends MyMapper<UserOrder>{
    @Insert("INSERT INTO user_order VALUES (null,#{sender_username},#{receiver_username},#{status},#{origin_location},#{destination_location},#{commit_time},null,#{transport_time})")
    int submitUserOrder(@Param("sender_username")String sender_username,@Param("receiver_username")String receiver_username,@Param("status")Integer status,@Param("origin_location")String origin_location,@Param("destination_location")String destination_location,@Param("commit_time")String commit_time,@Param("transport_time") Integer transport_time);
}
