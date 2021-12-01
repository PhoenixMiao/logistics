package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.Invite;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface InviteMapper extends MyMapper<Invite> {

    @Update("UPDATE invite SET if_publish=#{if_publish} WHERE username=#{username}")
    int updateIf_publishByUsername(@Param("if_publish")Integer if_publish,@Param("username")String username);

}
