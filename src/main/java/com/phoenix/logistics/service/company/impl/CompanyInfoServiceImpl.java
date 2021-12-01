package com.phoenix.logistics.service.company.impl;

import com.phoenix.logistics.exception.RRException;
import com.phoenix.logistics.mapper.CompanyMapper;
import com.phoenix.logistics.service.company.CompanyInfoService;
import com.phoenix.logistics.util.MultipartFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.logistics.common.EnumExceptionType;
import com.phoenix.logistics.entity.Tb_company;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    MultipartFileUtil multipartFileUtil;

    @Override
    public Tb_company setBaseInfo(Tb_company company) {
        //  填写公司的名称、logo、主营业务、公司简介、公司地址、联系人、电话、
        // 手机、微信、qq、传真，选填上传营业执照、公司授权书等信息
        company = Tb_company.builder().user_id(company.getUser_id())
                .company_name(Optional.ofNullable(company.getCompany_name()).orElse("新公司"))
                .company_product(Optional.ofNullable(company.getCompany_product()).orElse(""))
                .company_intro(Optional.ofNullable(company.getCompany_intro()).orElse(""))
                .company_address(Optional.ofNullable(company.getCompany_address()).orElse(""))
                .company_contacts(Optional.ofNullable(company.getCompany_contacts()).orElse(""))
                .company_phone(Optional.ofNullable(company.getCompany_phone()).orElse(""))
                .company_mobile(Optional.ofNullable(company.getCompany_mobile()).orElse(""))
                .wechat(Optional.ofNullable(company.getWechat()).orElse(""))
                .qq(Optional.ofNullable(company.getQq()).orElse(""))
                .company_fax(Optional.ofNullable(company.getCompany_fax()).orElse(""))
                .company_url(Optional.ofNullable(company.getCompany_url()).orElse(""))
                .build();

        //存文件


        //插入数据
        //如果有就更新，没有就插入
        Long id = companyMapper.getIdByUser_id(company.getUser_id());
        if(id == null){
            companyMapper.insert(company);
        }else{
            companyMapper.updateBaseInfoById(
                    company.getCompany_name(),
                    company.getCompany_product(),
                    company.getCompany_intro(),
                    company.getCompany_address(),
                    company.getCompany_contacts(),
                    company.getCompany_phone(),
                    company.getCompany_mobile(),
                    company.getWechat(),
                    company.getQq(),
                    company.getCompany_fax(),
                    company.getCompany_url(),
                    id
            );
        }
        company = companyMapper.getCompanyByUser_id(company.getUser_id());

        return company;
    }

    @Override
    public String setLogo(MultipartFile logo, long user_id) throws IOException {
        Tb_company company = companyMapper.getCompanyByUser_id(user_id);
        if(company == null)return "";
        //保存文件
        Long company_id = company.getId();
        String logoUrl = multipartFileUtil.saveLogo(logo, company_id);

        //更新logo字段
        companyMapper.updateLogo(logoUrl,company_id);
        company.setCompany_logo(logoUrl);
        return logoUrl;
    }

    @Override
    public String setLicense(MultipartFile license, long user_id) throws IOException {
        Tb_company company = companyMapper.getCompanyByUser_id(user_id);
        if(company == null)return "";
        //保存文件
        Long company_id = company.getId();

        return multipartFileUtil.saveLicense(license, company_id);
    }

    @Override
    public String setAuthorization(MultipartFile authorization, long user_id) throws IOException {
        Tb_company company = companyMapper.getCompanyByUser_id(user_id);
        if(company == null)return "";
        //保存文件
        Long company_id = company.getId();

        return multipartFileUtil.saveAuthorization(authorization, company_id);
    }

    @Override
    public Tb_company setContent(String company_content, int hours, long user_id) {
        Timestamp add_time = new Timestamp(System.currentTimeMillis());
        Timestamp update_time = new Timestamp(add_time.getTime() + hours * 3600000);

        Long id = companyMapper.getIdByUser_id(user_id);
        if(id == null){
            throw new RRException(EnumExceptionType.COMPANY_BASEINFO_NOT_EXIST);
        }
        companyMapper.updateContentById(company_content,add_time,update_time,id);
        Tb_company company = Tb_company.builder().id(id).build();
        company = companyMapper.selectOne(company);
        return company;
    }

    @Override
    public long closeContent(long user_id) {
        Long id = companyMapper.getIdByUser_id(user_id);
        if(id == null){
            throw new RRException(EnumExceptionType.COMPANY_BASEINFO_NOT_EXIST);
        }
        companyMapper.closeContent(id);
        return id;
    }

    @Override
    public Tb_company getCompanyById(long id) {
        Tb_company company = companyMapper.getCompanyById(id);
        if(company == null){
            throw new RRException(EnumExceptionType.COMPANY_NOT_EXIST);
        }
        return company;
    }

    @Override
    public Tb_company getCompanyByUser_id(long user_id) {
        Tb_company company = companyMapper.getCompanyByUser_id(user_id);
        if(company == null){
            throw new RRException(EnumExceptionType.COMPANY_NOT_EXIST);
        }
        return company;
    }

    @Override
    public String setContentFile(MultipartFile contentFile, Integer hours, long user_id) throws IOException {
        Tb_company company = companyMapper.getCompanyByUser_id(user_id);
        if(company == null){
            throw new RRException(EnumExceptionType.COMPANY_BASEINFO_NOT_EXIST);
        }
        //保存文件
        Long company_id = company.getId();
        String contentFileUrl = multipartFileUtil.saveContentFile(contentFile, company_id);

        //更新company_file_url字段
        companyMapper.updateCompany_file_url(contentFileUrl,company_id);
        company.setCompany_file_url(contentFileUrl);


        //处理hours
        if(hours == null)return contentFileUrl;
        Timestamp add_time = new Timestamp(System.currentTimeMillis());
        Timestamp update_time = new Timestamp(add_time.getTime() + hours * 3600000);

        Long id = company.getId();
        companyMapper.updateContentById(company.getCompany_content(),add_time,update_time,id);

        return contentFileUrl;
    }

    @Override
    public String setContentPicture(MultipartFile contentFile, long user_id) throws IOException {
        Tb_company company = companyMapper.getCompanyByUser_id(user_id);
        if(company == null)return "";
        //保存文件
        Long company_id = company.getId();
        String contentPictureUrl = multipartFileUtil.saveContentPicture(contentFile, company_id);

        return contentPictureUrl;
    }
}
