package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Car;
import com.phoenix.logistics.entity.Driver;

public interface DriverService {
    Page<Driver> getAllDrivers(int pageNum, int pageSize);

    void addDriver(Integer num);

    int deleteDriver(Long id);
}
