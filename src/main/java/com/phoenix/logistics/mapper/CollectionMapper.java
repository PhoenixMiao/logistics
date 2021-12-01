package com.phoenix.logistics.mapper;

import com.phoenix.logistics.MyMapper;
import org.apache.ibatis.annotations.*;
import com.phoenix.logistics.entity.Tb_collection;

import java.util.List;

public interface CollectionMapper extends MyMapper<Tb_collection> {

    @Insert("INSERT INTO tb_collection VALUES(#{id},#{company_url},#{is_delete},#{user_id},#{company_name},#{order})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer insertCollection(Tb_collection collection);

    @Select("SELECT * FROM tb_collection WHERE user_id=#{user_id}")
    List<Tb_collection> getCollectionsByUser_id(Integer user_id);

    @Select("SELECT * FROM tb_collection WHERE user_id=#{user_id} and company_name=#{company_name} and company_url=#{company_url}")
    Tb_collection getCollectionByUser_idAndCompany_nameAndCompany_url(@Param("user_id") Integer user_id,@Param("company_name") String company_name,@Param("company_url") String company_url);

    @Select("SELECT * FROM tb_collection WHERE id in (${collection_ids}) order by tb_collection.order desc")
    List<Tb_collection> getCollectionsByCollection_ids(String collection_ids);

    @Select("SELECT * FROM tb_collection WHERE id = #{id}")
    Tb_collection getCollectionById(Integer id);

    @Update("UPDATE tb_collection SET tb_collection.order=#{order} WHERE id=#{id}")
    int updateOrderById(@Param("order")Integer order,@Param("id")Integer id);

    @Delete("DELETE FROM tb_collection WHERE id=#{id}")
    int deleteById(Integer id);
}
