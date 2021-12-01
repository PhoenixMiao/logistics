package com.phoenix.logistics.entity;

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
@ApiModel("会员系统")
public class Member {

    private Integer user_id;
    private Double point = 0.0;
    private Integer if_member;
    private Timestamp expire_time;

}
