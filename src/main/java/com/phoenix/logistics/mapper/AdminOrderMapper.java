package com.phoenix.logistics.mapper;


import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.AdminOrder;
import com.phoenix.logistics.entity.User;
import com.phoenix.logistics.entity.UserOrder;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminOrderMapper extends MyMapper<AdminOrder> {
    @Insert("INSERT INTO admin_order(user_order_id,goods_id,transport_time,status,status_update_time) VALUES (#{userOrderId},#{goodsId},#{transportTime},#{status},#{statusUpdateTime})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int newAdminOrder(AdminOrder adminOrder);

    @Update("UPDATE admin_order SET car_id =#{carId},driver_id=#{driverId},admin_username=#{adminUsername},status=#{status},send_time=#{sendTime},arrive_time=#{arriveTime},status_update_time=#{statusUpdateTime} WHERE id=#{id}")
    void DealAdminOrder(@Param("carId")Long carId,@Param("driverId")Long driverId,@Param("adminUsername")String adminUsername,@Param("status")Integer status,@Param("sendTime")String sendTime,@Param("arriveTime")String arriveTime,@Param("statusUpdateTime")String statusUpdateTime,@Param("id")Long id);

    @Select("SELECT transport_time FROM admin_order WHERE id=#{id}")
    Integer getTransportTimeById(@Param("id")Long id);

    @Update("UPDATE admin_order SET status=#{status},status_update_time=#{statusUpdateTime} WHERE id=#{id}")
    void changeStatus(@Param("status")Integer status,@Param("statusUpdateTime")String statusUpdateTime,@Param("id")Long id);

    @Select("SELECT * FROM admin_order WHERE id=#{id}")
    AdminOrder getAdminOrderById(@Param("id") Long id);
}
