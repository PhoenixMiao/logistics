package com.phoenix.logistics.mapper;


import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.controller.response.TmpAdminOrder;
import com.phoenix.logistics.entity.AdminOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminOrderMapper extends MyMapper<AdminOrder> {
    @Insert("INSERT INTO adminOrder(userOrderId,goodsId,transportTime,status,statusUpdateTime,isRead) VALUES (#{userOrderId},#{goodsId},#{transportTime},#{status},#{statusUpdateTime},#{isRead})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int newAdminOrder(AdminOrder adminOrder);

    @Update("UPDATE adminOrder SET carId =#{carId},driverId=#{driverId},adminUsername=#{adminUsername},status=#{status},sendTime=#{sendTime},arriveTime=#{arriveTime},statusUpdateTime=#{statusUpdateTime} WHERE id=#{id}")
    void DealAdminOrder(@Param("carId")Long carId,@Param("driverId")Long driverId,@Param("adminUsername")String adminUsername,@Param("status")Integer status,@Param("sendTime")String sendTime,@Param("arriveTime")String arriveTime,@Param("statusUpdateTime")String statusUpdateTime,@Param("id")Long id);

    @Select("SELECT transportTime FROM adminOrder WHERE id=#{id}")
    Integer getTransportTimeById(@Param("id")Long id);

    @Update("UPDATE adminOrder SET status=#{status},statusUpdateTime=#{statusUpdateTime} WHERE id=#{id}")
    void changeStatus(@Param("status")Integer status,@Param("statusUpdateTime")String statusUpdateTime,@Param("id")Long id);

    @Select("SELECT * FROM adminOrder WHERE id=#{id}")
    AdminOrder getAdminOrderById(@Param("id") Long id);

    @Select("SELECT id,userOrderId,status,statusUpdateTime,goodsId FROM adminOrder")
    List<TmpAdminOrder> getBriefAdminOrderList();

    @Select("SELECT id,userOrderId,status,statusUpdateTime,goodsId FROM adminOrder WHERE status=#{status}")
    List<TmpAdminOrder> getBriefAdminOrderListByStatus(@Param("status")Integer status);

    @Select("SELECT * FROM adminOrder WHERE status=#{status}")
    List<AdminOrder> getAdminOrderByStatus(@Param("status") Integer status);

    @Update("UPDATE adminOrder SET isRead=#{isRead} WHERE id=#{id}")
    void readAdminOrder(@Param("isRead")Integer isRead,@Param("id")Long id);

    @Select("SELECT id,userOrderId,status,statusUpdateTime FROM adminOrder WHERE isRead=#{isRead}")
    List<TmpAdminOrder> getAdminMessage(@Param("isRead")Integer isRead);

    @Select("SELECT id,userOrderId,status,statusUpdateTime,goodsId FROM adminOrder WHERE id LIKE CONCAT('%',#{id,jdbcType=VARCHAR},'%')")
    List<TmpAdminOrder> searchAdminOrder(@Param("id")Integer id);
}
