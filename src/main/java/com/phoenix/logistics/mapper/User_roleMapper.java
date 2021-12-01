package com.phoenix.logistics.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.User_role;

public interface User_roleMapper extends MyMapper<User_role> {

    @Insert("INSERT INTO user_role ( username,company ) VALUES( #{username},#{company} )")
    int insertUser_role(User_role user_role);


    @Update("UPDATE user_role SET company = #{company} where username = #{username}")
    int updateCompanyByUsername(@Param("company")int company,@Param("username")String username);

    @Select("SELECT username FROM user_role WHERE username=#{username} AND company=#{company}")
    String getUsernameByUsernameAndCompany(@Param("username")String username,@Param("company")int company);

    @Select("SELECT * FROM user_role WHERE username=#{username}")
    User_role getUser_roleByUsername(@Param("username")String username);

    @Select("SELECT * FROM user_role WHERE username=(" +
            "SELECT user_name FROM tb_user WHERE id=#{user_id})")
    User_role getUser_roleByUser_id(@Param("user_id")long user_id);

}
