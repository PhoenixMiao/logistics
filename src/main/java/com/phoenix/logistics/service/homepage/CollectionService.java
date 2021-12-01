package com.phoenix.logistics.service.homepage;

import com.phoenix.logistics.dto.CollectionDTO;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Tb_collection;

import java.util.List;

public interface CollectionService {

    //查看我的分组
    List<String> getMyGroup_names(int user_id);

    //新建分组
    List<String> addGroup(int user_id,String group_name);

    //收藏一个公司，并放到分组里，如果分组不存在就新建
    Tb_collection addCollection(int user_id, String company_url,String group_name);

    //收藏一个公司，并放到分组里，如果分组不存在就新建
    Tb_collection addCollection(int user_id, long company_id,String group_name);

    //查看某个分组的收藏
    Page<Tb_collection> getCollections(int user_id, String group_name,int pageNum,int pageSize);

    //查看某个分组的会员收藏
    Page<CollectionDTO> getCollectionDTOs(int user_id, String group_name, int pageNum, int pageSize);

    //置顶收藏
    void setTop(int user_id,int collection_id);

    //取消置顶
    void removeTop(int user_id,int collection_id);

    //取消收藏
    void removeCollection(int user_id,int collection_id);

    //打开收藏夹中的公司地址
    Tb_collection openCompany_url(int user_id, int collection_id);
}
