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

    @Select("SELECT * FROM admin WHERE userName=#{userName}")
    Admin getAdminByUsername(String username);

}
