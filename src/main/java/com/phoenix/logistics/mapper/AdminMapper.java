package com.phoenix.logistics.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.Tb_admin_user;

import java.util.List;

/**
 * user  generated at 2019-11-30 18:52:33 by: undestiny
 */

public interface AdminMapper extends MyMapper<Tb_admin_user> {

    @Insert("INSERT INTO tb_admin_user(user_name,user_password) VALUES(#{user_name},#{user_password})")
    int insertAdmin(Tb_admin_user admin);

    @Select("SELECT * FROM tb_admin_user WHERE user_name=#{user_name}")
    Tb_admin_user getAdminByUsername(String username);

    //获取所有管理员信息（不包括超级管理员）
    @Select("SELECT * FROM tb_admin_user a WHERE a.user_name NOT IN (SELECT username FROM admin_role WHERE superior=1)")
    List<Tb_admin_user> getAdminAll();

    //删除管理员
    @Delete("DELETE FROM tb_admin_user WHERE id=#{id}")
    int deleteAdmin(Integer id);
}
