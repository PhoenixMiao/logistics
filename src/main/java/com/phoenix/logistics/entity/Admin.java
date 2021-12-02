package com.phoenix.logistics.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Controller 管理员")
public class Admin {
    @Id
    @ApiModelProperty("管理员id")
    private Long id;

    @ApiModelProperty("管理员用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("创建时间")
    private String createTime;
}
