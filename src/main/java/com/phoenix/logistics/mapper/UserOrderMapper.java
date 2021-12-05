package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.User;
import com.phoenix.logistics.entity.UserOrder;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderMapper extends MyMapper<UserOrder>{
    @Insert("INSERT INTO user_order(sender_username,receiver_username,goods_id,status,status_update_time,origin_location,destination_location,commit_time,transport_time) VALUES (#{senderUsername},#{receiverUsername},#{goodsId},#{status},#{statusUpdateTime},#{originLocation},#{destinationLocation},#{commitTime},#{transportTime})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void submitUserOrder(UserOrder userOrder);

    @Update("UPDATE user_order SET status=#{status},status_update_time=#{statusUpdateTime} WHERE id=#{id}")
    void changStatus(@Param("status")Integer status,@Param("statusUpdateTime")String statusUpdateTime,@Param("id")Long id);

    @Select("SELECT * FROM user_order WHERE id=#{id}")
    UserOrder getUserOrderById(@Param("id")Long id);

    @Update("UPDATE user_order SET admin_order_id=#{adminOrderId} WHERE id=#{id}")
    void beDealed(@Param("adminOrderId")Long adminOrderId,@Param("id")Long id);
}
