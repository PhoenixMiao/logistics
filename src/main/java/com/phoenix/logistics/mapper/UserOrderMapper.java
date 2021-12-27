package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.controller.response.TmpUserOrder;
import com.phoenix.logistics.entity.UserOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderMapper extends MyMapper<UserOrder>{
    @Insert("INSERT INTO userOrder(senderUsername,receiverUsername,goodsId,status,statusUpdateTime,originLocation,destinationLocation,commitTime,transportTime,isRead) VALUES (#{senderUsername},#{receiverUsername},#{goodsId},#{status},#{statusUpdateTime},#{originLocation},#{destinationLocation},#{commitTime},#{transportTime},#{isRead})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void submitUserOrder(UserOrder userOrder);

    @Update("UPDATE userOrder SET status=#{status},statusUpdateTime=#{statusUpdateTime} WHERE id=#{id}")
    void changStatus(@Param("status")Integer status,@Param("statusUpdateTime")String statusUpdateTime,@Param("id")Long id);

    @Select("SELECT * FROM userOrder WHERE id=#{id}")
    UserOrder getUserOrderById(@Param("id")Long id);

    @Update("UPDATE userOrder SET adminOrderId=#{adminOrderId} WHERE id=#{id}")
    void beDealed(@Param("adminOrderId")Long adminOrderId,@Param("id")Long id);

    @Select("SELECT * FROM userOrder WHERE status=#{status} AND (senderUsername=#{username} OR receiverUsername=#{username})")
    List<UserOrder> getTransportingUserOrderByStatusAndUsername(@Param("status")Integer status,@Param("username")String username);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE senderUsername=#{username}")
    List<TmpUserOrder> getBriefSendUserOrderList(@Param("username") String username);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE receiverUsername=#{username}")
    List<TmpUserOrder> getBriefReceiveUserOrderList(@Param("username") String username);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE senderUsername=#{username} AND status=#{status}")
    List<TmpUserOrder> getBriefSendUserOrderListByStatus(@Param("username") String username,@Param("status")Integer status);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE receiverUsername=#{username} AND status=#{status}")
    List<TmpUserOrder> getBriefReceiveUserOrderListByStatus(@Param("username") String username,@Param("status")Integer status);

    @Update("UPDATE userOrder SET isRead=#{isRead} WHERE id=#{id}")
    void readUserOrder(@Param("isRead")Integer isRead,@Param("id")Long id);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE isRead=#{isRead} AND (senderUsername=#{username} OR receiverUsername=#{username})")
    List<TmpUserOrder> getUserMessage(@Param("isRead")Integer isRead,@Param("username")String usename);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE senderUsername=#{username} AND id LIKE CONCAT('%',#{id,jdbcType=VARCHAR},'%')")
    List<TmpUserOrder> searchSendUserOrder(@Param("username") String username,@Param("id")Integer id);

    @Select("SELECT id,senderUsername,receiverUsername,status,statusUpdateTime,goodsId FROM userOrder WHERE receiverUsername=#{username} AND id LIKE CONCAT('%',#{id,jdbcType=VARCHAR},'%')")
    List<TmpUserOrder> searchReceiveUserOrder(@Param("username") String username,@Param("id")Integer id);

}
