package com.phoenix.logistics.controller.homepage;

import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.service.company.CompanyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.logistics.service.homepage.VisitService;

@RestController
@Api("访问公司主页控制器")
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    CompanyInfoService companyInfoService;

    @Autowired
    VisitService visitService;

//    @GetMapping("/{id}")
//    @ApiImplicitParam(name = "id",value = "公司id")
//    @ApiOperation("获取公司信息")
//    public Result visitCompany(@PathVariable("id")long id){
//        return Result.success(companyInfoService.getCompanyById(id));
//    }

    @GetMapping("/today_times/{id}")
    @ApiOperation("获取公司今日访问量")
    @ApiImplicitParam(name = "id",value = "公司id")
    public Result getToday_times(@PathVariable("id")long id){
        return Result.success(visitService.getVisitCompanyToday(id));
    }

}
