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
@ApiModel("主页显示的非会员公司")
public class Homepage_notMember {

    private Long id;
    private Long user_id;
    private String company_name;
    private Timestamp add_time;
    private byte company_status;
    private String company_url;//当主键用

    Homepage_notMember(Tb_company company){
        id = company.getId();
        user_id = company.getUser_id();
        company_name = company.getCompany_name();
        add_time = company.getAdd_time();
        company_status = company.getCompany_status();
        company_url = company.getCompany_url();
    }

}
