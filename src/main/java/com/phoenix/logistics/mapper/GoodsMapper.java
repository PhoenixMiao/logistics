package com.phoenix.logistics.mapper;

import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.Car;
import com.phoenix.logistics.entity.Goods;
import org.apache.ibatis.annotations.*;
import com.phoenix.logistics.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends MyMapper<Goods>{
    @Insert("INSERT INTO goods(name,type,volume,weight,value) VALUES (#{name},#{type},#{volume},#{weight},#{value})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void addGoods(Goods good);

}
