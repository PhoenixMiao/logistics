package com.phoenix.logistics.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.phoenix.logistics.entity.Tb_company;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("主页显示的会员公司")
public class Homepage_member {

    private Long id;
    private Long user_id;
    private String company_name;
    private String company_phone;
    private String company_content;
    private Timestamp add_time;
    private Timestamp update_time;
    private String company_product;
    private byte company_status;
    private String company_url;
    private String company_contacts;

    Homepage_member(Tb_company company){
        id = company.getId();
        user_id = company.getUser_id();
        company_name = company.getCompany_name();
        company_phone = company.getCompany_phone();
        company_content = company.getCompany_content();
        add_time = company.getAdd_time();
        update_time = company.getUpdate_time();
        company_product = company.getCompany_product();
        company_status = company.getCompany_status();
        company_url = company.getCompany_url();
        company_contacts = company.getCompany_contacts();
    }

}
