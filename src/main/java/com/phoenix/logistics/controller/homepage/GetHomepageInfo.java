package com.phoenix.logistics.controller.homepage;

import com.phoenix.logistics.common.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.logistics.service.homepage.GetHomePageInfoService;

import javax.validation.constraints.NotNull;

@RestController
@Api("主页公司信息展示")
@RequestMapping("/homepage")
@Validated
public class GetHomepageInfo {

    @Autowired
    GetHomePageInfoService getHomePageInfoService;

    @GetMapping("/members")
    public Result getHomepage_member(@NotNull @RequestParam("pageNum")Integer pageNum,
                                     @NotNull @RequestParam("pageSize")Integer pageSize){
        return Result.success(getHomePageInfoService.getHomepage_member(pageNum,pageSize));
    }

    @GetMapping("/notMembers")
    public Result getHomepage_notMember(@NotNull @RequestParam("pageNum")Integer pageNum,
                                     @NotNull @RequestParam("pageSize")Integer pageSize){
        return Result.success(getHomePageInfoService.getHomepage_notMember(pageNum,pageSize));
    }

}
