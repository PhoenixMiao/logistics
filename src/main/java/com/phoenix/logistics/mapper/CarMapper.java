package com.phoenix.logistics.mapper;

import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.Car;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import com.phoenix.logistics.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarMapper extends MyMapper<Car> {

    @Select("SELECT * FROM car")
    List<Car> getAllCars();

    @Insert("INSERT INTO car VALUES (null,#{status})")
    int insertCar(@Param("status") Integer status);

    @Select("SELECT FROM car WHERE id=#{id}")
    Car getCarById(@Param("id") Long id);

    @Delete("DELETE FROM car WHERE id=#{id}")
    void deleteCar(@Param("id")Long id);

    @Update("UPDATE car SET status=#{status} WHERE id=#{id}")
    void allocateCar(@Param("status")Integer status,@Param("id")Long id);

}
