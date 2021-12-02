package com.phoenix.logistics.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.User_role;

public interface User_roleMapper extends MyMapper<User_role> {

    @Insert("INSERT INTO user_role username VALUES #{username}")
    int insertUser_role(User_role user_role);

    @Select("SELECT * FROM user_role WHERE username=#{username}")
    User_role getUser_roleByUsername(@Param("username")String username);

    @Select("SELECT * FROM user_role WHERE username=(" +
            "SELECT user_name FROM user WHERE id=#{user_id})")
    User_role getUser_roleByUser_id(@Param("user_id")long user_id);

}
