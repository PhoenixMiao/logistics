package com.phoenix.logistics.service.homepage;

import com.phoenix.logistics.dto.Homepage_member;
import com.phoenix.logistics.common.Page;

public interface GetHomePageInfoService {

    Page<Homepage_member> getHomepage_member(Integer pageNum, Integer pageSize);

    Page<Homepage_member> getHomepage_notMember(Integer pageNum,Integer pageSize);

}
