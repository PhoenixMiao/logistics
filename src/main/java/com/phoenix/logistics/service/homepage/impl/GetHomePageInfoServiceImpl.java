package com.phoenix.logistics.service.homepage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.logistics.dto.Homepage_member;
import com.phoenix.logistics.mapper.CompanyMapper;
import com.phoenix.logistics.service.homepage.GetHomePageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.phoenix.logistics.common.Page;

import java.util.List;

@Service
public class GetHomePageInfoServiceImpl implements GetHomePageInfoService {

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public Page<Homepage_member> getHomepage_member(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Homepage_member> homepage_memberList = companyMapper.getHomepage_memberList();
        return new Page<>(new PageInfo<>(homepage_memberList));
    }

    @Override
    public Page<Homepage_member> getHomepage_notMember(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Homepage_member> homepage_notMemberList = companyMapper.getHomepage_notMemberList();
        return new Page<>(new PageInfo<>(homepage_notMemberList));
    }
}
