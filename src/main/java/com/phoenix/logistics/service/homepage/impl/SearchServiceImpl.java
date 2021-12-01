package com.phoenix.logistics.service.homepage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.mapper.CompanyMapper;
import com.phoenix.logistics.service.homepage.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Tb_company;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public Page<Tb_company> searchCompany(Integer pageNum, Integer pageSize,String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tb_company> companyList = companyMapper.searchCompany("%" + keyWord + "%");
        return new Page<>(new PageInfo<>(companyList));
    }

}
