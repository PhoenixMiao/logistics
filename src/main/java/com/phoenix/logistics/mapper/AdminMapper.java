package com.phoenix.logistics.mapper;

import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.phoenix.logistics.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user  generated at 2019-11-30 18:52:33 by: undestiny
 */

@Repository
public interface AdminMapper extends MyMapper<User> {

    @Insert("INSERT INTO admin(user_name,user_password) VALUES(#{user_name},#{user_password})")
    int insertAdmin(Admin admin);

    @Select("SELECT * FROM admin WHERE user_name=#{user_name}")
    Admin getAdminByUsername(String username);

    @Select("SELECT * FROM admin")
    List<Admin> getAdminAll();

    //删除管理员
    @Delete("DELETE FROM admin WHERE id=#{id}")
    int deleteAdmin(Integer id);
}
