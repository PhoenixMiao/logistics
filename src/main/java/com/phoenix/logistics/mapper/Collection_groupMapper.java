package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import com.phoenix.logistics.entity.Collection_group;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface Collection_groupMapper extends MyMapper<Collection_group> {

    @Select("select group_name from collection_group where user_id=#{user_id}")
    List<String> getGroup_namesByUser_id(Integer user_id);

    @Select("select * from collection_group where user_id=#{user_id} and group_name=#{group_name}")
    Collection_group getCollection_groupByUser_idAndGroup_name(@Param("user_id")Integer user_id,@Param("group_name")String group_name);

    //找到一条收藏所在的分组
    @Select("select * from collection_group where collection_ids like '%${collection_id}%'")
    Collection_group getCollection_groupByCollection_id(Integer collection_id);

}
