package com.phoenix.logistics.mapper;

import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.Driver;
import org.apache.ibatis.annotations.*;
import com.phoenix.logistics.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DriverMapper {
    @Select("SELECT * FROM driver")
    List<Driver> getAllDrivers();

    @Insert("INSERT INTO driver VALUES (null,#{status})")
    int insertDriver(@Param("status") Integer status);

    @Select("SELECT FROM driver WHERE id=#{id}")
    Driver getDriverById(@Param("id") Long id);

    @Delete("DELETE FROM driver WHERE id=#{id}")
    void deleteDriver(@Param("id")Long id);

    @Update("UPDATE driver SET status=#{status} WHERE id=#{id}")
    void allocateDriver(@Param("status")Integer status,@Param("id")Long id);
}
