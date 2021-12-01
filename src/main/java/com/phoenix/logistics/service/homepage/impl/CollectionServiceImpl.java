package com.phoenix.logistics.service.homepage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.dto.CollectionDTO;
import com.phoenix.logistics.dto.Homepage_member;
import com.phoenix.logistics.entity.Collection_group;
import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.mapper.CollectionMapper;
import com.phoenix.logistics.mapper.Collection_groupMapper;
import com.phoenix.logistics.service.BaseService;
import com.phoenix.logistics.service.homepage.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Tb_collection;
import com.phoenix.logistics.entity.Tb_company;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CollectionServiceImpl extends BaseService implements CollectionService {

    @Autowired
    Collection_groupMapper collection_groupMapper;

    @Autowired
    CollectionMapper collectionMapper;

    @Override
    public List<String> getMyGroup_names(int user_id) {

        List<String> group_names = new ArrayList<>();
        group_names.addAll(collection_groupMapper.getGroup_namesByUser_id(user_id));

        if(group_names.contains("默认分组"))return group_names;

        //新建默认分组
        return addGroup(user_id,"默认分组");
    }

    @Override
    public List<String> addGroup(int user_id,String group_name){
        List<String> group_names = new ArrayList<>();
        group_names.addAll(collection_groupMapper.getGroup_namesByUser_id(user_id));

        //分组已存在
        if(group_names.contains(group_name)){
            throw new RRException(EnumExceptionType.COLLECTION_GROUP_ALREADY_EXIST);
        }

        Collection_group collection_group = Collection_group
                .builder()
                .group_name(group_name)
                .user_id(user_id)
                .build();
        if(collection_groupMapper.insert(collection_group) != 1){
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }
        group_names.add(group_name);
        return group_names;
    }

    @Override
    public Tb_collection addCollection(int user_id, String company_url, String group_name) {

        //查找公司是否存在
        Tb_company company = companyMapper.getIdAndCompany_nameByCompany_url(company_url);
        if(company == null){
            throw new RRException(EnumExceptionType.COMPANY_NOT_EXIST);
        }


        //如果已收藏就不能重复收藏
        if(collectionMapper.getCollectionByUser_idAndCompany_nameAndCompany_url(user_id,company.getCompany_name(),company_url) != null){
            throw new RRException(EnumExceptionType.COMPANY_ALREADY_IN_COLLECTION);
        }

        //收藏
        Tb_collection collection = Tb_collection.builder()
                .user_id(user_id)
                .company_name(company.getCompany_name())
                .company_url(company_url)
                .build();
        if(collectionMapper.insertCollection(collection) != 1){
            throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
        }


        //收藏添加到分组中
        //查看分组是否存在，不存在就新建
        Collection_group group = collection_groupMapper.getCollection_groupByUser_idAndGroup_name(user_id,group_name);
        if(group == null){
            //不存在的情况
            group = Collection_group.builder()
                    .user_id(user_id)
                    .group_name(group_name)
                    .collection_ids(collection.getId()+"")
                    .build();
            collection_groupMapper.insert(group);
        }else{
            //存在的情况
            if(group.getCollection_ids() == null || group.getCollection_ids().equals("")){
                group.setCollection_ids(collection.getId() + "");
            }else {
                group.setCollection_ids(group.getCollection_ids() + "," + collection.getId());
            }
            if(collection_groupMapper.updateByPrimaryKey(group) != 1){
                throw new RRException(EnumExceptionType.SYSTEM_INTERNAL_ANOMALY);
            }
        }

        return collection;
    }


    @Override
    public Tb_collection addCollection(int user_id, long company_id, String group_name) {

        //查找公司是否存在
        Tb_company company = companyMapper.getCompanyById(company_id);
        if(company == null){
            throw new RRException(EnumExceptionType.COMPANY_NOT_EXIST);
        }

        return addCollection(user_id,company.getCompany_url(),group_name);
    }

    @Override
    public Page<Tb_collection> getCollections(int user_id, String group_name, int pageNum, int pageSize) {

        Collection_group group = Collection_group.builder().user_id(user_id).group_name(group_name).build();
        group = collection_groupMapper.selectOne(group);
        //查看分组是否存在，不存在就新建
        if(group == null) {
            //不存在的情况
            group = Collection_group.builder()
                    .user_id(user_id)
                    .group_name(group_name)
                    .collection_ids("")
                    .build();
            collection_groupMapper.insert(group);
        }
        String collection_ids = group.getCollection_ids();

        if(collection_ids == null || collection_ids.length() == 0){
            return new Page<>(new PageInfo<>(new ArrayList<>()));
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Tb_collection> collections = collectionMapper.getCollectionsByCollection_ids(collection_ids);
        return new Page<>(new PageInfo<>(collections));

    }

    @Override
    public Page<CollectionDTO> getCollectionDTOs(int user_id, String group_name, int pageNum, int pageSize) {
        Page<Tb_collection> collections = getCollections(user_id, group_name, pageNum, pageSize);
        List<Tb_collection> collectionList = collections.getItems();
        List<CollectionDTO> collectionDTOs = new ArrayList<>();
        for(Tb_collection collection:collectionList){
            Homepage_member company = companyMapper.getHomepage_memberByCompany_url(collection.getCompany_url());
            CollectionDTO collectionDTO;
            if(company == null){
                collectionDTO = new CollectionDTO();
            }else {
                collectionDTO = new CollectionDTO(company);
            }
                collectionDTO.setCollection_id(collection.getId());
                collectionDTO.setOrder(collection.getOrder());
                collectionDTOs.add(collectionDTO);
        }
        Page<CollectionDTO> collectionDTOPage = new Page<>();
        collectionDTOPage.setItems(collectionDTOs);
        collectionDTOPage.setPageNum(collections.getPageNum());
        collectionDTOPage.setPages(collections.getPages());
        collectionDTOPage.setPageSize(collections.getPageSize());
        collectionDTOPage.setTotal(collections.getTotal());
        return collectionDTOPage;
    }

    @Override
    public void setTop(int user_id, int collection_id) {
        checkCollection(user_id,collection_id);
        collectionMapper.updateOrderById(1,collection_id);
    }

    @Override
    public void removeTop(int user_id, int collection_id) {
        checkCollection(user_id,collection_id);
        collectionMapper.updateOrderById(0,collection_id);
    }

    @Override
    public void removeCollection(int user_id, int collection_id) {
        checkCollection(user_id,collection_id);

        //删除收藏
        collectionMapper.deleteById(collection_id);

        //删除分组中的记录
        Collection_group group = collection_groupMapper.getCollection_groupByCollection_id(collection_id);
        String collection_ids = group.getCollection_ids();
        String[] collection_idArray = collection_ids.split(",");
        collection_ids = "";
        for(String collection_idString : collection_idArray){
            if((collection_id + "").equals(collection_idString))continue;
            collection_ids += collection_idString + ",";
        }
        if(collection_ids.length() > 1){
            collection_ids = collection_ids.substring(0,collection_ids.length()-1);
        }
        group.setCollection_ids(collection_ids);

        collection_groupMapper.updateByPrimaryKey(group);
    }

    @Override
    public Tb_collection openCompany_url(int user_id, int collection_id) {
        return checkCollection(user_id,collection_id);
    }

    //检查该用户是否能管理该收藏
    private Tb_collection checkCollection(int user_id, int collection_id){
        Tb_collection collection = collectionMapper.getCollectionById(collection_id);
        if(collection == null || collection.getUser_id() != user_id){
            throw new RRException(EnumExceptionType.NO_COLLECTION_AUTHORITY);
        }
        return collection;
    }
}
