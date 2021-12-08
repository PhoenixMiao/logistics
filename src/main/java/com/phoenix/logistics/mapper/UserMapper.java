package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.controller.response.BriefUserOrder;
import com.phoenix.logistics.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * user  generated at 2019-11-30 18:52:33 by: undestiny
 */

@Repository
public interface UserMapper extends MyMapper<User> {

    @Update("UPDATE user SET password=#{password} WHERE username=#{username}")
    int updatePasswordByUsername(@Param("password")String password,@Param("username")String username);

    @Select("SELECT password FROM user WHERE username=#{username}")
    String getPasswordByUsername(@Param("username")String username);

    @Select("SELECT * FROM user WHERE username=#{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User getUserById(Integer id);

    @Insert("INSERT INTO user(username,password) VALUES(#{username},#{password})")
    int insertUser(@Param("username")String username,@Param("password")String password);

    @Update("UPDATE user SET gender=#{gender} WHERE username=#{username}")
    void updateUserGender(@Param("gender")Integer gender,@Param("username")String username);

    @Update("UPDATE user SET residence=#{residence} WHERE username=#{username}")
    void updateUserResidence(@Param("residence")String residence,@Param("username")String username);

    @Update("UPDATE user SET telephone=#{telephone} WHERE username=#{username}")
    void updateUserTelephone(@Param("telephone")String telephone,@Param("username")String username);


}
