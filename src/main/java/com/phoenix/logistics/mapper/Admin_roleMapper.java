package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.phoenix.logistics.entity.Admin_role;

/**
 * user  generated at 2019-11-30 18:52:33 by: undestiny
 */

public interface Admin_roleMapper extends MyMapper<Admin_role> {

    @Select("SELECT * FROM admin_role WHERE username=#{username}")
    Admin_role getAdmin_roleByUsername(@Param("username")String username);

}
