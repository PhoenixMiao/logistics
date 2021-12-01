package com.phoenix.logistics.controller.homepage;

import com.phoenix.logistics.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.logistics.service.homepage.SearchService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Api("公司基本信息搜索控制器")
@RequestMapping("/search")
@Validated
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/company")
    @ApiOperation("根据公司名、主营产品、报价内容来搜索公司")
    public Result searchCompany(@NotNull @RequestParam("pageNum")Integer pageNum,
                                @NotNull @RequestParam("pageSize")Integer pageSize,
                                @NotBlank @RequestParam("keyWord")String keyWord){
        return Result.success(searchService.searchCompany(pageNum,pageSize,keyWord));
    }

}
