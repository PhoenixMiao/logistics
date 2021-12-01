package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.Tb_user;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * user  generated at 2019-11-30 18:52:33 by: undestiny
 */

public interface UserMapper extends MyMapper<Tb_user> {

    @Update("UPDATE tb_user SET password=#{password} WHERE user_name=#{username}")
    int updatePasswordByUsername(@Param("password")String password,@Param("username")String username);

    @Select("SELECT * FROM tb_user WHERE user_name=#{user_name}")
    Tb_user getUserByUsername(String username);

    @Select("SELECT * FROM tb_user WHERE id=#{id}")
    Tb_user getUserById(Integer id);
}
