package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Car;
import com.phoenix.logistics.entity.Driver;

import java.util.List;

public interface DriverService {
    Page<Driver> getAllDrivers(int pageNum, int pageSize);

    void addDriver(Integer num);

    void deleteDriver(Long id);

    Page<Driver> getAllFreeDrivers(int pageNum,int pageSize);

    List<Driver> getAllFreeDrivers();
}
