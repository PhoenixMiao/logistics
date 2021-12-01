package com.phoenix.logistics.service.homepage.impl;

import com.phoenix.logistics.mapper.CompanyMapper;
import com.phoenix.logistics.service.homepage.VisitService;
import com.phoenix.logistics.util.DatesUtil;
import com.phoenix.logistics.util.RedisUtil;
import com.phoenix.logistics.util.RedisUtilComplex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    /**
     * 公司今日访问次数命名规则
     * key格式：{项目名}:VISIT:{公司id}
     * 访问公司id=3
     * key为：STELL:VISIT:3
     */

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    RedisUtilComplex redisUtilComplex;

    @Autowired
    RedisUtil redisUtil;

    //id：公司id
    public void visitCompany(long id){
        //公司今日被访问次数的key
        String visit_company_key = "STELL:VISIT:" + id;

        //当日结束时间
        Date dayEnd = DatesUtil.getDayEnd();

        int company_browse_num_today = Optional.ofNullable((Integer)redisUtil.get(visit_company_key)).orElse(0);

        company_browse_num_today++;
        redisUtilComplex.set(visit_company_key,company_browse_num_today,dayEnd.getTime());

        //目前总访问次数
        int company_browse_num_total = Optional.ofNullable(companyMapper.getCompany_browse_numById(id)).orElse(0);

//        //如果目前总访问次数少于当日访问次数，就更新为(当日访问次数+10)*2
//        //否则就只是总访问次数+1
//        if(company_browse_num_total < company_browse_num_today){
//            company_browse_num_total = (company_browse_num_today + 10) * 2;
//            companyMapper.setCompany_browse_numById(company_browse_num_total,id);
//        }else{
//            companyMapper.setCompany_browse_numById(company_browse_num_total+1,id);
//        }
        companyMapper.setCompany_browse_numById(company_browse_num_total+1,id);
    }

    //获取当日访问次数
    @Override
    public int getVisitCompanyToday(long id) {
        //公司今日被访问次数的key
        String visit_company_key = "STELL:VISIT:" + id;

        int company_browse_num_today = Optional.ofNullable((Integer)redisUtil.get(visit_company_key)).orElse(0);
        return company_browse_num_today;
    }



}
