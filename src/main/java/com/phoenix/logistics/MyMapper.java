package com.phoenix.logistics;

import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
