package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.controller.response.BriefUserOrder;
import com.phoenix.logistics.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * user  generated at 2019-11-30 18:52:33 by: undestiny
 */

@Repository
public interface UserMapper extends MyMapper<User> {

    @Update("UPDATE user SET password=#{password} WHERE userName=#{username}")
    int updatePasswordByUsername(@Param("password")String password,@Param("username")String username);

    @Select("SELECT * FROM user WHERE userName=#{userName}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User getUserById(Integer id);
}
