package com.phoenix.logistics.service.homepage;

import com.phoenix.logistics.common.Page;
import com.phoenix.logistics.entity.Tb_company;

public interface SearchService {

    Page<Tb_company> searchCompany(Integer pageNum, Integer pageSize,String keyWord);

}
