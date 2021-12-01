package com.phoenix.logistics.service.company;

import org.springframework.web.multipart.MultipartFile;
import com.phoenix.logistics.entity.Tb_company;

import java.io.IOException;

public interface CompanyInfoService{

    //修改公司基本信息
    //填写公司的名称、logo、主营业务、公司简介、公司地址、联系人、电话、手机微信、qq、传真，
    // 选填上传营业执照、公司授权书等信息
    Tb_company setBaseInfo(Tb_company company);
    String setLogo(MultipartFile logo,long user_id) throws IOException;
    String setLicense(MultipartFile license,long user_id) throws IOException;
    String setAuthorization(MultipartFile authorization,long user_id) throws IOException;

    //修改公司报价（报价，有效时长，用户id）
    Tb_company setContent(String company_content,int hours,long user_id);

    //封盘（让报价直接过期，修改company_status字段为1，返回公司id）
    //用户id
    long closeContent(long user_id);

    //获取公司信息
    Tb_company getCompanyById(long id);

    //获取公司信息
    Tb_company getCompanyByUser_id(long user_id);

    //修改公司报价附件（报价附件，有效时长，用户id）
    String setContentFile(MultipartFile contentFile,Integer hours,long user_id) throws IOException;

    //上传报价里的图片
    String setContentPicture(MultipartFile contentFile,long user_id) throws IOException;


}
