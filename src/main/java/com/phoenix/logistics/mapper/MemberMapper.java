package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.phoenix.logistics.entity.Member;

import java.sql.Timestamp;
import java.util.List;

public interface MemberMapper extends MyMapper<Member> {

    @Update("UPDATE member SET point=#{point} WHERE user_id=#{user_id}")
    int updatePointByUser_id(@Param("point") Double point,@Param("user_id") Integer user_id);

    //更新会员（包括积分兑换会员、线上支付购买会员）
    @Update("UPDATE member SET " +
            "point=#{point}," +
            "if_member=#{if_member}," +
            "expire_time=#{expire_time} " +
            "WHERE user_id=#{user_id}")
    int updateMemberByUser_id(@Param("point") Double point,
                              @Param("if_member") Integer if_member,
                              @Param("expire_time") Timestamp expire_time,
                              @Param("user_id") Integer user_id);

    //查看会员名单
    @Select("SELECT * FROM member WHERE if_member=1")
    List<Member> getMemberAll();

    //查看会员信息
    @Select("SELECT * FROM member WHERE user_id=#{user_id}")
    Member getMemberByUser_id(int user_id);

}
