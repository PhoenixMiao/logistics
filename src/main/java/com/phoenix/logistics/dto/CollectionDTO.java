package com.phoenix.logistics.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("收藏显示的公司")
public class CollectionDTO {

    private Long company_id;
    private Long user_id;
    private String company_name;
    private String company_phone;
    private String company_content;
    private Timestamp add_time;
    private String company_product;
    private byte company_status;
    private String company_url;//当主键用

    private Integer collection_id;
    private Integer order = 0;//是否置顶，置顶为1

    public CollectionDTO(Homepage_member company){
        company_id = company.getId();
        user_id = company.getUser_id();
        company_name = company.getCompany_name();
        company_phone = company.getCompany_phone();
        company_content = company.getCompany_content();
        add_time = company.getAdd_time();
        company_product = company.getCompany_product();
        company_status = company.getCompany_status();
        company_url = company.getCompany_url();
    }

}
