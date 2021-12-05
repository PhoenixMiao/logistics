package com.phoenix.logistics.service.admin;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.Car;


public interface CarService {

    Page<Car> getAllCars(int pageNum, int pageSize);

    void addCar(Integer num);

    int deleteCar(Long id);

}
