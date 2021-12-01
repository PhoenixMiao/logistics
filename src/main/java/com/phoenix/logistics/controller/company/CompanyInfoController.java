package com.phoenix.logistics.controller.company;

import com.google.gson.Gson;
import com.phoenix.logistics.common.Result;
import com.phoenix.logistics.service.company.CompanyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.entity.Tb_company;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@Api("公司基本信息和报价信息控制器")
@RequestMapping("/company")
@Validated
public class CompanyInfoController {

    @Resource(name = "json")
    Gson gson;

    @Autowired
    CompanyInfoService companyInfoService;

//  填写公司的名称、logo、主营业务、公司简介、公司地址、联系人、电话、
// 手机微信、qq、传真，选填上传营业执照、公司授权书等信息
    @RequiresRoles("company")
    @PostMapping("")
    @ApiOperation("填写公司基本信息")
    public Result setCompanyBasicInformation(@Valid @NotNull @RequestBody Tb_company company) throws IOException {
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        company.setUser_id(Long.parseLong(user_id+""));
        return Result.success(companyInfoService.setBaseInfo(company));
    }

    @RequiresRoles("company")
    @PutMapping("/logo")
    @ApiOperation("上传公司logo")
    public Result setCompanyLogo(@RequestPart(value = "logo")MultipartFile logo) throws IOException {

        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.setLogo(logo,user_id));
    }

    @RequiresRoles("company")
    @PutMapping("/authorization")
    @ApiOperation("上传公司授权书")
    public Result setCompanyAuthorization(@RequestPart(value = "authorization")MultipartFile authorization) throws IOException {

        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.setAuthorization(authorization,user_id));
    }

    @RequiresRoles("company")
    @PutMapping("/license")
    @ApiOperation("上传营业执照")
    public Result setCompanyLicense(@RequestPart(value = "license")MultipartFile license) throws IOException {

        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.setLicense(license,user_id));
    }

    @RequiresRoles("company")
    @PostMapping("/company_content")
    @ApiOperation("更新报价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company_content", value = "报价内容（不为空）", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "hours", value = "报价有效时常（1-8小时）", required = true, paramType = "query", dataType = "Integer"),
    })
    public Result setContent(@NotBlank @RequestParam("company_content") String company_content,
                             @Range(min = 1,max = 8) @RequestParam("hours") int hours){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.setContent(company_content,hours,user_id));
    }

    @RequiresRoles("company")
    @PostMapping("/closeContent")
    @ApiOperation("主动封盘")
    public Result closeContent(){
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.closeContent(user_id));
    }

    @GetMapping("")
    @ApiOperation("根据id或user_id获取公司全部信息（传一个参数即可）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户id", required = false, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "id", value = "公司id", required = false, paramType = "query", dataType = "Long"),
    })
    public Result getCompanyInfo(@RequestParam(value = "user_id",required = false) Long user_id,
                                 @RequestParam(value = "id",required = false) Long id){
        if(id != null){
            return Result.success(companyInfoService.getCompanyById(id));
        }else if(user_id != null){
            return Result.success(companyInfoService.getCompanyByUser_id(user_id));
        }else{
            return Result.fail("error");
        }
    }

    @RequiresRoles("company")
    @PutMapping("/contentFile")
    @ApiOperation("更新报价附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contentFile", value = "报价内容（文件）", required = true),
            @ApiImplicitParam(name = "hours", value = "报价有效时常（1-8小时）", required = false),
    })
    public Result setContentFile(@RequestPart("contentFile") MultipartFile contentFile,
                                @RequestParam(value = "hours",required = false) Integer hours) throws IOException {
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.setContentFile(contentFile,hours,user_id));
    }


    @RequiresRoles("company")
    @PutMapping("/content/picture")
    @ApiOperation("上传报价里的图片")
    @ApiImplicitParam(name = "picture", value = "图片", required = true)
    public Result setContentPicture(@RequestPart(value = "picture") MultipartFile contentPicture) throws IOException {
        UserDTO principal = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int user_id = principal.getId();
        return Result.success(companyInfoService.setContentPicture(contentPicture,user_id));
    }



}
